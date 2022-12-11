package com.advent.of.code.day11;

import com.advent.of.code.day10.AddXCommand;
import com.advent.of.code.day10.CPU;
import com.advent.of.code.day10.Command;
import com.advent.of.code.day10.NoopCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;
import static java.lang.Integer.parseInt;

class MonkeyBusinessTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperSumOfSignalStrengths(String inputFilePath, long expectedValue) {
        // given
        final CPU processor = new CPU();
        final List<Command> input = readFileLines(inputFilePath)
                .stream()
                .map(line -> line.startsWith("noop") ? new NoopCommand() : new AddXCommand(parseInt(line.split(" ")[1])))
                .collect(Collectors.toList());

        // when
        long result = processor.sumSignalStrengthsAt(input, List.of(20, 60, 100, 140, 180, 220));

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldProvideProperCRTOutput(String inputFilePath, String expectedValue) {
        // given
        final CPU processor = new CPU();
        final List<Command> input = readFileLines(inputFilePath)
                .stream()
                .map(line -> line.startsWith("noop") ? new NoopCommand() : new AddXCommand(parseInt(line.split(" ")[1])))
                .collect(Collectors.toList());

        // when
        String result = processor.getCRTOutput(input, 40);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day10/input0.in", 13140),
                Arguments.of("src/test/resources/day10/input1.in", 13440)
        );
    }
    
    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day10/input0.in", 
                        "##..##..##..##..##..##..##..##..##..##..\n" +
                        "###...###...###...###...###...###...###.\n" +
                        "####....####....####....####....####....\n" +
                        "#####.....#####.....#####.....#####.....\n" +
                        "######......######......######......####\n" +
                        "#######.......#######.......#######....."),
                Arguments.of("src/test/resources/day10/input1.in", 
                        "###..###..####..##..###...##..####..##..\n" +
                        "#..#.#..#....#.#..#.#..#.#..#....#.#..#.\n" +
                        "#..#.###....#..#....#..#.#..#...#..#..#.\n" +
                        "###..#..#..#...#.##.###..####..#...####.\n" +
                        "#....#..#.#....#..#.#.#..#..#.#....#..#.\n" +
                        "#....###..####..###.#..#.#..#.####.#..#.")
        );
    }
}