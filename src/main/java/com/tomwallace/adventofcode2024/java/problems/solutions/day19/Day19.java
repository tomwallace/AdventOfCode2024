package com.tomwallace.adventofcode2024.java.problems.solutions.day19;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.problems.solutions.day18.Maze;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.Arrays;
import java.util.List;

public class Day19 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Linen Layout";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 19; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day19Input.txt";
        var result = countPossibleDesigns(filePath);
        return result.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day19Input.txt";
        var result = countAllPossibleDesigns(filePath);
        return result.toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.MEDIUM;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }

    protected Long countPossibleDesigns(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var patterns = Arrays.stream(lines.get(0).split(", ")).toList();
        var designs = lines.stream().skip(2).toList();
        return designs.stream()
                .filter(d -> isDesignPossibleRecurse(d, patterns))
                .count();
    }

    protected Long countAllPossibleDesigns(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var patterns = Arrays.stream(lines.get(0).split(", ")).toList();
        var designs = lines.stream().skip(2).toList();
        return designs.stream()
                .mapToLong(d -> countDesignPossibleRecurse(d, patterns))
                .sum();
    }

    protected Boolean isDesignPossibleRecurse(String design, final List<String> patterns) {
        if (design.isEmpty())   {
            return true;
        }

        for (var pattern : patterns) {
            // May need to change to contains
            if (design.startsWith(pattern)) {
                if (isDesignPossibleRecurse(design.substring(pattern.length()), patterns)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected Integer countDesignPossibleRecurse(String design, final List<String> patterns) {
        if (design.isEmpty())   {
            return 1;
        }
        var total = 0;
        for (var pattern : patterns) {
            // May need to change to contains
            if (design.startsWith(pattern)) {
                total += countDesignPossibleRecurse(design.substring(pattern.length()), patterns);
            }
        }

        return total;
    }
}



