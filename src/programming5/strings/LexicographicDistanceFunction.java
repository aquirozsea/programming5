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

    public static enum Mode {DEFAULT, COUNT_DIFF_CHARS};

    private Mode distMode;

    public LexicographicDistanceFunction() {
        distMode = Mode.DEFAULT;
    }

    public LexicographicDistanceFunction(Mode myMode) {
        distMode = myMode;
    }

    public double distance(String obj1, String obj2) {
        int limit = (obj1.length() > obj2.length()) ? obj1.length() : obj2.length();
        double dist = 0;
        for (int i = 0; i < limit; i++) {
            char o1 = (i < obj1.length()) ? obj1.charAt(i) : (char) 0;
            char o2 = (i < obj2.length()) ? obj2.charAt(i) : (char) 0;
            dist += difference(o1, o2);
        }
        return dist;
    }

    private int difference(char c1, char c2) {
        if (c1 == c2) {
            return 0;
        }
        else {
            switch (distMode) {
                case COUNT_DIFF_CHARS: return 1;
                default: return Math.abs(c2 - c1);
            }
        }
    }

}
