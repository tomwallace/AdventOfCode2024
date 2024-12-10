package com.tomwallace.adventofcode2024.java.problems.solutions.day8;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Antenna {
    private Point point;
    private Character freq;

    public List<Point> findAntiNodes(Antenna other, Integer gridMaxX, Integer gridMaxY) {
        var antiNodes = new ArrayList<Point>();
        // Check to see that the antennae have the same frequency
        if (!freq.equals(other.freq))
            throw new IllegalArgumentException(String.format("Other frequency %s does not match my frequency of %s", other.freq, freq));
        var diffX = Math.abs(point.x() - other.point.x());
        var diffY = Math.abs(point.y() - other.point.y());
        var minX = Math.min(point.x(), other.point.x()) - diffX;
        var maxX = Math.max(point.x(), other.point.x()) + diffX;
        var minY = Math.min(point.y(), other.point.y()) - diffY;
        var maxY = Math.max(point.y(), other.point.y()) + diffY;
        // downward slope or flat
        if (point.x() <= other.point.x() && point.y() <= other.point.y()) {
            addAntiNodeIfInBounds(antiNodes, new Point(minX, minY), gridMaxX, gridMaxY);
            addAntiNodeIfInBounds(antiNodes, new Point(maxX, maxY), gridMaxX, gridMaxY);
            return antiNodes;
        }
        // upward slope
        addAntiNodeIfInBounds(antiNodes, new Point(minX, maxY), gridMaxX, gridMaxY);
        addAntiNodeIfInBounds(antiNodes, new Point(maxX, minY), gridMaxX, gridMaxY);
        return antiNodes;
    }

    public List<Point> findAntiNodesWithResonance(Antenna other, Integer gridMaxX, Integer gridMaxY) {
        var antiNodes = new ArrayList<Point>();
        // Check to see that the antennae have the same frequency
        if (!freq.equals(other.freq))
            throw new IllegalArgumentException(String.format("Other frequency %s does not match my frequency of %s", other.freq, freq));
        // With resonance, we automatically add both antenna points
        antiNodes.add(new Point(point.x(), point.y()));
        antiNodes.add(new Point(other.point.x(), other.point.y()));
        var keepGoing = true;
        var multiplier = 1;
        while (keepGoing) {
            var diffX = (Math.abs(point.x() - other.point.x())) * multiplier;
            var diffY = (Math.abs(point.y() - other.point.y())) * multiplier;
            var minX = Math.min(point.x(), other.point.x()) - diffX;
            var maxX = Math.max(point.x(), other.point.x()) + diffX;
            var minY = Math.min(point.y(), other.point.y()) - diffY;
            var maxY = Math.max(point.y(), other.point.y()) + diffY;
            // downward slope or flat
            if (point.x() <= other.point.x() && point.y() <= other.point.y()) {
                addAntiNodeIfInBounds(antiNodes, new Point(minX, minY), gridMaxX, gridMaxY);
                addAntiNodeIfInBounds(antiNodes, new Point(maxX, maxY), gridMaxX, gridMaxY);
                if (!isPointInBounds(new Point(minX, minY), gridMaxX, gridMaxY) && !isPointInBounds(new Point(maxX, maxY), gridMaxX, gridMaxY))
                    keepGoing = false;
            } else {
                // upward slope
                addAntiNodeIfInBounds(antiNodes, new Point(minX, maxY), gridMaxX, gridMaxY);
                addAntiNodeIfInBounds(antiNodes, new Point(maxX, minY), gridMaxX, gridMaxY);
                if (!isPointInBounds(new Point(minX, maxY), gridMaxX, gridMaxY) && !isPointInBounds(new Point(maxX, minY), gridMaxX, gridMaxY))
                    keepGoing = false;
            }
            multiplier++;
        }

        return antiNodes;
    }

    // TODO: Handle Part B

    private void addAntiNodeIfInBounds(List<Point> antiNodes, Point potentialPoint, Integer gridMaxX, Integer gridMaxY) {
        if (isPointInBounds(potentialPoint, gridMaxX, gridMaxY))
            antiNodes.add(new Point(potentialPoint.x(), potentialPoint.y()));
    }

    private Boolean isPointInBounds(Point potentialPoint, Integer gridMaxX, Integer gridMaxY) {
        return potentialPoint.x() >= 0 && potentialPoint.x() < gridMaxX && potentialPoint.y() >= 0 && potentialPoint.y() < gridMaxY;
    }
}
