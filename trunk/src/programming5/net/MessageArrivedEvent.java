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
 *Special event type for network messages. Its payload is a single string message or byte array.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class MessageArrivedEvent extends programming5.net.Event {
	
	public static final String TYPE_STRING = "MAE";
        
        protected byte[] messageBytes;
	
	/**
         *Creates a new message arrived event with the given message payload.
         */
        public MessageArrivedEvent(String msg) {
		super(TYPE_STRING);
		message.addMessageItem(msg);
                messageBytes = msg.getBytes();
	}
	
	/**
         *Creates a new message arrived event from the given Message object.
         *@throws java.lang.IllegalArgumentException if the given message isn't of the correct type
         */
        public MessageArrivedEvent(Message evtMsg) {
		super(evtMsg);
		if (!message.getHeader().equals(TYPE_STRING)) {
			throw new IllegalArgumentException("MessageArrivedEvent: Constructor: Message is not of correct type");
		}
                messageBytes = message.getMessageItem(0).getBytes();
	}
        
        /**
         *Creates a new message arrived event with the given payload, trasforming the byte array to a String object.
         */
        public MessageArrivedEvent(byte[] bytesMsg) {
            super(TYPE_STRING);
            message.addMessageItem(new String(bytesMsg));
            messageBytes = bytesMsg;
        }
	
	/**
         *@return the message associated with this event
         */
        public String getMessage() {
		return message.getMessageItem(0);
	}
        
        /**
         *@return the byte array representation of the message associated with this event
         */
        public byte[] getMessageBytes() {
            return messageBytes;
        }
	
}
