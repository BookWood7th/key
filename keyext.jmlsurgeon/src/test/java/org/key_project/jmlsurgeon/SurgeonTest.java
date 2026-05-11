package org.key_project.jmlsurgeon;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.jml.NodeWithContracts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SurgeonTest {

    @Test
    void testCheckAnnotations() {
        String prog = """
                public class Main {
                    /*@ normal_behaviour
                    @ ensures true;
                    @*/
                    public static void main(String[] args) {
                        while(true) { }
                    }
                }
                """;

        String prog2 = """
                public class Main {
                    /*@ normal_behaviour
                    @ ensures true;
                    @*/
                    public static void main(String[] args) {
                        while(true) { }
                    }
                }
                """;

        try {
            AbstractLoopVisitor visitor = new AbstractLoopVisitor() {

                @Override
                protected <T extends Node & NodeWithContracts<T>> T visitLoop(T n, T result) {
                    System.out.println(n.toString());
                    return result;
                }
            };
            visitor.visit(Surgeon.parseFile(prog), null);
            assertTrue(Surgeon.getIllegallyChangedContracts(prog, prog2).isEmpty());
            assertTrue(Surgeon.getFirstChangedNodeAndLocation(prog, prog2).isEmpty());
        } catch (ParsingException e) {
            fail();
        }
    }
}
