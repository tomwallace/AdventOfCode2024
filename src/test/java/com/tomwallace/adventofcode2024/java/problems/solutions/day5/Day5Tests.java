package com.tomwallace.adventofcode2024.java.problems.solutions.day5;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Day5Tests {

    @Test
    public void isSortedCorrectly_Success() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var rules = sut.getRules(filePath);
        var pages = List.of(75,47,61,53,29);
        var result = sut.isSortedCorrectly(pages, rules);

        assertTrue(result);
    }

    @Test
    public void isSortedCorrectly_Success_Two() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var rules = sut.getRules(filePath);
        var pages = List.of(97,75,47,61,53);
        var result = sut.isSortedCorrectly(pages, rules);

        assertTrue(result);
    }

    @Test
    public void isSortedCorrectly_Failure() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var rules = sut.getRules(filePath);
        var pages = List.of(75,97,47,61,53);
        var result = sut.isSortedCorrectly(pages, rules);

        assertFalse(result);
    }

    @Test
    public void sumMiddleValueCorrectUpdates() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var result = sut.sumMiddleValueUsingSorting(filePath);

        assertEquals(143, result);
    }

    @Test
    public void sumMiddleValueUsingSorting() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var result = sut.sumMiddleValueUsingSorting(filePath);

        assertEquals(143, result);
    }

    @Test
    public void sumMiddleValueUsingSortingFailed() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var result = sut.sumMiddleValueUsingSortingFailed(filePath);

        assertEquals(123, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day5();
        var result = sut.partA();

        assertEquals("5639", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day5();
        var result = sut.partB();

        assertEquals("5273", result);
    }
}
