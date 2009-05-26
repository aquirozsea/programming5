/*
 * SimpleSerializer.java
 *
 * Copyright 2006 Andres Quiroz Hernandez
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

import java.io.NotSerializableException;
import java.lang.reflect.Field;
import programming5.arrays.ArrayOperations;
import programming5.net.MalformedMessageException;
import programming5.net.Message;

/**
 * Provides a Message object for instances of a class composed only of primitive types (or of the primitive types of the class), 
 * using the reflective capabilities of the class. The class for which this serializer is used must have a default constructor.
 * @see programming5.net.Message
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class SimpleSerializer<T> {
    
    Class<T> classRef;
    
    public SimpleSerializer(Class<T> myClass) {
        classRef = myClass;
    }
    
    /**
     *@return the message string the represents the serialization of the primitive fields of the given object
     */
    public String serialize(T obj) throws NotSerializableException {
        String ret = null;
        Message aux = new Message();
        aux.setHeader(classRef.getName());
        Field[] myFields = classRef.getDeclaredFields();
        try {
            for (Field field : myFields) {
                if (!field.getType().isArray()) {
                    Object value = field.get(this);
                    if (value != null) {
                        aux.addMessageItem(field.getName());
                        aux.addMessageItem(value.toString());
                    }
                }
                else {
                    Object[] array = (Object[]) field.get(this);
                    for (Object item : array) {
                        aux.addMessageItem(field.getName()); 
                        aux.addMessageItem(item.toString());
                    }
                }
            }
        }
        catch (IllegalAccessException iae) {
            throw new NotSerializableException("SimpleSerializer: Some fields were not accessible: " + iae.getMessage());
        }
        try {
            ret = aux.getMessage();
        }
        catch (MalformedMessageException mme) {
            mme.printStackTrace();
        }
        return ret;
    }
    
    /**
     *@return an instance of the object represented by the message string
     *@throws java.lang.IllegalArgumentException if the object cannot be created from the given string
     */
    public T deserialize(String objStr) {
        T ret = null;
        try {
            ret = classRef.newInstance();
            Message aux = new Message(objStr);
            for (int i = 0; i < aux.getMessageSize(); i++) {
                String fieldName = aux.getMessageItem(i);
                Field field = classRef.getDeclaredField(fieldName);
                if (!field.getType().isArray()) {
                    if (field.getType().isPrimitive()) {
                        if (field.getType().getName().equals("int")) {
                            field.set(ret, new Integer(aux.getMessageItem(++i)));
                        }
                    }
                    else if (field.getType().equals(String.class)) {
                        field.set(ret, aux.getMessageItem(++i));
                    }
                    else {
                        i++;
                    }
                }
                else {
                    if (field.getType().getComponentType().isPrimitive()) {
                        if (field.getType().getComponentType().getName().equals("int")) {
                            int[] array = null;
                            do {
                                array = ArrayOperations.addElement(new Integer(aux.getMessageItem(++i)), array);
                            }
                            while (aux.getMessageItem(++i).equals(fieldName));
                            i--;
                            field.set(ret, array);
                        }
                    }
                    else if (field.getType().getComponentType().equals(String.class)) {
                        String[] array = null;
                        do {
                            array = ArrayOperations.addElement(aux.getMessageItem(++i), array);
                        }
                        while (aux.getMessageItem(++i).equals(fieldName));
                        i--;
                        field.set(ret, array);
                    }
                    else {
                        i++;
                    }
                }
            }
        }
        catch (InstantiationException ie) {
            throw new IllegalArgumentException("SimpleSerializer: Could not create object from string: Class cannot be instantiated: " + ie.getMessage());
        }
        catch (MalformedMessageException mme) {
            throw new IllegalArgumentException("SimpleSerializer: Could not create object from string: Invalid format: " + mme.getMessage());
        }
        catch (NoSuchFieldException nsfe) {
            throw new IllegalArgumentException("SimpleSerializer: Could not create object from string: Contains a field not declared in the class: " + nsfe.getMessage());
        }
        catch (IllegalAccessException iae) {
            throw new IllegalArgumentException("SimpleSerializer: Could not create object from string: Some fields were not accessible: " + iae.getMessage());
        }
        return ret;
    }
    
}
