package programming5.collections;

import java.util.*;

/**
 * O(logN) changeKey, amortized O(1) peek (O(ilogN) worst case, with i = number of changed keys), amortized O(logN) poll (O(ilogN) worst case)
 */
public class DynamicPriorityQueue<K, V> extends PairedPriorityQueue<K, V> {

    protected HashMap<V, K> objectLookup;

    public DynamicPriorityQueue() {
        super();
        objectLookup = new HashMap<>();
    }

    public DynamicPriorityQueue(int initialCapacity) {
        super(initialCapacity);
        objectLookup = new HashMap<>(initialCapacity);
    }

    public DynamicPriorityQueue(Comparator<K> keyComparator) {
        super(keyComparator);
        objectLookup = new HashMap<>();
    }

    public DynamicPriorityQueue(int initialCapacity, Comparator<K> keyComparator) {
        super(initialCapacity, keyComparator);
        objectLookup = new HashMap<>(initialCapacity);
    }

    @Override
    /**
     * Adds the given value with the given key, or changes the key of the given value if it exists in the queue
     */
    public boolean add(K key, V value) {
        objectLookup.put(value, key);
        return super.add(key, value);
    }

    /**
     * Same as add(newKey, object)
     */
    public void changeKey(V object, K newKey) {
        objectLookup.put(object, newKey);
        super.add(newKey, object);
    }

    public K getPriorityKey(V object) {
        return objectLookup.get(object);
    }

    @Override
    public int size() {
        return objectLookup.size();
    }

    @Override
    public V poll() {
        resetIterator();
        return iterativePoll();
    }

    @Override
    public V peek() {
        return iterativePeek();
    }

    @Override
    public Iterator<V> iterator() {
        if (queueIterator == null) {
            queueIterator = objectLookup.keySet().iterator();
        }
        return queueIterator;
    }

    @Override
    public boolean isEmpty() {
        return objectLookup.isEmpty();
    }

    @Override
    public void clear() {
        super.clear();
        objectLookup.clear();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[objectLookup.size()];
        int arrayIndex = 0;
        for (V value : objectLookup.keySet()) {
            array[arrayIndex++] = value;
        }
        return array;
    }

    @Override
    public V[] toArray(V[] destArray) {
        int arrayIndex = 0;
        for (V value : objectLookup.keySet()) {
            if (arrayIndex < destArray.length) {
                destArray[arrayIndex++] = value;
            }
            else {
                break;
            }
        }
        return destArray;
    }

    /**
     * Deletes duplicate entries in the base priority queue left after changeKey operations (useful to maintain expected performance of amortized operations by controlling when worst case is incurred)
     */
    public void purge() {
        resetIterator();
        PriorityQueue<EntryObject<K, V>> auxQueue = new PriorityQueue<>();
        for (Map.Entry<V, K> entry : objectLookup.entrySet()) {
            auxQueue.add(new EntryObject<K, V>(entry.getValue(), entry.getKey()));
        }
        queue = auxQueue;
    }

    private V iterativePoll() {
        Object auxCast = queue.comparator();
        Comparator<K> keyComparator = ((EntryComparator) auxCast).keyComparator;
        EntryObject<K, V> entry = queue.poll();
        while (keyComparator.compare(entry.getKey(), objectLookup.get(entry.getValue())) != 0) {
            entry = queue.poll();
        }
        objectLookup.remove(entry.getValue());
        return entry.getValue();
    }

    private V iterativePeek() {
        Object auxCast = queue.comparator();
        Comparator<K> keyComparator = ((EntryComparator) auxCast).keyComparator;
        EntryObject<K, V> entry = queue.peek();
        while (keyComparator.compare(entry.getKey(), objectLookup.get(entry.getValue())) != 0) {
            queue.poll();
            entry = queue.peek();
        }
        return entry.getValue();
    }

}
