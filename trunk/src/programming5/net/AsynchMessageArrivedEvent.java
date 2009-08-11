/*
 * AsynchMessageArrivedEvent.java
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

package programming5.net;

/**
 *Special event type for relaying messages asynchronously between application layers. Its payload 
 *is a single string message or byte array, and it contains the URL of the node where the message
 *originated. This class is used by UDP client classes that implement replyTo methods.
 *This class is "castable" from events with at least one item.
 *WARNING: The constructor and accessor semantics for this object is similar to those of the
 *MessageArrivedEvent class, which differ from those of the Event base class.
 *@see programming5.net.Event#castTo(java.lang.Class, programming5.net.Event)
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class AsynchMessageArrivedEvent extends MessageArrivedEvent {

    protected String sourceURL = null;

    protected AsynchMessageArrivedEvent() {
        super(TYPE_STRING);
    }

    /**
     *Creates a new message arrived event with the given message payload and origin
     *@param msg the message content encapsulated by the event
     *@param from the URL of the message origin
     */
    public AsynchMessageArrivedEvent(String msg, String from) {
        super(msg);
        sourceURL = from;
    }

    /**
     *Creates a new message arrived event with the given message payload and origin
     *@param msgBytes the message content encapsulated by the event
     *@param from the URL of the message origin
     */
    public AsynchMessageArrivedEvent(byte[] msgBytes, String from) {
        super(msgBytes);
        sourceURL = from;
    }

    /**
     * @return the URL of the node where the message originated
     */
    public String getSourceURL() {
        return sourceURL;
    }

}
