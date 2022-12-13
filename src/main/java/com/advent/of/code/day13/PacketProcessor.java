package com.advent.of.code.day13;

import com.advent.of.code.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

class PacketProcessor {
    long sumRightOrderIndices(List<Pair<String, String>> packets) {
        long sumOfIndices = 0L;
        for (int i = 0; i < packets.size(); i++) {
            final Pair<String, String> packet = packets.get(i);
            if (rightOrder(packet.getA(), packet.getB())) {
                sumOfIndices += (i + 1);
            }
        }
        return sumOfIndices;
    }

    long sortAndCalculateDecoderKey(List<String> allPackets) {
        allPackets.add("[[2]]");
        allPackets.add("[[6]]");

        List<String> sortedPackets = allPackets.stream().sorted(this::rightOrderComparator).toList();

        return (long) (sortedPackets.indexOf("[[2]]") + 1) * (sortedPackets.indexOf("[[6]]") + 1);
    }

    private int rightOrderComparator(String a, String b) {
        return rightOrder(a, b) ? -1 : 1;
    }
    private boolean rightOrder(String stringA, String stringB) {
        final JSONArray a = ((JSONArray) new JSONObject("{ 'data':" + stringA + "}").get("data"));
        final JSONArray b = ((JSONArray) new JSONObject("{ 'data':" + stringB + "}").get("data"));
        return compareArrays(a, b);
    }

    private Boolean compareArrays(JSONArray a, JSONArray b) {
        for (int i = 0; i < a.length(); i++) {
            if (b.length() <= i) {
                return false;
            } else {
                if (a.get(i) instanceof Integer && b.get(i) instanceof Integer) {
                    if (a.getInt(i) < b.getInt(i)) {
                        return true;
                    } else if (b.getInt(i) < a.getInt(i)) {
                        return false;
                    }
                } else if (a.get(i) instanceof Integer && b.get(i) instanceof JSONArray) {
                    Boolean result = compareArrays(new JSONArray(List.of(a.getInt(i))), b.getJSONArray(i));
                    if (result != null) {
                        return result;
                    }
                } else if (a.get(i) instanceof JSONArray && b.get(i) instanceof Integer) {
                    Boolean result = compareArrays(a.getJSONArray(i), new JSONArray(List.of(b.getInt(i))));
                    if (result != null) {
                        return result;
                    }
                } else if (a.get(i) instanceof JSONArray && b.get(i) instanceof JSONArray) {
                    Boolean result = compareArrays(a.getJSONArray(i), b.getJSONArray(i));
                    if (result != null) {
                        return result;
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (a.length() == b.length()) {
            return null;
        }
        return true;
    }
}