/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import programming5.arrays.RandomOrderGenerator;

/**
 * Hash set that keeps the last inserted of equal versions. Useful when items have extra data not used
 * in the equals comparison.
 * @author andresqh
 */
public class CacheSet<T> extends HashSet<T> {
    
    public CacheSet() {
        super();
    }

    /**
     *Creates a new set out of the collection elements. Any repeated elements in the collection will only be inserted once.
     */
    public CacheSet(Collection<? extends T> c) {
        super(c);
    }

    /**
     *Creates an empty set initialized to the given size
     */
    public CacheSet(int size) {
        super(size);
    }

    /**
     *Creates an empty set initialized to the given size with the given growth percentage
     */
    public CacheSet(int size, float pctGrowth) {
        super(size, pctGrowth);
    }

    @Override
    public boolean add(T e) {
        this.remove(e);
        return super.add(e);
    }

}
