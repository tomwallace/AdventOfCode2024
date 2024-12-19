package com.tomwallace.adventofcode2024.java.problems.solutions.day12;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Tests {

    @ParameterizedTest
    @CsvSource({"Day12TestInputA.txt,140", "Day12TestInputB.txt,1930"})
    public void sumAllRegionFencingPrices(String input, Long expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day12();
        var result = sut.sumAllRegionFencingPrices(filePath, false);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"Day12TestInputA.txt,80", "Day12TestInputB.txt,1206"})
    public void sumAllRegionFencingPrices_WithFenceSides(String input, Long expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day12();
        var result = sut.sumAllRegionFencingPrices(filePath, true);

        assertEquals(expected, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day12();
        var result = sut.partA();

        assertEquals("1452678", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day12();
        var result = sut.partB();

        assertEquals("873584", result);
    }
}
