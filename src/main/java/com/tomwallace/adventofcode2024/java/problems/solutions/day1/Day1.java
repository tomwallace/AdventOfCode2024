package com.tomwallace.adventofcode2024.java.problems.solutions.day1;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.ArrayList;
import java.util.Collections;

public class Day1 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Historian Hysteria";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 1;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day1Input.txt";
        var sum = sumListDifference(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day1Input.txt";
        var sum = calculateSimilarityScore(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.EASY;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }

    protected Long sumListDifference(String filePath) {
        var inputs = getInputs(filePath);

        var distances = new ArrayList<Long>();
        for (var i = 0; i < inputs.listOne.size(); i++) {
            distances.add(Math.abs(inputs.listOne.get(i) - inputs.listTwo.get(i)));
        }

        return distances.stream().mapToLong(Long::longValue).sum();
    }

    protected Long calculateSimilarityScore(String filePath) {
        var inputs = getInputs(filePath);

        var similarityScores = new ArrayList<Long>();
        for (var entry : inputs.listOne) {
            var timesFound = inputs.listTwo.stream()
                    .filter(e -> e.equals(entry))
                    .count();
            similarityScores.add(timesFound * entry);
        }

        return similarityScores.stream().mapToLong(Long::longValue).sum();
    }

    private Result getInputs(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var listOne = new ArrayList<Long>();
        var listTwo = new ArrayList<Long>();
        for (var line : lines) {
            //noinspection RegExpRepeatedSpace
            var split = line.split("   ");
            listOne.add(Long.parseLong(split[0]));
            listTwo.add(Long.parseLong(split[1]));
        }
        Collections.sort(listOne);
        Collections.sort(listTwo);

        return new Result(listOne, listTwo);
    }

    private record Result(ArrayList<Long> listOne, ArrayList<Long> listTwo) {
    }
}
