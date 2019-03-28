package de.uka.ilkd.key.smt.newsmt2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import de.uka.ilkd.key.java.Services;
import de.uka.ilkd.key.logic.Term;
import de.uka.ilkd.key.logic.sort.NullSort;
import de.uka.ilkd.key.logic.sort.Sort;
import de.uka.ilkd.key.smt.IllegalFormulaException;
import de.uka.ilkd.key.smt.SMTSettings;
import de.uka.ilkd.key.smt.SMTTranslator;
import de.uka.ilkd.key.smt.newsmt2.SExpr.Type;

public class ModularSMTLib2Translator implements SMTTranslator {

    public static final String SORT_PREFIX = "sort_";

    private static final String PREAMBLE = readResource("preamble.smt2");

    private List<Throwable> exceptions = Collections.emptyList();

    private List<Throwable> tacletExceptions = Collections.emptyList();

    // REVIEW MU: Eventually switch to StringBuilder which is faster
    @Override
    public StringBuffer translateProblem(Term problem, Services services, SMTSettings settings)
            throws IllegalFormulaException {

        MasterHandler master;
        try {
            master = new MasterHandler(services);
        } catch (IOException ex) {
            exceptions = Collections.singletonList(ex);
            // Review MU: This should not be reported as exceptions only ...
            return new StringBuffer("error while translationg");
        }

        //these are always needed
        master.addFromSnippets("bool");
        master.addFromSnippets("int");
        master.addFromSnippets("instanceof");

        SExpr result = master.translate(problem, Type.BOOL);
        exceptions = master.getExceptions();

        postProcess(result);

        StringBuffer sb = new StringBuffer();

        sb.append("; --- Preamble\n\n");
        sb.append(PREAMBLE);

        sb.append("; --- Declarations\n\n");

        if (problem.arity() != 0) {
            master.addSort(Sort.ANY);
            addAllSorts(problem, master);
        }

        List<SExpr> sortExprs = new LinkedList<>();
        for (Sort s : master.getSorts()) {
            if (s != Sort.ANY && !(MasterHandler.SPECIAL_SORTS.contains(s.toString()))) {
                master.addDeclaration(new SExpr("declare-const", SExpr.sortExpr(s).toString(), "T"));
            }
            sortExprs.add(SExpr.sortExpr(s));
        }
        if (master.getSorts().size() > 1) {
            master.addDeclaration(new SExpr("assert", Type.BOOL,
                    new SExpr("distinct", Type.BOOL, sortExprs)));
        }
        sb.append("\n");

        createSortTypeHierarchy(problem, services, master, sb);

        for (Writable decl : master.getDeclarations()) {
            decl.appendTo(sb);
            sb.append("\n");
        }

        sb.append("; --- Axioms\n\n");
        for (Writable ax : master.getAxioms()) {
            ax.appendTo(sb);
            sb.append("\n\n");
        }

        sb.append("; --- Sequent\n\n");
        SExpr assertion = new SExpr("assert", Type.NONE, new SExpr("not", Type.NONE, result));
        assertion.appendTo(sb);

        sb.append("\n(check-sat)");

        return sb;
    }


    private void createSortTypeHierarchy(Term problem, Services services,
                                         MasterHandler master, StringBuffer sb) {

        for (Sort s : master.getSorts()) {
            Set<Sort> children = directChildSorts(s, master.getSorts());
            for (Sort child : children) {
                master.addAxiom(new SExpr("assert", new SExpr("subtype", SExpr.sortExpr(child), SExpr.sortExpr(s))));
                for (Sort otherChild : children) {
                    if (!(child.equals(otherChild)) && (otherChild.name().toString() != ("Null")) && (child.name().toString() != ("Null"))) {
                        SExpr st = new SExpr("subtype", SExpr.sortExpr(child), SExpr.sortExpr(otherChild));
                        master.addAxiom(new SExpr("assert", new SExpr("not", st)));
                    }
                }
            }
        }
        // if sort has no direct parents, make it a child of any
        for (Sort s : master.getSorts()) {
            if (!(s instanceof NullSort) && !(s.equals(Sort.ANY))) {
                if (s.extendsSorts().isEmpty()) {
                    master.addAxiom(new SExpr("assert", new SExpr("subtype", SExpr.sortExpr(s), SExpr.sortExpr(Sort.ANY))));
                }
            }
        }
    }

    private void addAllSorts(Term problem, MasterHandler master) {
        Sort s = problem.sort();
        master.addSort(s);
        for (Term t : problem.subs()) {
            addAllSorts(t, master);
        }
    }

    /*
     * @param parent the (possible) parent sort
     * @param child the (possible) child sort
     * @return true if parent is a direct parent sort of child
     *
     * TODO js maybe this should be in the sort class
     */
    private boolean isDirectParentOf(Sort parent, Sort child) {
        if (!(child instanceof NullSort)) {
            return child.extendsSorts().contains(parent);
        } else {
            return true;
        }
    }

    /*
     * TODO js maybe this should go in the Sort class as well
     */
    private Set<Sort> directChildSorts(Sort s, Set<Sort> sorts) {
        Set<Sort> res = new HashSet<>();
        for (Sort child : sorts) {
            if (isDirectParentOf(s, child)) {
                res.add(child);
            }
        }
        return res;
    }

    private static String readResource(String s) {
        BufferedReader r = new BufferedReader(
                new InputStreamReader(
                        ModularSMTLib2Translator.class.getResourceAsStream(s)));

        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = r.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            return ";;;; CANNOT READ " + s;
        }
    }


    private void postProcess(SExpr result) {
        // TODO: remove (u2i (i2u x)) --->  x
    }


    @Override
    public Collection<Throwable> getExceptionsOfTacletTranslation() {
        return tacletExceptions;
    }


    @Override
    public ArrayList<StringBuffer> translateTaclets(Services services, SMTSettings settings) throws IllegalFormulaException {
        // not yet implemented. maybe adapt the existing method from abstractsmttranslator
        return null;
    }

}
