package com.advent.of.code.day16;

import java.util.List;

public record Valve(String label, int flowRate, List<String> neighbours) {
}
