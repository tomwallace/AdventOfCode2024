package com.tomwallace.adventofcode2024.java.problems.solutions.day20;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Tests {

    @ParameterizedTest
    @CsvSource({"12,8", "20,5", "38,3"})
    public void sumListDifference(Integer input, Integer expected) {
        var filePath = FileUtility.testDataPath + "Day20TestInputA.txt";
        var sut = new Race(filePath);
        var result = sut.countSuccessfulCheatsByPath(input, 2);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"70,41", "72,29", "76,3"})
    public void sumListDifference_Bigger(Integer input, Integer expected) {
        var filePath = FileUtility.testDataPath + "Day20TestInputA.txt";
        var sut = new Race(filePath);
        var result = sut.countSuccessfulCheatsByPath(input, 20);

        assertEquals(expected, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day20();
        var result = sut.partA();

        assertEquals("1321", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day20();
        var result = sut.partB();

        assertEquals("971737", result);
    }
}
