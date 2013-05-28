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

    /**
     * Returns the alphabetic (lexicographic of lower-case letters) distance of the first differing character in the two 
     * strings, to a negative exponent based on the position of that differing character (thus, strings that have more 
     * characters in common will be closer together). If all of the characters of the two strings are equal, except that 
     * the strings are of different length, then the distance is the distance from 'a' of the first non-matched character 
     * of the longer string plus one, to an exponent equal to the length of the shorter string. This is:
     * abs(str1.charAt(i) - str2.charAt(i)) E -i : i is the first differing character (i+1 if dist > 10)
     * abs('a' - longer.charAt(shorter.length())) E -shorter.length() (+1 if dist > 10)
     */
    public double distance(String obj1, String obj2) {
        obj1 = obj1.toLowerCase();
        obj2 = obj2.toLowerCase();
        // Trivial case
        if (obj1.equals(obj2)) {return 0;}
        // Comparison
        double ret = 0;
        int limit = (obj1.length() <= obj2.length()) ? obj1.length() : obj2.length();
        int i = 0;
        for (; i < limit; i++) {
            ret = Math.abs(obj1.charAt(i) - obj2.charAt(i));
            if (ret > 0) {
                break;
            }
        }
        if (ret == 0) { // i == limit
            if (obj1.length() < obj2.length()) {
                ret = Math.abs('a' - obj2.charAt(limit)) + 1;
            }
            else {
                ret = Math.abs('a' - obj1.charAt(limit)) + 1;
            }
        }
        if (ret >= 10 && i > 0) {
            i++;
        }
        ret = (i > 0) ? ret * Math.pow(10, -i) : ret;
        return ret;
    }

}
