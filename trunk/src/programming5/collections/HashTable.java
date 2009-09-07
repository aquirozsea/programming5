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
import java.util.Map;

/**
 * This extension of a java Hashtable implements the programming5 PMap interface.
 * @see programming5.collections.PMap
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class HashTable<E, D> extends Hashtable<E, D> implements PMap<E, D> {

    public HashTable() {
        super();
    }

    public HashTable(Map<? extends E, ? extends D> baseMap) {
        super(baseMap);
    }

    public HashTable(int initialSize) {
        super(initialSize);
    }

    public HashTable(int initialSize, float loadFactor) {
        super(initialSize, loadFactor);
    }

    /**
     * Implementation of the safeGet method from the PMap interface.
     * @param key the search key
     * @param defaultValue an initializer or default value to be associated with the key in case no value is
     * associated with the key in the map already
     * @return this.get(key) if it is not null; otherwise, defaultValue
     * @see programming5.collections.PMap#safeGet(java.lang.Object, java.lang.Object)
     */
    @Override
    public D safeGet(E key, D defaultValue) {
        D ret = this.get(key);
        if (ret == null) {
            this.put(key, defaultValue);
            ret = defaultValue;
        }
        return ret;
    }

    /**
     * Implementation of the safePut method of the PMap interface.
     * @param key the tentative insertion key
     * @param value the value to insert
     * @param keyGenerator the generator to be used for alternative keys in case of collisions
     * @return the key actually used to insert the value
     * @see programming5.collections.PMap#safePut(java.lang.Object, java.lang.Object, programming5.collections.MapKeyGenerator) 
     */
    @Override
    public E safePut(E key, D value, MapKeyGenerator<E> keyGenerator) {
        E retKey = key;
        while (this.get(retKey) != null) {
            retKey = keyGenerator.generateKey();
        }
        this.put(retKey, value);
        return retKey;
    }

    /**
     * Implementation of the randomPut method of the PMap interface
     * @param value the value to insert
     * @param keyGenerator the generator to be used for generating the key to use for the insertion
     * @return the key used to insert the value
     * @see programming5.collections.PMap#randomPut(java.lang.Object, programming5.collections.MapKeyGenerator) 
     */
    @Override
    public E randomPut(D value, MapKeyGenerator<E> keyGenerator) {
        E ret = keyGenerator.generateKey();
        while (this.get(ret) != null) {
            ret = keyGenerator.generateKey();
        }
        this.put(ret, value);
        return ret;
    }

}
