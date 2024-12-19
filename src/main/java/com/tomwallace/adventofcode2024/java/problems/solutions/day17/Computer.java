package com.tomwallace.adventofcode2024.java.problems.solutions.day17;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Computer {
    private HashMap<String, Long> registers;
    private List<Long> output;
    private List<Long> program;

    // For unit tests
    public Computer(List<Long> program) {
        registers = new HashMap<>();
        output = new ArrayList<>();
        this.program = program;
    }

    public Computer(String filePath) {
        registers = new HashMap<>();
        output = new ArrayList<>();
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        registers.put("A", Long.parseLong(lines.get(0).split(" ")[2]));
        registers.put("B", Long.parseLong(lines.get(1).split(" ")[2]));
        registers.put("C", Long.parseLong(lines.get(2).split(" ")[2]));
        program = Arrays.stream(lines.get(4).split(" ")[1].split(","))
                .map(Long::parseLong)
                .mapToLong(Long::longValue)
                .boxed()
                .toList();
    }

    public void run() {
        var pointer = 0;
        while (pointer < program.size()) {
            pointer = runInstruction(program.get(pointer), program.get(pointer + 1), pointer, registers);
        }
    }

    public String findOutput() {
        return output.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private Integer runInstruction(Long opCode, Long operand, Integer pointer, HashMap<String, Long> registers) {
        switch (Math.toIntExact(opCode)) {
            // adv -
            case 0:
                var num = registers.get("A");
                var denom = Math.pow(2, findComboOperand(operand, registers));
                registers.put("A", (long) (num/denom));
                return pointer + 2;
            // bxl -
            case 1:
                registers.compute("B", (k, b) -> (b ^ operand));
                return pointer + 2;
            // bst -
            case 2:
                registers.put("B", findComboOperand(operand, registers) % 8);
                return pointer + 2;
            // jnz -
            case 3:
                var a = registers.get("A");
                if (a == 0L)
                    return pointer + 2;
                return Math.toIntExact(operand);
            // bxc -
            case 4:
                var b = registers.get("B");
                var c = registers.get("C");
                registers.put("B", (b^c));
                return pointer + 2;
            // out -
            case 5:
                output.add(findComboOperand(operand, registers) % 8);
                return pointer + 2;
            // bdv
            case 6:
                var num2 = registers.get("A");
                var denom2 = Math.pow(2, findComboOperand(operand, registers));
                registers.put("B", (long) (num2/denom2));
                return pointer + 2;
            // cdv
            case 7:
                var num3 = registers.get("A");
                var denom3 = Math.pow(2, findComboOperand(operand, registers));
                registers.put("C", (long) (num3/denom3));
                return pointer + 2;
            default:
                throw new IllegalStateException("Unexpected opCode value: " + opCode);
        }
    }

    private Long findComboOperand(Long operand, HashMap<String, Long> registers) {
        return switch (Math.toIntExact(operand)) {
            case 0 -> 0L;
            case 1 -> 1L;
            case 2 -> 2L;
            case 3 -> 3L;
            case 4 -> registers.get("A");
            case 5 -> registers.get("B");
            case 6 -> registers.get("C");
            default -> throw new IllegalArgumentException(String.format("Unknown operand %d", operand));
        };
    }
}
