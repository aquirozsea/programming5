/*
 * ReceivingThread.java
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
 *This is a base class for receiving objects meant to complement PluggableClient objects, allowing them to receive
 *messages or callbacks asynchronously. The classes that implement the PluggableClient interface must instantiate the 
 *receiving thread and provide it with the reference of a Publisher object (possibly the PluggableClient itself). The 
 *specific Receiving threads should call the fireEvent method of this Publisher object within its run method every time 
 *a message is received.
 *@see PluggableClient
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public abstract class ReceivingThread extends Thread {
    
	protected Publisher<MessageArrivedEvent> ref;	

	/**
         *Creates a receiving thread for the given publisher of received messages
         */
        public ReceivingThread(Publisher<MessageArrivedEvent> myReference) {
		ref = myReference;
	}
        
}
