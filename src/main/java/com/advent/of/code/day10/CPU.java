package com.advent.of.code.day10;

import java.util.ArrayList;
import java.util.List;

class CPU {
    int sumSignalStrengthsAt(List<Command> commands, List<Integer> cycleNumbers) {
        final List<Integer> xValues = processCommands(commands);
        int sum = 0;
        for (Integer cycle : cycleNumbers) {
            sum += (cycle * xValues.get(cycle - 1));
        }
        return sum;
    }

    String getCRTOutput(List<Command> commands, int rowWidth) {
        final List<Integer> xValues = processCommands(commands);
        StringBuilder resultBuilder = new StringBuilder();
        int row = 0;

        for (int i = 0; i < xValues.size(); i++) {
            if (i > 0 && (i % rowWidth) == 0) {
                row++;
                resultBuilder.append("\n");
            }

            int cycle = i + 1;
            int cycleX = xValues.get(i) + (row * rowWidth);

            if (cycle == cycleX || cycle == cycleX + 1 || cycle == cycleX + 2) {
                resultBuilder.append("#");
            } else {
                resultBuilder.append(".");
            }
        }

        return resultBuilder.toString();
    }

    private List<Integer> processCommands(List<Command> commands) {
        List<Integer> xValues = new ArrayList<>();
        int x = 1;

        while (!commands.isEmpty()) {
            // cycle starts
            final Command command = commands.get(0);
            int updatedX = command.execute(x);

            xValues.add(x);

            // cycle ends
            if (command.isFinished()) {
                x = updatedX;
                commands.remove(0);
            }
        }

        return xValues;
    }
}
