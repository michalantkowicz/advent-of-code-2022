package com.advent.of.code.day12;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Node {
    private boolean isStart;
    private boolean isEnd;
    private Character label;
}
