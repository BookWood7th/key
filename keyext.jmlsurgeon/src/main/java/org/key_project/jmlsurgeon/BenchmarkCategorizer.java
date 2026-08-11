package org.key_project.jmlsurgeon;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.jml.body.JmlClassExprDeclaration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class BenchmarkCategorizer {
    public static void main(String[] args) {
        Path directory = Path.of(System.getProperty("user.home")).resolve("muss42ExperimentsRemote");
        Map<Categorizer, List<Path>> categorizedBenchmarks = null;
        try (Stream<Path> fileList = Files.walk(directory).filter(Files::isRegularFile).filter(path -> path.getFileName().toString().equals("annotated.java"))) {
            categorizedBenchmarks = categorizeBenchmarks(fileList.toList(), List.of(new ContainsInvariantCategorizer(), new ContainsStaticInvariantCategorizer(), new ContainsConstructorCategorizer(), new ContainsInvariantClassWithConstructorCategorizer()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        Map<String, List<String>> jsonData = new HashMap<>();
        for (Map.Entry<Categorizer, List<Path>> entry : categorizedBenchmarks.entrySet()) {
            String categoryName = entry.getKey().getClass().getSimpleName();

            List<String> paths = entry.getValue().stream().map(directory::relativize).map(Path::toString).toList();
            jsonData.put(categoryName, paths);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonData);
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(System.getProperty("user.home")).resolve("benchmark_categories.json"))) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static Map<Categorizer, List<Path>> categorizeBenchmarks(Collection<Path> benchmarks, List<Categorizer> categorizers) {
        Map<Categorizer, List<Path>> results = new HashMap<>();
        for (Categorizer c : categorizers) {
            results.put(c, new ArrayList<>());
        }
        for (Path benchmarkPath : benchmarks) {
            System.out.println("Checking " + benchmarkPath);
            CompilationUnit programParse = null;
            try {
                String benchmark = Files.readString(benchmarkPath);
                programParse = Surgeon.parseProgram(benchmark, true);
            } catch (IOException e) {
                System.err.println("Failed to read benchmark: " + benchmarkPath);
            } catch (ParsingException e) {
                System.err.println("Failed to parse benchmark: " + benchmarkPath);
            }
            if (programParse == null) {
                throw new RuntimeException("Unreachable");
            }

            for (Categorizer c : categorizers) {
                if (c.checkProgram(programParse)) {
                    results.get(c).add(benchmarkPath);
                }
            }
        }
        return results;
    }

    interface Categorizer {
        boolean checkProgram(Node program);
    }
    static class ContainsInvariantCategorizer implements Categorizer {
        @Override
        public boolean checkProgram(@MonotonicNonNull Node program) {
            return program.findAll(JmlClassExprDeclaration.class).stream().anyMatch(inv ->
                    inv.getModifiers().stream().noneMatch(m ->
                            m.getKeyword() == Modifier.DefaultKeyword.STATIC));
        }
    }

    static class ContainsStaticInvariantCategorizer implements Categorizer {
        @Override
        public boolean checkProgram(@MonotonicNonNull Node program) {
            return program.findAll(JmlClassExprDeclaration.class).stream().anyMatch(inv ->
                    inv.getModifiers().stream().anyMatch(m ->
                            m.getKeyword() == Modifier.DefaultKeyword.STATIC));
        }
    }

    static class ContainsConstructorCategorizer implements Categorizer {
        @Override
        public boolean checkProgram(@MonotonicNonNull Node program) {
            return !program.findAll(ConstructorDeclaration.class).isEmpty();
        }
    }

    static class ContainsInvariantClassWithConstructorCategorizer implements Categorizer {
        @Override
        public boolean checkProgram(@MonotonicNonNull Node program) {
            ContainsInvariantCategorizer inv = new ContainsInvariantCategorizer();
            ContainsStaticInvariantCategorizer invStatic = new ContainsStaticInvariantCategorizer();
            ContainsConstructorCategorizer constructor = new ContainsConstructorCategorizer();

            for (ClassOrInterfaceDeclaration clazz : program.findAll(ClassOrInterfaceDeclaration.class)) {
                if (constructor.checkProgram(clazz) && (inv.checkProgram(clazz) || invStatic.checkProgram(clazz))) {
                    return true;
                }
            }
            return false;
        }
    }

}
