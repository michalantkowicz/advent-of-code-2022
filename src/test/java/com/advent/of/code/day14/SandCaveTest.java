package com.advent.of.code.day14;

import com.advent.of.code.IntPair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class SandCaveTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperAmountOfRestSand(String inputFilePath, long expectedValue) {
        // given
        final SandCave sandCave = new SandCave();
        final List<List<IntPair>> inputLines = readFileLines(inputFilePath).stream()
                .map(line -> Arrays.stream(line.trim().split(" -> "))
                        .map(s -> {
                            int x = Integer.parseInt(s.trim().split(",")[0]);
                            int y = Integer.parseInt(s.trim().split(",")[1]);
                            return new IntPair(x, y);
                        })
                        .collect(Collectors.toList())
                ).toList();

        int minX = flatten(inputLines).stream().min(Comparator.comparingInt(IntPair::getA)).get().getA();
        int maxX = flatten(inputLines).stream().max(Comparator.comparingInt(IntPair::getA)).get().getA();
        int maxY = flatten(inputLines).stream().max(Comparator.comparingInt(IntPair::getB)).get().getB();

        final IntPair sandSource = new IntPair(500 - minX, 0);

        int[][] input = new int[maxX + 1][maxY + 1];

        for (int[] row : input) {
            Arrays.fill(row, 0);
        }

        for (List<IntPair> row : inputLines) {
            for (int i = 0; i < row.size() - 1; i++) {
                final IntPair first = row.get(i);
                final IntPair second = row.get(i + 1);
                if (Objects.equals(first.getA(), second.getA())) {
                    int x = first.getA() - minX;
                    for (int y = Math.min(first.getB(), second.getB()); y <= Math.max(first.getB(), second.getB()); y++) {
                        input[x][y] = SandCave.ROCK;
                    }
                } else if (Objects.equals(first.getB(), second.getB())) {
                    int y = first.getB();
                    for (int x = Math.min(first.getA(), second.getA()); x <= Math.max(first.getA(), second.getA()); x++) {
                        input[x - minX][y] = SandCave.ROCK;
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        }

        // when
        long result = sandCave.simulateAndCountRestSand(input, sandSource);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day14/input0.in", 24L),
                Arguments.of("src/test/resources/day14/input1.in", 1078L)
        );
    }

    private static <T> List<T> flatten(List<List<T>> list) {
        return list.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}