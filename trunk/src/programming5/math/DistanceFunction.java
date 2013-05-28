/*
 * DistanceFunction.java
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

package programming5.math;

/**
 * Defines the distance between any two objects of a particular class
 * @author Andres Quiroz Hernandez
 * @version 6.9
 */
public interface DistanceFunction<T> {

    /**
     * A distance must be a non-negative number, and must be 0 if the two objects are equals (note that the converse is not necessarily true: two objects that are not equal may have a distance equal to 0)
     * @return the distance between the two objects
     */
    public double distance(T obj1, T obj2);

}
