/*
 * RPCClient.java
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
 *This interface defines methods for a PluggableClient to invoke methods of a server over the network. If permitted by the 
 *implementation, the server can use the callback method to send messages asynchronously to the client.
 *@see PluggableClient
 *@see ReceivingThread
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public interface RPCClient extends PluggableClient, IPublisher<CallbackEvent> {
	
    /**
     *Meant to invoke the method with the given name with the given parameters on the remote object. This is a blocking method that 
     *should return the result of the invocation.
     */
    public Object invoke(String methodName, Object... parameters) throws NetworkException;
    
    /**
     *Meant to asynchronously invoke the method with the given name with the given parameters on the remote object. The result of the 
     *invocation can be returned as a callback event.
     */
    public void asyncInvoke(String methodName, Object... parameters) throws NetworkException;
	
}
