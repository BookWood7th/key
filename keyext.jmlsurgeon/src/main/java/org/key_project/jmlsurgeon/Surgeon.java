package org.key_project.jmlsurgeon;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.Problem;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.jml.NodeWithContracts;
import com.github.javaparser.ast.jml.clauses.*;
import com.github.javaparser.ast.jml.doc.JmlDocStmt;
import com.github.javaparser.ast.jml.stmt.*;
import com.github.javaparser.jml.JmlDocSanitizer;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.google.gson.Gson;
import org.key_project.util.collection.Pair;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Surgeon {
    private static final Collection<JmlClauseKind> legalClauseAdditions = new HashSet<JmlClauseKind>();
    static {
        legalClauseAdditions.add(JmlClauseKind.ENSURES);
        legalClauseAdditions.add(JmlClauseKind.ENSURES_FREE);
        legalClauseAdditions.add(JmlClauseKind.ENSURES_REDUNDANTLY);
        legalClauseAdditions.add(JmlClauseKind.MEASURED_BY);
    }

    public static NodeList<Node> parseFragment(String fragment) throws ParsingException {
        ParserConfiguration configuration = new ParserConfiguration();
        configuration.setProcessJml(false);
        JavaParser p = new JavaParser(configuration);
        var result = p.parseStatement(fragment);
        if (result.isSuccessful()) {
            JmlDocStmt jmlDocStmt = (JmlDocStmt) result.getResult().get();
            var sanitizer = new JmlDocSanitizer(new HashSet<>());
            var jmlContent = sanitizer.asString(jmlDocStmt.getJmlComments());
            var contracts = p.parseJmlMethodLevel(jmlContent);
            if (contracts.isSuccessful()) {
                return contracts.getResult().get().getChildren();
            } else {
                throw new ParsingException("Failed to parse JML fragment: " + contracts.getProblems().stream().map(Problem::getMessage).collect(Collectors.joining(", ")));
            }
            //return contract.getResult().get().getChildren().stream().map((x) -> (JmlContract) x).collect(NodeList::new, NodeList::add, NodeList::addAll);
        } else {
            throw new ParsingException("Failed to parse JML fragment: " + result.getProblems().stream().map(Problem::getMessage).collect(Collectors.joining(", ")));
        }
    }

    public static MethodDeclaration parseMethodDeclaration(String methodDeclaration) throws ParsingException {
        ParserConfiguration configuration = new ParserConfiguration();
        configuration.setProcessJml(false);
        JavaParser p = new JavaParser(configuration);
        var result = p.parseMethodDeclaration(methodDeclaration);
        if (result.isSuccessful()) {
            return result.getResult().get();
        } else {
            List<Map<String, String>> problems = new ArrayList<>();
            Base64.Encoder encoder = Base64.getEncoder();
            for (Problem problem : result.getProblems()) {
                Map<String, String> entry = new HashMap<>();
                entry.put("message", encoder.encodeToString(problem.getMessage().getBytes(StandardCharsets.UTF_8)));
                String location = problem.getLocation().map(l -> l.getBegin().getRange().map((r) -> r.begin.toString()).orElse("(line ?,col ?)")).orElse("(line ?,col ?)");
                entry.put("location", encoder.encodeToString(location.getBytes(StandardCharsets.UTF_8)));
                entry.put("cause", encoder.encodeToString(problem.getCause().map(Throwable::getMessage).orElse("").getBytes(StandardCharsets.UTF_8)));
                problems.add(entry);
            }
            throw new ParsingException(new Gson().toJson(problems));
        }
    }

    public static CompilationUnit parseFile(String join) throws ParsingException {
        ParserConfiguration configuration = new ParserConfiguration();
        configuration.setProcessJml(true);
        JavaParser p = new JavaParser(configuration);
        var result = p.parse(join);
        if (result.isSuccessful()) {
            return result.getResult().get();
        } else {
            throw new ParsingException("Failed to parse file: " + result.getProblems().stream().map(Problem::getMessage).collect(Collectors.joining(", ")));
        }
    }

    public static CompilationUnit parseProgram(String program, boolean preprocessJml) throws ParsingException {
        ParserConfiguration configuration = new ParserConfiguration();
        configuration.setProcessJml(preprocessJml);
        JavaParser p = new JavaParser(configuration);
        var result = p.parse(program);
        if (result.isSuccessful()) {
            return result.getResult().get();
        } else {
            List<Map<String, String>> problems = new ArrayList<>();
            Base64.Encoder encoder = Base64.getEncoder();
            for (Problem problem : result.getProblems()) {
                Map<String, String> entry = new HashMap<>();
                entry.put("message", encoder.encodeToString(problem.getMessage().getBytes(StandardCharsets.UTF_8)));
                String location = problem.getLocation().map(l -> l.getBegin().getRange().map((r) -> r.begin.toString()).orElse("(line ?,col ?)")).orElse("(line ?,col ?)");
                entry.put("location", encoder.encodeToString(location.getBytes(StandardCharsets.UTF_8)));
                entry.put("cause", encoder.encodeToString(problem.getCause().map(Throwable::getMessage).orElse("").getBytes(StandardCharsets.UTF_8)));
                problems.add(entry);
            }
            throw new ParsingException(new Gson().toJson(problems));
        }
    }

    public static CompilationUnit addMethodContract(CompilationUnit cu, String methodName, NodeList<Node> contracts) {
        var visitor = new MethodContractInsertionVisitor(methodName, contracts);
        return (CompilationUnit) visitor.visit(cu, null);
    }

    public static CompilationUnit addLoopInvariant(CompilationUnit cu, String methodName, String marker, NodeList<Node> contracts) {
        var visitor = new LoopContractInsertionVisitor(methodName, contracts, marker);
        return (CompilationUnit) visitor.visit(cu, null);
    }

    public static String integrateLoopInvariant(String file, String methodName, String marker, String invariant) throws ParsingException {
        CompilationUnit parsedFile = null;
        NodeList<Node> loopContract = null;
        try {
            parsedFile = parseFile(file);
        } catch (ParsingException e) {
            throw new ParsingException("Failed to parse file: "+e.getMessage());
        }
        try {
            loopContract = parseFragment(invariant);
        } catch (ParsingException e) {
            throw new ParsingException("Failed to parse loop invariant: "+e.getMessage());
        }
        var loopIntegrated = addLoopInvariant(parsedFile, methodName, marker, loopContract);
        return loopIntegrated.toString();
    }

    public static boolean checkProgramAnnotations(String program, String annotatedProgram) throws ParsingException {
        CompilationUnit programParse = parseProgram(program, true);
        programParse.getAllComments().forEach(Comment::remove);

        CompilationUnit annotatedParse = parseProgram(annotatedProgram, true);
        annotatedParse.getAllComments().forEach(Comment::remove);

        List<JmlContract> jmlContracts = programParse.findAll(JmlContract.class);

        List<JmlContract> annotatedJmlComments = annotatedParse.findAll(JmlContract.class);

        for (JmlContract jmlContract : jmlContracts) {
            if (!annotatedJmlComments.contains(jmlContract)) {
                return false;
            }
        }

        programParse = parseProgram(program, false);
        programParse.getAllComments().forEach(Comment::remove);

        annotatedParse = parseProgram(annotatedProgram, false);
        annotatedParse.getAllComments().forEach(Comment::remove);


        return NoJMLEqualsVisitor.equals(programParse, annotatedParse) != null;
    }

    private static boolean validContractAlteration(JmlContract c1, JmlContract c2) {
        if (c1.getBehavior() != c2.getBehavior()) {
            if (c1.getBehavior() == null || c2.getBehavior() == null) {
                return false;
            }
            if (!c1.getBehavior().equals(c2.getBehavior())) {
                return false;
            }
        }
        if (!clauseListMatches(c1.getClauses(), c2.getClauses(), legalClauseAdditions)) {
            return false;
        }
        if (!c1.getJmlTags().equals(c2.getJmlTags())) {
            return false;
        }
        if (!c1.getModifiers().equals(c2.getModifiers())) {
            return false;
        }
        if (!c1.getName().equals(c2.getName())) {
            return false;
        }
        if (!c1.getSubContracts().equals(c2.getSubContracts())) {
            return false;
        }
        if (!c1.getType().equals(c2.getType())) {
            return false;
        }
        return c1.getComment().equals(c2.getComment());
    }

    private static boolean clauseListMatches(NodeList<JmlClause> n, NodeList<JmlClause> n2, Collection<JmlClauseKind> clauseKindsToDisregard) {
        if (n == n2) {
            return true;
        } else if (n != null && n2 != null) {
            Collection<JmlClause> nClauseSet = new HashSet<>(n);
            Collection<JmlClause> n2ClauseSet = new HashSet<>(n2);
            for (JmlClause c : n) {
                boolean foundInN2 = n2.stream().anyMatch(other -> NoJMLEqualsVisitor.equals(c, other) == null);
                if (!foundInN2) {
                    return false;
                }
            }
            return n2ClauseSet.stream().filter(c-> !nClauseSet.contains(c)) //only consider non-original clauses
                    .map(JmlClause::getKind).allMatch(clauseKindsToDisregard::contains);
        } else {
            return false;
        }
    }

    public static List<String> getIllegallyChangedContracts(String program, String annotatedProgram) throws ParsingException {
        List<JmlContract> changedContracts = new ArrayList<>();

        CompilationUnit programParse = parseProgram(program, true);
        programParse.getAllComments().forEach(Comment::remove);

        CompilationUnit annotatedParse = parseProgram(annotatedProgram, true);
        annotatedParse.getAllComments().forEach(Comment::remove);

        List<JmlContract> jmlContracts = programParse.findAll(JmlContract.class);

        List<JmlContract> annotatedJmlComments = annotatedParse.findAll(JmlContract.class);

        for (JmlContract jmlContract : jmlContracts) {
            if (annotatedJmlComments.stream().noneMatch((c2) -> validContractAlteration(jmlContract, c2))) {
                changedContracts.add(jmlContract);
            }
        }

        return changedContracts.stream().map(JmlContract::toString).collect(Collectors.toList());
    }

    public static boolean checkAssumes(String program, String annotatedProgram) throws ParsingException {
        CompilationUnit programParse = parseProgram(program, true);
        programParse.getAllComments().forEach(Comment::remove);

        CompilationUnit annotatedParse = parseProgram(annotatedProgram, true);
        annotatedParse.getAllComments().forEach(Comment::remove);

        //TODO: check that only assumes from original are in annotated prog
        List<JmlExpressionStmt> originalAssumptions = programParse.findAll(JmlExpressionStmt.class).stream().filter(expr -> expr.getKind() == JmlExpressionStmt.JmlStmtKind.ASSUME).toList();

        return annotatedParse.findAll(JmlExpressionStmt.class).stream().filter(expr -> expr.getKind() == JmlExpressionStmt.JmlStmtKind.ASSUME).allMatch(originalAssumptions::contains);
    }

    public static Optional<Pair<String, String>> getFirstChangedNodeAndLocation(String program, String annotatedProgram) throws ParsingException {
        CompilationUnit programParse = parseProgram(program, false);
        programParse.getAllComments().forEach(Comment::remove);

        CompilationUnit annotatedParse = parseProgram(annotatedProgram, false);
        annotatedParse.getAllComments().forEach(Comment::remove);

        Node firstChangedNode = NoJMLEqualsVisitor.equals(programParse, annotatedParse);
        if (firstChangedNode == null) {
            return Optional.empty();
        }
        return Optional.of(new Pair<>(firstChangedNode.toString(), firstChangedNode.getRange().map(range -> range.begin.toString()).orElse("(line ?, col ?)")));
    }

    public static List<String> getLoopsWithoutInvariants(String program) throws ParsingException {
        List<String> loops = new ArrayList<>();
        CompilationUnit programParse = parseProgram(program, true);

        AbstractLoopVisitor loopVisitor = new AbstractLoopVisitor() {
            @Override
            protected <T extends Node & NodeWithContracts<T>> T visitLoop(T n, T result) {
                if (n.getContracts().isEmpty()) {
                    loops.add(n.toString());
                }
                return result;
            }
        };

        loopVisitor.visit(programParse, null);
        return loops;
    }

    public static List<String> getLoopsWithoutDecreases(String program) throws ParsingException {
        List<String> loops = new ArrayList<>();
        CompilationUnit programParse = parseProgram(program, true);

        AbstractLoopVisitor loopVisitor = new AbstractLoopVisitor() {
            @Override
            protected <T extends Node & NodeWithContracts<T>> T visitLoop(T n, T result) {
                for (JmlContract contract : n.getContracts()) {
                    if (contract.getClauses().stream().noneMatch(clause -> clause.getKind().equals(JmlClauseKind.DECREASES)))
                        loops.add(n.toString());
                }
                return result;
            }
        };

        loopVisitor.visit(programParse, null);
        return loops;
    }

    public static CompilationUnit parseWithResolver(String program) throws ParsingException {
        ParserConfiguration configuration = new ParserConfiguration();
        configuration.setProcessJml(true);
        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        typeSolver.add(new ReflectionTypeSolver());
        JavaSymbolSolver solver = new JavaSymbolSolver(typeSolver);
        configuration.setSymbolResolver(solver);
        JavaParser p = new JavaParser(configuration);
        var result = p.parse(program);
        if (result.isSuccessful()) {
            return result.getResult().get();
        } else {
            List<Map<String, String>> problems = new ArrayList<>();
            Base64.Encoder encoder = Base64.getEncoder();
            for (Problem problem : result.getProblems()) {
                Map<String, String> entry = new HashMap<>();
                entry.put("message", encoder.encodeToString(problem.getMessage().getBytes(StandardCharsets.UTF_8)));
                String location = problem.getLocation().map(l -> l.getBegin().getRange().map((r) -> r.begin.toString()).orElse("(line ?,col ?)")).orElse("(line ?,col ?)");
                entry.put("location", encoder.encodeToString(location.getBytes(StandardCharsets.UTF_8)));
                entry.put("cause", encoder.encodeToString(problem.getCause().map(Throwable::getMessage).orElse("").getBytes(StandardCharsets.UTF_8)));
                problems.add(entry);
            }
            throw new ParsingException(new Gson().toJson(problems));
        }
    }

    public static List<String> findRecursiveFunctionsWithoutMeasuredBy(String program) throws ParsingException {
        CompilationUnit programParse = parseWithResolver(program);
        Collection<MethodDeclaration> methods = programParse.findAll(MethodDeclaration.class);

        methods.removeIf(method -> {
            try {
                return !isRecursiveFunction(programParse, method);
            } catch (ParsingException e) {
                throw new RuntimeException(e);
            }
        });

        methods.removeIf(method -> {
            if (method.getContracts().isEmpty()) {
                return false;
            }
            return method.getContracts().stream()
                    .anyMatch(contract -> contract.getClauses().stream()
                            .noneMatch(clause -> clause.getKind().equals(JmlClauseKind.MEASURED_BY)));
        });

        return methods.stream().map((md) -> md.resolve().getQualifiedSignature()).collect(Collectors.toList());
    }

    //Checks syntactically if the function which matches the signature of the methodHeader has a recursion inside the program
    public static boolean isRecursiveFunction(CompilationUnit program, MethodDeclaration methodDeclaration) throws ParsingException {
        String targetSignature = methodDeclaration
                .resolve().getQualifiedSignature();

        Set<String> checkedSigs = new HashSet<>();
        Queue<MethodDeclaration> methodsToCheck = new LinkedList<>();
        methodsToCheck.add(methodDeclaration);

        while (!methodsToCheck.isEmpty()) {
            MethodDeclaration method = methodsToCheck.poll();
            String methodSignature = method.resolve().getQualifiedSignature();

            if (!checkedSigs.add(methodSignature)) {
                continue;
            }

            if (method.getBody().isEmpty()) {
                continue;
            }

            for (MethodCallExpr methodCall : method.getBody().get().findAll(MethodCallExpr.class)) {
                try {
                    ResolvedMethodDeclaration resolved = methodCall.resolve();
                    String callSignature = resolved.getQualifiedSignature();

                    if (callSignature.equals(targetSignature)) {
                        return true;
                    }

                    resolved.toAst().ifPresent(node -> {
                        if (node instanceof MethodDeclaration md) {
                            methodsToCheck.add(md);
                        }
                    });
                } catch (Exception ignored) {

                }
            }
        }
        return false;
    }

    private static Optional<MethodDeclaration> findMethodMatchingHeader(String program, String methodHeader) throws ParsingException {
        CallableDeclaration.Signature signature = parseMethodDeclaration(methodHeader).getSignature();


        return findMethodMatchingSignature(program, signature);
    }

    private static Optional<MethodDeclaration> findMethodMatchingSignature(String program, CallableDeclaration.Signature signature) throws ParsingException {
        CompilationUnit programParse = parseProgram(program, true);
        programParse.getAllComments().forEach(Comment::remove);

        return programParse.findFirst(MethodDeclaration.class, (md) -> md.getSignature().equals(signature));
    }
}
