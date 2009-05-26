/*
 * CallbackEvent.java
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

import java.io.IOException;
import programming5.io.Serializer;

/**
 *Event that holds the result of an asynchronous operation. It can hold any type of object as the result of the operation, but 
 *if the event string is needed, the object must be serializable.
 *@see #toString
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class CallbackEvent extends programming5.net.Event {
    
    public static final String TYPE_STRING = "CBKE";
    
    protected Object result = null;
    
    /**
     *Creates a new callback event for the given result, with the given message.
     */
    public CallbackEvent(String msg, Object opResult) {
        super(TYPE_STRING);
        message.addMessageItem(msg);
        result = opResult;
    }
    
    /**
     *Creates a new callback event from the given message object
     *@throws IllegalArgumentException if the message object does not correspond to this event type or format.
     */
    public CallbackEvent(Message evtMsg) {
        super(evtMsg);
        if (!message.getHeader().equals(TYPE_STRING)) {
            throw new IllegalArgumentException("CallbackEvent: Constructor: Message is not of correct type");
        }
    }
    
    /**
     *@return the result message associated with the event
     */
    public String getMessage() {
        return message.getMessageItem(0);
    }
    
    /**
     *@return the result object associated with the event
     */
    public Object getResult() throws IOException, ClassNotFoundException {
        if (result == null) {
            String serializedObj = message.getMessageItem(1);
            result = Serializer.deserialize(serializedObj);
        }
        return result;
    }
    
    /**
     *@return true if the result object associated with this event is an exception
     */
    public boolean isError() {
        boolean ind = false;
        if (result != null) {
            if (result instanceof Exception) {
                ind = true;
            }
        }
        return ind;
    }
    
    public String toString() {
        String ret = "";
        try {
            message.addMessageItem(Serializer.serialize(result));
            ret = message.getMessage();
        }
        catch (Exception e) {
            throw new RuntimeException("CallbackEvent: Couldn't get event string: " + e.getMessage());
        }
        return ret;
    }
    
}
