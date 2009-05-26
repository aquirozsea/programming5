/*
 * TCPServerAcceptThread.java
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
import java.net.ServerSocket;
import programming5.net.NetworkException;
import programming5.net.ServerAcceptThread;
import programming5.net.ServiceObject;

/**
 *This class implements a ServerAcceptThread that accepts TCP connections. It listens to the subscription port and passes each 
 *new client to the given service object.
 *@see programming5.net.ServerAcceptThread
 *@see programming5.net.ServiceObject
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class TCPServerAcceptThread extends ServerAcceptThread {
    
    protected ServerSocket accepter;
    private boolean listening = true;
    
    /**
     *Creates an accept thread that listens on an available port (which can be retrieved with the getLocalPort method).
     *@see #getLocalPort
     */
    public TCPServerAcceptThread(ServiceObject myServer) throws NetworkException {
        super(myServer);
        try {
            accepter = new ServerSocket(0);
        }
	catch (IOException ioe) {
            throw new NetworkException("TCPServerAcceptThread: Couldn't create server socket: " + ioe.getMessage());
        }
    }
    
    /**
     *Creates an accept thread that listens on the given port.
     */
    public TCPServerAcceptThread(ServiceObject myServer, int port) throws NetworkException {
        super(myServer);
        try {
            if (port < 0) {
                port = 0;
            }
            accepter = new ServerSocket(port);
        }
	catch (IOException ioe) {
            throw new NetworkException("TCPServerAcceptThread: Couldn't create server socket: " + ioe.getMessage());
        }
    }
    
    /**
     *@return the port on which the thread is listening (on which the server socket was created)
     */
    public int getLocalPort() {
        return accepter.getLocalPort();
    }
    
    /**
     *Accepts new clients until the thread is stopped
     */
    public void run() {
        while (listening) {
            try {
                serverRef.newClient(new TCPClient(accepter.accept()));
            }
            catch (IOException ioe) {
                if (listening) {
                    System.err.println("TCPServerAcceptThread: Couldn't accept connection: " + ioe.getMessage());
                }
            }
            catch (NetworkException ne) {
                System.err.println("TCPServerAcceptThread: Couldn't create TCPClient: " + ne.getMessage());
            }
        }
    }
    
    /**
     *Stops the thread and closes the socket
     */
    public void end() {
        listening = false;
        try {
            accepter.close();
        }
        catch (IOException ioe) {
            throw new RuntimeException("TCPServerAcceptThread: Couldn't close server socket");
        }
    }
    
}
