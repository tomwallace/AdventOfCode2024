package com.tomwallace.adventofcode2024.java.problems.solutions.day19;

import com.tomwallace.adventofcode2024.java.problems.solutions.day15.Day15;
import com.tomwallace.adventofcode2024.java.problems.solutions.day18.Day18;
import com.tomwallace.adventofcode2024.java.problems.solutions.day18.Maze;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19Tests {

    @ParameterizedTest
    @CsvSource({"brwrr,true", "bggr,true", "gbbr,true", "rrbgbr,true", "ubwu,false"})
    public void isDesignPossibleRecurse(String input, Boolean expected) {
        var patterns = List.of("r", "wr", "b", "g", "bwu", "rb", "gb", "br");
        var sut = new Day19();
        var result = sut.isDesignPossibleRecurse(input, patterns);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"brwrr,2", "bggr,1", "gbbr,4", "rrbgbr,6", "ubwu,0"})
    public void countDesignPossibleRecurse(String input, Integer expected) {
        var patterns = List.of("r", "wr", "b", "g", "bwu", "rb", "gb", "br");
        var sut = new Day19();
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
    /*
    // Ran for over 2 hours without an answer - will need to try something else
    @Test
    public void partB_Actual() {
        var sut = new Day19();
        var result = sut.partB();

        assertEquals("-1", result);
    }

    /*
    @Test
    public void findFewestSteps() {
        var filePath = FileUtility.testDataPath + "Day18TestInputA.txt";
        var sut = new Maze(filePath, 7, 7, 12);
        var result = sut.findFewestSteps();

        assertEquals(22, result);
    }

    @Test
    public void findFirstBlockingByte() {
        var filePath = FileUtility.testDataPath + "Day18TestInputA.txt";
        var sut = new Maze(filePath, 7, 7, 12);
        var result = sut.findFirstBlockingByte();

        assertEquals("6,1", result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day18();
        var result = sut.partA();

        assertEquals("354", result);
    }

    // Takes 36 sec, may want to comment out
    @Test
    public void partB_Actual() {
        var sut = new Day18();
        var result = sut.partB();

        assertEquals("36,17", result);
    }

     */
}
