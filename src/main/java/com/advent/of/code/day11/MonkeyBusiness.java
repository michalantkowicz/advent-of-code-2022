package com.advent.of.code.day11;

import java.util.Map;

class MonkeyBusiness {
    long getMonkeyBusinessAfterRounds(Map<Integer, Monkey> monkeys, boolean divide, int rounds) {
        for (int i = 0; i < rounds; i++) {
            playRound(monkeys, divide);
        }
        return monkeys.values().stream()
                .sorted((m1, m2) -> Long.compare(m2.getInspectCount(), m1.getInspectCount()))
                .limit(2)
                .mapToLong(Monkey::getInspectCount)
                .reduce(1, (a, b) -> a * b);
    }

    private void playRound(Map<Integer, Monkey> monkeys, boolean divide) {
        for (Integer key : monkeys.keySet().stream().sorted().toList()) {
            final Monkey monkey = monkeys.get(key);

            while (!monkey.getItems().isEmpty()) {
                monkey.setInspectCount(monkey.getInspectCount() + 1);

                final Long item = monkey.getItems().remove(0);
                Long worryLevel = divide ? Math.floorDiv(monkey.getOperation().apply(item), 3) : monkey.getOperation().apply(item);

                worryLevel %= (monkeys.values().stream().mapToLong(Monkey::getDiv).reduce((a,b) -> a*b)).getAsLong();
                
                Integer targetMonkey = null;

                if (monkey.getTest().test(worryLevel)) {
                    targetMonkey = monkey.getIfTrue().get();
                } else {
                    targetMonkey = monkey.getIfFalse().get();
                }

                monkeys.get(targetMonkey).getItems().add(worryLevel);
            }
        }
    }
}
