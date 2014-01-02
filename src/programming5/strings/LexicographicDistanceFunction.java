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
 * Implements a distance function based on lexicographic distance (the difference in the character codes of
 * corresponding characters, with left alignment).
 * <p> Currently implements two different modes:
 * <p> DEFAULT: Adds the difference of the the codes of characters at each position, where the empty character has
 * code 0; because of this, this mode is best suited for comparing strings of equal length, as unmatched characters
 * will contribute an arbitrary amount to the distance based on their character codes.
 * <p> COUNT_DIFF_CHARS: Adds 1 to the distance for every character that is different, including unmatched characters;
 * e.g. "example" and "exemplary" have a distance of 4.
 * <p> Note the consequence of the left alignment, which gives "assert" and "dessert" a distance of 6 instead of 2.
 * For a family of functions that finds a minimum distance by optimizing string alignment, try the StringEditDistanceFunction class
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
