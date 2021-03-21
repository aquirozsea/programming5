package programming5.code;

import programming5.arrays.ArrayOperations;
import programming5.strings.StringOperations;

import java.util.Arrays;

/**
 * This class wraps an array of objects and provides hashCode and equals implementations so that instances can be used
 * as hash keys. The correct functionality of the hashCode and equals methods depends on the correct implementation of
 * the corresponding methods in the contained objects' classes.
 */
public class Tuple {

    protected Object[] tuple;

    /**
     * Creates a tuple wrapping the given variable length array of objects. Note that, if not used as a varargs argument,
     * the tuple will point directly to the array given (a copy of the array will not be created)
     * @param contents the objects to be contained in the tuple
     */
    public Tuple(Object... contents) {
        tuple = contents;
    }

    /**
     * Convenience method that returns a new Tuple object (equivalent to calling new Tuple(contents))
     * @param contents the objects to be contained in the Tuple
     * @return the new Tuple object wrapping the content objects
     */
    public static Tuple of(Object... contents) {
        return new Tuple(contents);
    }

    /**
     * @return the number of tuple elements
     */
    public int size() {
        return tuple.length;
    }

    public Tuple thisWith(Object... newContents) {
        tuple = ArrayOperations.join(tuple, newContents);
        return this;
    }

    /**
     * Creates a new Tuple object that appends the new contents to the end of this tuple, while preserving this tuple's
     * array (note, however, that the tuple items are not cloned, so this tuple's items will be referenced by two arrays).
     * For example, if t1 = Tuple.of(1, 2) and t2 = t1.newWith(3, 4); t1 -> [1, 2] and t2 -> [1, 2, 3, 4]
     * @param newContents the contents to append to the new tuple
     * @return a new tuple object with a concatenation of this tuple and the new contents
     */
    public Tuple newWith(Object... newContents) {
        return new Tuple(this.tuple).thisWith(newContents);
    }

    /**
     * Creates a new tuple that contains only the items with the given indices, while preserving this tuple's array
     * (note, however, that the tuple items are not cloned, so the projected items will be referenced by two arrays).
     * For example, if t1 = Tuple.of("a", "b", "c", "d") and t2 = t1.project(0, 2); t1 -> ["a", "b", "c", "d"] and
     * t2 -> ["a", "c"].
     * @param items the indices of the items to include in the projection
     * @return a new tuple object containing only the projected items
     */
    public Tuple project(int... items) {
        Tuple ret = new Tuple(new Object[items.length]);
        for (int i = 0; i < items.length; i++) {
            ret.tuple[i] = this.tuple[items[i]];
        }
        return ret;
    }

    /**
     * @return true iif for every i, this.tuple[i].equals(other.tuple[i])
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple1 = (Tuple) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(tuple, tuple1.tuple);

    }

    /**
     * Uses {@link Arrays#hashCode(Object[])}
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(tuple);
    }

    /**
     * @return the item contained at the given index
     */
    public Object get(int index) {
        return tuple[index];
    }

    /**
     * Replaces the item at the given index with the new object
     * @return a reference to this tuple for command chaining
     */
    public Tuple set(int index, Object content) {
        tuple[index] = content;
        return this;
    }

    /**
     * @return the item contained at the given index, cast into the given class
     */
    public <T> T getAs(int index, Class<T> itemClass) {
        return (T) tuple[index];
    }

    /**
     * @return the item contained at the given index, cast into the receiving class
     */
    public <T> T getAs(int index) {
        return (T) tuple[index];
    }

    @Override
    public String toString() {
        return "[" + StringOperations.toList(tuple) + "]";
    }
}
