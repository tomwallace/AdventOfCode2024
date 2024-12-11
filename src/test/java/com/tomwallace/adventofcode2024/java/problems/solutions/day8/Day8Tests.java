package com.tomwallace.adventofcode2024.java.problems.solutions.day8;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day8Tests {

    @Test
    public void findAntiNodes_Downward() {
        var one = new Antenna(new Point(4,3), 'a');
        var two = new Antenna(new Point(5,5), 'a');
        var result = one.findAntiNodes(two,12, 12);

        assertEquals(2, result.size());
        assertTrue(result.contains(new Point(3,1)));
        assertTrue(result.contains(new Point(6,7)));
    }

    @Test
    public void findAntiNodes_Upward() {
        var one = new Antenna(new Point(5,5), 'a');
        var two = new Antenna(new Point(8,4), 'a');
        var result = one.findAntiNodes(two, 12, 12);

        assertEquals(2, result.size());
        assertTrue(result.contains(new Point(2,6)));
        assertTrue(result.contains(new Point(11,3)));
    }

    @Test
    public void findAntiNodes_Vertical() {
        var one = new Antenna(new Point(5,5), 'a');
        var two = new Antenna(new Point(5,8), 'a');
        var result = one.findAntiNodes(two, 12, 12);

        assertEquals(2, result.size());
        assertTrue(result.contains(new Point(5,2)));
        assertTrue(result.contains(new Point(5,11)));
    }

    @Test
    public void findAntiNodes_Horizontal() {
        var one = new Antenna(new Point(3,5), 'a');
        var two = new Antenna(new Point(5,5), 'a');
        var result = one.findAntiNodes(two, 12, 12);

        assertEquals(2, result.size());
        assertTrue(result.contains(new Point(1,5)));
        assertTrue(result.contains(new Point(7,5)));
    }

    @Test
    public void findAntiNodesWithResonance() {
        var one = new Antenna(new Point(0,0), 'T');
        var two = new Antenna(new Point(1,2), 'T');
        var result = one.findAntiNodesWithResonance(two,9, 9);

        assertEquals(5, result.size());
        assertTrue(result.contains(new Point(0,0)));
        assertTrue(result.contains(new Point(1,2)));
        assertTrue(result.contains(new Point(2,4)));
        assertTrue(result.contains(new Point(3,6)));
        assertTrue(result.contains(new Point(4,8)));
    }

    @Test
    public void countUniqueAntiNodeLocations() {
        var filePath = FileUtility.testDataPath + "Day8TestInputA.txt";
        var sut = new Day8();
        var result = sut.countUniqueAntiNodeLocations(filePath, false);

        assertEquals(14, result);
    }

    @Test
    public void countUniqueAntiNodeLocations_WithResonance() {
        var filePath = FileUtility.testDataPath + "Day8TestInputA.txt";
        var sut = new Day8();
        var result = sut.countUniqueAntiNodeLocations(filePath, true);

        assertEquals(34, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day8();
        var result = sut.partA();

        assertEquals("278", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day8();
        var result = sut.partB();

        assertEquals("1067", result);
    }
}
