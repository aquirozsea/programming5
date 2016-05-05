package programming5.code;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Collection of methods that extend or encapsulate some language functions
 */
public abstract class LangExtend {

    /**
     * Allows performing a test on a reference that may be null (or whose test may throw a NullPointerException), where
     * the null case is equivalent to a false test
     * @param reference the object on which to perform the test
     * @param test the test to be performed as a predicate function or lambda
     * @return the result of the test, or false if a null pointer exception is thrown when performing the test
     */
    public static <T> boolean safeTest(T reference, Predicate<T> test) {
        try {
            return test.test(reference);
        }
        catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * Allows performing an operation (function) on an object reference that may be null (or whose operation may throw a
     * NullPointerException), given a default operation result that will be returned in the null case.
     * @param obj the object on which to perform the operation
     * @param defResult the default result to return in the case of a NullPointerException
     * @param operation the operation function or lambda
     * @return the result of the operation, or the default result given if a NullPointerException is thrown when
     * performing the operation
     */
    public static <T, R> R safeOp(T obj, R defResult, Function<T, R> operation) {
        try {
            return operation.apply(obj);
        }
        catch (NullPointerException npe) {
            return defResult;
        }
    }

}
