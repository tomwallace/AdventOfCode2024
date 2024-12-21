package com.tomwallace.adventofcode2024.java.problems.solutions.day20;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;

import java.util.*;

public class Race {
    private final List<List<Character>> grid;
    private Point start;
    private Point end;

    public Race(String filePath) {
        grid = FileUtility.parseFileToListListCharacter(filePath);
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                var curr = grid.get(y).get(x);
                if (curr == 'S') {
                    start = new Point(x, y);
                    grid.get(y).set(x, '.');
                }
                if (curr == 'E') {
                    end = new Point(x, y);
                    grid.get(y).set(x, '.');
                }
            }
        }
    }

    protected Integer countSuccessfulCheatsByPath(Integer minSavings, Integer cheatDistance) {
        var successfulCheats = 0;
        var noCheatPath = findBestSpeedNoCheatingPath();
        var noCheatPathSteps = noCheatPath.size();
        for (var s = 0; s < noCheatPathSteps; s++) {
            for (var e = s + minSavings; e < noCheatPathSteps; e++) {
                var cheatStart = noCheatPath.get(s);
                var cheatEnd = noCheatPath.get(e);
                var dist = findManhattanDistance(cheatStart, cheatEnd);
                if (dist <= cheatDistance) {
                    // Make sure to also shave off the distance saved
                    var savings = e - s - dist;
                    if (savings >= minSavings)
                        successfulCheats++;
                }
            }
        }
        return successfulCheats;
    }

    private List<Point> findBestSpeedNoCheatingPath() {
        var visited = new HashSet<Point>();
        var queue = new PriorityQueue<StatePath>();
        queue.add(new StatePath(start, 0, new ArrayList<>()));
        visited.add(start);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.location.equals(end)) {
                current.visited.add(end);
                return current.visited;
            }
            var options = GridUtility.findOrthogonalNeighbours(current.location, grid).stream()
                    .filter(p -> grid.get(p.y()).get(p.x()) != '#' && !visited.contains(p))
                    .toList();
            for (var option : options) {
                visited.add(option);
                var newSteps = current.steps + 1;
                var newVisited = new ArrayList<>(current.visited);
                newVisited.add(current.location);
                queue.add(new StatePath(option, newSteps, newVisited));
            }
        }

        return new ArrayList<>();
    }

    private Integer findManhattanDistance(Point start, Point end) {
        var diffX = Math.abs(start.x() - end.x());
        var diffY = Math.abs(start.y() - end.y());
        return diffX + diffY;
    }
    private record StatePath(Point location, Integer steps, List<Point> visited) implements Comparable<StatePath> {
        @Override
        public int compareTo(StatePath o) {
            return steps - o.steps;
        }
    }
}
