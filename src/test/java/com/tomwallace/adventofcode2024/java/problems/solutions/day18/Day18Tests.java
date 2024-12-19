package com.tomwallace.adventofcode2024.java.problems.solutions.day18;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Tests {

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
}
