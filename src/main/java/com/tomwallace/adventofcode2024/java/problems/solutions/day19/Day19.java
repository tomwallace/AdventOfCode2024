package com.tomwallace.adventofcode2024.java.problems.solutions.day19;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day19 implements IAdventProblemSet {

    public HashMap<String, Long> cache;
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
        return true;
    }

    protected Long countPossibleDesigns(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var patterns = Arrays.stream(lines.get(0).split(", ")).toList();
        var designs = lines.stream().skip(2).toList();
        cache = new HashMap<>();
        return designs.stream()
                .filter(d -> countDesignPossibleRecurse(d, patterns) > 0)
                .count();
    }

    protected Long countAllPossibleDesigns(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var patterns = Arrays.stream(lines.get(0).split(", ")).toList();
        var designs = lines.stream().skip(2).toList();
        cache = new HashMap<>();
        return designs.stream()
                .mapToLong(d -> countDesignPossibleRecurse(d, patterns))
                .sum();
    }

    protected Long countDesignPossibleRecurse(String design, final List<String> patterns) {
        if (cache.containsKey(design)) {
            return cache.get(design);
        }
        if (design.isEmpty())   {
            return 1L;
        }
        var total = 0L;
        for (var pattern : patterns) {
            // May need to change to contains
            if (design.startsWith(pattern)) {
                total += countDesignPossibleRecurse(design.substring(pattern.length()), patterns);
            }
        }
        cache.put(design, total);

        return total;
    }
}



