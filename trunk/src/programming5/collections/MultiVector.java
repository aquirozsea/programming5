/*
 * MultiVector.java
 *
 * Copyright 2007 Andres Quiroz Hernandez
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *Wrapper class that links the elements of two vectors, so that the elements of one can be indexed with the elements 
 *of the other. This is, elements are added in pairs, so that an one element of the pair can be retrieved by 
 *specifying the other. Vector elements can also be accessed separately by search or by their index number.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class MultiVector<E, D> implements Serializable, Cloneable, PMap<E, D> {
    
    protected Vector<E> vector1;
    protected Vector<D> vector2;
    
    /**
     *Creates an empty MultiVector
     */
    public MultiVector() {
        vector1 = new Vector<E>();
        vector2 = new Vector<D>();
    }
    
    /**
     *Creates a MultiVector with the elements of the given collections, which must match in size
     *@throws java.lang.IllegalArgumentException if the sizes of the collections do not match
     */
    public MultiVector(Collection<? extends E> c1, Collection<? extends D> c2) {
        if (c1.size() == c2.size()) {
            vector1 = new Vector<E>(c1);
            vector2 = new Vector<D>(c2);
        }
        else {
            throw new IllegalArgumentException("MultiVector: Could not create: Given collections must be of equal size");
        }
    }
    
    /**
     *Creates a MultiVector from the given Map object
     */
    public MultiVector(Map<? extends E, ? extends D> map) {
        vector1 = new Vector<E>();
        vector2 = new Vector<D>();
        this.putAll(map);
    }
    
    /**
     *Creates an empty MultiVector, initialized with the given size
     */
    public MultiVector(int initialSize) {
        vector1 = new Vector<E>(initialSize);
        vector2 = new Vector<D>(initialSize);
    }
    
    /**
     *Creates an empty MultiVector, initialized with the given size and increment factor
     */
    public MultiVector(int initialSize, int increment) {
        vector1 = new Vector<E>(initialSize, increment);
        vector2 = new Vector<D>(initialSize, increment);
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
        }
        return modified;
    }
    
    /**
     *Adds an element pair at the given position (the position of succeeding elements shifts by one)
     */
    public void add(int index, E element1, D element2) {
        vector1.add(index, element1);
        vector2.add(index, element2);
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
            }
        }
        return modified;
    }
    
    /**
     *@return the allocated size of the multi-vector
     */
    public int capacity() {
        return vector1.capacity();
    }
    
    /**
     *Removes all element pairs
     */
    public void clear() {
        vector1.clear();
        vector2.clear();
    }
    
    /**
     *@return a new multi-vector, using the Vector class clone method
     *@see java.util.Vector#clone
     */
    @Override
    public MultiVector<E, D> clone() {
        return new MultiVector<E, D>((Vector<E>) vector1.clone(), (Vector<D>) vector2.clone());
    }
    
    /**
     *@return true if either component of any element pair equals the given object, using the Vector contains method
     *@see java.util.Vector#contains
     */
    public boolean contains(Object object) {
        return vector1.contains(object) || vector2.contains(object);
    }
    
    /**
     *@return true if the first component of any element pair equals the given object, using the Vector contains method
     *@see java.util.Vector#contains
     */
    public boolean containsFirst(E object) {
        return vector1.contains(object);
    }
    
    /**
     *@return true if the second component of any element pair equals the given object, using the Vector contains method
     *@see java.util.Vector#contains
     */
    public boolean containsSecond(D object) {
        return vector2.contains(object);
    }

    /**
     *@return true if a component of any element pair can be found that equals every object of the given collection, using the Vector containsAll method
     *@see java.util.Vector#containsAll
     */
    public boolean containsAll(Collection<?> c) {
        return vector1.containsAll(c) || vector2.containsAll(c);
    }

    /**
     *@return true if the first component of any element pair can be found that equals every object of the given collection, using the Vector containsAll method
     *@see java.util.Vector#containsAll
     */
    public boolean containsAllFirst(Collection<?> c) {
        return vector1.containsAll(c);
    }

    /**
     *@return true if the second component of any element pair can be found that equals every object of the given collection, using the Vector containsAll method
     *@see java.util.Vector#containsAll
     */
    public boolean containsAllSecond(Collection<?> c) {
        return vector2.containsAll(c);
    }
    
    /**
     *Copies the elements of each array to the corresponding positions in the multi-vector, using the Vector copyInto method.
     *@see java.util.Vector#copyInto
     */
    public void copyInto(Object[] array1, Object[] array2) {
        vector1.copyInto(array1);
        vector2.copyInto(array2);
    }
    
    /**
     *@return the first component of the element pair at the given position in the multi-vector
     */
    public E getInFirstAt(int index) {
        return vector1.elementAt(index);
    }
    
    /**
     *@return the second component of the element pair at the given position in the multi-vector
     */
    public D getInSecondAt(int index) {
        return vector2.elementAt(index);
    }
    
    /**
     *@return the first component in the multi-vector of the element pair that contains the given second component 
     */
    public E getInFirst(D element) {
        E ret = null;
        try {
            ret = vector1.get(vector2.indexOf(element));
        }
        catch (ArrayIndexOutOfBoundsException iobe) {
        }
        return ret;
    }
    
    /**
     *@return the second component in the multi-vector of the element pair that contains the given first component 
     */
    public D getInSecond(E element) {
        D ret = null;
        try {
            ret = vector2.get(vector1.indexOf(element));
        }
        catch (ArrayIndexOutOfBoundsException iobe) {
        }
        return ret;
    }

    /**
     *Ensures a given capacity of element pairs using the Vector ensureCapacity method.
     *@see java.util.Vector#ensureCapacity
     */
    public void ensureCapacity(int minCapacity) {
        vector1.ensureCapacity(minCapacity);
        vector2.ensureCapacity(minCapacity);
    }
    
    /**
     *@return the first component of the first element pair; analogous to Vector firstElement method
     *@see java.util.Vector#firstElement
     */
    public E firstElementFromFirst() {
        return vector1.firstElement();
    }
    
    /**
     *@return the second component of the first element pair; analogous to Vector firstElement method
     *@see java.util.Vector#firstElement
     */
    public D firstElementFromSecond() {
        return vector2.firstElement();
    }
    
    @Override
    public int hashCode() {
        return vector1.hashCode() + vector2.hashCode();
    }
    
    /**
     *@return the index of the first element pair whose first component equals the given object, using the Vector indexOf method
     *@see java.util.Vector#indexOf
     */
    public int indexOfInFirst(E element) {
        return vector1.indexOf(element);
    }
    
    /**
     *@return the index of the first element pair whose second component equals the given object, using the Vector indexOf method
     *@see java.util.Vector#indexOf
     */
    public int indexOfInSecond(D element) {
        return vector2.indexOf(element);
    }
    
    /**
     *@return the index of the first element pair after the given start index whose first component equals the given object, using the Vector indexOf method
     *@see java.util.Vector#indexOf
     */
    public int indexOfInFirst(E element, int startIndex) {
        return vector1.indexOf(element, startIndex);
    }
    
    /**
     *@return the index of the first element pair after the given start index whose second component equals the given object, using the Vector indexOf method
     *@see java.util.Vector#indexOf
     */
    public int indexOfInSecond(D element, int startIndex) {
        return vector2.indexOf(element, startIndex);
    }
    
    /**
     *@return the index of the last element pair whose first component equals the given object, using the Vector lastIndexOf method
     *@see java.util.Vector#lastIndexOf
     */
    public int lastIndexOfInFirst(E element) {
        return vector1.lastIndexOf(element);
    }
    
    /**
     *@return the index of the last element pair whose second component equals the given object, using the Vector lastIndexOf method
     *@see java.util.Vector#lastIndexOf
     */
    public int lastIndexOfInSecond(D element) {
        return vector2.lastIndexOf(element);
    }
    
    /**
     *@return true if the multi-vector has no element pairs
     */
    public boolean isEmpty() {
        return vector1.isEmpty();
    }
    
    /**
     *@return the first component of the last element pair
     */
    public E lastElementFromFirst() {
        return vector1.lastElement();
    }
    
    /**
     *@return the second component of the last element pair
     */
    public D lastElementFromSecond() {
        return vector2.lastElement();
    }
    
    /**
     *Removes the element pair at the given index
     */
    public void remove(int index) {
        vector1.remove(index);
        vector2.remove(index);
    }
    
    /**
     *Removes the first element pair whose first component matches the given object.
     *@return true if a matching element pair was found and removed
     */
    public boolean removeUsingFirst(E object) {
        boolean removed = false;
        int index = vector1.indexOf(object);
        if (index >= 0) {
            vector1.remove(index);
            vector2.remove(index);
            removed = true;
        }
        return removed;
    }
    
    /**
     *Removes the first element pair whose second component matches the given object.
     *@return true if a matching element pair was found and removed
     */
    public boolean removeUsingSecond(D object) {
        boolean removed = false;
        int index = vector2.indexOf(object);
        if (index >= 0) {
            vector1.remove(index);
            vector2.remove(index);
            removed = true;
        }
        return removed;
    }
    
    /**
     *Removes all element pairs whose first component matches any of the objects in the given collection.
     *@return true if some matching element pair was found and removed
     */
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
    public boolean retainAllUsingFirst(Collection<? extends E> c) {
        boolean modified = false;
        Vector<E> toRemove = new Vector<E>();
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
    public boolean retainAllUsingSecond(Collection<? extends D> c) {
        boolean modified = false;
        Vector<D> toRemove = new Vector<D>();
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
    }
    
    /**
     *Sets the first component of the first element pair whose second component matches the given object.
     *@param match the index object
     *@param object the value object
     */
    public boolean setInFirst(D match, E object) {
        boolean modified = true;
        try {
            vector1.set(vector2.indexOf(match), object);
        }
        catch (ArrayIndexOutOfBoundsException iobe) {
            modified = false;
        }
        return modified;
    }
    
    /**
     *Sets the first component of the element pair at the given position
     */
    public void setInFirstAt(int index, E object) {
        vector1.set(index, object);
    }
    
    /**
     *Sets the second component of the first element pair whose first component matches the given object.
     *@param match the index object
     *@param object the value object
     */
    public boolean setInSecond(E match, D object) {
        boolean modified = true;
        try {
            vector2.set(vector1.indexOf(match), object);
        }
        catch (ArrayIndexOutOfBoundsException iobe) {
            modified = false;
        }
        return modified;
    }
    
    /**
     *Sets the second component of the element pair at the given position
     */
    public void setInSecondAt(int index, D object) {
        vector2.set(index, object);
    }
    
    /**
     *Sets the multi-vector size using the Vector setSize method.
     *@see java.util.Vector#setSize
     */
    public void setSize(int newSize) {
        vector1.setSize(newSize);
        vector2.setSize(newSize);
    }

    /**
     *@return the number of element pairs in the multi-vector
     */
    public int size() {
        return vector1.size();
    }

    /**
     *@return an iterator to traverse all first components of element pairs
     */
    public Iterator<E> firstIterator() {
        return vector1.iterator();
    }

    /**
     *@return an iterator to traverse all second components of element pairs
     */
    public Iterator<D> secondIterator() {
        return vector2.iterator();
    }
    
    /**
     *@return an iterator to traverse all objects in the multi-vector (all first components, followed by all second components)
     */
    public Iterator iterator() {
        Vector all = (Vector) vector1.clone();
        all.addAll((Vector) vector2.clone());
        return all.iterator();
    }
    
    /**
     *@return a clone of the vector of first components of element pairs
     */
    public Vector<E> first() {
        Vector<E> ret = (Vector<E>) vector1.clone();
        return ret;
    }
    
    /**
     *@return a clone of the vector of second components of element pairs
     */
    public Vector<D> second() {
        Vector<D> ret = (Vector<D>) vector2.clone();
        return ret;
    }

    /**
     *@return an array with all first components of element pairs
     */
    public E[] toArrayFirst() {
        return (E[]) vector1.toArray();
    }

    /**
     *@return an array with all second components of element pairs
     */
    public D[] toArraySecond() {
        return (D[]) vector2.toArray();
    }
    
    @Override
    public String toString() {
        String ret = vector1.get(0).toString() + ", " + vector2.get(0).toString();
        for (int i = 1; i < this.size(); i++) {
            ret += "; " + vector1.get(i).toString() + ", " + vector2.get(i).toString();
        }
        return ret;
    }
    
    /**
     *A string in which element pairs are separated by the given separator string
     */
    public String toString(String separator) {
        String ret = vector1.get(0).toString() + ", " + vector2.get(0).toString();
        for (int i = 1; i < this.size(); i++) {
            ret += separator + vector1.get(i).toString() + ", " + vector2.get(i).toString();
        }
        return ret;
    }
    
    /**
     *Trims the multi-vector using the Vector trimToSize method.
     *@see java.util.Vector#trimToSize
     */
    public void trimToSize() {
        vector1.trimToSize();
        vector2.trimToSize();
    }

    /**
     *Implementation of the Map method, equivalent to containsFirst.
     *@see #containsFirst
     */
    public boolean containsKey(Object key) {
        return containsFirst((E) key);
    }

    /**
     *Implementation of the Map method, equivalent to containsSecond.
     *@see #containsSecond
     */
    public boolean containsValue(Object value) {
        return containsSecond((D) value);
    }

    /**
     *Equivalent to getInSecond
     *@see #getInSecond
     */
    public D get(Object key) {
        return getInSecond((E) key);
    }

    /**
     *Equivalent to setInSecond if the given key value exists; else equivalent to add
     *@see #setInSecond
     *@see #add
     */
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
    public void putAll(Map<? extends E, ? extends D> map) {
        for (Object key : map.keySet()) {
            this.put((E) key, (D) map.get((E) key));
        }
    }

    /**
     *Implementation of the Map keySet method.
     *@return a set with the first components of element pairs
     */
    public Set<E> keySet() {
        return new HashSet(first());
    }

    /**
     *Implementation of the Map values method.
     *@return the vector of second components of element pairs
     */
    public Collection<D> values() {
        return second();
    }

    /**
     *Unsupported method from Map interface
     */
    public Set<Map.Entry<E, D>> entrySet() {
        Set<Map.Entry<E, D>> ret = new HashSet<Map.Entry<E, D>>();
        for (int i = 0; i < this.size(); i++) {
            Map.Entry<E, D> entry = new MultiVectorEntry<E, D>(vector1.elementAt(i), vector2.elementAt(i));
            ret.add(entry);
        }
        return ret;
    }

    /**
     * Implementation of the safeGet method from the PMap interface.
     * @param key the search key
     * @param defaultValue an initializer or default value to be associated with the key in case no value is
     * associated with the key in the map already
     * @return this.get(key) if it is not null; otherwise, defaultValue
     * @see programming5.collections.PMap#safeGet(java.lang.Object, java.lang.Object) 
     */
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
     * Implementation of the Map.Entry interface
     */
    public class MultiVectorEntry<E, D> implements Map.Entry<E, D> {

        E firstElement;
        D secondElement;

        public MultiVectorEntry(E key, D value) {
            firstElement = key;
            secondElement = value;
        }

        public E getKey() {
            return firstElement;
        }

        public D getValue() {
            return secondElement;
        }

        /**
         * Sets a new value in the entry object, but does not modify the original multi vector object
         */
        public D setValue(D newValue) {
            secondElement = newValue;
            return secondElement;
        }

    }
    
}
