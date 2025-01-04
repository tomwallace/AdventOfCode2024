package com.tomwallace.adventofcode2024.java.problems.solutions.day21;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;

import java.util.*;

public class Day21 implements IAdventProblemSet {

    private final Map<RoadDepth, Long> roadDepthMap = new HashMap<>();

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Keypad Conundrum";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 21;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day21Input.txt";
        var sum = sumComplexitiesOfCodes(filePath, 2);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day21Input.txt";
        var sum = sumComplexitiesOfCodes(filePath, 25);
        return sum.toString();
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

    protected static final Point KEY_PAD_START = new Point(2, 3);
    protected static final Point DIR_PAD_START = new Point(2, 0);
    protected static final List<List<Character>> KEY_PAD = List.of(
            List.of('7', '8', '9'),
            List.of('4', '5', '6'),
            List.of('1', '2', '3'),
            List.of('#', '0', 'A')
    );
    protected static final List<List<Character>> DIR_PAD = List.of(
            List.of('#', '^', 'A'),
            List.of('<', 'v', '>')
    );
    protected static final Map<Character, Point> COORD_OF = Map.of(
            '^', new Point(1, 0),
            'A', new Point(2, 0),
            '<', new Point(0, 1),
            'v', new Point(1, 1),
            '>', new Point(2, 1)
    );

    protected Long sumComplexitiesOfCodes(String filePath, Integer depth) {
        var complexities = FileUtility.parseFileToList(filePath, line -> findComplexityOfCode(line, depth));
        return complexities.stream()
                .mapToLong(l -> l)
                .sum();
    }

    protected Long findComplexityOfCode(String code, Integer depth) {
        var best = Long.MAX_VALUE;
        var numericPadSequences = findSequencesRecursive(code, KEY_PAD_START, "", KEY_PAD);
        for (var sequence : numericPadSequences) {
            var count = countPushesRecursive(sequence, depth);
            best = Math.min(best, count);
        }

        return best * codeToNumber(code);
    }

    protected List<String> findSequencesRecursive(String code, Point start, String core, final List<List<Character>> pad) {
        // Exit conditions
        if (code.isEmpty()) {
            return List.of(core);
        }

        var c = code.charAt(0);
        var collected = new ArrayList<String>();
        var shortestPaths = calculateShortestPaths(start, c, pad);
        for (var path : shortestPaths) {
            collected.addAll(findSequencesRecursive(code.substring(1), path.end, core + path.steps + "A", pad));
        }
        var minLength = collected.stream()
                .mapToInt(String::length)
                .min()
                .orElseThrow();
        return collected.stream()
                .filter(s -> s.length() == minLength)
                .distinct()
                .toList();
    }

    private List<Path> calculateShortestPaths(Point start, Character goal, final List<List<Character>> pad) {
        if (isValidPoint(start,pad) && getPoint(start, pad) == goal) {
            return List.of(new Path(start, ""));
        }

        var queue = new ArrayDeque<>(List.of(new Path(start, "")));
        var result = new ArrayList<Path>();
        var best = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            var current = queue.poll();
            var neighbours = GridUtility.findOrthogonalNeighbours(current.end, pad);
            for (var neighbour : neighbours) {
                if (isValidPoint(neighbour, pad)) {
                    var newPath = new Path(neighbour, current.steps + makeDirection(current.end, neighbour));
                    if (getPoint(neighbour, pad) == goal) {
                        if (newPath.steps.length() < best) {
                            best = newPath.steps.length();
                            result.clear();
                            result.add(newPath);
                        } else if (newPath.steps.length() == best) {
                            result.add(newPath);
                        }
                    } else if (newPath.steps.length() < best) {
                        queue.add(newPath);
                    }
                }
            }
        }
        return result;
    }

    private Character makeDirection(Point current, Point other) {
        if (current.x() < other.x()) {
            return '>';
        } else if (current.x() > other.x()) {
            return '<';
        } else if (current.y() < other.y()) {
            return 'v';
        } else if (current.y() > other.y()) {
            return '^';
        } else {
            throw new IllegalArgumentException("Same point was given: " + this + " to: " + other);
        }
    }

    private Boolean isValidPoint(Point point, final List<List<Character>> pad) {
        return !getPoint(point, pad).equals('#')
                && point.x() >= 0 && point.x() < pad.get(0).size()
                && point.y() >= 0 && point.y() < pad.size();
    }

    private Character getPoint(Point point, final List<List<Character>> pad) {
        return pad.get(point.y()).get(point.x());
    }

    private Integer codeToNumber(String code) {
        return Integer.parseInt(code.substring(0, code.length() - 1));
    }

    private long countPushesRecursive(String move, Integer depth) {
        if (depth == 0) {
            return move.length();
        }
        var key = new RoadDepth(move, depth);
        // Use the cache
        if (roadDepthMap.containsKey(key)) {
            return roadDepthMap.get(key);
        }
        var position = DIR_PAD_START;
        var pushes = 0L;
        for (var c : move.toCharArray()) {
            var min = Long.MAX_VALUE;
            for (var road : getRoads(c, position)) {
                min = Long.min(min, countPushesRecursive(road, depth -1));
            }
            pushes += min;
            position = COORD_OF.get(c);
        }
        roadDepthMap.put(key, pushes);
        return pushes;

    }

    private List<String> getRoads(Character to, Point current) {
        return calculateShortestPaths(current, to, DIR_PAD).stream()
                .map(p -> p.steps + "A")
                .toList();
    }

    protected record Path(Point end, String steps) {}
    protected record RoadDepth(String road, Integer depth) {}
}

