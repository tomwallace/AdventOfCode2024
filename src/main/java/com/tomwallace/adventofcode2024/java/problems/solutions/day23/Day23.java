package com.tomwallace.adventofcode2024.java.problems.solutions.day23;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.*;
import java.util.stream.Collectors;

public class Day23 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "LAN Party";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 23;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day23Input.txt";
        var count = countConnectedComputers(filePath);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day23Input.txt";
        return findBestComputerConnection(filePath);
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.HARD;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }

    protected Integer countConnectedComputers(String filePath) {
        var connectionMap = makeConnectionMap(filePath);

        var connections = new HashSet<String>();
        for (var from : connectionMap.keySet()) {
            connections.addAll(findGroupsOfThree(from, connectionMap));
        }
        return connections.size();
    }

    protected String findBestComputerConnection(String filePath) {
        var connectionMap = makeConnectionMap(filePath);
        var bestComputers = findBestConnectionRecursive(new HashSet<>(), connectionMap.keySet(), new HashSet<>(), 0, connectionMap);
        return computersToString(bestComputers);
    }

    private HashSet<String> findGroupsOfThree(String from, HashMap<String, Set<String>> connectionMap) {
        var result = new HashSet<String>();
        var connected = connectionMap.get(from).stream().toList();

        if (connected.size() > 1) {
            for (int i = 0; i < connected.size() - 1; ++i) {
                for (int j = i + 1; j < connected.size(); ++j) {
                    String to1 = connected.get(i);
                    String to2 = connected.get(j);
                    if (!to1.startsWith("t") && !to2.startsWith("t") && !from.startsWith("t"))
                        continue;
                    if (!connectionMap.get(to1).contains(to2))
                        continue;
                    String group = computersToString(List.of(from, to1, to2));
                    result.add(group);
                }
            }
        }
        return result;
    }

    private HashMap<String, Set<String>> makeConnectionMap(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var connectionMap = new HashMap<String, Set<String>>();
        for (var line : lines) {
            var split = line.split("-");
            if (!connectionMap.containsKey(split[0])) {
                var list = new HashSet<String>();
                list.add(split[1]);
                connectionMap.put(split[0], list);
            } else {
                connectionMap.get(split[0]).add(split[1]);
            }

            if (!connectionMap.containsKey(split[1])) {
                var list = new HashSet<String>();
                list.add(split[0]);
                connectionMap.put(split[1], list);
            } else {
                connectionMap.get(split[1]).add(split[0]);
            }
        }
        return connectionMap;
    }

    private String computersToString(List<String> computers) {
        return computers.stream()
                .sorted()
                .collect(Collectors.joining(","));
    }

    private String computersToString(Set<String> computers) {
        return computers.stream()
                .sorted()
                .collect(Collectors.joining(","));
    }

    private Set<String> findBestConnectionRecursive(Set<String> computers, Set<String> candidates, Set<String> visited, Integer bestSize, final HashMap<String, Set<String>> connectionMap) {
        // Bron-Kerbosch's algorithm
        // Exit conditions
        if (computers.size() + candidates.size() <= bestSize)
            return computers;

        if (candidates.isEmpty())
            return computers;

        var bestComputers = computers;
        var newVisited = new HashSet<>(visited);
        for (var candidate : candidates) {
            if (!newVisited.add(candidate)) {
                continue;
            }
            var connected = connectionMap.get(candidate);
            if (!connected.containsAll(computers)) {
                continue;
            }
            var newComputers = new HashSet<>(computers);
            newComputers.add(candidate);
            var newCandidates = new HashSet<>(candidates);
            newCandidates.remove(candidate);
            newCandidates.retainAll(connected);

            var tempComputers = findBestConnectionRecursive(newComputers, newCandidates, newVisited, Math.max(bestSize, bestComputers.size()), connectionMap);
            if (tempComputers.size() > bestComputers.size()) {
                bestComputers = tempComputers;
            }
        }
        return bestComputers;
    }
}
