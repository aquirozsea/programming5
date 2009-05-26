/*
 * ReliableProtocolMessage.java
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
 *This is a special message wrapper for transmission in a reliable protocol. It allows the distinction of acknowledge messages 
 *from actual content messages.
 *@see programming5.net.Message
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class ReliableProtocolMessage extends Message {
    
    public static String MESSAGE_HEADER = "RPM";
    public static String ACK_HEADER = "ACK";
    
    /**
     *Creates a reliable protocol message from the given message string, which must follow the correct format (otherwise a MalformedMessageException is thrown)
     */
    public ReliableProtocolMessage(String rpm) throws MalformedMessageException {
        super(rpm);
        if (!this.header.equals(MESSAGE_HEADER) && !this.header.equals(ACK_HEADER)) {
            throw new MalformedMessageException("ReliableProtocolMessage: Constructor: Not a reliable protocol message");
        }
    }
    
    /**
     *Creates a reliable protocol message that encapsulates the given message payload with the given sequence number
     */
    public static ReliableProtocolMessage encapsulate(String msg, int sequence) {
        Message message = Message.constructMessage(MESSAGE_HEADER, sequence, msg);
        ReliableProtocolMessage ret = null;
        try {
            ret = new ReliableProtocolMessage(message.getMessage());
        } 
        catch (MalformedMessageException mme) {}
        return ret;
    }
    
    /**
     *Creates an acknowledgement message as a reliable protocol message for the given sequence number
     */
    public static ReliableProtocolMessage createAck(int sequence) {
        ReliableProtocolMessage ret = null;
        try {
            Message message = new Message();
            message.setHeader(ACK_HEADER);
            message.addMessageItem(sequence);
            ret = new ReliableProtocolMessage(message.getMessage());
        } 
        catch (MalformedMessageException mme) {}
        return ret;
    }
    
    /**
     *@return the payload if not an acknowledgement (otherwise a malformed message exception is thrown)
     */
    public String getPayload() throws MalformedMessageException {
        String ret = null;
        if (header.equals(MESSAGE_HEADER)) {
            try {
                ret = this.getMessageItem(1);
            }
            catch (IndexOutOfBoundsException iobe) {
                throw new MalformedMessageException("ReliableProtocolMessage: No message");
            }
        }
        else {
            throw new MalformedMessageException("ReliableProtocolMessage: Can't get message: Wrong message type");
        }
        return ret;
    }
    
    /**
     *@return the sequence number of message or acknowledgement
     */
    public int getSequence() throws MalformedMessageException {
        return this.getItemAsInt(0);
    }
    
    /**
     *@return true if the message is an acknowledgement
     */
    public boolean isAcknowledge() {
        return header.equals(ACK_HEADER);
    }
    
    /**
     *@return true if the message is not an acknowledgement and carries a message payload.
     */
    public boolean isMessage() {
        return header.equals(MESSAGE_HEADER);
    }
}
