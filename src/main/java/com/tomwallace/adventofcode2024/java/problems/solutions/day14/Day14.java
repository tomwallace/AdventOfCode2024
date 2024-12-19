package com.tomwallace.adventofcode2024.java.problems.solutions.day14;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import lombok.SneakyThrows;

public class Day14 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Restroom Redoubt";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 14; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day14Input.txt";
        var total = moveRobotsAndGetSafetyFactor(filePath, 100, 102, 100);
        return total.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day14Input.txt";
        var total = moveRobotsUntilChristmasTree(filePath, 100, 102, Integer.MAX_VALUE);
        return total.toString();
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

    protected Long moveRobotsAndGetSafetyFactor(String filePath, Integer maxX, Integer maxY, Integer steps) {
        var map = new Map(filePath, maxX, maxY);
        for (int i = 0; i < steps; i++) {
            map.step();
        }
        return map.calculateSafetyScore();
    }

    @SneakyThrows
    protected Integer moveRobotsUntilChristmasTree(String filePath, Integer maxX, Integer maxY, Integer steps) {
        var map = new Map(filePath, maxX, maxY);
        for (int i = 0; i < steps; i++) {
            map.step();
            if (map.doesMapContainRowWithConsecutiveRobots()) { // && i % 5 == 0) {
                map.print();
                return map.getTime();
            }
        }
        throw new Exception("Should never get here");
    }
}



