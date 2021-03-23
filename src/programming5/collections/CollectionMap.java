/*
 * CollectionMap.java
 *
 * Copyright 2014 Andres Quiroz Hernandez
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

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Base class for ListMap and SetMap classes, and in general for maintaining multiple collections by a key, with shortcuts for updating
 * individual collections by key. 
 * @author andresqh
 */
public abstract class CollectionMap<K, V> {

    protected HashTable<K, Collection<V>> baseTable;

    public CollectionMap() {
        baseTable = new HashTable<K, Collection<V>>();
    }

    public CollectionMap(Map<? extends K, ? extends Collection<V>> baseMap) {
        baseTable = new HashTable<K, Collection<V>>(baseMap);
    }

    public CollectionMap(int initialSize) {
        baseTable = new HashTable<K, Collection<V>>(initialSize);
    }

    public CollectionMap(int initialSize, float loadFactor) {
        baseTable = new HashTable<K, Collection<V>>(initialSize, loadFactor);
    }

    protected abstract Collection<V> newCollection();

    /**
     * Adds a new element to the Collection for the given key. If the Collection does not exist (first element added for that key), it will
     * be created to hold the element
     * @param collectionKey the key for the Collection
     * @param element the element to add
     */
    public void addToCollection(K collectionKey, V element) {
        baseTable.safeGet(collectionKey, newCollection()).add(element);
    }

    /**
     * @return the Collection associated with the given key, or null if no elements for that key have been added
     */
    public Collection<V> getCollection(K collectionKey) {
        return baseTable.get(collectionKey);
    }

    /**
     * Associates the given Collection with the given key, replacing the previous Collection, if any
     */
    public void putCollection(K collectionKey, Collection<V> collection) {
        baseTable.put(collectionKey, collection);
    }

    /**
     * @param collectionKey the key associated with the Collection to search
     * @param matcher predicate to compare elements in the Collection against
     * @return the "first" element in the Collection with the given key matching the given pattern, as determined by the given comparator
     * @throws NotFoundException if no such element (or Collection) exists
     */
    public V matchCollectionElement(K collectionKey, Predicate<V> matcher) throws NotFoundException {
        Collection<V> collection = baseTable.get(collectionKey);
        if (collection != null) {
            return collection.stream()
                    .filter(matcher)
                    .findFirst()
                    .orElse(null);
        }
        else {
            throw new NotFoundException("CollectionMap: Could not match element from Collection: Collection does not exist for given key");
        }
    }

    /**
     * @param collectionKey the key associated with the Collection to search
     * @return all of the elements in the Collection with the given key matching the given predicate
     * (may return an empty collection if no elements match)
     * @throws NotFoundException if no collection with the given key exists
     */
    public Set<V> matchesCollectionElement(K collectionKey, Predicate<V> matcher) throws NotFoundException {
        Collection<V> collection = baseTable.get(collectionKey);
        if (collection != null) {
            return collection.stream()
                    .filter(matcher)
                    .collect(Collectors.toSet());
        }
        else {
            throw new NotFoundException("CollectionMap: Could not match element from Collection: Collection does not exist for given key");
        }
    }

    /**
     * @param matcher the predicate to compare elements against
     * @return any element matching the given pattern, as determined by the given comparator, among all of the Collections
     * @throws NotFoundException if no such elements exist
     */
    public V matchAllCollectionElement(Predicate<V> matcher) throws NotFoundException {
        return this.getAll().stream()
                .filter(matcher)
                .findAny()
                .orElseThrow(() -> new NotFoundException(
                        "CollectionMap: Could not match element from all collections: No such element exists that matches the given matcher"));
    }

    /**
     * @param matcher the predicate to compare elements against
     * @return set of all elements matching the given pattern, as determined by the given comparator, among all of the Collections
     * @throws NotFoundException if no such elements exist
     */
    public Set<V> matchesAllCollectionElement(Predicate<V> matcher) throws NotFoundException {
        return this.getAll().stream()
                .filter(matcher)
                .collect(Collectors.toSet());
    }

    /**
     * @return a Collection with all elements, regardless of the keys
     */
    public Collection<V> getAll() {
        Collection<V> ret = newCollection();
        for (Collection<V> collection : baseTable.values()) {
            ret.addAll(collection);
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
     * @return true if the value is contained in at least one of the Collections in the map; false otherwise
     */
    public boolean containsValue(V value) {
        try {
            this.matchAllCollectionElement(value::equals);
            return true;
        }
        catch (NotFoundException nfe) {
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
     * Removes the Collection associated with the given key, if it exists
     * @return the Collection that was removed (null if no Collection was associated with the given key)
     */
    public Collection<V> remove(K collectionKey) {
        return baseTable.remove(collectionKey);
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
     * Compare to getAll, which returns all elements from all Collections in a single Collection; values preserves individual Collections
     * @return a collection with all the Collections associated with keys in this map
     */
    public <T extends Collection<V>> Collection<T> values() {
        return (Collection<T>) baseTable.values();
    }

    /**
     * @return the set of entries in this map
     */
    public <T extends Set<Entry<K, Collection<V>>>> T entrySet() {
        return (T) baseTable.entrySet();
    }

    @Override
    public String toString() {
        return baseTable.toString();
    }

}
