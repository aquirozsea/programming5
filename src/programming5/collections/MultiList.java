/*
 * MultiList.java
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import programming5.arrays.ArrayOperations;
import programming5.strings.StringOperations;

/**
 *Wrapper class that links the elements of two lists, so that the elements of one can be indexed with the elements 
 *of the other. This is, elements are added in pairs, so that an one element of the pair can be retrieved by 
 *specifying the other. List elements can also be accessed separately by search or by their index number.
 *@author Andres Quiroz Hernandez
 *@version 6.19
 */
public class MultiList<E, D> implements PMap<E, D>, Serializable {

    protected List<E> vector1;
    protected List<D> vector2;

    protected int[] sortedOrder1 = null;
    protected int[] sortedOrder2 = null;
    protected boolean isSorted1 = false;
    protected boolean isSorted2 = false;
    
    /**
     *Creates an empty MultiVector
     */
    public MultiList() {
        vector1 = new ArrayList<E>();
        vector2 = new ArrayList<D>();
    }
    
    /**
     *Creates a MultiVector with the elements of the given collections, which must match in size
     *@throws java.lang.IllegalArgumentException if the sizes of the collections do not match
     */
    public MultiList(Collection<? extends E> c1, Collection<? extends D> c2) {
        if (c1.size() == c2.size()) {
            vector1 = new ArrayList<E>(c1);
            vector2 = new ArrayList<D>(c2);
        }
        else {
            throw new IllegalArgumentException("MultiVector: Could not create: Given collections must be of equal size");
        }
    }
    
    /**
     *Creates a MultiVector from the given Map object
     */
    public MultiList(Map<? extends E, ? extends D> map) {
        vector1 = new ArrayList<E>();
        vector2 = new ArrayList<D>();
        this.putAll(map);
    }
    
    /**
     *Creates an empty MultiVector, initialized with the given size
     */
    public MultiList(int initialSize) {
        vector1 = new ArrayList<E>(initialSize);
        vector2 = new ArrayList<D>(initialSize);
    }
    
    /**
     * Implementation of the safeGet method from the PMap interface.
     * @param key the search key
     * @param defaultValue an initializer or default value to be associated with the key in case no value is
     * associated with the key in the map already
     * @return this.get(key) if it is not null; otherwise, defaultValue
     * @see programming5.collections.PMap#safeGet(java.lang.Object, java.lang.Object) 
     */
    @Override
    public D safeGet(E key, D defaultValue) {
        D ret = this.get(key);
        if (ret == null) {
            this.put(key, defaultValue);
            ret = defaultValue;
        }
        return ret;
    }

    /**
     * Implementation of the safePut method of the PMap interface.
     * @param key the tentative insertion key
     * @param value the value to insert
     * @param keyGenerator the generator to be used for alternative keys in case of collisions
     * @return the key actually used to insert the value
     * @see programming5.collections.PMap#safePut(java.lang.Object, java.lang.Object, programming5.collections.MapKeyGenerator)
     */
    @Override
    public E safePut(E key, D value, MapKeyGenerator<E> keyGenerator) {
        E retKey = key;
        while (this.get(retKey) != null) {
            retKey = keyGenerator.generateKey();
        }
        this.put(retKey, value);
        return retKey;
    }

    @Override
    public Map<E, E> safePutAll(Map<? extends E, ? extends D> otherMap, MapKeyGenerator<E> keyGenerator) {
        Map<E, E> changedKeys = new HashMap<E, E>();
        for (Entry<? extends E, ? extends D> otherEntry : otherMap.entrySet()) {
            E newKey = this.safePut(otherEntry.getKey(), otherEntry.getValue(), keyGenerator);
            if (!newKey.equals(otherEntry.getKey())) {
                changedKeys.put(otherEntry.getKey(), newKey);
            }
        }
        return changedKeys;
    }

    /**
     * Implementation of the randomPut method of the PMap interface
     * @param value the value to insert
     * @param keyGenerator the generator to be used for generating the key to use for the insertion
     * @return the key used to insert the value
     * @see programming5.collections.PMap#randomPut(java.lang.Object, programming5.collections.MapKeyGenerator)
     */
    @Override
    public E randomPut(D value, MapKeyGenerator<E> keyGenerator) {
        E ret = keyGenerator.generateKey();
        while (this.get(ret) != null) {
            ret = keyGenerator.generateKey();
        }
        this.put(ret, value);
        return ret;
    }

