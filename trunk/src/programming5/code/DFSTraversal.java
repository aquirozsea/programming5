/*
 * CollectionUtils.java
 *
 * Copyright 2009 Andres Quiroz Hernandez
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

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import programming5.collections.GraphNode;
import programming5.collections.HashTable;

/**
 * Implements a depth-first search traversal of a graph structure consisting of nodes of a given type. The graph structure must be composed
 * of nodes of the given type wrapped by @link{programming5.collections.GraphNode} objects. The class can (should) be extended to
 * provide specific processing operations for node content.
 * @author Andres Quiroz
 * @version 6.0
 */
public class DFSTraversal<T> {

    protected Stack<GraphNode<T>> dfsNodeStack = new Stack<GraphNode<T>>();
    protected Stack<Integer> dfsLinkStack = new Stack<Integer>();
    protected HashTable<GraphNode<T>, Boolean> visited = new HashTable<GraphNode<T>, Boolean>();

    /**
     * Default operation (nop) for a node content when the node is first encountered in the DFS
     * @param node the content object wrapped by the encountered graph node
     */
    public void preprocessNode(T node)  {}

    /**
     * Default operation (nop) for a node content after a child branch has been completely processed
     * (note that inprocess is not called for leaf nodes)
     * @param parent the node whose downstream graph is currently being explored (parent node)
     * @param child the child node whose downstream graph has been processed
     * @param link the index of the child node within the adjacent nodes of the parent node
     */
    public void inprocessNode(T parent, T child, int link) {}

    /**
     * Default operation (nop) for a node content after all of its neighbors have been completely processed
     * @param node the content object
     */
    public void postprocessNode(T node) {}

    public void reProcessNode(T node, T from) {}

    public Set<GraphNode<T>> getGraphNodes() {
        return new HashSet<GraphNode<T>>(visited.keySet());
    }

    /**
     * Invoked to execute the DFS completely
     * @param head the node to start the DFS from
     */
    public void start(GraphNode<T> head) {
        preprocessNode(head.getContent());
        dfsNodeStack.push(head);
        dfsLinkStack.push(0);
        visited.put(head, Boolean.TRUE);
        while (!dfsNodeStack.isEmpty()) {
            GraphNode<T> currentNode = dfsNodeStack.peek();
            int currentLink = dfsLinkStack.pop();
            if (currentLink > 0) {
                inprocessNode(currentNode.getContent(), currentNode.getAdjacentNode(currentLink-1).getContent(), currentLink-1);
            }
            if (currentLink < currentNode.numAdjacent()) {
                dfsLinkStack.push(currentLink + 1);
                GraphNode<T> nextNode = currentNode.getAdjacentNode(currentLink);
                if (!visited.safeGet(nextNode, Boolean.FALSE)) {
                    preprocessNode(nextNode.getContent());
                    dfsNodeStack.push(nextNode);
                    dfsLinkStack.push(0);
                    visited.put(nextNode, Boolean.TRUE);
                }
                else {
                    reProcessNode(nextNode.getContent(), currentNode.getContent());
                }
            }
            else {
                postprocessNode(currentNode.getContent());
                dfsNodeStack.pop();
            }
        }
    }

}
