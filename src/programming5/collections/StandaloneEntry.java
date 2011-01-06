/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.collections;

import java.util.Map.Entry;

/**
 *
 * @author andresqh
 */
public class StandaloneEntry<K, V> implements Entry<K, V> {

    protected final K key;
    protected V value;

    public StandaloneEntry(K myKey) {
        key = myKey;
    }

    public StandaloneEntry(K myKey, V myValue) {
        key = myKey;
        value = myValue;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

}