    /**
     *@return the number of element pairs in the multi-vector
     */
    @Override
    public int size() {
        return vector1.size();
    }

    /**
     *@return true if the multi-vector has no element pairs
     */
    @Override
    public boolean isEmpty() {
        return vector1.isEmpty();
    }

    /**
     *Implementation of the Map method, equivalent to containsFirst.
     *@see #containsFirst
     */
    @Override
    public boolean containsKey(Object key) {
        return containsInFirst((E) key);
    }

    /**
     *Implementation of the Map method, equivalent to containsSecond.
     *@see #containsSecond
     */
    @Override
    public boolean containsValue(Object value) {
        return containsInSecond((D) value);
    }

    /**
     *Equivalent to getInSecond
     *@see #getInSecond
     */
    @Override
    public D get(Object key) {
        return getInSecond((E) key);
    }

    /**
     *Equivalent to setInSecond if the given key value exists; else equivalent to add
     *@see #setInSecond
     *@see #add
     */
    @Override
    public D put(E key, D value) {
        D ret = getInSecond(key);
        if (ret != null) {
            setInSecond(key, value);
        }
        else {
            add(key, value);
        }
        return ret;
    }

    /**
     *Equivalent to removeUsingFirst
     *@see #removeUsingFirst
     */
    @Override
    public D remove(Object key) {
        E eKey = (E) key;
        D ret = getInSecond(eKey);
        if (ret != null) {
            removeUsingFirst(eKey);
        }
        return ret;
    }

    /**
     *Implementation of the Map putAll method
     */
    @Override
    public void putAll(Map<? extends E, ? extends D> map) {
        for (Object key : map.keySet()) {
            this.put((E) key, (D) map.get((E) key));
        }
    }

    /**
     *Removes all element pairs
     */
    @Override
    public void clear() {
        vector1.clear();
        vector2.clear();
        isSorted1 = false;
        isSorted2 = false;
    }

    /**
     *Implementation of the Map keySet method.
     *@return a set with the first components of element pairs
     */
    @Override
    public Set<E> keySet() {
        return new HashSet(vector1);
    }

    /**
     *Implementation of the Map values method.
     *@return the vector of second components of element pairs
     */
    @Override
    public Collection<D> values() {
        return second();
    }

    /**
     *Unsupported method from Map interface
     */
    @Override
    public Set<Map.Entry<E, D>> entrySet() {
        Set<Map.Entry<E, D>> ret = new HashSet<Map.Entry<E, D>>();
        for (int i = 0; i < this.size(); i++) {
            Map.Entry<E, D> entry = new MultiListEntry<E, D>(vector1.get(i), vector2.get(i));
            ret.add(entry);
        }
        return ret;
    }
    
    /**
     *Adds an element pair
     */
    public boolean add(E obj1, D obj2) {
        boolean modified = vector1.add(obj1);
        if (modified) {
            modified = vector2.add(obj2);
            if (!modified) {
                vector1.remove(obj1);
            }
            else {
                isSorted1 = false;
                isSorted2 = false;
            }
        }
        return modified;
    }
    
    /**
     *Adds an element pair at the given position (the position of succeeding elements shifts by one)
     */
    public void add(int index, E element1, D element2) {
        vector1.add(index, element1);
        vector2.add(index, element2);
        isSorted1 = false;
        isSorted2 = false;
    }
    
    /**
     *Adds the element pairs of the given collections; the multi-vector will not be modified if the collections are of different size.
     */
    public boolean addAll(Collection<? extends E> c1, Collection<? extends D> c2) {
        boolean modified = false;
        if (c1.size() == c2.size()) {
            modified = vector1.addAll(c1);
            if (modified) {
                modified = vector2.addAll(c2);
                if (!modified) {
                    vector1.removeAll(c1);
                }
                else {
                    isSorted1 = false;
                    isSorted2 = false;
                }
            }
        }
        return modified;
    }

