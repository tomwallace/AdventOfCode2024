package com.tomwallace.adventofcode2024.java.problems.solutions.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Tests {

    @Test
    public void sumAllMulMatchingPairs_Simple() {
        var input = "mul(44,46)";
        var sut = new Day3();
        var result = sut.sumAllMulMatchingPairs(input);

        assertEquals(2024, result);
    }

    @Test
    public void sumAllMulMatchingPairs_Complex() {
        var input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        var sut = new Day3();
        var result = sut.sumAllMulMatchingPairs(input);

        assertEquals(161, result);
    }

    @Test
    public void sumAllMulMatchingPairsOnOff() {
        var input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
        var sut = new Day3();
        var result = sut.sumAllMulMatchingPairsOnOff(input);

        assertEquals(48, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day3();
        var result = sut.partA();

        assertEquals("173785482", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day3();
        var result = sut.partB();

        assertEquals("83158140", result);
    }
}
