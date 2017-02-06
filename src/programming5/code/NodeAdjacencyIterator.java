/*
 * NodeAdjacencyIterator.java
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

import java.util.Iterator;

/**
 * An abstract iterator used for graph traversals, which provides access to the children (adjacency list) of a graph
 * node of a given generic type. Concrete implementations of this class must provide the logic to access the child
 * objects according to the specific node type, via the Iterator interface.
 */
public abstract class NodeAdjacencyIterator<V> implements Iterator<V> {

    protected final V node;

    private Iterator<V> childIterator = null;
    private V currentChild = null;

    /**
     * Creates an iterator object with the given node as the parent node
     */
    public NodeAdjacencyIterator(V myNode) {
        node = myNode;
    }

    /**
     * @return the parent node set by the class constructor
     */
    public V getParentNode() {
        return node;
    }

    /**
     * @return an iterator over the children of the parent node
     */
    public abstract Iterator<V> getChildIterator();

    @Override
    public final boolean hasNext() {
        if (childIterator == null) {
            childIterator = this.getChildIterator();
        }
        return childIterator.hasNext();
    }

    @Override
    public final V next() {
        if (!this.hasNext()) return null;
        currentChild = childIterator.next();
        return currentChild;
    }

    public final V current() {
        return currentChild;
    }

}
