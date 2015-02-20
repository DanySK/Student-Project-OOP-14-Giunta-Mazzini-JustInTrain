package com.example.lisamazzini.train_app.model.tragitto;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe che fa da wrapper per una lista di PlainSolution.
 *
 * @author albertogiunta
 */
public class PlainSolutionWrapper extends LinkedList<PlainSolution> {

    private final List<PlainSolution> plainSolutions;

    /**
     * Costruttore che prende in input una list<PlainSolution>.
     * @param plainSolutionList lista di plainSolution
     */
    public PlainSolutionWrapper(final List<PlainSolution> plainSolutionList) {
        this.plainSolutions = plainSolutionList;
    }

    /**
     * Metodo che ritorna la lista wrappata in forma di List<PlainSolution>.
     * @return la lista wrappata.
     */
    public final List<PlainSolution> getList() {
        return this.plainSolutions;
    }
}
