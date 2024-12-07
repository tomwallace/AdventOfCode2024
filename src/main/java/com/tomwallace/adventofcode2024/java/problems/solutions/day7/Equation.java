package com.tomwallace.adventofcode2024.java.problems.solutions.day7;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Equation {
    @Getter
    private final Long testValue;
    private final List<Long> numbers;
    private final Boolean useConcatenation;

    public Equation(String input, Boolean useConcatenation) {
        var splits = input.split(":");
        testValue = Long.parseLong(splits[0]);
        numbers = Arrays.stream(splits[1].trim().split(" "))
                .map(Long::parseLong)
                .toList();
        this.useConcatenation = useConcatenation;
    }

    public Boolean isPossible() {
        var queue = new LinkedList<State>();
        queue.add(new State(0L, numbers));
        while (!queue.isEmpty()) {
            var current = queue.remove();
            if (current.remaining.isEmpty()) {
                if (current.total.equals(testValue))
                    return true;
            } else {
                // Put all other options on the queue
                var firstValue = current.remaining.get(0);
                var newRemaining = new ArrayList<>(current.remaining);
                newRemaining.remove(0);
                queue.add(new State(current.total + firstValue, newRemaining));
                queue.add(new State(current.total * firstValue, newRemaining));
                if (useConcatenation) {
                    queue.add(new State(Long.parseLong(String.format("%d%d", current.total, firstValue)), newRemaining));
                }
            }
        }
        return false;
    }

    private record State(Long total, List<Long> remaining) {}
}