    /**
     *Adds the element pairs from the given collections at the given position (the position of succeeding elements shifts by the size of the collections). 
     *The multi-vector will not be modified if the collections are of different size.
     */
    public boolean addAll(int index, Collection<? extends E> c1, Collection<? extends D> c2) {
        boolean modified = false;
        if (c1.size() == c2.size()) {
            modified = vector1.addAll(index, c1);
            if (modified) {
                modified = vector2.addAll(index, c2);
                if (!modified) {
                    vector1.removeAll(c1);
                }
                else {
                    isSorted1 = false;
                    isSorted2 = false;
                }
            }
        }
        return modified;
    }
    
    /**
     *@return the first component of the element pair at the given position in the multi-vector
     */
    public E getInFirstAt(int index) {
        E ret;
        if (isSorted1) {
            ret = vector1.get(sortedOrder1[index]);
        }
        else {
            ret = vector1.get(index);
        }
        return ret;
    }

    /**
     * @return the first component of the element pair at the given position, according to the order in the second 
     * component vector. If the multi-list is sorted by the second component, this method will differ from 
     * getInFirstAt; otherwise the result will be equivalent.
     */
    public E getInFirstAtSecond(int index) {
        E ret;
        if (isSorted2) {
            ret = vector1.get(sortedOrder2[index]);
        }
        else {
            ret = vector1.get(index);
        }
        return ret;
    }
    
    /**
     *@return the second component of the element pair at the given position in the multi-vector
     */
    public D getInSecondAt(int index) {
        D ret;
        if (isSorted2) {
            ret = vector2.get(sortedOrder2[index]);
        }
        else {
            ret = vector2.get(index);
        }
        return ret;
    }

    /**
     * @return the second component of the element pair at the given position, according to the order in the first
     * component vector. If the multi-list is sorted by the first component, this method will differ from
     * getInSecondAt; otherwise the result will be equivalent.
     */
    public D getInSecondAtFirst(int index) {
        D ret;
        if (isSorted1) {
            ret = vector2.get(sortedOrder1[index]);
        }
        else {
            ret = vector2.get(index);
        }
        return ret;
    }
    
    /**
     *@return the first component in the multi-vector of the element pair that contains the given second component 
     */
    public E getInFirst(D element) {
        E ret = null;
        try {
            if (isSorted2) {
                ret = vector1.get(CollectionUtils.binarySearchRT(vector2, element, sortedOrder2));  // Runtime binary search (comparable type not checked until runtime)
            }
            else {
                ret = vector1.get(vector2.indexOf(element));
            }
        }
        catch (ArrayIndexOutOfBoundsException iobe) {   // Element not found, so nothing retrieved from vector1 (ret stays null)
        }
        return ret;
    }

    /**
     *@return the second component in the multi-vector of the element pair that contains the given first component 
     */
    public D getInSecond(E element) {
        D ret = null;
        try {
            if (isSorted1) {
                ret = vector2.get(CollectionUtils.binarySearchRT(vector1, element, sortedOrder1));  // Runtime binary search (comparable type not checked until runtime)
            }
            else {
                ret = vector2.get(vector1.indexOf(element));
            }
        }
        catch (ArrayIndexOutOfBoundsException iobe) {
        }
        return ret;
    }

    /**
     * @return the element pair at the given index, according to the order of the first component (if sorted, the index-th
     * element in the first list and its corresponding second element)
     */
    public Entry<E, D> getEntryAtFirst(int index) {
        return new MultiListEntry<E, D>(this.getInFirstAt(index), this.getInSecondAtFirst(index));
    }

    /**
     * @return the element pair at the given index, according to the order of the second component (if sorted, the index-th
     * element in the second list and its corresponding first element)
     */
    public Entry<E, D> getEntryAtSecond(int index) {
        return new MultiListEntry<E, D>(this.getInFirstAtSecond(index), this.getInSecondAt(index));
    }
    
    /**
     * Equivalent to getEntryAtFirst(index)
     */
    public Entry<E, D> getEntry(int index) {
        return getEntryAtFirst(index);
    }

