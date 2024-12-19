package com.tomwallace.adventofcode2024.java.problems.solutions.day14;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private final Integer maxX;
    private final Integer maxY;
    @Getter
    private List<Robot> robots;
    @Getter
    private Integer time;

    // For unit tests
    public Map(Integer maxX, Integer maxY) {
        robots = new ArrayList<>();
        this.maxX = maxX;
        this.maxY = maxY;
        time = 0;
    }

    public Map(String filePath, Integer maxX, Integer maxY) {
        robots = FileUtility.parseFileToList(filePath, this::parseLine);
        this.maxX = maxX;
        this.maxY = maxY;
        time = 0;
    }

    public void step() {
        var newRobots = new ArrayList<Robot>();
        for (var robot : robots) {
            newRobots.add(moveRobot(robot));
        }
        robots = newRobots;
        time++;
    }

    public Long calculateSafetyScore() {
        // top left quad
        long score = countRobotsInRange(0, (maxX/2) - 1, 0, maxY/2 - 1);
        // top right quad
        score *= countRobotsInRange((maxX/2) + 1, maxX, 0, maxY/2 - 1);
        // bottom left quad
        score *= countRobotsInRange(0, (maxX/2) - 1, maxY/2 + 1, maxY);
        // bottom right quad
        score *= countRobotsInRange((maxX/2) + 1, maxX, maxY/2 + 1, maxY);
        return score;
    }

    public void print() {
        var builder = new StringBuilder();
        builder.append(String.format("Time: %d\n", time));
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                final int yFinal = y;
                final int xFinal = x;
                var count = robots.stream()
                        .filter(r -> r.location.x() == xFinal && r.location.y() == yFinal)
                        .count();
                if (count > 0)
                    builder.append(count);
                else
                    builder.append('.');
            }
            builder.append("\n");
        }
        builder.append("\n");
        builder.append("\n");
        System.out.print(builder);
    }

    public Boolean doesMapContainRowWithConsecutiveRobots() {
        for (int y = 0; y <= maxY; y++) {
            final var finalY = y;
            var row = robots.stream()
                    .filter(r -> r.location.y() == finalY)
                    .map(r -> r.location.x())
                    .mapToInt(Integer::intValue)
                    .sorted()
                    .toArray();
            var maxCount = 1;
            var currentCount = 1;
            for (int i = 1; i < row.length; i++) {
                var current = row[i];
                var previous = row[i - 1];
                if (current == (previous + 1)) {
                    currentCount++;
                } else if (current != previous) {
                    currentCount = 1;
                }
                maxCount = Math.max(maxCount, currentCount);
                if (maxCount >= 8)
                    return true;
            }
        }
        return false;
    }

    protected Robot moveRobot(Robot robot) {
        var velocity = robot.velocity;
        var newX = robot.location.x() + velocity.x();
        if (newX > maxX) {
            newX = newX - maxX - 1;
        } else if (newX < 0) {
            newX = maxX + newX + 1;
        }

        var newY = robot.location.y() + velocity.y();
        if (newY > maxY) {
            newY = newY - maxY - 1;
        } else if (newY < 0) {
            newY = maxY + newY + 1;
        }
        return new Robot(new Point(newX, newY), velocity);
    }

    // Ex: p=2,4 v=2,-3
    protected Robot parseLine(String line) {
        var splits = line.split(" ");
        var splitZero = splits[0].split(",");
        var location = new Point(Integer.parseInt(splitZero[0].split("=")[1]), Integer.parseInt(splitZero[1]));
        var splitOne = splits[1].split(",");
        var velocity = new Point(Integer.parseInt(splitOne[0].split("=")[1]), Integer.parseInt(splitOne[1]));
        return new Robot(location, velocity);
    }

    private Long countRobotsInRange(Integer minX, Integer maxX, Integer minY, Integer maxY) {
        return robots.stream()
                .filter(r -> r.location.x() >= minX && r.location.x() <= maxX && r.location.y() >= minY && r.location.y() <= maxY)
                .count();
    }

    protected record Robot(Point location, Point velocity) {}
}
