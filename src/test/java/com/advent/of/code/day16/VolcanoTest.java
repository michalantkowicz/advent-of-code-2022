package com.advent.of.code.day16;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

class VolcanoTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateMaxPressure(String inputFilePath, int expectedValue) {
        // given
        final Volcano volcano = new Volcano();
        final Map<String, Valve> input = readFileLines(inputFilePath).stream()
                .map(line -> {
                            final String[] split = line.split(" has flow rate=");
                            final String label = split[0].split(" ")[1];
                            final int flowRate = parseInt(split[1].split(";")[0]);
                            final List<String> neighbours = stream(
                                    split[1].split((line.contains("leads") ? "leads to valve" : "lead to valves "))[1].split(", ")
                            ).map(String::trim).toList();

                            return new Valve(label, flowRate, neighbours);
                        }
                ).collect(Collectors.toMap(Valve::label, Function.identity()));

        // when
        int result = volcano.calculateMaxPressure(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Disabled("Not finished - running too long")
    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateMaxPressureWithHelperElephant(String inputFilePath, int expectedValue) {
        // given
        final Volcano volcano = new Volcano();
        final Map<String, Valve> input = readFileLines(inputFilePath).stream()
                .map(line -> {
                            final String[] split = line.split(" has flow rate=");
                            final String label = split[0].split(" ")[1];
                            final int flowRate = parseInt(split[1].split(";")[0]);
                            final List<String> neighbours = stream(
                                    split[1].split((line.contains("leads") ? "leads to valve" : "lead to valves "))[1].split(", ")
                            ).map(String::trim).toList();

                            return new Valve(label, flowRate, neighbours);
                        }
                ).collect(Collectors.toMap(Valve::label, Function.identity()));

        // when
        int result = volcano.calculateMaxPressureWithHelperElephant(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day16/input0.in", 1651),
                Arguments.of("src/test/resources/day16/input1.in", 2119)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day16/input0.in", 1707),
                Arguments.of("src/test/resources/day16/input2.in", 2119)
        );
    }
}