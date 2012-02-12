/*
 * NumberRange.java
 *
 * Copyright 2005 Andres Quiroz Hernandez
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

package programming5.math;

import java.util.Map;
import programming5.strings.StringOperations;

/**
 *This class provides a convenient way of defining and enforcing a range for a numeric value.
 *@author Andres Quiroz Hernandez
 *@version 6.01
 */
public class NumberRange {
    
    protected double lower, upper;
    protected boolean includeLower = true;
    protected boolean includeUpper = true;
    
    public static final boolean INCLUSIVE = true;
    public static final boolean EXCLUSIVE = false;
    
    /**
     *@param lowerLimit the lower limit, which is included in the range by default
     *@param upperLimit the upper limit, which is included in the range by default
     *@deprecated use of static create method is preferred, since it is more explicit
     */
    @Deprecated
    public NumberRange(int lowerLimit, int upperLimit) {
        if (lowerLimit <= upperLimit) {
            lower = lowerLimit;
            upper = upperLimit;
        }
        else {
            throw new IllegalArgumentException("NumberRange: Lower limit must be less than upper limit");
        }
    }
    
    /**
     *@param lowerLimit the lower limit, which is included in the range by default
     *@param upperLimit the upper limit, which is included in the range by default
     *@deprecated use of static create method is preferred, since it is more explicit
     */
    @Deprecated
    public NumberRange(float lowerLimit, float upperLimit) {
        if (lowerLimit <= upperLimit) {
            lower = lowerLimit;
            upper = upperLimit;
        }
        else {
            throw new IllegalArgumentException("NumberRange: Lower limit must be less than upper limit");
        }
    }
    
    /**
     *@param lowerLimit the lower limit, which is included in the range by default
     *@param upperLimit the upper limit, which is included in the range by default
     *@deprecated use of static create method is preferred, since it is more explicit
     */
    @Deprecated
    public NumberRange(double lowerLimit, double upperLimit) {
        if (lowerLimit <= upperLimit) {
            lower = lowerLimit;
            upper = upperLimit;
        }
        else {
            throw new IllegalArgumentException("NumberRange: Lower limit must be less than upper limit");
        }
    }
    
    /**
     *@param lowerLimit the lower limit
     *@param inclusiveLower indicates if the lower limit is included in the range
     *@param upperLimit the upper limit
     *@param inclusiveUpper indicates if the upper limit is included in the range
     */
    public NumberRange(int lowerLimit, boolean inclusiveLower, int upperLimit, boolean inclusiveUpper) {
        if (lowerLimit <= upperLimit) {
            lower = lowerLimit;
            upper = upperLimit;
            includeLower = inclusiveLower;
            includeUpper = inclusiveUpper;
        }
        else {
            throw new IllegalArgumentException("NumberRange: Lower limit must be less than upper limit");
        }
    }
    
    /**
     *@param lowerLimit the lower limit
     *@param inclusiveLower indicates if the lower limit is included in the range
     *@param upperLimit the upper limit
     *@param inclusiveUpper indicates if the upper limit is included in the range
     */
    public NumberRange(float lowerLimit, boolean inclusiveLower, float upperLimit, boolean inclusiveUpper) {
        if (lowerLimit <= upperLimit) {
            lower = lowerLimit;
            upper = upperLimit;
            includeLower = inclusiveLower;
            includeUpper = inclusiveUpper;
        }
        else {
            throw new IllegalArgumentException("NumberRange: Lower limit must be less than upper limit");
        }
    }
    
    /**
     *@param lowerLimit the lower limit
     *@param inclusiveLower indicates if the lower limit is included in the range
     *@param upperLimit the upper limit
     *@param inclusiveUpper indicates if the upper limit is included in the range
     */
    public NumberRange(double lowerLimit, boolean inclusiveLower, double upperLimit, boolean inclusiveUpper) {
        if (lowerLimit <= upperLimit) {
            lower = lowerLimit;
            upper = upperLimit;
            includeLower = inclusiveLower;
            includeUpper = inclusiveUpper;
        }
        else {
            throw new IllegalArgumentException("NumberRange: Lower limit must be less than upper limit");
        }
    }

    /**
     * Creates a number range from a range string, using parentheses for exclusive limits and brackets for inclusive limits.
     * @param rangeString the string representation of the range, e.g. (1,2) ; (3, 4] ; [20, 35) ; [0 , 100]
     * @throws IllegalArgumentException if the range string is not a valid range expression
     */
    public static NumberRange create(String rangeString) {
        try {
            Map<String, String> decoding = StringOperations.decodePattern(rangeString, "[\\(\\[]<ll>,<ul>[\\)\\]]");
            double lowerLimit = Double.parseDouble(decoding.get("ll").trim());
            double upperLimit = Double.parseDouble(decoding.get("ul").trim());
            return new NumberRange(lowerLimit, rangeString.startsWith("["), upperLimit, rangeString.endsWith("]"));
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("NumberRange: Cannot create from string: Not a valid range expression", iae);
        }
    }
    
