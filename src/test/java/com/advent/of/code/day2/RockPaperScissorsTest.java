package com.advent.of.code.day2;

import com.advent.of.code.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class RockPaperScissorsTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperResult(String inputFilePath, long expectedValue) {
        // given
        final RockPaperScissors rockPaperScissors = new RockPaperScissors();
        final List<Pair<Shape, Shape>> input = readFileLines(inputFilePath)
                .stream()
                .map(line -> new Pair<>(Shape.decode(line.split(" ")[0]), Shape.decode(line.split(" ")[1])))
                .collect(Collectors.toList());

        // when
        long result = rockPaperScissors.calculateResults(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateProperResultDueToWinningStrategy(String inputFilePath, long expectedValue) {
        // given
        final RockPaperScissors rockPaperScissors = new RockPaperScissors();
        final List<Pair<Shape, Shape>> input = readFileLines(inputFilePath)
                .stream()
                .map(line -> new Pair<>(
                        Shape.decode(line.split(" ")[0]),
                        Shape.decodeDueToStrategy(line.split(" ")[0], line.split(" ")[1]))
                )
                .collect(Collectors.toList());

        // when
        long result = rockPaperScissors.calculateResults(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day2/input0.in", 15),
                Arguments.of("src/test/resources/day2/input1.in", 11666)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day2/input0.in", 12),
                Arguments.of("src/test/resources/day2/input2.in", 12767)
        );
    }
}