/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.code;

import java.util.HashSet;
import java.util.Set;
import programming5.collections.GraphNode;
import programming5.collections.HashTable;
import programming5.io.Debug;

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
