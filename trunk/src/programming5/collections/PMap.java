/*
 * PMap.java
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

package programming5.collections;

import java.util.Map;

/**
 * This extension to the Map interface is meant to provide a method that passes a default value to the get
 * method in case the return value of the original get method would have been null. The use of this method
 * avoids having to test the result of the get method for a null value and automatically inserts the default
 * value with the given key.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public interface PMap<E, D> extends Map<E, D> {

    /**
     * Analogous to the get method, but if the key is not found, inserts (with the given key) and returns the 
     * given default value.
     * @param key the search key
     * @param defaultValue an initializer or default value to be associated with the key in case no value is 
     * associated with the key in the map already
     * @return map.get(key) if it is not null; otherwise, defaultValue
     */
    public D safeGet(E key, D defaultValue);

}
