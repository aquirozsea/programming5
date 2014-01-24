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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
    // Only works with key clashes at the first level
    public Map<K, K> safePutAll(Map<? extends K, ? extends V> otherMap, MapKeyGenerator<K> keyGenerator) {
        Map<K, K> changedKeys = new HashMap<K, K>();
        if (otherMap instanceof GeneralizedMap) {
            changedKeys.putAll(this.mapTable.safePutAll(((GeneralizedMap) otherMap).mapTable, keyGenerator));
            changedKeys.putAll(this.endTable.safePutAll(((GeneralizedMap) otherMap).endTable, keyGenerator));
        }
        else {
            changedKeys.putAll(this.endTable.safePutAll(otherMap, keyGenerator));
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

    // Embedded empty tables still contribute to being not empty
    public boolean isEmpty() {
        return endTable.isEmpty() && mapTable.isEmpty();
    }

    public boolean containsKey(Object key) {
        if (key instanceof String) {
            if (((String) key).contains(separator)) {
                String mapKey = ((String) key).substring(0, ((String) key).lastIndexOf(separator));
                String elementKey = ((String) key).substring(((String) key).lastIndexOf(separator) + 1);
                try {
                    return this.getMap((K) mapKey).containsKey(elementKey);
                }
                catch (NullPointerException npe) {
                    return false;
                }
                catch (ClassCastException cce) {
                    // Default to regular object search
                }
            }
        }
        // Default search 
        boolean contains = endTable.containsKey(key) || mapTable.containsKey(key);
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
        try {
            return endTableGet((K) key, null);
        }
        catch (ClassCastException cce) {
            return null;
        }
    }

    public V getArray(K... keys) {
        return safeGetArray(null, keys);
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
        if (keys == null) return mapTableGet(null);
        if (keys.length == 0) return mapTableGet(null);
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
        if (keys == null) return mapTableGet(null);
        if (keys.isEmpty()) return mapTableGet(null);
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
        if (keys == null) throw new IllegalArgumentException("GeneralizedMap: Cannot putArray: No key(s) provided");
        if (keys.length == 0) throw new IllegalArgumentException("GeneralizedMap: Cannot putArray: No key(s) provided");
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

    public GeneralizedMap<K, V> putMap(K key, GeneralizedMap<K, V> gmap) {
        return mapTablePut(key, gmap);
    }

    public GeneralizedMap<K, V> putMapArray(GeneralizedMap<K, V> gmap, K... keys) {
        // Edge cases
        if (keys == null) throw new IllegalArgumentException("GeneralizedMap: Cannot putMapArray: No key(s) provided");;
        if (keys.length == 0) throw new IllegalArgumentException("GeneralizedMap: Cannot putMapArray: No key(s) provided");;
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
        if (keys == null) return generalizedTableRemove(null);
        if (keys.length == 0) return generalizedTableRemove(null);
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

    public void putAll(Map<? extends K, ? extends V> otherMap) {
        if (otherMap instanceof GeneralizedMap) {
            this.mapTable.putAll(((GeneralizedMap) otherMap).mapTable);
            this.endTable.putAll(((GeneralizedMap) otherMap).endTable);
        }
        else {
            this.endTable.putAll(otherMap);
        }
    }

    public void clear() {
        mapTable.clear();
        endTable.clear();
    }

    public boolean isMap(K key) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator, -1);
            if (stringKeys.length > 1) {
                GeneralizedMap<K, V> aux = mapTable.get((K) stringKeys[0]);
                for (int i = 1; i < stringKeys.length-1; i++) {
                    if (aux != null) {
                        aux = aux.mapTable.get((K) stringKeys[i]);
                    }
                    else {
                        break;
                    }
                }
                if (aux != null) {
                    return aux.mapTable.containsKey(stringKeys[stringKeys.length-1]);
                }
                else {
                    return false;
                }
            }
        }
        return mapTable.containsKey(key);
    }

    // Only first level keys, for both primitive elements and embedded maps
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<K>(mapTable.keySet());
        keySet.addAll(endTable.keySet());
        return keySet;
    }

    public Set<List<K>> compoundKeySet() {
        Set<List<K>> keySet = new HashSet<List<K>>();
        for (K endKey : endTable.keySet()) {
            List<K> singleList = new LinkedList<K>();
            singleList.add(endKey);
            keySet.add(singleList);
        }
        for (K mapKey : mapTable.keySet()) {
            GeneralizedMap<K, V> embedded = mapTable.get(mapKey);
            for (List<K> compoundKey : embedded.compoundKeySet()) {
                ((LinkedList) compoundKey).addFirst(mapKey);
                keySet.add(compoundKey);
            }
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
            List<K> singleKey = new LinkedList<K>();
            singleKey.add(endEntry.getKey());
            entrySet.add(new EntryObject<List<K>, V>(singleKey, endEntry.getValue()));
        }
        for (Entry<K, GeneralizedMap<K, V>> mapEntry : mapTable.entrySet()) {
            for (Entry<List<K>, V> embeddedEntry : mapEntry.getValue().compoundEntrySet()) {
                ((LinkedList<K>) embeddedEntry.getKey()).addFirst(mapEntry.getKey());
                entrySet.add(embeddedEntry);
            }
        }
        return entrySet;
    }

    private GeneralizedMap<K, V> mapTableGet(K key) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator, -1);
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
            String[] stringKeys = ((String) key).split(separator, -1);
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
            String[] stringKeys = ((String) key).split(separator, -1);
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
            String[] stringKeys = ((String) key).split(separator, -1);
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

    private GeneralizedMap<K, V> mapTablePut(K key, GeneralizedMap<K, V> gmap) {
        if (key instanceof String) {
            String[] stringKeys = ((String) key).split(separator, -1);
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
            String[] stringKeys = ((String) key).split(separator, -1);
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
