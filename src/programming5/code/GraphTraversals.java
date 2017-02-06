/*
 * GraphTraversals.java
 *
 * Copyright 2015 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package programming5.code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Functional class that provides methods to traverse graph structures for arbitrary object types, using node and edge
 * processor functions. Users must provide a childWalker function to the traversal methods, which creates a
 * {@link NodeAdjacencyIterator} for a given node in order to iterate over the each node's children.
 */
public abstract class GraphTraversals {

    /**
     * Performs a depth first walk of the connected graph rooted at the given start node, processing nodes and/or
     * edges with the given functions
     * @param start the node at which the walk will be started
     * @param childWalker a function that provides a child iterator for a given node
     * @param nodePreprocessor function that will be called on a node when first visited (discovered) (may be identity
     *                         function or null)
     * @param edgePreprocessor function that will be called on first traversal of an edge (may be identity function or
     *                         null)
     * @param edgePostprocessor function that will be called when returning to a parent after the child corresponding to
     *                          the edge has been processed (may be identity function or null)
     * @param nodePostprocessor function that will be called on a node after all of its children have been
     *                          processed (may be identity function or null)
     */
    public static <V> void depthFirstWalk(V start, Function<V, NodeAdjacencyIterator<V>> childWalker, Consumer<V> nodePreprocessor, Consumer<Edge<V>> edgePreprocessor, Consumer<Edge<V>> edgePostprocessor, Consumer<V> nodePostprocessor) {
        if (start == null) return;
        // Create optionals in case of null inputs
        Optional<Consumer<V>> nodePreprocessorOpt = Optional.ofNullable(nodePreprocessor);
        Optional<Consumer<V>> nodePostprocessorOpt = Optional.ofNullable(nodePostprocessor);
        Optional<Consumer<Edge<V>>> edgePreprocessorOpt = Optional.ofNullable(edgePreprocessor);
        Optional<Consumer<Edge<V>>> edgePostprocessorOpt = Optional.ofNullable(edgePostprocessor);
        // DFS
        Stack<NodeAdjacencyIterator<V>> dfsStack = new Stack<>();
        Set<V> visited = new HashSet<>();
        nodePreprocessorOpt.ifPresent(pre -> pre.accept(start));
        if (childWalker == null) {
            nodePostprocessorOpt.ifPresent(post -> post.accept(start));
            return;
        }
        dfsStack.push(childWalker.apply(start));
        visited.add(start);
        while (!dfsStack.isEmpty()) {
            NodeAdjacencyIterator<V> currentNode = dfsStack.peek();
            try {
                Optional<V> currentChild = Optional.ofNullable(currentNode.current());
                final V parent = currentNode.getParentNode();
                currentChild.ifPresent(node -> edgePostprocessorOpt.ifPresent(post -> post.accept(new Edge<>(parent, node))));
            }
            catch (UnsupportedOperationException uoe) {}
            if (currentNode.hasNext()) {
                V nextNode = currentNode.next();
                if (!visited.contains(nextNode)) {
                    edgePreprocessorOpt.ifPresent(pre -> pre.accept(new Edge<>(currentNode.getParentNode(), nextNode)));
                    nodePreprocessorOpt.ifPresent(pre -> pre.accept(nextNode));
                    dfsStack.push(childWalker.apply(nextNode));
                    visited.add(nextNode);
                }
            }
            else {
                nodePostprocessorOpt.ifPresent(post -> post.accept(currentNode.getParentNode()));
                dfsStack.pop();
            }
        }
    }

    /**
     * Convenience method to perform a depth first walk while only providing node processing functions (not edge
     * processing functions)
     * @param start the node at which the walk will be started
     * @param childWalker a function that provides a child iterator for a given node
     * @param nodePreprocessor function that will be called on a node when first visited (discovered) (may be identity
     *                         function or null)
     * @param nodePostprocessor function that will be called on a node after all of its children have been
     *                          processed (may be identity function or null)
     */
    public static <V> void depthFirstNodeWalk(V start, Function<V, NodeAdjacencyIterator<V>> childWalker, Consumer<V> nodePreprocessor, Consumer<V> nodePostprocessor) {
        depthFirstWalk(start, childWalker, nodePreprocessor, edge -> {}, edge -> {}, nodePostprocessor);
    }

