package com.advent.of.code.day11;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Data
@Builder
class Monkey {
    private int id;
    private Long acc;
    private List<Long> muls;
    private List<Long> items;
    private Function<Long, Long> operation;
    private Predicate<Long> test;
    private Supplier<Integer> ifTrue;
    private Supplier<Integer> ifFalse;

    private long inspectCount;
}
