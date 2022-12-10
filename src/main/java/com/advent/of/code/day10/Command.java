package com.advent.of.code.day10;

interface Command {
    int execute(int x);
    boolean isFinished();
}
