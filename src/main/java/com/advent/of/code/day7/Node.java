package com.advent.of.code.day7;

import java.util.List;

interface Node {
    Node getParent();
    String getLabel();
    long getSize();
}
