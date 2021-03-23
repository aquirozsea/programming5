/*
 * ListMap.java
 *
 * Copyright 2012 Andres Quiroz Hernandez
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A list map is a wrapper for a hashtable of key, list pairs (each key refers to a list of related values).
 * @author andresqh
 * @version 6.0
 * TODO: Implement Map interface?
 */
public class ListMap<K, V> extends CollectionMap<K, V> {

    public ListMap() {
        super();
    }

    public ListMap(Map<? extends K, ? extends List<V>> baseMap) {
        super(baseMap);
    }

    public ListMap(int initialSize) {
        super(initialSize);
    }

    public ListMap(int initialSize, float loadFactor) {
        super(initialSize, loadFactor);
    }
    
    @Override
    protected Collection<V> newCollection() {
        return new ArrayList<V>();
    }


    /**
     * Convenience method that saves having to cast the return value from CollectionMap#getCollection
     * @return the list associated with the given key, or null if no elements for that key have been added
     */
    public List<V> getList(K listKey) {
        return (List<V>) this.getCollection(listKey);
    }

    /**
     * @return the element at the given index in the list associated with the given key
     * @throws IndexOutOfBoundsException if there is no such element (or list)
     */
    public V getListElement(K listKey, int listIndex) throws IndexOutOfBoundsException {
        V ret = null;
        try {
            return ((List<V>) baseTable.get(listKey)).get(listIndex);
        }
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("ListMap: Could not get element from list: List does not exist for given key");
        }
    }

}
