package com.tomwallace.adventofcode2024.java.problems.solutions.day13;

import com.tomwallace.adventofcode2024.java.common.LongPoint;
import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.ArrayList;
import java.util.List;

public class Day13 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Claw Contraption";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 13; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day13Input.txt";
        var sum = sumFewestTokensNeeded(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day13Input.txt";
        var sum = sumFewestTokensNeededBig(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.MEDIUM;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }

    protected Long sumFewestTokensNeeded(String filePath) {
        var machines = createClawMachines(filePath, true);
        return machines.stream()
                .mapToLong(ClawMachine::findSmallestTokensByEquation)
                .sum();
    }

    protected Long sumFewestTokensNeededBig(String filePath) {
        var machines = createClawMachines(filePath, false);
        return machines.stream()
                .mapToLong(ClawMachine::findSmallestTokensByEquation)
                .sum();
    }

    private List<ClawMachine> createClawMachines(String filePath, Boolean isPartA) {
        var machines = new ArrayList<ClawMachine>();
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        for (int i = 0; i < lines.size(); i += 4) {
            var aLine = lines.get(i);
            var aX = Long.parseLong(aLine.split(",")[0].split("\\+")[1]);
            var aY = Long.parseLong(aLine.split(",")[1].split("\\+")[1]);
            var a = new LongPoint(aX, aY);

            var bLine = lines.get(i + 1);
            var bX = Long.parseLong(bLine.split(",")[0].split("\\+")[1]);
            var bY = Long.parseLong(bLine.split(",")[1].split("\\+")[1]);
            var b = new LongPoint(bX, bY);

            var targetLine = lines.get(i + 2);
            var targetX = Long.parseLong(targetLine.split(",")[0].split("=")[1]);
            var targetY = Long.parseLong(targetLine.split(",")[1].split("=")[1]);
            if (!isPartA) {
                targetX += 10000000000000L;
                targetY += 10000000000000L;
            }
            var target = new LongPoint(targetX, targetY);

            machines.add(new ClawMachine(a, b, target));
        }
        return machines;
    }
}



