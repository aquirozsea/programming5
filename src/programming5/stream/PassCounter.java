package programming5.stream;

import java.util.function.UnaryOperator;

/**
 * A pass counter can be used to count the items flowing through a particular step in a stream by inserting it as a map
 * operator where necessary. Note that this implementation should not be used with parallel streams, as the result
 * will be undefined.
 */
public class PassCounter<T> implements UnaryOperator<T> {

    int counter = 0;

    @Override
    public T apply(T item) {
        counter++;
        return item;
    }

    public int getCounter() {
        return counter;
    }

}
