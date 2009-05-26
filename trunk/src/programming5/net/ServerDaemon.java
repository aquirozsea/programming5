/*
 * ServerDaemon.java
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
 * This class is meant to represent a standalone server accept thread that creates new service objects for each new client from 
 * a given service object factory. A concrete subclass of this class must override the Thread's run method to listen for new 
 * clients, to create the client objects according to the specific protocol, and to pass these clients to the new service object 
 * obtained from the service object factory.
 * @see ServiceObject
 * @see ServiveObjectFactory
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public abstract class ServerDaemon extends Thread {
    
    protected ServiceObjectFactory serverFactory = null;
    
    /**
     *Creates the daemon with reference to a service object factory that should be used to instantiate a new service object for each 
     *new client connection.
     */
    public ServerDaemon(ServiceObjectFactory myServerFactory) {
        serverFactory = myServerFactory;
    }
    
}
