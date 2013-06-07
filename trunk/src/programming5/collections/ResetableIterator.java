/*
 * ResetableIterator.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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

import java.util.Iterator;

/**
 * Extension of the iterator interface to allow reseting (starting the iterator from the first element)
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public interface ResetableIterator<T> extends Iterator<T> {

    /**
     * An instance of a resetable iterator must go back to its initial state after a call to reset. That is, the results of hasNext() and 
     * next() following the call to reset must be exactly the same as when the instance was originally created.
     */
    public void reset();

}
