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
public class ListMap<K, V> extends CollectionMap<K, V> {

    public ListMap() {
        super();
    }

    public ListMap(Map<? extends K, ? extends List<V>> baseMap) {
        super(baseMap);
    }

    public ListMap(int initialSize) {
        super(initialSize);
    }

    public ListMap(int initialSize, float loadFactor) {
        super(initialSize, loadFactor);
    }
    
    @Override
    protected Collection<V> newCollection() {
        return new ArrayList<V>();
    }


    /**
     * Adds a new element to the list for the given key. If the list does not exist (first element added for that key), it will 
     * be created to hold the element
     * @param listKey the key for the list
     * @param element the element to add
     * @deprecated replaced by functionality as CollectionMap#addToCollection
     */
    @Deprecated
    public void addToList(K listKey, V element) {
        baseTable.safeGet(listKey, new ArrayList<V>()).add(element);
    }

    /**
     * Convenience method that saves having to cast the return value from CollectionMap#getCollection
     * @return the list associated with the given key, or null if no elements for that key have been added
     */
    public List<V> getList(K listKey) {
        return (List<V>) this.getCollection(listKey);
    }

    /**
     * Associates the given list with the given key, replacing the previous list, if any
     * @deprecated replaced by functionality of CollectionMap#putCollection
     */
    @Deprecated
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
            ret = ((List<V>) baseTable.get(listKey)).get(listIndex);
        }
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("ListMap: Could not get element from list: List does not exist for given key");
        }
        finally {
            return ret;
        }
    }

    /**
     * @deprecated replaced by functionality as CollectionMap#matchCollectionElement
     * @param listKey the key associated with the list to search
     * @param pattern the pattern object to compare elements in the list against
     * @param comp implementation of Comparator interface
     * @return the first element in the list with the given key matching the given pattern, as determined by the given comparator
     * @throws IndexOutOfBoundsException if no such element (or list) exists
     */
    @Deprecated
    public V matchListElement(K listKey, Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        V ret = null;
        List<V> list = (List<V>) baseTable.get(listKey);
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
     * @deprecated replaced by functionality as CollectionMap#matchesCollectionElement
     * @param listKey the key associated with the list to search
     * @param pattern the pattern object to compare elements in the list against
     * @param comp implementation of Comparator interface
     * @return all of the elements in the list with the given key matching the given pattern, as determined by the given comparator
     * @throws IndexOutOfBoundsException if no such element (or list) exists
     */
    @Deprecated
    public List<V> matchesListElement(K listKey, Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        List<V> ret = null;
        List<V> list = (List<V>) baseTable.get(listKey);
        if (list != null) {
            ret = CollectionUtils.findMatch(list, pattern, comp);
        }
        else {
            throw new IndexOutOfBoundsException("ListMap: Could not match element from list: List does not exist for given key");
        }
        return ret;
    }

    /**
     * @deprecated replaced by functionality as CollectionMap#matchAllCollectionElement
     * @param pattern the pattern object to compare elements against
     * @param comp implementation of Comparator interface
     * @return any element matching the given pattern, as determined by the given comparator, among all of the lists
     * @throws IndexOutOfBoundsException if no such elements exist
     */
    @Deprecated
    public V matchAllListElement(Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        V ret = null;
        List<V> list = (List<V>) this.getAll();
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
     * @deprecated replaced by functionality as CollectionMap#matchesAllCollectionElement
     * @param pattern the pattern object to compare elements against
     * @param comp implementation of Comparator interface
     * @return all elements matching the given pattern, as determined by the given comparator, among all of the lists
     * @throws IndexOutOfBoundsException if no such elements exist
     */
    @Deprecated
    public List<V> matchesAllListElement(Object pattern, Comparator comp) throws IndexOutOfBoundsException {
        List<V> ret = null;
        List<V> list = (List<V>) this.getAll();
        if (list != null) {
            ret = CollectionUtils.findMatch(list, pattern, comp);
        }
        else {
            throw new IndexOutOfBoundsException("ListMap: Could not match element from list: List does not exist for given key");
        }
        return ret;
    }

}
