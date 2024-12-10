package com.tomwallace.adventofcode2024.java.problems.solutions.day10;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {

    @ParameterizedTest
    @CsvSource({"Day10TestInputA.txt,2", "Day10TestInputB.txt,4", "Day10TestInputC.txt,3", "Day10TestInputD.txt,36"})
    public void sumTrailCalculations(String input, Integer expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day10();
        var result = sut.sumTrailCalculations(filePath, true);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"Day10TestInputD.txt,81"})
    public void sumTrailCalculations_ValidTrails(String input, Integer expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day10();
        var result = sut.sumTrailCalculations(filePath, false);

        assertEquals(expected, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day10();
        var result = sut.partA();

        assertEquals("535", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day10();
        var result = sut.partB();

        assertEquals("1186", result);
    }
}
