package com.tomwallace.adventofcode2024.java.problems.solutions.day25;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.ArrayList;
import java.util.List;

public class Day25 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Code Chronicle";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 25;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day25Input.txt";
        var count = countFittingKeyLockPairs(filePath);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        return "Need all 49 other stars!";
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.EASY;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return true;
    }

    protected Integer countFittingKeyLockPairs(String filePath) {
        var keys = new ArrayList<ArrayList<Integer>>();
        var locks = new ArrayList<ArrayList<Integer>>();
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        for (int i = 0; i < lines.size(); i += 8) {
            var isLock = lines.get(i).contains("#");
            if (isLock) {
                var lockPins = new ArrayList<Integer>();
                for (int x = 0; x < 5; x++) {
                    var pinCount = 0;
                    for (int y = i + 1; y < i + 7; y++) {
                        var lineChar = lines.get(y).toCharArray();
                        if (lineChar[x] == '#') {
                            pinCount++;
                        }
                    }
                    lockPins.add(pinCount);
                }
                locks.add(lockPins);
            } else {
                var keyPins = new ArrayList<Integer>();
                for (int x = 0; x < 5; x++) {
                    var pinCount = 0;
                    for (int y = i; y < i + 6; y++) {
                        var lineChar = lines.get(y).toCharArray();
                        if (lineChar[x] == '#') {
                            pinCount++;
                        }
                    }
                    keyPins.add(pinCount);
                }
                keys.add(keyPins);
            }
        }

        var successful = 0;
        for (var key : keys) {
            for (var lock : locks) {
                if (fits(key, lock)) {
                    successful++;
                }
            }
        }

        return successful;
    }

    private Boolean fits(List<Integer> key, List<Integer> lock) {
        for (int i = 0; i < lock.size(); i++) {
            var keyPins = key.get(i);
            var lockPins = lock.get(i);
            if ((keyPins + lockPins) >= 6)
                return false;
        }

        return true;
    }
}
