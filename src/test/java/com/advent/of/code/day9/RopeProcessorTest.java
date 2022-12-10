package com.advent.of.code.day9;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class RopeProcessorTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCountVisitedPlaces(String inputFilePath, long expectedValue) {
        // given
        final RopeProcessor ropeProcessor = new RopeProcessor();
        final List<Move> input = readFileLines(inputFilePath)
                .stream()
                .map(line -> new Move(
                                line.split(" ")[0].charAt(0),
                                Integer.parseInt(line.split(" ")[1])
                        )
                ).toList();

        // when
        long result = ropeProcessor.countVisitedPlaces(input, 2);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCountVisitedPlacesFor10Nodes(String inputFilePath, long expectedValue) {
        // given
        final RopeProcessor ropeProcessor = new RopeProcessor();
        final List<Move> input = readFileLines(inputFilePath)
                .stream()
                .map(line -> new Move(
                                line.split(" ")[0].charAt(0),
                                Integer.parseInt(line.split(" ")[1])
                        )
                ).toList();

        // when
        long result = ropeProcessor.countVisitedPlaces(input, 10);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day9/input0.in", 13),
                Arguments.of("src/test/resources/day9/input1.in", 6037)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day9/input0.in", 1),
                Arguments.of("src/test/resources/day9/input2.in", 2485)
        );
    }
}