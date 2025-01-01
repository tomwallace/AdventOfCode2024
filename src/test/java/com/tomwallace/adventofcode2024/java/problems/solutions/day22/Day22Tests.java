package com.tomwallace.adventofcode2024.java.problems.solutions.day22;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Tests {

    @Test
    public void mix() {
        var sut = new Day22();
        var result = sut.mix(42L, 15L);

        assertEquals(37L, result);
    }

    @Test
    public void prune() {
        var sut = new Day22();
        var result = sut.prune(100000000L);

        assertEquals(16113920L, result);
    }

    @ParameterizedTest
    @CsvSource({"123,1,15887950", "123,2,16495136", "123,10,5908254"})
    public void regenerateSecretMultipleTimes(Long initial, Integer generations, Long expected) {
        var sut = new Day22();
        var result = sut.regenerateSecretMultipleTimes(initial, generations);

        assertEquals(expected, result);
    }

    @Test
    public void sumRegeneratedSecrets() {
        var filePath = FileUtility.testDataPath + "Day22TestInputA.txt";
        var sut = new Day22();
        var result = sut.sumRegeneratedSecrets(filePath);

        assertEquals(37327623, result);
    }

    @Test
    public void findPricesSecretMultipleTimes() {
        var sut = new Day22();
        var result = sut.findPricesSecretMultipleTimes(123L);

        assertEquals(3, result.get(0));
        assertEquals(0, result.get(1));
        assertEquals(6, result.get(2));
        assertEquals(5, result.get(3));
        assertEquals(4, result.get(4));
    }

    @Test
    public void getMostBananas() {
        var filePath = FileUtility.testDataPath + "Day22TestInputB.txt";
        var sut = new Day22();
        var result = sut.getMostBananas(filePath);

        assertEquals(23, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day22();
        var result = sut.partA();

        assertEquals("20506453102", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day22();
        var result = sut.partB();

        assertEquals("2423", result);
    }
}
