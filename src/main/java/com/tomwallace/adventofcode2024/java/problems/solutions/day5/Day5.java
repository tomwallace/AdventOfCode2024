package com.tomwallace.adventofcode2024.java.problems.solutions.day5;

import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.*;

public class Day5 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Print Queue";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 5; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day5Input.txt";
        var sum = sumMiddleValueUsingSorting(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day5Input.txt";
        var sum = sumMiddleValueUsingSortingFailed(filePath);
        return sum.toString();
    }

    protected Integer sumMiddleValueUsingSorting(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var updates = lines.stream()
                .filter(l -> l.contains(","))
                .toList();
        var rules = getRules(filePath);
        return updates.stream()
                .map(this::getPages)
                .filter(pages -> isSortedCorrectly(pages, rules))
                .mapToInt(pages -> pages.get(pages.size()/2))
                .sum();
    }

    protected Integer sumMiddleValueUsingSortingFailed(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var updates = lines.stream()
                .filter(l -> l.contains(","))
                .toList();
        var rules = getRules(filePath);
        return updates.stream()
                .map(this::getPages)
                .filter(pages -> !isSortedCorrectly(pages, rules))
                .map(pages -> sortedPages(pages, rules))
                .mapToInt(pages -> pages.get(pages.size()/2))
                .sum();
    }

    protected Boolean isSortedCorrectly(List<Integer> pages, List<Rule> rules) {
        return pages.equals(sortedPages(pages, rules));
    }

    protected List<Rule> getRules(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        return lines.stream()
                .filter(l -> l.contains("|"))
                .map(l -> {
                    var parts = l.split("\\|");
                    return new Rule(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
                .toList();
    }

    private List<Integer> sortedPages(List<Integer> pages, List<Rule> rules) {
        var comparator = new ComparePages(rules);
        return pages.stream().sorted(comparator).toList();
    }

    private List<Integer> getPages(String update) {
        return new ArrayList<>(Arrays.asList(update.split(","))).stream()
                .map(Integer::parseInt)
                .toList();
    }
}