package com.tomwallace.adventofcode2024.java.problems.solutions.oldday1;

import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.ArrayList;
import java.util.Map;

import static java.util.Map.entry;

// Previous year's problem to test out project set up.
public class OldDay1 implements IAdventProblemSet {

    private Map<String, String> numbers = Map.ofEntries(
            entry("one", "1"),
            entry("two", "2"),
            entry("three", "3"),
            entry("four", "4"),
            entry("five", "5"),
            entry("six", "6"),
            entry("seven", "7"),
            entry("eight", "8"),
            entry("nine", "9")
    );

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Trebuchet?!";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 99;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "OldDay1Input.txt";
        var sum = sumLinesOfFirstAndLastDigits(filePath, false);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "OldDay1Input.txt";
        var sum = sumLinesOfFirstAndLastDigits(filePath, true);
        return sum.toString();
    }

    protected Integer sumLinesOfFirstAndLastDigits(String filePath, Boolean useWords) {
        var lines = FileUtility.parseFileToList(filePath, line -> useWords ? processLineAsWords(line) : processLine(line));
        return lines.stream().mapToInt(Integer::intValue).sum();
    }

    protected Integer processLine(String line) {
        var digits = new ArrayList<Character>();
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                digits.add(line.charAt(i));
            }
        }
        return Integer.parseInt(String.format("%c%c", digits.get(0), digits.get(digits.size() - 1)));
    }

    protected Integer processLineAsWords(String line) {
        int lowestIndex = 99999;
        var lowestNum = "";
        int highestIndex = -1;
        var highestNum = "";
        var lineChars = line.toCharArray();

        for( int i = 0; i < line.length(); i++ )
        {
            if (!Character.isDigit(lineChars[i]))
                continue;

            if (i <= lowestIndex)
            {
                lowestIndex = i;
                lowestNum = String.valueOf(lineChars[i]);
            }

            if (i >= highestIndex)
            {
                highestIndex = i;
                highestNum = String.valueOf(lineChars[i]);
            }
        }
        for (var key : numbers.keySet()) {
            var indx = line.indexOf(key);
            var value = numbers.get(key);
            if (indx == -1)
                continue;

            if (indx <= lowestIndex)
            {
                lowestIndex = indx;
                lowestNum = value;
            }

            var highIndx = line.lastIndexOf(key);
            if (highIndx >= highestIndex)
            {
                highestIndex = highIndx;
                highestNum = value;
            }

        }

        return Integer.parseInt(String.format("%s%s", lowestNum, highestNum));//  "{lowestNum}{highestNum}");
    }
}
