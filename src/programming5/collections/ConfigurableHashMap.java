package programming5.collections;

import java.util.HashMap;
import java.util.Map;
import programming5.concurrent.ConditionVariable;

/**
 * <p>A ConfigurableHashMap is a HashMap that accepts lambda functions to call in place of its get, put, and getOrDefault
 * functions, in order to alter the behavior of particular instances. Thus, users of the class can achieve polymorphism
 * at the instance level without having to extend the original HashMap class.</p>
 * <p>The ConfigurableHashMap class also provides a number of pre-defined settings, for common types of hash map
 * behavior, as follows:</p>
 * <ul>
 *     <li>ALWAYS_PUT: Default behavior (coincides with HashMap put behavior), modifies the value for a key every time
 *     put is called</li>
 *     <li>PUT_ONCE: Only sets a value for a key the first time put is called, ignoring all subsequent calls</li>
 *     <li>READ_ONLY_GET: Default behavior (coincides with HashMap get behavior), does not alter the map contents on a
 *     call to get</li>
 *     <li>GET_AND_REMOVE: Removes the existing value associated with a given key after get for that key is called</li>
 *     <li>PUT_DEFAULT_ON_GET_MISS: Default behavior (differs from HashMap getOrDefault behavior), equivalent to
 *     calling map.put(key, def_value) after map.getOrDefault(key, def_value) returns the def_value (because the map
 *     did not contain the key prior to get</li>
 * </ul>
 */
// TODO: Debug thread safety of recur guard
public class ConfigurableHashMap<K, V> extends HashMap<K, V> {

    protected Putter<K, V> regularPutter = (key, value) -> super.put(key, value);

    protected Putter<K, V> oncePutter = (key, value) -> {
        if (!this.containsKey(key)) {
            super.put(key, value);
        }
        return super.get(key);
    };

    protected Getter<K, V> regularGetter = key -> super.get(key);

    protected Getter<K, V> removeGetter = key -> super.remove(key);

    protected OrDefaultGetter<K, V> regularOrDefaultGetter = (key, value) -> super.getOrDefault(key, value);

    protected OrDefaultGetter<K, V> getOrPutGetter = (key, value) -> {
        if (!this.containsKey(key)) {
            super.put(key, value);
        }
        return super.get(key);
    };

    Putter<K, V> putter = regularPutter;
    Getter<K, V> getter = regularGetter;
    OrDefaultGetter<K, V> orDefaultGetter = getOrPutGetter;

    private ConditionVariable recurGuard = new ConditionVariable();

    public enum Settings {
        ALWAYS_PUT, PUT_ONCE, READ_ONLY_GET, GET_AND_REMOVE, PUT_DEFAULT_ON_GET_MISS
    }

    public ConfigurableHashMap(Settings... settings) {
        super();
        configure(settings);
    }

    public ConfigurableHashMap(int initialCapacity, Settings... settings) {
        super(initialCapacity);
        configure(settings);
    }

    public ConfigurableHashMap(int initialCapacity, int loadFactor, Settings... settings) {
        super(initialCapacity, loadFactor);
        configure(settings);
    }

    public ConfigurableHashMap(Map<? extends K, ? extends V> seedMap, Settings... settings) {
        super(seedMap);
        configure(settings);
    }

    public void configure(Settings... settings) {
        for (Settings setting : settings) {
            switch (setting) {
                case ALWAYS_PUT: putter = regularPutter;
                    break;
                case PUT_ONCE: putter = oncePutter;
                    break;
                case READ_ONLY_GET: {
                    getter = regularGetter;
                    orDefaultGetter = regularOrDefaultGetter;
                    break;
                }
                case GET_AND_REMOVE: {
                    getter = removeGetter;
                    orDefaultGetter = regularOrDefaultGetter;
                    break;
                }
                case PUT_DEFAULT_ON_GET_MISS: orDefaultGetter = getOrPutGetter;
                    break;
            }
        }
    }

    public void setGetter(Getter<K, V> getter) {
        this.getter = getter;
    }

    public void setPutter(Putter<K, V> putter) {
        this.putter = putter;
    }

    public void setOrDefaultGetter(OrDefaultGetter<K, V> orDefaultGetter) {
        this.orDefaultGetter = orDefaultGetter;
    }

    @Override
    public V put(K key, V value) {
        if (recurGuard.isTrue()) {
            recurGuard.reset();
            return super.put(key, value);
        }
        recurGuard.evaluateCondition(true);
        V ret = putter.put(key, value);
        recurGuard.reset();
        return ret;
    }

    @Override
    public V get(Object key) {
        if (recurGuard.isTrue()) {
            recurGuard.reset();
            return super.get(key);
        }
        try {
            recurGuard.evaluateCondition(true);
            V ret = getter.get((K) key);
            recurGuard.reset();
            return ret;
        }
        catch (ClassCastException cce) {
            return null;
        }
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        if (recurGuard.isTrue()) {
            recurGuard.reset();
            return super.getOrDefault(key, defaultValue);
        }
        try {
            recurGuard.evaluateCondition(true);
            V ret = orDefaultGetter.getOrDefault((K) key, defaultValue);
            recurGuard.reset();
            return ret;
        }
        catch (ClassCastException cce) {
            return defaultValue;
        }
    }

    public interface Putter<K, V> {
        V put(K key, V value);
    }

    public interface Getter<K, V> {
        V get(K key);
    }

    public interface OrDefaultGetter<K, V> {
        V getOrDefault(K key, V defaultValue);
    }

}
