package com.tomwallace.adventofcode2024.java.problems.solutions.day15;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.ArrayList;

public class Day15 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Warehouse Woes";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 15; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day15Input.txt";
        var total = sumGpsCoordinates(filePath);
        return total.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day14Input.txt";
        // var total = moveRobotsUntilChristmasTree(filePath, 100, 102, Integer.MAX_VALUE);
        return "-1";
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

    protected Integer sumGpsCoordinates(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var warehouseLines = new ArrayList<String>();
        var builder = new StringBuilder();
        var doneWithWarehouse = false;
        for (var line : lines) {
            if (line.isEmpty()) {
                doneWithWarehouse = true;
                continue;
            }
            if (!doneWithWarehouse) {
                warehouseLines.add(line);
            } else {
                builder.append(line.replace("\n", ""));
            }
        }
        var warehouse = new Warehouse(warehouseLines);
        for (Character inst : builder.toString().toCharArray()) {
            warehouse.moveRobot(inst);
            //warehouse.printGrid();
        }

        return warehouse.sumGpsCoordinates();
    }

    // TODO: TW - Still have to implement Part B
}



