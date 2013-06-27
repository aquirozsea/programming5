/*
 * ListMap.java
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

package programming5.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A list map is a wrapper for a hashtable of key, list pairs (each key refers to a list of related values).
 * @author andresqh
 * @version 6.0
 * TODO: Implement Map interface?
 */
public class ListMap<K, V> {

    protected HashTable<K, List<V>> baseTable;

    public ListMap() {
        baseTable = new HashTable<K, List<V>>();
    }

    public ListMap(Map<? extends K, ? extends List<V>> baseMap) {
        baseTable = new HashTable<K, List<V>>(baseMap);
    }

    public ListMap(int initialSize) {
        baseTable = new HashTable<K, List<V>>(initialSize);
    }

    public ListMap(int initialSize, float loadFactor) {
        baseTable = new HashTable<K, List<V>>(initialSize, loadFactor);
    }

    /**
     * Adds a new element to the list for the given key. If the list does not exist (first element added for that key), it will 
     * be created to hold the element
     * @param listKey the key for the list
     * @param element the element to add
     */
    public void addToList(K listKey, V element) {
        baseTable.safeGet(listKey, new ArrayList<V>()).add(element);
    }

    /**
     * @return the list associated with the given key, or null if no elements for that key have been added
     */
    public List<V> getList(K listKey) {
        return baseTable.get(listKey);
    }

    /**
     * Associates the given list with the given key, replacing the previous list, if any
     */
    public void putList(K listKey, List<V> list) {
        baseTable.put(listKey, list);
    }

    /**
     * @return the element at the given index in the list associated with the given key
     * @throws IndexOutOfBoundsException if there is no such element (or list)
     */
    public V getListElement(K listKey, int listIndex) throws IndexOutOfBoundsException {
        V ret = null;
        try {
            ret = baseTable.get(listKey).get(listIndex);
        }
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("ListMap: Could not get element from list: List does not exist for given key");
        }
        finally {
            return ret;
        }
    }

    /**
     * @param listKey the key associated with the list to search
     * @param pattern the pattern object to compare elements in the list against
     * @param comp implementation of Comparator interface
     * @return the first element in the list with the given key matching the given pattern, as determined by the given comparator
     * @throws IndexOutOfBoundsException if no such element (or list) exists
     */
    public V matchListElement(K listKey, Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        V ret = null;
        List<V> list = baseTable.get(listKey);
        if (list != null) {
            List<V> matches = CollectionUtils.findMatch(list, pattern, comp);
            if (!matches.isEmpty()) {
                ret = matches.get(0);
            }
        }
        else {
            throw new IndexOutOfBoundsException("ListMap: Could not match element from list: List does not exist for given key");
        }
        return ret;
    }

    /**
     * @param listKey the key associated with the list to search
     * @param pattern the pattern object to compare elements in the list against
     * @param comp implementation of Comparator interface
     * @return all of the elements in the list with the given key matching the given pattern, as determined by the given comparator
     * @throws IndexOutOfBoundsException if no such element (or list) exists
     */
    public List<V> matchesListElement(K listKey, Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        List<V> ret = null;
        List<V> list = baseTable.get(listKey);
        if (list != null) {
            ret = CollectionUtils.findMatch(list, pattern, comp);
        }
        else {
            throw new IndexOutOfBoundsException("ListMap: Could not match element from list: List does not exist for given key");
        }
        return ret;
    }

    /**
     * @param pattern the pattern object to compare elements against
     * @param comp implementation of Comparator interface
     * @return any element matching the given pattern, as determined by the given comparator, among all of the lists
     * @throws IndexOutOfBoundsException if no such elements exist
     */
    public V matchAllListElement(Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        V ret = null;
        List<V> list = this.getAll();
        if (list != null) {
            List<V> matches = CollectionUtils.findMatch(list, pattern, comp);
            if (!matches.isEmpty()) {
                ret = matches.get(0);
            }
        }
        else {
            throw new IndexOutOfBoundsException("ListMap: Could not match element from list: List does not exist for given key");
        }
        return ret;
    }

    /**
     * @param pattern the pattern object to compare elements against
     * @param comp implementation of Comparator interface
     * @return all elements matching the given pattern, as determined by the given comparator, among all of the lists
     * @throws IndexOutOfBoundsException if no such elements exist
     */
    public List<V> matchesAllListElement(Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        List<V> ret = null;
        List<V> list = this.getAll();
        if (list != null) {
            ret = CollectionUtils.findMatch(list, pattern, comp);
        }
        else {
            throw new IndexOutOfBoundsException("ListMap: Could not match element from list: List does not exist for given key");
        }
        return ret;
    }

    /**
     * @return a list with all elements, regardless of the keys
     */
    public List<V> getAll() {
        List<V> ret = new ArrayList<V>();
        for (List<V> list : baseTable.values()) {
            ret.addAll(list);
        }
        return ret;
    }

    /**
     * @return true if the map contains a value for the given key; false otherwise
     */
    public boolean containsKey(K key) {
        return baseTable.containsKey(key);
    }

    /**
     * @return true if the value is contained in at least one of the lists in the map; false otherwise
     */
    public boolean containsValue(V value) {
        try {
            this.matchAllListElement(value, new Comparator() {

                public int compare(Object o1, Object o2) {
                    return ((o1.equals(o2)) ? 0 : -1);
                }

            });
            return true;
        }
        catch (IndexOutOfBoundsException iobe) {
            return false;
        }
    }

    /**
     * @return the size (number of keys) stored
     */
    public int size() {
        return baseTable.size();
    }

    /**
     * @return true if no keys are stored (size() == 0); false otherwise
     */
    public boolean isEmpty() {
        return baseTable.isEmpty();
    }

    /**
     * Removes the list associated with the given key, if it exists
     * @return the list that was removed (null if no list was associated with the given key)
     */
    public List<V> remove(K listKey) {
        return baseTable.remove(listKey);
    }

    /**
     * Remove all elements from the map
     */
    public void clear() {
        baseTable.clear();
    }

    /**
     * @return the set of keys stored in this map
     */
    public Set<K> keySet() {
        return baseTable.keySet();
    }

    /**
     * Compare to getAll, which returns all elements from all lists in a single list; values preserves individual lists
     * @return a collection with all the lists associated with keys in this map
     */
    public Collection<List<V>> values() {
        return baseTable.values();
    }

    /**
     * @return the set of entries in this map
     */
    public Set<Entry<K, List<V>>> entrySet() {
        return baseTable.entrySet();
    }

}
