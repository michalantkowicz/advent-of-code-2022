package com.advent.of.code.day11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFile;

class MonkeyBusinessTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperMonkeyBusiness(String inputFilePath, long expectedValue) {
        // given
        final MonkeyBusiness monkeyBusiness = new MonkeyBusiness();
        final Map<Integer, Monkey> input = getInput(inputFilePath);

        // when
        long result = monkeyBusiness.getMonkeyBusinessAfterRounds(input, true, 20);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateProperMonkeyBusinessWithUnmanageableLevels(String inputFilePath, long expectedValue) {
        // given
        final MonkeyBusiness monkeyBusiness = new MonkeyBusiness();
        final Map<Integer, Monkey> input = getInput(inputFilePath);

        // when
        long result = monkeyBusiness.getMonkeyBusinessAfterRounds(input, false, 10_000);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private Map<Integer, Monkey> getInput(String inputFilePath) {
        final List<Monkey> monkeys = new ArrayList<>();

        final String inputFile = readFile(inputFilePath);

        for (String monkeyDefinition : inputFile.split("\n\n")) {
            final String[] lines = monkeyDefinition.split("\n");
            final Monkey monkey = new Monkey.MonkeyBuilder()
                    .id(Integer.parseInt(lines[0].trim().split(":")[0].split(" ")[1]))
                    .items(Arrays.stream(lines[1].trim().split(": ")[1].split(", ")).map(Long::parseLong).collect(Collectors.toList()))
                    .operation(lines[2].trim().split(" ")[4].equals("+") ? calc(lines[2], Long::sum) : calc(lines[2], (a, b) -> a * b))
                    .test(x -> x % Long.parseLong(lines[3].trim().split(" ")[3]) == 0)
                    .ifTrue(() -> Integer.parseInt(lines[4].trim().split(" ")[5]))
                    .ifFalse(() -> Integer.parseInt(lines[5].trim().split(" ")[5]))
                    .inspectCount(0L)
                    .acc(0L)
                    .muls(new ArrayList<>())
                    .build();
            monkeys.add(monkey);
        }

        final Map<Integer, Monkey> input = monkeys.stream()
                .collect(Collectors.toMap(Monkey::getId, Function.identity()));
        return input;
    }

    private Function<Long, Long> calc(String line, BiFunction<Long, Long, Long> operation) {
        String a = line.trim().split(" ")[3];
        String b = line.trim().split(" ")[5];

        boolean isPlus = line.trim().split(" ")[4].equals("+");

        if (a.equals("old") && b.equals("old")) {
            return x -> operation.apply(x, x);
        } else if (a.equals("old")) {
            return x -> operation.apply(x, Long.parseLong(b));
        } else if (b.equals("old")) {
            return x -> operation.apply(Long.parseLong(a), x);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day11/input0.in", 10605L),
                Arguments.of("src/test/resources/day11/input1.in", 107822L)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day11/input0.in", 2713310158L),
                Arguments.of("src/test/resources/day11/input1.in", 107822L)
        );
    }
}