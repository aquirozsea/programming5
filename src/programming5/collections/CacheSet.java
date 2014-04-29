/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.collections;

import java.util.HashSet;

/**
 *
 * @author andresqh
 */
public class CacheSet<T> extends HashSet<T> {

    @Override
    public boolean add(T e) {
        this.remove(e);
        return super.add(e);
    }

}
