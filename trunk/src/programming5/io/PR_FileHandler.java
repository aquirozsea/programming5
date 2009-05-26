/*
 * PR_FileHandler.java
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

package programming5.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import programming5.arrays.ArrayOperations;
import programming5.io.FileHandler.FileType;
import programming5.io.FileHandler.HandleMode;
import programming5.net.Subscriber;

/**
 * Progress reporting file handler; provides versions of file handling methods that receive a listener of execution progress events.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class PR_FileHandler extends FileHandler {
    
    /**
     *Default constructor; a file must be set before any of the non-static methods can be used
     *@see #setFile(String)
     *@see #setFile(String, HandleMode)
     */
    public PR_FileHandler() {
    }
    
    /**
     *Creates a handler for the file in filepath. As a default, the type of the file is set to text, and new
     *data will be appended to existing data. If the file does not exist, it will be created.
     */
    public PR_FileHandler(String filepath) throws IOException {
        super(filepath);
    }
    
    /**
     *Creates a handler for the file in filepath, to be handled according to the handle mode. As a default, the type
     *of the file is set to text.
     */
    public PR_FileHandler(String filepath, HandleMode mode) throws IOException {
        super(filepath, mode);
    }
    
    /**
     *Creates a handler for the file in filepath, setting the type to the type given. As a default, new
     *data will be appended to existing data. If the file does not exist, it will be created.
     */
    public PR_FileHandler(String filepath, FileType fileType) throws IOException {
        super(filepath, fileType);
    }
    
    /**
     *Creates a handler for the file in filepath, setting the type to the type given, and to be handled according to the
     *handle mode given.
     */
    public PR_FileHandler(String filepath, HandleMode mode, FileType fileType) throws IOException {
        super(filepath, mode, fileType);
    }
    
    /**
     *The write method is overloaded to write the basic data types and arrays of these types. According to the file type,
     *the data will be written as plain text or in byte representation.
     */
    public void write(String data, Subscriber<ExecutionProgressEvent> progressListener) throws IOException {
        if (out != null) {
            switch (type) {
                
                case TEXT:
                    progressListener.signalEvent(new ExecutionProgressEvent(0));
                    out.writeBytes(data);
                    size += data.length();
                    progressListener.signalEvent(new ExecutionProgressEvent(1));
                    break;
                    
                case BINARY:
                    byte[] aux = data.getBytes();
                    int total = aux.length;
                    progressListener.signalEvent(new ExecutionProgressEvent(0));
                    int byteCount = 0;
                    for (byte b : aux) {
                        out.writeByte(b);
                        progressListener.signalEvent(new ExecutionProgressEvent((++byteCount) / (float) total));
                    }
                    size += aux.length;
                    break;
                    
            }
        } 
        else throw new IOException("FileHandler: No file");
    }
    
    /**
     *The writeln method works like the write methods, adding a newline character after the data. This method is
     *especially useful for creating text files, for which the specialized read methods can only work properly if each
     *datum to be read is in its own line.
     */
    public void writeln(String data, Subscriber<ExecutionProgressEvent> progressListener) throws IOException {
        write(data, progressListener);
        writeln();
    }
    
    /**
     *Plain write methods for arrays write each array element in its own line, if writing text data, or each directly
     *after the other, for binary files.
     */
    public void write(String[] array) throws IOException {
        for (String elem : array) {
            writeln(elem);
        }
    }
    
    /**
     *writeArray methods write the array elements horazontally, using the given separator between them. If the file is
     *of text type, they end with a newline.
     */
    public void writeArray(String[] array, String separator) throws IOException {
        write(array[0]);
        for (int i = 1; i < array.length; i++) {
            write(separator + array[i]);
        }
        writeln();
    }
    
    /**
     *Although meant for binary files, the write byte methods can also be used for text files, provided the user
     *decodes the data correctly.
     */
    public void write(byte b) throws IOException {
        if (out != null) {
            out.writeByte(b);
            size += 1;
        } else throw new IOException("FileHandler: No file");
    }
    
    public void writeln(byte b) throws IOException {
        write(b);
        writeln();
    }
    
    /**
     *This is the only function for writing byte arrays, as using separators between bytes does not make much sense.
     */
    public void write(byte[] array) throws IOException {
        for (byte elem : array) {
            write(elem);
            if (type == FileType.TEXT) {
                writeln();
            }
        }
    }
    
