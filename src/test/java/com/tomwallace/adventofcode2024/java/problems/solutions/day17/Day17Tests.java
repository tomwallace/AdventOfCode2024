package com.tomwallace.adventofcode2024.java.problems.solutions.day17;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Tests {

    @Test
    public void computerRun1() {
        var program = List.of(2L,6L);
        var sut = new Computer(program);
        sut.getRegisters().put("A", 0L);
        sut.getRegisters().put("B", 0L);
        sut.getRegisters().put("C", 9L);
        sut.run();

        assertEquals(1L, sut.getRegisters().get("B"));
    }

    @Test
    public void computerRun2() {
        var program = List.of(5L,0L,5L,1L,5L,4L);
        var sut = new Computer(program);
        sut.getRegisters().put("A", 10L);
        sut.getRegisters().put("B", 0L);
        sut.getRegisters().put("C", 0L);
        sut.run();

        assertEquals("0,1,2", sut.findOutput());
    }

    @Test
    public void computerRun3() {
        var program = List.of(0L,1L,5L,4L,3L,0L);
        var sut = new Computer(program);
        sut.getRegisters().put("A", 2024L);
        sut.getRegisters().put("B", 0L);
        sut.getRegisters().put("C", 0L);
        sut.run();

        assertEquals("4,2,5,6,7,7,7,7,3,1,0", sut.findOutput());
        assertEquals(0, sut.getRegisters().get("A"));
    }

    @Test
    public void computerRun4() {
        var program = List.of(1L,7L);
        var sut = new Computer(program);
        sut.getRegisters().put("A", 0L);
        sut.getRegisters().put("B", 29L);
        sut.getRegisters().put("C", 0L);
        sut.run();

        assertEquals(26L, sut.getRegisters().get("B"));
    }

    @Test
    public void computerRun5() {
        var program = List.of(4L,0L);
        var sut = new Computer(program);
        sut.getRegisters().put("A", 0L);
        sut.getRegisters().put("B", 2024L);
        sut.getRegisters().put("C", 43690L);
        sut.run();

        assertEquals(44354L, sut.getRegisters().get("B"));
    }

    @Test
    public void computerFindOutput() {
        var filePath = FileUtility.testDataPath + "Day17TestInputA.txt";
        var sut = new Computer(filePath);
        sut.run();
        var result = sut.findOutput();

        assertEquals("4,6,3,5,6,3,5,2,1,0", result);
    }

    @Test
    public void findLowestRegisterA() {
        var filePath = FileUtility.testDataPath + "Day17TestInputB.txt";
        var sut = new Day17();
        var result = sut.findLowestRegisterA(filePath);

        assertEquals(117440L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day17();
        var result = sut.partA();

        assertEquals("3,6,7,0,5,7,3,1,4", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day17();
        var result = sut.partB();

        assertEquals("164278496489149", result);
    }
}
