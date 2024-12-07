package com.tomwallace.adventofcode2024.java.problems.solutions.day6;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Tests {

    @Test
    public void findNumberOfStepsUntilGuardLeaves() {
        var filePath = FileUtility.testDataPath + "Day6TestInputA.txt";
        var sut = new Map(filePath, false);
        var result = sut.findNumberOfStepsUntilGuardLeaves();

        assertEquals(41, result);
    }

    @Test
    public void countObstructionPositions() {
        var filePath = FileUtility.testDataPath + "Day6TestInputA.txt";
        var sut = new Day6();
        var result = sut.countObstructionPositions(filePath);

        assertEquals(6, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day6();
        var result = sut.partA();

        assertEquals("5461", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day6();
        var result = sut.partB();

        assertEquals("1836", result);
    }
}
