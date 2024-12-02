package com.tomwallace.adventofcode2024.java.problems.solutions.day2;

import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

public class Day2 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Red-Nosed Reports";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 2;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day2Input.txt";
        var count = countSafeReports(filePath);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day2Input.txt";
        var count = countSafeReportsProblemDampened(filePath);
        return count.toString();
    }

    protected Long countSafeReports(String filePath) {
        var reports = FileUtility.parseFileToList(filePath, Report::new);
        return reports.stream()
                .filter(Report::isSafe)
                .count();
    }

    protected Long countSafeReportsProblemDampened(String filePath) {
        var reports = FileUtility.parseFileToList(filePath, Report::new);
        return reports.stream()
                .filter(Report::isSafeProblemDampened)
                .count();
    }
}

