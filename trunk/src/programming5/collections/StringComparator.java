/*
 * StringComparator.java
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

package programming5.collections;

import java.util.Comparator;

/**
 *A multi-criterium comparator class for Strings. It uses an enumeration of modes, which can be set at initialization and 
 *changed afterwards, to determine which criterium to use.
 *
 *Currently, the class supports:
 *- Lexicographical comparison, which uses the String class' compareTo method. 
 *- Comparison by length, where shorter strings precede longer ones (compare method returns the difference in length).
 *
 *@author Andrés Quiroz Hernández
 *@version 6.0
 */
public class StringComparator implements Comparator<String> {
	
	/**
	 *Each mode implements its own compare method.
	 */
	public static enum CompareMode {
		LEXGRAPH {int compare(String s1, String s2) {return s1.compareTo(s2);}},
		LENGTH {int compare(String s1, String s2) {return s1.length() - s2.length();}};
		
		abstract int compare(String s1, String s2);
	}
	
	protected CompareMode mode;
	
	/**
         *Creates a new string comparator that uses lexicographical comparison
         */
        public StringComparator() {
		mode = CompareMode.LEXGRAPH;
	}

	/**
         *Creates a new string comparator that uses the given compare mode
         */
        public StringComparator(CompareMode cm) {
		mode = cm;
	}

	/**
         *@return a comparison value of the two strings, depending on the current compare mode
         */
        public int compare(String s1, String s2) {
		return mode.compare(s1, s2);
	}

	/**
         *Changes the compare mode for all subsequent calls to compare.
         */
        public void setCompareMode(CompareMode cm) {
		mode = cm;
	}	
	
}
