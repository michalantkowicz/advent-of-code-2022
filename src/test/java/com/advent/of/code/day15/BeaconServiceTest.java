package com.advent.of.code.day15;

import com.advent.of.code.IntPair;
import com.advent.of.code.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.advent.of.code.Utils.readFileLines;
import static java.lang.Integer.parseInt;

class BeaconServiceTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForFirstTask")
    void test_shouldCalculateProperAmountOfReservedTiles(String inputFilePath, int row, long expectedValue) {
        // given
        final BeaconService beaconService = new BeaconService();
        final List<Pair<IntPair, IntPair>> input = readFileLines(inputFilePath).stream()
                .map(line -> {
                            final String[] split = line.trim().split(": closest beacon is at ");
                            final String[] sensorSplit = split[0].trim().substring(12).split(", y=");
                            final String[] beaconSplit = split[1].trim().substring(2).split(", y=");
                            final IntPair sensorPosition = new IntPair(parseInt(sensorSplit[0]), parseInt(sensorSplit[1]));
                            final IntPair beaconPosition = new IntPair(parseInt(beaconSplit[0]), parseInt(beaconSplit[1]));
                            return new Pair<>(sensorPosition, beaconPosition);
                        }
                ).toList();

        // when
        long result = beaconService.countReservedAt(input, row);

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForSecondTask")
    void test_shouldCalculateProperAmountOfReservedTilesWithBoundaries(String inputFilePath, int min, int max, long expectedValue) {
        // given
        final BeaconService beaconService = new BeaconService();
        final List<Pair<IntPair, IntPair>> input = readFileLines(inputFilePath).stream()
                .map(line -> {
                            final String[] split = line.trim().split(": closest beacon is at ");
                            final String[] sensorSplit = split[0].trim().substring(12).split(", y=");
                            final String[] beaconSplit = split[1].trim().substring(2).split(", y=");
                            final IntPair sensorPosition = new IntPair(parseInt(sensorSplit[0]), parseInt(sensorSplit[1]));
                            final IntPair beaconPosition = new IntPair(parseInt(beaconSplit[0]), parseInt(beaconSplit[1]));
                            return new Pair<>(sensorPosition, beaconPosition);
                        }
                ).toList();

        // when
        long result = -1;
        for (int i = min; i <= max; i++) {
            if (beaconService.countReservedAtWithBoundaries(input, i, min, max) < (max - min + 1)) {
                result = beaconService.findEmptyPlace(input, i, min, max).stream().toList().get(0) * 4_000_000L + i;
                break;
            }
        }

        // then
        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> provideArgumentsForFirstTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day15/input0.in", 10, 26L),
                Arguments.of("src/test/resources/day15/input1.in", 2000000, 5147333L)
        );
    }

    private static Stream<Arguments> provideArgumentsForSecondTask() {
        return Stream.of(
                Arguments.of("src/test/resources/day15/input0.in", 0, 20, 56000011L),
                Arguments.of("src/test/resources/day15/input1.in", 0, 4_000_000, 13734006908372L)
        );
    }
}