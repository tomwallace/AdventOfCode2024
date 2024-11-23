package com.tomwallace.adventofcode2024.java.problems.solutions;

import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldDay1Tests {

    @ParameterizedTest
    @CsvSource({"1abc2,12", "pqr3stu8vwx,38", "a1b2c3d4e5f,15", "treb7uchet,77"})
    public void processLine(String input, Integer expected) {
        var sut = new OldDay1();
        var result = sut.processLine(input);

        assertEquals(expected, result);
    }

    @Test
    public void sumLinesOfFirstAndLastDigits() {
        var filePath = FileUtility.testDataPath + "OldDay1TestInputA.txt";
        var sut = new OldDay1();
        var result = sut.sumLinesOfFirstAndLastDigits(filePath, false);

        assertEquals(142, result);
    }

    @ParameterizedTest
    @CsvSource({"two1nine,29", "eightwothree,83", "abcone2threexyz,13", "7one718onegfqtdbtxfcmd,71"})
    public void processLineAsWords(String input, Integer expected) {
        var sut = new OldDay1();
        var result = sut.processLineAsWords(input);

        assertEquals(expected, result);
    }

    @Test
    public void sumLinesOfFirstAndLastDigits_WithWords() {
        var filePath = FileUtility.testDataPath + "OldDay1TestInputB.txt";
        var sut = new OldDay1();
        var result = sut.sumLinesOfFirstAndLastDigits(filePath, true);

        assertEquals(281, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new OldDay1();
        var result = sut.partA();

        assertEquals("55108", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new OldDay1();
        var result = sut.partB();

        assertEquals("56324", result);
    }
}
