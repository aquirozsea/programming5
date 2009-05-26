/*
 * ServerAcceptThread.java
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

package programming5.net;

/**
 *These objects are created by a server or service object. A concrete subclass of this class must override the Thread's 
 *run method to listen for new clients, to create the client objects according to the specific protocol, and to pass 
 *these clients to the server using its newClient method.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public abstract class ServerAcceptThread extends Thread {
	
	protected ServiceObject serverRef = null;
        
	/**
         *Creates a server accept thread with the given service object reference, which should be signaled when new clients are 
         *accepted.
         */
        public ServerAcceptThread(ServiceObject myServer) {
            serverRef = myServer;
	}
        
        /**
         *Meant to stop the accept thread (no more connections/clients are should be accepted after the method is called)
         */
        public abstract void end();
	
}
