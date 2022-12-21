package com.advent.of.code.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Volcano {
    List<String> noZeroValves = new ArrayList<>();
    int noZeroSum = 0;

    int calculateMaxPressure(Map<String, Valve> graph) {
        noZeroValves.addAll(graph.values().stream().filter(v -> v.flowRate() > 0).map(Valve::label).toList());
        noZeroSum = noZeroValves.stream().map(graph::get).mapToInt(Valve::flowRate).sum();
        int result = getMaxPressure(graph, graph.get("AA"), "", 30, 0);
        return result;
    }

    int calculateMaxPressureWithHelperElephant(Map<String, Valve> graph) {
        noZeroValves.addAll(graph.values().stream().filter(v -> v.flowRate() > 0).map(Valve::label).toList());
        noZeroSum = noZeroValves.stream().map(graph::get).mapToInt(Valve::flowRate).sum();
        int result = getMaxPressure(graph, graph.get("AA"), graph.get("AA"), "", 26, 0);
        return result;
    }

    private record KeyRecord(String valve, String opened, int timeLeft) {
    }

    private static Map<KeyRecord, Integer> cache = new HashMap<>();

    private static int globalMax = Integer.MIN_VALUE;

    private int getMaxPressure(Map<String, Valve> graph, Valve valve, String opened, int timeLeft, int pressure) {
        int maxPressure = Integer.MIN_VALUE;

        if (pressure + timeLeft * noZeroSum < globalMax) {
            return Integer.MIN_VALUE;
        } else if (new HashSet<>(Arrays.stream(opened.split(",")).toList()).containsAll(noZeroValves)) {
            return pressure + timeLeft * noZeroSum;
        } else if (timeLeft == 0) {
            maxPressure = pressure;
        } else {
            if (!opened.isEmpty()) {
                pressure += Arrays.stream(opened.split(",")).map(graph::get).mapToInt(Valve::flowRate).sum();
            }
            if (valve.flowRate() > 0 && !opened.contains(valve.label())) {
                maxPressure = getOrUpdateCache(graph, valve, opened + (opened.isEmpty() ? "" : ",") + valve.label(), timeLeft - 1, pressure);
            }
            for (String targetLabel : valve.neighbours()) {
                final Valve neighbour = graph.get(targetLabel);
                int result = getOrUpdateCache(graph, neighbour, opened, timeLeft - 1, pressure);
                maxPressure = Math.max(maxPressure, result);
            }
            int result = getOrUpdateCache(graph, valve, opened, timeLeft - 1, pressure);
            maxPressure = Math.max(maxPressure, result);
        }
        if (maxPressure > globalMax) globalMax = maxPressure;
        return maxPressure;
    }

    private int getMaxPressure(Map<String, Valve> graph, Valve valve, Valve valve2, String opened, int timeLeft, int pressure) {
        int maxPressure = Integer.MIN_VALUE;

        if (pressure + timeLeft * noZeroSum < globalMax) {
            return Integer.MIN_VALUE;
        } else if (new HashSet<>(Arrays.stream(opened.split(",")).toList()).containsAll(noZeroValves)) {
            return pressure + timeLeft * noZeroSum;
        } else if (timeLeft == 0) {
            maxPressure = pressure;
        } else {
            if (!opened.isEmpty()) {
                pressure += Arrays.stream(opened.split(",")).map(graph::get).mapToInt(Valve::flowRate).sum();
            }
            boolean shouldOpenFirst = valve.flowRate() > 0 && !opened.contains(valve.label());
            boolean shouldOpenSecond = valve2.flowRate() > 0 && !opened.contains(valve2.label());

            if (!Objects.equals(valve.label(), valve2.label()) && shouldOpenFirst && shouldOpenSecond) {
                maxPressure = getOrUpdateCache(graph, valve, valve2, opened + (opened.isEmpty() ? "" : ",") + valve.label() + "," + valve2.label(), timeLeft - 1, pressure);
            }

            if (shouldOpenFirst && !shouldOpenSecond) {
                for (String secondTargetLabel : valve2.neighbours()) {
                    final Valve secondNeighbour = graph.get(secondTargetLabel);

                    int result = getOrUpdateCache(graph, valve, secondNeighbour, opened + (opened.isEmpty() ? "" : ",") + valve.label(), timeLeft - 1, pressure);
                    maxPressure = Math.max(maxPressure, result);
                }
            }

            if (!shouldOpenFirst && shouldOpenSecond) {
                for (String targetLabel : valve.neighbours()) {
                    final Valve neighbour = graph.get(targetLabel);

                    int result = getOrUpdateCache(graph, neighbour, valve2, opened + (opened.isEmpty() ? "" : ",") + valve2.label(), timeLeft - 1, pressure);
                    maxPressure = Math.max(maxPressure, result);
                }
            }

            for (String secondTargetLabel : valve2.neighbours()) {
                final Valve secondNeighbour = graph.get(secondTargetLabel);

                int result = getOrUpdateCache(graph, valve, secondNeighbour, opened, timeLeft - 1, pressure);
                maxPressure = Math.max(maxPressure, result);
            }

            for (String targetLabel : valve.neighbours()) {
                final Valve neighbour = graph.get(targetLabel);

                int result = getOrUpdateCache(graph, neighbour, valve2, opened, timeLeft - 1, pressure);
                maxPressure = Math.max(maxPressure, result);
            }

            for (String targetLabel : valve.neighbours()) {
                final Valve neighbour = graph.get(targetLabel);
                for (String secondTargetLabel : valve2.neighbours()) {
                    final Valve secondNeighbour = graph.get(secondTargetLabel);

                    int result = getOrUpdateCache(graph, neighbour, secondNeighbour, opened, timeLeft - 1, pressure);
                    maxPressure = Math.max(maxPressure, result);
                }
            }

            int result = getOrUpdateCache(graph, valve, valve2, opened, timeLeft - 1, pressure);
            maxPressure = Math.max(maxPressure, result);
        }
        if (maxPressure > globalMax) globalMax = maxPressure;
        return maxPressure;
    }

    private int getOrUpdateCache(Map<String, Valve> graph, Valve valve, String opened, int timeLeft, int pressure) {
        final KeyRecord key = new KeyRecord(valve.label(), opened, timeLeft);
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            int result = getMaxPressure(graph, valve, opened, timeLeft, pressure);
            cache.put(key, result);
            return result;
        }
    }

    private int getOrUpdateCache(Map<String, Valve> graph, Valve valve, Valve valve2, String opened, int timeLeft, int pressure) {
        final String label = valve.label();
        final String label2 = valve2.label();
        final String index = label.compareTo(label2) > 0 ? label2 + label : label + label2;
        final KeyRecord key = new KeyRecord(index, opened, timeLeft);
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            int result = getMaxPressure(graph, valve, valve2, opened, timeLeft, pressure);
            cache.put(key, result);
            return result;
        }
    }
}
