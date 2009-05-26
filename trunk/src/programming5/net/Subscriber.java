/*
 * Subscriber.java
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
 *This interface is meant to identify objects that listen to events of a specific type. Unlike the Listener interface,
 *it provides a method by which events defined in the Programming5 network framework can be received.
 *@see Listener
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public interface Subscriber<E extends programming5.net.Event> {
    
    /**
     *Called when the given event is generated, so that the subscriber can consume it.
     */
    public <T extends E> void signalEvent(T event);
        
}
