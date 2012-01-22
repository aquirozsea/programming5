/*
 * Message.java
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

import java.util.ArrayList;
import java.util.List;
import programming5.arrays.ArrayOperations;

/**
 *This class is used to store and manipulate an organized array of items in a message
 *context of header and body items, to produce encoded messages of the
 *following syntax: [header]:[item{::item}]<p>
 *There are no restrictions on the syntax of the items. If constructing a message string by hand, however, 
 *take care that all sequences of consecutive colons (::) correspond to message separators; otherwise, the
 *message will be decoded incorrectly. Additionally, if colon-slash sequences (:/) exist in an item, they will
 *be modified by the decoding methods. In both these cases, adding a slash after the first colon will allow
 *correct decoding of the message items; this is precisely what is done when messages are encoded normally,
 *to allow for arbitrary syntax of message items.<p>
 *Note that the shortest valid message is a single colon (:) (message with no header and no body) and that 
 *all messages must have at least one colon.<p>
 *As of version 6.1, the internal encoding of messages is handled with arrays of bytes, to prevent conversion
 *errors when encoding byte arrays as strings. For the basic types, these arrays correspond to the byte
 *representation of the items' string representations, not their internal numeric representation. Byte array
 *accessors were added to manipulate these items directly as byte arrays, for serialized objects or raw byte
 *payloads.
 *@author Andres Quiroz Hernandez
 *@version 6.19
 */
public class Message {
    
    protected String header = null;
    protected byte[][] body = null;

    private static final String sepString = ":";
    private static final String sep2String = "::";
    private static final byte sepByte = ":".getBytes()[0];
    private static final byte escByte = "/".getBytes()[0];
    
    /**
     *Constructor to create an empty message object
     */
    public Message() {
    }
    
    /**
     *Constructor to create a message object decoding a message string of an
     *unknown size. The message must follow the correct syntax or an exception is
     *thrown.
     *@param message the encoded message in String format
     *@deprecated the preferred way to encode messages is as byte arrays
     */
    @Deprecated
    public Message(String message) throws MalformedMessageException {
        if (message != null) {
            String[] parts = message.split(sepString, 2);
            if (parts.length == 2) {
                if (parts[0].length() > 0) {
                    header = parts[0];
                }
                if (!parts[1].equals("")) {
                    parts = parts[1].split(sep2String);
                    body = new byte[parts.length][];
                    for (int i = 0; i < parts.length; i++) {
                        body[i] = parts[i].getBytes();
                    }
                }
            } 
            else throw new MalformedMessageException("Message start not found");
        } 
        else throw new MalformedMessageException("Message is null");
    }
    
    /**
     *Constructor to create a message object decoding a message string of an
     *expected size. The message must follow the correct syntax and correspond
     *with the size, or an exception is thrown.
     *@param message the encoded message in String format
     *@param size the expected message size
     *@deprecated the preferred way to encode messages is as byte arrays
     */
    @Deprecated
    public Message(String message, int size) throws MalformedMessageException {
        if (message != null) {
            String[] parts = message.split(sepString, 2);
            if (parts.length == 2) {
                if (parts[0].length() > 0) {
                    header = parts[0];
                }
                parts = parts[1].split(sep2String);
                if (parts.length == size) {
                    body = new byte[parts.length][];
                    for (int i = 0; i < parts.length; i++) {
                        body[i] = parts[i].getBytes();
                    }
                }
                else {
                    throw new MalformedMessageException("Incorrect number of message items");
                }
            }
            else throw new MalformedMessageException("Message start not found");
        }
        else throw new MalformedMessageException("Message is null");
    }

    /**
     * Constructor to create a message object decoding a byte array with an
     * unknown number of items. The message must follow the correct syntax or an exception is
     * thrown.
     * @param messageBytes the encoded message as a byte array
     * @throws programming5.net.MalformedMessageException
     */
    public Message(byte[] messageBytes) throws MalformedMessageException {
        int headerIndex = ArrayOperations.seqFind(sepByte, messageBytes);
        if (headerIndex >= 0) {
            if (headerIndex > 0) {
                header = new String(ArrayOperations.prefix(messageBytes, headerIndex));
            }
            List<Integer> separators = new ArrayList<Integer>();
            int start = headerIndex + 1;
            int separatorIndex = ArrayOperations.seqFind(sepByte, messageBytes, start);
            while (separatorIndex > 0 && separatorIndex < (messageBytes.length-1)) {
                if (messageBytes[separatorIndex+1] == sepByte) {
                    separators.add(separatorIndex);
                }
                start = separatorIndex + 2;
                separatorIndex = ArrayOperations.seqFind(sepByte, messageBytes, start);
            }
            body = new byte[separators.size()+1][];
            start = headerIndex + 1;
            for (int i = 0; i < separators.size(); i++) {
                int separator = separators.get(i);
                body[i] = ArrayOperations.subArray(messageBytes, start, separator);
                start = separator + 2;
            }
            body[separators.size()] = ArrayOperations.subArray(messageBytes, start, messageBytes.length);
        }
        else throw new MalformedMessageException("Message: Could not construct message: Illegal start");
    }
    
