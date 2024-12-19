package com.tomwallace.adventofcode2024.java.problems.solutions.day12;

import com.tomwallace.adventofcode2024.java.common.Point;
import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;
import com.tomwallace.adventofcode2024.java.utilities.GridUtility;
import lombok.SneakyThrows;

import java.util.*;

public class Day12 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Garden Groups";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 12; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day12Input.txt";
        var count = sumAllRegionFencingPrices(filePath, false);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day12Input.txt";
        var count = sumAllRegionFencingPrices(filePath, true);
        return count.toString();
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

    protected Long sumAllRegionFencingPrices(String filePath, Boolean useSides) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var sum = 0L;
        while (true) {
            var score = useSides ? calculateRegionScoreByFloodByFenceSide(grid) : calculateRegionScoreByFlood(grid);
            if (score == -1)
                break;
            sum += score;
        }
        return sum;
    }

    private Long calculateRegionScoreByFlood(List<List<Character>> grid) {
        var region = findRegion(grid);
        // Transfer exit condition up
        if (region == null)
            return -1L;

        // Calculate perimeter via flood
        var perimeter = region.size() * 4L;
        for (var point1 : region) {
            for (var point2 : region) {
                if (point1.x().equals(point2.x())) {
                    if (Math.abs(point1.y() - point2.y()) == 1)
                        perimeter--;
                }
                if (point1.y().equals(point2.y())) {
                    if (Math.abs(point1.x() - point2.x()) == 1)
                        perimeter--;
                }
            }
        }

        // Set all local region markers to symbol for taken by any region
        cleanGrid(grid);
        return region.size() * perimeter;
    }

    private Long calculateRegionScoreByFloodByFenceSide(List<List<Character>> grid) {
        var region = findRegion(grid);
        // Transfer exit condition up
        if (region == null)
            return -1L;

        var sides = countVerticalSides(region, grid) + countHorizontalSides(region, grid);

        // Set all local region markers to symbol for taken by any region
        cleanGrid(grid);
        return region.size() * sides;
    }

    private HashSet<Point> findRegion(List<List<Character>> grid) {
        var start = findUnusedPoint(grid);
        // Transfer exit condition out
        if (start.x() == -1)
            return null;
        var region = new HashSet<Point>();
        region.add(start);
        var target = grid.get(start.y()).get(start.x());
        grid.get(start.y()).set(start.x(), '*');
        while (true) {
            var newRegion = new HashSet<Point>();
            for (var point : region) {
                for (var neighbor : GridUtility.findOrthogonalNeighbours(point, grid)) {
                    if (grid.get(neighbor.y()).get(neighbor.x()) == target) {
                        grid.get(neighbor.y()).set(neighbor.x(), '*');
                        newRegion.add(neighbor);
                    }
                }
            }
            newRegion.addAll(region);
            // If there is no change in size, then we did not find any qualifying neighbors and can exit
            if (newRegion.size() == region.size())
                break;

            region = newRegion;
        }

        return region;
    }

    private Long countVerticalSides(HashSet<Point> region, List<List<Character>> grid) {
        var sides = 0L;
        var vert = new ArrayList<Integer>();
        for (int i = 0; i < grid.get(0).size(); i++){
            List<Integer> tmp = new ArrayList<>();
            for (var point: region){
                if (point.x() == i){
                    tmp.add((point.y() + 1) * -1);
                    tmp.add(point.y() + 2);
                }
            }
            var tmp2 = new ArrayList<Integer>();
            for (var in: tmp){
                if (!tmp.contains(-1 * in)){
                    tmp2.add(in);
                }
            }
            for (var in: tmp2){
                if (!vert.contains(in)){
                    sides++;
                }
            }
            vert = tmp2;
        }
        return sides;
    }

    private Long countHorizontalSides(HashSet<Point> region, List<List<Character>> grid) {
        var sides = 0L;
        var horiz = new ArrayList<Integer>();
        for (int i = 0; i < grid.size(); i++){
            var tmp = new ArrayList<Integer>();
            for (var point: region){
                if (point.y() == i){
                    tmp.add((point.x() + 1) * -1);
                    tmp.add(point.x() + 2);
                }
            }
            var tmp2 = new ArrayList<Integer>();
            for (var in: tmp){
                if (!tmp.contains(-1 * in)){
                    tmp2.add(in);
                }
            }
            for (var in: tmp2){
                if (!horiz.contains(in)){
                    sides++;
                }
            }
            horiz = tmp2;
        }
        return sides;
    }

    private void cleanGrid(List<List<Character>> grid) {
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (grid.get(y).get(x).equals('*'))
                    grid.get(y).set(x, '|');
            }
        }
    }

    // * = Used by this region
    // | = Used by previous regions
    private Point findUnusedPoint(List<List<Character>> grid) {
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (!grid.get(y).get(x).equals('|'))
                    return new Point(x, y);
            }
        }
        // Indicates we have gone through all the points, which is an exit condition
        return new Point(-1, -1);
    }

    protected Integer sumRegionFencingPrices(String filePath) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var totalPoints = grid.size() * grid.get(0).size();
        var usedPoints = new HashSet<Point>();
        var outsideSides = new HashMap<Point, Integer>();
        var regions = new ArrayList<Region>();
        while (usedPoints.size() < totalPoints) {
            var start = findFirstUnusedPoint(grid, usedPoints);
            var startLetter = grid.get(start.y()).get(start.x());
            var regionPoints = new HashSet<Point>();
            var queue = new LinkedList<Point>();
            queue.add(start);
            while (!queue.isEmpty()) {
                var current = queue.pop();
                regionPoints.add(current);
                usedPoints.add(current);
                var neighbors = GridUtility.findOrthogonalNeighbours(current, grid);
                outsideSides.put(current, (neighbors.stream()
                        .filter(n -> grid.get(n.y()).get(n.x()) != startLetter)
                        .toList()
                        .size()) + countOutsideEdges(current, grid)
                );
                queue.addAll(neighbors.stream()
                        .filter(n -> grid.get(n.y()).get(n.x()) == startLetter && !usedPoints.contains(n))
                        .toList()
                );
            }
            regions.add(new Region(startLetter, regionPoints));
        }

        var sum = 0;
        for (var region : regions) {
            var area = region.points.size();
            var perimeter = region.points.stream()
                    .map(outsideSides::get)
                    .mapToInt(Integer::intValue)
                    .sum();
            sum += area * perimeter;
        }
        return sum;
    }

    @SneakyThrows
    private Point findFirstUnusedPoint(List<List<Character>> grid, HashSet<Point> usedPoints) {
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (!usedPoints.contains(new Point(x, y)))
                    return new Point(x, y);
            }
        }
        throw new Exception("Should have found at least one unused point");
    }

    private Integer countOutsideEdges(Point point, List<List<Character>> grid) {
        var edges = 0;
        if (point.x() == 0)
            edges++;
        if (point.y() == 0)
            edges++;
        if (point.x() == grid.get(0).size() - 1)
            edges++;
        if (point.y() == grid.size() - 1)
            edges++;
        return edges;
    }

    protected record Region(Character id, HashSet<Point> points) {}
}



