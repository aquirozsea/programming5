/*
 * Message.java
 *
 * Copyright 2008 Andres Quiroz Hernandez
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

import programming5.arrays.ArrayOperations;

/**
 *This class is used to store an organized array of strings in a message 
 *context of header and body items, to produce message strings of the   
 *following syntax: [header]:[item{::item}]<p>
 *There are no restrictions on the syntax of the items. If constructing a message string by hand, however, take care 
 *that all sequences of consecutive colons (::) correspond to message separators; otherwise, the message will be decoded 
 *incorrectly. Additionally, if colon-slash sequences (:/) exist in an item, they will be modified by the decoding 
 *methods. In both these cases, adding a slash after the first colon will allow correct decoding of the message items; 
 *this is precisely what is done when messages are encoded normally, to allow for arbitrary syntax of message items.
 *This class uses the String split method for message decoding.<p>
 *Note that the shortest valid message is a single colon (:) (message with no header and no body) and that all messages 
 *must have at least one colon.<p>
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class Message {
    
    protected String header, body[];
    
    /**
     *Constructor to create an empty message object
     */
    public Message() {
        header = null;
        body = null;
    }
    
    /**
     *Constructor to create a message object decoding a message string of an
     *unknown size. The message must follow the correct syntax or an exception is
     *thrown.
     */
    public Message(String message) throws MalformedMessageException {
        if (message != null) {
            body = message.split(":", 2);
            if (body.length == 2) {
                if (body[0].length() > 0) {
                    header = body[0];
                }
                else {
                    header = null;
                }
                if (!body[1].equals("")) {
                    body = body[1].split("::");
                }
                else {
                    body = null;
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
     */
    public Message(String message, int size) throws MalformedMessageException {
        body = message.split(":", 2);
        if (body.length == 2) {
            if (body[0].length() > 0) {
                header = body[0];
            }
            else {
                header = null;
            }
            body = body[1].split("::");
            if (body.length != size) {
                throw new MalformedMessageException("Incorrect number of message items");
            }
        } 
        else throw new MalformedMessageException("Message start not found");
    }
    
    /**
     *Creates a complete message from specific objects.
     *WARNING: This method uses the objects' toString method to obtain the item it will keep.
     */
    public static Message constructMessage(String header, Object... items) {
        Message ret = new Message();
        ret.setHeader(header);
        for (Object item : items) {
            ret.addMessageItem(item.toString());
        }
        return ret;
    }
    
    /**
     *Creates a message without a header from specific objects.
     *WARNING: This method uses the objects' toString method to obtain the item it will keep.
     */
    public static Message constructHeaderlessMessage(Object... items) {
        Message ret = new Message();
        for (Object item : items) {
            ret.addMessageItem(item.toString());
        }
        return ret;
    }
    
    /**
     *Gets the message string of a message object.
     */
    public String getMessage() throws MalformedMessageException {
        String message;
        if (header != null) {
            message = header + ":";
        }
        else {
            message = ":";
        }
        if (body != null) {
            try {
                message = message.concat(body[0]);
                for (int i = 1; i < body.length; i++) {
                    message = message.concat("::");
                    message = message.concat(body[i]);
                }
            } 
            catch (ArrayIndexOutOfBoundsException np) {
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
     *@param index the position of the item in the message, with numbering from 0 on
     */
    public String getMessageItem(int index) throws IndexOutOfBoundsException {
        String ret = null;
        if (body != null) {
            ret = deflate(body[index]);
        } 
        else throw new IndexOutOfBoundsException("Message: No body");
        return ret;
    }
    
    /**
     *Gets an item if it can be representend by an integer
     *@param index the position of the item in the message, with numbering from 0 on
     */
    public int getItemAsInt(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        int i;
        try {
            i = Integer.parseInt(deflate(body[index]));
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
     */
    public double getItemAsDouble(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        double d;
        try {
            d = Double.parseDouble(deflate(body[index]));
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
     */
    public float getItemAsFloat(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        float f;
        try {
            f = Float.parseFloat(deflate(body[index]));
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
     */
    public long getItemAsLong(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        long l;
        try {
            l = Long.parseLong(deflate(body[index]));
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
     */
    public char getItemAsChar(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        char c = ' ';
        if (body != null) {
            if (body[index].length() == 1) {
                c = body[index].charAt(0);
            }
            else throw new MalformedMessageException("Incorrect type for item " + index + ": Expected char, found " + deflate(body[index]));
        } 
        else throw new IndexOutOfBoundsException("Message: No body");
        return c;
    }
    
    /**
     *Gets an item if it can be representend by a boolean
     *@param index the position of the item in the message, with numbering starting at 0
     */
    public boolean getItemAsBoolean(int index) throws MalformedMessageException, IndexOutOfBoundsException {
        boolean b;
        try {
            b = Boolean.parseBoolean(deflate(body[index]));
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
     *Sets the message header
     */
    public void setHeader(String header) {
        this.header = header;
    }
    
    /**
     *Adds a string item to the message, following previously added items
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(String item) {
        body = ArrayOperations.addElement(inflate(item), body);
        return body.length-1;
    }
    
    /**
     *Adds an integer item to the message, following previously added items. Its is
     *converted to a string to be stored.
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(int item) {
        body = ArrayOperations.addElement(inflate(Integer.toString(item)), body);
        return body.length-1;
    }
    
    /**
     *Adds a double item to the message, following previously added items. Its is
     *converted to a string to be stored.
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(double item) {
        body = ArrayOperations.addElement(inflate(Double.toString(item)), body);
        return body.length-1;
    }
    
    /**
     *Adds a float item to the message, following previously added items. Its is
     *converted to a string to be stored.
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(float item) {
        body = ArrayOperations.addElement(inflate(Float.toString(item)), body);
        return body.length-1;
    }
    
    /**
     *Adds a long item to the message, following previously added items. Its is
     *converted to a string to be stored.
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(long item) {
        body = ArrayOperations.addElement(inflate(Long.toString(item)), body);
        return body.length-1;
    }
    
    /**
     *Adds a char item to the message, following previously added items. Its is
     *converted to a string to be stored.
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(char item) {
        char[] s = new char[1];
        s[0] = item;
        body = ArrayOperations.addElement(new String(s), body);
        return body.length-1;
    }
    
    /**
     *Adds a boolean item to the message, following previously added items. Its is
     *converted to a string to be stored.
     *@return the position of the item in the message, with numbering starting at 0
     */
    public int addMessageItem(boolean item) {
        body = ArrayOperations.addElement(inflate(Boolean.toString(item)), body);
        return body.length-1;
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the string representation of the item
     */
    public void editMessageItem(int index, String item) throws IndexOutOfBoundsException {
        body[index] = inflate(item);
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the int representation of the item
     */
    public void editMessageItem(int index, int item) throws IndexOutOfBoundsException {
        body[index] = inflate(Integer.toString(item));
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the double representation of the item
     */
    public void editMessageItem(int index, double item) throws IndexOutOfBoundsException {
        body[index] = inflate(Double.toString(item));
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the float representation of the item
     */
    public void editMessageItem(int index, float item) throws IndexOutOfBoundsException {
        body[index] = inflate(Float.toString(item));
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the char representation of the item
     */
    public void editMessageItem(int index, char item) throws IndexOutOfBoundsException {
        char[] s = new char[1];
        s[0] = item;
        body[index] = new String(s);
    }
    
    /**
     *Allows the replacement of an existing item.
     *@param index the position of the item in the message, with numbering starting at 0
     *@param item the boolean representation of the item
     */
    public void editMessageItem(int index, boolean item) throws IndexOutOfBoundsException {
        body[index] = inflate(Boolean.toString(item));
    }
    
    /**
     *@return the number of items in the message
     */
    public int getMessageSize() {
        int ret = 0;
        try {
            ret = body.length;
        } 
        catch (NullPointerException npe) {}
        return ret;
    }
    
    /**
     *Adds escape characters to all existing sequences in a message item that can conflict with the special separator
     *sequences used by this class.
     */
    private String inflate(String item) {
        String ret = item.replaceAll(":/", "://");
        ret = ret.replaceAll("::", ":/:");
        return ret;
    }
    
    /**
     *Reverses the result of inflate method
     */
    private String deflate(String item) {
        return item.replaceAll(":/", ":");
    }
}
