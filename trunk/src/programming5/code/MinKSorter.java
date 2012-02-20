/*
 * MinKSorter.java
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
 * This class keeps an ordered list of the min k elements of a given type found after evaluating an element stream (or, alternatively, an
 * entire list). It uses a tree map of size k that automatically eliminates the largest element when a k+1 element is evaluated.
 * Only k elements are kept in memory so the evaluation function is O(k).
 * @author Andres Quiroz
 * @version 6.0
 */
// TODO: Improve for speed when a sorted min k list is not needed: Only need to keep and compare against the largest element
// TODO: Add a new MinKMap class for keeping a value along with the sorted key.
public class MinKSorter <T> {

    protected TreeMap<T, Integer> sorter = new TreeMap<T, Integer>();
    protected int k;
    private int sourceSize = 0;

    /**
     * Creates a MinKSorter for k elements
     */
    public MinKSorter(int k) {
        this.k = k;
    }

    /**
     * Considers a new element from a stream for inclusion in the min k, keeps it (temporarily, until it is no longer
     * in the min k) if so, and discards it if not.
     */
    public void evaluate(T element) {
        sorter.put(element, sourceSize++);
        if (sorter.size() > k) {
            sorter.remove(sorter.lastKey());
        }
    }

    /**
     * @return the current list of min k elements of those evaluated so far, in ascending order
     */
    public List<T> getMinK() {
        List<T> ret = new ArrayList<T>();
        for (T element : sorter.navigableKeySet()) {
            ret.add(element);
        }
        return ret;
    }

    /**
     * @return the indices associated with the min k elements, corresponding to the order in which they were evaluated
     * (starting at 0), so that getMinKIndices()[0] is the index of the minimum element (i.e. the number of elements evaluated
     * before it was evaluated).
     */
    public int[] getMinKIndices() {
        List<Integer> aux = new ArrayList<Integer>();
        for (T element : sorter.navigableKeySet()) {
            aux.add(sorter.get(element));
        }
        return CollectionUtils.toIntArray(aux);
    }

    /**
     * Convenience method, equivalent to getMinK().get(0)
     * @return the minimum element of those evaluated so far
     */
    public T getMin() {
        return sorter.firstKey();
    }

    /**
     * Convenience method, equivalent to getMinKIndices()[0]
     * @return the index of the minimum element of those evaluated so far (i.e. the number elements evaluated before the
     * minimum element was evaluated)
     */
    public int getMinIndex() {
        return sorter.firstEntry().getValue();
    }

    /**
     * Convenience method, used to automatically extract the min k elements from a list (not necessarily sorted), without
     * creating an instance of MinKSorter.
     */
    public static <T> List<T> extractMinK(List<T> list, int k) {
        MinKSorter sorter = new MinKSorter(k);
        for (T element : list) {
            sorter.evaluate(element);
        }
        return sorter.getMinK();
    }

    /**
     * Convenience method, used to automatically extract the min k element indices from a list (not necessarily sorted),
     * without creating an instance of MinKSorter.
     */
    public static <T> int[] extractMinKIndices(List<T> list, int k) {
        MinKSorter sorter = new MinKSorter(k);
        for (T element : list) {
            sorter.evaluate(element);
        }
        return sorter.getMinKIndices();
    }

}
