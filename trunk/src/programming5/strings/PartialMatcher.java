/*
 * PartialMatcher.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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

package programming5.strings;

import programming5.code.ObjectMatcher;

/**
 * Implementation of an object matcher for strings that implements a partial string match by inclusion, i.e. this.matches(obj1, obj2) == true iif obj1.contains(obj2)
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class PartialMatcher implements ObjectMatcher<String> {

    public boolean matches(String obj1, String obj2) {
        return obj1.contains(obj2);
    }

}