    /**
     *@return true if either component of any element pair equals the given object
     */
    public boolean contains(Object object) {
        boolean contains = false;
        if (isSorted1) {
            try {
                E eobject = (E) object;
                contains = (CollectionUtils.binarySearchRT(vector1, eobject, sortedOrder1) >= 0);
            }
            catch (ClassCastException cce) {} // Contains does not change (= false)
        }
        if (!contains && isSorted2) {
            try {
                D dobject = (D) object;
                contains = (CollectionUtils.binarySearchRT(vector2, dobject, sortedOrder2) >= 0);
            }
            catch (ClassCastException cce) {}   // Contains does not change (= false)
        }
        if (!contains && !isSorted1) {
            contains = vector1.contains(object);
        }
        if (!contains && !isSorted2) {
            contains = vector2.contains(object);
        }
        return  contains;
    }
    
    /**
     *@return true if the first component of any element pair equals the given object
     */
    public boolean containsInFirst(E object) {
        boolean contains = false;
        if (isSorted1) {
            contains = (CollectionUtils.binarySearchRT(vector1, object, sortedOrder1) >= 0);
        }
        else {
            contains = vector1.contains(object);
        }
        return contains;
    }
    
    /**
     *@return true if the second component of any element pair equals the given object
     */
    public boolean containsInSecond(D object) {
        boolean contains = false;
        if (isSorted2) {
            contains = (CollectionUtils.binarySearchRT(vector2, object, sortedOrder2) >= 0);
        }
        else {
            contains = vector2.contains(object);
        }
        return contains;
    }

    /**
     *@return true if a component of any element pair can be found that equals every object of the given collection
     */
    public boolean containsAll(Collection<?> c) {
        boolean contained = true;
        for (Object o : c) {
            contained = this.contains(o);
            if (!contained) {
                break;
            }
        }
        return contained;
    }

    /**
     *@return true if the first component of any element pair can be found that equals every object of the given collection
     */
    public boolean containsAllInFirst(Collection<?> c) {
        boolean contained = true;
        for (Object o : c) {
            try {
                contained = this.containsInFirst((E) o);
            }
            catch (ClassCastException cce) {
                contained = false;
            }
            if (!contained) {
                break;
            }
        }
        return contained;
    }

    /**
     *@return true if the second component of any element pair can be found that equals every object of the given collection
     */
    public boolean containsAllInSecond(Collection<?> c) {
        boolean contained = true;
        for (Object o : c) {
            try {
                contained = this.containsInSecond((D) o);
            }
            catch (ClassCastException cce) {
                contained = false;
            }
            if (!contained) {
                break;
            }
        }
        return contained;
    }
    
    /**
     *@return the first component of the last element pair
     */
    public E lastElementFromFirst() {
        E ret;
        if (isSorted1) {
            ret = vector1.get(sortedOrder1[vector1.size()-1]);
        }
        else {
            ret = vector1.get(vector1.size()-1);
        }
        return ret;
    }
    
    /**
     *@return the second component of the last element pair
     */
    public D lastElementFromSecond() {
        D ret;
        if (isSorted2) {
            ret = vector2.get(sortedOrder2[vector2.size()-1]);
        }
        else {
            ret = vector2.get(vector2.size()-1);
        }
        return ret;
    }
    
    /**
     *Removes the element pair at the given index
     */
    public void remove(int index) {
        if (isSorted1) {
            int vector1Pos = sortedOrder1[index]; 
            vector1.remove(vector1Pos);
            vector2.remove(vector1Pos);
            ArrayOperations.delete(sortedOrder1, index);
            if (isSorted2) {
                int vector2Pos = ArrayOperations.seqFind(vector1Pos, sortedOrder2);
                ArrayOperations.delete(sortedOrder2, vector2Pos);
            }
        }
        else {
            vector1.remove(index);
            vector2.remove(index);
        }

    }
    
    /**
     *Removes the first element pair whose first component matches the given object.
     *@return true if a matching element pair was found and removed
     */
    // TODO: Optimize (and remove all matches?)
    public boolean removeUsingFirst(E object) {
        boolean removed = false;
        int index = vector1.indexOf(object);
        if (index >= 0) {
            vector1.remove(index);
            if (isSorted1) {
                int sortedPos = ArrayOperations.seqFind(index, sortedOrder1);
                ArrayOperations.delete(sortedOrder1, sortedPos);
            }
            vector2.remove(index);
            if (isSorted2) {
                int sortedPos = ArrayOperations.seqFind(index, sortedOrder2);
                ArrayOperations.delete(sortedOrder2, sortedPos);
            }
            removed = true;
        }
        return removed;
    }
    
