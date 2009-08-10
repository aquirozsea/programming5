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
import programming5.net.MessageArrivedEvent;
import programming5.net.Publisher;
import programming5.net.ReceivingThread;

/**
 *This class complements the UDPClient class as a constant listener to the socket
 *@see programming5.net.sockets.UDPClient
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class UDPReceiver extends ReceivingThread {
    
    protected DatagramSocket socket;
    protected InetAddress lastAddress = null;
    protected int lastPort = NO_PORT;
    private final int PACKET_SIZE = 65536;
    private boolean listening = true;

    private Hashtable<InetAddress, byte[][]> assembly = new Hashtable<InetAddress, byte[][]>();
    private Hashtable<InetAddress, Integer> assemblyCounter = new Hashtable<InetAddress, Integer>();
    
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
                boolean messageComplete = depacketize(p);
                if (messageComplete) {
                    lastAddress = p.getAddress();
                    lastPort = p.getPort();
                    Debug.println("Received from " + lastPort);
                    byte[][] toAssemble = assembly.get(lastAddress);
                    assembly.remove(lastAddress);
                    bytesMessage = toAssemble[0];
                    for (int i = 1; i < toAssemble.length; i++) {
                        bytesMessage = ArrayOperations.join(bytesMessage, toAssemble[i]);
                    }
                    ref.fireEvent(new MessageArrivedEvent(bytesMessage));
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
            Debug.println("Replying to: " + ret);
        }
        catch (URISyntaxException use) {}
        finally {
            return ret;
        }
    }
    
    public void end() {
        listening = false;
    }

    private boolean depacketize(DatagramPacket packet) {
        int packetSize = packet.getLength();
        byte[] bytesMessage;
        boolean ret = false;
        if (packetSize > 0) {
            InetAddress host = packet.getAddress();
            byte[][] parts = assembly.get(host);
            boolean initialize = (parts == null);
            bytesMessage = packet.getData();
            bytesMessage = ArrayOperations.prefix(bytesMessage, packetSize);
            int separatorIndex = ArrayOperations.seqFind(UDPClient.SEPARATOR.getBytes()[0], bytesMessage);
            if (separatorIndex > 0) {
                String sequenceString = new String(ArrayOperations.subArray(bytesMessage, 0, separatorIndex));
                String[] numbers = sequenceString.split("/");
                if (initialize) {
                    int total = Integer.parseInt(numbers[1]);
                    parts = new byte[total][];
                    assembly.put(host, parts);
                    assemblyCounter.put(host, total);
                }
                int index = Integer.parseInt(numbers[0]);
                parts[index-1] = ArrayOperations.suffix(bytesMessage, separatorIndex+1);
                int left = assemblyCounter.get(host);
                if (left == 1) {
                    ret = true;
                    assemblyCounter.remove(host);
                }
                else {
                    assemblyCounter.put(host, left-1);
                }
            }
            else {
                parts = new byte[1][];
                parts[0] = ArrayOperations.suffix(bytesMessage, separatorIndex+1);
                assembly.put(host, parts);
            }
        }
        return ret;
    }

}
