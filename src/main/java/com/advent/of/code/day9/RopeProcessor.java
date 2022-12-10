package com.advent.of.code.day9;

import com.advent.of.code.IntPair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RopeProcessor {
    long countVisitedPlaces(List<Move> moves, int n) {
        IntPair[] nodes = new IntPair[n];
        Arrays.fill(nodes, new IntPair(0, 0));

        final Set<IntPair> visited = new HashSet<>();

        for (Move move : moves) {
            for (int step = 0; step < move.getSteps(); step++) {
                visited.add(nodes[n - 1]);
                nodes[0] = makeMove(nodes[0], move.getType());
                for (int j = 1; j < n; j++) {
                    if (shouldTailMove(nodes[j], nodes[j - 1])) {
                        IntPair moveToMake = calculateMovement(nodes[j], nodes[j - 1]);
                        nodes[j] = addToB(addToA(nodes[j], moveToMake.getA()), moveToMake.getB());
                    } else {
                        break;
                    }
                }
            }
        }
        return visited.size();
    }

    private IntPair calculateMovement(IntPair tail, IntPair head) {
        if (tail.getA().equals(head.getA())) {
            if (head.getB() > tail.getB()) {
                return new IntPair(0, 1);
            } else {
                return new IntPair(0, -1);
            }
        } else if (tail.getB().equals(head.getB())) {
            if (head.getA() > tail.getA()) {
                return new IntPair(1, 0);
            } else {
                return new IntPair(-1, 0);
            }
        } else if (head.getA() > tail.getA() && head.getB() > tail.getB()) {
            return new IntPair(1, 1);
        } else if (head.getA() > tail.getA() && head.getB() < tail.getB()) {
            return new IntPair(1, -1);
        } else if (head.getA() < tail.getA() && head.getB() > tail.getB()) {
            return new IntPair(-1, 1);
        } else if (head.getA() < tail.getA() && head.getB() < tail.getB()) {
            return new IntPair(-1, -1);
        } else {
            throw new IllegalStateException();
        }
    }

    private IntPair makeMove(IntPair position, Character moveType) {
        return switch (moveType) {
            case 'R' -> addToA(position, 1);
            case 'L' -> addToA(position, -1);
            case 'U' -> addToB(position, 1);
            case 'D' -> addToB(position, -1);
            default -> throw new IllegalArgumentException();
        };
    }

    private boolean shouldTailMove(IntPair tailPosition, IntPair headPosition) {
        final boolean isNeighbour = tailPosition.equals(headPosition)
                || addToA(tailPosition, 1).equals(headPosition)
                || addToA(tailPosition, -1).equals(headPosition)
                || addToB(tailPosition, 1).equals(headPosition)
                || addToB(tailPosition, -1).equals(headPosition)
                || addToA(addToB(tailPosition, 1), 1).equals(headPosition)
                || addToA(addToB(tailPosition, -1), -1).equals(headPosition)
                || addToA(addToB(tailPosition, -1), 1).equals(headPosition)
                || addToA(addToB(tailPosition, 1), -1).equals(headPosition);
        return !isNeighbour;
    }

    private IntPair addToA(IntPair pair, int a) {
        return new IntPair(pair.getA() + a, pair.getB());
    }

    private IntPair addToB(IntPair pair, int b) {
        return new IntPair(pair.getA(), pair.getB() + b);
    }
}
