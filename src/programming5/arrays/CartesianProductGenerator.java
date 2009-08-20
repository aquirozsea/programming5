/*
 * CartesianProductGenerator.java
 *
 * Copyright 2009 Andres Quiroz Hernandez
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
 * This class generates a cartesian product of a set of arrays. It is useful when the number of arrays for
 * which the product is required is only known at runtime, so that the product cannot be accomplished with a
 * nested loop of foreach statements.
 * <p>It can be used like an enumeration, going through one combination tuple at a time,
 * until the combinations are exhausted.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class CartesianProductGenerator {

    protected TupleIndexGenerator tupleGenerator;
    protected Object[][] sets;

    /**
	 *Initializes the generator with the arrays to be crossed.
	 *@param arraySets the original arrays, each of which is a set of elements that will be used for the
     * cartesian product
	 */
	public CartesianProductGenerator(Object[]... arraySets) {
        sets = arraySets;
        int[] setSizes = new int[sets.length];
        for (int i = 0; i < sets.length; i++) {
            setSizes[i] = sets[i].length;
        }
        tupleGenerator = new TupleIndexGenerator(setSizes);
    }

    /**
	 *@return whether more combinations remain for the set elements
	 */
	public boolean moreTuples() {
		return tupleGenerator.moreTuples();
	}

    /**
	 *@return an array tuple with a new combination of elements from the cartesian product of the given sets.
	 *When all the combinations are exhausted, it will return null.
	 */
	public Object[] nextTuple() {
        Object[] ret = null;
        int[] indexTuple = tupleGenerator.nextTuple();
        if (indexTuple != null) {
            ret = new Object[sets.length];
            for (int i = 0; i < indexTuple.length; i++) {
                ret[i] = sets[i][indexTuple[i]];
            }
        }
        return ret;
    }

	/**
	 *Restarts the object to go through the same combinations from the beginning
	 */
	 public void restart() {
	 	tupleGenerator.restart();
	 }

	 /**
	  *Restarts the object for a new combination of sets
	  */
	  public void restart(Object[]... arraySets) {
	  	sets = arraySets;
        int[] setSizes = new int[sets.length];
        for (int i = 0; i < sets.length; i++) {
            setSizes[i] = sets[i].length;
        }
        tupleGenerator.restart(setSizes);
	  }

}
