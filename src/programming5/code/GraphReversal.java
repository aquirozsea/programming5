/*
 * GraphReversal.java
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

import programming5.collections.GraphNode;
import programming5.collections.HashTable;
import programming5.io.Debug;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author aquirozh
 */
public class GraphReversal<T> extends DFSTraversal<T> {

    GraphNode<T> currentNode = null;
    Set<GraphNode<T>> heads = new HashSet<GraphNode<T>>();
    HashTable<T, GraphNode<T>> copied = new HashTable<T, GraphNode<T>>();

    @Override
    public void preprocessNode(T node) {
        currentNode = null;
    }

    @Override
    public void inprocessNode(T parent, T child, int link) {
        if (Debug.isEnabled()) {
            System.out.println("Inprocess (parent = " + parent.toString() + " child = " + child.toString() + ")");
            if (currentNode == null) {
                System.out.println("Current is null");
            }
            else {
                System.out.println("Current is " + currentNode.getContent().toString());
            }
        }
        if (currentNode == null) {
            GraphNode<T> head = copied.safeGet(child, new GraphNode<T>(child));
            heads.add(head);
            currentNode = head;
        }
        GraphNode<T> next = copied.safeGet(parent, new GraphNode<T>(parent));
        currentNode.addLink(next);
        currentNode = next;
    }

    public Set<GraphNode<T>> getReversedGraph() {
        Set<GraphNode<T>> ret = heads;
        currentNode = null;
        heads = new HashSet<GraphNode<T>>();
        return ret;
    }

    public GraphNode<T> getRootedReversedGraph() {
        GraphNode<T> root = new GraphNode<T>(null);
        for (GraphNode<T> head : heads) {
            root.addLink(head);
        }
        currentNode = null;
        heads = new HashSet<GraphNode<T>>();
        return root;
    }

    @Override
    public Set<GraphNode<T>> getGraphNodes() {
        return new HashSet<GraphNode<T>>(copied.values());
    }

}