//	public void writeArray(byte[] array, String separator) throws IOException {
//		write(array[0]);
//		for (int i = 1; i < array.length; i++) {
//			write(separator);
//			write(array[i]);
//		}
//		if (type == FileType.TEXT) {
//			writeln();
//		}
//	}
    
    public void write(int number) throws IOException {
        if (out != null) {
            switch (type) {
                
                case TEXT:
                    String aux = Integer.toString(number);
                    out.writeBytes(aux);
                    size += aux.length();
                    break;
                    
                case BINARY:
                    out.writeInt(number);
                    size += 4;	//TODO: Generalize
                    break;
                    
            }
        } else throw new IOException("FileHandler: No file");
    }
    
    public void writeln(int number) throws IOException {
        write(number);
        writeln();
    }
    
    public void write(int[] array) throws IOException {
        for (int elem : array) {
            switch (type) {
                case TEXT: writeln(elem);
                break;
                case BINARY: write(elem);
                break;
            }
        }
    }
    
    public void writeArray(int[] array, String separator) throws IOException {
        write(array[0]);
        for (int i = 1; i < array.length; i++) {
            write(separator);
            write(array[i]);
        }
        if (type == FileType.TEXT) {
            writeln();
        }
    }
    
    public void write(float number) throws IOException {
        if (out != null) {
            switch (type) {
                
                case TEXT:
                    String aux = Float.toString(number);
                    out.writeBytes(aux);
                    size += aux.length();
                    break;
                    
                case BINARY:
                    out.writeFloat(number);
                    size += 8;	//TODO: Check size
                    break;
            }
        } else throw new IOException("FileHandler: No file");
    }
    
    public void writeln(float number) throws IOException {
        write(number);
        writeln();
    }
    
    public void write(float[] array) throws IOException {
        for (float elem : array) {
            switch (type) {
                case TEXT: writeln(elem);
                break;
                case BINARY: write(elem);
                break;
            }
        }
    }
    
    public void writeArray(float[] array, String separator) throws IOException {
        write(array[0]);
        for (int i = 1; i < array.length; i++) {
            write(separator);
            write(array[i]);
        }
        if (type == FileType.TEXT) {
            writeln();
        }
    }
    
    public void write(double number) throws IOException {
        if (out != null) {
            switch (type) {
                
                case TEXT:
                    String aux = Double.toString(number);
                    out.writeBytes(aux);
                    size += aux.length();
                    break;
                    
                case BINARY:
                    out.writeDouble(number);
                    size += 12;		//TODO: Check size
                    break;
            }
        } else throw new IOException("FileHandler: No file");
    }
    
    public void writeln(double number) throws IOException {
        write(number);
        writeln();
    }
    
    public void write(double[] array) throws IOException {
        for (double elem : array) {
            switch (type) {
                case TEXT: writeln(elem);
                break;
                case BINARY: write(elem);
                break;
            }
        }
    }
    
    public void writeArray(double[] array, String separator) throws IOException {
        write(array[0]);
        for (int i = 1; i < array.length; i++) {
            write(separator);
            write(array[i]);
        }
        if (type == FileType.TEXT) {
            writeln();
        }
    }
    
    public void write(char c) throws IOException {
        if (out != null) {
            switch (type) {
                case TEXT:
                    char[] aux = new char[1];
                    aux[0] = c;
                    write(new String(aux));
                    size += 1;
                    break;
                    
                case BINARY:
                    out.writeChar(c);
                    size += 1;		//TODO: Check size
                    break;
                    
            }
        } else throw new IOException("FileHandler: No file");
    }
    
    public void writeln(char c) throws IOException {
        write(c);
        writeln();
    }
    
    public void write(char[] array) throws IOException {
        for (char elem : array) {
            switch (type) {
                case TEXT: writeln(elem);
                break;
                case BINARY: write(elem);
                break;
            }
        }
    }
    
    public void writeArray(char[] array, String separator) throws IOException {
        write(array[0]);
        for (int i = 1; i < array.length; i++) {
            write(separator);
            write(array[i]);
        }
        if (type == FileType.TEXT) {
            writeln();
        }
    }
    
    /**
     *Writes an object as an array of bytes (only for binary files).
     */
    public void write(Serializable object) throws IOException {
        if (out != null) {
            if (type == FileType.BINARY) {
                write(Serializer.serializeBytes(object));
            } else throw new IOException("FileHandler: Objects can only be written and read as binary files");
        } else throw new IOException("FileHandler: No file");
    }
    
    /**
     *Inserts a tab character in the file
     */
    public void tab() throws IOException {
        write("\t");
    }
    
    /**
     *Inserts the number of consecutive tab characters given by times
     */
    public void tab(int times) throws IOException {
        for (int i = 0; i < times; i++) {
            tab();
        }
    }
    
    /**
     *Reads until a newline character is found and returns the data as a string.
     *NOTE: This method replaces the generic read method in the previous Programming implementation, which was discarded
     *for not having a clear meaning for each of the file types.
     */
    public String readLine() throws IOException {
        String ret = null;
        switch (type) {
            case TEXT:
                if (inT != null) {
                    ret = inT.readLine();
                } else throw new IOException("FileHandler: No file");
                break;
                
            case BINARY:
                if (inB != null) {
                    byte[] buffer = new byte[(int)size];
                    int i = 0;
                    for (; i < size; i++) {
                        try {
                            buffer[i] = inB.readByte();
                            if (buffer[i] == '\n') {
                                break;
                            }
                        } catch (java.io.EOFException eofe) {
                            break;
                        }
                    }
                    if (i > 0) {
                        ret = new String(ArrayOperations.prefix(buffer, i));
                    }
                } else throw new IOException("FileHandler: No file");
                break;
                
        }
        return ret;
    }
    
    /**
     *Returns an array with the number of strings given, reading each one from a separate line in the file.
     */
    public String[] readStrings(int num) throws IOException {
        String[] ret = new String[num];
        for (int i = 0; i < num; i++)
            ret[i] = readLine();
        return ret;
    }
    
    /**
     *Returns an array of the strings of a line in the file, using the given separator to distinguish them.
     */
    public String[] readStrings(String separator) throws IOException {
        String chain = readLine();
        String[] ret = null;
        if (chain != null) {
            ret = chain.split(separator);
        }
        return ret;
    }
    
    /**
     *Reads an integer from the file. If the file is of text type, the integer to be read must be on its own on a single
     *line.
     */
    public int readInt() throws IOException, NumberFormatException {
        int ret = 0;
        switch (type) {
            case TEXT:
                if (inT != null) {
                    String number = inT.readLine();
                    ret = Integer.parseInt(number);
                } else throw new IOException("FileHandler: No file");
                break;
                
            case BINARY:
                if (inB != null) {
                    ret = inB.readInt();
                } else throw new IOException("FileHandler: No file");
                break;
        }
        return ret;
    }
    
    /**
     *Returns an array with the number of ints given. If the file is of text type, each integer must be on its own on a
     *single line.
     */
    public int[] readInts(int num) throws IOException, NumberFormatException {
        int[] ret = new int[num];
        for (int i = 0; i < num; i++)
            ret[i] = readInt();
        return ret;
    }
    
    /**
     *Returns an array with the number of ints found on a line in the file, using the given separator to distinguish
     *them.
     */
    public int[] readInts(String separator) throws IOException, NumberFormatException {
        int[] ret = null;
        switch (type) {
            case TEXT:
                String[] numbers = readStrings(separator);
                ret = new int[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    ret[i] = Integer.parseInt(numbers[i]);
                }
                break;
                
            case BINARY:
                byte[] auxsep = separator.getBytes();
                boolean reading = true;
                while (reading) {
                    ret = ArrayOperations.addElement(readInt(), ret);
                    for (byte b : auxsep) {
                        try {
                            if (b != inB.readByte()) {
                                reading = false;
                                break;
                            }
                        } catch (java.io.EOFException eofe) {
                            reading = false;
                            break;
                        }
                    }
                }
                break;
                
        }
        return ret;
    }
    
    /**
     *Reads a float from the file. If the file is of text type, the number to be read must be on its own on a single
     *line.
     */
    public float readFloat() throws IOException, NumberFormatException {
        float ret = 0;
        switch (type) {
            case TEXT:
                if (inT != null) {
                    String number = inT.readLine();
                    ret = Float.parseFloat(number);
                } else throw new IOException("FileHandler: No file");
                break;
                
            case BINARY:
                if (inB != null) {
                    ret = inB.readFloat();
                } else throw new IOException("FileHandler: No file");
                break;
        }
        return ret;
    }
    
    /**
     *Returns an array with the number of floats given. If the file is of text type, each number must be on its own on a
     *single line.
     */
    public float[] readFloats(int num) throws IOException, NumberFormatException {
        float[] ret = new float[num];
        for (int i = 0; i < num; i++)
            ret[i] = readFloat();
        return ret;
    }
    
    /**
     *Returns an array with the number of floats found on a line in the file, using the given separator to distinguish
     *them.
     */
    public float[] readFloats(String separator) throws IOException, NumberFormatException {
        float[] ret = null;
        switch (type) {
            
            case TEXT:
                String[] numbers = readStrings(separator);
                ret = new float[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    ret[i] = Float.parseFloat(numbers[i]);
                }
                break;
                
            case BINARY:
                byte[] auxsep = separator.getBytes();
                boolean reading = true;
                while (reading) {
                    ret = ArrayOperations.addElement(readFloat(), ret);
                    for (int i = 0; i < auxsep.length; i++) {
                        try {
                            if (auxsep[i] != inB.readByte()) {
                                reading = false;
                                break;
                            }
                        } catch (java.io.EOFException eofe) {
                            reading = false;
                            break;
                        }
                    }
                }
                break;
                
        }
        return ret;
    }
    
    /**
     *Reads an double from the file. If the file is of text type, the number to be read must be on its own on a single
     *line.
     */
    public double readDouble() throws IOException, NumberFormatException {
        double ret = 0;
        switch (type) {
            case TEXT:
                if (inT != null) {
                    String number = inT.readLine();
                    ret = Double.parseDouble(number);
                } else throw new IOException("FileHandler: No file");
                break;
                
            case BINARY:
                if (inB != null) {
                    ret = inB.readDouble();
                } else throw new IOException("FileHandler: No file");
                break;
        }
        return ret;
    }
    
    /**
     *Returns an array with the number of doubles given. If the file is of text type, each number must be on its own on a
     *single line.
     */
    public double[] readDoubles(int num) throws IOException, NumberFormatException {
        double[] ret = new double[num];
        for (int i = 0; i < num; i++)
            ret[i] = readDouble();
        return ret;
    }
    
    /**
     *Returns an array with the number of doubles found on a line in the file, using the given separator to distinguish
     *them.
     */
    public double[] readDoubles(String separator) throws IOException, NumberFormatException {
        double[] ret = null;
        switch (type) {
            
            case TEXT:
                String[] numbers = readStrings(separator);
                ret = new double[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    ret[i] = Double.parseDouble(numbers[i]);
                }
                break;
                
            case BINARY:
                byte[] auxsep = separator.getBytes();
                boolean reading = true;
                while (reading) {
                    ret = ArrayOperations.addElement(readDouble(), ret);
                    for (int i = 0; i < auxsep.length; i++) {
                        try {
                            if (auxsep[i] != inB.readByte()) {
                                reading = false;
                                break;
                            }
                        } catch (java.io.EOFException eofe) {
                            reading = false;
                            break;
                        }
                    }
                }
                break;
                
        }
        return ret;
    }
    
    /**
     *Reads a byte from the file. If the file is of text type, the byte to be read must be on its own on a single
     *line.
     */
    public byte readByte() throws IOException {
        byte ret = Byte.MAX_VALUE;
        switch (type) {
            
            case TEXT:
                if (inT != null) {
                    String aux = inT.readLine();
                    if (aux != null) {
                        byte[] aux2 = aux.getBytes();
                        ret = aux2[0];
                    } else throw new java.io.EOFException();
                } else throw new IOException("FileHandler: No file");
                break;
                
            case BINARY:
                if (inB != null) {
                    ret = inB.readByte();
                } else throw new IOException("FileHandler: No file");
                break;
                
        }
        return ret;
    }
    
    /**
     *Returns an array with the number of bytes given. If the file is of text type, each byte must be on its own on a
     *single line.
     */
    public byte[] readBytes(int num) throws IOException {
        byte[] ret = new byte[num];
        for (int i = 0; i < num; i++)
            ret[i] = readByte();
        return ret;
    }
    
    /**
     *Reads the entire file in bytes
     */
    public byte[] readFully() throws IOException {
        return readBytes((int) size);
    }
    
    /**
     *Reads a char from the file. If the file is of text type, the character to be read must be on its own on a single
     *line.
     */
    public char readChar() throws IOException {
        char ret = '\0';
        switch (type) {
            
            case TEXT:
                if (inT != null) {
                    String aux = inT.readLine();
                    if (aux != null) {
                        byte[] aux2 = aux.getBytes();
                        ret = (char)aux2[0];
                    } else throw new java.io.EOFException();
                } else throw new IOException("FileHandler: No file");
                break;
                
            case BINARY:
                if (inB != null) {
                    ret = inB.readChar();
                } else throw new IOException("FileHandler: No file");
                break;
                
        }
        return ret;
    }
    
    /**
     *Returns an array with the number of chars given. If the file is of text type, each character must be on its own on a
     *single line.
     */
    public char[] readChars(int num) throws IOException {
        char[] ret = new char[num];
        for (int i = 0; i < num; i++)
            ret[i] = readChar();
        return ret;
    }
    
    /**
     *Returns an array with the number of chars found on a line in the file, using the given separator to distinguish
     *them.
     */
    public char[] readChars(String separator) throws IOException {
        char[] ret = null;
        switch (type) {
            
            case TEXT:
                String[] chars = readStrings(separator);
                ret = new char[chars.length];
                byte[] aux;
                for (int i = 0; i < chars.length; i++) {
                    aux = chars[i].getBytes();
                    ret[i] = (char)aux[0];
                }
                break;
                
            case BINARY:
                byte[] auxsep = separator.getBytes();
                boolean reading = true;
                while (reading) {
                    ret = ArrayOperations.addElement(readChar(), ret);
                    for (int i = 0; i < auxsep.length; i++) {
                        try {
                            if (auxsep[i] != inB.readByte()) {
                                reading = false;
                                break;
                            }
                        } catch (java.io.EOFException eofe) {
                            reading = false;
                            break;
                        }
                    }
                }
                break;
                
        }
        return ret;
    }
    
    /**
     *Returns an object found on its own in the file, which must be opened as binary in order to be read.
     */
    public Object readObject() throws IOException {
        Object ret = null;
        try {
            if (type == FileType.BINARY) {
                if (inB != null) {
                    byte[] buffer = new byte[(int)size];
                    inB.readFully(buffer);
                    ret = Serializer.deserialize(buffer);
                } else throw new IOException("FileHandler: No file");
            } 
            else throw new IOException("FileHandler: Objects can only be written and read as binary files");
        }
        catch (ClassNotFoundException cnfe) {
            throw new IOException("FileHandler: Could not read object: Unable to deserialize: " + cnfe.getMessage());
        }
        return ret;
    }
    
    /**
     *Copies files or directories from the source path to the destination path. It will throw an exception if a file
     *with the same name as a source file already exists at the destination.
     */
    public static void copy(String sourcePath, String destPath) throws IOException {
        File source = new File(sourcePath);
        if (source.isDirectory()) {
            String[] existing = null;
            destPath = destPath.concat("/" + source.getName());
            File[] files = source.listFiles();
            for (File file : files) {
                try {
                    copy(file.getAbsolutePath(), destPath);
                } catch (OverwriteException oe) {
                    if (existing == null) {
                        existing = oe.getSources();
                    } else {
                        existing = ArrayOperations.join(existing, oe.getSources());
                    }
                }
            }
            if (existing != null) {
                throw new OverwriteException(existing);
            }
        } else {
            DataInputStream in = new DataInputStream(new FileInputStream(source));
            if (in != null) {
                File auxfile = new File(destPath);
                auxfile.mkdirs();
                String destFile = destPath.concat("/" + source.getName());
                auxfile = null;
                auxfile = new File(destFile);
                if (!auxfile.isFile()) {
                    DataOutputStream copier = new DataOutputStream(new FileOutputStream(destFile));
                    byte[] buffer = new byte[(int)source.length()];
                    in.readFully(buffer);
                    copier.write(buffer);
                    copier.close();
                } else throw new OverwriteException(source.getAbsolutePath());
            }
        }
    }
    
    /**
     *Copies files or directories from the source path to the destination path. If a file with the same name as a
     *source file already exists at the destination, it will be overwritten.
     *WARNING: The file will be copied incorrectly if the destination path is the same as the source path.
     */
    public static void copyOver(String sourcePath, String destPath) throws IOException {
        File source = new File(sourcePath);
        if (source.isDirectory()) {
            destPath = destPath.concat("/" + source.getName());
            File[] files = source.listFiles();
            for (File file : files) {
                copyOver(file.getAbsolutePath(), destPath);
            }
        } else {
            DataInputStream in = new DataInputStream(new FileInputStream(source));
            if (in != null) {
                File auxfile = new File(destPath);
                auxfile.mkdirs();
                String destFile = destPath.concat("/" + source.getName());
                auxfile = null;
                auxfile = new File(destFile);
//                DataOutputStream copier = new DataOutputStream(new FileOutputStream(destFile, HandleMode.OVERWRITE.fileSwitch));
//                byte[] buffer = new byte[(int)source.length()];
//                in.readFully(buffer);
//                copier.write(buffer);
//                copier.close();
            }
        }
    }
    
    /**
     *Copies the files and directories from the source directory, without copying said directory. Same conditions as
     *copy. The source path must be an existing directory.
     *@see #copy(String, String)
     */
    public static void copyContents(String sourcePath, String destPath) throws IOException {
        File source = new File(sourcePath);
        if (source.isDirectory()) {
            String[] existing = null;
            File[] files = source.listFiles();
            for (File file : files) {
                try {
                    copy(file.getAbsolutePath(), destPath);
                } catch (OverwriteException oe) {
                    if (existing == null) {
                        existing = oe.getSources();
                    } else {
                        existing = ArrayOperations.join(existing, oe.getSources());
                    }
                }
            }
            if (existing != null) {
                throw new OverwriteException(existing);
            }
        } else throw new IOException("FileHandler: Invalid source: Must be a valid directory");
    }
    
    /**
     *Copies the files and directories from the source directory, without copying said directory. Same conditions as
     *copyOver. The source path must be an existing directory.
     *@see #copyOver(String, String)
     */
    public static void copyContentsOver(String sourcePath, String destPath) throws IOException {
        File source = new File(sourcePath);
        if (source.isDirectory()) {
            File[] files = source.listFiles();
            for (File file : files) {
                copyOver(file.getAbsolutePath(), destPath);
            }
        } else throw new IOException("FileHandler: Invalid source: Must be a valid directory");
    }
    
    /**
     *Sets or resets the file for the handler
     */
    public void setFile(String filepath) throws IOException {
        if (inB != null) {
            inB.close();
        }
        if (inT != null) {
            inT.close();
        }
        if (out != null) {
            out.flush();
            out.close();
        }
//        out = new DataOutputStream(new FileOutputStream(filepath, HandleMode.APPEND.fileSwitch));
        switch (type) {
            case TEXT: inT = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
            break;
            case BINARY: inB = new DataInputStream(new FileInputStream(filepath));
            break;
        }
        File auxfile = new File(filepath);
        name = auxfile.getName();
        path = filepath;
    }
    
    /**
     *Sets or resets the file for the handler in a given handle mode.
     */
    public void setFile(String filepath, HandleMode mode) throws IOException {
        if (inB != null) {
            inB.close();
        }
        if (inT != null) {
            inT.close();
        }
        if (out != null) {
            out.flush();
            out.close();
        }
//        out = new DataOutputStream(new FileOutputStream(filepath, mode.fileSwitch));
        switch (type) {
            case TEXT: inT = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
            break;
            case BINARY: inB = new DataInputStream(new FileInputStream(filepath));
            break;
        }
        File auxfile = new File(filepath);
        name = auxfile.getName();
        path = filepath;
    }
    
    public void close() throws IOException {
        if (inB == null && inT == null && out == null) throw new RuntimeException("File not set");
        else {
            if (inB != null) {
                inB.close();
            }
            if (inT != null) {
                inT.close();
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
    
    /**
     *WARNING: If reading the size of a file which has been written to, the value might be inaccurate.
     *TODO: Fix
     */
    public long getFileSize() {
        return size;
    }
    
    public static boolean fileExists(String path) {
        File checkFile = new File(path);
        return checkFile.isFile();
    }

}
