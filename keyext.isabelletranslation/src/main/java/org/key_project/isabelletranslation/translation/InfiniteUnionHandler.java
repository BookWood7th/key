/* This file is part of KeY - https://key-project.org
 * KeY is licensed under the GNU General Public License Version 2
 * SPDX-License-Identifier: GPL-2.0-only */
package org.key_project.isabelletranslation.translation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.uka.ilkd.key.java.Services;

import org.key_project.logic.Term;
import org.key_project.logic.op.Operator;

/**
 * This class handles the infiniteUnion function.
 *
 * @author Nils Buchholz
 */
public class InfiniteUnionHandler implements IsabelleHandler {
    /**
     * Map of the operators supported by this handler and their respective translation.
     */
    private final Map<Operator, String> supportedOperators = new HashMap<>();

    @Override
    public void init(IsabelleMasterHandler masterHandler, Services services,
            Properties handlerSnippets, String[] handlerOptions) throws IOException {
        supportedOperators.put(services.getTypeConverter().getLocSetLDT().getInfiniteUnion(),
            "infiniteUnion");
    }

    @Override
    public boolean canHandle(Operator op) {
        return supportedOperators.containsKey(op);
    }

    @Override
    public StringBuilder handle(IsabelleMasterHandler trans, Term term) {
        Operator op = term.op();
        String arg1 = "{"
            + trans.translate(term.sub(0)) + "| " + LogicalVariableHandler.makeVarRef(trans,
                term.boundVars().get(0).name().toString(), term.boundVars().get(0).sort())
            + ". True }";

        return new StringBuilder("(").append(supportedOperators.get(op)).append(arg1).append(")");
    }
}