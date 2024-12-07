package com.tomwallace.adventofcode2024.java.problems.solutions.day7;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Tests {

    @ParameterizedTest
    @CsvSource({"190: 10 19,true", "3267: 81 40 27,true", "83: 17 5,false", "156: 15 6, false", "7290: 6 8 6 15,false", "292: 11 6 16 20,true"})
    public void isPossible(String input, Boolean expected) {
        var sut = new Equation(input, false);
        var result = sut.isPossible();

        assertEquals(expected, result);
    }

    @Test
    public void isPossible_UseConcat() {
        var input = "7290: 6 8 6 15";
        var expected = true;
        var sut = new Equation(input, true);
        var result = sut.isPossible();

        assertEquals(expected, result);
    }

    @Test
    public void sumPossibleEquations() {
        var filePath = FileUtility.testDataPath + "Day7TestInputA.txt";
        var sut = new Day7();
        var result = sut.sumPossibleEquations(filePath, false);

        assertEquals(3749, result);
    }

    @Test
    public void sumPossibleEquations_UseConcat() {
        var filePath = FileUtility.testDataPath + "Day7TestInputA.txt";
        var sut = new Day7();
        var result = sut.sumPossibleEquations(filePath, true);

        assertEquals(11387, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day7();
        var result = sut.partA();

        assertEquals("7710205485870", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day7();
        var result = sut.partB();

        assertEquals("20928985450275", result);
    }
}
