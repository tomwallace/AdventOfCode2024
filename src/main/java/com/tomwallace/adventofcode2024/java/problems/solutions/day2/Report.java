package com.tomwallace.adventofcode2024.java.problems.solutions.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Report {

    private final List<Integer> levels;

    public Report(String input) {
        levels = Arrays.stream(input.split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }

    public Boolean isSafe() {
        return areAllLevelsSafe(levels);
    }

    public Boolean isSafeProblemDampened() {
        if (areAllLevelsSafe(levels))
            return true;
        // Otherwise, try removing one at a time to see if any succeed
        for (int i = 0; i < levels.size(); i++) {
            var levelsLocal = new ArrayList<>(levels);
            //noinspection SuspiciousListRemoveInLoop
            levelsLocal.remove(i);
            if (areAllLevelsSafe(levelsLocal))
                return true;
        }

        return false;
    }

    public String toString() {
        return levels.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    private Boolean areAllLevelsSafe(List<Integer> levelsLocal) {
        var goingUp = levelsLocal.get(0) < levelsLocal.get(1);
        for (int i = 0; i < levelsLocal.size() - 1; i++) {
            if (Math.abs(levelsLocal.get(i + 1) - levelsLocal.get(i)) > 3 || Math.abs(levelsLocal.get(i + 1) - levelsLocal.get(i)) < 1)
                return false;
            if (goingUp) {
                if (levelsLocal.get(i) >= levelsLocal.get(i + 1))
                    return false;
            } else {
                if (levelsLocal.get(i) <= levelsLocal.get(i + 1))
                    return false;
            }
        }

        return true;
    }
}