    /**
     *Checks if the given value is in the range, according to the definition of inclusion of the range (the default is 
     *for both limits to be included in the range).
     */
    public boolean contains(int value) {
        boolean meetsLower = false;
        boolean meetsUpper = false;
        if (value > lower) {
            meetsLower = true;
        }
        else if (includeLower && value == lower) {
            meetsLower = true;
        }
        if (meetsLower) {
            if (value < upper) {
                meetsUpper = true;
            }
            else if (includeUpper && value == upper) {
                meetsUpper = true;
            }
        }
        return (meetsLower && meetsUpper);
    }
    
    /**
     *Checks if the given value is in the range, according to the definition of inclusion of the range (the default is 
     *for both limits to be included in the range).
     */
    public boolean contains(float value) {
        boolean meetsLower = false;
        boolean meetsUpper = false;
        if (value > lower) {
            meetsLower = true;
        }
        else if (includeLower && value == lower) {
            meetsLower = true;
        }
        if (meetsLower) {
            if (value < upper) {
                meetsUpper = true;
            }
            else if (includeUpper && value == upper) {
                meetsUpper = true;
            }
        }
        return (meetsLower && meetsUpper);
    }
    
    /**
     *Checks if the given value is in the range, according to the definition of inclusion of the range (the default is 
     *for both limits to be included in the range).
     */
    public boolean contains(double value) {
        boolean meetsLower = false;
        boolean meetsUpper = false;
        if (value > lower) {
            meetsLower = true;
        }
        else if (includeLower && value == lower) {
            meetsLower = true;
        }
        if (meetsLower) {
            if (value < upper) {
                meetsUpper = true;
            }
            else if (includeUpper && value == upper) {
                meetsUpper = true;
            }
        }
        return (meetsLower && meetsUpper);
    }
    
    /**
     *Checks if the given value is in the range, including both limits (value E [lowerLimit, upperLimit])
     */
    public boolean containsInclusive(int value) {
        return (value >= lower && value <= upper);
    }
    
    /**
     *Checks if the given value is in the range, including both limits (value E [lowerLimit, upperLimit])
     */
    public boolean containsInclusive(float value) {
        return (value >= lower && value <= upper);
    }
    
    /**
     *Checks if the given value is in the range, including both limits (value E [lowerLimit, upperLimit])
     */
    public boolean containsInclusive(double value) {
        return (value >= lower && value <= upper);
    }
    
    /**
     *Checks if the given value is in the range, excluding both limits (value E (lowerLimit, upperLimit))
     */
    public boolean containsExclusive(int value) {
        return (value > lower && value < upper);
    }
    
    /**
     *Checks if the given value is in the range, excluding both limits (value E (lowerLimit, upperLimit))
     */
    public boolean containsExclusive(float value) {
        return (value > lower && value < upper);
    }
    
    /**
     *Checks if the given value is in the range, excluding both limits (value E (lowerLimit, upperLimit))
     */
    public boolean containsExclusive(double value) {
        return (value > lower && value < upper);
    }
    
    /**
     *Checks if the given value is in the range, including the lower limit (value E [lowerLimit, upperLimit))
     */
    public boolean containsInclusiveLower(int value) {
        return (value >= lower && value < upper);
    }
    
    /**
     *Checks if the given value is in the range, including the lower limit (value E [lowerLimit, upperLimit))
     */
    public boolean containsInclusiveLower(float value) {
        return (value >= lower && value < upper);
    }
    
    /**
     *Checks if the given value is in the range, including the lower limit (value E [lowerLimit, upperLimit))
     */
    public boolean containsInclusiveLower(double value) {
        return (value >= lower && value < upper);
    }
    
    /**
     *Checks if the given value is in the range, including the upper limit (value E (lowerLimit, upperLimit])
     */
    public boolean containsInclusiveUpper(int value) {
        return (value > lower && value <= upper);
    }
    
    /**
     *Checks if the given value is in the range, including the upper limit (value E (lowerLimit, upperLimit])
     */
    public boolean containsInclusiveUpper(float value) {
        return (value > lower && value <= upper);
    }
    
    /**
     *Checks if the given value is in the range, including the upper limit (value E (lowerLimit, upperLimit])
     */
    public boolean containsInclusiveUpper(double value) {
        return (value > lower && value <= upper);
    }

    public double getLowerLimit() {
        return lower;
    }

    public double getUpperLimit() {
        return upper;
    }
    
    public String toString() {
        String open, close, lowerString, upperString;
        if (includeLower) {
            open = "[";
        }
        else {
            open = "(";
        }
        if (includeUpper) {
            close = "]";
        }
        else {
            close = ")";
        }
        if (lower == java.lang.Math.round(lower)) {
            lowerString = Integer.toString((int) lower);
        }
        else {
            lowerString = Double.toString(lower);
        }
        if (upper == java.lang.Math.round(upper)) {
            upperString = Integer.toString((int) upper);
        }
        else {
            upperString = Double.toString(upper);
        }
        return open + lowerString + ", " + upperString + close;
    }

}
