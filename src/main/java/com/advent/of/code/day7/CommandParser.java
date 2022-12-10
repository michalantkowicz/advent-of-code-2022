package com.advent.of.code.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

class CommandParser {
    long getSizeSumForDirsLowerThan(List<String> commands, long maxSize) {
        long result = 0;
        final DirNode root = parseCommands(commands);
        final List<DirNode> dirs = traverseTree(root, new ArrayList<>());

        for (DirNode dir : dirs) {
            if (dir.getSize() <= maxSize) {
                result += dir.getSize();
            }
        }

        return result;
    }

    long findMinDirSizeToDelete(List<String> commands, long discSpace, long necessarySpace) {
        final DirNode root = parseCommands(commands);
        final List<DirNode> dirs = traverseTree(root, new ArrayList<>(List.of(root)));

        long allocatedSpace = dirs.stream().filter(d -> "/".equals(d.getLabel())).findFirst().get().getSize();
        long emptySpace = discSpace - allocatedSpace;
        long cleanUpSpace = necessarySpace - emptySpace;

        long minDir = allocatedSpace;

        for (DirNode dir : dirs) {
            if (dir.getSize() < minDir && dir.getSize() >= cleanUpSpace) {
                minDir = dir.getSize();
            }
        }

        return minDir;
    }

    private List<DirNode> traverseTree(DirNode dirNode, List<DirNode> dirs) {
        for (FileNode file : dirNode.getFileChildren()) {
            updateParentsSize(file);
        }
        for (DirNode dir : dirNode.getDirChildren()) {
            dirs.add(dir);
            traverseTree(dir, dirs);
        }
        return dirs;
    }

    private void updateParentsSize(FileNode node) {
        DirNode parent = node.getParent();
        while (parent != null) {
            parent.setSize(parent.getSize() + node.getSize());
            parent = parent.getParent();
        }
    }

    private DirNode parseCommands(List<String> commands) {
        final DirNode root = new DirNode(null, "/");
        DirNode currentNode = root;
        for (int i = 0; i < commands.size(); i++) {
            final String command = commands.get(i);
            if (command.startsWith("$ cd")) {
                final String argument = command.split(" ")[2];
                if ("..".equals(argument)) {
                    currentNode = currentNode.getParent();
                } else {
                    final Optional<DirNode> node = currentNode.getDirChildren().stream()
                            .filter(c -> argument.equals(c.getLabel()))
                            .findFirst();
                    if (node.isPresent()) {
                        currentNode = node.get();
                    } else {
                        DirNode newChild = new DirNode(currentNode, argument);
                        currentNode.getDirChildren().add(newChild);
                        currentNode = newChild;
                    }
                }
            } else if (command.equals("$ ls")) {
                while ((i + 1) < commands.size() && !commands.get(i + 1).startsWith("$")) {
                    final String output = commands.get(++i);
                    final String a = output.split(" ")[0];
                    final String b = output.split(" ")[1];
                    if ("dir".equals(a)) {
                        currentNode.getDirChildren().add(new DirNode(currentNode, b));
                    } else {
                        currentNode.getFileChildren().add(new FileNode(currentNode, b, parseLong(a)));
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid command provided: [" + command + "]");
            }
        }
        return root;
    }
}
