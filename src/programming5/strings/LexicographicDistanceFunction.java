/*
 * LexicographicDistanceFunction.java
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
 * Implements a distance function based on the current String class compareTo method (abs(s1.compareTo(s2)))
 * @author Andres Quiroz Hernandez
 * @version 6.9
 */
public class LexicographicDistanceFunction implements DistanceFunction<String> {

    public double distance(String obj1, String obj2) {
        // Trivial case
        if (obj1.equals(obj2)) {return 0;}
        // Comparison
        double ret = 0;
        int limit = (obj1.length() <= obj2.length()) ? obj1.length() : obj2.length();
        for (int i = 0; i < limit; i++) {
            ret = Math.abs(obj1.charAt(i) - obj2.charAt(i));
            if (ret > 0) {
                break;
            }
        }
        if (ret == 0) {
            ret = Math.abs(obj1.length() - obj2.length());
        }
        return ret;
    }

}
