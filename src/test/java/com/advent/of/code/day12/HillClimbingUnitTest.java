package com.advent.of.code.day12;

import com.advent.of.code.IntPair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class HillClimbingUnitTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperMonkeyBusiness(String inputFilePath, int expectedValue) {
        // given
        final HillClimbingUnit climbingUnit = new HillClimbingUnit();
        final List<String> inputLines = readFileLines(inputFilePath);

        IntPair rootPosition = null;
        IntPair targetPosition = null;
        final Node[][] input = new Node[inputLines.get(0).length()][inputLines.size()];

        for (int y = 0; y < inputLines.size(); y++) {
            for (int x = 0; x < inputLines.get(y).length(); x++) {
                final char c = inputLines.get(y).charAt(x);
                if (c == 'S') {
                    input[x][y] = new Node(true, false, 'a');
                    rootPosition = new IntPair(x, y);
                } else if (c == 'E') {
                    input[x][y] = new Node(false, true, 'z');
                    targetPosition = new IntPair(x, y);
                } else {
                    input[x][y] = new Node(false, false, c);
                }
            }
        }

        // when
        int result = climbingUnit.getFewestSteps(input, rootPosition, targetPosition, Integer.MAX_VALUE);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Disabled("It's running very long")
    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateProperDistanceWhenStartingFromAllA(String inputFilePath, int expectedValue) {
        // given
        final HillClimbingUnit climbingUnit = new HillClimbingUnit();
        final List<String> inputLines = readFileLines(inputFilePath);

        List<IntPair> rootPositions = new ArrayList<>();
        IntPair targetPosition = null;
        final Node[][] input = new Node[inputLines.get(0).length()][inputLines.size()];

        for (int y = 0; y < inputLines.size(); y++) {
            for (int x = 0; x < inputLines.get(y).length(); x++) {
                final char c = inputLines.get(y).charAt(x);
                if (c == 'S') {
                    input[x][y] = new Node(true, false, 'a');
                    rootPositions.add(new IntPair(x, y));
                } else if (c == 'E') {
                    input[x][y] = new Node(false, true, 'z');
                    targetPosition = new IntPair(x, y);
                } else {
                    input[x][y] = new Node(false, false, c);
                    if(c == 'a') {
                        rootPositions.add(new IntPair(x, y));
                    }
                }
            }
        }

        // when
        int result = Integer.MAX_VALUE;
        int i = 0;
        for(IntPair rootPosition : rootPositions) {
            int temp = climbingUnit.getFewestSteps(input, rootPosition, targetPosition, result);
            if(temp < result) {
                result = temp;
            }
            System.out.println(i++);
        }

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day12/input0.in", 31),
                Arguments.of("src/test/resources/day12/input1.in", 520)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day12/input0.in", 29),
                Arguments.of("src/test/resources/day12/input2.in", 4)
        );
    }
}