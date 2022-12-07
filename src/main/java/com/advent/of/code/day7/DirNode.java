package com.advent.of.code.day7;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class DirNode implements Node {
    private final Node parent;
    private final String label;

    private long size = 0L;

    private List<Node> children = new ArrayList<>();

    @Override
    public String toString() {
        return label + "[" + children.size() + " children]";
    }
}
