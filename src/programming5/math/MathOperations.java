/*
 * MathOperations.java
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

package programming5.math;

import java.math.BigInteger;

/**
 * This class provides useful math functions, additional to those found in the java.lang.MathOperations class
 */
public final class MathOperations {
    
    /**
     *@return the given value rounded to the given number of decimals
     */
    public static final double fix(double value, int decimals) {
        if (decimals < 0) {
            throw new IllegalArgumentException("Math: fix: Cannot fix to a negative value");
        }
        double aux = java.lang.Math.pow(10d, (double)decimals);
        return java.lang.Math.round(value * aux)/aux;
    }
    
    /**
     *@return the factorial of the given number
     *@throws IllegalArgumentException if the number is negative
     */
    public static final long fact(int number) {
        long ret;
        if (number > 0) {
            ret = number;
            for (int i = number-1; i > 1; i--) {
                ret *= i;
            }
        } 
        else if (number == 0) {
            ret = 1L;
        }
        else throw new IllegalArgumentException("Math: fact: Argument must be a non-negative integer");
        return ret;
    }
    
    /**
     *@return the number of ordered combinations of k elements out of n total elements
     */
    public static final long combinations(int n, int k) {
        long ret = n;
        if (k >= 1 && k <= n) {
            int limit = n - k;
            for (int i = n-1; i > limit; i--) {
                ret *= i;
            }
        }
        else if (k == 0) {
            ret = 1;
        }
        else throw new IllegalArgumentException("MathOperations: choose: k must be netween 1 and n");
        return ret;
    }
    
    /**
     *@return n choose k, the number of unordered sets of k elements out of n total elements
     */
    public static final long choose(int n, int k) {
        long ret;
        if (k >= 1 && k < n) {
            ret = n;
            int limit = k;
            int divisor = n - k;
            if (k < (n/2)) {
                limit = divisor;
                divisor = k;
            }
            for (int i = n-1; i > limit; i--) {
                ret *= i;
            }
            ret = (long) (ret / fact(divisor));
        }
        else if (k == 0 || k == n) {
            ret = 1;
        }
        else throw new IllegalArgumentException("MathOperations: choose: k must be between 0 and n");
        return ret;
    }
    
    /**
     *Calculates the absolute angle of a position vector in a cartesian plane with respect to the positive X 
     *axis of the plane.
     *@param oppositeLength the value of the vector's projection onto the Y axis of the plane (signed)
     *@param adjacentLength the value of the vector's projection onto the X axis of the plane (signed)
     *@return an angle in the range [0, 2PI)
     */
    public static final double absoluteAngle(double oppositeLength, double adjacentLength) {
        double angle = 0;
        if (adjacentLength != 0) {
            double tanAngle = oppositeLength / adjacentLength;
            angle = java.lang.Math.atan(tanAngle);
            if (tanAngle >= 0) {
                if (adjacentLength < 0) {
                    angle += java.lang.Math.PI;
                }
            }
            else {
                if (adjacentLength > 0) {
                    angle += 2 * java.lang.Math.PI;
                }
                else {
                    angle += java.lang.Math.PI;
                }
            }
        }
        else {
            if (oppositeLength > 0) {
                angle = java.lang.Math.PI/2;
            }
            else if (oppositeLength < 0) {
                angle = (3*java.lang.Math.PI)/2;
            }
            else {
                throw new IllegalArgumentException("Cannot calculate absoluteArcTan: adjacentLength and oppositeLength cannot both be zero");
            }
        }
        return angle;
    }

    public static int compare(Number n, Number m) {
        if (extractValue(n) < extractValue(m)) {
            return -1;
        }
        else if (extractValue(n) > extractValue(m)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public static Number add(Number... nArray) {
        Number ret;
        double sum = 0;
        for (Number n : nArray) {
            sum += extractValue(n);
        }
        byte byteSum = (byte) sum;
        short shortSum = (short) sum;
        int intSum = (int) sum;
        long longSum = (long) sum;
        float floatSum = (float) sum;
        if (byteSum == sum) {
            ret = new Byte(byteSum);
        }
        else if (shortSum == sum) {
            ret = new Short(shortSum);
        }
        else if(intSum == sum) {
            ret = new Integer(intSum);
        }
        else if (longSum == sum) {
            ret = new Long(longSum);
        }
        else if (floatSum == sum) {
            ret = new Float(floatSum);
        }
        else {
            ret = new Double(sum);
        }
        return ret;
    }

    public static double extractValue(Number n) {
        double ret;
        if (n instanceof Integer) {
            ret = n.intValue();
        }
        else if (n instanceof Long) {
            ret = n.longValue();
        }
        else if (n instanceof Float) {
            ret = n.floatValue();
        }
        else if (n instanceof Double) {
            ret = n.doubleValue();
        }
        else if (n instanceof Short) {
            ret = n.shortValue();
        }
        else if (n instanceof Byte) {
            ret = n.byteValue();
        }
        else {
            ret = Double.NaN;
        }
        return ret;
    }
    
}
