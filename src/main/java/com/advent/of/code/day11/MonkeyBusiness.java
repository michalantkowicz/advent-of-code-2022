package com.advent.of.code.day11;

import java.util.Map;

class MonkeyBusiness {
    long getMonkeyBusinessAfterRounds(Map<Integer, Monkey> monkeys, int rounds) {
        for (int i = 0; i < rounds; i++) {
            monkeys = playRound(monkeys);
        }
        return monkeys.values().stream()
                .sorted((m1, m2) -> Long.compare(m2.getItems().size(), m1.getItems().size()))
                .limit(2)
                .mapToLong(m -> m.getItems().size())
                .reduce(1, (a, b) -> a * b);
    }

    private Map<Integer, Monkey> playRound(Map<Integer, Monkey> monkeys) {
        return monkeys;
    }

}
