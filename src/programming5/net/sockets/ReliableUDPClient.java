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

import java.util.Hashtable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import programming5.collections.RotatingVector;
import programming5.io.Debug;
import programming5.net.AsynchMessageArrivedEvent;
import programming5.net.MalformedMessageException;
import programming5.net.MessageArrivedEvent;
import programming5.net.MessageArrivedListener;
import programming5.net.MessagingClient;
import programming5.net.NetworkException;
import programming5.net.Publisher;
import programming5.net.ReceiveRequest;
import programming5.net.ReliableMessageArrivedListener;
import programming5.net.ReliableProtocolMessage;
import programming5.net.Subscriber;


/**
 *This class is a UDP socket implementation of a MessagingClient that ensures reliable transmission
 *It uses a UDPClient object to send and receive messages. It keeps track and waits for
 *acknowledgements for all messages sent, and also automatically sends acknowledgements for all
 *messages received. Messages for which no acknowledgements are received within a configurable
 *amount of time are resent, up to a certain limit, after which the message is dropped (and
 *subscribed clients are notified if enabled to do so as ReliableMessageArrivedListeners). Both
 *ends of the unicast communication must implement this class, or a class that behaves in the same
 *way.<p>
 *<p>It uses a special message object that creates messages with the following syntax:<p>
 *<p>"RPM:seq_number::payload"<p>
 *"ACK:seq_number"
 *@see programming5.net.sockets.UDPClient
 *@see programming5.net.ReliableProtocolMessage
 *@see programming5.net.ReliableMessageArrivedListener
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public class ReliableUDPClient extends Publisher<MessageArrivedEvent> implements MessagingClient, MessageArrivedListener {
    
    protected UDPClient client;
    protected final Hashtable<Long, ReliableProtocolMessage> messageTable = new Hashtable<Long, ReliableProtocolMessage>();
    protected final RotatingVector<Long> receivedMessages = new RotatingVector<Long>(DEF_RCV_MEMORY);
    protected final Vector<ReceiveRequest> receiveRequests = new Vector<ReceiveRequest>();
    
    private long timeout = DEF_TIMEOUT;
    private int maxResend = DEF_RESEND;
    private Random random = new Random(System.currentTimeMillis());
    private Timer timer = new Timer();
    
    public static final long DEF_TIMEOUT = 10000;
    public static final int DEF_RESEND = 3;
    public static final int DEF_RCV_MEMORY = 100;
    
    /**
     *Creates a reliable unicast client for which the local port will be determined by an
     *available port.
     *The new client will be listening on this port upon creation (no need to call
     *establishConnection), unlike previous implementations of this class.
     */
    public ReliableUDPClient() throws NetworkException {
        client = new UDPClient();
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates an unbound reliable unicast client, to be bound on the specified local port at the
     *time of connection.
     *The new client will be listening on this port upon creation (no need to call
     *establishConnection), unlike previous implementations of this class.
     *@param localPort the local port on which the socket will be created to receive messages
     */
    public ReliableUDPClient(int localPort) throws NetworkException {
        client = new UDPClient(localPort);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and remote port, to be
     *bound on an available local port.
     *Will not use a connect protocol for use with a UDPServer class.
     *The new client will be listening on this port upon creation (no need to call
     *establishConnection), unlike previous implementations of this class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     */
    public ReliableUDPClient(String address, int remotePort) throws NetworkException {
        client = new UDPClient(address, remotePort);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and the specified ports.
     *Will not use a connect protocol for use with a UDPServer class.
     *The new client will be listening on this port upon creation (no need to call establishConnection),
     *unlike in previous implementations of this class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     *@param localPort the local port on which the socket will be created to receive messages
     */
    public ReliableUDPClient(String address, int remotePort, int localPort) throws NetworkException {
        client = new UDPClient(address, remotePort, localPort);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and remote port, to be
     *bound on an available local port. If specified, will use a connect protocol to establish
     *connection with a UDPServer class (requires calling establishConnection method).
     *The new client will be listening on this port upon creation, unlike previous implementations
     *of this class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     *@param useConnectMsg if true, the connect protocol will be used with a UDP server at the given remote
     *address and port
     *@see #establishConnection
     */
    public ReliableUDPClient(String address, int remotePort, boolean useConnectMsg) throws NetworkException {
        client = new UDPClient(address, remotePort, useConnectMsg);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and the specified ports.
     *When establishConnection is called, a special connect message will be sent if useConnectMsg
     *parameter is true.
     *The new client will be listening on this port upon creation, unlike previous implementations
     *of this class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     *@param localPort the local port on which the socket will be created to receive messages
     *@param useConnectMsg if true, the connect protocol will be used with a UDP server at the given remote
     *address and port
     */
    public ReliableUDPClient(String address, int remotePort, int localPort, boolean useConnectMsg) throws NetworkException {
        client = new UDPClient(address, remotePort, localPort, useConnectMsg);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Sets a specific timeout for acknowledgement reception.
     *@param timeMillis the time to wait before resending unacknowledged messages
     */
    public void setTimeout(long timeMillis) {
        timeout = timeMillis;
    }

    /**
     * Sets a specific number of times a message can be resent before it is dropped.
     * @param value the resend limit
     */
    public void setResendLimit(int value) {
        maxResend = value;
    }

    /**
     * When a message that has already been acknowledged is received again (possibly due to a lost 
     * ack), it should not be delivered to this application again. This parameter controls the 
     * number of received messages kept in memory in order to accomplish this.
     * @param value the number of messages to keep in memory in case of duplicate reception
     */
    public void setReceiveMemory(int value) {
        receivedMessages.setSize(value);
    }
    
    /**
     *Implementation of the PluggableClient interface. Implements a connection protocol for use
     *with a UDPServer class if this behavior has been specified upon instantiation of the UDP
     *client. If not, a call to this method will do nothing.
     */
    @Override
    public void establishConnection() throws NetworkException {
        client.establishConnection();
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param msg the message string to send to the host
     */
    @Override
    public void send(String msg) throws NetworkException {
        if (client.fixedHost) {
            String destURL = "//" + client.getHostAddress() + ":" + Integer.toString(client.getHostPort());
            ReliableProtocolMessage rmsg = this.createMessage(msg.getBytes(), destURL);
            client.send(rmsg.getMessageBytes());
        }
        else throw new NetworkException("ReliableUDPClient: Could not send message: No remote host specified");
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param bytesMessage the packet of bytes to send to the host
     */
    @Override
    public void send(byte[] msgBytes) throws NetworkException {
        if (client.fixedHost) {
            String destURL = "//" + client.getHostAddress() + ":" + Integer.toString(client.getHostPort());
            ReliableProtocolMessage rmsg = this.createMessage(msgBytes, destURL);
            client.send(rmsg.getMessageBytes());
        }
        else throw new NetworkException("ReliableUDPClient: Could not send message: No remote host specified");
    }
    
    /**
     *Implementation of the MessagingClient interface. Sends the given message to the given uri.
     *@param message the message to send
     *@param uri the destination uri, which must be in the format [protocol:]//host:port[/...]
     */
    @Override
    public void send(String message, String uri) throws NetworkException {
        ReliableProtocolMessage rmsg = this.createMessage(message.getBytes(), uri);
        client.send(rmsg.getMessageBytes(), uri);
    }

    /**
     *Implementation of the MessagingClient interface. Sends the given message to the given uri.
     *@param message the message to send
     *@param uri the destination uri, which must be in the format [protocol:]//host:port[/...]
     */
    @Override
    public void send(byte[] bytesMessage, String url) throws NetworkException {
        ReliableProtocolMessage rmsg = this.createMessage(bytesMessage, url);
        client.send(rmsg.getMessageBytes(), url);
    }

    /**
     * Allows a client to send the message to the host from which the last message was received. Recommended
     * for synchronous protocols where no interleaved messages are expected.
     * This class is particular to UDPClient
     * @param msg the message to send to the host
     */
    public void replyTo(MessageArrivedEvent event, String msg) throws NetworkException {
        if (event instanceof AsynchMessageArrivedEvent) {
            String destAddress = ((AsynchMessageArrivedEvent) event).getSourceURL();
            if (destAddress != null) {
                send(msg, destAddress);
            }
            else throw new NetworkException("UDPClient: Cannot send reply: No message to reply to");
        }
        else throw new NetworkException("UDPClient: Cannot send reply: Arrived event does not contain return address");
    }

    /**
     * Allows a client to send the message to the host from which the last message was received. Recommended
     * for synchronous protocols where no interleaved messages are expected.
     * This class is particular to UDPClient
     * @param msg the message to send to the host
     */
    public void replyTo(MessageArrivedEvent event, byte[] msg) throws NetworkException {
        if (event instanceof AsynchMessageArrivedEvent) {
            String destAddress = ((AsynchMessageArrivedEvent) event).getSourceURL();
            if (destAddress != null) {
                send(msg, destAddress);
            }
            else throw new NetworkException("UDPClient: Cannot send reply: Return address not set");
        }
        else throw new NetworkException("UDPClient: Cannot send reply: Arrived event does not contain return address");
    }

    /**
     *Implementation of the MessagingClient interface. Blocking receive until message arrives.
     *@return the message bytes
     */
    @Override
    public byte[] receiveBytes() {
        byte[] ret = null;
        ReceiveRequest myRequest = new ReceiveRequest();
        synchronized (receiveRequests) {
            receiveRequests.add(myRequest);
        }
        myRequest.awaitUninterruptibly();
        if (myRequest.isDone()) {
            ret = myRequest.getMessage();
        }
        return ret;
    }

    /**
     *Implementation of the MessagingClient interface. Blocking receive until message arrives.
     *@return the message string
     */
    @Override
    public String receive() {
        return new String(this.receiveBytes());
    }

    /**
     *Implementation of the MessagingClient interface. Waits for an incoming message for limited time.
     *@param timeout wait time in milliseconds
     *@return the message bytes
     */
    @Override
    public byte[] receiveBytes(long timeout) throws InterruptedException {
        byte[] ret = null;
        ReceiveRequest myRequest = new ReceiveRequest();
        synchronized (receiveRequests) {
            receiveRequests.add(myRequest);
        }
        myRequest.await(timeout, TimeUnit.MILLISECONDS);
        if (myRequest.isDone()) {
            ret = myRequest.getMessage();
        }
        return ret;
    }

    /**
     *Implementation of the MessagingClient interface. Waits for an incoming message for limited time.
     *@param timeout wait time in milliseconds
     *@return the message string
     */
    @Override
    public String receive(long timeout) throws InterruptedException {
        return new String(this.receiveBytes(timeout));
    }

    /**
     *Implementation of the MessageArrivedListener interface to receive and decode messages
     *received over the network, and send the corresponding acknowledgements and relay the message
     *to the application. Not meant for external use.
     */
    @Override
    public void signalEvent(MessageArrivedEvent protocolEvent) {
        if (protocolEvent != null) {
            try {
                ReliableProtocolMessage rcvdMsg = new ReliableProtocolMessage(protocolEvent.getContentBytes());
                if (rcvdMsg.isAcknowledge()) {
                    Debug.println("Ack received at " + client.getLocalPort());
                    synchronized (messageTable) {
                        messageTable.remove(rcvdMsg.getSequence());
                    }
                }
                else {
                    long sequence = rcvdMsg.getSequence();
                    ReliableProtocolMessage ack = new ReliableProtocolMessage(sequence);
                    try {
                        client.replyTo(protocolEvent, ack.getMessageBytes());
                    }
                    catch (NetworkException ne) {
                        Debug.printStackTrace(ne, "programming5.net.sockets.ReliableUDPClient");
                    }
                    if (!receivedMessages.contains(sequence)) {
                        receivedMessages.add(sequence);
                        AsynchMessageArrivedEvent messageEvent = new AsynchMessageArrivedEvent(rcvdMsg.getPayload(), ((AsynchMessageArrivedEvent) protocolEvent).getSourceURL());
                        this.fireEvent(messageEvent);
                        synchronized (receiveRequests) {
                            for (ReceiveRequest request : receiveRequests) {
                                request.setMessage(messageEvent.getContentBytes());
                            }
                            receiveRequests.clear();
                        }
                    }
                }
            }
            catch (MalformedMessageException mme) {
                Debug.println("ReliableProtocolMessage: Bad message received: " + protocolEvent.getContent(), "programming5.net.sockets.ReliableUDPClient");
                Debug.printStackTrace(mme, "programming5.net.sockets.ReliableUDPClient");
            }
        }
    }
    
    /**
     *Implementation of the PluggableClient interface. Stops the receiver thread and
     *closes the socket and cancels the resend timer.
     */
    @Override
    public void endConnection() {
        client.endConnection();
        timer.cancel();
    }
    
    protected void signalFail(String destURL) {
        for (Subscriber<MessageArrivedEvent> listener : this.listeners) {
            if (listener instanceof ReliableMessageArrivedListener) {
                ((ReliableMessageArrivedListener) listener).signalSendError(destURL, "ReliableUDPClient: Cannot guarantee connection: Exceeded resend limit");
            }
        }
    }
    
    /**
     *@return the local host address
     */
    public String getHostAddress() {
        return client.getHostAddress();
    }

    /**
     *@return the port to which the client is currently sending
     */
    public int getHostPort() {
        return client.getHostPort();
    }

    /**
     * @return the url of the host from which the last message was received; null if no messages have been
     * received
     */
    public String getReplyAddress() {
        return client.getReplyAddress();
    }

    /**
     *@return the local port on which the client is listening
     */
    public int getLocalPort() {
        return client.getLocalPort();
    }

    private ReliableProtocolMessage createMessage(byte[] msgBytes, String destURL) {
        ReliableProtocolMessage ret = null;
        long sendSequence = random.nextLong();
        synchronized (messageTable) {
            ReliableProtocolMessage collision = messageTable.get(sendSequence);
            while (collision != null) {
                sendSequence = random.nextLong();
                collision = messageTable.get(sendSequence);
            }
            ret = new ReliableProtocolMessage(msgBytes, sendSequence, destURL);
            ret.signalSent();
            messageTable.put(sendSequence, ret);
        }
        return ret;
    }

    private class ReliableProtocolEnforceTask extends TimerTask {

        @Override
        public void run() {
            Vector<ReliableProtocolMessage> unackedMessages;
            synchronized (messageTable) {
                unackedMessages = new Vector<ReliableProtocolMessage>(messageTable.values());
            }
            for (ReliableProtocolMessage message : unackedMessages) {
                try {
                    Debug.println("Resending unacked message: " + new String(message.getPayload()));
                    if (message.getSendCount() < maxResend) {
                        message.signalSent();
                        try {
                            client.send(message.getMessageBytes(), message.getDestination());
                        }
                        catch (NetworkException ne) {
                            Debug.printStackTrace(ne, "programming5.net.sockets.ReliableUDPClient");
                        }
                    }
                    else {
                        synchronized (messageTable) {
                            messageTable.remove(message.getSequence());
                        }
                        signalFail(message.getDestination());
                    }
                }
                catch (MalformedMessageException mme) {
                    Debug.printStackTrace(mme);
                }
            }
        }

    }

}
