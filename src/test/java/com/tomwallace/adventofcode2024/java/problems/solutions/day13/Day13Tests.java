package com.tomwallace.adventofcode2024.java.problems.solutions.day13;

import com.tomwallace.adventofcode2024.java.common.LongPoint;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Tests {

    @Test
    public void findSmallestTokens() {
        var sut = new ClawMachine(new LongPoint(94L, 34L), new LongPoint(22L, 67L), new LongPoint(8400L, 5400L));
        var result = sut.findSmallestTokens(100L);
        var result2 = sut.findSmallestTokensByEquation(100L);

        assertEquals(280, result);
        assertEquals(280, result2);
    }

    @Test
    public void findSmallestTokens_Not_Possible() {
        var sut = new ClawMachine(new LongPoint(26L, 66L), new LongPoint(67L, 21L), new LongPoint(12748L, 12176L));
        var result = sut.findSmallestTokens(100L);
        var result2 = sut.findSmallestTokensByEquation(100L);

        assertEquals(0, result);
        assertEquals(0, result2);
    }

    @Test
    public void sumFewestTokensNeeded() {
        var filePath = FileUtility.testDataPath + "Day13TestInputA.txt";
        var sut = new Day13();
        var result = sut.sumFewestTokensNeeded(filePath);

        assertEquals(480, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day13();
        var result = sut.partA();

        assertEquals("39290", result);
    }

    // TODO: TW - Still not getting Part B
    // 74609225581053 is too high
    @Test
    public void partB_Actual() {
        var sut = new Day13();
        var result = sut.partB();

        assertEquals("-1", result);
    }
}
