package com.advent.of.code.day11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        final List<Monkey> monkeys = new ArrayList<>();

        final String inputFile = readFile(inputFilePath);

        for (String monkeyDefinition : inputFile.split("\r\n\r\n")) {
            String[] lines = monkeyDefinition.split("\r\n");
            Monkey monkey = new Monkey.MonkeyBuilder()
                    .id(Integer.parseInt(lines[0].split(":")[0].split(" ")[1]))
                    .items(Arrays.stream(lines[1].split(": ")[1].split(", ")).mapToLong(Long::parseLong);
        }

        final Map<Integer, Monkey> input = monkeys.stream()
                .collect(Collectors.toMap(Monkey::getId, Function.identity()));

        // when
        long result = monkeyBusiness.getMonkeyBusinessAfterRounds(input, 20);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day11/input0.in", 10605),
                Arguments.of("src/test/resources/day11/input1.in", 13440)
        );
    }
}