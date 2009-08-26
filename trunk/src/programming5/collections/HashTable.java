/*
 * HashTable.java
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

import java.util.Hashtable;

/**
 * This extension of a java Hashtable implements the programming5 PMap interface to provide a method that
 * passes a default value to the get method in case the return value of the original get method would have
 * been null. The use of this method avoids having to test the result of the get method for a null value and
 * automatically inserts the default value with the given key.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class HashTable<E, D> extends Hashtable<E, D> implements PMap<E, D> {

    /**
     * Implementation of the safeGet method from the PMap interface.
     * @param key the search key
     * @param defaultValue an initializer or default value to be associated with the key in case no value is
     * associated with the key in the map already
     * @return this.get(key) if it is not null; otherwise, defaultValue
     * @see programming5.collections.PMap#safeGet(java.lang.Object, java.lang.Object)
     */
    public D safeGet(E key, D defaultValue) {
        D ret = this.get(key);
        if (ret == null) {
            this.put(key, defaultValue);
            ret = defaultValue;
        }
        return ret;
    }

}
