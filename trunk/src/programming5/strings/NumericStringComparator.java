/*
 * NumericStringComparator.java
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

import java.util.Comparator;

/**
 * Implementation of the Comparator interface for string with a numeric value
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class NumericStringComparator implements Comparator<String> {

    /**
     * @return 0 if the numeric value of the strings is equal to within 10^-9; 1 if value(s1) > value(s2); -1 if value(s1) < value(s2)
     */
    public int compare(String s1, String s2) {
        try {
            double svalue1 = Double.parseDouble(s1);
            double svalue2 = Double.parseDouble(s2);
            double compValue = svalue1 - svalue2;
            if (Math.abs(compValue) < 0.000000001d) {
                return 0;
            }
            else {
                if (compValue > 0) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        }
        catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("NumericStringComparator: Cannot compare strings: Non-numeric", nfe);
        }
    }

}
