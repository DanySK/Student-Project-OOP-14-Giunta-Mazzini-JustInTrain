package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.LinkedList;
import java.util.List;

public class PlainSolutionWrapper extends LinkedList<PlainSolution> {

    private final List<PlainSolution> plainSolutions;

    public PlainSolutionWrapper(List<PlainSolution> plainSolutionList) {
        this.plainSolutions = plainSolutionList;
    }

    public List<PlainSolution> getList() {
        return this.plainSolutions;
    }
}
