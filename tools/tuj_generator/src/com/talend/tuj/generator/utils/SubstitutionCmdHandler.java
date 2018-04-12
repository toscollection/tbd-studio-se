package com.talend.tuj.generator.utils;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SubstitutionCmdHandler {
    /*
    arg : xxx=>yyy,aa=>bb
     */
    public static List<Pair<String, String>> processArgument(String arg) {
        List<Pair<String, String>> substitutions = new ArrayList<>();

        for (String substitution : arg.split(",")) {
            String[] parts = substitution.split("=>");
            substitutions.add(new Pair<>(parts[0], parts[1]));
        }

        return substitutions;
    }
}
