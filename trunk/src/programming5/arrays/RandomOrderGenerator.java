/*
 * RandomOrderGenerator.java
 *
 * Copyright 2004 Andres Quiroz Hernandez
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

import java.util.Random;

/**
 *This class uses a Java Random object to generate arrays of indices in random orderings. These arrays can then be used
 *to access elements of another array or collection exhaustively in a random order.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class RandomOrderGenerator {
    
    private int[] order = null;
    private int index;
    private Random r;
    
    public RandomOrderGenerator() {
        r = new Random();
    }
    
    public RandomOrderGenerator(long seed) {
        r = new Random(seed);
    }
    
    /**
     *@return an array with elements from 0 to size-1 in random order
     */
    public int[] generate(int size) {
        order = new int[size];
        int[] auxorder = ArrayOperations.generateEnumeration(size);
        int aux = size;
        int pos;
        for (int i = 0; i < size; i++) {
            pos = r.nextInt(aux--);
            order[i] = auxorder[pos];
            auxorder[pos] = auxorder[aux];
        }
        index = 0;
        return order;
    }
    
    /**
     *Allows the cyclical traversal of the order that was generated
     *@throws java.lang.IllegalStateException if called before generate method has been called
     */
    public int nextIndex() {
        int ret = 0;
        if (order != null) {
            ret = order[index++];
            if (index == order.length) {
                index = 0;
            }
        } 
        else throw new IllegalStateException("RandomOrderGenerator: generate() must be called before nextIndex()");
        return ret;
    }
    
}
