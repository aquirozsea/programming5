/*
 * SetMap.java
 *
 * Copyright 2014 Andres Quiroz Hernandez
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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A collection map, where each collection is a HashSet.
 * @author andresqh
 */
public class SetMap<K, V> extends CollectionMap<K, V> {

    public SetMap() {
        super();
    }

    public SetMap(Map<? extends K, ? extends Collection<V>> baseMap) {
        super(baseMap);
    }

    public SetMap(int initialSize) {
        super(initialSize);
    }

    public SetMap(int initialSize, float loadFactor) {
        super(initialSize, loadFactor);
    }

    @Override
    protected Collection<V> newCollection() {
        return new CacheSet<V>();
    }

    public Set<V> getSet(K setKey) {
        return (Set<V>) this.getCollection(setKey);
    }

}
