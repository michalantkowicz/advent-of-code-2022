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

public class BeaconService {

    public long countReservedAt(List<Pair<IntPair, IntPair>> input, int line) {
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

        long result = 0;

        for (int i = 0; i < ranges.size(); i++) {
            Set<Pair<Long, Long>> exclusive = new HashSet<>();
            exclusive.add(ranges.get(i));

            for (int j = i + 1; j < ranges.size(); j++) {
                final Pair<Long, Long> current = ranges.get(i);
                final Pair<Long, Long> next = ranges.get(j);
                result -= Math.max(0, Math.min(current.getB(), next.getB()) - Math.max(current.getA(), next.getA()) + 1);
            }

            result += exclusive.stream().mapToLong(r -> r.getB() - r.getA() + 1).sum();
        }

        return result - beaconsInRow;
    }
}
//4200403 too low