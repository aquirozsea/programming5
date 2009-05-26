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
 *An Event is a holder class based on a Message object. It is meant to serve as a base class for other events to be used 
 *within the Programming5 network framework, being able to hold a message object and provide 
 *accessor methods for its items. All events must have a specified type, with is coded as the message header.
 *@see Message
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class Event {
    protected Message message;
    
    /**
     *Creates an event of the given type
     */
    public Event(String type) {
        message = new Message();
        message.setHeader(type);
    }
    
    /**
     *Creates an event from the given event message, which must have a header, which is taken as the event type.
     */
    public Event(Message evtMsg) {
        if (evtMsg.getHeader() != null) {
            message = evtMsg;
        } 
        else throw new IllegalArgumentException("Event: Constructor: A valid type is needed (code as message header)");
    }
    
    /**
     *@return the string that corresponds to the event type
     */
    public String getType() {
        return message.getHeader();
    }
    
    /**
     *@return true if the event is of the given type (the descriptor equals the given string)
     */
    public boolean isType(String type) {
        return type.equals(message.getHeader());
    }
    
    /**
     *@return each of the items carried within the event message
     */
    public String[] getPayload() {
        String[] ret = new String[message.getMessageSize()];
        for (int i = 0; i < message.getMessageSize(); i++) {
            ret[i] = message.getMessageItem(i);
        }
        return ret;
    }
    
    /**
     *@return the string message that holds event data in the format TYPE:PAYLOAD
     */
    public String toString() {
        String ret = null;
        try {
            ret = message.getMessage();
        } 
        catch (MalformedMessageException mme) {}
        return ret;
    }
}
