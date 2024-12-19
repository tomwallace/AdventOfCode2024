package com.tomwallace.adventofcode2024.java.problems.solutions.day16;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Tests {

    @ParameterizedTest
    @CsvSource({"Day16TestInputA.txt,7036", "Day16TestInputB.txt,11048"})
    public void getLowestMazePoints(String input, Integer expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day16();
        var result = sut.getLowestMazePoints(filePath);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"Day16TestInputA.txt,45", "Day16TestInputB.txt,64"})
    public void countBestSeats(String input, Integer expected) {
        var filePath = FileUtility.testDataPath + input;
        var sut = new Day16();
        var result = sut.countBestSeats(filePath);

        assertEquals(expected, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day16();
        var result = sut.partA();

        assertEquals("109516", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day16();
        var result = sut.partB();

        assertEquals("568", result);
    }
}
