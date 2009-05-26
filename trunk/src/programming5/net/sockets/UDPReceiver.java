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
import programming5.arrays.ArrayOperations;
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
    private final int PACKET_SIZE = 65507;
    private boolean listening = true;
    
    public static final int NO_PORT = -1;
    
    public UDPReceiver(Publisher<MessageArrivedEvent> myReference, DatagramSocket mySocket) {
        super(myReference);
        socket = mySocket;
    }
    
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
                packetSize = p.getLength();
                if (packetSize > 0) {
                    bytesMessage = p.getData();
                    bytesMessage = ArrayOperations.prefix(bytesMessage, packetSize);
                }
                lastAddress = p.getAddress();
                lastPort = p.getPort();
                while (packetSize == PACKET_SIZE) {
                    p = new DatagramPacket(buf, buf.length);
                    socket.receive(p);
                    packetSize = p.getLength();
                    if (packetSize > 0) {
                        bytesMessage = ArrayOperations.join(bytesMessage, ArrayOperations.prefix(p.getData(), packetSize));
                    }
                }
            }
            catch (IOException io) {
                System.out.println("UDPReceiver: " + io.getMessage());
                end();
            }
            ref.fireEvent(new MessageArrivedEvent(bytesMessage));
            buf = new byte[PACKET_SIZE];
        }
    }

    public InetAddress getLastAddress() {
        return lastAddress;
    }

    public int getLastPort() {
        return lastPort;
    }
    
    // TODO: Make graceful exit
    public void end() {
        listening = false;
    }
}
