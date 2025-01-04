package com.tomwallace.adventofcode2024.java.problems.solutions.day24;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.*;
import java.util.stream.Collectors;

public class Day24 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Crossed Wires";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 24;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day24Input.txt";
        var output = findOutputOfZWires(filePath);
        return output.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day24Input.txt";
        return findFaultyOperatorWires(filePath);
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

    protected Long findOutputOfZWires(String filePath) {
        var wiresMap = getWiresMap(filePath);
        var operators = getOperators(filePath);

        var allOutputWires = operators.stream()
                .map(o -> o.out)
                .toList();

        while (!wiresMap.keySet().containsAll(allOutputWires)) {
            for (var operator : operators) {
                var result = performOperation(operator, wiresMap);
                if (result  > -1) {
                    wiresMap.put(operator.out, result);
                }
            }
        }

        return collectZWires(wiresMap);
    }

    protected String findFaultyOperatorWires(String filePath) {
        // I could not understand this part of the problem, and needed to find a summary of what to look for on Reddit
        /*
         * There are 4 cases in which are faulty:
         *
         * 1. If there is output to a z-wire, the operator should always be XOR (unless
         * it is the final bit). If not -> faulty.
         * 2. If the output is not a z-wire and the inputs are not x and y, the operator
         * should always be AND or OR. If not -> faulty.
         * 3. If the inputs are x and y (but not the first bit) and the operator is XOR,
         * the output wire should be the input of another XOR-gate. If not -> faulty.
         * 4. If the inputs are x and y (but not the first bit) and the operator is AND,
         * the output wire should be the input of an OR-gate. If not -> faulty.
         */
        var operators = getOperators(filePath);
        var faultyOperators = new ArrayList<Operator>();
        for (final var operator : operators) {
            if (operator.out.startsWith("z") && !operator.out.equals("z45") && !operator.operation.equals(Operation.XOR)) {
                faultyOperators.add(operator);
            } else if (!operator.out.startsWith("z")
                    && !(operator.inOne.startsWith("x") || operator.inOne.startsWith("y"))
                    && !(operator.inTwo.startsWith("x") || operator.inTwo.startsWith("y"))) {
                if (operator.operation.equals(Operation.XOR)) {
                    faultyOperators.add(operator);
                }
            } else if (operator.operation.equals(Operation.XOR)
                    && (operator.inOne.startsWith("x") || operator.inOne.startsWith("y"))
                    && (operator.inTwo.startsWith("x") || operator.inTwo.startsWith("y"))) {
                if (!(operator.inOne.endsWith("00") && operator.inTwo.endsWith("00"))) {
                    var output = operator.out;
                    var anotherFound = false;
                    for (final var operator2 : operators) {
                        if (!operator2.equals(operator)) {
                            if ((operator2.inOne.equals(output) || operator2.inTwo.equals(output))
                                    && operator2.operation.equals(Operation.XOR)) {
                                anotherFound = true;
                                break;
                            }
                        }
                    }
                    if (!anotherFound) {
                        faultyOperators.add(operator);
                    }
                }
            } else if (operator.operation.equals(Operation.AND)
                    && (operator.inOne.startsWith("x") || operator.inOne.startsWith("y"))
                    && (operator.inTwo.startsWith("x") || operator.inTwo.startsWith("y"))) {
                if (!(operator.inOne.endsWith("00") && operator.inTwo.endsWith("00"))) {
                    var output = operator.out;
                    var anotherFound = false;
                    for (final var operator2 : operators) {
                        if (!operator2.equals(operator)) {
                            if ((operator2.inOne.equals(output) || operator2.inTwo.equals(output))
                                    && operator2.operation.equals(Operation.OR)) {
                                anotherFound = true;
                                break;
                            }
                        }
                    }
                    if (!anotherFound) {
                        faultyOperators.add(operator);
                    }
                }
            }
        }

        return faultyOperators.stream()
                .sorted()
                .map(f -> f.out)
                .collect(Collectors.joining(","));
    }

    private HashMap<String, Integer> getWiresMap(String filePath) {
        var wiresMap = new HashMap<String, Integer>();
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        for (var line : lines) {
            if (line.isEmpty()) {
                break;
            }
            var split = line.split(": ");
            wiresMap.put(split[0], Integer.parseInt(split[1]));
        }
        return wiresMap;
    }

    private List<Operator> getOperators(String filePath) {
        var operators = new ArrayList<Operator>();
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var firstPart = true;
        for (var line : lines) {
            if (line.isEmpty()) {
                firstPart = false;
                continue;
            }
            if (!firstPart) {
                operators.add(lineToOperator(line));
            }
        }
        return operators;
    }

    private Operator lineToOperator(String line) {
        var splitOne = line.split(" -> ");
        var out = splitOne[1];
        var splitTwo = splitOne[0].split(" ");
        var inOne = splitTwo[0];
        Operation operation = Operation.valueOf(splitTwo[1]);
        var inTwo = splitTwo[2];
        return new Operator(inOne, inTwo, operation, out);
    }

    private Integer performOperation(Operator operator, HashMap<String, Integer> wiresMap) {
        var inOneValue = wiresMap.get(operator.inOne);
        var inTwoValue = wiresMap.get(operator.inTwo);
        // If we don't have either input, we return -1, indicating it cannot process
        if (inOneValue == null || inTwoValue == null) {
            return -1;
        }
        return switch (operator.operation) {
            case AND -> inOneValue == 1 && inTwoValue == 1 ? 1 : 0;
            case OR -> inOneValue == 1 || inTwoValue == 1 ? 1 : 0;
            case XOR -> inOneValue.equals(inTwoValue) ? 0 : 1;
        };
    }

    private Long collectZWires(HashMap<String, Integer> wireMap) {
        var zValues = wireMap.keySet().stream()
                .filter(wireKey -> wireKey.charAt(0) == 'z')
                .sorted(Comparator.reverseOrder())
                .map(wireKey -> wireMap.get(wireKey).toString())
                .toList();
        var binaryString = String.join("", zValues);
        return Long.parseLong(binaryString, 2);
    }

    protected record Operator(String inOne, String inTwo, Operation operation, String out) implements Comparable<Operator> {
        @Override
        public int compareTo(Operator o) {
            return this.out.compareTo(o.out);
        }
    }
    protected enum Operation {
        AND,
        OR,
        XOR
    }
}
