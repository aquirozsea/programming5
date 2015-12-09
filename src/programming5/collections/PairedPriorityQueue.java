package programming5.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by andres on 12/9/15.
 */
public class PairedPriorityQueue<K, V> {

    protected PriorityQueue<EntryObject<K, V>> queue;
    protected Iterator<V> queueIterator = null;

    public PairedPriorityQueue() {
        queue = new PriorityQueue<>(new EntryComparator());
    }

    public PairedPriorityQueue(int initialCapacity) {
        queue = new PriorityQueue<>(initialCapacity, new EntryComparator());
    }

    public PairedPriorityQueue(Comparator<K> keyComparator) {
        queue = new PriorityQueue<>(new EntryComparator(keyComparator));
    }

    public PairedPriorityQueue(int initialCapacity, Comparator<K> keyComparator) {
        queue = new PriorityQueue<>(initialCapacity, new EntryComparator(keyComparator));
    }

    public boolean add(K key, V value) {
        resetIterator();
        return queue.add(new EntryObject<>(key, value));
    }

    public void clear() {
        queue.clear();
        resetIterator();
    }

    public Iterator<V> iterator() {
        if (queueIterator == null) {
            final Iterator<EntryObject<K, V>> baseIterator = queue.iterator();
            queueIterator = new Iterator<V>() {
                @Override
                public boolean hasNext() {
                    return baseIterator.hasNext();
                }

                @Override
                public V next() {
                    return baseIterator.next().getValue();
                }
            };
        }
        return queueIterator;
    }

    public V peek() {
        return queue.peek().getValue();
    }

    public V poll() {
        resetIterator();
        return queue.poll().getValue();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Object[] toArray() {
        Object[] baseArray = queue.toArray();
        for (int i = 0; i < baseArray.length; i++) {
            baseArray[i] = ((EntryObject) baseArray[i]).getValue();
        }
        return baseArray;
    }

    public V[] toArray(V[] destArray) {
        Object[] baseArray = this.toArray();
        for (int i = 0; i < destArray.length; i++) {
            destArray[i] = (V) baseArray[i];
        }
        return destArray;
    }

    public void resetIterator() {
        if (queueIterator != null) {
            queueIterator = null;
        }
    }

    protected class EntryComparator implements Comparator<EntryObject<K, V>> {

        protected Comparator<K> keyComparator;

        public EntryComparator() {
            keyComparator = new Comparator<K>() {
                @Override
                public int compare(K o1, K o2) {
                    return ((Comparable<K>) o1).compareTo(o2);
                }
            };
        }

        public EntryComparator(Comparator<K> myKeyComparator) {
            keyComparator = myKeyComparator;
        }

        @Override
        public int compare(EntryObject<K, V> o1, EntryObject<K, V> o2) {
            return keyComparator.compare(o1.getKey(), o2.getKey());
        }
    }
}
