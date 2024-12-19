package com.tomwallace.adventofcode2024.java.problems.solutions.day14;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Tests {

    @Test
    public void moveRobot() {
        var input = "p=2,4 v=2,-3";
        var map = new Map(10, 6);
        var robot = map.parseLine(input);
        var result = map.moveRobot(robot);
        assertEquals(new Point(4,1), result.location());
        result = map.moveRobot(result);
        assertEquals(new Point(6,5), result.location());
        result = map.moveRobot(result);
        assertEquals(new Point(8,2), result.location());
        result = map.moveRobot(result);
        assertEquals(new Point(10,6), result.location());
        result = map.moveRobot(result);
        assertEquals(new Point(1,3), result.location());
    }

    @Test
    public void moveRobotsAndGetSafetyFactor() {
        var filePath = FileUtility.testDataPath + "Day14TestInputA.txt";
        var sut = new Day14();
        var result = sut.moveRobotsAndGetSafetyFactor(filePath, 10, 6, 100);

        assertEquals(12, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day14();
        var result = sut.partA();

        assertEquals("221655456", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day14();
        var result = sut.partB();

        assertEquals("7858", result);
    }
}
