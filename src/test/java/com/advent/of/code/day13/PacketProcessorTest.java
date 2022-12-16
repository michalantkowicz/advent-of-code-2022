package com.advent.of.code.day13;

import com.advent.of.code.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;

class PacketProcessorTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperSum(String inputFilePath, long expectedValue) {
        // given
        final PacketProcessor packetProcessor = new PacketProcessor();
        final List<String> inputLines = readFileLines(inputFilePath);

        List<Pair<String, String>> input = new ArrayList<>();

        for (int i = 0; i < inputLines.size(); i++) {
            if (inputLines.get(i).isEmpty()) {
                input.add(new Pair<>(inputLines.get(i - 2), inputLines.get(i - 1)));
            }
        }
        input.add(new Pair<>(inputLines.get(inputLines.size() - 2), inputLines.get(inputLines.size() - 1)));

        // when
        long result = packetProcessor.sumRightOrderIndices(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateProperDecoderKey(String inputFilePath, long expectedValue) {
        // given
        final PacketProcessor packetProcessor = new PacketProcessor();
        final List<String> input = readFileLines(inputFilePath)
                .stream()
                .filter(l -> !l.isEmpty())
                .collect(Collectors.toList());

        // when
        long result = packetProcessor.sortAndCalculateDecoderKey(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day13/input0.in", 13L),
                Arguments.of("src/test/resources/day13/input1.in", 6076L)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day13/input0.in", 140L),
                Arguments.of("src/test/resources/day13/input1.in", 24805L)
        );
    }
}