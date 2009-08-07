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

import java.util.Comparator;

/**
 *This class provides additional array manipulation operations to those in java.util.Arrays.
 *@author Andres Quiroz Hernandez
 *@version 6.0.1
 */
public abstract class ArrayOperations {
    
    /**
     *@return a new copy of the input array
     */
    public static final byte[] replicate(byte[] array) {
        byte[] ret = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    public static final int[] replicate(int[] array) {
        int[] ret = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    public static final float[] replicate(float[] array) {
        float[] ret = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    public static final double[] replicate(double[] array) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    public static final char[] replicate(char[] array) {
        char[] ret = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new copy of the input array
     */
    public static final String[] replicate(String[] array) {
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = new String(array[i]);
        }
        return ret;
    }
    
    /**
     *@param source the array to be replicated
     *@param destination the pre-allocated distination array, of equal size as the source array, which will be filled with values from the source
     *@throws java.lang.IllegalArgumentException if the arrays are of different sizes
     */
    public static final <T> void replicate(T[] source, T[] destination) {
        if (source.length == destination.length) {
            for (int i = 0; i < source.length; i++) {
                destination[i] = source[i];
            }
        } 
        else {
            throw new IllegalArgumentException("ArrayOperations: Could not replicate source array: Arrays of different dimensions");
        }
    }
    
    /**
     *@return the sum of the elements of the input array
     */
    public static final int sum(int[] array) {
        int sum = 0;
        for (int elem : array) {
            sum += elem;
        }
        return sum;
    }
    
    /**
     *@return the sum of the elements of the input array
     */
    public static final float sum(float[] array) {
        float sum = 0;
        for (float elem : array) {
            sum += elem;
        }
        return sum;
    }
    
    /**
     *@return the sum of the elements of the input array
     */
    public static final double sum(double[] array) {
        double sum = 0;
        for (double elem : array) {
            sum += elem;
        }
        return sum;
    }

    /**
     * @return the product of the elements of the given array
     */
    public static final long product(int[] array) {
        long product = 1;
        for (int elem : array) {
            product *= elem;
        }
        return product;
    }

    /**
     * @return the product of the elements of the given array
     */
    public static final double product(float[] array) {
        double product = 1;
        for (float elem : array) {
            product *= elem;
        }
        return product;
    }

    /**
     * @return the product of the elements of the given array
     */
    public static final double product(double[] array) {
        double product = 1;
        for (double elem : array) {
            product *= elem;
        }
        return product;
    }
    
    /**
     *@return the average of the elements of the input array
     */
    public static final double avg(int[] array) {
        return (double)sum(array)/(double)array.length;
    }
    
    /**
     *@return the average of the elements of the input array
     */
    public static final double avg(float[] array) {
        return (double)sum(array)/(double)array.length;
    }
    
    /**
     *@return the average of the elements of the input array
     */
    public static final double avg(double[] array) {
        return sum(array)/(double)array.length;
    }
    
    /**
     *@return the maximum element value of the elements of the input array
     */
    public static final int max(int[] array) {
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
    public static final float max(float[] array) {
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
    public static final double max(double[] array) {
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
    public static final <T, S extends T> Object max(S[] array, Comparator<T> comp) {
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
    public static final int maxIndex(int[] array) {
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
    public static final int maxIndex(float[] array) {
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
    public static final int maxIndex(double[] array) {
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
    public static final <T, S extends T> int maxIndex(S[] array, Comparator<T> comp) {
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
    public static final int min(int[] array) {
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
    public static final float min(float[] array) {
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
    public static final double min(double[] array) {
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
    public static final <T, S extends T> Object min(S[] array, Comparator<T> comp) {
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
    public static final int minIndex(int[] array) {
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
    public static final int minIndex(float[] array) {
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
    public static final int minIndex(double[] array) {
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
    public static final <T, S extends T> int minIndex(S[] array, Comparator<T> comp) {
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
    public static final byte[] prefix(byte[] array, int until) throws ArrayIndexOutOfBoundsException {
        byte[] ret = new byte[until];
        for (int i = 0; i < until; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return  a new subarray of elements of array from 0 to that indexed by until-1
     */
    public static final int[] prefix(int[] array, int until) throws ArrayIndexOutOfBoundsException {
        int[] ret = new int[until];
        for (int i = 0; i < until; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    public static final float[] prefix(float[] array, int until) throws ArrayIndexOutOfBoundsException {
        float[] ret = new float[until];
        for (int i = 0; i < until; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    public static final double[] prefix(double[] array, int until) throws ArrayIndexOutOfBoundsException {
        double[] ret = new double[until];
        for (int i = 0; i < until; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    public static final char[] prefix(char[] array, int until) throws ArrayIndexOutOfBoundsException {
        char[] ret = new char[until];
        for (int i = 0; i < until; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    public static final String[] prefix(String[] array, int until) throws ArrayIndexOutOfBoundsException {
        String[] ret = new String[until];
        for (int i = 0; i < until; i++) {
            ret[i] = new String(array[i]);
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1. Caution: Does not replicate objects
     */
    public static final Object[] prefix(Object[] array, int until) throws ArrayIndexOutOfBoundsException {
        Object[] ret = new Object[until];
        for (int i = 0; i < until; i++) {
            ret[i] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from 0 to that indexed by until-1
     */
    public static final byte[] suffix(byte[] array, int from) throws ArrayIndexOutOfBoundsException {
        byte[] ret = new byte[array.length-from];
        for (int i = from; i < array.length; i++) {
            ret[i-from] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    public static final int[] suffix(int[] array, int from) throws ArrayIndexOutOfBoundsException {
        int[] ret = new int[array.length-from];
        for (int i = from; i < array.length; i++) {
            ret[i-from] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    public static final float[] suffix(float[] array, int from) throws ArrayIndexOutOfBoundsException {
        float[] ret = new float[array.length-from];
        for (int i = from; i < array.length; i++) {
            ret[i-from] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    public static final double[] suffix(double[] array, int from) throws ArrayIndexOutOfBoundsException {
        double[] ret = new double[array.length-from];
        for (int i = from; i < array.length; i++) {
            ret[i-from] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    public static final char[] suffix(char[] array, int from) throws ArrayIndexOutOfBoundsException {
        char[] ret = new char[array.length-from];
        for (int i = from; i < array.length; i++) {
            ret[i-from] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element
     */
    public static final String[] suffix(String[] array, int from) throws ArrayIndexOutOfBoundsException {
        String[] ret = new String[array.length-from];
        for (int i = from; i < array.length; i++) {
            ret[i-from] = new String(array[i]);
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to the last element. Caution: Does not replicate objects!
     */
    public static final Object[] suffix(Object[] array, int from) throws ArrayIndexOutOfBoundsException {
        Object[] ret = new Object[array.length-from];
        for (int i = from; i < array.length; i++) {
            ret[i-from] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    public static final byte[] subArray(byte[] array, int from, int until) throws ArrayIndexOutOfBoundsException {
        return suffix(prefix(array, until), from);
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    public static final int[] subArray(int[] array, int from, int until) throws ArrayIndexOutOfBoundsException {
        return suffix(prefix(array, until), from);
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    public static final float[] subArray(float[] array, int from, int until) throws ArrayIndexOutOfBoundsException {
        return suffix(prefix(array, until), from);
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    public static final double[] subArray(double[] array, int from, int until) throws ArrayIndexOutOfBoundsException {
        return suffix(prefix(array, until), from);
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    public static final char[] subArray(char[] array, int from, int until) throws ArrayIndexOutOfBoundsException {
        return suffix(prefix(array, until), from);
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1
     */
    public static final String[] subArray(String[] array, int from, int until) throws ArrayIndexOutOfBoundsException {
        return suffix(prefix(array, until), from);
    }
    
    /**
     *@return a new subarray of elements of array from that indexed by from to that indexed by until-1. Caution: Does not replicate objects!
     */
    public static final Object[] subArray(Object[] array, int from, int until) throws ArrayIndexOutOfBoundsException {
        return suffix(prefix(array, until), from);
    }
    
    /**
     *@return a new array containing the elements of a1 followed by those of a2
     */
    public static final byte[] join(byte[] a1, byte[] a2) {
        byte[] ret = new byte[a1.length+a2.length];
        for (int i = 0; i < a1.length; i++) {
            ret[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            ret[i+a1.length] = a2[i];
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of a1 followed by those of a2
     */
    public static final int[] join(int[] a1, int[] a2) {
        int[] ret = new int[a1.length+a2.length];
        for (int i = 0; i < a1.length; i++) {
            ret[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            ret[i+a1.length] = a2[i];
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of a1 followed by those of a2
     */
    public static final float[] join(float[] a1, float[] a2) {
        float[] ret = new float[a1.length+a2.length];
        for (int i = 0; i < a1.length; i++) {
            ret[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            ret[i+a1.length] = a2[i];
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of a1 followed by those of a2
     */
    public static final double[] join(double[] a1, double[] a2) {
        double[] ret = new double[a1.length+a2.length];
        for (int i = 0; i < a1.length; i++) {
            ret[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            ret[i+a1.length] = a2[i];
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of a1 followed by those of a2
     */
    public static final char[] join(char[] a1, char[] a2) {
        char[] ret = new char[a1.length+a2.length];
        for (int i = 0; i < a1.length; i++) {
            ret[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            ret[i+a1.length] = a2[i];
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of a1 followed by those of a2
     */
    public static final String[] join(String[] a1, String[] a2) {
        String[] ret = new String[a1.length+a2.length];
        for (int i = 0; i < a1.length; i++) {
            ret[i] = new String(a1[i]);
        }
        for (int i = 0; i < a2.length; i++) {
            ret[i+a1.length] = new String(a2[i]);
        }
        return ret;
    }
    
    /**
     *@return a new array containing the elements of a1 followed by those of a2. Caution: Does not replicate objects!
     */
    public static final Object[] join(Object[] a1, Object[] a2) {
        Object[] ret = new Object[a1.length+a2.length];
        for (int i = 0; i < a1.length; i++) {
            ret[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            ret[i+a1.length] = a2[i];
        }
        return ret;
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    public static final byte[] delete(byte[] array, int item) {
        return join(prefix(array, item), suffix(array, item+1));
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    public static final int[] delete(int[] array, int item) {
        return join(prefix(array, item), suffix(array, item+1));
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    public static final float[] delete(float[] array, int item) {
        return join(prefix(array, item), suffix(array, item+1));
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    public static final double[] delete(double[] array, int item) {
        return join(prefix(array, item), suffix(array, item+1));
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    public static final char[] delete(char[] array, int item) {
        return join(prefix(array, item), suffix(array, item+1));
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified)
     */
    public static final String[] delete(String[] array, int item) {
        return join(prefix(array, item), suffix(array, item+1));
    }
    
    /**
     *@return a new array of size array.length-1 in which the element in the position given by item in the input array has been deleted (the input array is not modified). Caution: Does not replicate objects!
     */
    public static final Object[] delete(Object[] array, int item) {
        return join(prefix(array, item), suffix(array, item+1));
    }
    
    /**
     *Prints the elements of the array to System.out, one per line
     */
    public static final void print(byte[] array) {
        for (byte elem : array)
            System.out.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, one per line
     */
    public static final void print(int[] array) {
        for (int elem : array)
            System.out.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, one per line
     */
    public static final void print(float[] array) {
        for (float elem : array)
            System.out.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, one per line
     */
    public static final void print(double[] array) {
        for (double elem : array)
            System.out.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, one per line
     */
    public static final void print(char[] array) {
        for (char elem : array)
            System.out.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, one per line
     */
    public static final void print(String[] array) {
        for (String elem : array)
            System.out.println(elem);
    }
    
    /**
     *Prints the elements of the array to System.out, one per line, using objects' toString method
     */
    public static final void print(Object[] array) {
        for (Object elem : array) {
            System.out.println(elem.toString());
        }
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out
     */
    public static final void printHorizontal(byte[] array) {
        System.out.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i]);
        }
        System.out.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out
     */
    public static final void printHorizontal(int[] array) {
        System.out.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i]);
        }
        System.out.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out
     */
    public static final void printHorizontal(float[] array) {
        System.out.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i]);
        }
        System.out.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out
     */
    public static final void printHorizontal(double[] array) {
        System.out.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i]);
        }
        System.out.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out
     */
    public static final void printHorizontal(char[] array) {
        System.out.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i]);
        }
        System.out.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out
     */
    public static final void printHorizontal(String[] array) {
        System.out.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i]);
        }
        System.out.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out, using objects' toString method
     */
    public static final void printHorizontal(Object[] array) {
        System.out.print(array[0].toString());
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i].toString());
        }
        System.out.println();
    }
    
    /**
     *Prints a comma separated list of the elements of the input array to System.out
     */
    public static final void printHorizontal(boolean[] array) {
        System.out.print(array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.print(", " + array[i]);
        }
        System.out.println();
    }
    
    /**
     *@return true if all of the elements of the input array are true
     */
    public static final boolean tautology(boolean[] array) {
        boolean ret = true;
        for (boolean ind : array) {
            if (!ind) {
                ret = false;
                break;
            }
        }
        return ret;
    }
    
    /**
     *@return true if all the elements of the input array are false
     */
    public static final boolean contradiction(boolean[] array) {
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
     */
    public static final int seqFind(byte b, byte[] array) {
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
     *@return the index of the first b in the array, or -1 if not found
     */
    public static final int seqFind(int b, int[] array) {
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
     *@return the index of the first b in the array, or -1 if not found
     */
    public static final int seqFind(float b, float[] array) {
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
     *@return the index of the first b in the array, or -1 if not found
     */
    public static final int seqFind(double b, double[] array) {
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
     *@return the index of the first b in the array, or -1 if not found
     */
    public static final int seqFind(char b, char[] array) {
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
     *@return the index of the first b in the array, or -1 if not found
     */
    public static final int seqFind(Object b, Object[] array) {
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
     *@return the index of the first b in the array, starting at from, or -1 if not found
     */
    public static final int seqFind(byte b, byte[] array, int from) {
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
     *@return the index of the first b in the array, starting at from, or -1 if not found
     */
    public static final int seqFind(int b, int[] array, int from) {
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
     *@return the index of the first b in the array, starting at from, or -1 if not found
     */
    public static final int seqFind(float b, float[] array, int from) {
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
     *@return the index of the first b in the array, starting at from, or -1 if not found
     */
    public static final int seqFind(double b, double[] array, int from) {
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
     *@return the index of the first b in the array, starting at from, or -1 if not found
     */
    public static final int seqFind(char b, char[] array, int from) {
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
     *@return the index of the first b in the array, starting at from, or -1 if not found
     */
    public static final int seqFind(Object b, Object[] array, int from) {
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
     *@return the element of array that is closest to value
     */
    public static final int findClosest(int[] array, int value) {
        int ret = array[0];
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
    public static final int findClosestIndex(int[] array, int value) {
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
     *@return the element of array that is closest to value
     */
    public static final double findClosest(double[] array, double value) {
        double ret = array[0];
        double minDiff = Math.abs(value - array[0]);
        double diff;
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
    public static final int findClosestIndex(double[] array, double value) {
        int ret = 0;
        double minDiff = Math.abs(value - array[0]);
        double diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) < minDiff) {
                ret = i;
                minDiff = diff;
            }
        }
        return ret;
    }
    
    /**
     *@return the element of array that is closest to value
     */
    public static final float findClosest(float[] array, float value) {
        float ret = array[0];
        float minDiff = Math.abs(value - array[0]);
        float diff;
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
    public static final int findClosestIndex(float[] array, float value) {
        int ret = 0;
        float minDiff = Math.abs(value - array[0]);
        float diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) < minDiff) {
                ret = i;
                minDiff = diff;
            }
        }
        return ret;
    }
    
    /**
     *@return the element of array that is closest to value
     */
    public static final String findClosest(String[] array, String value) {
        String ret = array[0];
        int minDiff = Math.abs(value.compareTo(array[0]));
        int diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs(value.compareTo(array[i]))) < minDiff) {
                ret = array[i];
                minDiff = diff;
            }
        }
        return new String(ret);
    }
    
    /**
     *@return the index of the element of array that is closest to value
     */
    public static final int findClosestIndex(String[] array, String value) {
        int ret = 0;
        int minDiff = Math.abs(value.compareTo(array[0]));
        int diff;
        for (int i = 1; i < array.length; i++) {
            if ((diff = Math.abs(value.compareTo(array[i]))) < minDiff) {
                ret = i;
                minDiff = diff;
            }
        }
        return ret;
    }
    
    /**
     *@return the element of array that is closest to value
     */
    public static final char findClosest(char[] array, char value) {
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
    public static final int findClosestIndex(char[] array, char value) {
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
     *@return the element of a sorted array that is closest to value
     */
    public static final int findClosestInOrder(int[] array, int value) {
        int ret = array[0];
        int minDiff = Math.abs(value - array[0]);
        int diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = array[i-1];
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = array[i-1];
        }
        return ret;
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    public static final int findClosestIndexInOrder(int[] array, int value) {
        int ret = 0;
        int minDiff = Math.abs(value - array[0]);
        int diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = i - 1;
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = i - 1;
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array that is closest to value
     */
    public static final double findClosestInOrder(double[] array, double value) {
        double ret = array[0];
        double minDiff = Math.abs(value - array[0]);
        double diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = array[i-1];
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = array[i-1];
        }
        return ret;
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    public static final int findClosestIndexInOrder(double[] array, double value) {
        int ret = 0;
        double minDiff = Math.abs(value - array[0]);
        double diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = i - 1;
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = i - 1;
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array that is closest to value
     */
    public static final float findClosestInOrder(float[] array, float value) {
        float ret = array[0];
        float minDiff = Math.abs(value - array[0]);
        float diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = array[i-1];
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = array[i-1];
        }
        return ret;
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    public static final int findClosestIndexInOrder(float[] array, float value) {
        int ret = 0;
        float minDiff = Math.abs(value - array[0]);
        float diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = i - 1;
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = i - 1;
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array that is closest to value
     */
    public static final char findClosestInOrder(char[] array, char value) {
        char ret = array[0];
        int minDiff = Math.abs(value - array[0]);
        int diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = array[i-1];
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = array[i-1];
        }
        return ret;
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    public static final int findClosestIndexInOrder(char[] array, char value) {
        int ret = 0;
        int minDiff = Math.abs(value - array[0]);
        int diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value - array[i])) > minDiff) {
                ret = i - 1;
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = i - 1;
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array that is closest to value
     */
    public static final String findClosestInOrder(String[] array, String value) {
        String ret = array[0];
        int minDiff = Math.abs(value.compareTo(array[0]));
        int diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value.compareTo(array[i]))) > minDiff) {
                ret = array[i-1];
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = array[i-1];
        }
        return new String(ret);
    }
    
    /**
     *@return the index of the element of a sorted array that is closest to value
     */
    public static final int findClosestIndexInOrder(String[] array, String value) {
        int ret = 0;
        int minDiff = Math.abs(value.compareTo(array[0]));
        int diff;
        int i = 1;
        for (; i < array.length; i++) {
            if ((diff = Math.abs(value.compareTo(array[i]))) > minDiff) {
                ret = i - 1;
                break;
            }
            minDiff = diff;
        }
        if (i == array.length) {
            ret = i - 1;
        }
        return ret;
    }
    
    /**
     *@return the element of a sorted array of comparable elements that follows (or is equal to) the given value in order
     *(null if the value is larger than all values in the array)
     *@see java.lang.Comparable
     */
    public static final <T extends Comparable, S extends T> T findNextInOrder(T[] array, S value) {
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
    public static final <T extends Comparable, S extends T> int findPositionInOrder(T[] array, S value) {
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
     *@return the element of a sorted array that follows (or is equal to) the given value in order
     *(null if the value is larger than all values in the array), using the given comparator
     */
    public static final <T, S extends T, U extends T> S findNextInOrder(S[] array, U value, Comparator<T> comp) {
        S ret;
        if (comp.compare(value, array[0]) <= 0) {
            ret = array[0];
        } 
        else if (comp.compare(value, array[array.length-1]) > 0) {
            ret = null;
        } 
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (comp.compare(value, array[middle]) > 0) {
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
     *@return the position in the sorted array of the element that follows (or is equal to) the given
     *value in order, using the given comparator, which is the position where the value should be inserted in the array.
     *The position is the length of the array if the value is greater than all values in the array.
     *@see java.lang.Comparable
     */
    public static final <T, S extends T, U extends T> int findPositionInOrder(S[] array, U value, Comparator<T> comp) {
        int ret;
        if (comp.compare(value, array[0]) <= 0) {
            ret = 0;
        } 
        else if (comp.compare(value, array[array.length-1]) > 0) {
            ret = array.length;
        } 
        else {
            int a = 0;
            int b = array.length-1;
            int middle;
            while (b - a > 1) {
                middle = (a+b) / 2;
                if (comp.compare(value, array[middle]) > 0) {
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
     *@return new array with normalized values of array with respect to the max value of array
     */
    public static final double[] normalizeMax(int[] array) {
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
    public static final double[] normalizeSum(int[] array) {
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
    public static final double[] normalizeVal(int[] array, double maxval) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/maxval;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the max value of array
     */
    public static final double[] normalizeMax(float[] array) {
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
    public static final double[] normalizeSum(float[] array) {
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
    public static final double[] normalizeVal(float[] array, double maxval) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = (double)array[i]/maxval;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the max value of array
     */
    public static final double[] normalizeMax(double[] array) {
        double[] ret = new double[array.length];
        double maxval = max(array);
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i]/maxval;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the sum of the values of array
     */
    public static final double[] normalizeSum(double[] array) {
        double[] ret = new double[array.length];
        double arraysum = sum(array);
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i]/arraysum;
        }
        return ret;
    }
    
    /**
     *@return new array with normalized values of array with respect to the value given
     */
    public static final double[] normalizeVal(double[] array, double maxval) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i]/maxval;
        }
        return ret;
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    public static final int[] reverse(int[] array) {
        int[] ret = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    public static final float[] reverse(float[] array) {
        float[] ret = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    public static final double[] reverse(double[] array) {
        double[] ret = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    public static final char[] reverse(char[] array) {
        char[] ret = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    public static final String[] reverse(String[] array) {
        String[] ret = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    public static final boolean[] reverse(boolean[] array) {
        boolean[] ret = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return a new array in which the elements of the input array are in reverse order (does not modify the original array)
     */
    public static final Object[] reverse(Object[] array) {
        Object[] ret = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[ret.length-i-1] = array[i];
        }
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    public static final int[] insert(int value, int[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final int[] addElement(int elem, int[] array) {
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
    public static final int[] insertSorted(int value, int[] array) {
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
    public static final byte[] insert(byte value, byte[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final byte[] addElement(byte elem, byte[] array) {
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
    public static final float[] insert(float value, float[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final float[] addElement(float elem, float[] array) {
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
    public static final float[] insertSorted(float value, float[] array) {
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
    public static final double[] insert(double value, double[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final double[] addElement(double elem, double[] array) {
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
    public static final double[] insertSorted(double value, double[] array) {
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
    public static final char[] insert(char value, char[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final char[] addElement(char elem, char[] array) {
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
    public static final char[] insertSorted(char value, char[] array) {
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
    public static final String[] insert(String value, String[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final String[] addElement(String elem, String[] array) {
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
    public static final String[] insertSorted(String value, String[] array) {
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
    public static final Object[] insert(Object value, Object[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final Object[] addElement(Object elem, Object[] array) {
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
    public static final <T extends Comparable, S extends T> T[] insertSorted(S value, T[] array) {
        int pos = findPositionInOrder(array, value);
        T[] ret = null;
        ret = (T[])insert(value, array, pos);
        return ret;
    }
    
    /**
     *@return new sorted array where value is inserted into an initially sorted array.
     */
    public static final <T, S extends T> T[] insertSorted(S value, T[] array, Comparator<T> comp) {
        int pos = findPositionInOrder(array, value, comp);
        T[] ret = null;
        ret = (T[])insert(value, array, pos);
        return ret;
    }
    
    /**
     *@return new array where value is inserted into array in the position given
     */
    public static final boolean[] insert(boolean value, boolean[] array, int pos) throws ArrayIndexOutOfBoundsException {
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
    public static final boolean[] addElement(boolean elem, boolean[] array) {
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
    public static final int[] generateEnumeration(int numElements) {
        int[] ret = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            ret[i] = i;
        }
        return ret;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     */
    public static final int[] initialize(int[] array, int initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     */
    public static final float[] initialize(float[] array, float initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     */
    public static final double[] initialize(double[] array, double initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     */
    public static final char[] initialize(char[] array, char initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }
    
    /**
     *@return the given array with all values set to the given initValue
     */
    public static final boolean[] initialize(boolean[] array, boolean initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }
    
    /**
     *@return el arreglo dado con todos sus valores iguales al valor dado
     */
    public static final String[] initialize(String[] array, String initValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = initValue;
        }
        return array;
    }
    
    /**
     *Casts the elements of the origin array to the type of the destination array and copies each of these elements to the new array, which must be pre-allocated with the correct size.
     *@throws java.lang.IllegalArgumentException if the arrays are of different sizes
     *@throws java.lang.ClassCastException if the elements of the origin array cannot be cast to the destination type
     */
    public static final <T> void arrayCast(T[] destination, Object[] origin) {
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
    public static final String[] toString(int[] array) {
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
    public static final String[] toString(double[] array) {
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
    public static final String[] toString(long[] array) {
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
    public static final String[] toString(float[] array) {
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
    public static final String[] toString(boolean[] array) {
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
    public static final int[] toIntArray(String[] array) {
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
    public static final double[] toDoubleArray(String[] array) {
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
    public static final long[] toLongArray(String[] array) {
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
    public static final float[] toFloatArray(String[] array) {
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
    public static final boolean[] toBooleanArray(String[] array) {
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
    public static final boolean areEqual(int[] array1, int[] array2) {
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
    public static final boolean areEqual(long[] array1, long[] array2) {
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
    public static final boolean areEqual(float[] array1, float[] array2) {
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
    public static final boolean areEqual(double[] array1, double[] array2) {
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
    public static final boolean areEqual(byte[] array1, byte[] array2) {
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
    public static final boolean areEqual(char[] array1, char[] array2) {
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
    public static final boolean areEqual(Object[] array1, Object[] array2) {
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
    public static final <T> boolean isEmpty(T[] array) {
        int length = (array == null) ? 0 : array.length;
        return (length == 0);
    }

}