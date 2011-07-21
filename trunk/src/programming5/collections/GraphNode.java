/*
 * GraphNode.java
 *
 * Copyright 2011 Andres Quiroz Hernandez
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

package programming5.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a generic wrapper for a graph node of any type. It is meant to enable generic graph operations such as traversals
 * (e.g. see @link{programming5.code.DFSTraversal}).
 * @author Andres Quiroz
 * @version 6.0
 */
public class GraphNode<T> {

    protected T content;
    protected List<GraphNode<T>> adjacent;

    /**
     * Creates an unlinked graph node to wrap the given content
     * @param nodeContent Object with node contents
     */
    public GraphNode(T nodeContent) {
        content = nodeContent;
        adjacent = new ArrayList<GraphNode<T>>();
    }

    /**
     * Creates a graph node to wrap the given content, linking it to the list of nodes. The links are directed.
     * @param nodeContent Object with node contents
     * @param adjacentNodes list of nodes adjacent to the given node
     */
    public GraphNode(T nodeContent, List<GraphNode<T>> adjacentNodes) {
        content = nodeContent;
        adjacent = adjacentNodes;
    }

    /**
     * Creates a graph node to wrap the given content, linking it with the list of nodes according to the boolean indicator.
     * @param nodeContent Object with node contents
     * @param adjacentNodes list of nodes adjacent to the given node
     * @param undirected if true, the given links will be undirected (bi-directional); otherwise, they will be directed.
     */
    public GraphNode(T nodeContent, List<GraphNode<T>> adjacentNodes, boolean undirected) {
        content = nodeContent;
        adjacent = adjacentNodes;
    }

    /**
     * Creates a directed adjacency link between the current node and the given node
     */
    public void addLink(GraphNode<T> adjacentNode) {
        adjacent.add(adjacentNode);
    }

    /**
     * Creates an undirected adjacency link between the current node and the given node
     */
    public void addUndirectedLink(GraphNode<T> adjacentNode) {
        adjacent.add(adjacentNode);
        adjacentNode.addLink(this);
    }

    /**
     * @param link the index of the adjacent node that should be retrieved, in the order that the links were added
     * @return a node that is adjacent to the current node in the order given by the link index
     */
    public GraphNode<T> getAdjacentNode(int link) {
        return adjacent.get(link);
    }

    /**
     * @return the type-specific content wrapped by the graph node
     */
    public T getContent() {
        return content;
    }

    /**
     * @return the number of nodes adjacent to the current node
     */
    public int numAdjacent() {
        return adjacent.size();
    }

}
