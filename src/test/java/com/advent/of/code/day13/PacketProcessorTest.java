package com.advent.of.code.day13;

import com.advent.of.code.Pair;
import com.advent.of.code.day11.Monkey;
import com.advent.of.code.day11.MonkeyBusiness;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFile;
import static com.advent.of.code.Utils.readFileLines;
import static org.junit.jupiter.api.Assertions.*;

class PacketProcessorTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperSum(String inputFilePath, long expectedValue) {
        // given
        final PacketProcessor packetProcessor = new PacketProcessor();
        final List<String> inputLines = readFileLines(inputFilePath);

        List<Pair<Packet, Packet>> input = new ArrayList<>();
        
        input.add(new Pair<>(null, null));
        
        for(String line : inputLines) {
            
        }
        
        // when
        long result = packetProcessor.sumRightOrderIndices(input);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day13/input0.in", 10605L),
                Arguments.of("src/test/resources/day13/input1.in", 107822L)
        );
    }
}