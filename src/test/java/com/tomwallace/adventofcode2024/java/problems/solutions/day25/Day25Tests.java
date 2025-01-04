package com.tomwallace.adventofcode2024.java.problems.solutions.day25;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Tests {

    @Test
    public void countFittingKeyLockPairs() {
        var filePath = FileUtility.testDataPath + "Day25TestInputA.txt";
        var sut = new Day25();
        var result = sut.countFittingKeyLockPairs(filePath);

        assertEquals(3, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day25();
        var result = sut.partA();

        assertEquals("3264", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day25();
        var result = sut.partB();

        assertEquals("Need all 49 other stars!", result);
    }
}
