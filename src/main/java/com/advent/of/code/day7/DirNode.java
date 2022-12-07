package com.advent.of.code.day7;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class DirNode {
    private final DirNode parent;
    private final String label;

    private long size = 0L;

    private List<DirNode> dirChildren = new ArrayList<>();
    private List<FileNode> fileChildren = new ArrayList<>();

    @Override
    public String toString() {
        return label + "[" + dirChildren.size() + " dirs, " + fileChildren.size() + "files]";
    }
}
