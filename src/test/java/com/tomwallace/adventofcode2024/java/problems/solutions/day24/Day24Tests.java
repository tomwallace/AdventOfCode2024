package com.tomwallace.adventofcode2024.java.problems.solutions.day24;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24Tests {

    @Test
    public void findOutputOfZWires() {
        var filePath = FileUtility.testDataPath + "Day24TestInputA.txt";
        var sut = new Day24();
        var result = sut.findOutputOfZWires(filePath);

        assertEquals(4L, result);
    }

    @Test
    public void findOutputOfZWires_Big() {
        var filePath = FileUtility.testDataPath + "Day24TestInputB.txt";
        var sut = new Day24();
        var result = sut.findOutputOfZWires(filePath);

        assertEquals(2024L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day24();
        var result = sut.partA();

        assertEquals("50411513338638", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day24();
        var result = sut.partB();

        assertEquals("gfv,hcm,kfs,tqm,vwr,z06,z11,z16", result);
    }
}
