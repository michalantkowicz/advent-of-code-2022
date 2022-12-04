package com.advent.of.code.day3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class ItemFinderTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperSumOfPriorities(String inputFilePath, long expectedValue) {
        // given
        final ItemFinder itemFinder = new ItemFinder();
        final List<String> input = readFileLines(inputFilePath);

        // when
        long result = itemFinder.sumOfPriorities(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateProperSumOfGroupPriorities(String inputFilePath, long expectedValue) {
        // given
        final ItemFinder itemFinder = new ItemFinder();
        final List<String> inputLines = readFileLines(inputFilePath);
        final List<List<String>> input = new ArrayList<>();

        List<String> temp = null;

        for (int i = 0; i < inputLines.size(); i++) {
            if (i % 3 == 0) {
                if (temp != null) {
                    input.add(temp);
                }
                temp = new ArrayList<>();
            }
            temp.add(inputLines.get(i));
        }

        input.add(temp);

        // when
        long result = itemFinder.sumOfGroupPriorities(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day3/input0.in", 157),
                Arguments.of("src/test/resources/day3/input1.in", 7785)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day3/input0.in", 70),
                Arguments.of("src/test/resources/day3/input2.in", 2633)
        );
    }
}