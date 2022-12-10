package com.advent.of.code.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CraneController {
    String getTopsAfterMoves(List<String> stacks, List<Move> moves, boolean canPickMultipleCrates) {
        final List<String> result = new ArrayList<>(stacks);

        for (Move move : moves) {
            final String source = result.get(move.from - 1);
            final String target = result.get(move.to - 1);
            
            String partToMove = source.substring(source.length() - move.amount);
            
            if(!canPickMultipleCrates) {
                partToMove = new StringBuilder(partToMove).reverse().toString();
            }
            
            result.set(move.to - 1, target + partToMove);
            result.set(move.from - 1, source.substring(0, source.length() - move.amount));
        }

        return result.stream()
                .map(s -> s.substring(s.length() - 1))
                .collect(Collectors.joining());
    }
}
