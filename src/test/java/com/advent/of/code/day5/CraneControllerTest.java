package com.advent.of.code.day5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class CraneControllerTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldReturnProperTops(String inputFilePath, String expectedValue) {
        // given
        final CraneController craneController = new CraneController();
        final List<String> lines = readFileLines(inputFilePath);

        int emptyLineIndex = lines.indexOf("");
        int stacksCount = getStacksCount(lines);

        final List<String> stacks = trimAndReverse(getStacks(lines, stacksCount, emptyLineIndex));
        final List<Move> moves = getMoves(lines, emptyLineIndex);

        // when
        String result = craneController.getTopsAfterMoves(stacks, moves, false);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldReturnProperTopsWhenAbleToPickMultipleCrates(String inputFilePath, String expectedValue) {
        // given
        final CraneController craneController = new CraneController();
        final List<String> lines = readFileLines(inputFilePath);

        int emptyLineIndex = lines.indexOf("");
        int stacksCount = getStacksCount(lines);

        final List<String> stacks = trimAndReverse(getStacks(lines, stacksCount, emptyLineIndex));
        final List<Move> moves = getMoves(lines, emptyLineIndex);

        // when
        String result = craneController.getTopsAfterMoves(stacks, moves, true);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private List<String> getStacks(List<String> lines, int stacksCount, int emptyLineIndex) {
        final List<String> result = Stream.generate(String::new)
                .limit(stacksCount)
                .collect(Collectors.toList());
        for (int i = 0; i < emptyLineIndex - 1; i++) {
            for (int j = 0; j < stacksCount; j++) {
                final String currentStack = result.get(j);
                int stackElementIndex = 1 + j * 4;
                result.set(j, currentStack + lines.get(i).charAt(stackElementIndex));
            }
        }
        return result;
    }

    private List<String> trimAndReverse(List<String> stacks) {
        return stacks.stream()
                .map(String::trim)
                .map(s -> new StringBuilder(s).reverse().toString())
                .collect(Collectors.toList());
    }

    private int getStacksCount(List<String> lines) {
        int stacksCount = 1;
        final String firstLine = lines.get(0);
        for (int i = 1; i + 4 < firstLine.length(); i += 4) {
            stacksCount++;
        }
        return stacksCount;
    }

    private List<Move> getMoves(List<String> lines, int emptyLineIndex) {
        return lines.stream()
                .skip(emptyLineIndex + 1)
                .map(line -> {
                    final String[] split = line.split(" ");
                    return new Move(
                            Integer.parseInt(split[3]),
                            Integer.parseInt(split[5]),
                            Integer.parseInt(split[1])
                    );
                })
                .collect(Collectors.toList());
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day5/input0.in", "CMZ"),
                Arguments.of("src/test/resources/day5/input1.in", "CVCWCRTVQ")
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day5/input0.in", "MCD"),
                Arguments.of("src/test/resources/day5/input1.in", "CNSCZWLVT")
        );
    }
}