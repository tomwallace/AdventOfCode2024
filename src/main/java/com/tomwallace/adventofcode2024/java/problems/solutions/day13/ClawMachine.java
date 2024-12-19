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

    public Long findSmallestTokensByEquation() {
        // equation from Reddit thread, as brute force would clearly not work and high school math was a long time ago
        // b=(py*ax-px*ay)/(by*ax-bx*ay) a=(px-b*bx)/ax
        var bTop = (target.y() * aMod.x() - target.x() * aMod.y());
        var bBottom = (bMod.y() * aMod.x() - bMod.x() * aMod.y());
        if (bTop % bBottom != 0)
            return 0L;
        var b = bTop/bBottom;
        var aTop = target.x() - b * bMod.x();
        var aBottom = aMod.x();
        if (aTop % aBottom != 0)
            return 0L;
        var a = aTop/aBottom;
        return (a * 3) + b;
    }

    private Boolean isSuccessful(Long a, Long b) {
        return ((a * aMod.x()) + (b * bMod.x())) == target.x() && ((a * aMod.y()) + (b * bMod.y())) == target.y();
    }

    private Long numberOfTokens(Long a, Long b) {
        return (a * 3) + b;
    }
}
