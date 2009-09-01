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
 *This is a special message wrapper for transmission in a reliable protocol. It allows the
 *distinction of acknowledge messages from actual content messages.
 *@see programming5.net.Message
 *@author Andres Quiroz Hernandez
 *@version 6.11
 */
public class ReliableProtocolMessage extends Message {
    
    public static String MESSAGE_HEADER = "RPM";
    public static String ACK_HEADER = "ACK";

    protected String destination = null;
    protected int sendCount = 0;
    protected boolean acked = false;
    
    /**
     *Creates a reliable protocol message from the given message string, which must follow the 
     *correct format (otherwise a MalformedMessageException is thrown)
     *@param rpm the encoded reliable protocol message
     *@throws MalformedMessageException when the byte message does not follow the correct format
     */
    public ReliableProtocolMessage(byte[] rpm) throws MalformedMessageException {
        super(rpm);
        if (!this.header.equals(MESSAGE_HEADER) && !this.header.equals(ACK_HEADER)) {
            throw new MalformedMessageException("ReliableProtocolMessage: Constructor: Not a reliable protocol message");
        }
    }
    
    /**
     *Creates a reliable protocol message that encapsulates the given message payload with the 
     *given sequence and part numbers
     *@param msg the message to encapsulate
     *@param sequence the sequence number associated with the message
     *@param index the index of the message part, for multiple part messages (1 to total)
     *@param total the total number of parts of this message
     *@param destURL the URL of the destination where the message will be sent (this information
     * is not encoded in the message sent over the network
     */
    public ReliableProtocolMessage(byte[] msg, long sequence, int index, int total, String destURL) {
        super();
        this.setHeader(MESSAGE_HEADER);
        this.addMessageItem(sequence);
        this.addMessageItem(index);
        this.addMessageItem(total);
        this.addMessageItem(msg);
        destination = destURL;
    }
    
    /**
     *Creates an acknowledgement message as a reliable protocol message for the given sequence and part
     *numbers
     *@param sequence the sequence number of the message for which a part will be acknowledged
     *@param index the index of the message part that will be acknowledged
     */
    public ReliableProtocolMessage(long sequence, int index) {
        super();
        this.setHeader(ACK_HEADER);
        this.addMessageItem(sequence);
        this.addMessageItem(index);
    }
    
    /**
     *@return the payload, if not an acknowledgement
     *@throws MalformedMessageException if the message was not correctly constructed
     */
    public byte[] getPayload() throws MalformedMessageException {
        byte[] ret = null;
        if (header.equals(MESSAGE_HEADER)) {
            try {
                ret = this.getItemAsByteArray(3);
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
     *@return the sequence number of message or acknowledgement (identifies an entire message and is shared
     * by all parts is a multipart message
     *@throws MalformedMessageException if the message was not constructed correctly
     */
    public long getSequence() throws MalformedMessageException {
        return this.getItemAsLong(0);
    }

    /**
     * @return the index of the message part of a multipart message (will be 1 if the message has a single 
     * part)
     * @throws programming5.net.MalformedMessageException if the message was not constructed correctly
     */
    public int getIndex() throws MalformedMessageException {
        return this.getItemAsInt(1);
    }

    /**
     * @return the total number of parts of this message (will be 1 if the message has a single part)
     * @throws programming5.net.MalformedMessageException if the message was not constructed correctly
     */
    public int getTotal() throws MalformedMessageException {
        return this.getItemAsInt(2);
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

    /**
     * Increments an internal counter, not sent over the network, for every time the message has 
     * been sent.
     */
    public void signalSent() {
        sendCount++;
    }

    /**
     * @return the number of times the message has been sent, if this has been kept track of using 
     * the signalSent method
     */
    public int getSendCount() {
        return sendCount;
    }

    /**
     * @return the URL corresponding to the destination of the message
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Set when an acknowledgement has been received for this message
     */
    public void signalAcked() {
        acked = true;
    }

    /**
     * @return true if an acknowledgement has been received for this message, as set by signalAcked
     */
    public boolean isAcked() {
        return acked;
    }

}
