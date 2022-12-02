package com.advent.of.code.day2;

import com.advent.of.code.Pair;

import java.util.List;

class RockPaperScissors {
    long calculateResults(List<Pair<Shape, Shape>> strategyGuide) {
        long result = 0;
        for (Pair<Shape, Shape> pair : strategyGuide) {
            result += pair.getB().getScore();
            result += calculatePairResult(pair);
        }
        return result;
    }

    private long calculatePairResult(Pair<Shape, Shape> pair) {
        final List<Pair<Shape, Shape>> winningPairs = List.of(
                new Pair<>(Shape.PAPER, Shape.SCISSORS),
                new Pair<>(Shape.SCISSORS, Shape.ROCK),
                new Pair<>(Shape.ROCK, Shape.PAPER)
        );

        if (pair.getA().equals(pair.getB())) {
            return 3;
        } else if (winningPairs.contains(pair)) {
            return 6;
        } else {
            return 0;
        }
    }
}
