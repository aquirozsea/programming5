/*
 * ConditionalPassCounter.java
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

package programming5.stream;

import java.util.function.Predicate;

/**
 * Pass counter that only counts items that satisfy the given condition predicate. Note that this implementation should
 * not be used with parallel streams, as it is a side-effect operation, the result of which will be undefined.
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
