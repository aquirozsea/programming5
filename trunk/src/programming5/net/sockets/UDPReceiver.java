/*
 * UDPReceiver.java
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

package programming5.net.sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import programming5.arrays.ArrayOperations;
import programming5.io.Debug;
import programming5.net.AsynchMessageArrivedEvent;
import programming5.net.MessageArrivedEvent;
import programming5.net.Publisher;
import programming5.net.ReceivingThread;

/**
 *This class complements the UDPClient class as a constant listener to the socket
 *@see programming5.net.sockets.UDPClient
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public class UDPReceiver extends ReceivingThread {
    
    protected DatagramSocket socket;
    protected InetAddress lastAddress = null;
    protected int lastPort = NO_PORT;
    private final int PACKET_SIZE = 65536;
    private boolean listening = true;

    private Hashtable<String, byte[][]> assembly = new Hashtable<String, byte[][]>();
    private Hashtable<String, boolean[]> assemblyCounter = new Hashtable<String, boolean[]>();
    
    public static final int NO_PORT = -1;
    
    public UDPReceiver(Publisher<MessageArrivedEvent> myReference, DatagramSocket mySocket) {
        super(myReference);
        socket = mySocket;
    }
    
    @Override
    public void run() {
        byte[] buf = new byte[PACKET_SIZE];
        byte[] bytesMessage;
        DatagramPacket p = null;
        int packetSize;
        while (listening) {
            p = new DatagramPacket(buf, buf.length);
            bytesMessage = null;
            try {
                socket.receive(p);
                String streamID = depacketize(p);
                if (streamID != null) {    // Message is completely received (all packets)
                    lastAddress = p.getAddress();
                    lastPort = p.getPort();
                    Debug.println("StreamID: " + streamID);
                    byte[][] toAssemble = assembly.get(streamID);
                    assembly.remove(streamID);
                    bytesMessage = assemble(toAssemble);
                    ref.fireEvent(new AsynchMessageArrivedEvent(bytesMessage, "//" + lastAddress.getHostAddress() + ":" + Integer.toString(lastPort)));
                }
                buf = new byte[PACKET_SIZE];
            }
            catch (IOException io) {
                if (listening) {
                    System.out.println("UDPReceiver: Error while receiving: " + io.getMessage());
                    listening = false;
                }
            }
        }
    }

    /**
     * @return the last address from which a message was received
     * @deprecated One of two methods to obtain a full address, in between which another message could be
     * received and the address could change; use getReplyAddress instead, which returns a URL
     */
    @Deprecated
    public InetAddress getLastAddress() {
        return lastAddress;
    }

    /**
     * @return the last port from which a message was received
     * @deprecated One of two methods to obtain a full address, in between which another message could be
     * received and the address could change; use getReplyAddress instead, which returns a URL string
     */
    @Deprecated
    public int getLastPort() {
        return lastPort;
    }

    /**
     * @return the last address from which a message was received; if no message was received when called, 
     * null will be returned.
     */
    public String getReplyAddress() {
        String ret = null;
        try {
            if (lastAddress != null) {
                ret = new URI("//" + lastAddress.getHostAddress() + ":" + Integer.toString(lastPort)).toString();
            }
        }
        catch (URISyntaxException use) {}
        finally {
            return ret;
        }
    }
    
    protected void end() {
        listening = false;
    }

    private String depacketize(DatagramPacket packet) {
        int packetSize = packet.getLength();
        byte[] bytesMessage;
        String ret = null;
        if (packetSize > 0) {
            InetAddress host = packet.getAddress();
            bytesMessage = packet.getData();
            bytesMessage = ArrayOperations.prefix(bytesMessage, packetSize);
            int separatorIndex = ArrayOperations.seqFind(UDPClient.SEPARATOR.getBytes()[0], bytesMessage);
            if (separatorIndex > 0) {
                String sequenceString = new String(ArrayOperations.subArray(bytesMessage, 0, separatorIndex));
                Debug.println("Packet " + sequenceString);
                String[] numbers = sequenceString.split("/");
                String streamID = host.getHostAddress() + "/" + numbers[0];
                Debug.println("StreamID: " + streamID);
                byte[][] parts = assembly.get(streamID);
                if (parts == null) {
                    int total = Integer.parseInt(numbers[2]);
                    parts = new byte[total][];
                    assembly.put(streamID, parts);
                    boolean[] counter = new boolean[total];
                    ArrayOperations.initialize(counter, false);
                    assemblyCounter.put(streamID, counter);
                    Debug.println("Total packets: " + total);
                }
                int index = Integer.parseInt(numbers[1]);
                parts[index-1] = ArrayOperations.suffix(bytesMessage, separatorIndex+1);
                boolean[] progress = assemblyCounter.get(streamID);
                progress[index-1] = true;
                if (ArrayOperations.tautology(progress)) {
                    ret = streamID;
                    assemblyCounter.remove(streamID);
                }
            }
            else {
                int start = (separatorIndex == 0) ? 1 : 0;
                byte[][] parts = new byte[1][];
                parts[0] = ArrayOperations.suffix(bytesMessage, start);
                ret = host.getHostAddress();
                assembly.put(ret, parts);
            }
        }
        return ret;
    }

    private byte[] assemble(byte[][] parts) {
        int size = 0;
        for (byte[] part : parts) {
            size += part.length;
        }
        byte[] ret = new byte[size];
        int place = 0;
        for (int i = 0; i < parts.length; i++) {
            for (int j = 0; j < parts[i].length; j++) {
                ret[place++] = parts[i][j];
            }
        }
        return ret;
    }

}
