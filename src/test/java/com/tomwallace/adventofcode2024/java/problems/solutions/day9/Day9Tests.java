package com.tomwallace.adventofcode2024.java.problems.solutions.day9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Tests {

    @Test
    public void defragBlocksAndCalculateChecksum() {
        var input = "2333133121414131402";
        var inputChars = input.chars().mapToObj(c -> (char) c).toList();
        var sut = new Day9();
        var result = sut.defragBlocksAndCalculateChecksum(inputChars, false);

        assertEquals(1928, result);
    }

    @Test
    public void defragBlocksAndCalculateChecksum_UseFiles() {
        var input = "2333133121414131402";
        var inputChars = input.chars().mapToObj(c -> (char) c).toList();
        var sut = new Day9();
        var result = sut.defragBlocksAndCalculateChecksum(inputChars, true);

        assertEquals(2858, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day9();
        var result = sut.partA();

        assertEquals("6519155389266", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day9();
        var result = sut.partB();

        assertEquals("6547228115826", result);
    }
}