    /**
     *Removes the first element pair whose second component matches the given object.
     *@return true if a matching element pair was found and removed
     */
    // TODO: Optimize (and remove all matches?)
    public boolean removeUsingSecond(D object) {
        boolean removed = false;
        int index = vector2.indexOf(object);
        if (index >= 0) {
            vector1.remove(index);
            if (isSorted1) {
                int sortedPos = ArrayOperations.seqFind(index, sortedOrder1);
                ArrayOperations.delete(sortedOrder1, sortedPos);
            }
            vector2.remove(index);
            if (isSorted2) {
                int sortedPos = ArrayOperations.seqFind(index, sortedOrder2);
                ArrayOperations.delete(sortedOrder2, sortedPos);
            }
            removed = true;
        }
        return removed;
    }
    
    /**
     *Removes all element pairs whose first component matches any of the objects in the given collection.
     *@return true if some matching element pair was found and removed
     */
    // TODO: Optimize (and remove all matches?)
    public boolean removeAllUsingFirst(Collection<? extends E> c) {
        boolean modified = false;
        for (E object : c) {
            modified |= this.removeUsingFirst(object);
        }
        return modified;
    }
    
    /**
     *Removes all element pairs whose second component matches any of the objects in the given collection.
     *@return true if some matching element pair was found and removed
     */
    // TODO: Optimize (and remove all matches?)
    public boolean removeAllUsingSecond(Collection<? extends D> c) {
        boolean modified = false;
        for (D object : c) {
            modified |= this.removeUsingSecond(object);
        }
        return modified;
    }
    
    /**
     *Removes all element pairs whose first component doesn't match any of the objects in the given collection.
     *@return true if the multi-vector was modified (element pairs were removed)
     */
    // TODO: Optimize
    public boolean retainAllUsingFirst(Collection<? extends E> c) {
        boolean modified = false;
        List<E> toRemove = new ArrayList<E>();
        for (E element : vector1) {
            if (!c.contains(element)) {
                toRemove.add(element);
            }
        }
        if (!toRemove.isEmpty()) {
            modified = true;
            for (E element : toRemove) {
                this.removeUsingFirst(element);
            }
        }
        return modified;
    }
    
    /**
     *Removes all element pairs whose second component doesn't match any of the objects in the given collection.
     *@return true if the multi-vector was modified (element pairs were removed)
     */
    // TODO: Optimize
    public boolean retainAllUsingSecond(Collection<? extends D> c) {
        boolean modified = false;
        List<D> toRemove = new ArrayList<D>();
        for (D element : vector2) {
            if (!c.contains(element)) {
                toRemove.add(element);
            }
        }
        if (!toRemove.isEmpty()) {
            modified = true;
            for (D element : toRemove) {
                this.removeUsingSecond(element);
            }
        }
        return modified;
    }
    
    /**
     *Sets the element pair at index with the given value pair
     */
    public void set(int index, E obj1, D obj2) {
        vector1.set(index, obj1);
        vector2.set(index, obj2);
        isSorted1 = false;
        sortedOrder1 = null;
        isSorted2 = false;
        sortedOrder2 = null;
    }
    
    /**
     *Sets the first component of the first element pair whose second component matches the given object.
     *@param match the index object
     *@param object the value object
     */
    // TODO: Test
    public boolean setInFirst(D match, E object) {
        boolean modified = false;
        try {
            if (isSorted2) {
                vector1.set(CollectionUtils.binarySearchRT(vector2, match, sortedOrder2), object);
            }
            else {
                vector1.set(vector2.indexOf(match), object);
            }
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}  // match not found; modified stays false
        if (modified && isSorted1) {
            isSorted1 = false;
            sortedOrder1 = null;
        }
        return modified;
    }
    
    /**
     *Sets the first component of the element pair at the given position
     */
    public void setInFirstAt(int index, E object) {
        vector1.set(index, object);
        if (isSorted1) {
            isSorted1 = false;
            sortedOrder1 = null;
        }
    }
    
