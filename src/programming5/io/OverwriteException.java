/*
 * OverwriteException.java
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

package programming5.io;

import programming5.arrays.ArrayOperations;

import java.io.IOException;

/**
 * Exception that applies to copying directories or files with the FileHandler class.
 * @see programming5.io.FileHandler
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class OverwriteException extends IOException {
	
	String[] sources = null;
	
	public OverwriteException(String sourcePath) {
		super("File exists at destination");
		sources = ArrayOperations.addElement(sourcePath, sources);
	}	
	
	public OverwriteException(String[] sourcePaths) {
		super("Files exist at destination");
		sources = sourcePaths.clone();
	}
	
	/**
         *@return the source paths that caused the exception
         */
        public String[] getSources() {
		return sources;
	}
	
}
