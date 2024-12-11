package com.tomwallace.adventofcode2024.java.problems.solutions.day8;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day8 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Resonant Collinearity";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 8; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day8Input.txt";
        var count = countUniqueAntiNodeLocations(filePath, false);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day8Input.txt";
        var count = countUniqueAntiNodeLocations(filePath, true);
        return count.toString();
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
        return true;
    }

    protected Integer countUniqueAntiNodeLocations(String filePath, Boolean useResonance) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var antennae = new ArrayList<Antenna>();
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (!grid.get(y).get(x).equals('.')) {
                    antennae.add(new Antenna(new Point(x, y), grid.get(y).get(x)));
                }
            }
        }

        var maxX = grid.get(0).size();
        var maxY = grid.size();
        var groups = antennae.stream()
                .collect(Collectors.groupingBy(Antenna::getFreq));
        var antiNodes = new HashSet<Point>();
        groups.forEach((freq, antennaList) -> {
            var pairs = findAllPairs(antennaList);
            pairs.forEach(pair -> antiNodes.addAll(useResonance ? pair.left().findAntiNodesWithResonance(pair.right(), maxX, maxY) : pair.left().findAntiNodes(pair.right(), maxX, maxY)));
        });

        return antiNodes.size();
    }

    private List<Pair> findAllPairs(List<Antenna> antennas) {
        return IntStream.range(0, antennas.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, antennas.size())
                        .mapToObj(j -> new Pair(antennas.get(i), antennas.get(j))))
                .collect(Collectors.toList());
    }
}

