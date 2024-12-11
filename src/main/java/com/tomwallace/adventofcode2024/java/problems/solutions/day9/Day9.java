package com.tomwallace.adventofcode2024.java.problems.solutions.day9;

import com.tomwallace.adventofcode2024.java.problems.Difficulty;
import com.tomwallace.adventofcode2024.java.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2024.java.utilities.FileUtility;

import java.util.List;

public class Day9 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Disk Fragmenter";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 9; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day9Input.txt";
        var lines = FileUtility.parseFileToListListCharacter(filePath);
        var checksum = defragBlocksAndCalculateChecksum(lines.get(0), false);

        return checksum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day9Input.txt";
        var lines = FileUtility.parseFileToListListCharacter(filePath);
        var checksum = defragBlocksAndCalculateChecksum(lines.get(0), true);

        return checksum.toString();
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

    // After two different approaches, I could not get the actual Problem input to solve for Part A (test cases worked)
    // so, I implemented code from another repo - https://github.com/hsabbas/AdventOfCode-Java/blob/master/src/main/java/io/github/hsabbas/aoc2024/Day9.java
    protected Long defragBlocksAndCalculateChecksum(List<Character> input, Boolean useFiles) {
        int[] filesystem = input.stream()
                .mapToInt(c -> Integer.parseInt(c.toString()))
                .toArray();
        return !useFiles ? solvePartOne(filesystem) : solvePartTwo(filesystem);
    }

    private long solvePartOne(int[] filesystem) {
        long checksum = 0;
        int currIndex = 0;

        int left = 0;
        int right = filesystem.length - 1;
        int spaceNeeded = filesystem[right];
        while(left < right) {
            for(int i = 0; i < filesystem[left]; i++){
                checksum += (long) (left / 2) * currIndex;
                currIndex++;
            }
            left++;

            for(int i = 0; i < filesystem[left]; i++) {
                if(spaceNeeded == 0) {
                    right -= 2;
                    if(right <= left) {
                        break;
                    }
                    spaceNeeded = filesystem[right];
                }
                checksum += (long) (right / 2) * currIndex;
                currIndex++;
                spaceNeeded--;
            }
            left++;
        }
        for(int i = 0; i < spaceNeeded; i++) {
            checksum += (long) (right / 2) * currIndex;
            currIndex++;
        }

        return checksum;
    }

    private long solvePartTwo(int[] filesystem) {
        long checksum = 0L;

        int[] openStartIndex = new int[filesystem.length];
        openStartIndex[0] = 0;
        for(int i = 1; i < filesystem.length; i++) {
            openStartIndex[i] = openStartIndex[i - 1] + filesystem[i - 1];
        }

        for(int right = filesystem.length - 1; right >= 0; right -= 2) {
            boolean found = false;
            for (int left = 1; left < right; left += 2) {
                if (filesystem[left] >= filesystem[right]) {
                    for (int i = 0; i < filesystem[right]; i++) {
                        checksum += (long) (right / 2) * (openStartIndex[left] + i);
                    }
                    filesystem[left] -= filesystem[right];
                    openStartIndex[left] += filesystem[right];
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (int i = 0; i < filesystem[right]; i++) {
                    checksum += (long) (right / 2) * (openStartIndex[right] + i);
                }
            }
        }

        return checksum;
    }
}

