package com.tomwallace.adventofcode2024.java.problems.solutions.day17;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Chronospatial Computer";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 17; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day17Input.txt";
        var computer = new Computer(filePath);
        computer.run();
        return computer.findOutput();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day17Input.txt";
        var result = findLowestRegisterA2(filePath);
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

    // This brute force method, of course, did not work on the actual Part B input
    protected Long findLowestRegisterA(String filePath) {
        var computer = new Computer(filePath);
        var regA = 0L;
        var target = computer.getProgram().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        var output = "notequal";
        while (!target.equals(output)) {
            regA++;
            computer = new Computer(filePath);
            computer.getRegisters().put("A", regA);
            computer.run();
            output = computer.findOutput();
        }
        return regA;
    }

    // I am terrible at reverse-engineering the inputs as they run through the problem set
    // I pulled the following code, which make some sense to me, from https://github.com/ash42/adventofcode/blob/main/adventofcode2024/src/nl/michielgraat/adventofcode2024/day17/Day17.java
    protected Long findLowestRegisterA2(String filePath) {
        // Work from end of program backwards. We will get the last digit when a is
        // somewhere between 0 and 7.
        // After we have this first a, shift it left by 3 and start from that a until
        // a+7. When an a is found for the last and last-1 digits, shift a left by 3
        // again. Rinse and repeat.
        // For a digit there are potentially multiple a's that fit. So use these all
        // as candidates for generating the next digit.
        // In the end, get the minimum of all a's which lead to a copy of the program.
        var candidates = new HashSet<Long>();
        candidates.add(0L);
        var computer = new Computer(filePath);
        for (int i = 1; i <= computer.getProgram().size(); i++) {
            var newCandidates = new HashSet<Long>();
            for (final long candidate : candidates) {
                newCandidates.addAll(findCandidates(candidate, i, filePath));
            }
            candidates = newCandidates;
        }
        return candidates.stream().mapToLong(l -> l).min().getAsLong();
    }


    private Set<Long> findCandidates(final long start, final int position, final String filePath) {
        var candidates = new HashSet<Long>();
        for (var a = start; a < start + 8; a++) {
            var computer = new Computer(filePath);
            computer.getRegisters().put("A", a);
            computer.run();
            var program = computer.getProgram();
            var output = computer.getOutput();
            var valid = true;
            for (int i = position; i > 0; i--) {
                if (!program.get(program.size() - position).equals(output.get(output.size() - position))) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                candidates.add(position < program.size() ? a << 3 : a);
            }
        }
        return candidates;
    }

}



