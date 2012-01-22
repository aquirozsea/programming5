/*
 * RotatingList.java
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
import java.util.List;

/**
 * A rotating list is one that has a fixed capacity, so that elements added beyond this capacity are cycled around to the start of the list,
 * however many times is necessary. It is useful for implementing buffers. Element removal causes the insert position to be reset to the 
 * end of the list.
 * @author Andres Quiroz Hernandez
 * @version 6.19
 */
public class RotatingList<T> extends ArrayList<T> {
    
    private int pointer = 0;
    private int fixedSize = 10; // From ArrayList implementation
    
    /**
     *Creates a new rotating vector based on the default initial capacity of the ArrayList class.
     *@see java.util.List
     */
    public RotatingList() {
        super();
    }
    
    /**
     *Creates a new rotating list with the elements of the given collection. The list's capacity 
     *will be the default initial capacity of the ArrayList class given that number of elements.
     *@see java.util.List
     */
    public RotatingList(Collection<? extends T> c) {
        super(c);
        fixedSize = c.size();
    }
    
    /**
     *Creates a new rotating vector with the given initial capacity.
     */
    public RotatingList(int initialCapacity) {
        super(initialCapacity);
        fixedSize = initialCapacity;
    }

    /**
     *Adds an item after the position of the previous item or at the first position if the last item was 
     *added at the last available position given the list's capacity.
     *@return true always
     */
    @Override
    public boolean add(T item) {
        if (this.size() < fixedSize) {
            this.add(item);
            pointer = 0;
        }
        else {
            this.set(pointer++, item);
            if (pointer == fixedSize) {
                pointer = 0;
            }
        }
        return true;
    }
    
    /**
     *Equivalent to calling add method for each element in the collection.
     *@return true always
     *@see #add
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T item : c) {
            this.add(item);
        }
        return true;
    }
    
    /**
     *Adds the elements of the given collection to the list, starting at the given index
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        pointer = index;
        return this.addAll(c);
    }
    
    /**
     *@return the fixed size of the vector
     */
    public int capacity() {
        return fixedSize;
    }
    
    /**
     *Resets the fixed size of the vector and the insert position
     */
    public void setSize(int newSize) {
        fixedSize = newSize;
        if (pointer >= fixedSize) {
            pointer = 0;
        }
    }
    
    @Override
    public int size() {
        if (super.size() < fixedSize) {
            return super.size();
        }
        else {
            return fixedSize;
        }
    }
    
    @Override
    public void ensureCapacity(int capacity) {
        super.ensureCapacity(capacity);
        this.setSize(capacity);
    }
    
}
