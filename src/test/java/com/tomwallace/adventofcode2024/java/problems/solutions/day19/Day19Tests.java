package com.tomwallace.adventofcode2024.java.problems.solutions.day19;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19Tests {

    @ParameterizedTest
    @CsvSource({"brwrr,2", "bggr,1", "gbbr,4", "rrbgbr,6", "ubwu,0"})
    public void countDesignPossibleRecurse(String input, Long expected) {
        var patterns = List.of("r", "wr", "b", "g", "bwu", "rb", "gb", "br");
        var sut = new Day19();
        sut.cache = new HashMap<>();
        var result = sut.countDesignPossibleRecurse(input, patterns);

        assertEquals(expected, result);
    }

    @Test
    public void countPossibleDesigns() {
        var filePath = FileUtility.testDataPath + "Day19TestInputA.txt";
        var sut = new Day19();
        var result = sut.countPossibleDesigns(filePath);

        assertEquals(6, result);
    }

    @Test
    public void countAllPossibleDesigns() {
        var filePath = FileUtility.testDataPath + "Day19TestInputA.txt";
        var sut = new Day19();
        var result = sut.countAllPossibleDesigns(filePath);

        assertEquals(16, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day19();
        var result = sut.partA();

        assertEquals("220", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day19();
        var result = sut.partB();

        assertEquals("565600047715343", result);
    }
}
