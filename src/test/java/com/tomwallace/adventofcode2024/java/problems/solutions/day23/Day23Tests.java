package com.tomwallace.adventofcode2024.java.problems.solutions.day23;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day23Tests {

    @Test
    public void countConnectedComputers() {
        var filePath = FileUtility.testDataPath + "Day23TestInputA.txt";
        var sut = new Day23();
        var result = sut.countConnectedComputers(filePath);

        assertEquals(7, result);
    }

    @Test
    public void findBestComputerConnection() {
        var filePath = FileUtility.testDataPath + "Day23TestInputA.txt";
        var sut = new Day23();
        var result = sut.findBestComputerConnection(filePath);

        assertEquals("co,de,ka,ta", result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day23();
        var result = sut.partA();

        assertEquals("1062", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day23();
        var result = sut.partB();

        assertEquals("bz,cs,fx,ms,oz,po,sy,uh,uv,vw,xu,zj,zm", result);
    }
}
