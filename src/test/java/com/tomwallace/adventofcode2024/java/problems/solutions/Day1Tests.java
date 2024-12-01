package com.tomwallace.adventofcode2024.java.problems.solutions;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Tests {

    @Test
    public void sumListDifference() {
        var filePath = FileUtility.testDataPath + "Day1TestInputA.txt";
        var sut = new Day1();
        var result = sut.sumListDifference(filePath);

        assertEquals(11, result);
    }

    @Test
    public void calculateSimilarityScore() {
        var filePath = FileUtility.testDataPath + "Day1TestInputA.txt";
        var sut = new Day1();
        var result = sut.calculateSimilarityScore(filePath);

        assertEquals(31, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day1();
        var result = sut.partA();

        assertEquals("2375403", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day1();
        var result = sut.partB();

        assertEquals("23082277", result);
    }
}
