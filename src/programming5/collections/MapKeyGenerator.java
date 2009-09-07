/*
 * MapKeyGenerator.java
 *
 * Copyright 2009 Andres Quiroz Hernandez
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

package programming5.collections;

/**
 * Classes that implement this interface are meant to be used with Map objects to generate the keys associated
 * with objects to store. Depending on the application, implementing classes will have their own key
 * distributions.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public interface MapKeyGenerator<E> {

    /**
     * @return a new key of the generic key type
     */
    public E generateKey();

}
