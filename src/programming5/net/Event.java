/*
 * Event.java
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
 *As of version 6.1, an Event is a message with a header, which is interpreted as the event type. All events
 *must have a specified type.
 *@author Andres Quiroz Hernandez
 *@version 6.11
 */
public class Event extends Message {
    
    /**
     *Creates an event of the given type.
     *WARNING: Note that this differs from the functionality of the corresponding deprecated constructor in
     *the Message class.
     *@param type the event type, which will be encoded as the message header
     */
    public Event(String type) {
        super();
        this.setHeader(type);
    }
    
    /**
     *@deprecated this constructor is no longer supported as of version 6.1
     *@throws UnsupportedOperationException
     */
    @Deprecated
    public Event(Message evtMsg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates an event by decoding the given byte array
     * @param messageBytes the encoded event message, which must follow the Message class syntax
     * @throws programming5.net.MalformedMessageException if the encoded message does not follow the correct 
     * syntax or does not contain a header to be used as the event type
     */
    public Event(byte[] messageBytes) throws MalformedMessageException {
        super(messageBytes);
        if (this.header == null) {
            throw new MalformedMessageException("Event: Cannot create event from byte array: No event type");
        }
    }

    /**
     * Used to downcast a given event instance (created with an Event class constructor) to a specific event 
     * class type. This method creates a new instance of the given type and copies the event body into the 
     * new instance (the original object should be discarded). In order to be "castable" a derived class must 
     * provide a public default constructor and should override the assertFormat method, to return false for 
     * events with items that do not match the specific event format and force the castTo method to throw a 
     * ClassCastException.
     * @param eventClass the derived Event class type
     * @param eventObject the object to cast into the new type
     * @return a new object of the desired type, if the original object is "castable"
     * @throws java.lang.ClassCastException if the given object is not "castable"
     */
    public static <T extends Event> T castTo(Class<T> eventClass, Event eventObject) throws ClassCastException {
        T ret = null;
        try {
            ret = eventClass.newInstance();
            ret.body = eventObject.body;
            if (!ret.assertFormat()) {
                throw new ClassCastException("Event: Cannot cast to given event type: Incorrect event format");
            }
        }
        catch (InstantiationException ie) {
            throw new ClassCastException("Event: Cannot cast to given type: Default constructor not available: " + ie.getMessage());
        }
        catch (IllegalAccessException iae) {
            throw new ClassCastException("Event: Cannot cast to given type: Illegal access exception: " + iae.getMessage());
        }
        return ret;
    }

    public boolean assertFormat() {
        return true;
    }

    /**
     *Analogous to getHeader
     *@return the string that corresponds to the event type
     */
    public String getType() {
        return this.getHeader();
    }
    
    /**
     *@param type a type string for comparison
     *@return true if the event is of the given type (the descriptor equals the given string)
     */
    public boolean isType(String type) {
        return type.equals(this.getHeader());
    }
    
    /**
     *@return each of the items carried within the event message
     *@deprecated since events can carry raw byte payloads as of version 6.1, conversion to strings might
     *corrupt the payload content. The payload can be accessed via the specific accessor methods defined in
     *the Message class.
     */
    @Deprecated
    public String[] getPayload() {
        String[] ret = new String[this.getMessageSize()];
        for (int i = 0; i < this.getMessageSize(); i++) {
            ret[i] = this.getMessageItem(i);
        }
        return ret;
    }
    
    /**
     *@return the string message that holds event data in the format TYPE:PAYLOAD, for display purposes
     */
    @Override
    public String toString() {
        return this.toString();
    }
}
