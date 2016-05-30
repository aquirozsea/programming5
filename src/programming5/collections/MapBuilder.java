/*
 * MultiList.java
 *
 * Copyright 2016 Andres Quiroz Hernandez
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a shortcut for creating and filling a map object inline
 */
public class MapBuilder<K, V> {

    protected Map<K, V> map;

    protected MapBuilder() {
        map = new HashMap<>();
    }

    protected MapBuilder(Map<K, V> seedMap) {
        map = seedMap;
    }

    /**
     * Puts a key value pair in the map being built, returning a reference to the map builder to continue building
     * @param key the key object
     * @param value the value object
     * @return this map builder
     */
    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    /**
     * Puts contents of the given map into the map being built, overwriting any keys already present in the map (as in
     * the regular {@link Map#putAll(Map)}).
     * @param other another map containing the key/value pairs to put into the map being built
     * @return this map builder
     */
    public MapBuilder<K, V> putAll(Map<? extends K, ? extends V> other) {
        map.putAll(other);
        return this;
    }

    /**
     * @return the reference to the constructed map with any entries from calls to put
     */
    public Map<K, V> get() {
        return map;
    }

    /**
     * Can be used to create a map from a reference key and value. This method is different from {@link Collections#singletonMap(Object, Object)}
     * in that the constructed map is a hash map that may be modified.
     * @param firstKey the key object
     * @param firstValue the value object
     * @return a MapBuilder reference, which can be further operated upon
     */
    public static <K, V> MapBuilder<K, V> from(K firstKey, V firstValue) {
        return new MapBuilder<K, V>().put(firstKey, firstValue);
    }

    /**
     * Can be used to create a map from a reference key and value. Equivalent to {@link #from(Object, Object)}, but added
     * because of clearer semantics.
     * @param firstKey the key object
     * @param firstValue the value object
     * @return a MapBuilder reference, which can be further operated upon
     */
    public static <K, V> MapBuilder<K, V> start(K firstKey, V firstValue) {
        return new MapBuilder<K, V>().put(firstKey, firstValue);
    }

    /**
     * Can be used to populate a seed map inline. E.g. MapBuilder.in(new HashMap<Integer, String>()).put(1, "value")
     * @param seedMap the map object that the map builder will operate upon
     * @return a MapBuilder reference, which can be further operated upon
     */
    public static <K, V> MapBuilder<K, V> in(Map<K, V> seedMap) {
        return new MapBuilder<>(seedMap);
    }
}
