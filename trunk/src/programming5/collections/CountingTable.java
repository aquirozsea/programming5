/*
 * CountingTable.java
 *
 * Copyright 2010 Andres Quiroz Hernandez
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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Wrapper class for a hashtable with an integer value that can be used to keep track of counts for its key elements
 * of a given type.
 * @author aquirozh
 * @version 6.09
 */
public class CountingTable<E> {

    protected HashTable<E, Integer> baseTable;

    public CountingTable() {
        baseTable = new HashTable<E, Integer>();
    }

    public CountingTable(Map<? extends E, Integer> baseMap) {
        baseTable = new HashTable<E, Integer>(baseMap);
    }

    public CountingTable(int initialSize) {
        baseTable = new HashTable<E, Integer>(initialSize);
    }

    public CountingTable(int initialSize, float loadFactor) {
        baseTable = new HashTable<E, Integer>(initialSize, loadFactor);
    }

    /**
     * Adds one to the current value stored for the given key, or adds the key with a value of 1 if it was not 
     * previously in the table.
     * @param key the key of the item to increase
     */
    public void increaseCount(E key) {
        baseTable.put(key, baseTable.safeGet(key, 0) + 1);
    }

    /**
     * Adds the given value to the current value stored for the given key, or adds the key with the given value if
     * it was not previously in the table.
     * @param key the key of the item to increase
     * @param value the value to add to the current value for the key
     */
    public void increaseCount(E key, int value) {
        baseTable.put(key, baseTable.safeGet(key, 0) + value);
    }

    /**
     * Subtracts one from the current value stored for the given key, or adds the key with a value of -1 if it was not
     * previously in the table.
     * @param key the key of the item to decrease
     */
    public void decreaseCount(E key) {
        baseTable.put(key, baseTable.safeGet(key, 0) - 1);
    }

    /**
     * Subtracts the given value from the current value stored for the given key, or adds the key with a value of
     * -value if it was not previously in the table.
     * @param key the key of the item to decrease
     * @param value the value to subtract from the current value for the key
     */
    public void decreaseCount(E key, int value) {
        baseTable.put(key, baseTable.safeGet(key, 0) - value);
    }

    /**
     * @return the key of the item with the highest count. If there are multiple items with the highest count, 
     * only one will be returned
     */
    public E getMostFrequent() {
        E mostFrequent = null;
        int maxCount = 0;
        for (Entry<E, Integer> entry : baseTable.entrySet()) {
            if (entry.getValue() /*Count*/ > maxCount) {
                mostFrequent = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        return mostFrequent;
    }

    /**
     * @return the highest count value stored in the table
     */
    public int getMaxCount() {
        return CollectionUtils.max(baseTable.values());
    }

    public int getCount(E key) {
        return baseTable.safeGet(key, 0);
    }
    
    public Set<E> keySet() {
        return baseTable.keySet();
    }
    
    public Set<Entry<E, Integer>> entryIterator() {
        return baseTable.entrySet();
    }

    public static <T> CountingTable<T> add(CountingTable<T>... tables) {
        CountingTable<T> ret = new CountingTable<T>();
        for (CountingTable<T> table : tables) {
            for (T key : table.keySet()) {
                ret.increaseCount(key, table.getCount(key));
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return baseTable.toString();
    }

}
