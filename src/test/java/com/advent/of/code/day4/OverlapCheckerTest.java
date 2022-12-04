package com.advent.of.code.day4;

import com.advent.of.code.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

public class OverlapCheckerTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperAmountOfFullyContainedRanges(String inputFilePath, long expectedValue) {
        // given
        final OverlapChecker overlapChecker = new OverlapChecker();
        final List<Pair<Pair<Long, Long>, Pair<Long, Long>>> input = readFileLines(inputFilePath).stream()
                .map(line -> {
                    String[] split = line.split(",");
                    String[] firstRange = split[0].split("-");
                    String[] secondRange = split[1].split("-");
                    return new Pair<>(
                            new Pair<>(Long.valueOf(firstRange[0]), Long.valueOf(firstRange[1])),
                            new Pair<>(Long.valueOf(secondRange[0]), Long.valueOf(secondRange[1]))
                    );
                }).collect(Collectors.toList());

        // when
        long result = overlapChecker.getCountOfIncludedRanges(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateProperAmountOfPartiallyContainedRanges(String inputFilePath, long expectedValue) {
        // given
        final OverlapChecker overlapChecker = new OverlapChecker();
        final List<Pair<Pair<Long, Long>, Pair<Long, Long>>> input = readFileLines(inputFilePath).stream()
                .map(line -> {
                    String[] split = line.split(",");
                    String[] firstRange = split[0].split("-");
                    String[] secondRange = split[1].split("-");
                    return new Pair<>(
                            new Pair<>(Long.valueOf(firstRange[0]), Long.valueOf(firstRange[1])),
                            new Pair<>(Long.valueOf(secondRange[0]), Long.valueOf(secondRange[1]))
                    );
                }).collect(Collectors.toList());

        // when
        long result = overlapChecker.getCountOfOverlappingRanges(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day4/input0.in", 2),
                Arguments.of("src/test/resources/day4/input1.in", 588)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day4/input0.in", 4),
                Arguments.of("src/test/resources/day4/input1.in", 911)
        );
    }
}
