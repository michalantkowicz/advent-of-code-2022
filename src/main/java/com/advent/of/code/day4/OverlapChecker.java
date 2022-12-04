package com.advent.of.code.day4;

import com.advent.of.code.Pair;

import java.util.List;

class OverlapChecker {
    long getCountOfIncludedRanges(List<Pair<Pair<Long, Long>, Pair<Long, Long>>> input) {
        long count = 0;
        for (Pair<Pair<Long, Long>, Pair<Long, Long>> pairPairPair : input) {
            if (isIncluded(pairPairPair.getA(), pairPairPair.getB())) {
                count++;
            }
        }
        return count;
    }

    long getCountOfOverlappingRanges(List<Pair<Pair<Long, Long>, Pair<Long, Long>>> input) {
        long count = 0;
        for (Pair<Pair<Long, Long>, Pair<Long, Long>> pairPairPair : input) {
            if (isOverlapping(pairPairPair.getA(), pairPairPair.getB())) {
                count++;
            }
        }
        return count;
    }

    private boolean isIncluded(Pair<Long, Long> firstRange, Pair<Long, Long> secondRange) {
        return (firstRange.getA() >= secondRange.getA() && firstRange.getB() <= secondRange.getB())
                || (secondRange.getA() >= firstRange.getA() && secondRange.getB() <= firstRange.getB());
    }

    private boolean isOverlapping(Pair<Long, Long> firstRange, Pair<Long, Long> secondRange) {
        return (firstRange.getA() >= secondRange.getA() && firstRange.getA() <= secondRange.getB())
                || (firstRange.getB() <= secondRange.getB() && firstRange.getB() >= secondRange.getA())
                || (secondRange.getA() >= firstRange.getA() && secondRange.getA() <= firstRange.getB())
                || (secondRange.getB() <= firstRange.getB() && secondRange.getB() >= firstRange.getA());
    }
}
