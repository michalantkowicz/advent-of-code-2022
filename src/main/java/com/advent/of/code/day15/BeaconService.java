package com.advent.of.code.day15;

import com.advent.of.code.IntPair;
import com.advent.of.code.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

class BeaconService {
    long countReservedAt(List<Pair<IntPair, IntPair>> input, int line) {
        final List<Pair<Long, Long>> ranges = new ArrayList<>();
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
                long rest = distance - abs(sensor.getB() - line);
                ranges.add(new Pair<>(sensor.getA() - rest, sensor.getA() + rest));
            }
        }

        ranges.sort(Comparator.comparingLong(Pair::getA));

        final Set<Long> reservedPositions = new HashSet<>();

        for (Pair<Long, Long> range : ranges) {
            for (long i = range.getA(); i <= range.getB(); i++) {
                reservedPositions.add(i);
            }
        }

        return reservedPositions.size() - beaconsInRow;
    }
}