package programming5.stream;

import java.util.function.Predicate;

/**
 * Pass counter that only counts items that satisfy the given condition predicate. Note that this implementation should
 * not be used with parallel streams, as the result will be undefined.
 */
public class ConditionalPassCounter<T> extends PassCounter<T> {

    protected Predicate<T> condition;

    protected ConditionalPassCounter() {}

    public ConditionalPassCounter(Predicate<T> myCondition) {
        condition = myCondition;
    }

    @Override
    public T apply(T item) {
        if (condition.test(item)) {
            return super.apply(item);
        }
        else {
            return item;
        }
    }

}
