/*
 * CollectionUtils.java
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

/**
 *
 * @author usx19576
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

    public void increaseCount(E key) {
        baseTable.put(key, baseTable.safeGet(key, 0) + 1);
    }

    public void increaseCount(E key, int value) {
        baseTable.put(key, baseTable.safeGet(key, 0) + value);
    }

    public void decreaseCount(E key) {
        baseTable.put(key, baseTable.safeGet(key, 0) - 1);
    }

    public void decreaseCount(E key, int value) {
        baseTable.put(key, baseTable.safeGet(key, 0) - value);
    }

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

    public int getMaxCount() {
        return CollectionUtils.max(baseTable.values());
    }

}
