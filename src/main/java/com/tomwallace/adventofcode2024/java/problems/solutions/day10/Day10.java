package com.tomwallace.adventofcode2024.java.problems.solutions.day10;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day10 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Hoof It";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 10; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day10Input.txt";
        var sum = sumTrailCalculations(filePath, true);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day10Input.txt";
        var sum = sumTrailCalculations(filePath, false);
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

    protected Integer sumTrailCalculations(String filePath, Boolean useTrailScore) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var trailHeads = new ArrayList<Point>();
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (grid.get(y).get(x).equals('0')) {
                    trailHeads.add(new Point(x, y));
                }
            }
        }

        return trailHeads.stream()
                .map(th -> useTrailScore ? recurseForTrailScore(new Point(th.x(), th.y()), grid) : recurseForValidTrails(new Point(th.x(), th.y()), List.of(new Point(th.x(), th.y())), grid))
                .mapToInt(HashSet::size)
                .sum();
    }

    private HashSet<Point> recurseForTrailScore(Point current, final List<List<Character>> grid) {
        if (grid.get(current.y()).get(current.x()) == '9')
            return new HashSet<>(List.of(new Point(current.x(), current.y())));
        var target = Character.getNumericValue(grid.get(current.y()).get(current.x()));
        if (target == -1)
            return new HashSet<>();
        // Targeting next biggest
        target++;
        var found = new HashSet<Point>();
        var surroundingPoints = GridUtility.findOrthogonalNeighbours(current, grid);
        for (var surroundingPoint : surroundingPoints) {
            var surroundingValue = Character.getNumericValue(grid.get(surroundingPoint.y()).get(surroundingPoint.x()));
            if (surroundingValue == target) {
                found.addAll(recurseForTrailScore(surroundingPoint, grid));
            }
        }

        return found;
    }

    private HashSet<List<Point>> recurseForValidTrails(Point current, List<Point> trail, final List<List<Character>> grid) {
        if (grid.get(current.y()).get(current.x()) == '9') {
            return new HashSet<>(List.of(trail));
        }

        var target = Character.getNumericValue(grid.get(current.y()).get(current.x()));
        if (target == -1)
            return new HashSet<>();
        // Targeting next biggest
        target++;
        var found = new HashSet<List<Point>>();
        var surroundingPoints = GridUtility.findOrthogonalNeighbours(current, grid);
        for (var surroundingPoint : surroundingPoints) {
            var surroundingValue = Character.getNumericValue(grid.get(surroundingPoint.y()).get(surroundingPoint.x()));
            if (surroundingValue == target) {
                var newTrailList = new ArrayList<>(trail);
                newTrailList.add(surroundingPoint);
                found.addAll(recurseForValidTrails(surroundingPoint, newTrailList, grid));
            }
        }

        return found;
    }
}



