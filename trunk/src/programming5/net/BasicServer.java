/*
 * BasicServer.java
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

import java.util.ArrayList;
import java.util.List;

/**
 *This is a base class for server implementations in a client/server system. A concrete subclass of this class must 
 *implement the createAcceptThread method to create an accept thread to listen for new clients according to a particular 
 *protocol. The accept thread must have a reference to the BasicServer object, which is a ServiceObject, so that it can 
 *call the newClient method every time a new client connects.
 *@author Andres Quiroz Hernandez
 *@version 6.09
 */
public abstract class BasicServer implements ServiceObject {
	
	protected List<PluggableClient> clients = new ArrayList<PluggableClient>();
	protected ServerAcceptThread accepter = null;
	protected ServiceObjectFactory sObjFact = null;	
	
	/**
         *Creates and starts a new basic server.
         *@throws java.lang.IllegalStateException if the implementation of the createAcceptThread method did not correctly create the 
         *server accept thread.
         */
        public BasicServer() {
		createAcceptThread();
		if (accepter != null) {
			accepter.start();
		}
		else {
			throw new IllegalStateException("BasicServer: The ServerAcceptThread must be created in the implementation of createAcceptThread method");
		}
	}
	
	/**
         *Sets a service object factory to handle newly connected clients
         */
        public void setServiceObjectFactory(ServiceObjectFactory factory) {
		sObjFact = factory;
	}

	/**
         *Called by the server accept thread for each new client.
         */
        public void newClient(PluggableClient client) {
		clients.add(client);
		if (sObjFact != null) {
			sObjFact.getServiceObject().newClient(client);
		}
	}
	
	/**
         *All implementing classes must initialize the accepter field appropriately with a server accept thread.
         */
        protected abstract void createAcceptThread();

}
