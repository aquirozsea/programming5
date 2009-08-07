/*
 * ReliableUDPClient.java
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

import java.net.MalformedURLException;
import java.net.URL;
import programming5.net.MalformedMessageException;
import programming5.net.MessageArrivedEvent;
import programming5.net.MessageArrivedListener;
import programming5.net.MessagingClient;
import programming5.net.NetworkException;
import programming5.net.Publisher;
import programming5.net.ReliableProtocolMessage;


/**
 *<-----------WARNING!------------> Implementation of this class is not complete
 *This class is a UDP socket implementation of a MessagingClient that ensures reliable transmission (replaces the UDPClient 
 *class of the previous Programming release). It uses a UDPClient object to send and receive messages. After each send, it 
 *waits a time given by timeout for an acknowledgement packet, and responds with an acknowledgement of its own upon message 
 *reception. Both ends of the unicast communication must implement this class, or a class that behaves in the same way.<p> 
 *<p>It uses a special message object that creates messages with the following syntax:<p>
 *<p>"RPM:seq_number::payload"<p>
 *"ACK:seq_number"
 *@see programming5.net.sockets.UDPClient
 *@see programming5.net.ReliableProtocolMessage
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class ReliableUDPClient extends Publisher<MessageArrivedEvent> implements MessagingClient, MessageArrivedListener {
    
    protected UDPClient client;
    protected int sendSequence = 0;
    protected int rcvdSequence = -1;
    protected int timeout = DEF_TIMEOUT;
    
    public static final int DEF_TIMEOUT = 5000;
    
    /**
     *Creates a reliable unicast client for which the local port will be determined by an available port at the time of 
     *connection. Messages will be sent by default to the localhost on port 4445.
     */
    public ReliableUDPClient() throws NetworkException {
        client = new UDPClient();
    }
    
    /**
     *Creates a reliable unicast client on the specified local port. Messages will be sent by default to the localhost on 
     *port 4445.
     */
    public ReliableUDPClient(int localPort) throws NetworkException {
        client = new UDPClient(localPort);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and remote port, to be bound on an available
     *local port. When establishConnection is called, no special connect message will be sent.
     */
    public ReliableUDPClient(String address, int remotePort) throws NetworkException {
        client = new UDPClient(address, remotePort);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and the specified ports
     */
    public ReliableUDPClient(String address, int remotePort, int localPort) throws NetworkException {
        client = new UDPClient(address, remotePort, localPort);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and remote port, to be bound on an available
     *local port. When establishConnection is called, a special connect message will be sent if useConnectMsg
     *parameter is true.
     */
    public ReliableUDPClient(String address, int remotePort, boolean useConnectMsg) throws NetworkException {
        client = new UDPClient(address, remotePort, useConnectMsg);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and the specified ports. The client will send
     *a special connect message upon call of establishConnection if specified by useConnectMsg.
     */
    public ReliableUDPClient(String address, int remotePort, int localPort, boolean useConnectMsg) throws NetworkException {
        client = new UDPClient(address, remotePort, localPort, useConnectMsg);
    }
    
    /**
     *Sets a specific timeout for acknowledgement reception.
     */
    public void setTimeout(int timeMillis) {
        timeout = timeMillis;
    }
    
    /**
     *Implementation of the PluggableClient interface. Opens the datagram socket and
     *starts the receiver thread.
     */
    public void establishConnection() throws NetworkException {
        client.establishConnection();
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param msg the message string to send to the host
     */
    public void send(String msg) throws NetworkException {
        ReliableProtocolMessage rmsg = new ReliableProtocolMessage(msg.getBytes(), sendSequence++);
        client.send(rmsg.getMessageBytes());
        new ReliableUDPEnforcerThread(rmsg, client, timeout, this).start();
    }
    
    /**
     *Temporary implementation of the MessagingClient interface, which is a wrapper for send(String)
     *@param msgBytes the message bytes, which might be corrupted if it contains characters that are not defined for a String object
     */
    public void send(byte[] msgBytes) throws NetworkException {
        ReliableProtocolMessage rmsg = new ReliableProtocolMessage(msgBytes, sendSequence++);
        client.send(rmsg.getMessageBytes());
        new ReliableUDPEnforcerThread(rmsg, client, timeout, this).start();
    }
    
    /**
     *Implementation of the MessagingClient interface. Blocking receive until message
     *arrives. It sends an acknowledgement upon message reception.
     *@return the message string
     */
    public String receive() {
        ReliableProtocolMessage rmsg = null;
        ReliableProtocolMessage ack = null;
        String ret = null;
        int auxseq;
        boolean ok = false;
        while (!ok) {
            try {
                rmsg = new ReliableProtocolMessage(client.receiveBytes());
                while (!rmsg.isMessage())
                    rmsg = new ReliableProtocolMessage(client.receiveBytes());
                ret = new String(rmsg.getPayload());
                auxseq = rmsg.getSequence();
                ack = new ReliableProtocolMessage(auxseq);
                client.send(ack.getMessageBytes());
                if (auxseq == rcvdSequence + 1 || rcvdSequence == -1) {
                    ok = true;
                    rcvdSequence = auxseq;
                }
            } 
            catch (MalformedMessageException mme) {
                ok = false;
            } 
            catch (NetworkException ne) {
                ok = false;
            }
        }
        return ret;
    }
    
    /**
     *Temporary implementation of the MessagingClient interface. Blocking receive until message
     *arrives. It sends an acknowledgement upon message reception.
     *@return the message bytes, which might be corrupted if the message was sent as a string
     */
    public byte[] receiveBytes() {
        return this.receive().getBytes();
    }
    
    /**
     *Implementation of the MessagingClient interface. Waits for an incoming message for limited time. It sends an 
     *acknowledgement upon message reception.
     *@param timeout wait time in milliseconds
     *@return the message string
     */
    public String receive(long timeout) throws InterruptedException {
        ReliableProtocolMessage rmsg = null;
        ReliableProtocolMessage ack = null;
        String ret = null;
        int auxseq;
        boolean ok = false;
        while (!ok) {
            try {
                rmsg = new ReliableProtocolMessage(client.receiveBytes(timeout));
                while (!rmsg.isMessage())
                    rmsg = new ReliableProtocolMessage(client.receiveBytes(timeout));
                ret = new String(rmsg.getPayload());
                auxseq = rmsg.getSequence();
                ack = new ReliableProtocolMessage(auxseq);
                client.send(ack.getMessageBytes());
                if (auxseq == rcvdSequence + 1 || rcvdSequence == -1) {
                    ok = true;
                    rcvdSequence = auxseq;
                }
                ok = true;
            } 
            catch (MalformedMessageException mme) {
                ok = false;
            } 
            catch (NetworkException ne) {
                ok = false;
            }
        }
        return ret;
    }
    
    /**
     *Temporary implementation of the MessagingClient interface. Waits for an incoming message for limited time. It sends an 
     *acknowledgement upon message reception.
     *@param timeout wait time in milliseconds
     *@return the message bytes, which might be corrupted if the message was sent as a string
     */
    public byte[] receiveBytes(long timeout) throws InterruptedException {
        return this.receive(timeout).getBytes();
    }
    
    /**
     *Implementation of the MessageArrivedListener interface. Acknowledges messages received by subscribers to the
     *unicast client.
     */
    public void signalEvent(MessageArrivedEvent event) {
        ReliableProtocolMessage rmsg = null;
        ReliableProtocolMessage ack = null;
        try {
            rmsg = new ReliableProtocolMessage(event.getMessageBytes());
            if (rmsg.isMessage()) {
                int auxseq = rmsg.getSequence();
                ack = new ReliableProtocolMessage(auxseq);
                client.send(ack.getMessage());
                if (auxseq == rcvdSequence + 1 || rcvdSequence == -1) {
                    fireEvent(new MessageArrivedEvent(rmsg.getPayload()));
                    rcvdSequence = auxseq;
                }
            }
        } 
        catch (MalformedMessageException mme) {} catch (NetworkException ne) {}
    }
    
    /**
     *Implementation of the PluggableClient interface. Stops the receiver thread and
     *closes the socket.
     */
    public void endConnection() {
        client.endConnection();
    }
    
    /**
     *Overrides method in Publisher to realize implementation of PluggableClient interface
     */
    public void addListener(MessageArrivedListener mal) {
        super.addListener(mal);
        if (listeners.size() == 1) {
            client.addListener(this);
        }
    }
    
    /**
     *Overrides method in Publisher to realize implementation of PluggableClient interface
     */
    public void removeListener(MessageArrivedListener mal) {
        super.removeListener(mal);
        if (listeners.size() == 0) {
            client.removeListener(this);
        }
    }
    
    protected void signalFail() {
        System.out.println("ReliableUDPClient: Cannot guarantee connection");
    }
    
    public String getHostAddress() {
        return client.getHostAddress();
    }
    
    public int getHostPort() {
        return client.getHostPort();
    }
    
    public int getLocalPort() {
        return client.getLocalPort();
    }

    public void send(String message, String url) throws NetworkException {
        try {
            URL urlObj = new URL(url);
            ReliableProtocolMessage rmsg = new ReliableProtocolMessage(message.getBytes(), sendSequence++);
            client.send(message, urlObj.getHost(), urlObj.getPort());
            new ReliableUDPEnforcerThread(rmsg, client, timeout, this).start();
        }
        catch (MalformedURLException murle) {
            throw new NetworkException("ReliableUDPClient: Cannot send message: " + murle.getMessage());
        }
    }

    public void send(byte[] bytesMessage, String url) throws NetworkException {
        this.send(new String(bytesMessage), url);
    }

}
