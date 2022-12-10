package com.advent.of.code.day10;

import lombok.Value;

@Value
class NoopCommand implements Command {
    @Override
    public int execute(int x) {
        return x;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
