/*
 * ServiceObject.java
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
 *A service object performs the service for a given client at the server side. A server or daemon creates or 
 *references the ServiceObject instances every time an object connects to or calls the server, providing them with a proxy 
 *for the client (another PluggableClient object), through which the communication with the client can be made.
 *This interface replaces the SuperServer and HandoffServer interfaces of the previous Programming release.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public interface ServiceObject {
        
        /**
         *Called by a server thread for each new client connection, meant to provide service to the client. 
         *This method can be implemented as synchronized if a single ServiceObject serves multiple clients
         */
	public void newClient(PluggableClient client);
        
}
