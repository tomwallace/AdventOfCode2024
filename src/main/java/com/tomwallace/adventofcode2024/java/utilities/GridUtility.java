package com.tomwallace.adventofcode2024.java.utilities;

import com.tomwallace.adventofcode2024.java.common.Point;

import java.util.List;

public class GridUtility {

    /***
     * Returns a list of Points for neighbors in orthogonal directions (no diagonals) that are in-bounds
     * of the grid.
     * @param current - current point to find neighbors for
     * @param grid - grid used to establish boundaries
     * @return - collection of orthogonal neighbor points
     */
    public static List<Point> findOrthogonalNeighbours(Point current, final List<List<Character>> grid) {
        final var mods = List.of(new Point(0,-1), new Point(1,0), new Point(0, 1), new Point(-1, 0));
        return mods.stream()
                .map(mod -> new Point(current.x() + mod.x(), current.y() + mod.y()))
                .filter(p -> isPointInBounds(p, grid))
                .toList();
    }

    /***
     * Returns a list of Points for neighbors in orthogonal directions (no diagonals) that are in-bounds
     * of the grid.
     * @param current - current point to find neighbors for
     * @param maxX - max x valye in the grid
     * @param maxY - max y value in the grid
     * @return - collection of orthogonal neighbor points
     */
    public static List<Point> findOrthogonalNeighbours(Point current, final Integer maxX, Integer maxY) {
        final var mods = List.of(new Point(0,-1), new Point(1,0), new Point(0, 1), new Point(-1, 0));
        return mods.stream()
                .map(mod -> new Point(current.x() + mod.x(), current.y() + mod.y()))
                .filter(p -> isPointInBounds(p, maxX, maxY))
                .toList();
    }

    /***
     * Returns a list of Points for neighbors in all directions (including diagonals) that are in-bounds
     * of the grid.
     * @param current - current point to find neighbors for
     * @param grid - grid used to establish boundaries
     * @return - collection of orthogonal neighbor points
     */
    public static List<Point> findAllNeighbours(Point current, final List<List<Character>> grid) {
        final var mods = List.of(new Point(-1,-1), new Point(0,-1), new Point(1,-1), new Point(1,0), new Point(1,1), new Point(0, 1), new Point(-1, 1), new Point(-1, 0));
        return mods.stream()
                .map(mod -> new Point(current.x() + mod.x(), current.y() + mod.y()))
                .filter(p -> isPointInBounds(p, grid))
                .toList();
    }

    /***
     * Determines if the passed point is in-bounds of the grid
     * @param point - point to evaluate
     * @param grid - grid used to establish boundaries
     * @return - Boolean if the point is in-bounds
     */
    public static Boolean isPointInBounds(Point point, final List<List<Character>> grid) {
        return point.x() >= 0 && point.x() < grid.get(0).size() && point.y() >= 0 && point.y() < grid.size();
    }

    /***
     * Determines if the passed point is in-bounds of the grid
     * @param point - point to evaluate
     * @param maxX - max x valye in the grid
     * @param maxY - max y value in the grid
     * @return - Boolean if the point is in-bounds
     */
    public static Boolean isPointInBounds(Point point, final Integer maxX, Integer maxY) {
        return point.x() >= 0 && point.x() < maxX && point.y() >= 0 && point.y() < maxY;
    }
}
