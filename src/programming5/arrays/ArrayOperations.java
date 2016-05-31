/*
 * ArrayOperations.java
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

import jdk.nashorn.internal.ir.annotations.Immutable;
import programming5.code.ObjectMatcher;
import programming5.code.Replicable;
import programming5.collections.NotFoundException;
import programming5.math.DistanceFunction;
import programming5.strings.LexicographicDistanceFunction;
import programming5.strings.LexicographicStringComparator;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntToDoubleFunction;

/**
 *This class provides additional array manipulation operations to those in java.util.Arrays.
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public abstract class ArrayOperations {

    public static PrintStream OUT = System.out;
    public static DistanceFunction<String> DEFAULT_STRING_DISTFUNC = new LexicographicDistanceFunction(LexicographicDistanceFunction.Mode.COUNT_DIFF_CHARS);
    public static Comparator<String> DEFAULT_STRING_COMPARATOR = new LexicographicStringComparator();
    
    /**
     *@return a new copy of the input array
     */
    @Immutable
    public static byte[] replicate(byte[] array) {
        if (array == null) {return null;}
        byte[] ret = new byte[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    @Immutable
    public static int[] replicate(int[] array) {
        if (array == null) {return null;}
        int[] ret = new int[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    @Immutable
    public static float[] replicate(float[] array) {
        if (array == null) {return null;}
        float[] ret = new float[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    @Immutable
    public static double[] replicate(double[] array) {
        if (array == null) {return null;}
        double[] ret = new double[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    @Immutable
    public static char[] replicate(char[] array) {
        if (array == null) {return null;}
        char[] ret = new char[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    @Immutable
    public static String[] replicate(String[] array) {
        if (array == null) {return null;}
        String[] ret = new String[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return ret;
    }

    /**
     *@return a new copy of the input array
     */
    @Immutable
    public static long[] replicate(long[] array) {
        if (array == null) {return null;}
        long[] ret = new long[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return ret;
    }
    
    /**
     *@param source the array to be replicated
     *@param destination the pre-allocated destination array, of equal size as the source array, which will be filled with values from the source
     *@throws java.lang.IllegalArgumentException if the arrays are of different sizes
     */
    public static <T> void replicate(T[] source, T[] destination) {
        if (source.length == destination.length) {
            System.arraycopy(source, 0, destination, 0, source.length);
        } 
        else {
            throw new IllegalArgumentException("ArrayOperations: Could not replicate source array: Arrays of different dimensions");
        }
    }
    
    /**
     *@param source the array to be replicated
     *@param destination the pre-allocated destination array, of equal size as the source array, which will be filled with values from the source
     *@throws java.lang.IllegalArgumentException if the arrays are of different sizes
     */
    @Immutable
    public static <T extends Replicable> void replicate(T[] source, T[] destination) {
        if (source.length == destination.length) {
            for (int i = 0; i < source.length; i++) {
                destination[i] = (T) source[i].replicate();
            }
        }
        else {
            throw new IllegalArgumentException("ArrayOperations: Could not replicate source array: Arrays of different dimensions");
        }
    }
    
    /**
     *@return the sum of the elements of the input array
     */
    @Immutable
    public static int sum(int... array) {
        int sum = 0;
        for (int elem : array) {
            sum += elem;
        }
        return sum;
    }
    
    /**
     *@return the sum of the elements of the input array
     */
    @Immutable
    public static float sum(float... array) {
        float sum = 0;
        for (float elem : array) {
            sum += elem;
        }
        return sum;
    }
    
    /**
     *@return the sum of the elements of the input array
     */
    @Immutable
    public static double sum(double... array) {
        double sum = 0;
        for (double elem : array) {
            sum += elem;
        }
        return sum;
    }

    /**
     * @return the product of the elements of the given array (beware of overflow)
     */
    @Immutable
    public static long product(int... array) {
        if (array.length == 0) {return 0;}
        long product = 1;
        for (int elem : array) {
            product *= elem;
        }
        return product;
    }

    /**
     * @return the product of the elements of the given array (using a BigInteger in case of overflow)
     */
    @Immutable
    public static BigInteger bigProduct(int... array) {
        if (array.length == 0) {return BigInteger.ZERO;}
        BigInteger product = BigInteger.ONE;
        for (int elem : array) {
            product = product.multiply(BigInteger.valueOf(elem));
        }
        return product;
    }

    /**
     * @return the product of the elements of the given array (using a BigInteger in case of overflow)
     */
    @Immutable
    public static BigInteger bigProduct(long... array) {
        if (array.length == 0) {return BigInteger.ZERO;}
        BigInteger product = BigInteger.ONE;
        for (long elem : array) {
            product = product.multiply(BigInteger.valueOf(elem));
        }
        return product;
    }

    /**
     * @return the product of the elements of the given array (beware of overflow)
     */
    @Immutable
    public static double product(float... array) {
        if (array.length == 0) {return 0;}
        double product = 1;
        for (float elem : array) {
            product *= elem;
        }
        return product;
    }

    /**
     * @return the product of the elements of the given array (beware of overflow)
     */
    @Immutable
    public static double product(double... array) {
        if (array.length == 0) {return 0;}
        double product = 1;
        for (double elem : array) {
            product *= elem;
        }
        return product;
    }
    
    /**
     *@return the average of the elements of the input array
     */
    @Immutable
    public static double avg(int... array) {
        if (array.length == 0) {return 0;}
        return (double)sum(array)/(double)array.length;
    }
    
    /**
     *@return the average of the elements of the input array
     */
    @Immutable
    public static double avg(float... array) {
        if (array.length == 0) {return 0;}
        return (double)sum(array)/(double)array.length;
    }
    
    /**
     *@return the average of the elements of the input array
     */
    @Immutable
    public static double avg(double... array) {
        if (array.length == 0) {return 0;}
        return sum(array)/(double)array.length;
    }
    
    /**
     *@return the maximum element value of the elements of the input array
     */
    @Immutable
    public static int max(int... array) {
        int ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > ret) {
                ret = array[i];
            }
        }
        return ret;
    }
    
    /**
     *@return the maximum element value of the elements of the input array
     */
    @Immutable
    public static float max(float... array) {
        float ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > ret) {
                ret = array[i];
            }
        }
        return ret;
    }
    
    /**
     *@return the maximum element value of the elements of the input array
     */
    @Immutable
    public static double max(double... array) {
        double ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > ret) {
                ret = array[i];
            }
        }
        return ret;
    }
    
    /**
     *@return the maximum element value of the elements of the input array, with respect to the given comparator
     */
    @Immutable
    public static <T, S extends T> Object max(S[] array, Comparator<T> comp) {
        S ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comp.compare(array[i], ret) > 0) {
                ret = array[i];
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of maximum value in the input array
     */
    @Immutable
    public static int maxIndex(int[] array) {
        int ret = 0;
        int maxval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxval) {
                maxval = array[i];
                ret = i;
            }
        }
        return ret;
    }

    /**
     *@return the index of the element of maximum value in the input array
     */
    @Immutable
    public static int maxIndex(long[] array) {
        int ret = 0;
        long maxval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxval) {
                maxval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of maximum value in the input array
     */
    @Immutable
    public static int maxIndex(float[] array) {
        int ret = 0;
        float maxval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxval) {
                maxval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of maximum value in the input array
     */
    @Immutable
    public static int maxIndex(double[] array) {
        int ret = 0;
        double maxval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxval) {
                maxval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of maximum value in the input array, with respect to the given comparator
     */
    @Immutable
    public static <T, S extends T> int maxIndex(S[] array, Comparator<T> comp) {
        int ret = 0;
        S maxval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comp.compare(array[i], maxval) > 0) {
                maxval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return the element of minimum value of the elements in the array
     */
    @Immutable
    public static int min(int... array) {
        int ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < ret)
                ret = array[i];
        }
        return ret;
    }
    
    /**
     *@return the element of minimum value of the elements in the array
     */
    @Immutable
    public static float min(float... array) {
        float ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < ret)
                ret = array[i];
        }
        return ret;
    }
    
    /**
     *@return the element of minimum value of the elements in the array
     */
    @Immutable
    public static double min(double... array) {
        double ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < ret)
                ret = array[i];
        }
        return ret;
    }
    
    /**
     *@return the element of minimum value of the elements in the array, with respect to the given comparator
     */
    @Immutable
    public static <T, S extends T> Object min(S[] array, Comparator<T> comp) {
        S ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comp.compare(array[i], ret) < 0) {
                ret = array[i];
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of minimum value of the elements in the input array
     */
    @Immutable
    public static int minIndex(int[] array) {
        int ret = 0;
        int minval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minval) {
                minval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of minimum value of the elements in the input array
     */
    @Immutable
    public static int minIndex(float[] array) {
        int ret = 0;
        float minval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minval) {
                minval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of minimum value of the elements in the input array
     */
    @Immutable
    public static int minIndex(double[] array) {
        int ret = 0;
        double minval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minval) {
                minval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of minimum value of the elements in the input array, with respect to the given comparator
     */
    @Immutable
    public static <T, S extends T> int minIndex(S[] array, Comparator<T> comp) {
        int ret = 0;
        S maxval = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comp.compare(array[i], maxval) < 0) {
                maxval = array[i];
                ret = i;
            }
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    @Immutable
    public static byte[] prefix(byte[] array, int until) throws ArrayIndexOutOfBoundsException {
        byte[] ret = new byte[until];
        System.arraycopy(array, 0, ret, 0, until);
        return ret;
    }
    
    /**
     *@return  a new subarray of elements of array from 0 to that indexed by until-1
     */
    @Immutable
    public static int[] prefix(int[] array, int until) throws ArrayIndexOutOfBoundsException {
        int[] ret = new int[until];
        System.arraycopy(array, 0, ret, 0, until);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    @Immutable
    public static float[] prefix(float[] array, int until) throws ArrayIndexOutOfBoundsException {
        float[] ret = new float[until];
        System.arraycopy(array, 0, ret, 0, until);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    @Immutable
    public static double[] prefix(double[] array, int until) throws ArrayIndexOutOfBoundsException {
        double[] ret = new double[until];
        System.arraycopy(array, 0, ret, 0, until);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    @Immutable
    public static char[] prefix(char[] array, int until) throws ArrayIndexOutOfBoundsException {
        char[] ret = new char[until];
        System.arraycopy(array, 0, ret, 0, until);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    @Immutable
    public static String[] prefix(String[] array, int until) throws ArrayIndexOutOfBoundsException {
        String[] ret = new String[until];
        System.arraycopy(array, 0, ret, 0, until);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1. Caution: Does not replicate objects
     */
    @Immutable
    public static Object[] prefix(Object[] array, int until) throws ArrayIndexOutOfBoundsException {
        Object[] ret = new Object[until];
        System.arraycopy(array, 0, ret, 0, until);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    @Immutable
    public static byte[] suffix(byte[] array, int from) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        byte[] ret = new byte[array.length-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    @Immutable
    public static int[] suffix(int[] array, int from) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        int[] ret = new int[array.length-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    @Immutable
    public static float[] suffix(float[] array, int from) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        float[] ret = new float[array.length-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    @Immutable
    public static double[] suffix(double[] array, int from) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        double[] ret = new double[array.length-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    @Immutable
    public static char[] suffix(char[] array, int from) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        char[] ret = new char[array.length-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    @Immutable
    public static String[] suffix(String[] array, int from) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        String[] ret = new String[array.length-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element. Caution: Does not replicate objects!
     */
    @Immutable
    public static Object[] suffix(Object[] array, int from) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        Object[] ret = new Object[array.length-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    @Immutable
    public static byte[] subArray(byte[] array, int from, int until) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        byte[] ret = new byte[until-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    @Immutable
    public static int[] subArray(int[] array, int from, int until) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        int[] ret = new int[until-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    @Immutable
    public static float[] subArray(float[] array, int from, int until) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        float[] ret = new float[until-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    @Immutable
    public static double[] subArray(double[] array, int from, int until) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        double[] ret = new double[until-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    @Immutable
    public static char[] subArray(char[] array, int from, int until) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        char[] ret = new char[until-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    @Immutable
    public static String[] subArray(String[] array, int from, int until) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        String[] ret = new String[until-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1. Caution: Does not replicate objects!
     */
    @Immutable
    public static Object[] subArray(Object[] array, int from, int until) throws ArrayIndexOutOfBoundsException, NegativeArraySizeException {
        Object[] ret = new Object[until-from];
        System.arraycopy(array, from, ret, 0, ret.length);
        return ret;
    }
    
    /**
     *@return a new array containing the elements of all arrays in sequential order
     */
    @Immutable
    public static byte[] join(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] array : arrays) {
            totalLength += array.length;
        }
        byte[] ret = new byte[totalLength];
        int pos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of all arrays in sequential order
     */
    @Immutable
    public static int[] join(int[]... arrays) {
        int totalLength = 0;
        for (int[] array : arrays) {
            totalLength += array.length;
        }
        int[] ret = new int[totalLength];
        int pos = 0;
        for (int[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of all arrays in sequential order
     */
    @Immutable
    public static float[] join(float[]... arrays) {
        int totalLength = 0;
        for (float[] array : arrays) {
            totalLength += array.length;
        }
        float[] ret = new float[totalLength];
        int pos = 0;
        for (float[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of all arrays in sequential order
     */
    @Immutable
    public static double[] join(double[]... arrays) {
        int totalLength = 0;
        for (double[] array : arrays) {
            totalLength += array.length;
        }
        double[] ret = new double[totalLength];
        int pos = 0;
        for (double[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of all arrays in sequential order
     */
    @Immutable
    public static char[] join(char[]... arrays) {
        int totalLength = 0;
        for (char[] array : arrays) {
            totalLength += array.length;
        }
        char[] ret = new char[totalLength];
        int pos = 0;
        for (char[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }

    /**
     *@return a new array containing the elements of all arrays in sequential order
     */
    @Immutable
    public static boolean[] join(boolean[]... arrays) {
        int totalLength = 0;
        for (boolean[] array : arrays) {
            totalLength += array.length;
        }
        boolean[] ret = new boolean[totalLength];
        int pos = 0;
        for (boolean[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of all arrays in sequential order
     */
    @Immutable
    public static String[] join(String[]... arrays) {
        int totalLength = 0;
        for (String[] array : arrays) {
            totalLength += array.length;
        }
        String[] ret = new String[totalLength];
        int pos = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of all arrays in sequential order.
     *Caution: Does not replicate objects!
     */
    @Immutable
    public static Object[] join(Object[]... arrays) {
        int totalLength = 0;
        for (Object[] array : arrays) {
            totalLength += array.length;
        }
        Object[] ret = new Object[totalLength];
        int pos = 0;
        for (Object[] array : arrays) {
            System.arraycopy(array, 0, ret, pos, array.length);
            pos += array.length;
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    @Immutable
    public static byte[] delete(byte[] array, int item) {
        if (item < 0 || item >= array.length) {throw new ArrayIndexOutOfBoundsException("ArrayOperations: Cannot delete item: Index " + item + " out of bounds on array of length " + array.length);}
        byte[] ret = new byte[array.length-1];
        if (ret.length > 0) {
            System.arraycopy(array, 0, ret, 0, item);
            System.arraycopy(array, item+1, ret, item, ret.length-item);
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    @Immutable
    public static int[] delete(int[] array, int item) {
        if (item < 0 || item >= array.length) {throw new ArrayIndexOutOfBoundsException("ArrayOperations: Cannot delete item: Index " + item + " out of bounds on array of length " + array.length);}
        int[] ret = new int[array.length-1];
        if (ret.length > 0) {
            System.arraycopy(array, 0, ret, 0, item);
            System.arraycopy(array, item+1, ret, item, ret.length-item);
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    @Immutable
    public static float[] delete(float[] array, int item) {
        if (item < 0 || item >= array.length) {throw new ArrayIndexOutOfBoundsException("ArrayOperations: Cannot delete item: Index " + item + " out of bounds on array of length " + array.length);}
        float[] ret = new float[array.length-1];
        if (ret.length > 0) {
            System.arraycopy(array, 0, ret, 0, item);
            System.arraycopy(array, item+1, ret, item, ret.length-item);
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    @Immutable
    public static double[] delete(double[] array, int item) {
        if (item < 0 || item >= array.length) {throw new ArrayIndexOutOfBoundsException("ArrayOperations: Cannot delete item: Index " + item + " out of bounds on array of length " + array.length);}
        double[] ret = new double[array.length-1];
        if (ret.length > 0) {
            System.arraycopy(array, 0, ret, 0, item);
            System.arraycopy(array, item+1, ret, item, ret.length-item);
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    @Immutable
    public static char[] delete(char[] array, int item) {
        if (item < 0 || item >= array.length) {throw new ArrayIndexOutOfBoundsException("ArrayOperations: Cannot delete item: Index " + item + " out of bounds on array of length " + array.length);}
        char[] ret = new char[array.length-1];
        if (ret.length > 0) {
            System.arraycopy(array, 0, ret, 0, item);
            System.arraycopy(array, item+1, ret, item, ret.length-item);
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    @Immutable
    public static String[] delete(String[] array, int item) {
        if (item < 0 || item >= array.length) {throw new ArrayIndexOutOfBoundsException("ArrayOperations: Cannot delete item: Index " + item + " out of bounds on array of length " + array.length);}
        String[] ret = new String[array.length-1];
        if (ret.length > 0) {
            System.arraycopy(array, 0, ret, 0, item);
            System.arraycopy(array, item+1, ret, item, ret.length-item);
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified). Caution: Does not replicate objects!
     */
    @Immutable
    public static Object[] delete(Object[] array, int item) {
        if (item < 0 || item >= array.length) {throw new ArrayIndexOutOfBoundsException("ArrayOperations: Cannot delete item: Index " + item + " out of bounds on array of length " + array.length);}
        Object[] ret = new Object[array.length-1];
        if (ret.length > 0) {
            System.arraycopy(array, 0, ret, 0, item);
            System.arraycopy(array, item+1, ret, item, ret.length-item);
        }
        return ret;
    }
    
    /**
     *Prints the elements of the array to System.out, or the current OUT stream, one per line
     */
    @Immutable
    public static void print(byte[] array) {
        for (byte elem : array)
            OUT.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, or the current OUT stream, one per line
     */
    @Immutable
    public static void print(int... array) {
        for (int elem : array)
            OUT.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, or the current OUT stream, one per line
     */
    @Immutable
    public static void print(float... array) {
        for (float elem : array)
            OUT.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, or the current OUT stream, one per line
     */
    @Immutable
    public static void print(double... array) {
        for (double elem : array)
            OUT.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, or the current OUT stream, one per line
     */
    @Immutable
    public static void print(char... array) {
        for (char elem : array)
            OUT.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, or the current OUT stream, one per line
     */
    @Immutable
    public static void print(String... array) {
        for (String elem : array)
            OUT.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, or the current OUT stream, one per line, using objects' toString method
     */
    @Immutable
    public static void print(Object... array) {
        for (Object elem : array) {
            OUT.println(elem.toString());
        }
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream
     */
    @Immutable
    public static void printHorizontal(byte[] array) {
        OUT.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i]);
        }
        OUT.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream
     */
    @Immutable
    public static void printHorizontal(int... array) {
        OUT.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i]);
        }
        OUT.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream
     */
    @Immutable
    public static void printHorizontal(float... array) {
        OUT.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i]);
        }
        OUT.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream
     */
    @Immutable
    public static void printHorizontal(double... array) {
        OUT.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i]);
        }
        OUT.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream
     */
    @Immutable
    public static void printHorizontal(char... array) {
        OUT.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i]);
        }
        OUT.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream
     */
    @Immutable
    public static void printHorizontal(String... array) {
        OUT.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i]);
        }
        OUT.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream, using objects' toString method
     */
    @Immutable
    public static void printHorizontal(Object... array) {
        OUT.print(array[0].toString());
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i].toString());
        }
        OUT.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, or the current OUT stream
     */
    @Immutable
    public static void printHorizontal(boolean... array) {
        OUT.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            OUT.print(", " + array[i]);
        }
        OUT.println();
    }
    
    /**
     *@return true if all of the elements of the input array are true
     */
    @Immutable
    public static boolean tautology(boolean... array) {
        boolean ret = (array.length > 0);
        for (boolean ind : array) {
            if (!ind) {
                ret = false;
                break;
            }
        }
        return ret;
    }
    
    /**
     *@return true if all the elements of the input array are false, or if the array is empty
     */
    @Immutable
    public static boolean contradiction(boolean... array) {
        boolean ret = true;
        for (boolean ind : array) {
            if (ind) {
                ret = false;
                break;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(byte b, byte[] array) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array
     * @throws NotFoundException if b is not found in the array
     */
    @Immutable
    public static int findInSequence(byte b, byte[] array) throws NotFoundException {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(int b, int[] array) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array
     * @throws NotFoundException if b is not found in the array
     */
    @Immutable
    public static int findInSequence(int b, int[] array) throws NotFoundException {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(float b, float[] array) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array
     * @throws NotFoundException if b is not found in the array
     */
    @Immutable
    public static int findInSequence(float b, float[] array) throws NotFoundException {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(double b, double[] array) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array
     * @throws NotFoundException if b is not found in the array
     */
    @Immutable
    public static int findInSequence(double b, double[] array) throws NotFoundException {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(char b, char[] array) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array
     * @throws NotFoundException if b is not found in the array
     */
    @Immutable
    public static int findInSequence(char b, char[] array) throws NotFoundException {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(Object b, Object[] array) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(b)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array
     * @throws NotFoundException if b is not found in the array
     */
    @Immutable
    public static int findInSequence(Object b, Object[] array) throws NotFoundException {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }

    /**
     *@return the index of the first b in the array using the given matcher, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(Object b, Object[] array, ObjectMatcher matcher) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (matcher.matches(array[i], b)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array using the given matcher
     * @throws NotFoundException if b is not found in the array
     */
    @Immutable
    public static <T> int findInSequence(T b, T[] array, ObjectMatcher<T> matcher) throws NotFoundException {
        for (int i = 0; i < array.length; i++) {
            if (matcher.matches(array[i], b)) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(byte b, byte[] array, int from) {
        int ret = -1;
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array after and including the given starting index
     * @throws NotFoundException if b is not found from the starting position
     */
    @Immutable
    public static int findInSequence(byte b, byte[] array, int from) throws NotFoundException {
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(int b, int[] array, int from) {
        int ret = -1;
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array after and including the given starting index
     * @throws NotFoundException if b is not found from the starting position
     */
    @Immutable
    public static int findInSequence(int b, int[] array, int from) throws NotFoundException {
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(float b, float[] array, int from) {
        int ret = -1;
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array after and including the given starting index
     * @throws NotFoundException if b is not found from the starting position
     */
    @Immutable
    public static int findInSequence(float b, float[] array, int from) throws NotFoundException {
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(double b, double[] array, int from) {
        int ret = -1;
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array after and including the given starting index
     * @throws NotFoundException if b is not found from the starting position
     */
    @Immutable
    public static int findInSequence(double b, double[] array, int from) throws NotFoundException {
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(char b, char[] array, int from) {
        int ret = -1;
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array after and including the given starting index
     * @throws NotFoundException if b is not found from the starting position
     */
    @Immutable
    public static int findInSequence(char b, char[] array, int from) throws NotFoundException {
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(Object b, Object[] array, int from) {
        int ret = -1;
        for (int i = from; i < array.length; i++) {
            if (array[i].equals(b)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array after and including the given starting index
     * @throws NotFoundException if b is not found from the starting position
     */
    @Immutable
    public static int findInSequence(Object b, Object[] array, int from) throws NotFoundException {
        for (int i = from; i < array.length; i++) {
            if (array[i] == b) {
                return i;
            }
        }
        throw new NotFoundException();
    }

    /**
     *@return the index of the first b in the array, or -1 if not found
     *@deprecated the behavior of returning -1 when not found is considered clumsy and has been replaced with method findInSequence that throws a NotFoundException instead
     */
    @Deprecated
    public static int seqFind(Object b, Object[] array, int from, ObjectMatcher matcher) {
        int ret = -1;
        for (int i = 0; i < array.length; i++) {
            if (matcher.matches(array[i], b)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * @return the index of the first b in the array after and including the given starting index
     * @throws NotFoundException if b is not found from the starting position
     */
    @Immutable
    public static <T> int findInSequence(T b, T[] array, int from, ObjectMatcher<T> matcher) throws NotFoundException {
        for (int i = from; i < array.length; i++) {
            if (matcher.matches(array[i], b)) {
                return i;
            }
        }
        throw new NotFoundException();
    }
    
    /**
     *@return the element of array that is closest to the given value (the first one encountered if multiple elements are equally close)
     */
    @Immutable
    public static int findClosest(int[] array, int value) {
        int ret = array[0];
        long minDiff = Math.abs((long) value - array[0]);
        long diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs((long) value - array[i])) < minDiff) {
                ret = array[i];
                minDiff = diff;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of array that is closest to value (the first one encountered if multiple elements are equally close)
     */
    @Immutable
    public static int findClosestIndex(int[] array, int value) {
        int ret = 0;
        long minDiff = Math.abs((long) value - array[0]);
        long diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs((long) value - array[i])) < minDiff) {
                ret = i;
                minDiff = diff;
            }
        }
        return ret;
    }

    // TODO: findClosest(long)
    
    /**
     * Uses the BigDecimal class to obtain the difference between values (and as such is expected to incur a performance penalty, which is unmeasured, over
     * using straight floating point arithmetic). This prevents inaccuracies in cases where there would be overflows in the difference
     * between two values, but it does not compensate for the precision in the input (e.g. two values in the array intended to be different, but that are equal
     * in the floating point representation).
     * @return the element of array that is closest to the given value (the first one encountered if multiple elements are equally close, within double floating
     * point precision)
     */
    @Immutable
    public static double findClosest(double[] array, double value) {
        int retIndex = 0;
        BigDecimal auxValue = new BigDecimal(value);
        BigDecimal minDiff = auxValue.subtract(new BigDecimal(array[0])).abs();
        for (int i = 1; i < array.length; i++) {
            BigDecimal diff = auxValue.subtract(new BigDecimal(array[i])).abs();
            if (diff.compareTo(minDiff) < 0) {  // diff < minDiff
                retIndex = i;
                minDiff = diff;
            }
        }
        return array[retIndex];
    }
    
    /**
     * Uses the difference between values, and, if the difference is the same because of precision, direct comparisons for certain known cases.
     * @return the index of the element of array that is closest to value
     * @deprecated Known cases where overflow can cause the wrong result (good precision to about 15 significant digits)
     */
    @Deprecated
    public static int findClosestIndex(double[] array, double value) {
        int ret = 0;
        double minDiff = Math.abs(value - array[0]);
        double diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) < minDiff) {
                ret = i;
                minDiff = diff;
            }
            else if (diff == minDiff && array[i] != array[ret]) {  // This is for rare occasions where the differences are so large that the precision isn't enough to differentiate them
                if (((array[i] > array[ret]) && (array[i] < value && array[ret] < value))
                 || ((array[i] < array[ret]) && (array[i] > value && array[ret] > value))
                 || (array[i] < 0 && value < 0 && array[ret] > 0)
                 || (array[i] > 0 && value > 0 && array[ret] < 0))
                {
                    ret = i;
                }
            }
        }
        return ret;
    }
    
    /**
     * Works with current tests, but be wary of precision issues (good precision to about eight significant digits). Uses the difference between values, and, if the difference is the same because of precision, direct comparisons for certain known cases.
     * @return the element of array that is closest to value
     */
    @Immutable
    public static float findClosest(float[] array, float value) {
        float ret = array[0];
        double minDiff = Math.abs((double) value - array[0]);
        double diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs((double) value - array[i])) < minDiff) {
                ret = array[i];
                minDiff = diff;
            }
            else if (diff == minDiff && array[i] != ret) {  // This is for rare occasions where the differences are so large that the precision isn't enough to differentiate them
                if (((array[i] > ret) && (array[i] < value && ret < value))
                 || ((array[i] < ret) && (array[i] > value && ret > value))
                 || (array[i] < 0 && value < 0 && ret > 0)
                 || (array[i] > 0 && value > 0 && ret < 0))
                {
                    ret = array[i];
                }
            }
        }
        return ret;
    }
    
    /**
     * Works with current tests, but be wary of precision issues (good precision to about eight significant digits). Uses the difference between values, and, if the difference is the same because of precision, direct comparisons for certain known cases.
     * @return the index of the element of array that is closest to value
     */
    @Immutable
    public static int findClosestIndex(float[] array, float value) {
        int ret = 0;
        double minDiff = Math.abs((double) value - array[0]);
        double diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs((double) value - array[i])) < minDiff) {
                ret = i;
                minDiff = diff;
            }
            else if (diff == minDiff && array[i] != array[ret]) {  // This is for rare occasions where the differences are so large that the precision isn't enough to differentiate them
                if (((array[i] > array[ret]) && (array[i] < value && array[ret] < value))
                 || ((array[i] < array[ret]) && (array[i] > value && array[ret] > value))
                 || (array[i] < 0 && value < 0 && array[ret] > 0)
                 || (array[i] > 0 && value > 0 && array[ret] < 0))
                {
                    ret = i;
                }
            }
        }
        return ret;
    }
    
    /**
     * @return the element of array that is closest to value, using a lexicographic distance function that counts the number of characters that are different between the two strings (with left alignment)
     */
    @Immutable
    public static String findClosest(String[] array, String value) {
        return findClosest(array, value, DEFAULT_STRING_DISTFUNC);
    }

    /**
     * This method takes a custom distance function for a parameterized object class. There are a number of distance functions provided for strings in the programming5.strings package
     * @return the element of the array that is closest to the given object, using the given distance function
     */
    @Immutable
    public static <T> T findClosest(T[] array, T value, DistanceFunction<T> df) {
        T ret = array[0];
        double minDiff = df.distance(value, array[0]);
        double diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = df.distance(value, array[i])) < minDiff) {
                ret = array[i];
                minDiff = diff;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of array that is closest to value, using a lexicographic distance function that counts the number of characters that are different between the two strings (with left alignment)
     */
    @Immutable
    public static int findClosestIndex(String[] array, String value) {
        return findClosestIndex(array, value, DEFAULT_STRING_DISTFUNC);
    }
    
    /**
     * This method takes a custom distance function for a parameterized object class. There are a number of distance functions provided for strings in the programming5.strings package
     * @return the index of the element of array that is closest to the given object, using the given distance function
     */
    @Immutable
    public static <T> int findClosestIndex(T[] array, T value, DistanceFunction<T> df) {
        int ret = 0;
        double minDiff = df.distance(value, array[0]);
        double diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = df.distance(value, array[i])) < minDiff) {
                ret = i;
                minDiff = diff;
            }
        }
        return ret;
    }

    /**
     *@return the element of array that is closest to value, using lexicographic distance (difference in the char code values)
     */
    @Immutable
    public static char findClosest(char[] array, char value) {
        char ret = array[0];
        int minDiff = Math.abs(value - array[0]);
        int diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) < minDiff) {
                ret = array[i];
                minDiff = diff;
            }
        }
        return ret;
    }
    
    /**
     *@return the index of the element of array that is closest to value
     */
    @Immutable
    public static int findClosestIndex(char[] array, char value) {
        int ret = 0;
        int minDiff = Math.abs(value - array[0]);
        int diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) < minDiff) {
                ret = i;
                minDiff = diff;
            }
        }
        return ret;
    }
    
    /**
     * This method is faster in general than the corresponding findClosest method, because it uses binary search (the
     * java.util.Arrays implementation) for the sorted array. Note that this method is not functionally equivalent to
     * the binarySearch method, since the position returned by the latter would not be that of the closest element in
     * the case that the value is greater than it (and, indeed, it may not even be a valid position of the array if
     * the value is greater than the last element).
     * @return the element of a sorted array that is closest to the given value
     */
    @Immutable
    public static int findClosestInOrder(int[] array, int value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                long diffLeft = value - array[position-1];
                long diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return array[ret];
    }
    
    /**
     * This method is faster in general than the corresponding findClosestIndex method, because it uses binary search (the
     * java.util.Arrays implementation) for the sorted array. Note that this method is not functionally equivalent to
     * the binarySearch method, since the position returned by the latter would not be that of the closest element in
     * the case that the value is greater than it (and, indeed, it may not even be a valid position of the array if
     * the value is greater than the last element).
     * @return the index of the element of a sorted array that is closest to the given value
     */
    @Immutable
    public static int findClosestIndexInOrder(int[] array, int value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                long diffLeft = value - array[position-1];
                long diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array that is closest to value
     */
    @Immutable
    public static double findClosestInOrder(double[] array, double value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                double diffLeft = value - array[position-1];
                double diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return array[ret];
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    @Immutable
    public static int findClosestIndexInOrder(double[] array, double value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                double diffLeft = value - array[position-1];
                double diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array that is closest to value
     */
    @Immutable
    public static float findClosestInOrder(float[] array, float value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                float diffLeft = value - array[position-1];
                float diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return array[ret];
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    @Immutable
    public static int findClosestIndexInOrder(float[] array, float value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                float diffLeft = value - array[position-1];
                float diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array that is closest to value
     */
    @Immutable
    public static char findClosestInOrder(char[] array, char value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                int diffLeft = value - array[position-1];
                int diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return array[ret];
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    @Immutable
    public static int findClosestIndexInOrder(char[] array, char value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                int diffLeft = value - array[position-1];
                int diffRight = array[position] - value;
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return ret;
    }

    /**
     * @param array a an array sorted according to the given comparator
     * @param value the value to search
     * @param distanceFunction the distance function for the given type
     * @param comparator the comparator that defines the order for the given type
     * @return the element in the given sorted array (order determined by given comparator) that is closest (distance determined by given distance function) to the given value
     */
    @Immutable
    public static <T> T findClosestInOrder(T[] array, T value, DistanceFunction<T> distanceFunction, Comparator<T> comparator) {
        int ret = Arrays.binarySearch(array, value, comparator);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            }
            else {
                double diffLeft = distanceFunction.distance(value, array[position-1]);
                double diffRight = distanceFunction.distance(array[position], value);
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return array[ret];
    }
    
    /**
     * @param array an array of string sorted in lexicographic order
     * @return the element of a sorted array that is closest to value, according to lexicographic distance with different character counting
     * @see programming5.strings.LexicographicDistanceFunction
     */
    @Immutable
    public static String findClosestInOrder(String[] array, String value) {
        return findClosestInOrder(array, value, DEFAULT_STRING_DISTFUNC, DEFAULT_STRING_COMPARATOR);
    }

    /**
     * @param array a an array sorted according to the given comparator
     * @param value the value to search
     * @param distanceFunction the distance function for the given type
     * @param comparator the comparator that defines the order for the given type
     * @return the index of the element in the given sorted array (order determined by given comparator) that is closest (distance determined by given distance function) to the given value
     */
    @Immutable
    public static <T> int findClosestIndexInOrder(T[] array, T value, DistanceFunction<T> distanceFunction, Comparator<T> comparator) {
        int ret = Arrays.binarySearch(array, value, comparator);
        if (ret < 0) {
            int position = -ret - 1;
            if (position == 0) {
                ret = position;
            }
            else if (position == array.length) {
                ret = position - 1;
            } else {
                double diffLeft = distanceFunction.distance(value, array[position-1]);
                double diffRight = distanceFunction.distance(array[position], value);
                if (diffLeft <= diffRight) {
                    ret = position - 1;
                }
                else {
                    ret = position;
                }
            }
        }
        return ret;
    }

    /**
     * @param array an array of string sorted in lexicographic order
     * @return the index of the element of a sorted array that is closest to value, according to lexicographic distance with different character counting
     * @see programming5.strings.LexicographicDistanceFunction
     */
    @Immutable
    public static int findClosestIndexInOrder(String[] array, String value) {
        return findClosestIndexInOrder(array, value, DEFAULT_STRING_DISTFUNC, DEFAULT_STRING_COMPARATOR);
    }
    
    /**
     *@return the element of a sorted array of comparable elements that follows (or is equal to) the given value in order
     *(null if the value is larger than all values in the array)
     *@see java.lang.Comparable
     */
    @Immutable
    public static <T extends Comparable, S extends T> T findNextInOrder(T[] array, S value) {
        T ret;
        if (value.compareTo(array[0]) <= 0) {
            ret = array[0];
        } 
        else if (value.compareTo(array[array.length-1]) > 0) {
            ret = null;
        } 
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (value.compareTo(array[middle]) > 0) {
                    a = middle;
                } 
                else {
                    b = middle;
                }
            }
            ret = array[b];
        }
        return ret;
    }
    
    /**
     *@return the position in the sorted array of comparable elements of the element that follows (or is equal to) the given
     *value in order, which is the position where the value should be inserted in the array. The position is the length of the
     *array if the value is greater than all values in the array.
     *@see java.lang.Comparable
     */
    @Immutable
    public static <T extends Comparable, S extends T> int findPositionInOrder(T[] array, S value) {
        int ret;
        if (value.compareTo(array[0]) <= 0) {
            ret = 0;
        }
        else if (value.compareTo(array[array.length-1]) > 0) {
            ret = array.length;
        }
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (value.compareTo(array[middle]) > 0) {
                    a = middle;
                }
                else {
                    b = middle;
                }
            }
            ret = b;
        }
        return ret;
    }

    /**
     *@return the position in the sorted array of numeric elements of the element that follows (or is equal to) the given
     *value in order, which is the position where the value should be inserted in the array. The position is the length of the
     *array if the value is greater than all values in the array.
     */
    @Immutable
    public static int findPositionInOrder(int[] array, int value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            ret = -ret - 1;
        }
        return ret;
    }

    /**
     *@return the position in the sorted array of numeric elements of the element that follows (or is equal to) the given
     *value in order, which is the position where the value should be inserted in the array. The position is the length of the
     *array if the value is greater than all values in the array.
     */
    @Immutable
    public static int findPositionInOrder(float[] array, float value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            ret = -ret - 1;
        }
        return ret;
    }

    /**
     *@return the position in the sorted array of numeric elements of the element that follows (or is equal to) the given
     *value in order, which is the position where the value should be inserted in the array. The position is the length of the
     *array if the value is greater than all values in the array.
     */
    @Immutable
    public static int findPositionInOrder(double[] array, double value) {
        int ret = Arrays.binarySearch(array, value);
        if (ret < 0) {
            ret = -ret - 1;
        }
        return ret;
    }

    /**
     *@return the element of a sorted array that follows (or is equal to) the given value in order
     *(null if the value is larger than all values in the array), using the given comparator
     */
    @Immutable
    public static <T, S extends T, U extends T> S findNextInOrder(S[] array, U value, Comparator<T> comp) {
        int pos = findPositionInOrder(array, value, comp);
        if (pos < array.length) {
            return array[pos];
        }
        else {
            return null;
        }
    }
    
    /**
     *@return the position in the sorted array of the element that follows (or is equal to) the given
     *value in order, using the given comparator, which is the position where the value should be inserted in the array.
     *The position is the length of the array if the value is greater than all values in the array.
     */
    @Immutable
    public static <T, S extends T, U extends T> int findPositionInOrder(S[] array, U value, Comparator<T> comp) {
        int ret = Arrays.binarySearch(array, value, comp);
        if (ret < 0) {
            ret = -ret - 1;
        }
        return ret;
    }

    /**
     * @return lambda function that divides the values given to it by the given norm
     */
    public static IntToDoubleFunction normalizer(double norm) {
        return (value) -> {return (double) value / norm;};
    }

    /**
     *@return new array with normalized values of array with respect to the max value of array
     */
    @Immutable
    public static double[] normalizeMax(int[] array) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        double maxval = (double)(max(array));
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/maxval;
        }
        return ret;
    }

    /**
     *@return new array with normalized values of array with respect to the sum of the values of array
     */
    @Immutable
    public static double[] normalizeSum(int[] array) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        double arraysum = (double)(sum(array));
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/arraysum;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the value given
     */
    @Immutable
    public static double[] normalizeVal(int[] array, double maxval) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/maxval;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the max value of array
     */
    @Immutable
    public static double[] normalizeMax(float[] array) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        double maxval = (double)(max(array));
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/maxval;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the sum of the values of array
     */
    @Immutable
    public static double[] normalizeSum(float[] array) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        double arraysum = (double)(sum(array));
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/arraysum;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the value given
     */
    @Immutable
    public static double[] normalizeVal(float[] array, double maxval) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/maxval;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the max value of array
     */
    @Immutable
    public static double[] normalizeMax(double[] array) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        double maxval = max(array);
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i]/maxval;
        }
        return ret;
    }

    /**
     *Normalizes values of array with respect to the max value of the array
     */
    public static void normalizeMaxInPlace(double[] array) {
        if (array != null) {
            double maxval = max(array);
            for (int i = 0; i < array.length; i++) {
                array[i] /= maxval;
            }
        }
    }

    /**
     *@return new array with normalized values of array with respect to the sum of the values of array
     */
    @Immutable
    public static double[] normalizeSum(double[] array) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        double arraysum = sum(array);
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i]/arraysum;
        }
        return ret;
    }

    /**
     *Normalizes values of array with respect to the sum of the values of the array
     */
    public static void normalizeSumInPlace(double[] array) {
        if (array != null) {
            double sum = sum(array);
            for (int i = 0; i < array.length; i++) {
                array[i] /= sum;
            }
        }
    }

    /**
     *@return new array with normalized values of array with respect to the value given
     */
    @Immutable
    public static double[] normalizeVal(double[] array, double maxval) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i]/maxval;
        }
        return ret;
    }

    /**
     *Normalizes values of array with respect to the given value
     */
    public static void normalizeValInPlace(double[] array, double value) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                array[i] /= value;
            }
        }
    }

    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    @Immutable
    public static int[] reverse(int[] array) {
        if (array == null) return null;
        int[] ret = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }

    /**
     * Reverses the elements of the given array
     */
    public static void reverseInPlace(int[] array) {
        if (array != null) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i++, j--);
            }
        }
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    @Immutable
    public static float[] reverse(float[] array) {
        if (array == null) return null;
        float[] ret = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }

    /**
     * Reverses the elements of the given array
     */
    public static void reverseInPlace(float[] array) {
        if (array != null) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i++, j--);
            }
        }
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    @Immutable
    public static double[] reverse(double[] array) {
        if (array == null) return null;
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }

    /**
     * Reverses the elements of the given array
     */
    public static void reverseInPlace(double[] array) {
        if (array != null) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i++, j--);
            }
        }
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    @Immutable
    public static char[] reverse(char[] array) {
        if (array == null) return null;
        char[] ret = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }

    /**
     * Reverses the elements of the given array
     */
    public static void reverseInPlace(char[] array) {
        if (array != null) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i++, j--);
            }
        }
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    @Immutable
    public static String[] reverse(String[] array) {
        if (array == null) return null;
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }

    /**
     * Reverses the elements of the given array
     */
    public static void reverseInPlace(String[] array) {
        if (array != null) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i++, j--);
            }
        }
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    @Immutable
    public static boolean[] reverse(boolean[] array) {
        if (array == null) return null;
        boolean[] ret = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }

    /**
     * Reverses the elements of the given array
     */
    public static void reverseInPlace(boolean[] array) {
        if (array != null) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i++, j--);
            }
        }
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    @Immutable
    public static Object[] reverse(Object[] array) {
        Object[] ret = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }

    /**
     * Reverses the elements of the given array
     */
    public static void reverseInPlace(Object[] array) {
        if (array != null) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i++, j--);
            }
        }
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static int[] insert(int value, int[] array, int pos) throws ArrayIndexOutOfBoundsException {
        int[] ret = new int[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = array[i];
        }
        ret[pos] = value;
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static int[] addElement(int elem, int[] array) {
        int[] ret = null;
        if (array == null) {
            array = new int[1];
            array[0] = elem;
            ret = array;
        } 
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    @Immutable
    public static int[] insertSorted(int value, int[] array) {
        int pos = findClosestIndexInOrder(array, value);
        int[] ret = null;
        if (array[pos] >= value) {
            ret = insert(value, array, pos);
        } 
        else {
            ret = insert(value, array, pos+1);
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static byte[] insert(byte value, byte[] array, int pos) throws ArrayIndexOutOfBoundsException {
        byte[] ret = new byte[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = array[i];
        }
        ret[pos] = value;
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = array[i];
        }
        return ret;
    }

    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static byte[] addElement(byte elem, byte[] array) {
        byte[] ret = null;
        if (array == null) {
            array = new byte[1];
            array[0] = elem;
            ret = array;
        }
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }

    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static float[] insert(float value, float[] array, int pos) throws ArrayIndexOutOfBoundsException {
        float[] ret = new float[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = array[i];
        }
        ret[pos] = value;
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static float[] addElement(float elem, float[] array) {
        float[] ret = null;
        if (array == null) {
            array = new float[1];
            array[0] = elem;
            ret = array;
        } 
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    @Immutable
    public static float[] insertSorted(float value, float[] array) {
        int pos = findClosestIndexInOrder(array, value);
        float[] ret = null;
        if (array[pos] >= value) {
            ret = insert(value, array, pos);
        } 
        else {
            ret = insert(value, array, pos+1);
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static double[] insert(double value, double[] array, int pos) throws ArrayIndexOutOfBoundsException {
        double[] ret = new double[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = array[i];
        }
        ret[pos] = value;
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static double[] addElement(double elem, double[] array) {
        double[] ret = null;
        if (array == null) {
            array = new double[1];
            array[0] = elem;
            ret = array;
        } 
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    @Immutable
    public static double[] insertSorted(double value, double[] array) {
        int pos = findClosestIndexInOrder(array, value);
        double[] ret = null;
        if (array[pos] >= value) {
            ret = insert(value, array, pos);
        } 
        else {
            ret = insert(value, array, pos+1);
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static char[] insert(char value, char[] array, int pos) throws ArrayIndexOutOfBoundsException {
        char[] ret = new char[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = array[i];
        }
        ret[pos] = value;
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static char[] addElement(char elem, char[] array) {
        char[] ret = null;
        if (array == null) {
            array = new char[1];
            array[0] = elem;
            ret = array;
        } 
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    @Immutable
    public static char[] insertSorted(char value, char[] array) {
        int pos = findClosestIndexInOrder(array, value);
        char[] ret = null;
        if (array[pos] >= value) {
            ret = insert(value, array, pos);
        } 
        else {
            ret = insert(value, array, pos+1);
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static String[] insert(String value, String[] array, int pos) throws ArrayIndexOutOfBoundsException {
        String[] ret = new String[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = new String(array[i]);
        }
        ret[pos] = new String(value);
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = new String(array[i]);
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static String[] addElement(String elem, String[] array) {
        String[] ret = null;
        if (array == null) {
            array = new String[1];
            array[0] = new String(elem);
            ret = array;
        } 
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    @Immutable
    public static String[] insertSorted(String value, String[] array) {
        int pos = findClosestIndexInOrder(array, value);
        String[] ret = null;
        if (array[pos].compareTo(value) >= 0) {
            ret = insert(value, array, pos);
        } 
        else {
            ret = insert(value, array, pos+1);
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static Object[] insert(Object value, Object[] array, int pos) throws ArrayIndexOutOfBoundsException {
        Object[] ret = new Object[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = array[i];
        }
        ret[pos] = value;
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static Object[] addElement(Object elem, Object[] array) {
        Object[] ret = null;
        if (array == null) {
            array = new Object[1];
            array[0] = elem;
            ret = array;
        } 
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    @Immutable
    public static <T extends Comparable, S extends T> T[] insertSorted(S value, T[] array) {
        int pos = findPositionInOrder(array, value);
        T[] ret = null;
        ret = (T[])insert(value, array, pos);
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    @Immutable
    public static <T, S extends T> T[] insertSorted(S value, T[] array, Comparator<T> comp) {
        int pos = findPositionInOrder(array, value, comp);
        T[] ret = null;
        ret = (T[])insert(value, array, pos);
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    @Immutable
    public static boolean[] insert(boolean value, boolean[] array, int pos) throws ArrayIndexOutOfBoundsException {
        boolean[] ret = new boolean[array.length+1];
        for (int i = 0; i < pos; i++) {
            ret[i] = array[i];
        }
        ret[pos] = value;
        for (int i = pos; i < array.length; i++) {
            ret[i+1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array at the end
     */
    @Immutable
    public static boolean[] addElement(boolean elem, boolean[] array) {
        boolean[] ret = null;
        if (array == null) {
            array = new boolean[1];
            array[0] = elem;
            ret = array;
        } 
        else {
            ret = insert(elem, array, array.length);
        }
        return ret;
    }
    
    /**
     *@return a new array that has elements that go from 0 to numElements-1
     */
    public static int[] generateEnumeration(int numElements) {
        int[] ret = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            ret[i] = i;
        }
        return ret;
    }

    /**
     * @param start first element in the resulting enumeration
     * @param end last element in the resulting enumeration
     * @return a new array of numElements, from start to end inclusive
     */
    public static int[] generateEnumeration(int start, int end) {
        int numElements = Math.abs(end - start) + 1;
        int step = (end - start) / Math.abs(end - start);
        return generateEnumeration(start, numElements, step);
    }

    /**
     *@return a new array of numElements, from start and increasing by step
     */
    public static int[] generateEnumeration(int start, int numElements, int step) {
        int[] ret = new int[numElements];
        ret[0] = start;
        for (int i = 1; i < numElements; i++) {
            ret[i] = ret[i-1] + step;
        }
        return ret;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     *@deprecated same functionality as fill method in java.util.Arrays class
     */
    @Deprecated
    public static int[] initialize(int[] array, int initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }

    /**
     * Creates a new array initialized with the given value
     * @param size the size of the array
     * @param initValue the value to fill the array with
     * @return a new array of the given size with every position filled by the given value
     */
    public static int[] newIntArray(int size, int initValue) {
        int[] ret = new int[size];
        Arrays.fill(ret, initValue);
        return ret;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     *@deprecated same functionality as fill method in java.util.Arrays class
     */
    @Deprecated
    public static float[] initialize(float[] array, float initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }

    /**
     * Creates a new array initialized with the given value
     * @param size the size of the array
     * @param initValue the value to fill the array with
     * @return a new array of the given size with every position filled by the given value
     */
    public static float[] newFloatArray(int size, float initValue) {
        float[] ret = new float[size];
        Arrays.fill(ret, initValue);
        return ret;
    }

    /**
     *@return the given array with all values set to the given initValue
     *@deprecated same functionality as fill method in java.util.Arrays class
     */
    @Deprecated
    public static double[] initialize(double[] array, double initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }

    /**
     * Creates a new array initialized with the given value
     * @param size the size of the array
     * @param initValue the value to fill the array with
     * @return a new array of the given size with every position filled by the given value
     */
    public static double[] newDoubleArray(int size, double initValue) {
        double[] ret = new double[size];
        Arrays.fill(ret, initValue);
        return ret;
    }

    /**
     *@return the given array with all values set to the given initValue
     *@deprecated same functionality as fill method in java.util.Arrays class
     */
    @Deprecated
    public static char[] initialize(char[] array, char initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     *@deprecated same functionality as fill method in java.util.Arrays class
     */
    @Deprecated
    public static boolean[] initialize(boolean[] array, boolean initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }

    /**
     * Creates a new array initialized with the given value
     * @param size the size of the array
     * @param initValue the value to fill the array with
     * @return a new array of the given size with every position filled by the given value
     */
    public static boolean[] newBooleanArray(int size, boolean initValue) {
        boolean[] ret = new boolean[size];
        Arrays.fill(ret, initValue);
        return ret;
    }

    /**
     *@return the given array with all values set to the given initValue
     *@deprecated same functionality as fill method in java.util.Arrays class
     */
    @Deprecated
    public static String[] initialize(String[] array, String initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }

    /**
     * @return the given matrix with the values set to the given initValue
     */
    public static int[][] initialize(int[][] matrix, int initValue) {
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], initValue);
        }
        return matrix;
    }
    
    /**
     *Casts the elements of the origin array to the type of the destination array and copies each of these elements to
     *the new array, which must be pre-allocated with the correct size.
     *@throws java.lang.IllegalArgumentException if the arrays are of different sizes
     *@throws java.lang.ClassCastException if the elements of the origin array cannot be cast to the destination type
     */
    @Immutable
    public static <T> void arrayCast(T[] destination, Object[] origin) {
        if (destination.length == origin.length) {
            for (int i = 0; i < destination.length; i++) {
                destination[i] = (T) origin[i];
            }
        }
        else {
            throw new IllegalArgumentException("ArrayOperations: arrayCast: Arrays must be of the same length");
        }
    }

    /**
     * Turns an integer array into a string array using the Integer toString method.
     * @param array the array to transform
     * @return an array of String representing each of the elements of the given array
     */
    @Immutable
    public static String[] toString(int[] array) {
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Integer.toString(array[i]);
        }
        return ret;
    }

    /**
     * Turns an array of double into a string array using the Double toString method.
     * @param array the array to transform
     * @return an array of String representing each of the elements of the given array
     */
    @Immutable
    public static String[] toString(double[] array) {
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Double.toString(array[i]);
        }
        return ret;
    }

    /**
     * Turns an array of long into a string array using the Long toString method.
     * @param array the array to transform
     * @return an array of String representing each of the elements of the given array
     */
    @Immutable
    public static String[] toString(long[] array) {
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Long.toString(array[i]);
        }
        return ret;
    }

    /**
     * Turns an array of float into a string array using the Float toString method.
     * @param array the array to transform
     * @return an array of String representing each of the elements of the given array
     */
    @Immutable
    public static String[] toString(float[] array) {
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Float.toString(array[i]);
        }
        return ret;
    }

    /**
     * Turns a boolean array into a string array using the Boolean toString method.
     * @param array the array to transform
     * @return an array of String representing each of the elements of the given array
     */
    @Immutable
    public static String[] toString(boolean[] array) {
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Boolean.toString(array[i]);
        }
        return ret;
    }

    /**
     * Parses each of the elements of the string array as integers and returns a new array of ints with the
     * corresponding values.
     * @param array the array to transform
     * @return an array of int with the value that each of the elements of the given array represents
     */
    @Immutable
    public static int[] toIntArray(String[] array) {
        int[] ret = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Integer.parseInt(array[i]);
        }
        return ret;
    }

    /**
     * Parses each of the elements of the string array as double and returns a new array of double with the
     * corresponding values.
     * @param array the array to transform
     * @return an array of double with the value that each of the elements of the given array represents
     */
    @Immutable
    public static double[] toDoubleArray(String[] array) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Double.parseDouble(array[i]);
        }
        return ret;
    }

    /**
     * Parses each of the elements of the string array as long and returns a new array of long with the
     * corresponding values.
     * @param array the array to transform
     * @return an array of long with the value that each of the elements of the given array represents
     */
    @Immutable
    public static long[] toLongArray(String[] array) {
        long[] ret = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Long.parseLong(array[i]);
        }
        return ret;
    }

    /**
     * Parses each of the elements of the string array as float and returns a new array of float with the
     * corresponding values.
     * @param array the array to transform
     * @return an array of float with the value that each of the elements of the given array represents
     */
    @Immutable
    public static float[] toFloatArray(String[] array) {
        float[] ret = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Float.parseFloat(array[i]);
        }
        return ret;
    }

    /**
     * Parses each of the elements of the string array as boolean and returns a new array of boolean with the
     * corresponding values.
     * @param array the array to transform
     * @return an array of boolean with the value that each of the elements of the given array represents
     */
    @Immutable
    public static boolean[] toBooleanArray(String[] array) {
        boolean[] ret = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = Boolean.parseBoolean(array[i]);
        }
        return ret;
    }

    /**
     * Element by element comparison of two arrays
     * @return true if the input arrays are of equal length and all elements in corresponding positions are equal; false otherwise
     */
    @Immutable
    public static boolean areEqual(int[] array1, int[] array2) {
        boolean ret = (array1.length == array2.length);
        if (ret) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Element by element comparison of two arrays
     * @return true if the input arrays are of equal length and all elements in corresponding positions are equal; false otherwise
     */
    @Immutable
    public static boolean areEqual(long[] array1, long[] array2) {
        boolean ret = (array1.length == array2.length);
        if (ret) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Element by element comparison of two arrays
     * @return true if the input arrays are of equal length and all elements in corresponding positions are equal; false otherwise
     */
    @Immutable
    public static boolean areEqual(float[] array1, float[] array2) {
        boolean ret = (array1.length == array2.length);
        if (ret) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Element by element comparison of two arrays
     * @return true if the input arrays are of equal length and all elements in corresponding positions are equal; false otherwise
     */
    @Immutable
    public static boolean areEqual(double[] array1, double[] array2) {
        boolean ret = (array1.length == array2.length);
        if (ret) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Element by element comparison of two arrays
     * @return true if the input arrays are of equal length and all elements in corresponding positions are equal; false otherwise
     */
    @Immutable
    public static boolean areEqual(byte[] array1, byte[] array2) {
        boolean ret = (array1.length == array2.length);
        if (ret) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Element by element comparison of two arrays
     * @return true if the input arrays are of equal length and all elements in corresponding positions are equal; false otherwise
     */
    @Immutable
    public static boolean areEqual(char[] array1, char[] array2) {
        boolean ret = (array1.length == array2.length);
        if (ret) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Element by element comparison of two arrays
     * @return true if the input arrays are of equal length and all elements in corresponding positions are equal; false otherwise
     */
    @Immutable
    public static boolean areEqual(Object[] array1, Object[] array2) {
        boolean ret = (array1.length == array2.length);
        if (ret) {
            for (int i = 0; i < array1.length; i++) {
                if (!array1[i].equals(array2[i])) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * @param array an array reference
     * @return true if the given reference is null or an array of length zero; false otherwise
     */
    @Immutable
    public static <T> boolean isEmpty(T[] array) {
        int length = (array == null) ? 0 : array.length;
        return (length == 0);
    }

    /**
     * Transforms a primitive type array into an array of the corresponding object type
     * @param array the primitive array
     * @return the object type array
     */
    @Immutable
    public static Integer[] box(int[] array) {
        Integer[] ret = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a primitive type array into an array of the corresponding object type
     * @param array the primitive array
     * @return the object type array
     */
    @Immutable
    public static Long[] box(long[] array) {
        Long[] ret = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a primitive type array into an array of the corresponding object type
     * @param array the primitive array
     * @return the object type array
     */
    @Immutable
    public static Float[] box(float[] array) {
        Float[] ret = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a primitive type array into an array of the corresponding object type
     * @param array the primitive array
     * @return the object type array
     */
    @Immutable
    public static Double[] box(double[] array) {
        Double[] ret = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a primitive type array into an array of the corresponding object type
     * @param array the primitive array
     * @return the object type array
     */
    @Immutable
    public static Boolean[] box(boolean[] array) {
        Boolean[] ret = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a numeric object type array into an array of the corresponding primitive type
     * @param array the object type array
     * @return the primitive array
     */
    @Immutable
    public static int[] unbox(Integer[] array) {
        int[] ret = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a numeric object type array into an array of the corresponding primitive type
     * @param array the object type array
     * @return the primitive array
     */
    @Immutable
    public static long[] unbox(Long[] array) {
        long[] ret = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a numeric object type array into an array of the corresponding primitive type
     * @param array the object type array
     * @return the primitive array
     */
    @Immutable
    public static float[] unbox(Float[] array) {
        float[] ret = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a numeric object type array into an array of the corresponding primitive type
     * @param array the object type array
     * @return the primitive array
     */
    @Immutable
    public static double[] unbox(Double[] array) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * Transforms a numeric object type array into an array of the corresponding primitive type
     * @param array the object type array
     * @return the primitive array
     */
    @Immutable
    public static boolean[] unbox(Boolean[] array) {
        boolean[] ret = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    /**
     * @return the number of true elements in the given array
     */
    @Immutable
    public static int countTrue(boolean[] array) {
        int ret = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * Equivalent to {@link #generateEnumeration(int, int, int)}, invoked with generateEnumeration(start, size, step)
     */
    public static int[] createArithSeries(int start, int step, int size) {
        int[] ret = new int[size];
        int value = start;
        for (int i = 0; i < size; i++) {
            ret[i] = value;
            value += step;
        }
        return ret;
    }

    /**
     * Equivalent to {@link #generateEnumeration(int)}
     */
    public static int[] createEnumeration(int size) {
        return createArithSeries(0, 1, size);
    }

    /**
     * Finds the first index (i.e. index of the start of a sequence of the given element) of the given element in the
     * given array, which is expected to be sorted
     * @param array a sorted array
     * @param element the element to search for in the array
     * @return the index of the first occurrence of the element in the array, or -1 if not found
     */
    public static int findFirstIndexInOrder(int[] array, int element) {
        int pos = Arrays.binarySearch(array, element);
        if (pos > 0 && array[0] < element) {
            if (array[pos-1] == array[pos]) {
                int a = 0;
                int b = pos;
                while (b - a > 1) {
                    pos = (a+b) / 2;
                    if (array[pos] < element) {
                        a = pos;
                    }
                    else {
                        b = pos;
                    }
                }
                pos = b;
            }
        }
        else {
            pos = 0;
        }
        return pos;
    }

    /**
     * Finds the first index (i.e. index of the start of a sequence of the given element) of the given element in the
     * given array, which is expected to be sorted
     * @param array a sorted array
     * @param element the element to search for in the array
     * @return the index of the first occurrence of the element in the array, or -1 if not found
     */
    public static int findFirstIndexInOrder(float[] array, float element) {
        int pos = Arrays.binarySearch(array, element);
        if (pos > 0 && array[0] < element) {
            if (array[pos-1] == array[pos]) {
                int a = 0;
                int b = pos;
                while (b - a > 1) {
                    pos = (a+b) / 2;
                    if (array[pos] < element) {
                        a = pos;
                    }
                    else {
                        b = pos;
                    }
                }
                pos = b;
            }
        }
        else {
            pos = 0;
        }
        return pos;
    }

    /**
     * Finds the first index (i.e. index of the start of a sequence of the given element) of the given element in the
     * given array, which is expected to be sorted
     * @param array a sorted array
     * @param element the element to search for in the array
     * @return the index of the first occurrence of the element in the array, or -1 if not found
     */
    public static int findFirstIndexInOrder(long[] array, long element) {
        int pos = Arrays.binarySearch(array, element);
        if (pos > 0 && array[0] < element) {
            if (array[pos-1] == array[pos]) {
                int a = 0;
                int b = pos;
                while (b - a > 1) {
                    pos = (a+b) / 2;
                    if (array[pos] < element) {
                        a = pos;
                    }
                    else {
                        b = pos;
                    }
                }
                pos = b;
            }
        }
        else {
            pos = 0;
        }
        return pos;
    }

    /**
     * Finds the first index (i.e. index of the start of a sequence of the given element) of the given element in the
     * given array, which is expected to be sorted
     * @param array a sorted array
     * @param element the element to search for in the array
     * @return the index of the first occurrence of the element in the array, or -1 if not found
     */
    public static int findFirstIndexInOrder(double[] array, double element) {
        int pos = Arrays.binarySearch(array, element);
        if (pos > 0 && array[0] < element) {
            if (array[pos-1] == array[pos]) {
                int a = 0;
                int b = pos;
                while (b - a > 1) {
                    pos = (a+b) / 2;
                    if (array[pos] < element) {
                        a = pos;
                    }
                    else {
                        b = pos;
                    }
                }
                pos = b;
            }
        }
        else {
            pos = 0;
        }
        return pos;
    }

    /**
     * Finds the first index (i.e. index of the start of a sequence of the given element) of the given element in the
     * given array, which is expected to be sorted according to the Comparable interface
     * @param array a sorted array
     * @param element the element to search for in the array
     * @return the index of the first occurrence of the element in the array, or -1 if not found
     */
    public static int findFirstIndexInOrder(Comparable[] array, Comparable element) {
        int pos = Arrays.binarySearch(array, element);
        if (pos > 0 && (array[0].compareTo(element) < 0)) {
            if (array[pos-1].compareTo(array[pos]) == 0) {
                int a = 0;
                int b = pos;
                while (b - a > 1) {
                    pos = (a+b) / 2;
                    if (array[pos].compareTo(element) < 0) {
                        a = pos;
                    }
                    else {
                        b = pos;
                    }
                }
                pos = b;
            }
        }
        else {
            pos = 0;
        }
        return pos;
    }

    /**
     * Finds the first index (i.e. index of the start of a sequence of the given element) of the given element in the
     * given array, which is expected to be sorted in an order given by the comparator
     * @param array a sorted array
     * @param element the element to search for in the array
     * @param comp the comparator that determines the order of elements in the array
     * @return the index of the first occurrence of the element in the array, or -1 if not found
     */
    public static <T, U extends T> int findFirstIndexInOrder(T[] array, U element, Comparator<T> comp) {
        int pos = Arrays.binarySearch(array, element);
        if (pos > 0 && (comp.compare(array[0], element) < 0)) {
            if (comp.compare(array[pos-1], array[pos]) == 0) {
                int a = 0;
                int b = pos;
                while (b - a > 1) {
                    pos = (a+b) / 2;
                    if (comp.compare(array[pos], element) < 0) {
                        a = pos;
                    }
                    else {
                        b = pos;
                    }
                }
                pos = b;
            }
        }
        else {
            pos = 0;
        }
        return pos;
    }

    /**
     * Sorts the array with {@link Arrays#sort}, but also returns the unsorted order of the array elements in order to
     * be able to reconstruct the original array. This method allocates an extra array of the same size of the original
     * array to find the unsorted order.
     * @param array the array to be sorted
     * @return the unsorted order of the original array elements, so that for int[] order = sort(array), array[order[i]]
     * returns the original element in position i of the array before sorting; for example, the unsorted order of
     * [3, 1, 2] is [2, 0, 1].
     * @deprecated the semantics of the return array of this method have changed, as it used to return the sorted order
     * of the original array instead of the unsorted order of the sorted array (the equivalent return value is now
     * given by {@link #sortedOrder(int[])})
     */
    public static int[] sort(int[] array) {
        int[] unsortedOrder = new int[array.length];
        int[] original = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(array);
        for (int i = 0; i < original.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(array, original[i]);
            unsortedOrder[i] = repetitions[firstIndex] + firstIndex;
            repetitions[firstIndex]++;
        }
        return unsortedOrder;
    }

    /**
     * Sorts the array with {@link Arrays#sort}, but also returns the unsorted order of the array elements in order to
     * be able to reconstruct the original array. This method allocates an extra array of the same size of the original
     * array to find the unsorted order.
     * @param array the array to be sorted
     * @return the unsorted order of the original array elements, so that for int[] order = sort(array), array[order[i]]
     * returns the original element in position i of the array before sorting; for example, the unsorted order of
     * [3, 1, 2] is [2, 0, 1].
     * @deprecated the semantics of the return array of this method have changed, as it used to return the sorted order
     * of the original array instead of the unsorted order of the sorted array (the equivalent return value is now
     * given by {@link #sortedOrder(int[])})
     */
    public static int[] sort(float[] array) {
        int[] unsortedOrder = new int[array.length];
        float[] original = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(array);
        for (int i = 0; i < original.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(array, original[i]);
            unsortedOrder[i] = repetitions[firstIndex] + firstIndex;
            repetitions[firstIndex]++;
        }
        return unsortedOrder;
    }

    /**
     * Sorts the array with {@link Arrays#sort}, but also returns the unsorted order of the array elements in order to
     * be able to reconstruct the original array. This method allocates an extra array of the same size of the original
     * array to find the unsorted order.
     * @param array the array to be sorted
     * @return the unsorted order of the original array elements, so that for int[] order = sort(array), array[order[i]]
     * returns the original element in position i of the array before sorting; for example, the unsorted order of
     * [3, 1, 2] is [2, 0, 1].
     * @deprecated the semantics of the return array of this method have changed, as it used to return the sorted order
     * of the original array instead of the unsorted order of the sorted array (the equivalent return value is now
     * given by {@link #sortedOrder(int[])})
     */
    public static int[] sort(long[] array) {
        int[] unsortedOrder = new int[array.length];
        long[] original = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(array);
        for (int i = 0; i < original.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(array, original[i]);
            unsortedOrder[i] = repetitions[firstIndex] + firstIndex;
            repetitions[firstIndex]++;
        }
        return unsortedOrder;
    }

    /**
     * Sorts the array with {@link Arrays#sort}, but also returns the unsorted order of the array elements in order to
     * be able to reconstruct the original array. This method allocates an extra array of the same size of the original
     * array to find the unsorted order.
     * @param array the array to be sorted
     * @return the unsorted order of the original array elements, so that for int[] order = sort(array), array[order[i]]
     * returns the original element in position i of the array before sorting; for example, the unsorted order of
     * [3, 1, 2] is [2, 0, 1].
     * @deprecated the semantics of the return array of this method have changed, as it used to return the sorted order
     * of the original array instead of the unsorted order of the sorted array (the equivalent return value is now
     * given by {@link #sortedOrder(int[])})
     */
    public static int[] sort(double[] array) {
        int[] unsortedOrder = new int[array.length];
        double[] original = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(array);
        for (int i = 0; i < original.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(array, original[i]);
            unsortedOrder[i] = repetitions[firstIndex] + firstIndex;
            repetitions[firstIndex]++;
        }
        return unsortedOrder;
    }

    /**
     * Sorts the array with {@link Arrays#sort}, but also returns the unsorted order of the array elements in order to
     * be able to reconstruct the original array. This method allocates an extra array of the same size of the original
     * array to find the unsorted order.
     * @param array the array to be sorted
     * @return the unsorted order of the original array elements, so that for int[] order = sort(array), array[order[i]]
     * returns the original element in position i of the array before sorting; for example, the unsorted order of
     * [3, 1, 2] is [2, 0, 1].
     * @deprecated the semantics of the return array of this method have changed, as it used to return the sorted order
     * of the original array instead of the unsorted order of the sorted array (the equivalent return value is now
     * given by {@link #sortedOrder(int[])})
     */
    public static int[] sort(Comparable[] array) {
        int[] unsortedOrder = new int[array.length];
        Comparable[] original = new Comparable[array.length];
        ArrayOperations.replicate(array, original);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(array);
        for (int i = 0; i < original.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(array, original[i]);
            unsortedOrder[i] = repetitions[firstIndex] + firstIndex;
            repetitions[firstIndex]++;
        }
        return unsortedOrder;
    }

    /**
     * Returns the sorted order of the elements in the given array, so that array[so[i]] >= array[so[i+1]] for 0 <= i < array.length,
     * while the original array remains unmodified. For example, the sorted order of [3, 1, 2] is [1, 2, 0]. Note that
     * this method creates a copy of the original array and sorts it with {@link Arrays#sort(int[])}.
     * @param array the array to sort
     * @return the sorted order of the array elements
     */
    @Immutable
    public static int[] sortedOrder(int[] array) {
        int[] sortedOrder = new int[array.length];
        int[] sorted = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(sorted);
        for (int i = 0; i < array.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(sorted, array[i]);
            sortedOrder[repetitions[firstIndex] + firstIndex] = i;
            repetitions[firstIndex]++;
        }
        return sortedOrder;
    }

    /**
     * Returns the sorted order of the elements in the given array, so that array[so[i]] >= array[so[i+1]] for 0 <= i < array.length,
     * while the original array remains unmodified. For example, the sorted order of [3, 1, 2] is [1, 2, 0]. Note that
     * this method creates a copy of the original array and sorts it with {@link Arrays#sort(float[])}.
     * @param array the array to sort
     * @return the sorted order of the array elements
     */
    @Immutable
    public static int[] sortedOrder(float[] array) {
        int[] sortedOrder = new int[array.length];
        float[] sorted = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(sorted);
        for (int i = 0; i < array.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(sorted, array[i]);
            sortedOrder[repetitions[firstIndex] + firstIndex] = i;
            repetitions[firstIndex]++;
        }
        return sortedOrder;
    }

    /**
     * Returns the sorted order of the elements in the given array, so that array[so[i]] >= array[so[i+1]] for 0 <= i < array.length,
     * while the original array remains unmodified. For example, the sorted order of [3, 1, 2] is [1, 2, 0]. Note that
     * this method creates a copy of the original array and sorts it with {@link Arrays#sort(long[])}.
     * @param array the array to sort
     * @return the sorted order of the array elements
     */
    @Immutable
    public static int[] sortedOrder(long[] array) {
        int[] sortedOrder = new int[array.length];
        long[] sorted = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(sorted);
        for (int i = 0; i < array.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(sorted, array[i]);
            sortedOrder[repetitions[firstIndex] + firstIndex] = i;
            repetitions[firstIndex]++;
        }
        return sortedOrder;
    }

    /**
     * Returns the sorted order of the elements in the given array, so that array[so[i]] >= array[so[i+1]] for 0 <= i < array.length,
     * while the original array remains unmodified. For example, the sorted order of [3, 1, 2] is [1, 2, 0]. Note that
     * this method creates a copy of the original array and sorts it with {@link Arrays#sort(double[])}.
     * @param array the array to sort
     * @return the sorted order of the array elements
     */
    @Immutable
    public static int[] sortedOrder(double[] array) {
        int[] sortedOrder = new int[array.length];
        double[] sorted = ArrayOperations.replicate(array);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(sorted);
        for (int i = 0; i < array.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(sorted, array[i]);
            sortedOrder[repetitions[firstIndex] + firstIndex] = i;
            repetitions[firstIndex]++;
        }
        return sortedOrder;
    }

    /**
     * Returns the sorted order of the elements in the given array, so that array[so[i]] >= array[so[i+1]] for 0 <= i < array.length,
     * while the original array remains unmodified. For example, the sorted order of [3, 1, 2] is [1, 2, 0]. Note that
     * this method creates a copy of the original array and sorts it with {@link Arrays#sort(Object[])}.
     * @param array the array to sort
     * @return the sorted order of the array elements
     */
    @Immutable
    public static int[] sortedOrder(Comparable[] array) {
        int[] sortedOrder = new int[array.length];
        Comparable[] sorted = new Comparable[array.length];
        ArrayOperations.replicate(array, sorted);
        int[] repetitions = newIntArray(array.length, 0);
        Arrays.sort(sorted);
        for (int i = 0; i < array.length; i++) {
            int firstIndex = ArrayOperations.findFirstIndexInOrder(sorted, array[i]);
            sortedOrder[repetitions[firstIndex] + firstIndex] = i;
            repetitions[firstIndex]++;
        }
        return sortedOrder;
    }

    // Continue here

    public static int binarySearch(int[] array, int value, int[] order) {
        int ret;
        // Check first and last element
        if (value <= array[order[0]]) {
            ret = (value == array[order[0]]) ? order[0] : -1;
        }
        else if (value >= array[order[array.length-1]]) {
            ret = -1;
        }
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (value > array[order[middle]]) {
                    a = middle;
                }
                else {
                    b = middle;
                }
            }
            ret = (value == array[order[b]]) ? order[b] : -1;
        }
        return ret;
    }

    public static int binarySearch(float[] array, float value, int[] order) {
        int ret;
        // Check first and last element
        if (value <= array[order[0]]) {
            ret = (value == array[order[0]]) ? order[0] : -1;
        }
        else if (value > array[order[array.length-1]]) {
            ret = -1;
        }
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (value > array[order[middle]]) {
                    a = middle;
                }
                else {
                    b = middle;
                }
            }
            ret = (value == array[order[b]]) ? order[b] : -1;
        }
        return ret;
    }

    public static int binarySearch(long[] array, long value, int[] order) {
        int ret;
        // Check first and last element
        if (value <= array[order[0]]) {
            ret = (value == array[order[0]]) ? order[0] : -1;
        }
        else if (value > array[order[array.length-1]]) {
            ret = -1;
        }
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (value > array[order[middle]]) {
                    a = middle;
                }
                else {
                    b = middle;
                }
            }
            ret = (value == array[order[b]]) ? order[b] : -1;
        }
        return ret;
    }

    public static int binarySearch(double[] array, double value, int[] order) {
        int ret;
        // Check first and last element
        if (value <= array[order[0]]) {
            ret = (value == array[order[0]]) ? order[0] : -1;
        }
        else if (value > array[order[array.length-1]]) {
            ret = -1;
        }
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (value > array[order[middle]]) {
                    a = middle;
                }
                else {
                    b = middle;
                }
            }
            ret = (value == array[order[b]]) ? order[b] : -1;
        }
        return ret;
    }

    public static <T extends Comparable, S extends T> int binarySearch(T[] array, S value, int[] order) {
        int ret;
        // Check first and last elements, to maintain invariant
        if (value.compareTo(array[order[0]]) <= 0) {
            ret = (value.compareTo(array[order[0]]) == 0) ? order[0] : -1;
        }
        else if (value.compareTo(array[order[array.length-1]]) > 0) {
            ret = -1;
        }
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (value.compareTo(array[order[middle]]) > 0) {
                    a = middle;
                }
                else {
                    b = middle;
                }
            }
            ret = (value.compareTo(array[order[b]]) == 0) ? order[b] : -1;
        }
        return ret;
    }

    public static <T, U> void castArray(U[] original, T[] dest) {
        for (int i = 0; i < original.length; i++) {
            dest[i] = (T) original[i];
        }
    }

    public static int[] parseInts(String[] intStrings) {
        int[] ret = new int[intStrings.length];
        for (int i = 0; i < intStrings.length; i++) {
            ret[i] = Integer.parseInt(intStrings[i]);
        }
        return ret;
    }

    public static int[] doubleCapacity(int[] array) {
        int[] ret = new int[2 * array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        for (int i = array.length; i < ret.length; i++) {
            ret[i] = 0;
        }
        return ret;
    }

    public static int[] doubleCapacity(int[] array, int padding) {
        int[] ret = new int[2 * array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        for (int i = array.length; i < ret.length; i++) {
            ret[i] = padding;
        }
        return ret;
    }

    public static void swap(int[] array, int i, int j) {
        int aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    public static void swap(long[] array, int i, int j) {
        long aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    public static void swap(float[] array, int i, int j) {
        float aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    public static void swap(double[] array, int i, int j) {
        double aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    public static <T> void swap(T[] array, int i, int j) {
        T aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    public static void swap(byte[] array, int i, int j) {
        byte aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    public static void swap(char[] array, int i, int j) {
        char aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    public static void swap(boolean[] array, int i, int j) {
        boolean aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

}