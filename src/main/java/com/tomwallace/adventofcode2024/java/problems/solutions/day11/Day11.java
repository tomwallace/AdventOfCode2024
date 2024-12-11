package com.tomwallace.adventofcode2024.java.problems.solutions.day11;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.*;

public class Day11 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Plutonian Pebbles";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 11; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day11Input.txt";
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var count = countPebblesByMap(lines.get(0), 25);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day11Input.txt";
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var count = countPebblesByMap(lines.get(0), 75);
        return count.toString();
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

    protected Long countPebblesByMap(String input, Integer timesToBlink) {
        var pebbles = Arrays.stream(input.split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();
        // Use a Map to track how many pebbles a pebble spawns because brute force does not work for Part B
        var spawnMap = new HashMap<Long, Long>();
        for (Long pebble : pebbles) {
            spawnMap.put(pebble, spawnMap.getOrDefault(pebble, 0L) + 1);
        }
        for (int i = 0; i < timesToBlink; i++) {
            var localSpawnMap = new HashMap<Long, Long>();
            // Apply rules for each entry in the spawn map, as those represent our current pebbles
            for (var pebble : spawnMap.entrySet()) {
                pebbles = processPebble(pebble.getKey());
                for (Long nextPebble : pebbles) {
                    localSpawnMap.put(nextPebble, localSpawnMap.getOrDefault(nextPebble, 0L) + pebble.getValue());
                }
            }
            spawnMap = localSpawnMap;
        }
        return spawnMap.values().stream().mapToLong(Long::longValue).sum();
    }

    private List<Long> processPebble(Long pebble) {
        if (pebble == 0)
            return List.of(1L);
        else if ((countDigits(pebble) % 2) == 0) {
            var intString = String.valueOf(pebble);
            int digits = countDigits(pebble);
            var firstHalf = intString.substring(0, digits/2);
            var secondHalf = intString.substring(digits/2);
            return List.of(Long.parseLong(firstHalf), Long.parseLong(secondHalf));
        }
        return List.of(pebble * 2024);
    }

    private Integer countDigits(Long input) {
        var intString = String.valueOf(input);
        return intString.length();
    }
}



