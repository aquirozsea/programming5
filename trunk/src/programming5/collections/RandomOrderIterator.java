/*
 * RandomOrderIterator.java
 *
 * Copyright 2009 Andres Quiroz Hernandez
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
import java.util.Iterator;
import programming5.arrays.RandomOrderGenerator;

/**
 * This class allows the traversal of a given collection in random order. Modifications to the collection will not be reflected in the objects returned
 * @author Andres
 * @version 6.0
 */
public class RandomOrderIterator<T> implements Iterator<T> {
    
    protected Object[] array;
    protected RandomOrderGenerator rog;
    
    private int count = 0;
    
    /**
     *Creates an iterator object to randomly traverse the given collection (can be traversed cyclically, in a new order each time)
     */
    public RandomOrderIterator(Collection<T> myCollection) {
        array = myCollection.toArray();
        rog = new RandomOrderGenerator();
        rog.generate(array.length);
    }

    /**
     *Creates an iterator object to randomly traverse the given collection (can be traversed cyclically, in a new order each time) using the given seed for the random order generator
     */
    public RandomOrderIterator(Collection<T> myCollection, long seed) {
        array = myCollection.toArray();
        rog = new RandomOrderGenerator(seed);
        rog.generate(array.length);
    }

    /**
     *@return false once all of the elements of the collection have been traversed; is reset if next is called to start a new traversal
     */
    public boolean hasNext() {
        return (count != array.length);
    }

    /**
     *@return a randomly chosen element of the collection that has not been traversed in the current order
     */
    public T next() {
        count++;
        if (count > array.length) {
            rog.generate(array.length);
            count = 1;
        }
        return (T) array[rog.nextIndex()];
    }

    /**
     *Method not supported by this implementation
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
}
