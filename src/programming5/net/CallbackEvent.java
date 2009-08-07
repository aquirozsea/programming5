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
import java.io.NotSerializableException;
import programming5.io.Debug;
import programming5.io.Serializer;

/**
 *Event that holds the result of an asynchronous operation. As of version 6.1, objects encapsulated into a
 *CallbackEvent must be serializable.
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public class CallbackEvent extends programming5.net.Event {
    
    public static final String TYPE_STRING = "CBKE";
    
    /**
     *Creates a new callback event for the given result, with the given message.
     *@param qualifier a message that describes the encapsulated result
     *@param opResult the operation result
     */
    public CallbackEvent(String qualifier, Object opResult) {
        super(TYPE_STRING);
        this.addMessageItem(qualifier);
        if (opResult != null) {
            try {
                this.addMessageItem(Serializer.serializeBytes(opResult));
            }
            catch (NotSerializableException nse) {
                throw new IllegalArgumentException("CallbackEvent: Object must be serializable");
            }
            catch (IOException ioe) {
                throw new RuntimeException("CallbackEvent: Could not serialize object: " + ioe.getMessage());
            }
        }
    }
    
    /**
     *@deprecated this constructor is no longer supported
     *@throws UnsupportedOperationException
     */
    @Deprecated
    public CallbackEvent(Message evtMsg) {
        super(evtMsg);
    }

    /**
     * Creates a callback event by decoding the given byte array
     * @param eventBytes the encoded event message, which must follow the Message class syntax
     * @throws programming5.net.MalformedMessageException if the encoded message does not follow the correct
     * syntax or is of an incorrect type
     */
    public CallbackEvent(byte[] eventBytes) throws MalformedMessageException {
        super(eventBytes);
        if (!this.getHeader().equals(TYPE_STRING)) {
            throw new IllegalArgumentException("CallbackEvent: Cannot create from byte array: Not a callback event: Found (" + this.getHeader() + ")");
        }
        if (this.getMessageSize() > 2) {
            throw new MalformedMessageException("CallbackEvent: Cannot create from byte array: Too many items (at most 2)");
        }
    }
    
    /**
     *@return the result message associated with the event
     *@deprecated different functionality from (deprecated) base class method; use getQualifier instead
     */
    @Override
    @Deprecated
    public String getMessage() {
        return this.getMessageItem(0);
    }

    /**
     * @return the descriptive message associated with the event
     */
    public String getQualifier() {
        return this.getMessageItem(0);
    }
    
    /**
     *@return the result object associated with the event
     */
    public Object getResult() throws IOException, ClassNotFoundException {
        Object result = null;
        if (this.getMessageSize() == 2) {
            result = Serializer.deserialize(this.getItemAsByteArray(1));
        }
        return result;
    }
    
    /**
     *@return true if the result object associated with this event is an exception
     */
    public boolean isError() {
        boolean ret = false;
        try {
            Object result = this.getResult();
            if (result != null) {
                if (result instanceof Exception) {
                    ret = true;
                }
            }
        }
        catch (Exception e) {
            Debug.printStackTrace(e, "programming5.net.CallbackEvent");
        }
        return ret;
    }
    
}
