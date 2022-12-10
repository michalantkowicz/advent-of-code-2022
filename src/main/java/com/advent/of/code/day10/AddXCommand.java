package com.advent.of.code.day10;

import lombok.Data;

@Data
class AddXCommand implements Command {
    private final int value;
    private int cyclesLeft = 2;

    @Override
    public int execute(int x) {
        cyclesLeft--;
        if (cyclesLeft == 0) {
            return x + value;
        } else {
            return x;
        }
    }

    @Override
    public boolean isFinished() {
        return cyclesLeft == 0;
    }
}
