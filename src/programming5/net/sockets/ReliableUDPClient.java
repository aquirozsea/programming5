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

import programming5.arrays.ArrayOperations;
import programming5.collections.RotatingList;
import programming5.io.Debug;
import programming5.net.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


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
 *<p>Unacknowledged messages are resent every 50 msec, up to 50 times. This configuration has been tested
 *effective for the order of under 1000 small consecutive messages (in a for loop). Higher order streams
 *require resetting the timeout and resend limit configuration parameters with the methods provided. The
 *following settings have been found effective for small message streams of the following orders:
 *<p>1000 messages: timeout=100
 *<p>10000 messages: timeout=200; resendLimit=125
 *<p>100000 messages: timeout=800; resendLimit=1000 (upper bound)
 *<p>The use of this class is not recommended for larger message streams. As the sizes of the streams
 *increase, the receive memory size (default 1000 messages) should also be increased, but the correct
 *settings have not been tested.
 *@see programming5.net.sockets.UDPClient
 *@see programming5.net.ReliableProtocolMessage
 *@see programming5.net.ReliableMessageArrivedListener
 *@author Andres Quiroz Hernandez
 *@version 6.19
 */
public class ReliableUDPClient extends Publisher<MessageArrivedEvent> implements MessagingClient, MessageArrivedListener {
    
    protected UDPClient client;
    protected final Map<Long, ReliableProtocolMessage[]> messageTable = new HashMap<Long, ReliableProtocolMessage[]>();
    protected final List<ReceiveRequest> receiveRequests = new ArrayList<ReceiveRequest>();
    protected final Map<String, byte[][]> assembly = new HashMap<String, byte[][]>();
    protected final Map<String, boolean[]> assemblyCounter = new HashMap<String, boolean[]>();
    protected final RotatingList<String> receivedMessages = new RotatingList<String>(DEF_RCV_MEMORY);

    private long timeout = DEF_TIMEOUT;
    private int maxResend = DEF_RESEND;
    private Random random = new Random(System.currentTimeMillis());
    private Timer timer = new Timer();
    
