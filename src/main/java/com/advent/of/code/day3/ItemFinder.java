package com.advent.of.code.day3;

import java.util.Arrays;
import java.util.List;

class ItemFinder {
    long sumOfPriorities(List<String> rucksucks) {
        long result = 0;

        for (String rucksuck : rucksucks) {
            int[] items1 = new int[53];
            Arrays.fill(items1, 0);
            int[] items2 = new int[53];
            Arrays.fill(items2, 0);

            int half = rucksuck.length() / 2;

            for (int i = 0; i < half; i++) {
                items1[getPriority(rucksuck.charAt(i))]++;
                items2[getPriority(rucksuck.charAt(i + half))]++;
            }

            for (int i = 0; i < 53; i++) {
                if (items1[i] > 0 && items2[i] > 0) {
                    result += i;
                }
            }
        }

        return result;
    }

    long sumOfGroupPriorities(List<List<String>> groups) {
        long result = 0;
        for (List<String> group : groups) {
            result += findBadgePriority(group);
        }
        return result;
    }

    private int findBadgePriority(List<String> group) {
        int[] elf1 = new int[53];
        Arrays.fill(elf1, 0);
        int[] elf2 = new int[53];
        Arrays.fill(elf2, 0);
        int[] elf3 = new int[53];
        Arrays.fill(elf3, 0);

        for (int i = 0; i < group.get(0).length(); i++) {
            elf1[getPriority(group.get(0).charAt(i))]++;
        }

        for (int i = 0; i < group.get(1).length(); i++) {
            elf2[getPriority(group.get(1).charAt(i))]++;
        }

        for (int i = 0; i < group.get(2).length(); i++) {
            elf3[getPriority(group.get(2).charAt(i))]++;
        }

        for (int i = 0; i < 53; i++) {
            if (elf1[i] > 0 && elf2[i] > 0 && elf3[i] > 0) {
                return i;
            }
        }
        throw new IllegalStateException("No badge found");
    }

    private int getPriority(Character c) {
        if (Character.isLowerCase(c)) {
            return ((int) c) - 96;
        } else {
            return ((int) c) - 38;
        }
    }
}

