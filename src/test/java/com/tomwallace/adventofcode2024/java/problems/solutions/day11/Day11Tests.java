package com.tomwallace.adventofcode2024.java.problems.solutions.day11;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Tests {

    @ParameterizedTest
    @CsvSource({"6,22", "25,55312"})
    public void countPebblesByMap(Integer timesToBlink, Long expected) {
        var input = "125 17";
        var sut = new Day11();
        var result = sut.countPebblesByMap(input, timesToBlink);

        assertEquals(expected, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day11();
        var result = sut.partA();

        assertEquals("199982", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day11();
        var result = sut.partB();

        assertEquals("237149922829154", result);
    }
}
