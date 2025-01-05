package com.tomwallace.adventofcode2024.java.problems.solutions.day15;

import com.tomwallace.adventofcode2024.java.common.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Warehouse {
    private List<List<Character>> grid;
    private Point robot;
    private final Integer maxX;
    private final Integer maxY;

    private List<Point> boxes;
    private List<Point> moveableBoxes;
    private final List<Point> walls;
    private final Boolean isWide;

    public Warehouse(List<String> input, Boolean isWide) {
        grid = new ArrayList<>();
        boxes = new ArrayList<>();
        moveableBoxes = new ArrayList<>();
        walls = new ArrayList<>();
        this.isWide = isWide;
        for (String line : input) {
            grid.add(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }
        checkAndWidenGrid();
        maxX = grid.get(0).size();
        maxY = grid.size();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (grid.get(y).get(x) == '@') {
                    robot = new Point(x, y);
                } else if (grid.get(y).get(x) == '#') {
                    walls.add(new Point(x, y));
                } else if (grid.get(y).get(x) == 'O') {
                    boxes.add(new Point(x, y));
                } else if (grid.get(y).get(x) == '[') {
                    boxes.add(new Point(x, y));
                }
            }
        }
    }

    public void moveRobot(Character direction) {
        var robotCanMove = true;
        if (isRobotBlockedByWall(direction)) {
            robotCanMove = false;
            moveableBoxes.clear();
        } else {
            var possibleBox = findBoxPointNextToRobot(direction);
            if (possibleBox == null) {
                moveableBoxes.clear();
            } else {
                moveableBoxes = findMovableBoxes(possibleBox, direction);
                robotCanMove = !moveableBoxes.isEmpty();
            }
        }

        if (!moveableBoxes.isEmpty()) {
            var newBoxes = new ArrayList<Point>();
            newBoxes.addAll(boxes.stream()
                    .filter(Predicate.not(moveableBoxes::contains))
                    .toList());
            newBoxes.addAll(boxes.stream()
                    .filter(moveableBoxes::contains)
                    .map(mb -> getNextPoint(mb, direction))
                    .toList());
            boxes = newBoxes;
        }

        if (robotCanMove) {
            robot = getNextPoint(robot, direction);
        }
    }

    public void printGrid(Character direction) {
        var builder = new StringBuilder();
        builder.append("Direction: ").append(direction);
        builder.append("\n");
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (y == robot.y() && x == robot.x()) {
                    builder.append("@");
                } else if (boxes.contains(new Point(x, y))) {
                    if (isWide) {
                        builder.append("[]");
                        x++;
                    } else {
                        builder.append("0");
                    }
                } else if (walls.contains(new Point(x, y))) {
                    builder.append("#");
                } else {
                    builder.append(".");
                }
            }
            builder.append("\n");
        }
        builder.append("\n");
        builder.append("\n");
        System.out.print(builder);
    }

    public Long sumGpsCoordinates() {
        return boxes.stream()
                .mapToLong(b -> b.x() + 100L * b.y())
                .sum();
    }

    private Boolean isRobotBlockedByWall(Character direction) {
        var next = getNextPoint(robot, direction);
        return walls.contains(next);
    }

    private Boolean isBoxBlockedByWall(Point box, Character direction) {
        var next = getNextPoint(box, direction);
        if (isWide) {
            var wideNext = getNextPoint(next, '>');
            return walls.contains(next) || walls.contains(wideNext);
        }
        return walls.contains(next);
    }

    // Returns null if a box is not next to the robot, otherwise returns its position
    private Point findBoxPointNextToRobot(Character direction) {
        var next = getNextPoint(robot, direction);
        if (boxes.contains(next)) {
            return next;
        }
        if (isWide) {
            var wideNext = getNextPoint(next, '<');
            if (boxes.contains(wideNext)) {
                return wideNext;
            }
        }
        return null;
    }

    private List<Point> findMovableBoxes(Point initialPosition, Character direction) {
        var visited = new ArrayList<Point>();
        var queue = new ArrayDeque<Point>();
        queue.add(initialPosition);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            var next = getNextPoint(current, direction);
            if (boxes.contains(next)) {
                queue.addLast(next);
            }
            if (isWide) {
                var wideNext = getNextPoint(next, '>');
                if (boxes.contains(wideNext)) {
                    queue.addLast(wideNext);
                }
                var wideEast = getNextPoint(next, '<');
                if (boxes.contains(wideEast)) {
                    queue.addLast(wideEast);
                }
            }
        }
        return visited.stream()
                .noneMatch(p -> isBoxBlockedByWall(p, direction))
                ? visited
                : new ArrayList<>();
    }

    private Point getNextPoint(Point current, Character direction) {
        return switch (direction) {
            case '^' -> new Point(current.x(), current.y() - 1);
            case '>' -> new Point(current.x() + 1, current.y());
            case 'v' -> new Point(current.x(), current.y() + 1);
            case '<' -> new Point(current.x() - 1, current.y());
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };
    }

    private void checkAndWidenGrid() {
        if (!isWide) {
            return;
        }

        var newGrid = new ArrayList<List<Character>>();
        for (List<Character> characters : grid) {
            var row = new ArrayList<Character>();
            for (var x = 0; x < grid.get(0).size(); x++) {
                var current = characters.get(x);
                if (current.equals('#')) {
                    row.add('#');
                    row.add('#');
                } else if (current.equals('.')) {
                    row.add('.');
                    row.add('.');
                } else if (current.equals('O')) {
                    row.add('[');
                    row.add(']');
                } else if (current.equals('@')) {
                    row.add('@');
                    row.add('.');
                }
            }
            newGrid.add(row);
        }
        grid = newGrid;
    }
}
