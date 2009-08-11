/*
 * CollectionUtils.java
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

import java.util.Vector;

/**
 *Provides utility methods to use with/for collections classes
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public abstract class CollectionUtils {

    /**
     * To create a vector with the elements of a given array, for which there is surprisingly no constructor
     * in the Vector class.
     * @param array the input array; the elements of the array will be put into the vector by reference
     * @return the filled vector of the type of the input array elements
     */
    public static final <T> Vector<T> vectorFromArray(T[] array) {
        Vector<T> ret = new Vector<T>();
        for (T element : array) {
            ret.add(element);
        }
        return ret;
    }

    /**
     * To create a vector initialized to contain the given element, for which there is surprisingly no constructor
     * in the Vector class.
     * @param element the input element, which will be put into the vector by reference
     * @return the typed vector containing the given element
     */
    public static final <T> Vector<T> vectorFromElement(T element) {
        Vector<T> ret = new Vector<T>();
        ret.add(element);
        return ret;
    }

    /**
     * To create a vector initialized to contain the given element of a subclass of the vector type.
     * @param element the input element, which will be put into the vector by reference
     * @param type the vector type, which is a superclass of the element type.
     * @return the typed vector containing the given element
     */
    public static final <T, U extends T> Vector<T> vectorFromElement(U element, Class<T> type) {
        Vector<T> ret = new Vector<T>();
        ret.add(element);
        return ret;
    }

    /**
     * Upcasts the given vector of a derived class to a vector of a base class
     * @param vector the vector to downcast
     * @param type the superclass that will determine the returned vector class
     * @return a new vector of the base type with all the elements of the input vector (by
     * reference)
     */
    public static final <T, U extends T> Vector<T> upcastVector(Vector<U> vector, Class<T> type) {
        Vector<T> ret = new Vector<T>();
        for (U element : vector) {
            ret.add(element);
        }
        return ret;
    }

    /**
     * Downcasts the given vector of a base class, if possible, to a vector of a derived class.
     * @param vector the vector to upcast, which must be composed of elements of the derived class
     * @return a new vector of the derived class with all the elements of the input vector
     * @throws ClassCastException if the elements of the input vector are not of the derived class
     */
    public static final <T, U extends T> Vector<U> downcastVector(Vector<T> vector) {
        Vector<U> ret = new Vector<U>();
        for (T element : vector) {
            ret.add((U) element);
        }
        return ret;
    }

}
