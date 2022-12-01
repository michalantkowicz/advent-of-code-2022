package com.advent.of.code.day1;

import java.util.Comparator;
import java.util.List;

public class CaloriesProcessor {
    public long getMaxCalories(List<List<Long>> groups) {
        long max = getGroupCalories(groups.get(0));
        for (List<Long> group : groups) {
            final long groupCalories = getGroupCalories(group);
            if (groupCalories > max) {
                max = groupCalories;
            }
        }
        return max;
    }

    public long getTopThreeCaloriesSum(List<List<Long>> groups) {
        return groups.stream()
                .map(this::getGroupCalories)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Long::valueOf)
                .sum();
    }

    private long getGroupCalories(List<Long> group) {
        return group.stream().mapToLong(Long::valueOf).sum();
    }
}
