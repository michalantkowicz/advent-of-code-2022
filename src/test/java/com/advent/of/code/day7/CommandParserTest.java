package com.advent.of.code.day7;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class CommandParserTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldFindProperSumOfSizes(String inputFilePath, long expectedValue) {
        // given
        final CommandParser commandParser = new CommandParser();
        final List<String> input = readFileLines(inputFilePath);

        // when
        long result = commandParser.getSizeSumForDirsLowerThan(input, 100000);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldFindMinDirThatMustBeDeleted(String inputFilePath, long expectedValue) {
        // given
        final CommandParser commandParser = new CommandParser();
        final List<String> input = readFileLines(inputFilePath);

        // when
        long result = commandParser.findMinDirSizeToDelete(input, 70000000, 30000000);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    /**
     * Manually removed first '$ cd /' line from inputs to make parsing easier
     */
    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day7/input0.in", 95437),
                Arguments.of("src/test/resources/day7/input1.in", 95437)
        );
    }

    /**
     * Manually removed first '$ cd /' line from inputs to make parsing easier
     */
    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day7/input0.in", 24933642),
                Arguments.of("src/test/resources/day7/input2.in", 95437)
        );
    }
}