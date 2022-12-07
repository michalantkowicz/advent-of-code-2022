package com.advent.of.code.day7;

import lombok.Value;

@Value
class FileNode {
    DirNode parent;
    String label;
    long size;

    @Override
    public String toString() {
        return label + "[" + size + " size]";
    }
}