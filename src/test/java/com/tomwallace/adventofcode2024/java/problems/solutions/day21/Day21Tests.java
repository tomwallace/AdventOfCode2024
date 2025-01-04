package com.tomwallace.adventofcode2024.java.problems.solutions.day21;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day21Tests {

    @Test
    public void findSequencesRecursive_Key() {
        var sut = new Day21();
        var result = sut.findSequencesRecursive("029A", Day21.KEY_PAD_START, "", Day21.KEY_PAD);

        assertNotNull(result);
        assertTrue(result.contains("<A^A>^^AvvvA"));
        assertTrue(result.contains("<A^A^^>AvvvA"));
        assertEquals(3 ,result.size());
    }

    @Test
    public void findComplexityOfCode() {
        var sut = new Day21();
        var result = sut.findComplexityOfCode("029A", 2);

        assertEquals((68 * 29), result);
    }

    @Test
    public void sumComplexitiesOfCodes_2() {
        var filePath = FileUtility.testDataPath + "Day21TestInputA.txt";
        var sut = new Day21();
        var result = sut.sumComplexitiesOfCodes(filePath, 2);

        assertEquals(126384, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day21();
        var result = sut.partA();

        assertEquals("246990", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day21();
        var result = sut.partB();

        assertEquals("306335137543664", result);
    }
}
