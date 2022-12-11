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
    private List<Long> items;
    private Function<Long, Long> operation;
    private Predicate<Long> test;
    private Supplier<Long> ifTrue;
    private Supplier<Long> ifFalse;
}