    /**
     *Creates a complete message from specific objects.
     *WARNING: This method uses the objects' toString method to obtain the item it will keep, with the 
     *exception of byte array objects, which will be encoded without change.
     *@param header the header string (must not contain the reserved character ':')
     *@param items a list of items to be encoded in the message object
     *@return the filled message object
     */
    public static Message constructMessage(String header, Object... items) {
        Message ret = new Message();
        ret.setHeader(header);
        for (Object item : items) {
            if (item instanceof byte[]) {
                ret.addMessageItem((byte[]) item);
            }
            else {
                ret.addMessageItem(item);
            }
        }
        return ret;
    }
    
    /**
     *Creates a headerless message from specific objects.
     *WARNING: This method uses the objects' toString method to obtain the item it will keep, with the
     *exception of byte array objects, which will be encoded without change.
     *@param header the header string (must not contain the reserved character ':')
     *@param items a list of items to be encoded in the message object
     *@return the filled message object
     */
    public static Message constructHeaderlessMessage(Object... items) {
        Message ret = new Message();
        for (Object item : items) {
            if (item instanceof byte[]) {
                ret.addMessageItem((byte[]) item);
            }
            else {
                ret.addMessageItem(item);
            }
        }
        return ret;
    }
    
    /**
     *Gets the encoded string of a message object.
     *@deprecated the string representation might not be reconstructed correctly as a new Message object
     * because of byte array to String conversion
     */
    @Deprecated
    public String getMessage() throws MalformedMessageException {
        String message;
        if (header != null) {
            message = header + sepString;
        }
        else {
            message = sepString;
        }
        int bodyLength = (body == null) ? 0 : body.length;
        if (bodyLength > 0) {
            message = message.concat(new String(body[0]));
            for (int i = 1; i < bodyLength; i++) {
                message = message.concat(sep2String);
                message = message.concat(new String(body[i]));
            }
        }
        return message;
    }

    /**
     * @return a string representation of the encoded message, for inspection purposes; the string returned 
     * by this method should not be used for decoding unless no raw byte array items are contained
     */
    @Override
    public String toString() {
        String message;
        if (header != null) {
            message = header + sepString;
        }
        else {
            message = sepString;
        }
        int bodyLength = (body == null) ? 0 : body.length;
        if (bodyLength > 0) {
            message = message.concat(new String(body[0]));
            for (int i = 1; i < bodyLength; i++) {
                message = message.concat(sep2String);
                message = message.concat(new String(body[i]));
            }
        }
        return message;
    }

    /**
     * @return the encoded message as a byte array; the array obtained from this method can be used to 
     * reconstruct a message object when decoded using the corresponding constructor
     */
    public byte[] getMessageBytes() {
        byte[] message;
        if (header != null) {
            message = (header + sepString).getBytes();
        }
        else {
            message = sepString.getBytes();
        }
        int bodyLength = (body == null) ? 0 : body.length;
        if (bodyLength > 0) {
            message = ArrayOperations.join(message, body[0]);
            for (int i = 1; i < bodyLength; i++) {
                message = ArrayOperations.join(message, sep2String.getBytes());
                message = ArrayOperations.join(message, body[i]);
            }
        }
        return message;
    }
    
    /**
     *@return the message header or null if one has not been specified
     */
    public String getHeader() {
        return header;
    }
    
    /**
     *Message items are returned as strings.
     *WARNING: If any items were saved as raw byte arrays, the conversion to string might corrupt the item's
     *content.
     *@param index the position of the item in the message, with numbering from 0 on
     *@return the string representation of the item at the given position
     */
    public String getMessageItem(int index) throws IndexOutOfBoundsException {
        String ret = null;
        if (body != null) {
            ret = new String(deflate(body[index]));
        } 
        else throw new IndexOutOfBoundsException("Message: No body");
        return ret;
    }
    
