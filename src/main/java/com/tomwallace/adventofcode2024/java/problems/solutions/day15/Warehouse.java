package com.tomwallace.adventofcode2024.java.problems.solutions.day15;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {
    private List<List<Character>> grid;
    private Point robot;
    private Integer maxX;
    private Integer maxY;

    public Warehouse(List<String> input) {
        grid = new ArrayList<>();
        for (String line : input) {
            grid.add(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }
        maxX = grid.get(0).size();
        maxY = grid.size();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (grid.get(y).get(x) == '@') {
                    robot = new Point(x, y);
                    grid.get(y).set(x, '.');
                }
            }
        }
    }

    public void moveRobot(Character direction) {
        switch (direction) {
            case '^' -> tryMoveUp();
            case '>' -> tryMoveRight();
            case 'v' -> tryMoveDown();
            case '<' -> tryMoveLeft();
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };

    }

    public void printGrid() {
        var builder = new StringBuilder();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (y == robot.y() && x == robot.x()) {
                    builder.append("@");
                } else {
                    builder.append(grid.get(y).get(x));
                }
            }
            builder.append("\n");
        }
        builder.append("\n");
        builder.append("\n");
        System.out.print(builder);
    }

    public Integer sumGpsCoordinates() {
        var sum = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (grid.get(y).get(x) == 'O') {
                    sum += (100 * y) + x;
                }
            }
        }
        return sum;
    }


    // TODO: Refactor to remove duplicated code
    private void tryMoveRight() {
        var targetX = robot.x() + 1;
        var targetY = robot.y();
        if (!GridUtility.isPointInBounds(new Point(targetX, targetY), grid))
            return;
        if (grid.get(targetY).get(targetX) == '#')
            return;
        if (grid.get(targetY).get(targetX) == '.') {
            robot = new Point(targetX, targetY);
            return;
        }
        // Try to push
        if (grid.get(targetY).get(targetX) == 'O') {
            for (var x = targetX; x < maxX; x++) {
                if (grid.get(targetY).get(x) == '#')
                    break;
                if (grid.get(targetY).get(x) == '.') {
                    for (var backX = x; backX > targetX; backX--) {
                        grid.get(targetY).set(backX, 'O');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    break;
                }
            }
        }
    }

    private void tryMoveDown() {
        var targetX = robot.x();
        var targetY = robot.y() + 1;
        if (!GridUtility.isPointInBounds(new Point(targetX, targetY), grid))
            return;
        if (grid.get(targetY).get(targetX) == '#')
            return;
        if (grid.get(targetY).get(targetX) == '.') {
            robot = new Point(targetX, targetY);
            return;
        }
        // Try to push
        if (grid.get(targetY).get(targetX) == 'O') {
            for (var y = targetY; y < maxY; y++) {
                if (grid.get(y).get(targetX) == '#')
                    break;
                if (grid.get(y).get(targetX) == '.') {
                    for (var backY = y; backY > targetY; backY--) {
                        grid.get(backY).set(targetX, 'O');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    break;
                }
            }
        }
    }

    private void tryMoveLeft() {
        var targetX = robot.x() - 1;
        var targetY = robot.y();
        if (!GridUtility.isPointInBounds(new Point(targetX, targetY), grid))
            return;
        if (grid.get(targetY).get(targetX) == '#')
            return;
        if (grid.get(targetY).get(targetX) == '.') {
            robot = new Point(targetX, targetY);
            return;
        }
        // Try to push
        if (grid.get(targetY).get(targetX) == 'O') {
            for (var x = targetX; x >= 0; x--) {
                if (grid.get(targetY).get(x) == '#')
                    break;
                if (grid.get(targetY).get(x) == '.') {
                    for (var backX = x; backX < targetX; backX++) {
                        grid.get(targetY).set(backX, 'O');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    break;
                }
            }
        }
    }

    private void tryMoveUp() {
        var targetX = robot.x();
        var targetY = robot.y() - 1;
        if (!GridUtility.isPointInBounds(new Point(targetX, targetY), grid))
            return;
        if (grid.get(targetY).get(targetX) == '#')
            return;
        if (grid.get(targetY).get(targetX) == '.') {
            robot = new Point(targetX, targetY);
            return;
        }
        // Try to push
        if (grid.get(targetY).get(targetX) == 'O') {
            for (var y = targetY; y >= 0; y--) {
                if (grid.get(y).get(targetX) == '#')
                    break;
                if (grid.get(y).get(targetX) == '.') {
                    for (var backY = y; backY < targetY; backY++) {
                        grid.get(backY).set(targetX, 'O');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    break;
                }
            }
        }
    }
}
