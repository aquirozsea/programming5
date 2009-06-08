/*
 * TCPServerDaemon.java
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
import java.net.Socket;
import programming5.net.NetworkException;
import programming5.net.ServerDaemon;
import programming5.net.ServiceObjectFactory;

/**
 *This class implements a ServerDaemon that accepts TCP connections. It listens to the subscription port and passes each new 
 *client to a service object obtained from the given service object factory.
 *@see programming5.net.ServerDaemon
 *@see programming5.net.ServiceObjectFactory
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class TCPServerDaemon extends ServerDaemon {
    
    protected ServerSocket accepter;
    private boolean listening = true;
    
    /**
     *Creates a daemon that listens on an available port (which can be retrieved with the getLocalPort method).
     *@see #getLocalPort
     */
    public TCPServerDaemon(ServiceObjectFactory myServerFactory) throws NetworkException {
        super(myServerFactory);
        try {
            accepter = new ServerSocket(0);
        }
	catch (IOException ioe) {
            throw new NetworkException("TCPServerDaemon: Couldn't create server socket: " + ioe.getMessage());
        }
    }
    
    /**
     *Creates a daemon that listens on the given port.
     */
    public TCPServerDaemon(ServiceObjectFactory myServerFactory, int port) throws NetworkException {
        super(myServerFactory);
        try {
            accepter = new ServerSocket(port);
        }
	catch (IOException ioe) {
            throw new NetworkException("TCPServerDaemon: Couldn't create server socket: " + ioe.getMessage());
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
    @Override
    public void run() {
        while (listening) {
            try {
                final Socket socket = accepter.accept();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            serverFactory.getServiceObject().newClient(new TCPClient(socket));
                        }
                        catch (NetworkException ne) {
                            System.err.println("TCPServerDaemon: Couldn't create TCPClient: " + ne.getMessage());
                        }
                    }
                }).start();
            }
            catch (IOException ioe) {
                System.err.println("TCPServerDaemon: Couldn't accept connection: " + ioe.getMessage());
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
