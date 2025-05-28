package de.uka.ilkd.key.smt.communication.newCommunication.commands;

import de.uka.ilkd.key.smt.newsmt2.SExpr;

public record AssertCommand(SExpr term) implements StandardCommand {
}
