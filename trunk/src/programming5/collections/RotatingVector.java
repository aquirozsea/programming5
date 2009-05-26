/*
 * RotatingVector.java
 *
 * Copyright 2008 Andres Quiroz Hernandez
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
import java.util.Vector;

/**
 * A rotating vector is one that has a fixed capacity, so that elements added beyond this capacity are cycled around to the start of the vector,
 * however many times is necessary. It is useful for implementing buffers. Element removal causes the insert position to be reset to the 
 * end of the vector.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class RotatingVector<E> extends Vector<E> {
    
    private int pointer = 0;
    private int fixedSize;
    
    /**
     *Creates a new rotating vector based on the default initial capicity of the Vector class.
     *@see java.util.Vector
     */
    public RotatingVector() {
        super();
        fixedSize = super.capacity();
    }
    
    /**
     *Creates a new rotating vector with the elements of the given collection. The vector's capacity 
     *will be the default initial capacity of the vector class given that number of elements.
     *@see java.util.Vector
     */
    public RotatingVector(Collection<? extends E> c) {
        super(c);
        fixedSize = super.capacity();
    }
    
    /**
     *Creates a new rotating vector with the given initial capicity.
     */
    public RotatingVector(int initialCapacity) {
        super(initialCapacity);
        fixedSize = initialCapacity;
    }
    
    /**
     *Adds an item after the position of the previous item or at the first position if the last item was 
     *added at the last available position given the vector's capacity.
     *@return true always
     */
    public synchronized boolean add(E item) {
        if (this.size() < fixedSize) {
            super.add(item);
            pointer = 0;
        }
        else {
            this.setElementAt(item, pointer++);
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
    public boolean addAll(Collection<? extends E> c) {
        for (E item : c) {
            this.add(item);
        }
        return true;
    }
    
    /**
     *Adds the elements of the given collection to the vector, starting at the given index
     */
    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        pointer = index;
        return this.addAll(c);
    }
    
    /**
     *Equivalent to add
     *@see #add
     */
    public void addElement(E item) {
        this.add(item);
    }
    
    /**
     *@return the fixed size of the vector
     */
    public int capacity() {
        return fixedSize;
    }
    
    /**
     *@return a clone of this vector, using the Vector clone method.
     *@see java.util.Vector#clone
     */
    public Object clone() {
        return new RotatingVector((Vector) super.clone());
    }
    
    /**
     *Resets the fixed size of the vector and the insert position
     */
    public synchronized void setSize(int newSize) {
        fixedSize = newSize;
        if (pointer >= fixedSize) {
            pointer = 0;
        }
        super.setSize(newSize);
    }
    
}
