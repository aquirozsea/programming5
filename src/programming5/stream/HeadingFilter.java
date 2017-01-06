/*
 * HeadingFilter.java
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
 * Can be used in a stream filter function to filter out the first X elements from an ordered stream. Because it is a
 * stateful filter, it is not thread safe and should not be used on parallelizable streams.
 */
public class HeadingFilter implements Predicate<String> {

    int seen = 0;
    int discard;

    public HeadingFilter() {
        discard = 1;
    }

    public HeadingFilter(int discardRows) {
        discard = discardRows;
    }

    @Override
    public boolean test(String s) {
        seen++;
        return seen > discard;
    }

}
