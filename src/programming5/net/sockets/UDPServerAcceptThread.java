/*
 * UDPServerAcceptThread.java
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

import programming5.net.MessagingClient;
import programming5.net.NetworkException;
import programming5.net.ServerAcceptThread;
import programming5.net.ServiceObject;

/**
 *This class implements a ServerAcceptThread that accepts UDP connections. It listens to the subscription port and passes each 
 *new client to the given service object.
 *@see programming5.net.ServerAcceptThread
 *@see programming5.net.ServiceObject
 *@see programming5.net.sockets.UDPClient
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class UDPServerAcceptThread extends ServerAcceptThread {
    
    protected UDPClient accepter;
    private boolean listening = true;
    private boolean reliable = false;
    private static final int DEFPORT = 4445;
    
    /**
     *Creates an accept thread that listens on the default port (4445).
     */
    public UDPServerAcceptThread(ServiceObject myServer) throws NetworkException {
        super(myServer);
        accepter = new UDPClient(DEFPORT);
        accepter.establishConnection();
    }
    
    /**
     *Creates an accept thread that listens on the given port.
     */
    public UDPServerAcceptThread(ServiceObject myServer, int port) throws NetworkException {
        super(myServer);
        accepter = new UDPClient(port);
        accepter.establishConnection();
    }
    
    /**
     *Creates a reliable accept thread that listens on the default port (4445).
     */
    public UDPServerAcceptThread(ServiceObject myServer, boolean useReliable) throws NetworkException {
        super(myServer);
        accepter = new UDPClient(DEFPORT);
        accepter.establishConnection();
        reliable = useReliable;
    }
    
    /**
     *Creates a reliable accept thread that listens on the given port.
     */
    public UDPServerAcceptThread(ServiceObject myServer, int port, boolean useReliable) throws NetworkException {
        super(myServer);
        accepter = new UDPClient(port);
        accepter.establishConnection();
        reliable = useReliable;
    }
    
    /**
     *Listens for connection messages and creates the new clients. Any message received on the thread's port will be 
     *considered a connection message.
     */
    public void run() {
        while (listening) {
            try {
                accepter.receive();
                MessagingClient c = null;
                int clientPort = 0;
                if (!reliable) {
                    c = new UDPClient(accepter.getHostAddress(), accepter.getHostPort());
                    c.establishConnection();
                    clientPort = ((UDPClient)c).getLocalPort();
                } 
                else {
                    c = new ReliableUDPClient(accepter.getHostAddress(), accepter.getHostPort());
                    c.establishConnection();
                    clientPort = ((ReliableUDPClient)c).getLocalPort();
                }
                accepter.send(Integer.toString(clientPort));
                serverRef.newClient(c);
            }
            catch (NetworkException ne) {
                throw new RuntimeException("UDPServerAcceptThread: Couldn't create new client: " + ne.getMessage());
            }
        }
    }
    
    public void end() {
        listening = false;
        accepter.endConnection();
    }
}
