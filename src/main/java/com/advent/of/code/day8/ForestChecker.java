package com.advent.of.code.day8;

import com.advent.of.code.Pair;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

class ForestChecker {
    int countVisibleTrees(Integer[][] trees) {
        final Set<IntPair> visible = new HashSet<>();
        final Forest forest = new Forest(trees, trees.length, trees[0].length);
        visible.addAll(checkFromLeft(forest));
        visible.addAll(checkFromRight(forest));
        visible.addAll(checkFromTop(forest));
        visible.addAll(checkFromBottom(forest));
        return visible.size() + 2 * (forest.getWidth() + forest.getHeight()) - 4;
    }

    private Set<IntPair> checkFromLeft(Forest forest) {
        final Set<IntPair> result = new HashSet<>();
        for (int y = 1; y < forest.getHeight() - 1; y++) {
            int max = forest.getTrees()[0][y];
            for (int x = 1; x < forest.getWidth() - 1; x++) {
                if (forest.getTrees()[x][y] > max) {
                    max = forest.getTrees()[x][y];
                    result.add(new IntPair(x, y));
                }
            }
        }
        return result;
    }

    private Set<IntPair> checkFromRight(Forest forest) {
        final Set<IntPair> result = new HashSet<>();
        for (int y = 1; y < forest.getHeight() - 1; y++) {
            int max = forest.getTrees()[forest.getWidth() - 1][y];
            for (int x = forest.getWidth() - 1; x > 0; x--) {
                if (forest.getTrees()[x][y] > max) {
                    max = forest.getTrees()[x][y];
                    result.add(new IntPair(x, y));
                }
            }
        }
        return result;
    }

    private Set<IntPair> checkFromTop(Forest forest) {
        final Set<IntPair> result = new HashSet<>();
        for (int x = 1; x < forest.getWidth() - 1; x++) {
            int max = forest.getTrees()[x][0];
            for (int y = 0; y < forest.getHeight() - 1; y++) {
                if (forest.getTrees()[x][y] > max) {
                    max = forest.getTrees()[x][y];
                    result.add(new IntPair(x, y));
                }
            }
        }
        return result;
    }

    private Set<IntPair> checkFromBottom(Forest forest) {
        final Set<IntPair> result = new HashSet<>();
        for (int x = 1; x < forest.getWidth() - 1; x++) {
            int max = forest.getTrees()[x][forest.getHeight() - 1];
            for (int y = forest.getHeight() - 1; y > 0; y--) {
                if (forest.getTrees()[x][y] > max) {
                    max = forest.getTrees()[x][y];
                    result.add(new IntPair(x, y));
                }
            }
        }
        return result;
    }

    int getMaxScenicScore(Integer[][] trees) {
        int maxScore = 0;
        final Forest forest = new Forest(trees, trees.length, trees[0].length);

        for (int x = 1; x < forest.getWidth() - 1; x++) {
            for (int y = 1; y < forest.getHeight() - 1; y++) {
                int score = calculateScenicScore(forest, x, y);
                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }

        return maxScore;
    }

    private int calculateScenicScore(Forest forest, int x, int y) {
        int leftScore = calculateScore(forest, new IntPair(x, y), p -> p.getA() > 0, p -> new IntPair(p.getA() - 1, p.getB()));
        int rightScore = calculateScore(forest, new IntPair(x, y), p -> p.getA() < forest.getWidth() - 1, p -> new IntPair(p.getA() + 1, p.getB()));
        int topScore = calculateScore(forest, new IntPair(x, y), p -> p.getB() > 0, p -> new IntPair(p.getA(), p.getB() - 1));
        int bottomScore = calculateScore(forest, new IntPair(x, y), p -> p.getB() < forest.getHeight() - 1, p -> new IntPair(p.getA(), p.getB() + 1));

        return leftScore * rightScore * topScore * bottomScore;
    }

    private int calculateScore(Forest forest, IntPair position, Predicate<IntPair> condition, Function<IntPair, IntPair> transform) {
        int score = 0;
        final Integer treeHeight = forest.trees[position.getA()][position.getB()];
        IntPair tempPosition = new IntPair(position);
        do {
            score++;
            tempPosition = transform.apply(tempPosition);
        } while (condition.test(tempPosition) && forest.trees[tempPosition.getA()][tempPosition.getB()] < treeHeight);
        return score;
    }

    @Value
    private static class Forest {
        Integer[][] trees;
        int width, height;
    }

    private static class IntPair extends Pair<Integer, Integer> {
        IntPair(Integer a, Integer b) {
            super(a, b);
        }

        IntPair(IntPair pair) {
            super(pair.getA(), pair.getB());
        }
    }

}



