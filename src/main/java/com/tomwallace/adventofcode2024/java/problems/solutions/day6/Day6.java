package com.tomwallace.adventofcode2024.java.problems.solutions.day6;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

public class Day6 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Guard Gallivant";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 6; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day6Input.txt";
        var map = new Map(filePath, false);
        return map.findNumberOfStepsUntilGuardLeaves().toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day6Input.txt";
        var count = countObstructionPositions(filePath);
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

    protected Integer countObstructionPositions(String filePath) {
        var originalMap = new Map(filePath, true);
        var grid = originalMap.getGrid();
        var successfulObstructions = 0;
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                var localMap = new Map(filePath, true);
                var localGrid = localMap.getGrid();
                if (localGrid.get(y).get(x).equals('#') || (localMap.getGuardLocation().x().equals(x) && localMap.getGuardLocation().y().equals(y))) {
                    continue;
                }
                localGrid.get(y).set(x, '#');
                var result = localMap.findNumberOfStepsUntilGuardLeaves();
                successfulObstructions += result == -1 ? 1 : 0;
            }
        }

        return successfulObstructions;
    }
}