    /**
     *Sets the second component of the first element pair whose first component matches the given object.
     *@param match the index object
     *@param object the value object
     */
    // TODO: Test
    public boolean setInSecond(E match, D object) {
        boolean modified = false;
        try {
            if (isSorted1) {
                vector2.set(CollectionUtils.binarySearchRT(vector1, match, sortedOrder1), object);
            }
            else {
                vector2.set(vector1.indexOf(match), object);
            }
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}  // match not found; modified stays false
        if (modified && isSorted2) {
            isSorted2 = false;
            sortedOrder2 = null;
        }
        return modified;
    }
    
    /**
     *Sets the second component of the element pair at the given position
     */
    public void setInSecondAt(int index, D object) {
        vector2.set(index, object);
        if (isSorted2) {
            isSorted2 = false;
            sortedOrder2 = null;
        }
    }
    
    /**
     *Copies the elements of each array to the corresponding positions in the multi-list
     */
    public void copyInto(E[] array1, D[] array2) {
        vector1 = CollectionUtils.listFromArray(array1);
        vector2 = CollectionUtils.listFromArray(array2);
        isSorted1 = false;
        isSorted2 = false;
    }
    
    /**
     *@return an iterator to traverse all first components of element pairs
     */
    public Iterator<E> firstIterator() {
        Iterator<E> ret;
        if (isSorted1) {
            ret = new SortedIterator<E>(vector1, sortedOrder1);
        }
        else {
            ret = vector1.iterator();
        }
        return ret;
    }

    /**
     *@return an iterator to traverse all second components of element pairs
     */
    public Iterator<D> secondIterator() {
        Iterator<D> ret;
        if (isSorted2) {
            ret = new SortedIterator<D>(vector2, sortedOrder2);
        }
        else {
            ret = vector2.iterator();
        }
        return ret;
    }
    
    /**
     *@return an iterator to traverse all objects in the multi-vector (all first components, followed by all second components)
     */
    public Iterator iterator() {
        List all = new ArrayList(vector1);
        all.addAll(vector2);
        return all.iterator();
    }

    public Iterator<Entry<E, D>> entryIterator() {
        Iterator<Entry<E, D>> ret;
        List<Map.Entry<E, D>> entryList = new ArrayList<Map.Entry<E, D>>();
        for (int i = 0; i < this.size(); i++) {
            Map.Entry<E, D> entry = new MultiListEntry<E, D>(vector1.get(i), vector2.get(i));
            entryList.add(entry);
        }
        if (isSorted1) {
            ret = new SortedIterator<Entry<E, D>>(entryList, sortedOrder1);
        }
        else {
            ret = entryList.iterator();
        }
        return ret;
    }
    
    /**
     *@return a clone of the vector of first components of element pairs
     */
    public List<E> first() {
        List<E> ret;
        if (isSorted1) {
            ret = new ArrayList<E>();
            for (int i = 0; i < sortedOrder1.length; i++) {
                ret.add(vector1.get(sortedOrder1[i]));
            }
        }
        else {
            ret = new ArrayList<E>(vector1);
        }
        return ret;
    }
    
