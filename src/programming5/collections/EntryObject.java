/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.collections;

/**
 *
 * @author andresqh
 */
public class EntryObject<K, V> implements java.util.Map.Entry<K, V> {

    protected final K key;
    protected V value;

    public EntryObject(K myKey) {
        key = myKey;
    }

    public EntryObject(K myKey, V myValue) {
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
