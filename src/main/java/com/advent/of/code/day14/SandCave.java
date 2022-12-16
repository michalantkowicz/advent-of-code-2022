package com.advent.of.code.day14;

import com.advent.of.code.IntPair;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SandCave {
    static int EMPTY = 0, SAND = 1, ROCK = 2;

    int simulateAndCountRestSand(int[][] map, IntPair sandSource) {
        final Set<IntPair> restSand = new HashSet<>();
        while (true) {
            final IntPair sandPosition = findPosition(map, new IntPair(sandSource));
            if (sandIsInMap(map, sandPosition)) {
                map[sandPosition.getA()][sandPosition.getB()] = SAND;
                restSand.add(sandPosition);
            } else {
                break;
            }
        }
        return restSand.size();
    }


    private IntPair findPosition(int[][] map, IntPair sandPosition) {
        while (true) {
            final IntPair newPosition = makeStep(map, sandPosition);
            if (Objects.equals(newPosition, sandPosition) || !sandIsInMap(map, newPosition)) {
                return newPosition;
            } else {
                sandPosition = newPosition;
            }
        }
    }

    private IntPair makeStep(int[][] map, IntPair sandPosition) {
        final IntPair bottom = bottom(sandPosition);
        final IntPair bottomLeft = bottomLeft(sandPosition);
        final IntPair bottomRight = bottomRight(sandPosition);

        if (!sandIsInMap(map, bottom)) {
            return bottom;
        } else if (EMPTY == map[bottom.getA()][bottom.getB()]) {
            return makeStep(map, bottom);
        } else if (!sandIsInMap(map, bottomLeft)) {
            return bottomLeft;
        } else if (EMPTY == map[bottomLeft.getA()][bottomLeft.getB()]) {
            return makeStep(map, bottomLeft);
        } else if (!sandIsInMap(map, bottomRight)) {
            return bottomRight;
        } else if (EMPTY == map[bottomRight.getA()][bottomRight.getB()]) {
            return makeStep(map, bottomRight);
        } else {
            return sandPosition;
        }
    }

    private static IntPair bottom(IntPair p) {
        return new IntPair(p.getA(), p.getB() + 1);
    }

    private static IntPair bottomLeft(IntPair p) {
        return new IntPair(p.getA() - 1, p.getB() + 1);
    }

    private static IntPair bottomRight(IntPair p) {
        return new IntPair(p.getA() + 1, p.getB() + 1);
    }

    private static boolean sandIsInMap(int[][] map, IntPair sandPosition) {
        return sandPosition.getA() >= 0
                && sandPosition.getA() < map.length
                && sandPosition.getB() >= 0
                && sandPosition.getB() < map[0].length;
    }
}
