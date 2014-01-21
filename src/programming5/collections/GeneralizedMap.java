/*
 * GeneralizedMap.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author andresqh
 */
public class GeneralizedMap<K, V> implements PMap<K, V> {

    protected HashTable<K, GeneralizedMap<K, V>> mapTable = new HashTable();
    protected HashTable<K, V> endTable = new HashTable();

    private static final String separator = ":";

    public V safeGet(K key, V defaultValue) {
        return endTableGet(key, defaultValue);
    }

    public V safeGetList(List<K> keys, V defaultValue) {
        // Edge cases
        if (keys == null) return defaultValue;
        if (keys.isEmpty()) return defaultValue;
        // Have keys
        if (keys.size() == 1) {
            return this.safeGet(keys.get(0), defaultValue);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys.get(0));
            for (int i = 1; i < keys.size()-1; i++) {
                aux = aux.getMap(keys.get(i));
            }
            return aux.safeGet(keys.get(keys.size()-1), defaultValue);
        }
    }

    public V safeGetArray(V defaultValue, K... keys) {
        // Edge cases
        if (keys == null) return defaultValue;
        if (keys.length == 0) return defaultValue;
        // Have keys
        if (keys.length == 1) {
            return this.safeGet(keys[0], defaultValue);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys[0]);
            for (int i = 1; i < keys.length-1; i++) {
                aux = aux.getMap(keys[i]);
            }
            return aux.safeGet(keys[keys.length-1], defaultValue);
        }
    }

    public K safePut(K key, V value, MapKeyGenerator<K> keyGenerator) {
        return endTableSafePut(key, value, keyGenerator);
    }

    public K safePutArray(V value, MapKeyGenerator<K> keyGenerator, K... keys) {
        // Edge cases
        if (keys == null) return this.safePut(keyGenerator.generateKey(), value, keyGenerator);
        if (keys.length == 0) return this.safePut(keyGenerator.generateKey(), value, keyGenerator);
        // Have keys
        if (keys.length == 1) {
            return this.safePut(keys[0], value, keyGenerator);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys[0]);
            for (int i = 1; i < keys.length-1; i++) {
                aux = aux.getMap(keys[i]);
            }
            return aux.safePut(keys[keys.length-1], value, keyGenerator);
        }
    }

    public K safePutMap(GeneralizedMap<K, V> gmap, MapKeyGenerator<K> keyGenerator, K... keys) {
        // Edge cases
        if (keys == null) return mapTableSafePut(keyGenerator.generateKey(), gmap, keyGenerator);
        if (keys.length == 0) return mapTableSafePut(keyGenerator.generateKey(), gmap, keyGenerator);
        // Have keys
        if (keys.length == 1) {
            return mapTableSafePut(keys[0], gmap, keyGenerator);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys[0]);
            for (int i = 1; i < keys.length-1; i++) {
                aux = aux.getMap(keys[i]);
            }
            return aux.safePutMap(gmap, keyGenerator, keys[keys.length-1]);
        }
    }

    @Override
    public Map<K, K> safePutAll(Map<? extends K, ? extends V> otherMap, MapKeyGenerator<K> keyGenerator) {
        Map<K, K> changedKeys = new HashMap<K, K>();
        for (Entry<? extends K, ? extends V> otherEntry : otherMap.entrySet()) {
            K newKey = this.safePut(otherEntry.getKey(), otherEntry.getValue(), keyGenerator);
            if (!newKey.equals(otherEntry.getKey())) {
                changedKeys.put(otherEntry.getKey(), newKey);
            }
        }
        return changedKeys;
    }

    public K randomPut(V value, MapKeyGenerator<K> keyGenerator) {
        return this.safePut(keyGenerator.generateKey(), value, keyGenerator);
    }

    public K randomPutMap(GeneralizedMap<K, V> gmap, MapKeyGenerator<K> keyGenerator) {
        return this.safePutMap(gmap, keyGenerator, keyGenerator.generateKey());
    }

    /**
     * @return the total number of elements on the first level of the generalized map (end values and embedded maps are counted
     * equally as elements, but elements of embedded maps are not counted)
     */
    public int size() {
        return mapTable.size() + endTable.size();
    }

    /**
     * @return the total number of elements of the generalized map and all embedded maps recursively (embedded maps are not
     * counted as elements)
     */
    public int numPrimitiveElements() {
        int size = endTable.size();
        for (GeneralizedMap<K, V> embeddedMap : mapTable.values()) {
            size += embeddedMap.numPrimitiveElements();
        }
        return size;
    }

    public boolean isEmpty() {
        boolean empty = endTable.isEmpty();
        if (empty) {
            for (GeneralizedMap<K, V> embedded : mapTable.values()) {
                empty = embedded.isEmpty();
                if (!empty) {
                    break;
                }
            }
        }
        return empty;
    }

    public boolean containsKey(Object key) {
        boolean contains = endTable.containsKey(key);
        if (!contains) {
            for (GeneralizedMap<K, V> embedded : mapTable.values()) {
                contains = embedded.containsKey(key);
                if (contains) {
                    break;
                }
            }
        }
        return contains;
    }

    public boolean containsValue(Object value) {
        boolean contains = endTable.containsValue(value);
        if (!contains) {
            for (GeneralizedMap<K, V> embedded : mapTable.values()) {
                contains = embedded.containsValue(value);
                if (contains) {
                    break;
                }
            }
        }
        return contains;
    }

    public V get(Object key) {
        return endTableGet((K) key, null);
    }

    public V getList(List<K> keys) {
        // Edge cases
        if (keys == null) return null;
        if (keys.isEmpty()) return null;
        // Have keys
        if (keys.size() == 1) {
            return this.get(keys.get(0));
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys.get(0));
            for (int i = 1; i < keys.size()-1; i++) {
                aux = aux.getMap(keys.get(i));
            }
            return aux.get(keys.get(keys.size()-1));
        }
    }

    public GeneralizedMap<K, V> getMap(K... keys) {
        // Edge cases
        if (keys == null) return new GeneralizedMap<K, V>();
        if (keys.length == 0) return new GeneralizedMap<K, V>();
        // Have keys
        if (keys.length == 1) {
            return mapTableGet(keys[0]);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys[0]);
            for (int i = 1; i < keys.length-1; i++) {
                aux = aux.getMap(keys[i]);
            }
            return aux.getMap(keys[keys.length-1]);
        }
    }

    public GeneralizedMap<K, V> getMapList(List<K> keys) {
        // Edge cases
        if (keys == null) return new GeneralizedMap<K, V>();
        if (keys.isEmpty()) return new GeneralizedMap<K, V>();
        // Have keys
        if (keys.size() == 1) {
            return mapTableGet(keys.get(0));
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys.get(0));
            for (int i = 1; i < keys.size()-1; i++) {
                aux = aux.getMap(keys.get(i));
            }
            return aux.getMap(keys.get(keys.size()-1));
        }
    }

    public V put(K key, V value) {
        return endTablePut(key, value);
    }

    public V putArray(V value, K... keys) {
        // Edge cases
        if (keys == null) return null;
        if (keys.length == 0) return null;
        // Have keys
        if (keys.length == 1) {
            return this.put(keys[0], value);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys[0]);
            for (int i = 1; i < keys.length-1; i++) {
                aux = aux.getMap(keys[i]);
            }
            return aux.put(keys[keys.length-1], value);
        }

    }

    // TODO: put with List<K>?

    public Object putMap(K key, GeneralizedMap<K, V> gmap) {
        return mapTablePut(key, gmap);
    }

    public Object putMapArray(GeneralizedMap<K, V> gmap, K... keys) {
        // Edge cases
        if (keys == null) return null;
        if (keys.length == 0) return null;
        // Have keys
        if (keys.length == 1) {
            return this.putMap(keys[0], gmap);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys[0]);
            for (int i = 1; i < keys.length-1; i++) {
                aux = aux.getMap(keys[i]);
            }
            return aux.putMap(keys[keys.length-1], gmap);
        }
    }

    // Will not remove a map; use generalizedRemove instead
    public V remove(Object key) {
        if (key instanceof String) {
            String sKey = (String) key;
            if (sKey.contains(separator)) { // Compound key
                int objectKeySeparator = sKey.lastIndexOf(separator);
                String mapKey = sKey.substring(0, objectKeySeparator);
                String objectKey = sKey.substring(objectKeySeparator + 1);
                return getMap((K) mapKey).remove(objectKey);
            }
        }
        return endTable.remove(key);
    }

    public boolean generalizedRemove(K... keys) {
        // Edge cases
        if (keys == null) return false;
        if (keys.length == 0) return false;
        // Have keys
        if (keys.length == 1) {
            return generalizedTableRemove(keys[0]);
        }
        else {
            GeneralizedMap<K, V> aux = this.getMap(keys[0]);
            for (int i = 1; i < keys.length-1; i++) {
                aux = aux.getMap(keys[i]);
            }
            return aux.generalizedRemove(keys[keys.length-1]);
        }
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        mapTable.clear();
        endTable.clear();
    }

    // Non-recursive, even for compound strings
    public boolean isMap(K key) {
        return mapTable.containsKey(key);
    }

    // Only first level keys, for both primitive elements and embedded maps
    public Set<K> keySet() {
        Set<K> keySet = mapTable.keySet();
        keySet.addAll(endTable.keySet());
        return keySet;
    }

    public Set<List<K>> compoundKeySet() {
        Set<List<K>> keySet = new HashSet<List<K>>();
        for (K endKey : endTable.keySet()) {
            List<K> singleList = new ArrayList<K>();
            singleList.add(endKey);
            keySet.add(singleList);
        }
        for (K mapKey : mapTable.keySet()) {
            List<K> compoundList = new ArrayList<K>();
            compoundList.add(mapKey);
            GeneralizedMap<K, V> embedded = mapTable.get(mapKey);
            for (List<K> compoundKey : embedded.compoundKeySet()) {
                compoundList.addAll(compoundKey);   // TODO: Check the order is correct
            }
            keySet.add(compoundList);
        }
        return keySet;
    }

    // Recursive
    public Collection<V> values() {
        Set<V> valueSet = new HashSet<V>();
        valueSet.addAll(endTable.values());
        for (GeneralizedMap<K, V> map : mapTable.values()) {
            valueSet.addAll(map.values());
        }
        return valueSet;
    }

    // A peculiar implementation: May return multiple entries for a single key, if a map is associated with that key, and, in 
    // that case, those entries contain the values reachable with that key. The path to the elements is lost, though, if there 
    // are multiple embedded maps.
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<Entry<K, V>>();
        entrySet.addAll(endTable.entrySet());
        for (Entry<K, GeneralizedMap<K, V>> mapEntry : mapTable.entrySet()) {
            for (V value : mapEntry.getValue().values()) {
                entrySet.add(new EntryObject<K, V>(mapEntry.getKey(), value));
            }
        }
        return entrySet;
    }

    public Set<Entry<List<K>, V>> compoundEntrySet() {
        Set<Entry<List<K>, V>> entrySet = new HashSet<Entry<List<K>, V>>();
        for (Entry<K, V> endEntry : endTable.entrySet()) {
            List<K> singleKey = new ArrayList<K>();
            singleKey.add(endEntry.getKey());
            entrySet.add(new EntryObject<List<K>, V>(singleKey, endEntry.getValue()));
        }
        for (Entry<K, GeneralizedMap<K, V>> mapEntry : mapTable.entrySet()) {
            List<K> compoundKey = new ArrayList<K>();
            compoundKey.add(mapEntry.getKey());
            for (Entry<List<K>, V> embeddedEntry : mapEntry.getValue().compoundEntrySet()) {
                compoundKey.addAll(embeddedEntry.getKey());
                entrySet.add(new EntryObject<List<K>, V>(compoundKey, embeddedEntry.getValue()));
            }
        }
        return entrySet;
    }

    private GeneralizedMap<K, V> mapTableGet(K key) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator);
            if (stringKeys.length > 1) {
                GeneralizedMap<K, V> aux = mapTable.safeGet((K) stringKeys[0], new GeneralizedMap<K, V>());
                for (int i = 1; i < stringKeys.length; i++) {
                    aux = aux.mapTable.safeGet((K) stringKeys[i], new GeneralizedMap<K, V>());
                }
                return aux;
            }
        }
        return mapTable.safeGet(key, new GeneralizedMap<K, V>());
    }

    private V endTableGet(K key, V defaultValue) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator);
            if (stringKeys.length > 1) {
                GeneralizedMap<K, V> aux = mapTable.safeGet((K) stringKeys[0], new GeneralizedMap<K, V>());
                for (int i = 1; i < stringKeys.length-1; i++) {
                    aux = aux.mapTable.safeGet((K) stringKeys[i], new GeneralizedMap<K, V>());
                }
                return aux.endTable.safeGet((K) stringKeys[stringKeys.length-1], defaultValue);
            }
        }
        return endTable.safeGet(key, defaultValue);
    }
    
    private V endTablePut(K key, V value) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator);
            if (stringKeys.length > 1) {
                GeneralizedMap<K, V> aux = mapTable.safeGet((K) stringKeys[0], new GeneralizedMap<K, V>());
                for (int i = 1; i < stringKeys.length-1; i++) {
                    aux = aux.mapTable.safeGet((K) stringKeys[i], new GeneralizedMap<K, V>());
                }
                return aux.endTable.put((K) stringKeys[stringKeys.length-1], value);
            }
        }
        return endTable.put(key, value);
    }

    private K endTableSafePut(K key, V value, MapKeyGenerator<K> keyGenerator) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator);
            if (stringKeys.length > 1) {
                GeneralizedMap<K, V> aux = mapTable.safeGet((K) stringKeys[0], new GeneralizedMap<K, V>());
                for (int i = 1; i < stringKeys.length-1; i++) {
                    aux = aux.mapTable.safeGet((K) stringKeys[i], new GeneralizedMap<K, V>());
                }
                return aux.endTable.safePut((K) stringKeys[stringKeys.length-1], value, keyGenerator);
            }
        }
        return endTable.safePut(key, value, keyGenerator);
    }

    // Fulfills the regular map put contract, but the return value could either be a final element or an embedded map
    private Object mapTablePut(K key, GeneralizedMap<K, V> gmap) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator);
            if (stringKeys.length > 1) {
                GeneralizedMap<K, V> aux = mapTable.safeGet((K) stringKeys[0], new GeneralizedMap<K, V>());
                for (int i = 1; i < stringKeys.length-1; i++) {
                    aux = aux.mapTable.safeGet((K) stringKeys[i], new GeneralizedMap<K, V>());
                }
                return aux.mapTable.put((K) stringKeys[stringKeys.length-1], gmap);
            }
        }
        return mapTable.put(key, gmap);
    }

    private K mapTableSafePut(K key, GeneralizedMap<K, V> gmap, MapKeyGenerator<K> keyGenerator) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator);
            if (stringKeys.length > 1) {
                GeneralizedMap<K, V> aux = mapTable.safeGet((K) stringKeys[0], new GeneralizedMap<K, V>());
                for (int i = 1; i < stringKeys.length-1; i++) {
                    aux = aux.mapTable.safeGet((K) stringKeys[i], new GeneralizedMap<K, V>());
                }
                return aux.mapTable.safePut((K) stringKeys[stringKeys.length-1], gmap, keyGenerator);
            }
        }
        return mapTable.safePut(key, gmap, keyGenerator);
    }

    private boolean generalizedTableRemove(K key) {
        if (key instanceof String) {
            String sKey = (String) key;
            if (sKey.contains(separator)) { // Compound key
                int objectKeySeparator = sKey.lastIndexOf(separator);
                String mapKey = sKey.substring(0, objectKeySeparator);
                String objectKey = sKey.substring(objectKeySeparator + 1);
                return getMap((K) mapKey).generalizedTableRemove((K) objectKey);
            }
        }
        boolean removed = (endTable.remove(key) != null);
        removed |= (mapTable.remove(key) != null);
        return removed;
    }

}