    /**
     *@return a clone of the vector of second components of element pairs
     */
    public List<D> second() {
        List<D> ret;
        if (isSorted2) {
            ret = new ArrayList<D>();
            for (int i = 0; i < sortedOrder2.length; i++) {
                ret.add(vector2.get(sortedOrder2[i]));
            }
        }
        else {
            ret = new ArrayList<D>(vector2);
        }
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MultiList<E, D> other = (MultiList<E, D>) obj;
        if (this.vector1 != other.vector1 && (this.vector1 == null || !this.vector1.equals(other.vector1))) {
            return false;
        }
        if (this.vector2 != other.vector2 && (this.vector2 == null || !this.vector2.equals(other.vector2))) {
            return false;
        }
        if (this.isSorted1 != other.isSorted1) {
            return false;
        }
        if (this.isSorted2 != other.isSorted2) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.vector1 != null ? this.vector1.hashCode() : 0);
        hash = 83 * hash + (this.vector2 != null ? this.vector2.hashCode() : 0);
        hash = 83 * hash + ((this.isSorted1) ? 1 : 0);
        hash = 83 * hash + ((this.isSorted2) ? 1 : 0);
        return hash;
    }
    
    /**
     *@return the index of the first element pair whose first component equals the given object
     */
    // TODO: Test
    public int indexOfInFirst(E element) {
        int ret;
        if (isSorted1) {
//            int unsortedIndex = vector1.indexOf(element);
            ret = CollectionUtils.binarySearchRT(vector1, element, sortedOrder1);
        }
        else {
            ret = vector1.indexOf(element);
        }
        return ret;
    }
    
    /**
     *@return the index of the first element pair whose second component equals the given object
     */
    public int indexOfInSecond(D element) {
        int ret;
        if (isSorted2) {
//            int unsortedIndex = vector2.indexOf(element);
            ret = CollectionUtils.binarySearchRT(vector2, element, sortedOrder2);
        }
        else {
            ret = vector2.indexOf(element);
        }
        return ret;
    }
    
    /**
     *@return the index of the first element pair after the given start index whose first component equals the given object
     */
    public int indexOfInFirst(E element, int startIndex) {
        int ret;
        if (isSorted1) {
            int firstIndex = this.indexOfInFirst(element);
            if (startIndex <= firstIndex) {
                ret = firstIndex;
            }
            else {
                Comparable start = (Comparable) vector1.get(sortedOrder1[startIndex]);
                Comparable compElement = (Comparable) element;
                if (start.compareTo(compElement) == 0) {
                    ret = startIndex;
                }
                else {
                    ret = -1;
                }
            }
        }
        else {
            ret = startIndex + vector1.subList(startIndex, vector1.size()).indexOf(element);
        }
        return ret;
    }
    
    /**
     *@return the index of the first element pair after the given start index whose second component equals the given object
     */
    public int indexOfInSecond(D element, int startIndex) {
        int ret;
        if (isSorted2) {
            int firstIndex = this.indexOfInSecond(element);
            if (startIndex <= firstIndex) {
                ret = firstIndex;
            }
            else {
                Comparable start = (Comparable) vector2.get(sortedOrder2[startIndex]);
                Comparable compElement = (Comparable) element;
                if (start.compareTo(compElement) == 0) {
                    ret = startIndex;
                }
                else {
                    ret = -1;
                }
            }
        }
        else {
            ret = startIndex + vector2.subList(startIndex, vector2.size()).indexOf(element);
        }
        return ret;
    }
    
    /**
     *@return the index of the last element pair whose first component equals the given object
     */
    // TODO: Test
    public int lastIndexOfInFirst(E element) {
        int ret;
        if (isSorted1) {
            int index = this.indexOfInFirst(element);
            if (index >= 0) {
                Comparable compElement = (Comparable) element;
                boolean repeats;
                do {
                    if (index < vector1.size()) {
                        Comparable next = (Comparable) vector1.get(sortedOrder1[index + 1]);
                        if (next.compareTo(compElement) == 0) {
                            repeats = true;
                            index++;
                        }
                        else {
                            repeats = false;
                        }
                    }
                    else {
                        repeats = false;
                    }
                } while (repeats);
            }
            ret = index;
        }
        else {
            ret = vector1.lastIndexOf(element);
        }
        return ret;
    }
    
    /**
     *@return the index of the last element pair whose second component equals the given object
     */
    // TODO: Test
    public int lastIndexOfInSecond(D element) {
        int ret;
        if (isSorted2) {
            int index = this.indexOfInSecond(element);
            if (index >= 0) {
                Comparable compElement = (Comparable) element;
                boolean repeats;
                do {
                    if (index < vector2.size()) {
                        Comparable next = (Comparable) vector2.get(sortedOrder2[index + 1]);
                        if (next.compareTo(compElement) == 0) {
                            repeats = true;
                            index++;
                        }
                        else {
                            repeats = false;
                        }
                    }
                    else {
                        repeats = false;
                    }
                } while (repeats);
            }
            ret = index;
        }
        else {
            ret = vector2.lastIndexOf(element);
        }
        return ret;
    }

    /**
     *@return an array with all first components of element pairs
     */
    public E[] toArrayFirst() {
        return (E[]) first().toArray();
    }

    /**
     *@return an array with all second components of element pairs
     */
    public D[] toArraySecond() {
        return (D[]) second().toArray();
    }
    
    @Override
    public String toString() {
        String ret = "";
        Iterator<Entry<E, D>> entryIterator = this.entryIterator();
        while (entryIterator.hasNext()) {
            ret = StringOperations.addToList(ret, entryIterator.next().toString());
        }
        return ret;
    }
    
    /**
     *A string in which element pairs are separated by the given separator string
     */
    public String toString(String separator) {
        String ret = "";
        Iterator<Entry<E, D>> entryIterator = this.entryIterator();
        while (entryIterator.hasNext()) {
            ret = StringOperations.addToList(ret, separator, entryIterator.next().toString());
        }
        return ret;
    }
    
    public void sortFirst() {
        if (!isSorted1) {
            if (sortedOrder1 == null) {
                sortedOrder1 = ArrayOperations.sortedOrder(vector1.toArray(new Comparable[] {}));
            }
            else {
                reSortFirst();
            }
            isSorted1 = true;
        }
    }

    public void sortSecond() {
        if (!isSorted2) {
            if (sortedOrder2 == null) {
                sortedOrder2 = ArrayOperations.sortedOrder(vector2.toArray(new Comparable[] {}));
            }
            else {
                reSortSecond();
            }
            isSorted2 = true;
        }
    }

    public void sort() {
        sortFirst();
        sortSecond();
    }

    public boolean isSortedFirst() {
        return isSorted1;
    }

    public boolean isSortedSecond() {
        return isSorted2;
    }

    public boolean isSorted() {
        return (isSortedFirst() || isSortedSecond());
    }

    // TODO: Verify for insertions that don't modify the length
    private void reSortFirst() {
        int unsortedStart = sortedOrder1.length;
        List<Comparable> sortedVector = new ArrayList<Comparable>();
        for (int i = 0; i < unsortedStart; i++) {
            sortedVector.add((Comparable) vector1.get(sortedOrder1[i]));
        }
        for (int i = unsortedStart; i < vector1.size(); i++) {
            int sortedPos = CollectionUtils.findPositionInOrder(sortedVector, (Comparable) vector1.get(i));
            ArrayOperations.insert(i, sortedOrder1, sortedPos);
            CollectionUtils.insert(sortedVector, (Comparable) vector1.get(i), sortedPos);
        }
    }

    // TODO: Verify for insertions that don't modify the length
    private void reSortSecond() {
        int unsortedStart = sortedOrder2.length;
        List<Comparable> sortedVector = new ArrayList<Comparable>();
        for (int i = 0; i < unsortedStart; i++) {
            sortedVector.add((Comparable) vector2.get(sortedOrder2[i]));
        }
        for (int i = unsortedStart; i < vector2.size(); i++) {
            int sortedPos = CollectionUtils.findPositionInOrder(sortedVector, (Comparable) vector2.get(i));
            ArrayOperations.insert(i, sortedOrder2, sortedPos);
            CollectionUtils.insert(sortedVector, (Comparable) vector2.get(i), sortedPos);
        }
    }
    
    /**
     * Implementation of the Map.Entry interface
     */
    public class MultiListEntry<E, D> implements Map.Entry<E, D> {

        E firstElement;
        D secondElement;

        public MultiListEntry(E key, D value) {
            firstElement = key;
            secondElement = value;
        }

        @Override
        public E getKey() {
            return firstElement;
        }

        @Override
        public D getValue() {
            return secondElement;
        }

        /**
         * Sets a new value in the entry object, but does not modify the original multi vector object
         */
        @Override
        public D setValue(D newValue) {
            secondElement = newValue;
            return secondElement;
        }

        @Override
        public String toString() {
            return "[" + firstElement.toString() + ", " + secondElement.toString() + "]";
        }

    }

    public class SortedIterator<T> implements Iterator<T> {

        int[] sortedOrder;
        List<T> list;
        int current = 0;

        public SortedIterator(List<T> myList, int[] mySortedOrder) {
            sortedOrder = mySortedOrder;
            list = myList;
        }

        @Override
        public boolean hasNext() {
            return (current < sortedOrder.length);
        }

        @Override
        public T next() {
            return list.get(sortedOrder[current++]);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
    
}
