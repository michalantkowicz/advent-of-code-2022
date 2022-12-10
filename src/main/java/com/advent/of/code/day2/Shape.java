package com.advent.of.code.day2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
enum Shape {
    ROCK(1, List.of("A", "X")), PAPER(2, List.of("B", "Y")), SCISSORS(3, List.of("C", "Z"));

    static {
        ROCK.winWith = SCISSORS;
        ROCK.looseWith = PAPER;
        PAPER.winWith = ROCK;
        PAPER.looseWith = SCISSORS;
        SCISSORS.winWith = PAPER;
        SCISSORS.looseWith = ROCK;
    }

    @Getter
    private final int score;
    private final List<String> codes;
    private Shape winWith, looseWith;

    static Shape decode(String code) {
        for (Shape shape : Shape.values()) {
            if (shape.codes.contains(code)) {
                return shape;
            }
        }
        throw new IllegalArgumentException("Provided code [" + code + "] does not match to any of shapes");
    }

    static Shape decodeDueToStrategy(String firstCode, String code) {
        final Shape firstShape = decode(firstCode);

        if ("Y".equals(code)) {
            return firstShape;
        } else if ("X".equals(code)) {
            return firstShape.winWith;
        } else if ("Z".equals(code)) {
            return firstShape.looseWith;
        }

        throw new IllegalArgumentException("Provided code [" + code + "] does not match to any of strategies");
    }
}
