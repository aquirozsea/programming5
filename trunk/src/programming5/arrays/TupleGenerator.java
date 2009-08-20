/*
 * TupleGenerator.java
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
 *This class implements a cartesian product of sets by generating the different 
 *combinations among elements. It does not work with the elements themselves, but 
 *rather with the element indices, so that the tuples it returns are integer arrays 
 *that can be used by the calling application to draw a particular combination from 
 *the real sets.
 *<p>It can be used like an enumeration, going through one combination at a time, 
 *until the combinations are exhausted.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 *@deprecated As of jdk1.5.0, the easiest way to implement a cartesian product of arrays or collections is to use nested foreach statements.
 * However, this class has been found to be useful when the number of sets is determined at runtime and thus cannot be coded in nested loops.
 * In the next version of this library the class will be renamed to TupleIndexGenerator to more closely reflect its functionality.
 */
public class TupleGenerator {
	
	private int sets;
	private int[] limits;
	private int[] tuple;
	
	/**
	 *The object will work only with the sizes of the sets to be combined, numbering 
	 *the elements in the arrays returned from 0 to the corresponding set size.
	 *@param setSizes an array with the number of elements of each of the sets to be 
	 *combined; the order specified here will be the order in which the indices will 
	 *be returned for each of the tuples.
	 */
	public TupleGenerator(int[] setSizes) {
		sets = setSizes.length;
		limits = ArrayOperations.replicate(setSizes);
		tuple = new int[sets];
		tuple[0] = -1;
		for (int i = 1; i < sets; i++)
			tuple[i] = 0;
	}
	
	/**
	 *This class will keep track of the progress through the set combinations, as 
	 *nextTuple is called
	 *@return whether more combinations remain for the set elements
	 *@see #nextTuple()
	 */
	private boolean moreTuples() {
		boolean ind = false;
		for (int i = 0; i < sets; i++) {
			if (tuple[i] != limits[i]-1) {
				ind = true;
				break;
			}
		}
		return ind;
	}
	
	/**
	 *Returns an integer array with the positions of the elements in a new combination 
	 *tuple. When all the combinations are exhausted, it will return null.
	 */
	public int[] nextTuple() {
		if (tuple != null) {
			if (moreTuples()) {
				int i = 0;
				tuple[i]++;
				while (tuple[i] == limits[i]) {
					i++;
					tuple[i]++;
				}
				for (int j = 0; j < i; j++)
					tuple[j] = 0;
			}
			else {
				tuple = null;
			}
		}
		return tuple;
	}
	
	/**
	 *Restarts the object to go through the same combinations from the beginning
	 */
	 public void restart() {
	 	tuple = new int[sets];
	 	tuple[0] = -1;
	 	for (int i = 1; i < sets; i++)
	 		tuple[i] = 0;
	 }
	 
	 /**
	  *Restarts the object for a new combination of sets
	  */
	  public void restart(int[] setSizes) {
	  	sets = setSizes.length;
		limits = ArrayOperations.replicate(setSizes);
		restart();
	  }
}