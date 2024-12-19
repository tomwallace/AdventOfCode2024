package com.tomwallace.adventofcode2024.java.problems.solutions.day18;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;

import java.util.*;

public class Maze {
    private final Integer maxX;
    private final Integer maxY;
    private final Point start;
    private final Point end;
    private final List<Point> corrupted;
    private final Integer numBytes;

    public Maze(String filePath, Integer maxX, Integer maxY, Integer numBytes) {
        corrupted = FileUtility.parseFileToList(filePath, this::processLine);
        this.maxX = maxX;
        this.maxY = maxY;
        this.numBytes = numBytes;
        start = new Point(0, 0);
        end = new Point(maxX - 1, maxY - 1);
    }

    public Integer findFewestSteps() {
        return findFewestStepsForBytes(numBytes);
    }

    public String findFirstBlockingByte() {
        for (var i = 1; i <= corrupted.size(); i++) {
            var result = findFewestStepsForBytes(i);
            if (result == -1) {
                var block = corrupted.get(i - 1);
                return String.format("%d,%d", block.x(), block.y());
            }
        }
        return "Could not find it";
    }

    public Integer findFewestStepsForBytes(Integer bytesAllowed) {
        var blocks = corrupted.stream()
                .limit(bytesAllowed)
                .toList();
        var visited = new HashSet<Point>();
        var queue = new PriorityQueue<State>();
        queue.add(new State(start, 0));
        visited.add(start);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.location.equals(end)) {
                return current.steps;
            }
            var options = GridUtility.findOrthogonalNeighbours(current.location, maxX, maxY).stream()
                    .filter(p -> !blocks.contains(p) && !visited.contains(p))
                    .toList();
            for (var option : options) {
                visited.add(option);
                var newSteps = current.steps + 1;
                queue.add(new State(option, newSteps));
            }
        }

        return -1;
    }

    private Point processLine(String line) {
        var split = line.split(",");
        return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private record State(Point location, Integer steps) implements Comparable<State> {
        @Override
        public int compareTo(State o) {
            return steps - o.steps;
        }
    }
}
