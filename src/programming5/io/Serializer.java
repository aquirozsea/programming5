/*
 * Serializer.java
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

package programming5.io;

import java.io.*;

/**
 *This class performs serialization and deserialization of serializable objects, as byte arrays or strings.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class Serializer {
    
    /**
     *@return the serialization of the given object as a byte array, using the ObjectOutputStream writeObject method.
     *@see java.io.ObjectOutputStream#writeObject
     *@throws NotSerializableException if the given object does not implement the Serializable interface
     */
    public static byte[] serializeBytes(Object obj) throws IOException {
        byte[] ret = null;
        if (obj instanceof Serializable) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            ret = out.toByteArray();
        }
        else throw new NotSerializableException();
        return ret;
    }
    
    /**
     *WARNING! Strings do not support certain byte values; may create a corrupted serialization; use only when byte array handling methods 
     *are unavailable so that strings must be used.
     *@return the serialization of the given object as a String, using the ObjectOutputStream writeObject method.
     *@see java.io.ObjectOutputStream#writeObject
     */
    public static String serialize(Object obj) throws IOException {
        String r = null;
        if (obj instanceof Serializable) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            r = out.toString();
        } 
        else {
            throw new NotSerializableException();
        }
        return r;
    }
    
    /**
     *@return the object represented by the byte array
     */
    public static Object deserialize(byte[] objBytes) throws IOException, ClassNotFoundException {
        Object r = null;
        ByteArrayInputStream in = new ByteArrayInputStream(objBytes);
        ObjectInputStream objIn = new ObjectInputStream(in);
        r = objIn.readObject();
        return r;
    }
    
    /**
     *@return the object represented by the given string
     */
    public static Object deserialize(String objStr) throws IOException, ClassNotFoundException {
        Object r = null;
        ByteArrayInputStream in = new ByteArrayInputStream(objStr.getBytes());
        ObjectInputStream objIn = new ObjectInputStream(in);
        r = objIn.readObject();
        return r;
    }
    
}