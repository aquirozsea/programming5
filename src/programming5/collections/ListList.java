/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programming5.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author andresqh
 * @param <T>
 */
public class ListList<T> implements List<List<T>> {
    
    protected List<List<T>> baseList;
    protected int currentIteratorIndex = -1;
    protected Iterator<T> currentIterator = null;
    
    public ListList() {
        baseList = new ArrayList<List<T>>();
    }
    
    public ListList(Collection<? extends List<T>> collection) {
        baseList = new ArrayList<List<T>>(collection);
    } 
    
    public ListList(int initialCapacity) {
        baseList = new ArrayList<List<T>>(initialCapacity);
    }

    public int size() {
        return baseList.size();
    }
    
    public int size(int index) {
        return baseList.get(index).size();
    }
    
    public int totalElements() {
        int total = 0;
        for (List list : baseList) {
            total += list.size();
        }
        return total;
    }

    public boolean isEmpty() {
        return baseList.isEmpty();
    }
    
    public boolean isEmpty(int index) {
        return baseList.get(index).isEmpty();
    }

    public boolean contains(Object o) {
        for (List list : baseList) {
            if (list.contains(o)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(int index, T obj) {
        return baseList.get(index).contains(obj);
    }

    public Iterator<List<T>> iterator() {
        return baseList.listIterator();
    }
    
    public Iterator<T> iterator(int index) {
        return baseList.get(index).listIterator();
    }
    
    public Iterator<T> completeIterator() {
        return new Iterator<T>() {

            public boolean hasNext() {
                if (baseList.isEmpty()) return false;
                if (currentIterator == null) {
                    currentIterator = baseList.get(0).listIterator();
                    currentIteratorIndex = 0;
                }
                if (!currentIterator.hasNext() && currentIteratorIndex < baseList.size() - 1) {
                    currentIterator = baseList.get(++currentIteratorIndex).listIterator();
                }
                return currentIterator.hasNext();
            }

            public T next() {
                if (hasNext()) {
                    return currentIterator.next();
                }
                else {
                    return null;
                }
            }

        };
    }

    public Object[] toArray() {
        Object[] retArray = new Object[totalElements()];
        int index = 0;
        for (List<T> list : baseList) {
            for (T element : list) {
                retArray[index++] = element;
            }
        }
        return retArray;
    }
    
    public Object[] toArray(int index) {
        return baseList.get(index).toArray();
    }

    public <U> U[] toArray(U[] a) {
        int index = 0;
        for (List<T> list : baseList) {
            for (T element : list) {
                if (index < a.length) {
                    a[index++] = (U) element;
                }
                else {
                    return a;
                }
            }
        }
        return a;
    }

    public boolean add(List<T> e) {
        resetIterator();
        return baseList.add(e);
    }
    
    public boolean addToList(int index, T element) {
        resetIterator();
        return baseList.get(index).add(element);
    }
    
    public boolean addToList(int index, List<T> elements) {
        resetIterator();
        return baseList.get(index).addAll(elements);
    }
    
    public boolean startNewList() {
        return add(new ArrayList<T>());
    }
    
    public boolean startNewList(T element) {
        if (startNewList()) {
            return addToList(size() - 1, element);
        }
        else {
            return false;
        }
    }
    
    public boolean addToCurrentList(T element) {
        if (baseList.isEmpty()) {
            return startNewList(element);
        }
        else {
            return addToList(size() - 1, element);
        }
    }
    
    public boolean addToCurrentList(List<T> elements) {
        if (baseList.isEmpty()) {
            startNewList();
        }
        return addToList(size() - 1, elements);
    }

    public boolean remove(Object o) {
        resetIterator();
        if (o instanceof List) {
            return baseList.remove(o);
        }
        else {
            boolean removed = false;
            for (List list : baseList) {
                removed |= list.remove(o);
            }
            return removed;
        }
    }

    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends List<T>> c) {
        boolean addedAll = true;
        for (List<T> list : c) {
            addedAll &= add(list);
        }
        return addedAll;
    }

    public boolean addAll(int index, Collection<? extends List<T>> c) {
        resetIterator();
        return baseList.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        boolean removedAll = true;
        for (Object obj : c) {
            removedAll &= this.remove(obj);
        }
        return removedAll;
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported"); //To change body of generated methods, choose Tools | Templates.
    }

    public void clear() {
        resetIterator();
        baseList.clear();
    }

    public List<T> get(int index) {
        return baseList.get(index);
    }
    
    public T getElement(int totalOrderIndex) {
        if (isEmpty()) throw new IndexOutOfBoundsException("List is empty");
        int listIndex = 0;
        while (totalOrderIndex >= baseList.get(listIndex).size()) {
            totalOrderIndex -= baseList.get(listIndex++).size();
        }
        return baseList.get(listIndex).get(totalOrderIndex);
    }
    
    public T getElement(int listIndex, int elementIndex) {
        return baseList.get(listIndex).get(elementIndex);
    }

    public List<T> set(int index, List<T> list) {
        resetIterator();
        return baseList.set(index, list);
    }
    
    public T setElement(int listIndex, int elementIndex, T element) {
        resetIterator();
        return baseList.get(listIndex).set(elementIndex, element);
    }
    
    public T setElement(int totalOrderIndex, T element) {
        resetIterator();
        if (isEmpty()) throw new IndexOutOfBoundsException("List is empty");
        int listIndex = 0;
        while (totalOrderIndex >= baseList.get(listIndex).size()) {
            totalOrderIndex -= baseList.get(listIndex++).size();
        }
        return baseList.get(listIndex).set(totalOrderIndex, element);
    }

    public void add(int index, List<T> element) {
        resetIterator();
        baseList.add(index, element);
    }

    public List<T> remove(int index) {
        resetIterator();
        return baseList.remove(index);
    }

    public int indexOf(Object o) {
        if (o instanceof List) {
            return baseList.indexOf(o);
        }
        else {
            int listIndex = 0;
            for (List list : baseList) {
                int elementIndex = list.indexOf(o);
                if (elementIndex >= 0) {
                    return listIndex + elementIndex;
                }
                listIndex += list.size();
            }
            return -1;
        }
    }

    public int lastIndexOf(Object o) {
        if (o instanceof List) {
            return baseList.lastIndexOf(o);
        }
        else {
            for (int listIndex = baseList.size() - 1; listIndex >= 0; listIndex--) {
                int elementIndex = baseList.get(listIndex).lastIndexOf(o);
                if (elementIndex >= 0) {
                    for (int j = 0; j < listIndex; j++) {
                        elementIndex += baseList.get(j).size();
                    }
                    return elementIndex;
                }
            }
            return -1;
        }
    }

    public ListIterator<List<T>> listIterator() {
        return baseList.listIterator();
    }

    public ListIterator<List<T>> listIterator(int index) {
        return baseList.listIterator(index);
    }

    public List<List<T>> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return baseList.toString();
    }
    
    private void resetIterator() {
        currentIterator = null;
        currentIteratorIndex = -1;
    }
    
}
