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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import programming5.arrays.ArrayOperations;
import programming5.strings.StringOperations;

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

    protected int[] sortedOrder1 = null;
    protected int[] sortedOrder2 = null;
    protected boolean isSorted1 = false;
    protected boolean isSorted2 = false;
    
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
        isSorted1 = false;
        isSorted2 = false;
    }
    
    /**
     *@return a new multi-vector, using the Vector class clone method
     *@see java.util.Vector#clone
     */
    @Override
    public MultiVector<E, D> clone() {
        MultiVector<E, D> ret = new MultiVector<E, D>((Vector<E>) vector1.clone(), (Vector<D>) vector2.clone());
        if (this.isSorted1) {
            ret.sortedOrder1 = ArrayOperations.replicate(this.sortedOrder1);
            ret.isSorted1 = true;
        }
        if (this.isSorted2) {
            ret.sortedOrder2 = ArrayOperations.replicate(this.sortedOrder2);
            ret.isSorted2 = true;
        }
        return ret;
    }
    
    /**
     *@return true if either component of any element pair equals the given object, using the Vector contains method
     *@see java.util.Vector#contains
     */
    public boolean contains(Object object) {
        boolean contains;
        if (isSorted1) {

        }
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
        E ret;
        if (isSorted1) {
            ret = vector1.get(sortedOrder1[index]);
        }
        else {
            ret = vector1.elementAt(index);
        }
        return ret;
    }

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
            ret = vector2.elementAt(index);
        }
        return ret;
    }

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

    public Entry<E, D> getEntry(int index) {
        return new MultiVectorEntry<E, D>(this.getInFirstAt(index), this.getInSecondAtFirst(index));
    }

    public Entry<E, D> getEntryAtSecond(int index) {
        return new MultiVectorEntry<E, D>(this.getInFirstAtSecond(index), this.getInSecondAt(index));
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
        E ret;
        if (isSorted1) {
            ret = vector1.get(sortedOrder1[0]);
        }
        else {
            ret = vector1.firstElement();
        }
        return ret;
    }
    
    /**
     *@return the second component of the first element pair; analogous to Vector firstElement method
     *@see java.util.Vector#firstElement
     */
    public D firstElementFromSecond() {
        D ret;
        if (isSorted2) {
            ret = vector2.get(sortedOrder2[0]);
        }
        else {
            ret = vector2.firstElement();
        }
        return ret;
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
        int ret;
        if (isSorted1) {
            int unsortedIndex = vector1.indexOf(element);
            ret = ArrayOperations.seqFind(unsortedIndex, sortedOrder1);
        }
        else {
            ret = vector1.indexOf(element);
        }
        return ret;
    }
    
    /**
     *@return the index of the first element pair whose second component equals the given object, using the Vector indexOf method
     *@see java.util.Vector#indexOf
     */
    public int indexOfInSecond(D element) {
        int ret;
        if (isSorted2) {
            int unsortedIndex = vector2.indexOf(element);
            ret = ArrayOperations.seqFind(unsortedIndex, sortedOrder2);
        }
        else {
            ret = vector2.indexOf(element);
        }
        return ret;
    }
    
    /**
     *@return the index of the first element pair after the given start index whose first component equals the given object, using the Vector indexOf method
     *@see java.util.Vector#indexOf
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
            ret = vector1.indexOf(element, startIndex);
        }
        return ret;
    }
    
    /**
     *@return the index of the first element pair after the given start index whose second component equals the given object, using the Vector indexOf method
     *@see java.util.Vector#indexOf
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
            ret = vector2.indexOf(element, startIndex);
        }
        return ret;
    }
    
    /**
     *@return the index of the last element pair whose first component equals the given object, using the Vector lastIndexOf method
     *@see java.util.Vector#lastIndexOf
     */
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
     *@return the index of the last element pair whose second component equals the given object, using the Vector lastIndexOf method
     *@see java.util.Vector#lastIndexOf
     */
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
     *@return true if the multi-vector has no element pairs
     */
    public boolean isEmpty() {
        return vector1.isEmpty();
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
            ret = vector1.lastElement();
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
            ret = vector2.lastElement();
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
    public boolean setInFirst(D match, E object) {
        boolean modified = true;
        try {
            vector1.set(vector2.indexOf(match), object);
        }
        catch (ArrayIndexOutOfBoundsException iobe) {
            modified = false;
        }
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
    public boolean setInSecond(E match, D object) {
        boolean modified = true;
        try {
            vector2.set(vector1.indexOf(match), object);
        }
        catch (ArrayIndexOutOfBoundsException iobe) {
            modified = false;
        }
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
        Vector all = (Vector) vector1.clone();
        all.addAll((Vector) vector2.clone());
        return all.iterator();
    }

    public Iterator<Entry<E, D>> entryIterator() {
        Iterator<Entry<E, D>> ret;
        List<Map.Entry<E, D>> entryList = new ArrayList<Map.Entry<E, D>>();
        for (int i = 0; i < this.size(); i++) {
            Map.Entry<E, D> entry = new MultiVectorEntry<E, D>(vector1.elementAt(i), vector2.elementAt(i));
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
    public Vector<E> first() {
        Vector<E> ret;
        if (isSorted1) {
            ret = new Vector<E>();
            for (int i = 0; i < sortedOrder1.length; i++) {
                ret.add(vector1.get(sortedOrder1[i]));
            }
        }
        else {
            ret = (Vector<E>) vector1.clone();
        }
        return ret;
    }
    
    /**
     *@return a clone of the vector of second components of element pairs
     */
    public Vector<D> second() {
        Vector<D> ret;
        if (isSorted2) {
            ret = new Vector<D>();
            for (int i = 0; i < sortedOrder2.length; i++) {
                ret.add(vector2.get(sortedOrder2[i]));
            }
        }
        else {
            ret = (Vector<D>) vector2.clone();
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
        return new HashSet(vector1);
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
