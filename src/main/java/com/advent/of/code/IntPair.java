package com.advent.of.code;

public class IntPair extends Pair<Integer, Integer> {
    public IntPair(Integer a, Integer b) {
        super(a, b);
    }

    public IntPair(IntPair pair) {
        super(pair.getA(), pair.getB());
    }
}
