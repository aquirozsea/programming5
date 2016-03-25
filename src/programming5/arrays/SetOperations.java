/*
 * SetOperations.java
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

/**
 *This class groups operations for array representations of sets. Returns a new array 
 *with the result.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public abstract class SetOperations {
	
	/**
	 *@return a new array with the union of the two sets
	 */
	public static final int[] union(int[] set1, int[] set2) {
		int[] union = new int[set1.length+set2.length];
		int[] seek, base;
		int intersect = 0;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		int ui = 0;
		boolean found;
		for (int bElem : base) {
			found = false;
			for (int sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				union[ui++] = bElem;
			}
		}
		for (int sElem : seek) {
			union[ui++] = sElem;
		}
		if (intersect > 0) {
			union = ArrayOperations.prefix(union, union.length-intersect);
		}
		return union;
	}
	
	/**
	 *@return a new array with the union of the two sets
	 */
	public static final float[] union(float[] set1, float[] set2) {
		float[] union = new float[set1.length+set2.length];
		float[] seek, base;
		int intersect = 0;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		int ui = 0;
		boolean found;
		for (float bElem : base) {
			found = false;
			for (float sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				union[ui++] = bElem;
			}
		}
		for (float sElem : seek) {
			union[ui++] = sElem;
		}
		if (intersect > 0) {
			union = ArrayOperations.prefix(union, union.length-intersect);
		}
		return union;
	}
	
	/**
	 *@return a new array with the union of the two sets
	 */
	public static final double[] union(double[] set1, double[] set2) {
		double[] union = new double[set1.length+set2.length];
		double[] seek, base;
		int intersect = 0;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		int ui = 0;
		boolean found;
		for (double bElem : base) {
			found = false;
			for (double sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				union[ui++] = bElem;
			}
		}
		for (double sElem : seek) {
			union[ui++] = sElem;
		}
		if (intersect > 0) {
			union = ArrayOperations.prefix(union, union.length-intersect);
		}
		return union;
	}
	
	/**
	 *@return a new array with the union of the two sets
	 */
	public static final char[] union(char[] set1, char[] set2) {
		char[] union = new char[set1.length+set2.length];
		char[] seek, base;
		int intersect = 0;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		int ui = 0;
		boolean found;
		for (char bElem : base) {
			found = false;
			for (char sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				union[ui++] = bElem;
			}
		}
		for (char sElem : seek) {
			union[ui++] = sElem;
		}
		if (intersect > 0) {
			union = ArrayOperations.prefix(union, union.length-intersect);
		}
		return union;
	}
	
	/**
	 *@return a new array with the union of the two sets
	 */
	public static final Object[] union(Object[] set1, Object[] set2) {
		Object[] union = new Object[set1.length+set2.length];
		Object[] seek, base;
		int intersect = 0;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		int ui = 0;
		boolean found;
		for (Object bElem : base) {
			found = false;
			for (Object sElem : seek) {
				if (bElem.equals(sElem)) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				union[ui++] = bElem;
			}
		}
		for (Object sElem : seek) {
			union[ui++] = sElem;
		}
		if (intersect > 0) {
			union = ArrayOperations.prefix(union, union.length-intersect);
		}
		return union;
	}
	
	/**
	 *@return the intersection of the two sets or a zero length array if they are disjoint
	 */
	public static final int[] intersect(int[] set1, int[] set2) {
		int[] inter;
		int[] seek, base;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		inter = new int[base.length];
		int interCount = 0;
		for (int bElem : base) {
			for (int sElem : seek) {
				if (bElem == sElem) {
					inter[interCount++] = bElem;
					break;
				}
			}
		}
		if (interCount < base.length) {
			if (interCount == 0) {
				inter = new int[0];
			}
			else {
				inter = ArrayOperations.prefix(inter, interCount);
			}
		}
		return inter;
	}
	
	/**
	 *@return the intersection of the two sets or a zero length array if they are disjoint
	 */
	public static final float[] intersect(float[] set1, float[] set2) {
		float[] inter;
		float[] seek, base;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		inter = new float[base.length];
		int interCount = 0;
		for (float bElem : base) {
			for (float sElem : seek) {
				if (bElem == sElem) {
					inter[interCount++] = bElem;
					break;
				}
			}
		}
		if (interCount < base.length) {
			if (interCount == 0) {
				inter = new float[0];
			}
			else {
				inter = ArrayOperations.prefix(inter, interCount);
			}
		}
		return inter;
	}
	
	/**
	 *@return the intersection of the two sets or a zero length array if they are disjoint
	 */
	public static final double[] intersect(double[] set1, double[] set2) {
		double[] inter;
		double[] seek, base;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		inter = new double[base.length];
		int interCount = 0;
		for (double bElem : base) {
			for (double sElem : seek) {
				if (bElem == sElem) {
					inter[interCount++] = bElem;
					break;
				}
			}
		}
		if (interCount < base.length) {
			if (interCount == 0) {
				inter = new double[0];
			}
			else {
				inter = ArrayOperations.prefix(inter, interCount);
			}
		}
		return inter;
	}
	
	/**
	 *@return the intersection of the two sets or a zero length array if they are disjoint
	 */
	public static final char[] intersect(char[] set1, char[] set2) {
		char[] inter;
		char[] seek, base;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		inter = new char[base.length];
		int interCount = 0;
		for (char bElem : base) {
			for (char sElem : seek) {
				if (bElem == sElem) {
					inter[interCount++] = bElem;
					break;
				}
			}
		}
		if (interCount < base.length) {
			if (interCount == 0) {
				inter = new char[0];
			}
			else {
				inter = ArrayOperations.prefix(inter, interCount);
			}
		}
		return inter;
	}
	
	/**
	 *@return the intersection of the two sets or a zero length array if they are disjoint, where the equality of objects is given by 
	 *the implementation of the equals method (same object if not implemented?)
	 */
	public static final String[] intersect(String[] set1, String[] set2) {
		String[] inter;
		String[] seek, base;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		inter = new String[base.length];
		int interCount = 0;
		for (String bElem : base) {
			for (String sElem : seek) {
				if (bElem.equals(sElem)) {
					inter[interCount++] = bElem;
					break;
				}
			}
		}
		if (interCount < base.length) {
			if (interCount == 0) {
				inter = new String[0];
			}
			else {
				inter = ArrayOperations.prefix(inter, interCount);
			}
		}
		return inter;
	}

        /**
	 *@return the intersection of the two sets or a zero length array if they are disjoint, where the equality of objects is given by
	 *the implementation of the equals method (same object if not implemented?)
	 */
	public static final Object[] intersect(Object[] set1, Object[] set2) {
		Object[] inter;
		Object[] seek, base;
		if (set1.length < set2.length) {
			seek = set2;
			base = set1;
		}
		else {
			seek = set1;
			base = set2;
		}
		inter = new Object[base.length];
		int interCount = 0;
		for (Object bElem : base) {
			for (Object sElem : seek) {
				if (bElem.equals(sElem)) {
					inter[interCount++] = bElem;
					break;
				}
			}
		}
		if (interCount < base.length) {
			if (interCount == 0) {
				inter = new Object[0];
			}
			else {
				inter = ArrayOperations.prefix(inter, interCount);
			}
		}
		return inter;
	}
	
	/**
	 *@return a new array with the difference between set1 and set2, which can be a zero length array if set1 is a subset of set2
	 */
	public static final int[] difference(int[] set1, int[] set2) {
		int[] diff = new int[set1.length];
		int[] base = set1;
		int[] seek = set2;
		int intersect = 0;
		int di = 0;
		boolean found;
		for (int bElem : base) {
			found = false;	
			for (int sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				diff[di++] = bElem;
			}
		}
		if (intersect > 0) {
			if (intersect < base.length) {
				diff = ArrayOperations.prefix(diff, base.length-intersect);
			}
			else {
				diff = new int[0];
			}
		}
		return diff;
	}
	
	/**
	 *@return a new array with the difference between set1 and set2, which can be a zero length array if set1 is a subset of set2
	 */
	public static final float[] difference(float[] set1, float[] set2) {
		float[] diff = new float[set1.length];
		float[] base = set1;
		float[] seek = set2;
		int intersect = 0;
		int di = 0;
		boolean found;
		for (float bElem : base) {
			found = false;	
			for (float sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				diff[di++] = bElem;
			}
		}
		if (intersect > 0) {
			if (intersect < base.length) {
				diff = ArrayOperations.prefix(diff, base.length-intersect);
			}
			else {
				diff = new float[0];
			}
		}
		return diff;
	}
	
	/**
	 *@return a new array with the difference between set1 and set2, which can be a zero length array if set1 is a subset of set2
	 */
	public static final double[] difference(double[] set1, double[] set2) {
		double[] diff = new double[set1.length];
		double[] base = set1;
		double[] seek = set2;
		int intersect = 0;
		int di = 0;
		boolean found;
		for (double bElem : base) {
			found = false;	
			for (double sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				diff[di++] = bElem;
			}
		}
		if (intersect > 0) {
			if (intersect < base.length) {
				diff = ArrayOperations.prefix(diff, base.length-intersect);
			}
			else {
				diff = new double[0];
			}
		}
		return diff;
	}
	
	/**
	 *@return a new array with the difference between set1 and set2, which can be a zero length array if set1 is a subset of set2
	 */
	public static final char[] difference(char[] set1, char[] set2) {
		char[] diff = new char[set1.length];
		char[] base = set1;
		char[] seek = set2;
		int intersect = 0;
		int di = 0;
		boolean found;
		for (char bElem : base) {
			found = false;	
			for (char sElem : seek) {
				if (bElem == sElem) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				diff[di++] = bElem;
			}
		}
		if (intersect > 0) {
			if (intersect < base.length) {
				diff = ArrayOperations.prefix(diff, base.length-intersect);
			}
			else {
				diff = new char[0];
			}
		}
		return diff;
	}

        /**
	 *@return a new array with the difference between set1 and set2, which can be a zero length array if set1 is a subset of set2
	 */
	public static final String[] difference(String[] set1, String[] set2) {
		String[] diff = new String[set1.length];
		String[] base = set1;
		String[] seek = set2;
		int intersect = 0;
		int di = 0;
		boolean found;
		for (String bElem : base) {
			found = false;
			for (String sElem : seek) {
				if (bElem.equals(sElem)) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				diff[di++] = bElem;
			}
		}
		if (intersect > 0) {
			if (intersect < base.length) {
				diff = ArrayOperations.prefix(diff, base.length-intersect);
			}
			else {
				diff = new String[0];
			}
		}
		return diff;
	}
	
	/**
	 *@return a new array with the difference between set1 and set2, which can be a zero length array if set1 is a subset of set2
	 */
	public static final Object[] difference(Object[] set1, Object[] set2) {
		Object[] diff = new Object[set1.length];
		Object[] base = set1;
		Object[] seek = set2;
		int intersect = 0;
		int di = 0;
		boolean found;
		for (Object bElem : base) {
			found = false;	
			for (Object sElem : seek) {
				if (bElem.equals(sElem)) {
					intersect++;
					found = true;
					break;
				}
			}
			if (!found) {
				diff[di++] = bElem;
			}
		}
		if (intersect > 0) {
			if (intersect < base.length) {
				diff = ArrayOperations.prefix(diff, base.length-intersect);
			}
			else {
				diff = new Object[0];
			}
		}
		return diff;
	}
	
	/**
         *@return true if the elements of the array are all distinct (no repeated elements)
         */
        public static final boolean isSet(int[] array) {
		boolean ret = true;
		for (int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					ret = false;
					break;
				}
			}
			if (!ret) {
				break;
			}
		}
		return ret;
	}
	
	/**
         *@return true if the elements of the array are all distinct (no repeated elements)
         */
        public static final boolean isSet(float[] array) {
		boolean ret = true;
		for (int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					ret = false;
					break;
				}
			}
			if (!ret) {
				break;
			}
		}
		return ret;
	}
	
	/**
         *@return true if the elements of the array are all distinct (no repeated elements)
         */
        public static final boolean isSet(double[] array) {
		boolean ret = true;
		for (int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					ret = false;
					break;
				}
			}
			if (!ret) {
				break;
			}
		}
		return ret;
	}
	
	/**
         *@return true if the elements of the array are all distinct (no repeated elements)
         */
        public static final boolean isSet(char[] array) {
		boolean ret = true;
		for (int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					ret = false;
					break;
				}
			}
			if (!ret) {
				break;
			}
		}
		return ret;
	}
	
	/**
         *@return true if the elements of the array are all distinct (no repeated elements) using the objects' equals method
         */
        public static final boolean isSet(Object[] array) {
		boolean ret = true;
		for (int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i].equals(array[j])) {
					ret = false;
					break;
				}
			}
			if (!ret) {
				break;
			}
		}
		return ret;
	}
	
	/**
	 *@return A new array where the repetitions of elements of the original array have been removed. If there are no 
	 *repetitions, it returns the original array.
	 */
	public static final int[] enforce(int[] array) {
		int[] remove = new int[array.length-1];
		int ri = 0;
		for (int i = 0; i < array.length-1; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					remove[ri++] = i;
					break;
				}
			}
		}
		int[] ret = null;
		if (ri > 0) {
			ret = new int[array.length-ri];
			ri = 0;
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				if (ri < remove.length) {
					if (remove[ri] != i) {
						ret[j++] = array[i];
					}
					else {
						ri++;
					}
				}
				else {
					ret[j++] = array[i];
				}
			}
		}
		else {
			ret = array;
		}
		return ret;
	}
	
	/**
	 *@return A new array where the repetitions of elements of the original array have been removed. If there are no 
	 *repetitions, it returns the original array.
	 */
	public static final float[] enforce(float[] array) {
		int[] remove = new int[array.length-1];
		int ri = 0;
		for (int i = 0; i < array.length-1; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					remove[ri++] = i;
					break;
				}
			}
		}
		float[] ret = null;
		if (ri > 0) {
			ret = new float[array.length-ri];
			ri = 0;
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				if (ri < remove.length) {
					if (remove[ri] != i) {
						ret[j++] = array[i];
					}
					else {
						ri++;
					}
				}
				else {
					ret[j++] = array[i];
				}
			}
		}
		else {
			ret = array;
		}
		return ret;
	}
	
	/**
	 *@return A new array where the repetitions of elements of the original array have been removed. If there are no 
	 *repetitions, it returns the original array.
	 */
	public static final double[] enforce(double[] array) {
		int[] remove = new int[array.length-1];
		int ri = 0;
		for (int i = 0; i < array.length-1; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					remove[ri++] = i;
					break;
				}
			}
		}
		double[] ret = null;
		if (ri > 0) {
			ret = new double[array.length-ri];
			ri = 0;
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				if (ri < remove.length) {
					if (remove[ri] != i) {
						ret[j++] = array[i];
					}
					else {
						ri++;
					}
				}
				else {
					ret[j++] = array[i];
				}
			}
		}
		else {
			ret = array;
		}
		return ret;
	}
	
	/**
	 *@return A new array where the repetitions of elements of the original array have been removed. If there are no 
	 *repetitions, it returns the original array.
	 */
	public static final char[] enforce(char[] array) {
		int[] remove = new int[array.length-1];
		int ri = 0;
		for (int i = 0; i < array.length-1; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i] == array[j]) {
					remove[ri++] = i;
					break;
				}
			}
		}
		char[] ret = null;
		if (ri > 0) {
			ret = new char[array.length-ri];
			ri = 0;
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				if (ri < remove.length) {
					if (remove[ri] != i) {
						ret[j++] = array[i];
					}
					else {
						ri++;
					}
				}
				else {
					ret[j++] = array[i];
				}
			}
		}
		else {
			ret = array;
		}
		return ret;
	}
	
	/**
	 *@return A new array where the repetitions of elements of the original array have been removed. If there are no 
	 *repetitions, it returns the original array.
	 */
	public static final Object[] enforce(Object[] array) {
		int[] remove = new int[array.length-1];
		int ri = 0;
		for (int i = 0; i < array.length-1; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i].equals(array[j])) {
					remove[ri++] = i;
					break;
				}
			}
		}
		Object[] ret = null;
		if (ri > 0) {
			ret = new Object[array.length-ri];
			ri = 0;
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				if (ri < remove.length) {
					if (remove[ri] != i) {
						ret[j++] = array[i];
					}
					else {
						ri++;
					}
				}
				else {
					ret[j++] = array[i];
				}
			}
		}
		else {
			ret = array;
		}
		return ret;
	}
	
	/**
	 *@return true if set1 is a subset of set2
	 */
	public static final boolean isSubset(int[] set1, int[] set2) {
		return (difference(set1, set2).length == 0);
	}
	
	/**
	 *@return true if set1 is a subset of set2
	 */
	public static final boolean isSubset(float[] set1, float[] set2) {
		return (difference(set1, set2).length == 0);
	}
	
	/**
	 *@return true if set1 is a subset of set2
	 */
	public static final boolean isSubset(double[] set1, double[] set2) {
		return (difference(set1, set2).length == 0);
	}
	
	/**
	 *@return true if set1 is a subset of set2
	 */
	public static final boolean isSubset(char[] set1, char[] set2) {
		return (difference(set1, set2).length == 0);
	}
	
	/**
	 *@return true if set1 is a subset of set2
	 */
	public static final boolean isSubset(Object[] set1, Object[] set2) {
		return (difference(set1, set2).length == 0);
	}
}
