package com.advent.of.code.day15;

import com.advent.of.code.IntPair;
import com.advent.of.code.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.abs;

public class BeaconService {

    public long countReservedAt(List<Pair<IntPair, IntPair>> input, int line) {
        final List<Pair<Long, Long>> ranges = new ArrayList<>();

        for (Pair<IntPair, IntPair> pair : input) {
            final IntPair sensor = pair.getA();
            final IntPair beacon = pair.getB();
            final long distance = abs(sensor.getA() - beacon.getA()) + abs(sensor.getB() - beacon.getB());

            if (abs(sensor.getB() - line) < distance) {
                long rest = distance - abs(sensor.getB() - line);
                ranges.add(new Pair<>(sensor.getB() - rest, sensor.getB() + rest));
            }
        }

        ranges.sort(Comparator.comparingLong(Pair::getA));

        long result = 0;

        for (int i = 0; i < ranges.size(); i++) {
            result += ranges.get(i).getB() - ranges.get(i).getA() + 1;
            for(int j = i + 1; j < ranges.size(); j++) {
                if(i != j) {
                    result -= Math.max(0, Math.min(ranges.get(i).getB(), ranges.get(j).getB()) - Math.max(ranges.get(i).getA(), ranges.get(j).getA()) + 1);
                }
            }
        }

        return result;
    }
}
