package com.advent.of.code.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

public class CommandParser {
    long getSizeSumForDirsLowerThan(List<String> commands, long maxSize) {
        long result = 0;
        final Node root = parseCommands(commands);
        final List<DirNode> dirs = traverseTree(root, new ArrayList<>());

        for(DirNode dir : dirs) {
            if(dir.getSize() <= maxSize) {
                result += dir.getSize();
            }
        }

        return result;
    }

    long findMinDirSizeToDelete(List<String> commands, long discSpace, long necessarySpace) {
        final Node root = parseCommands(commands);
        final List<DirNode> dirs = traverseTree(root, new ArrayList<>());

        long allocatedSpace = dirs.stream().filter(d -> "/".equals(d.getLabel())).findFirst().get().getSize();
        long emptySpace = discSpace - allocatedSpace;
        long cleanUpSpace = necessarySpace - emptySpace;

        long minDir = allocatedSpace;

        for(DirNode dir : dirs) {
            if(dir.getSize() < minDir && dir.getSize() >= cleanUpSpace) {
                minDir = dir.getSize();
            }
        }

        return minDir;
    }

    private List<DirNode> traverseTree(Node node, List<DirNode> dirs) {
        if (node instanceof DirNode) {
            dirs.add((DirNode) node);
            for (Node child : ((DirNode) node).getChildren()) {
                traverseTree(child, dirs);
            }
        } else {
            updateParentsSize((FileNode) node);
        }
        return dirs;
    }

    private void updateParentsSize(FileNode node) {
        DirNode parent = (DirNode) node.getParent();
        while (parent != null) {
            parent.setSize(parent.getSize() + node.getSize());
            parent = (DirNode) parent.getParent();
        }
    }

    private Node parseCommands(List<String> commands) {
        final Node root = new DirNode(null, "/");
        DirNode currentNode = (DirNode) root;
        for (int i = 0; i < commands.size(); i++) {
            final String command = commands.get(i);
            if (command.startsWith("$ cd")) {
                final String argument = command.split(" ")[2];
                if ("..".equals(argument)) {
                    currentNode = (DirNode) currentNode.getParent();
                } else {
                    final Optional<Node> node = currentNode.getChildren().stream()
                            .filter(c -> argument.equals(c.getLabel()))
                            .findFirst();
                    if (node.isPresent()) {
                        currentNode = (DirNode) node.get();
                    } else {
                        DirNode newChild = new DirNode(currentNode, argument);
                        currentNode.getChildren().add(newChild);
                        currentNode = newChild;
                    }
                }
            } else if (command.equals("$ ls")) {
                while ((i + 1) < commands.size() && !commands.get(i + 1).startsWith("$")) {
                    final String output = commands.get(++i);
                    final String a = output.split(" ")[0];
                    final String b = output.split(" ")[1];
                    final Node child = "dir".equals(a) ? new DirNode(currentNode, b) : new FileNode(currentNode, b, parseLong(a));
                    currentNode.getChildren().add(child);
                }
            } else {
                throw new IllegalArgumentException("Invalid command provided: [" + command + "]");
            }
        }
        return root;
    }
}
