/*
 * NDArray.java
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

package programming5.arrays;

/**
 * This class implements an N dimensional array, where N is determined at runtime given the number of dimensions
 * with which the object is constructed. It provides methods to index the array by a coordinate along each
 * dimension, in the way that a matrix/multidimensional primitive array type would be indexed (e.g. array[i][j][k]
 * == ndarray(i, j, k)). The reason for this class is that it is impossible to allocate a multidimensional array
 * at compile time in order to index it in this way if the number of dimensions is variable/unknown.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class NDArray<E> {

    protected Object[] array;
    protected int[] sizes;
    protected int[] step;
    protected int dimensions;

    /**
     * Creates a new array of the given dimensions; e.g. new NDArray(3, 2, 4) is equivalent to new array[3][2][4])
     * @param dimSizes variable argument, where each entry indicates the size along the corresponding dimension
     */
    public NDArray(int... dimSizes) {
        dimensions = dimSizes.length;
        sizes = dimSizes;
        step = new int[dimensions];
        step[dimensions-1] = 1;
        for (int i = dimensions-1; i > 0; i--) {
            step[i-1] = step[i] * sizes[i];
        }
        array = new Object[step[0] * sizes[0]];
    }

    public NDArray(E initializer, int... dimSizes) {
        dimensions = dimSizes.length;
        sizes = dimSizes;
        step = new int[dimensions];
        step[dimensions-1] = 1;
        for (int i = dimensions-1; i > 0; i--) {
            step[i-1] = step[i] * sizes[i];
        }
        array = new Object[step[0] * sizes[0]];
        initialize(initializer);
    }

    /**
     * Initializes the array with the value given
     * @param initializer the value that will fill the array
     */
    public void initialize(E initializer) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initializer;
        }
    }

    /**
     * Sets the given element in the array at the position given by the coordinates
     * @param element the element to store
     * @param coords variable argument; the number of coordinates must correspond to the number of dimensions of the array
     */
    public void set(E element, int... coords) {
        array[index(coords)] = element;
    }

    /**
     * Retrieves an element from the array with the coordinates given
     * @param coords variable argument; the number of coordinates must correspond to the number of dimensions of the array
     * @return the element at the given position in the array
     */
    public E get(int... coords) {
        return (E) array[index(coords)];
    }

    private int index(int[] coords) {
        int index = 0;
        for (int i = 0; i < dimensions; i++) {
            index += coords[i] * step[i];
        }
        return index;
    }

}
