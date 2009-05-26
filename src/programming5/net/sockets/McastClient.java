/*
 * McastClient.java
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
import java.net.MulticastSocket;
import programming5.net.NetworkException;

/**
 *This class is the multicast socket implementation of a MessagingClient
 *@see programming5.net.MessagingClient
 *@see java.net.MulticastSocket
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class McastClient extends UDPClient {
    
    private static final String DEFAULTHOST = "230.0.0.1";
    private static final int DEFAULTREMOTEPORT = 4445;
    private static final int ANYPORT = -1;
    
    /**
     *Creates a multicast client for which the local port will be determined by an available port at the time of connection.
     *Messages will be sent by default to the localhost on port 4445.
     */
    public McastClient() throws NetworkException {
        super();
    }
    
    /**
     *Creates a multicast client on the specified local port. Messages will be sent by default to the localhost on port 4445.
     */
    public McastClient(int localPort) throws NetworkException {
        super(localPort);
    }
    
    /**
     *Creates a multicast client for the specified host address and remote port, to be bound on an available local port.
     *When establishConnection is called, no special connect message will be sent.
     */
    public McastClient(String address, int remotePort) throws NetworkException {
        super(address, remotePort);
    }
    
    /**
     *Creates a multicast client for the specified host address and the specified ports. When establishConnection is
     *called, no special connect message will be sent.
     */
    public McastClient(String address, int remotePort, int localPort) throws NetworkException {
        super(address, remotePort, localPort);
    }
    
    /**
     *Implementation of the PluggableClient interface. Opens the multicast socket and
     *starts the receiver thread. Will send a special connect message if specified in the instantiation.
     */
    public void establishConnection() throws NetworkException {
        try {
            if (localPort == ANYPORT) {
                socket = new MulticastSocket();
                localPort = socket.getLocalPort();
            } 
            else {
                socket = new MulticastSocket(localPort);
            }
            receiver = new UDPReceiver(this, socket);
            receiver.start();
        } 
        catch (IOException e) {
            throw new NetworkException("McastClient: Can't establish connection: " + e.getMessage());
        }
    }
    
}