    public static final long DEF_TIMEOUT = 50;
    public static final int DEF_RESEND = 50;
    public static final int DEF_RCV_MEMORY = 1000;
    protected static final int MAX_SIZE = 65400;
    protected static final String SEPARATOR = ":";
    protected static final String SLASH = "/";

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
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
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
            ReliableProtocolMessage[] rmsgs = this.createMessage(msg.getBytes(), destURL);
            for (ReliableProtocolMessage rmsg : rmsgs) {
                client.send(rmsg.getMessageBytes());
            }
        }
        else throw new NetworkException("ReliableUDPClient: Could not send message: No remote host specified");
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param msgBytes the packet of bytes to send to the host
     */
    @Override
    public void send(byte[] msgBytes) throws NetworkException {
        if (client.fixedHost) {
            String destURL = "//" + client.getHostAddress() + ":" + Integer.toString(client.getHostPort());
            ReliableProtocolMessage[] rmsgs = this.createMessage(msgBytes, destURL);
            for (ReliableProtocolMessage rmsg : rmsgs) {
                client.send(rmsg.getMessageBytes());
            }
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
        ReliableProtocolMessage[] rmsgs = this.createMessage(message.getBytes(), uri);
        Debug.println("Sending " + rmsgs.length + " reliable messages", "programming5.net.sockets.ReliableUDPClient");
        for (ReliableProtocolMessage rmsg : rmsgs) {
            client.send(rmsg.getMessageBytes(), uri);
            Debug.println("RUDP message length " + rmsg.getMessageBytes().length, "programming5.net.sockets.ReliableUDPClient");
        }
    }

    /**
     *Implementation of the MessagingClient interface. Sends the given message to the given uri.
     *@param bytesMessage the message to send
     *@param uri the destination uri, which must be in the format [protocol:]//host:port[/...]
     */
    @Override
    public void send(byte[] bytesMessage, String uri) throws NetworkException {
        if (bytesMessage == null) {
            throw new NetworkException("ReliableUDPClient: Cannot send message: Message is null");
        }
        else if (bytesMessage.length == 0) {
            throw new NetworkException("ReliableUDPClient: Cannot send message: No message to send");
        }
        else {
            ReliableProtocolMessage[] rmsgs = this.createMessage(bytesMessage, uri);
            Debug.println("Sending " + rmsgs.length + " reliable messages", "programming5.net.sockets.ReliableUDPClient");
            for (ReliableProtocolMessage rmsg : rmsgs) {
                client.send(rmsg.getMessageBytes(), uri);
            }
        }
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
                    Debug.println("RUDP Ack received for " + rcvdMsg.getSequence() + " at " + rcvdMsg.getIndex(), "programming5.net.sockets.ReliableUDPClient");
                    synchronized (messageTable) {
                        ReliableProtocolMessage[] sentSequence = messageTable.get(rcvdMsg.getSequence());
                        if (sentSequence != null) {
                            sentSequence[rcvdMsg.getIndex()-1].signalAcked();
                        }
                    }
                }
                else {
                    long sequence = rcvdMsg.getSequence();
                    ReliableProtocolMessage ack = new ReliableProtocolMessage(sequence, rcvdMsg.getIndex());
                    try {
                        client.replyTo(protocolEvent, ack.getMessageBytes());
                    }
                    catch (NetworkException ne) {
                        Debug.printStackTrace(ne);
                    }
                    String streamID = ((AsynchMessageArrivedEvent) protocolEvent).getSourceURL() + "/" + Long.toString(rcvdMsg.getSequence());
                    if (!receivedMessages.contains(streamID)) {
                        byte[][] toAssemble = depacketize(rcvdMsg, streamID);
                        if (toAssemble != null) {
                            receivedMessages.add(streamID);
                            byte[] bytesMessage = assemble(toAssemble);
                            AsynchMessageArrivedEvent messageEvent = new AsynchMessageArrivedEvent(bytesMessage, ((AsynchMessageArrivedEvent) protocolEvent).getSourceURL());
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
            }
            catch (MalformedMessageException mme) {
                Debug.println("ReliableProtocolMessage: Bad message received: " + protocolEvent.getContent(), "programming5.net.sockets.ReliableUDPClient");
                Debug.printStackTrace(mme);
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

    protected void signalFail(String destURL) {
        for (Subscriber<MessageArrivedEvent> listener : this.listeners) {
            if (listener instanceof ReliableMessageArrivedListener) {
                ((ReliableMessageArrivedListener) listener).signalSendError(destURL, "ReliableUDPClient: Cannot guarantee connection: Exceeded resend limit");
            }
        }
    }

    private ReliableProtocolMessage[] createMessage(byte[] msgBytes, String destURL) {
        byte[][] packetization = packetize(msgBytes);
        ReliableProtocolMessage[] ret = new ReliableProtocolMessage[packetization.length];
        long sendSequence = random.nextLong();
        synchronized (messageTable) {
            ReliableProtocolMessage[] collision = messageTable.get(sendSequence);
            while (collision != null) {
                sendSequence = random.nextLong();
                collision = messageTable.get(sendSequence);
            }
            for (int i = 0; i < packetization.length; i++) {
                ret[i] = new ReliableProtocolMessage(packetization[i], sendSequence, (i+1), packetization.length, destURL);
            }
            messageTable.put(sendSequence, ret);
        }
        return ret;
    }

    private byte[][] packetize(byte[] bytesMsg) {
        int msgSize = bytesMsg.length;
        int numPackets = (int) (msgSize / MAX_SIZE);
        if (msgSize % MAX_SIZE != 0) {
            numPackets++;
        }
        byte[][] ret = new byte[numPackets][];
        for (int i = 0; i < numPackets - 1; i++) {
            ret[i] = ArrayOperations.subArray(bytesMsg, i*MAX_SIZE, (i+1)*MAX_SIZE);
            Debug.println(new String(ret[i]), "programming5.net.sockets.ReliableUDPClient#packetize");
        }
        ret[numPackets-1] = ArrayOperations.suffix(bytesMsg, (numPackets-1)*MAX_SIZE);
        Debug.println(new String(ret[numPackets-1]), "programming5.net.sockets.ReliableUDPClient#packetize");
        return ret;
    }

    private byte[][] depacketize(ReliableProtocolMessage rpm, String streamID) throws MalformedMessageException {
        byte[][] ret = null;
        byte[][] parts;
        synchronized (assembly) {
            parts = assembly.get(streamID);
            if (parts == null) {
                int total = rpm.getTotal();
                parts = new byte[total][];
                assembly.put(streamID, parts);
                boolean[] counter = new boolean[total];
                ArrayOperations.initialize(counter, false);
                assemblyCounter.put(streamID, counter);
            }
            int index = rpm.getIndex();
            parts[index-1] = rpm.getPayload();
            boolean[] progress = assemblyCounter.get(streamID);
            if (progress != null) {
                progress[index-1] = true;
            }
            else {
                progress = new boolean[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i] != null) {
                        progress[i] = true;
                    }
                    else {
                        progress[i] = false;
                    }
                }
            }
            if (ArrayOperations.tautology(progress)) {
                ret = parts;
                assembly.remove(streamID);
                assemblyCounter.remove(streamID);
            }
        }
        return ret;
    }

    private byte[] assemble(byte[][] parts) {
        int size = 0;
        for (byte[] part : parts) {
            size += part.length;
        }
        byte[] ret = new byte[size];
        int place = 0;
        for (int i = 0; i < parts.length; i++) {
            for (int j = 0; j < parts[i].length; j++) {
                ret[place++] = parts[i][j];
            }
        }
        return ret;
    }

    private class ReliableProtocolEnforceTask extends TimerTask {

        @Override
        public void run() {
            List<ReliableProtocolMessage[]> unackedMessages;
            synchronized (messageTable) {
                unackedMessages = new ArrayList<ReliableProtocolMessage[]>(messageTable.values());
            }
            for (ReliableProtocolMessage[] message : unackedMessages) {
                boolean allAcked = true;
                for (ReliableProtocolMessage messagePart : message) {
                    if (!messagePart.isAcked()) {
                        allAcked = false;
                        try {
                            Debug.println("Resending unacked message from sequence " + messagePart.getSequence() + " at index " + messagePart.getIndex(), "programming5.net.sockets.ReliableUDPClient");
                            if (messagePart.getSendCount() < maxResend) {
                                messagePart.signalSent();
                                try {
                                    client.send(messagePart.getMessageBytes(), messagePart.getDestination());
                                }
                                catch (NetworkException ne) {
                                    Debug.printStackTrace(ne);
                                }
                            }
                            else {
                                synchronized (messageTable) {
                                    messageTable.remove(messagePart.getSequence());
                                }
                                signalFail(messagePart.getDestination());
                            }
                        }
                        catch (MalformedMessageException mme) {
                            Debug.printStackTrace(mme);
                        }
                    }
                }
                if (allAcked) {
                    synchronized (messageTable) {
                        try {
                            messageTable.remove(message[0].getSequence());
                        }
                        catch (MalformedMessageException mme) {
                            Debug.printStackTrace(mme, "programming5.net.sockets.ReliableUDPClient");
                        }
                        catch (NullPointerException npe) {
                            Debug.printStackTrace(npe, "programming5.net.sockets.ReliableUDPClient");
                        }
                    }
                }
            }
        }

    }

}
