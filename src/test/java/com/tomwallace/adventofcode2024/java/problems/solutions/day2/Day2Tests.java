package com.tomwallace.adventofcode2024.java.problems.solutions.day2;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Tests {

    @ParameterizedTest
    @CsvSource({"7 6 4 2 1,true", "1 2 7 8 9,false", "9 7 6 2 1,false", "1 3 2 4 5, false", "8 6 4 4 1,false", "1 3 6 7 9,true"})
    public void reportIsSafe(String input, Boolean expected) {
        var sut = new Report(input);

        assertEquals(expected, sut.isSafe());
    }

    @Test
    public void countSafeReports() {
        var filePath = FileUtility.testDataPath + "Day2TestInputA.txt";
        var sut = new Day2();
        var result = sut.countSafeReports(filePath);

        assertEquals(2, result);
    }

    @ParameterizedTest
    @CsvSource({"7 6 4 2 1,true", "1 2 7 8 9,false", "9 7 6 2 1,false", "1 3 2 4 5, true", "8 6 4 4 1,true", "1 3 6 7 9,true", "80 82 85 86 87 90 94, true"})
    public void reportIsSafeProblemDampened(String input, Boolean expected) {
        var sut = new Report(input);

        assertEquals(expected, sut.isSafeProblemDampened());
    }

    @Test
    public void countSafeReportsProblemDampened() {
        var filePath = FileUtility.testDataPath + "Day2TestInputA.txt";
        var sut = new Day2();
        var result = sut.countSafeReportsProblemDampened(filePath);

        assertEquals(4, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day2();
        var result = sut.partA();

        assertEquals("282", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day2();
        var result = sut.partB();

        assertEquals("349", result);
    }
}
