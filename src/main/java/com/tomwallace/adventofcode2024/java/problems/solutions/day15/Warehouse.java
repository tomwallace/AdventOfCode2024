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
        determineRobot();
    }

    public void widen() {
        // Put robot symbol back
        grid.get(robot.y()).set(robot.x(),'@');
        List<List<Character>> newGrid = new ArrayList<>();
        for (int y = 0; y < maxY; y++) {
            var row = new ArrayList<Character>();
            for (int x = 0; x < maxX; x++) {
                var current = grid.get(y).get(x);
                switch (current) {
                    case '#':
                        row.add('#');
                        row.add('#');
                        break;
                    case 'O':
                        row.add('[');
                        row.add(']');
                        break;
                    case '.':
                        row.add('.');
                        row.add('.');
                        break;
                    case '@':
                        row.add('@');
                        row.add('.');
                        break;
                    default:
                        throw new IllegalStateException("Unexpected in creating wider grid value: " + current);
                }
            }
            newGrid.add(row);
        }
        grid = newGrid;
        maxX = grid.get(0).size();
        maxY = grid.size();
        determineRobot();
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

    private void determineRobot() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (grid.get(y).get(x) == '@') {
                    robot = new Point(x, y);
                    grid.get(y).set(x, '.');
                }
            }
        }
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
        } else if (grid.get(targetY).get(targetX) == '[') {
            for (var x = targetX + 1; x < maxX; x++) {
                if (grid.get(targetY).get(x) == '#')
                    break;
                if (grid.get(targetY).get(x) == '.') {
                    for (var backX = x; backX > targetX; backX -= 2) {
                        grid.get(targetY).set(backX, ']');
                        grid.get(targetY).set(backX - 1, '[');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    grid.get(targetY).set(targetX - 1, '.');
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
        } else if (grid.get(targetY).get(targetX) == '[') {
            for (var y = targetY; y < maxY; y++) {
                if (grid.get(y).get(targetX) == '#' || grid.get(y).get(targetX + 1) == '#')
                    break;
                if (grid.get(y).get(targetX) == '.' && grid.get(y).get(targetX + 1) == '.') {
                    for (var backY = y; backY > targetY; backY--) {
                        grid.get(backY).set(targetX, '[');
                        grid.get(backY).set(targetX + 1, ']');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    grid.get(targetY).set(targetX + 1, '.');
                    break;
                }
            }
        } else if (grid.get(targetY).get(targetX) == ']') {
            for (var y = targetY; y < maxY; y++) {
                if (grid.get(y).get(targetX) == '#' || grid.get(y).get(targetX - 1) == '#')
                    break;
                if (grid.get(y).get(targetX) == '.' && grid.get(y).get(targetX - 1) == '.') {
                    for (var backY = y; backY > targetY; backY--) {
                        grid.get(backY).set(targetX, ']');
                        grid.get(backY).set(targetX - 1, '[');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    grid.get(targetY).set(targetX - 1, '.');
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
        } else if (grid.get(targetY).get(targetX) == ']') {
            for (var x = targetX - 1; x >= 0; x--) {
                if (grid.get(targetY).get(x) == '#')
                    break;
                if (grid.get(targetY).get(x) == '.') {
                    for (var backX = x; backX < targetX; backX += 2) {
                        grid.get(targetY).set(backX, '[');
                        grid.get(targetY).set(backX + 1, ']');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    grid.get(targetY).set(targetX + 1, '.');
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
        } else if (grid.get(targetY).get(targetX) == '[') {
            for (var y = targetY; y >= 0; y--) {
                if (grid.get(y).get(targetX) == '#' || grid.get(y).get(targetX + 1) == '#')
                    break;
                if (grid.get(y).get(targetX) == '.' && grid.get(y).get(targetX + 1) == '.') {
                    for (var backY = y; backY < targetY; backY++) {
                        grid.get(backY).set(targetX, '[');
                        grid.get(backY).set(targetX + 1, ']');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    grid.get(targetY).set(targetX + 1, '.');
                    break;
                }
            }
        } else if (grid.get(targetY).get(targetX) == ']') {
            for (var y = targetY; y >= 0; y--) {
                if (grid.get(y).get(targetX) == '#' || grid.get(y).get(targetX - 1) == '#')
                    break;
                if (grid.get(y).get(targetX) == '.' && grid.get(y).get(targetX - 1) == '.') {
                    for (var backY = y; backY < targetY; backY++) {
                        grid.get(backY).set(targetX, ']');
                        grid.get(backY).set(targetX - 1, '[');
                    }
                    robot = new Point(targetX, targetY);
                    grid.get(targetY).set(targetX, '.');
                    grid.get(targetY).set(targetX - 1, '.');
                    break;
                }
            }
        }
    }
}
