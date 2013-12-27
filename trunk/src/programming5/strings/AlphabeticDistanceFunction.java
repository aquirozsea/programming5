/*
 * AlphabeticDistanceFunction.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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

package programming5.strings;

import programming5.math.DistanceFunction;

/**
 * Implements a distance function that compares strings alphabetically. Will accept non-alphabetic strings, but the distance is not well defined.
 * @author Andres Quiroz Hernandez
 * @version 6.9
 */
public class AlphabeticDistanceFunction implements DistanceFunction<String> {

    private static final double factor = 1d / ('z' - 'a' + 2);

    public double distance(String obj1, String obj2) {
        String smaller, larger;
        if (obj1.compareTo(obj2) < 0) {
            smaller = obj1;
            larger = obj2;
        }
        else {
            smaller = obj2;
            larger = obj1;
        }
        int[] diff = new int[((obj1.length() > obj2.length()) ? obj1.length() : obj2.length())];
        boolean borrow = false;
        for (int i = diff.length-1; i >= 0; i--) {
            char l = (i < larger.length()) ? larger.charAt(i) : ('a' - 1);
            if (borrow) {
                l = (l == ('a' - 1)) ? 'z' : (char) (l-1);
            }
            char s = (i < smaller.length()) ? smaller.charAt(i) : ('a' - 1);
            if (l >= s) {
                diff[i] = l - s;
                borrow = false;
            }
            else {
                diff[i] = ('z' - s + 1) + (l - 'a' + 1);
                borrow = true;
            }
        }
        double dist = diff[0];
        double exp = factor;
        for (int i = 1; i < diff.length; i++) {
            dist += diff[i] * exp;
            exp *= exp; // TODO: Verify epsilon
        }
        return dist;
    }

}
