/*
 * Notifier.java
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
 *This interface is meant to support network communication classes for         
 *the client side of a client/server pair. Each client that implements this    
 *interface must handle addressing issues, and, since they are ignored     
 *in the interface, it is reccommended that one object be created for each     
 *individual message destination (server). However, the PluggableClient interface 
 *should not be directly implemented: the subinterfaces provided (currently 
 *MessagingClient and RPCClient) should be used instead.
 *                                                                             
 *If the client implementation allows subscriptions for the notification of 
 *messages or events received through the client, an extension of ReceiverThread should 
 *be used to complement the PluggableClient.
 *
 *@see MessagingClient
 *@see RPCClient
 *@see ReceivingThread
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public interface PluggableClient {

    /**
     *Meant to be called once after instantiation of the client object to handle protocol-specific addressing or connection issues
     */
    public void establishConnection() throws NetworkException;
    
    /**
     *Meant to be called once after necessary communication with the client object to handle protocol-specific addressing or connection issues
     */
    public void endConnection();

}