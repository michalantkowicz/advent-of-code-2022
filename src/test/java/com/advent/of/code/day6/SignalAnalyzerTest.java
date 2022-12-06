package com.advent.of.code.day6;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFile;

class SignalAnalyzerTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldReturnProperMarkerPosition(String inputFilePath, int expectedValue) {
        // given
        final SignalAnalyzer signalAnalyzer = new SignalAnalyzer();
        final String input = readFile(inputFilePath);

        // when
        int result = signalAnalyzer.findMarkerPosition(input, 4);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldReturnProperMarkerPositionWhenUniqueStringIsLonger(String inputFilePath, int expectedValue) {
        // given
        final SignalAnalyzer signalAnalyzer = new SignalAnalyzer();
        final String input = readFile(inputFilePath);

        // when
        int result = signalAnalyzer.findMarkerPosition(input, 14);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day6/example/example0.in", 7),
                Arguments.of("src/test/resources/day6/example/example1.in", 5),
                Arguments.of("src/test/resources/day6/example/example2.in", 6),
                Arguments.of("src/test/resources/day6/example/example3.in", 10),
                Arguments.of("src/test/resources/day6/example/example4.in", 11),
                Arguments.of("src/test/resources/day6/input1.in", 1275)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day6/example/example0.in", 19),
                Arguments.of("src/test/resources/day6/example/example1.in", 23),
                Arguments.of("src/test/resources/day6/example/example2.in", 23),
                Arguments.of("src/test/resources/day6/example/example3.in", 29),
                Arguments.of("src/test/resources/day6/example/example4.in", 26),
                Arguments.of("src/test/resources/day6/input1.in", 1275)
        );
    }
}