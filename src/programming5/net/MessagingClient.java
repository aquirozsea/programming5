/*
 * MessagingClient.java
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
 *This interface defines methods for a PluggableClient to send and receive messages over the network. The receive methods 
 *are meant to be blocking; to receive messages asynchronously, use an extension of ReceiverThread to complement the client 
 *object.
 *@see PluggableClient
 *@see ReceiverThread
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public interface MessagingClient extends PluggableClient, IPublisher<MessageArrivedEvent> {
	
	public void send(String message) throws NetworkException;
        public String receive();
	public String receive(long timeout) throws InterruptedException;
        public void send(byte[] bytesMessage) throws NetworkException;
        public byte[] receiveBytes();
        public byte[] receiveBytes(long timeout) throws InterruptedException;
        public void send(String message, String url) throws NetworkException;
        public void send(byte[] bytesMessage, String url) throws NetworkException;
        
}
