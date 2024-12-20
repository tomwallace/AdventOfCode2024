package com.tomwallace.adventofcode2024.java.problems.solutions.day4;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;

import java.util.List;

public class Day4 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Ceres Search";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 4; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day4Input.txt";
        var count = countWordOccurrences(filePath);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day4Input.txt";
        var count = countMasCrosses(filePath);
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

    protected Integer countWordOccurrences(String filePath) {
        var targetWord = "XMAS";
        List<List<Character>> grid = FileUtility.parseFileToListListCharacter(filePath);
        var wordCount = 0;
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                wordCount += countWordFoundFromPoint(grid, targetWord, new Point(x, y));
            }
        }
        return wordCount;
    }

    protected Integer countMasCrosses(String filePath) {
        List<List<Character>> grid = FileUtility.parseFileToListListCharacter(filePath);
        var wordCount = 0;
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                wordCount += isValidMasCross(grid, new Point(x, y)) ? 1 : 0;
            }
        }
        return wordCount;
    }

    protected Integer countWordFoundFromPoint(List<List<Character>> grid, String targetWord, Point current) {
        var mods = List.of(new Point(-1,-1), new Point(0,-1), new Point(1,-1), new Point(1,0), new Point(1,1), new Point(0, 1), new Point(-1, 1), new Point(-1, 0));
        // We only start checking with the first letter of the targetWord
        if (grid.get(current.y()).get(current.x()) != targetWord.charAt(0))
            return 0;

        var count = 0;
        // Check each direction
        for (var mod : mods) {
            var found = wordFoundInDirection(grid, targetWord, current, mod) ? 1 : 0;
            count += found;

        }

        return count;
    }

    private Boolean wordFoundInDirection(List<List<Character>> grid, String targetWord, Point current, Point direction) {
        for (int i = 1; i < targetWord.length(); i++) {
            var currX = current.x() + (direction.x() * i);
            var currY = current.y() + (direction.y() * i);
            if (!GridUtility.isPointInBounds(new Point(currX, currY), grid))
                return false;

            if (grid.get(currY).get(currX) != targetWord.charAt(i))
                return false;
        }
        return true;
    }

    private Boolean isValidMasCross(List<List<Character>> grid, Point current) {
        // Looking for a X pattern of two "MAS" where the "A" is in the center and would be current
        if (grid.get(current.y()).get(current.x()) != 'A')
            return false;

        // check top left
        if (!GridUtility.isPointInBounds(new Point(current.x() - 1, current.y() - 1), grid))
            return false;
        var topLeft = grid.get(current.y() - 1).get(current.x() - 1);
        if (topLeft != 'M' && topLeft != 'S')
            return false;
        // bottom right must be other
        if (!GridUtility.isPointInBounds(new Point(current.x() + 1, current.y() + 1), grid))
            return false;
        var bottomRight = grid.get(current.y() + 1).get(current.x() + 1);
        if ((topLeft == 'M' && bottomRight != 'S') || (topLeft == 'S' && bottomRight != 'M'))
            return false;

        // Continue on to other part of X
        // check top right
        if (!GridUtility.isPointInBounds(new Point(current.x() + 1, current.y() - 1), grid))
            return false;
        var topRight = grid.get(current.y() - 1).get(current.x() + 1);
        if (topRight != 'M' && topRight != 'S')
            return false;
        // bottom left must be other
        if (!GridUtility.isPointInBounds(new Point(current.x() - 1, current.y() + 1), grid))
            return false;
        var bottomLeft = grid.get(current.y() + 1).get(current.x() - 1);
        //noinspection RedundantIfStatement
        if ((topRight == 'M' && bottomLeft != 'S') || (topRight == 'S' && bottomLeft != 'M'))
            return false;

        // If we have not failed yet, then we are valid
        return true;
    }
}