    /**
     *Gets an item if it can be representend by an integer
     *@param index the position of the item in the message, with numbering from 0 on
     *@return the integer representation of the item at the given position
     *@throws MalformedMessageException if the item cannot be represented as an integer
     */
    public int getItemAsInt(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        int i;
        try {
            i = Integer.parseInt(new String(deflate(body[index])));
        } 
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("Message: No body");
        } 
        catch (NumberFormatException nf) {
            throw new MalformedMessageException("Incorrect type for item " + index + ": Expected int, found " + deflate(body[index]));
        }
        return i;
    }
    
    /**
     *Gets an item if it can be representend by a double
     *@param index the position of the item in the message, with numbering from 0 on
     *@return the double representation of the item at the given position
     *@throws MalformedMessageException if the item cannot be represented as an double
     */
    public double getItemAsDouble(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        double d;
        try {
            d = Double.parseDouble(new String(deflate(body[index])));
        } 
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("Message: No body");
        } 
        catch (NumberFormatException nf) {
            throw new MalformedMessageException("Incorrect type for item " + index + ": Expected double, found " + deflate(body[index]));
        }
        return d;
    }
    
    /**
     *Gets an item if it can be representend by a float
     *@param index the position of the item in the message, with numbering starting at 0
     *@return the floating point representation of the item at the given position
     *@throws MalformedMessageException if the item cannot be represented as an float
     */
    public float getItemAsFloat(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        float f;
        try {
            f = Float.parseFloat(new String(deflate(body[index])));
        } 
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("Message: No body");
        } 
        catch (NumberFormatException nf) {
            throw new MalformedMessageException("Incorrect type for item " + index + ": Expected float, found " + deflate(body[index]));
        }
        return f;
    }
    
    /**
     *Gets an item if it can be representend by a long integer
     *@param index the position of the item in the message, with numbering starting at 0
     *@return the long integer representation of the item at the given position
     *@throws MalformedMessageException if the item cannot be represented as a long integer
     */
    public long getItemAsLong(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        long l;
        try {
            l = Long.parseLong(new String(deflate(body[index])));
        } 
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("Message: No body");
        } 
        catch (NumberFormatException nf) {
            throw new MalformedMessageException("Incorrect type for item " + index + ": Expected long, found " + deflate(body[index]));
        }
        return l;
    }
    
    /**
     *Gets an item if it can be representend by a char
     *@param index the position of the item in the message, with numbering starting at 0
     *@return the char representation of the item at the given position
     *@throws MalformedMessageException if the item cannot be represented as a char
     */
    public char getItemAsChar(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        String cs = this.getMessageItem(index);
        char c = cs.charAt(0);
        if (cs.length() > 1) {
            throw new MalformedMessageException("Incorrect type for item " + index + ": Expected char, found " + deflate(body[index]));
        }
        return c;
    }
    
    /**
     *Gets an item if it can be representend by a boolean
     *@param index the position of the item in the message, with numbering starting at 0
     *@return the boolean representation of the item at the given position
     *@throws MalformedMessageException if the item cannot be represented as a boolean
     */
    public boolean getItemAsBoolean(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        boolean b;
        try {
            b = Boolean.parseBoolean(new String(deflate(body[index])));
        } 
        catch (NullPointerException npe) {
            throw new IndexOutOfBoundsException("Message: No body");
        } 
        catch (Exception e) {
            throw new MalformedMessageException("Incorrect type for item " + index + ": Expected boolean, found " + deflate(body[index]));
        }
        return b;
    }

    /**
     * Gets the byte array representation of a message item
     * @param index the position of the item in the message, with numbering starting at 0
     * @return the byte array representation of the item at the given position
     */
    public byte[] getItemAsByteArray(int index) throws IndexOutOfBoundsException {
        byte[] ret = null;
        if (body != null) {
            ret = deflate(body[index]);
        }
        else {
            throw new IndexOutOfBoundsException("Message: Cannot get given item: No message body");
        }
        return ret;
    }
    
    /**
     *Sets the message header
     *@param header the header string, which must not contain the reserved character ':'
     */
    public void setHeader(String header) {
        if (header.indexOf(sepString) < 0) {
            this.header = header;
        }
        else throw new IllegalArgumentException("Message: Header must not contain the reserved character '" + sepString + "'");
    }

    /**
     * Adds the string representation of the given object to the message, following previously added items.
     * Uses the object's toString method
     * @param item the item to be added
     * @return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(Object item) {
        this.addBodyItem(inflate(item.toString().getBytes()));
        return body.length-1;
    }
    
    /**
     *Adds a string item to the message, following previously added items
     *@param item the item to be added
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(String item) {
        this.addBodyItem(inflate(item.getBytes()));
        return body.length-1;
    }
    
    /**
     *Adds an integer item to the message, following previously added items. 
     *@param item the item to be added
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(int item) {
        this.addBodyItem(inflate(Integer.toString(item).getBytes()));
        return body.length-1;
    }
    
    /**
     *Adds a double item to the message, following previously added items. 
     *@param item the item to be added
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(double item) {
        this.addBodyItem(inflate(Double.toString(item).getBytes()));
        return body.length-1;
    }
    
    /**
     *Adds a float item to the message, following previously added items. 
     *@param item the item to be added
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(float item) {
        this.addBodyItem(inflate(Float.toString(item).getBytes()));
        return body.length-1;
    }
    
    /**
     *Adds a long item to the message, following previously added items. 
     *@param item the item to be added
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(long item) {
        this.addBodyItem(inflate(Long.toString(item).getBytes()));
        return body.length-1;
    }
    
    /**
     *Adds a char item to the message, following previously added items. 
     *@param item the item to be added
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(char item) {
        byte[] b = new byte[1];
        b[0] = (byte) item;
        return this.addMessageItem(new String(b));
    }
    
    /**
     *Adds a boolean item to the message, following previously added items. 
     *@param item the item to be added
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(boolean item) {
        this.addBodyItem(inflate(Boolean.toString(item).getBytes()));
        return body.length-1;
    }

    /**
     * Adds a raw byte array as a message item, following previously added items.
     * @param item the item to be added
     * @return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(byte[] item) {
        this.addBodyItem(inflate(item));
        return body.length-1;
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the string representation of the item
     */
    public void editMessageItem(int index, String item) throws IndexOutOfBoundsException {
        body[index] = inflate(item.getBytes());
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the int representation of the item
     */
    public void editMessageItem(int index, int item) throws IndexOutOfBoundsException {
        body[index] = inflate(Integer.toString(item).getBytes());
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the double representation of the item
     */
    public void editMessageItem(int index, double item) throws IndexOutOfBoundsException {
        body[index] = inflate(Double.toString(item).getBytes());
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the float representation of the item
     */
    public void editMessageItem(int index, float item) throws IndexOutOfBoundsException {
        body[index] = inflate(Float.toString(item).getBytes());
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the char representation of the item
     */
    public void editMessageItem(int index, char item) throws IndexOutOfBoundsException {
        byte[] b = new byte[1];
        b[0] = (byte) item;
        body[index] = b;
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the boolean representation of the item
     */
    public void editMessageItem(int index, boolean item) throws IndexOutOfBoundsException {
        body[index] = inflate(Boolean.toString(item).getBytes());
    }

    /**
     * Allows the replacement of an existing item.
     * @param index the position of the item in the message, with numbering starting at 0
     * @param item the byte array representation of the item
     */
    public void editMessageItem(int index, byte[] item) throws IndexOutOfBoundsException {
        body[index] = inflate(item);
    }
    
    /**
     *@return the number of items in the message (excluding the header)
     */
    public int getMessageSize() {
        return (body == null) ? 0 : body.length;
    }
    
//    /**
//     *Adds escape characters to all existing sequences in a message item that can conflict with the special separator
//     *sequences used by this class.
//     */
//    private String inflate(String item) {
//        String ret = item.replaceAll(":/", "://");
//        ret = ret.replaceAll("::", ":/:");
//        return ret;
//    }
//
//    /**
//     *Reverses the result of inflate method
//     */
//    private String deflate(String item) {
//        String ret = item.replaceAll(":/", ":");
//        return ret;
//    }

    private byte[] inflate(byte[] byteItem) {
        byte[] ret = ArrayOperations.replicate(byteItem);
        int separatorIndex = ArrayOperations.seqFind(sepByte, ret);
        while (separatorIndex >= 0) { //  && separatorIndex < (ret.length-1)
            if (separatorIndex < (ret.length-1)) {
                if (ret[separatorIndex+1] == sepByte || ret[separatorIndex+1] == escByte) {
                    ret = ArrayOperations.insert(escByte, ret, ++separatorIndex);
                }
                separatorIndex = ArrayOperations.seqFind(sepByte, ret, separatorIndex+1);
            }
            else {
                ret = ArrayOperations.addElement(escByte, ret);
                separatorIndex = -1;
            }
        }
        return ret;
    }

    private byte[] deflate(byte[] byteItem) {
        byte[] ret = ArrayOperations.replicate(byteItem);
        int separatorIndex = ArrayOperations.seqFind(sepByte, ret);
        while (separatorIndex >= 0 && separatorIndex < (ret.length-1)) {
            if (ret[separatorIndex+1] == escByte) {
                ret = ArrayOperations.delete(ret, separatorIndex+1);
            }
            separatorIndex = ArrayOperations.seqFind(sepByte, ret, separatorIndex+1);
        }
        return ret;
    }

    private void addBodyItem(byte[] item) {
        if (body == null) {
            body = new byte[1][];
        }
        else {
            byte[][] aux = body;
            body = new byte[body.length+1][];
            for (int i = 0; i < aux.length; i++) {
                body[i] = aux[i];
            }
        }
        body[body.length-1] = item;
    }

}
