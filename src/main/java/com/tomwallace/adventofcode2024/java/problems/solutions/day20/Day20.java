package com.tomwallace.adventofcode2024.java.problems.solutions.day20;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

public class Day20 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Race Condition";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 20; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day20Input.txt";
        var race = new Race(filePath);
        return race.countSuccessfulCheatsByPath(100, 2).toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day20Input.txt";
        var race = new Race(filePath);
        return race.countSuccessfulCheatsByPath(100, 20).toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.HARD;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }
}



