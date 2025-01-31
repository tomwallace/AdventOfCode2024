package com.tomwallace.adventofcode2024.java.problems.solutions.day15;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Tests {

    @ParameterizedTest
    @CsvSource({"Day15TestInputA.txt,2028", "Day15TestInputB.txt,10092"})
    public void sumGpsCoordinates(String input, Long expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day15();
        var result = sut.sumGpsCoordinates(filePath, false);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"Day15TestInputC.txt,618", "Day15TestInputB.txt,9021"})
    public void sumGpsCoordinates_Wide(String input, Long expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day15();
        var result = sut.sumGpsCoordinates(filePath, true);

        assertEquals(expected, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day15();
        var result = sut.partA();

        assertEquals("1517819", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day15();
        var result = sut.partB();

        assertEquals("1538862", result);
    }
}
