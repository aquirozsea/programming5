/*
 * TopKSorter.java
 *
 * Copyright 2012 Andres Quiroz Hernandez
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
import java.util.List;
import java.util.TreeMap;
import programming5.collections.CollectionUtils;

/**
 * This class keeps an ordered list of the top k elements of a given type found after evaluating an element stream (or, alternatively, an
 * entire list). It uses a tree map of size k that automatically eliminates the smallest element when a k+1 element is evaluated.
 * Only k elements are kept in memory so the evaluation function is O(k).
 * @author Andres Quiroz
 * @version 6.0
 */
// TODO: Improve for speed when a sorted top k list is not needed: Only need to keep and compare against the smallest element
// TODO: Add a new TopKMap class for keeping a value along with the sorted key.
public class TopKSorter<T> {

    protected TreeMap<T, Integer> sorter = new TreeMap<T, Integer>();
    protected int k;
    private int sourceSize = 0;

    /**
     * Creates a TopKSorter for k elements
     */
    public TopKSorter(int k) {
        this.k = k;
    }

    /**
     * Considers a new element from a stream for inclusion in the top k, keeps it (temporarily, until it is no longer 
     * in the top k) if so, and discards it if not.
     */
    public void evaluate(T element) {
        sorter.put(element, sourceSize++);
        if (sorter.size() > k) {
            sorter.remove(sorter.firstKey());
        }
    }

    /**
     * @return the current list of top k elements of those evaluated so far, in descending order
     */
    public List<T> getTopK() {
        List<T> ret = new ArrayList<T>();
        for (T element : sorter.descendingKeySet()) {
            ret.add(element);
        }
        return ret;
    }

    /**
     * @return the indices associated with the top k elements, corresponding to the order in which they were evaluated
     * (starting at 0), so that getTopKIndices()[0] is the index of the top element (i.e. the number of elements evaluated
     * before it was evaluated).
     */
    public int[] getTopKIndices() {
        List<Integer> aux = new ArrayList<Integer>();
        for (T element : sorter.descendingKeySet()) {
            aux.add(sorter.get(element));
        }
        return CollectionUtils.toIntArray(aux);
    }

    /**
     * Convenience method, equivalent to getTopK().get(0)
     * @return the maximum element of those evaluated so far
     */
    public T getMax() {
        return sorter.lastKey();
    }

    /**
     * Convenience method, equivalent to getTopKIndices()[0]
     * @return the index of the maximum element of those evaluated so far (i.e. the number elements evaluated before the 
     * maximum element was evaluated)
     */
    public int getMaxIndex() {
        return sorter.lastEntry().getValue();
    }

    /**
     * Convenience method, used to automatically extract the top k elements from a list (not necessarily sorted), without 
     * creating an instance of TopKSorter.
     */
    public static <T> List<T> extractTopK(List<T> list, int k) {
        TopKSorter sorter = new TopKSorter(k);
        for (T element : list) {
            sorter.evaluate(element);
        }
        return sorter.getTopK();
    }

    /**
     * Convenience method, used to automatically extract the top k element indices from a list (not necessarily sorted),
     * without creating an instance of TopKSorter.
     */
    public static <T> int[] extractTopKIndices(List<T> list, int k) {
        TopKSorter sorter = new TopKSorter(k);
        for (T element : list) {
            sorter.evaluate(element);
        }
        return sorter.getTopKIndices();
    }

}