    /**
     * Convenience method to perform a depth first walk while processing nodes in pre-order
     * @param start the node at which the walk will be started
     * @param childWalker a function that provides a child iterator for a given node
     * @param nodePreprocessor function that will be called on a node when first visited (discovered) (should not be
     *                         identity function or null; method will return immediately without performing the walk
     *                         in the latter case)
     */
    public static <V> void depthFirstNodePreprocess(V start, Function<V, NodeAdjacencyIterator<V>> childWalker, Consumer<V> nodePreprocessor) {
        if (nodePreprocessor == null) return;
        depthFirstNodeWalk(start, childWalker, nodePreprocessor, node -> {});
    }

    /**
     * Convenience method to perform a depth first walk while processing nodes in post-order
     * @param start the node at which the walk will be started
     * @param childWalker a function that provides a child iterator for a given node
     * @param nodePostprocessor function that will be called on a node after all of its children have been
     *                          processed (should not be identity function or null; method will return immediately
     *                          without performing the walk in the latter case)
     */
    public static <V> void depthFirstNodePostprocess(V start, Function<V, NodeAdjacencyIterator<V>> childWalker, Consumer<V> nodePostprocessor) {
        if (nodePostprocessor == null) return;
        depthFirstNodeWalk(start, childWalker, node -> {}, nodePostprocessor);
    }

    /**
     * Convenience method to perform a depth first walk while only providing edge processing functions (not node processing
     * functions)
     * @param start the node at which the walk will be started
     * @param childWalker a function that provides a child iterator for a given node
     * @param edgePreprocessor function that will be called on first traversal of an edge (may be identity function or
     *                         null)
     * @param edgePostprocessor function that will be called when returning to a parent after the child corresponding to
     *                          the edge has been processed (may be identity function or null)
     */
    public static <V> void depthFirstEdgeWalk(V start, Function<V, NodeAdjacencyIterator<V>> childWalker, Consumer<Edge<V>> edgePreprocessor, Consumer<Edge<V>> edgePostprocessor) {
        depthFirstWalk(start, childWalker, node -> {}, edgePreprocessor, edgePostprocessor, node -> {});
    }

    /**
     * Convenience method to perform a depth first walk while processing edges in pre-order
     * @param start the node at which the walk will be started
     * @param childWalker a function that provides a child iterator for a given node
     * @param edgePreprocessor function that will be called on first traversal of an edge (should not be identity
     *                         function or null; method will return immediately without performing the walk in the latter
     *                         case)
     */
    public static <V> void depthFirstEdgePreprocess(V start, Function<V, NodeAdjacencyIterator<V>> childWalker, Consumer<Edge<V>> edgePreprocessor) {
        if (edgePreprocessor == null) return;
        depthFirstEdgeWalk(start, childWalker, edgePreprocessor, edge -> {});
    }

    /**
     * Convenience method to perform a depth first walk while processing edges in post-order
     * @param start the node at which the walk will be started
     * @param childWalker a function that provides a child iterator for a given node
     * @param edgePostprocessor function that will be called when returning to a parent after the child corresponding to
     *                          the edge has been processed (should not be identity function or null; method will return
     *                          immediately without performing the walk in the latter case)
     */
    public static <V> void depthFirstEdgePostprocess(V start, Function<V, NodeAdjacencyIterator<V>> childWalker, Consumer<Edge<V>> edgePostprocessor) {
        if (edgePostprocessor == null) return;
        depthFirstEdgeWalk(start, childWalker, edge -> {}, edgePostprocessor);
    }

    /**
     * Returns a list containing the nodes of the connected graph rooted at the given start node in pre-order (in the
     * order that they are first visited in a depth first graph traversal)
     * @param start
     * @param childWalker
     * @param <V>
     * @return
     */
    public static <V> List<V> graphPreOrder(V start, Function<V, NodeAdjacencyIterator<V>> childWalker) {
        final List<V> preorder = new ArrayList<>();
        depthFirstNodeWalk(start, childWalker, preorder::add, node -> {});
        return preorder;
    }

    public static <V> List<V> graphPostOrder(V start, Function<V, NodeAdjacencyIterator<V>> childWalker) {
        final List<V> postorder = new ArrayList<>();
        depthFirstNodeWalk(start, childWalker, node -> {}, postorder::add);
        return postorder;
    }

    public static class Edge<V> {

        private final V parent;
        private final V child;

        public Edge(V parent, V child) {
            this.parent = parent;
            this.child = child;
        }

        public V getParent() {
            return parent;
        }

        public V getChild() {
            return child;
        }

    }
}
