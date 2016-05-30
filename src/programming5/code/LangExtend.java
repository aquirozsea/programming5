/*
 * LangExtend.java
 *
 * Copyright 2016 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
