package com.advent.of.code.day6;

import java.util.HashSet;
import java.util.Set;

class SignalAnalyzer {
    int findMarkerPosition(String signal, int uniqueStringLength) {
        int start = 0;
        int end = uniqueStringLength;

        while (true) {
            int s = getRepeatingIndex(signal, start, end);
            if (s < start) {
                break;
            } else {
                start = s + 1;
                end = start + uniqueStringLength;
            }
        }

        return end;
    }

    private int getRepeatingIndex(String signal, int start, int end) {
        final Set<Character> set = new HashSet<>();

        for (int i = end - 1; i >= start; i--) {
            Character c = signal.charAt(i);
            if (set.contains(c)) {
                return i;
            } else {
                set.add(c);
            }
        }

        return start - 1;
    }


}
