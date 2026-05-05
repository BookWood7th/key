package org.key_project.jmlsurgeon;

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
                    }
                }
                """;

        String prog2 = """
                public class Main {
                    /*@ normal_behaviour
                    @ requires true;
                    @ ensures true;
                    @*/
                    public static void main(String[] args) {
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
