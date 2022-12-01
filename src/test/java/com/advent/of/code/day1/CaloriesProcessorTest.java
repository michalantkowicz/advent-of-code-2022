package com.advent.of.code.day1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;


class CaloriesProcessorTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldReturnMaxSumOfCalories(String inputFilePath, long expectedValue) {
        // given
        final CaloriesProcessor processor = new CaloriesProcessor();
        final List<List<Long>> input = getInput(inputFilePath);

        // when
        long result = processor.getMaxCalories(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldReturnMaxSumOfTopThreeCalories(String inputFilePath, long expectedValue) {
        // given
        final CaloriesProcessor processor = new CaloriesProcessor();
        final List<List<Long>> input = getInput(inputFilePath);

        // when
        long result = processor.getTopThreeCaloriesSum(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private List<List<Long>> getInput(String inputFilePath) {
        final List<String> lines = readFileLines(inputFilePath);
        final List<List<Long>> input = new ArrayList<>();

        List<Long> currentGroup = new ArrayList<>();

        for (String line : lines) {
            if ("".equals(line)) {
                input.add(currentGroup);
                currentGroup = new ArrayList<>();
            } else {
                currentGroup.add(Long.valueOf(line));
            }
        }

        input.add(currentGroup);
        return input;
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day1/input0.in", 24000),
                Arguments.of("src/test/resources/day1/input1.in", 71506)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day1/input0.in", 45000),
                Arguments.of("src/test/resources/day1/input2.in", 209603)
        );
    }
}