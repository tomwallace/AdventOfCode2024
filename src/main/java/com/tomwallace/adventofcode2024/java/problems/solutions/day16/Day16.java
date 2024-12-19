package com.tomwallace.adventofcode2024.java.problems.solutions.day16;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;
import lombok.SneakyThrows;

import java.util.*;

public class Day16 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Reindeer Maze";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 16; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day16Input.txt";
        var result = getLowestMazePoints(filePath);
        return result.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day16Input.txt";
        var result = countBestSeats(filePath);
        return result.toString();
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

    protected Integer getLowestMazePoints(String filePath) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var start = new Point(1, grid.size() - 2);
        var end = new Point(grid.get(1).size() - 2,1);
        var queue = new PriorityQueue<State>();
        var visited = new HashMap<PointFace, Integer>();
        queue.add(new State(new PointFace(start, 2), 0));
        visited.put(new PointFace(start, 0), 0);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.pointFace.location.equals(end)) {
                return current.score;
            }
            if (visited.getOrDefault(current.pointFace, Integer.MAX_VALUE) < current.score) {
                continue;
            }
            visited.put(current.pointFace, current.score);
            // Add other options
            var options = getAvailablePoints(current.pointFace.location, current.pointFace().facing, grid).stream()
                    .filter(p -> grid.get(p.y()).get(p.x()) != '#')
                    .toList();
            for (var option : options) {
                var newFacing = calculateNewFacing(current.pointFace, option);
                var newScore = current.score + 1 + (1000 * (newFacing.equals(current.pointFace.facing()) ? 0 : 1));
                queue.add(new State(new PointFace(option, newFacing), newScore));
            }
        }

        return -1;
    }

    protected Integer countBestSeats(String filePath) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var start = new Point(1, grid.size() - 2);
        var end = new Point(grid.get(1).size() - 2,1);
        var queue = new PriorityQueue<StatePart2>();
        var seen = new HashMap<PointFace, Integer>();
        var bestSeats = new HashSet<Point>();
        queue.add(new StatePart2(new PointFace(start, 2), 0, new HashSet<>()));
        seen.put(new PointFace(start, 0), 0);
        bestSeats.add(start);
        var bestScore = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.score > bestScore) {
                break;
            }
            if (current.pointFace.location.equals(end)) {
                bestScore = current.score;
                bestSeats.addAll(current.visited);
            }
            if (seen.getOrDefault(current.pointFace, Integer.MAX_VALUE) < current.score) {
                continue;
            }
            seen.put(current.pointFace, current.score);
            // Add other options
            var options = getAvailablePoints(current.pointFace.location, current.pointFace().facing, grid).stream()
                    .filter(p -> grid.get(p.y()).get(p.x()) != '#')
                    .toList();
            for (var option : options) {
                var localVisited = new HashSet<>(current.visited);
                localVisited.add(option);
                var newFacing = calculateNewFacing(current.pointFace, option);
                var newScore = current.score + 1 + (1000 * (newFacing.equals(current.pointFace.facing()) ? 0 : 1));
                queue.add(new StatePart2(new PointFace(option, newFacing), newScore, localVisited));
            }
        }

        return bestSeats.size();
    }

    @SneakyThrows
    private List<Point> getAvailablePoints(Point current, Integer facing, List<List<Character>> grid) {
        var points = new ArrayList<Point>();
        var north = new Point(current.x(), current.y() - 1);
        var east = new Point(current.x() + 1, current.y());
        var south = new Point(current.x(), current.y() + 1);
        var west = new Point(current.x() - 1, current.y());
        switch (facing) {
            case 1:
                if (GridUtility.isPointInBounds(north, grid))
                    points.add(north);
                if (GridUtility.isPointInBounds(west, grid))
                    points.add(west);
                if (GridUtility.isPointInBounds(east, grid))
                    points.add(east);
                return points;
            case 2:
                if (GridUtility.isPointInBounds(east, grid))
                    points.add(east);
                if (GridUtility.isPointInBounds(north, grid))
                    points.add(north);
                if (GridUtility.isPointInBounds(south, grid))
                    points.add(south);
                return points;
            case 3:
                if (GridUtility.isPointInBounds(south, grid))
                    points.add(south);
                if (GridUtility.isPointInBounds(east, grid))
                    points.add(east);
                if (GridUtility.isPointInBounds(west, grid))
                    points.add(west);
                return points;
            case 4:
                if (GridUtility.isPointInBounds(west, grid))
                    points.add(west);
                if (GridUtility.isPointInBounds(south, grid))
                    points.add(south);
                if (GridUtility.isPointInBounds(north, grid))
                    points.add(north);
                return points;
            default:
                throw new Exception(String.format("facing %d is not possible", facing));
        }
    }

    @SneakyThrows
    private Integer calculateNewFacing(PointFace current, Point future) {
        var diffX = future.x() - current.location.x();
        var diffY = future.y() - current.location.y();
        if (diffX == 0 && diffY < 0)
            return 1;
        if (diffX > 0 && diffY == 0)
            return 2;
        if (diffX == 0 && diffY > 0)
            return 3;
        if (diffX < 0 && diffY == 0)
            return 4;

        throw new Exception(String.format("current location to future location with heading %d is not possible", current.facing));
    }

    // Facing: 1: N, 2: E, 3: S, 4: W
    private record PointFace(Point location, Integer facing) {}
    private record State(PointFace pointFace, Integer score) implements Comparable<State> {
        @Override
        public int compareTo(State o) {
            return score - o.score;
        }
    }
    private record StatePart2(PointFace pointFace, Integer score, HashSet<Point> visited) implements Comparable<StatePart2> {
        @Override
        public int compareTo(StatePart2 o) {
            return score - o.score;
        }
    }
}



