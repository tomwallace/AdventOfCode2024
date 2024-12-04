package com.tomwallace.adventofcode2024.java.problems.solutions.day4;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Tests {

    @Test
    public void countWordOccurrences() {
        var filePath = FileUtility.testDataPath + "Day4TestInputA.txt";
        var sut = new Day4();
        var result = sut.countWordOccurrences(filePath);

        assertEquals(18, result);
    }

    @Test
    public void countMasCrosses() {
        var filePath = FileUtility.testDataPath + "Day4TestInputA.txt";
        var sut = new Day4();
        var result = sut.countMasCrosses(filePath);

        assertEquals(9, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day4();
        var result = sut.partA();

        assertEquals("2583", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day4();
        var result = sut.partB();

        assertEquals("1978", result);
    }
}
