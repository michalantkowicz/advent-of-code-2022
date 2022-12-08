package com.advent.of.code.day8;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class ForestCheckerTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCountVisibleTrees(String inputFilePath, int expectedValue) {
        // given
        final ForestChecker forestChecker = new ForestChecker();
        final List<List<Integer>> listInput = readFileLines(inputFilePath)
                .stream()
                .map(line -> Arrays.stream(line.split(""))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());

        Integer[][] input = listInput.stream()
                .map(arr -> arr.toArray(Integer[]::new))
                .toArray(Integer[][]::new);

        // when
        long result = forestChecker.countVisibleTrees(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateMaxScenicScore(String inputFilePath, int expectedValue) {
        // given
        final ForestChecker forestChecker = new ForestChecker();
        final List<List<Integer>> listInput = readFileLines(inputFilePath)
                .stream()
                .map(line -> Arrays.stream(line.split(""))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());

        Integer[][] input = listInput.stream()
                .map(arr -> arr.toArray(Integer[]::new))
                .toArray(Integer[][]::new);

        // when
        long result = forestChecker.getMaxScenicScore(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day8/input0.in", 21),
                Arguments.of("src/test/resources/day8/input1.in", 1779)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day8/input0.in", 8),
                Arguments.of("src/test/resources/day8/input2.in", 172224)
        );
    }
}