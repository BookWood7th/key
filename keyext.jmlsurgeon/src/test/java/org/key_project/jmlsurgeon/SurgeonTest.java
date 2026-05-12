package org.key_project.jmlsurgeon;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
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
            assertTrue(Surgeon.getIllegallyChangedContracts(prog, prog2).isEmpty());
            assertTrue(Surgeon.getFirstChangedNodeAndLocation(prog, prog2).isEmpty());
        } catch (ParsingException e) {
            fail();
        }
    }
}
