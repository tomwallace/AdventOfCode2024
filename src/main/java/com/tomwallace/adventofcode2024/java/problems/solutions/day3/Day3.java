package com.tomwallace.adventofcode2024.java.problems.solutions.day3;

import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.regex.Pattern;

public class Day3 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Mull It Over";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 3;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day3Input.txt";
        var input = String.join("", FileUtility.parseFileToList(filePath, line -> line));
        long total = sumAllMulMatchingPairs(input);
        return String.format("%d", total);
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day3Input.txt";
        var input = String.join("", FileUtility.parseFileToList(filePath, line -> line));
        long total = sumAllMulMatchingPairsOnOff(input);
        return String.format("%d", total);
    }

    protected Long sumAllMulMatchingPairs(String input) {
        long total = 0;
        var pattern = Pattern.compile("(?<=mul\\()(\\d+,\\d+)(?=\\))");
        var matcher = pattern.matcher(input);
        while (matcher.find()) {
            var matchedString = matcher.group();
            var split = matchedString.split(",");
            total += (Long.parseLong(split[0]) * Long.parseLong(split[1]));
        }
        return total;
    }

    // Provide for ability to turn matching on with "do()" and off with "don't()"
    protected Long sumAllMulMatchingPairsOnOff(String input) {
        long total = 0;
        var on = true;
        var pattern = Pattern.compile("((do\\(\\))|(don't\\(\\))|(?<=mul\\()(\\d+,\\d+)(?=\\)))");
        var matcher = pattern.matcher(input);
        while (matcher.find()) {
            var matchedString = matcher.group();
            if (matchedString.equals("do()")) {
                on = true;
                continue;
            }
            if (matchedString.equals("don't()")) {
                on = false;
                continue;
            }
            if (on) {
                var split = matchedString.split(",");
                total += (Long.parseLong(split[0]) * Long.parseLong(split[1]));
            }
        }
        return total;
    }
}

