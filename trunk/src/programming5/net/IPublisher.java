/*
 * IPublisher.java
 *
 * Copyright 2004 Andres Quiroz Hernandez
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

package programming5.net;

/**
 *Interface for event publishers. There is an abstract implementation of this interface that handles the basic subscription and 
 *notification methods, which is more convenient to use, so you may want to implement this interface directly only when 
 *single inheritance gets in the way, or you want to create an extended interface.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public interface IPublisher<E extends programming5.net.Event> {

    public void addListener(Subscriber<E> s);
    public void removeListener(Subscriber<E> s);
    public <T extends E> void fireEvent(T event);

}
