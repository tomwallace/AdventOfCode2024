package com.tomwallace.adventofcode2024.java.problems.solutions.day7;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

public class Day7 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Bridge Repair";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 7; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day7Input.txt";
        var sum = sumPossibleEquations(filePath, false);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day7Input.txt";
        var sum = sumPossibleEquations(filePath, true);
        return sum.toString();
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

    protected Long sumPossibleEquations(String filePath, Boolean useConcatenation) {
        var equations = FileUtility.parseFileToList(filePath, line -> new Equation(line, useConcatenation));
        return equations.stream()
                .filter(Equation::isPossible)
                .mapToLong(Equation::getTestValue)
                .sum();
    }
}

