package com.advent.of.code.day15;

import com.advent.of.code.IntPair;
import com.advent.of.code.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

class BeaconService {
    long countReservedAt(List<Pair<IntPair, IntPair>> input, int line) {
        final List<Wrapper> wrappers = new ArrayList<>();
        long beaconsInRow = new HashSet<>(input.stream().map(Pair::getB).collect(Collectors.toList()))
                .stream()
                .filter(b -> b.getB().equals(line))
                .collect(Collectors.toSet())
                .size();

        for (Pair<IntPair, IntPair> pair : input) {
            final IntPair sensor = pair.getA();
            final IntPair beacon = pair.getB();
            final long distance = abs(sensor.getA() - beacon.getA()) + abs(sensor.getB() - beacon.getB());

            if (abs(sensor.getB() - line) < distance) {
                int rest = (int) (distance - abs(sensor.getB() - line));
                IntPair range = new IntPair(sensor.getA() - rest, sensor.getA() + rest);
                updateWrappers(wrappers, range);
            }
        }

        long result = 0L;

        for (Wrapper wrapper : wrappers) {
            int length = length(wrapper.range());
            if (wrapper.add()) {
                result += length;
            } else {
                result -= length;
            }
        }

        return result - beaconsInRow;
    }

    long countReservedAtWithBoundaries(List<Pair<IntPair, IntPair>> input, int line, int min, int max) {
        final IntPair boundaries = new IntPair(min, max);
        final List<Wrapper> wrappers = new ArrayList<>();

        for (Pair<IntPair, IntPair> pair : input) {
            final IntPair sensor = pair.getA();
            final IntPair beacon = pair.getB();
            final long distance = abs(sensor.getA() - beacon.getA()) + abs(sensor.getB() - beacon.getB());

            if (abs(sensor.getB() - line) < distance) {
                int rest = (int) (distance - abs(sensor.getB() - line));
                IntPair range = new IntPair(sensor.getA() - rest, sensor.getA() + rest);

                if (intersects(range, boundaries)) {
                    range = intersection(range, boundaries);
                    updateWrappers(wrappers, range);
                }
            }
        }

        long result = 0L;

        for (Wrapper wrapper : wrappers) {
            int length = length(wrapper.range());
            if (wrapper.add()) {
                result += length;
            } else {
                result -= length;
            }
        }

        return result;
    }

    Set<Integer> findEmptyPlace(List<Pair<IntPair, IntPair>> input, int line, int min, int max) {
        final Set<Integer> full = new HashSet<>();
        for (int i = min; i <= max; i++) {
            full.add(i);
        }

        for (Pair<IntPair, IntPair> pair : input) {
            final IntPair sensor = pair.getA();
            final IntPair beacon = pair.getB();
            final long distance = abs(sensor.getA() - beacon.getA()) + abs(sensor.getB() - beacon.getB());

            if (abs(sensor.getB() - line) < distance) {
                int rest = (int) (distance - abs(sensor.getB() - line));
                IntPair range = new IntPair(sensor.getA() - rest, sensor.getA() + rest);
                for (int i = range.getA(); i <= range.getB(); i++) {
                    full.remove(i);
                }
            }
        }

        return full;
    }

    private void updateWrappers(List<Wrapper> wrappers, IntPair range) {
        List<Wrapper> toAdd = new ArrayList<>();

        for (Wrapper wrapper : wrappers) {
            if (intersects(wrapper.range(), range)) {
                toAdd.add(new Wrapper(intersection(wrapper.range(), range), !wrapper.add()));
            }
        }
        wrappers.addAll(toAdd);
        wrappers.add(new Wrapper(range, true));
    }

    private static IntPair intersection(IntPair a, IntPair b) {
        return new IntPair(Math.max(a.getA(), b.getA()), Math.min(a.getB(), b.getB()));
    }

    private static boolean intersects(IntPair a, IntPair b) {
        return b.getA() <= a.getB() && a.getA() <= b.getB();
    }

    private static int length(IntPair range) {
        return range.getB() - range.getA() + 1;
    }
}