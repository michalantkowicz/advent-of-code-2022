package com.advent.of.code.day12;

import com.advent.of.code.IntPair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

class HillClimbingUnit {
    int getFewestSteps(Node[][] graph, IntPair rootPosition, IntPair targetPosition, int currentResult) {
        int steps = 0;

        Map<IntPair, Integer> distances = new HashMap<>();
        Map<IntPair, IntPair> parent = new HashMap<>();


        for (int x = 0; x < graph.length; x++) {
            for (int y = 0; y < graph[0].length; y++) {
                distances.put(new IntPair(x, y), Integer.MAX_VALUE);
            }
        }

        distances.put(rootPosition, 0);

        PriorityQueue<IntPair> Q = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Q.addAll(distances.keySet());

        while (!Q.isEmpty()) {
            IntPair current = Q.poll();
            for(IntPair neighbour : getNeighbours(current, graph)) {
                if(distances.get(neighbour) > distances.get(current) + 1) {
                    distances.put(neighbour, distances.get(current) + 1);
                    parent.put(neighbour, current);
                }
            }
           PriorityQueue<IntPair> temp = new PriorityQueue<>(Comparator.comparingInt(distances::get));
            temp.addAll(Q.stream().collect(Collectors.toList()));
            Q = temp;

            if(current.equals(targetPosition)) break;
            if(distances.get(current) > currentResult) break;
        }

        return distances.get(targetPosition);
    }

    List<IntPair> getNeighbours(IntPair currentPosition, Node[][] graph) {
        final List<IntPair> neighbours = new ArrayList<>();

        if (currentPosition.getA() > 0) {
            neighbours.add(new IntPair(currentPosition.getA() - 1, currentPosition.getB()));
        }
        if (currentPosition.getB() > 0) {
            neighbours.add(new IntPair(currentPosition.getA(), currentPosition.getB() - 1));
        }
        if (currentPosition.getA() < graph.length - 1) {
            neighbours.add(new IntPair(currentPosition.getA() + 1, currentPosition.getB()));
        }
        if (currentPosition.getB() < graph[0].length - 1) {
            neighbours.add(new IntPair(currentPosition.getA(), currentPosition.getB() + 1));
        }

        return neighbours.stream()
                .filter(n -> (int) graph[n.getA()][n.getB()].getLabel()
                        <=
                        (int) graph[currentPosition.getA()][currentPosition.getB()].getLabel() + 1)
                .collect(Collectors.toList());
    }
}
