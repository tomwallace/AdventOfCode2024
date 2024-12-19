package com.tomwallace.adventofcode2024.java.problems.solutions.day13;

import com.tomwallace.adventofcode2024.java.common.LongPoint;

public class ClawMachine {
    
    private final LongPoint aMod;
    private final LongPoint bMod;
    private final LongPoint target;

    public ClawMachine(LongPoint aMod, LongPoint bMod, LongPoint target) {
        this.aMod = aMod;
        this.bMod = bMod;
        this.target = target;
    }

    public Long findSmallestTokens(Long maxClicks) {
        var best = Long.MAX_VALUE;
        for (long a = 0; a < maxClicks; a++) {
            for (long b = 0; b < maxClicks; b++) {
                if (isSuccessful(a, b)) {
                    var tokens = numberOfTokens(a, b);
                    best = Math.min(best, tokens);
                }
            }
        }
        return best == Long.MAX_VALUE ? 0 : best;
    }

    // Works on everything BUT Part B
    public Long findSmallestTokensByEquation(Long maxClicks) {
        // equation from Reddit thread, as brute force would clearly not work and high school math was a long time ago
        // b=(py*ax-px*ay)/(by*ax-bx*ay) a=(px-b*bx)/ax
        var bTop = (target.y() * aMod.x() - target.x() * aMod.y());
        var bBottom = (bMod.y() * aMod.x() - bMod.x() * aMod.y());
        if (bTop % bBottom != 0)
            return 0L;
        var b = bTop/bBottom;
        var a = (target.x() - b * bMod.x())/aMod.x();
        if (a > maxClicks || b > maxClicks)
            return 0L;
        return (a * 3) + b;
    }

    // TODO: TW - clean up unused code
    public Long findSmallestTokensByMid() {
        var low = 0L;
        var high = Long.MAX_VALUE;
        var b = 0L;
        while (high > low) {
            var middle = (high - low) / 2;
            b = (target.x() - aMod.x() * middle) / bMod.x();
            var destinationY = aMod.y() * middle + bMod.y() * b;
            if (destinationY >= target.y()) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }

        var a = low;
        b = (target.x() - aMod.x() * b);
        return (a * 3) + b;
    }

    private Boolean isSuccessful(Long a, Long b) {
        return ((a * aMod.x()) + (b * bMod.x())) == target.x() && ((a * aMod.y()) + (b * bMod.y())) == target.y();
    }

    private Long numberOfTokens(Long a, Long b) {
        return (a * 3) + b;
    }
}
