package com.tomwallace.adventofcode2024.java.problems.solutions.day6;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Map {

    private final List<Point> mods = List.of(new Point(0,-1), new Point(1,0), new Point(0, 1), new Point(-1, 0));
    private HashSet<Point> visited = new HashSet<>();
    private HashSet<State> states;

    private List<List<Character>> grid;
    private Point guardLocation;
    // 0 = N, 1 = E, 2 = S, 3 = W
    private Integer guardHeading;
    private Boolean exitOnRepeatState;

    public Map(String filePath, Boolean exitOnRepeatState) {
        grid = FileUtility.parseFileToList(filePath, line -> line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        findGuardLocationAndHeading();
        states = new HashSet<>();
        visited = new HashSet<>();
        this.exitOnRepeatState = exitOnRepeatState;
    }

    public Integer findNumberOfStepsUntilGuardLeaves() {
        var notOutYet = 1;
        do {
            visited.add(new Point(guardLocation.x, guardLocation.y));
            states.add(new State(new Point(guardLocation.x, guardLocation.y), guardHeading));
            notOutYet = moveGuard();
            if (exitOnRepeatState && notOutYet == -1) {
                return -1;
            }
        } while (notOutYet == 1);

        return visited.size();
    }

    @SneakyThrows
    private Integer moveGuard() {
        var newX = guardLocation.x + mods.get(guardHeading).x;
        var newY = guardLocation.y + mods.get(guardHeading).y;
        if (!isPointInBounds(new Point(newX, newY))) {
            return 0;
        }
        if (exitOnRepeatState && states.contains(new State(new Point(newX, newY), guardHeading))) {
            return -1;
        }
        // Continue moving
        if (grid.get(newY).get(newX).equals('.')) {
            guardLocation = new Point(newX, newY);
            return 1;
        }
        // Turn right enough times until we have a clear path
        for (int i = 1; i <= 4; i++) {
            guardHeading++;
            if (guardHeading > 3)
                guardHeading = 0;

            newX = guardLocation.x + mods.get(guardHeading).x;
            newY = guardLocation.y + mods.get(guardHeading).y;
            if (!isPointInBounds(new Point(newX, newY))) {
                return 0;
            }
            if (exitOnRepeatState && states.contains(new State(new Point(newX, newY), guardHeading))) {
                return -1;
            }
            // Continue moving
            if (grid.get(newY).get(newX).equals('.')) {
                guardLocation = new Point(newX, newY);
                return 1;
            }
        }

        // Should not get here
        throw new Exception("Should not have gotten here");
    }

    private Boolean isPointInBounds(Point point) {
        return point.x >= 0 && point.x < grid.get(0).size() && point.y >= 0 && point.y < grid.size();
    }

    @SneakyThrows
    private void findGuardLocationAndHeading() {
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                var location = grid.get(y).get(x);
                if (location != '#' && location != '.') {
                    guardLocation = new Point(x, y);
                    switch(location) {
                        case '^':
                            guardHeading = 0;
                            break;
                        case '>':
                            guardHeading = 1;
                            break;
                        case 'v':
                            guardHeading = 2;
                            break;
                        case '<':
                            guardHeading = 3;
                            break;
                        default:
                            throw new Exception(String.format("location %s is illegal", location));
                    }
                    // Remove first guard location
                    //noinspection ReassignedVariable
                    grid.get(y).set(x, '.');
                }
            }
        }
    }



    public record Point(Integer x, Integer y) {}
    public record State(Point guardLocation, Integer guardHeading) {}
}
