/*
 * MessageArrivedEvent.java
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
 *Special event type for relaying messages between application layers. Its payload is a single
 *string message or byte array. This class is "castable" from events with at least one item.
 *WARNING: The constructor and accessor semantics for this object differ from those of the Event base class.
 *Please note the specific documentation for each method.
 *@see programming5.net.Event#castTo(java.lang.Class, programming5.net.Event) 
 *@author Andres Quiroz Hernandez
 *@version 6.11
 */
public class MessageArrivedEvent extends programming5.net.Event {

    public static final String TYPE_STRING = "MAE";

    protected MessageArrivedEvent() {
        super(TYPE_STRING);
    }

    /**
     *Creates a new message arrived event with the given message payload.
     *WARNING: This differs from the semantics of the corresponding Event constructor, which takes the string
     *input as the event type. This class automatically assigns an event type to the encoded object.
     *@param msg the message content encapsulated by the event
     */
    public MessageArrivedEvent(String msg) {
        super(TYPE_STRING);
        this.addMessageItem(msg);
    }

    /**
     *Creates a new message arrived event with the given payload, trasforming the byte array to a String 
     *object.
     *WARNING: This differs from the semantics of the corresponding Event constructor, which takes the byte
     *array input as the encoded event. To decode a complete message arrived event byte array, use the static
     *decode method.
     *@param bytesMsg the message content encapsulated by the event
     */
    public MessageArrivedEvent(byte[] bytesMsg) {
        super(TYPE_STRING);
        this.addMessageItem(bytesMsg);
    }

    /**
     * Creates a MessageArrivedEvent object from an encoded byte array
     * @param eventBytes the encoded byte array, which must be an event of the correct type
     * @return the decoded MessageArrivedEvent object
     * @throws programming5.net.MalformedMessageException if the byte array does not conform to the correct 
     * syntax or is of the incorrect type.
     */
    public static MessageArrivedEvent decode(byte[] eventBytes) throws MalformedMessageException {
        Event event = new Event(eventBytes);
        if (!event.getHeader().equals(TYPE_STRING)) {
            throw new MalformedMessageException("MessageArrivedEvent: Unable to decode: Not a message arrived event (Found " + event.getHeader() + ")");
        }
        else if (event.getMessageSize() != 1) {
            throw new MalformedMessageException("MessageArrivedEvent: Unable to decode: Event must have single payload message");
        }
        return new MessageArrivedEvent(event.getItemAsByteArray(0));
    }

    @Override
    public boolean assertFormat() {
        return (this.getMessageSize() >= 1);
    }

    /**
     * @return the message payload associated with this event
     */
    public String getContent() {
        return this.getMessageItem(0);
    }

    /**
     *@return the byte array representation of the message associated with this event
     */
    public byte[] getContentBytes() {
        return this.getItemAsByteArray(0);
    }

}
