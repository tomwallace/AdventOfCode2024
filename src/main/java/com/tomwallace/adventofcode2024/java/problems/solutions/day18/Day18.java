package com.tomwallace.adventofcode2024.java.problems.solutions.day18;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

public class Day18 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "RAM Run";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 18; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day18Input.txt";
        var maze = new Maze(filePath, 71, 71, 1024);
        return maze.findFewestSteps().toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day18Input.txt";
        var maze = new Maze(filePath, 71, 71, 1024);
        return maze.findFirstBlockingByte();
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
}



