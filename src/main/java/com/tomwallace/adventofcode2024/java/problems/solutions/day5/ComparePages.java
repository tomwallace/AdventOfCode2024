package com.tomwallace.adventofcode2024.java.problems.solutions.day5;

import java.util.Comparator;
import java.util.List;

public class ComparePages implements Comparator<Integer> {
    private final List<Rule> rules;

    public ComparePages(List<Rule> rules) {
        this.rules = rules;
    }

    public int compare(Integer page1, Integer page2) {
        if (rules.stream().anyMatch(r -> r.left().equals(page1) && r.right().equals(page2)))
            return -1;
        if (rules.stream().anyMatch(r -> r.left().equals(page2) && r.right().equals(page1)))
            return 1;
        return 0;
    }
}
