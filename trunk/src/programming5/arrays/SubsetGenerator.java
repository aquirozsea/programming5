/*
 * SubsetGenerator.java
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
 *This class provides for the selection of each of the subsets of a set of elements. It 
 *does not work with the elements themselves, but rather with arrays of indices for them,
 *so that the calling application can draw the particular elements from the real set.
 *<p>The nextSet method is the main method for interaction.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 *@see #nextSet()
 */
public class SubsetGenerator {
 	
 	private int elements, group;
 	private double combinations;
 	private int[] set;
 	
 	/**
 	 *The object will work only with the size of the set, numbering its elements from 
 	 *zero to the set size. 
 	 *@param setSize the size of the set to be decomposed
 	 */
 	 public SubsetGenerator(int setSize) {
 	 	elements = setSize;
 	 	combinations = Math.pow(2, elements);
 	 	group = 1;
 	 	set = new int[1];
 	 	set[0] = -1;
 	 }
 	 
 	/**
	 *@return an integer array with the positions of the elements in a new subset; 
	 *when all the subsets are exhausted, it will return null, and keep doing so 
	 *unless the object is restarted.
	 */
	 public int[] nextSet() {
	 	int pi;
	 	if (set != null) {
	 		set[group-1]++;
	 		pi = 1; 
	 		while(set[group-pi] > elements-pi) {
	 			pi++;
	 			if (pi <= group)
	 				set[group-pi]++;
	 			else
	 				break;
	 		}
	 		if (pi <= group) {
	 			for (int pj = group-pi+1; pj < group; pj++)
	 				set[pj] = set[pj-1] + 1;
	 		}
	 		if (set[0] > elements-group) {
	 			group++;
	 			if (group <= elements) {
	 				set = new int[group];
	 				for (int i = 0; i < group; i++)
						set[i] = i;
				}
				else
					set = null;
	 		}
	 	}
	 	int[] ret = null;
	 	if (set != null) {
	 		ret = ArrayOperations.replicate(set);
	 	}
	 	return ret;
	 }
 	 	
 	/**
	 *Restarts the object to go through the same subsets from the beginning
	 */
	 public void restart() {
	 	group = 1;
	 	set = new int[1];
	 	set[0] = -1;
	 }
	 
	 /**
	  *Restarts the object for a new combination of sets
	  */
	 public void restart(int setSize) {
	  	elements = setSize;
 	 	combinations = Math.pow(2, elements);
 	 	group = 1;
 	 	set = new int[1];
 	 	set[0] = -1;
 	 }
 	  
 	 /**
 	  *@return the number of subsets, including the empty set (the empty set can be 
 	  *counted as the last return of the nextSet method, which is null)
 	  */
 	 public double numSubsets() {
 	   	return combinations;
 	 }
}