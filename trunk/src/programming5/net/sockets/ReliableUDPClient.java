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
import programming5.io.Debug;
import programming5.net.MalformedMessageException;
import programming5.net.MessageArrivedEvent;
import programming5.net.MessageArrivedListener;
import programming5.net.MessagingClient;
import programming5.net.NetworkException;
import programming5.net.Publisher;
import programming5.net.ReceiveRequest;
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
 *@version 6.1
 */
public class ReliableUDPClient extends Publisher<MessageArrivedEvent> implements MessagingClient, MessageArrivedListener {
    
    protected UDPClient client;
    protected final Hashtable<Long, ReliableProtocolMessage> messageTable = new Hashtable<Long, ReliableProtocolMessage>();
    protected final Vector<ReceiveRequest> receiveRequests = new Vector<ReceiveRequest>();
    
    private long timeout = DEF_TIMEOUT;
    private int maxResend = DEF_RESEND;
    private Random random = new Random(System.currentTimeMillis());
    private Timer timer = new Timer();
    
    public static final long DEF_TIMEOUT = 10000;
    public static final int DEF_RESEND = 3;
    
    /**
     *Creates a reliable unicast client for which the local port will be determined by an available port at the time of 
     *connection. Messages will be sent by default to the localhost on port 4445.
     */
    public ReliableUDPClient() throws NetworkException {
        client = new UDPClient();
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client on the specified local port. Messages will be sent by default to the localhost on 
     *port 4445.
     */
    public ReliableUDPClient(int localPort) throws NetworkException {
        client = new UDPClient(localPort);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and remote port, to be bound on an available
     *local port. When establishConnection is called, no special connect message will be sent.
     */
    public ReliableUDPClient(String address, int remotePort) throws NetworkException {
        client = new UDPClient(address, remotePort);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and the specified ports
     */
    public ReliableUDPClient(String address, int remotePort, int localPort) throws NetworkException {
        client = new UDPClient(address, remotePort, localPort);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and remote port, to be bound on an available
     *local port. When establishConnection is called, a special connect message will be sent if useConnectMsg
     *parameter is true.
     */
    public ReliableUDPClient(String address, int remotePort, boolean useConnectMsg) throws NetworkException {
        client = new UDPClient(address, remotePort, useConnectMsg);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Creates a reliable unicast client for the specified host address and the specified ports. The client will send
     *a special connect message upon call of establishConnection if specified by useConnectMsg.
     */
    public ReliableUDPClient(String address, int remotePort, int localPort, boolean useConnectMsg) throws NetworkException {
        client = new UDPClient(address, remotePort, localPort, useConnectMsg);
        client.addListener(this);
        timer.scheduleAtFixedRate(new ReliableProtocolEnforceTask(), timeout, timeout);
    }
    
    /**
     *Sets a specific timeout for acknowledgement reception.
     */
    public void setTimeout(long timeMillis) {
        timeout = timeMillis;
    }

    public void setResendLimit(int value) {
        maxResend = value;
    }
    
    /**
     *Implementation of the PluggableClient interface. Opens the datagram socket and
     *starts the receiver thread.
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
//            new ReliableUDPEnforcerThread(rmsg, client, timeout, this).start();
        }
        else throw new NetworkException("ReliableUDPClient: Could not send message: No remote host specified");
    }
    
    /**
     *Temporary implementation of the MessagingClient interface, which is a wrapper for send(String)
     *@param msgBytes the message bytes, which might be corrupted if it contains characters that are not defined for a String object
     */
    @Override
    public void send(byte[] msgBytes) throws NetworkException {
        if (client.fixedHost) {
            String destURL = "//" + client.getHostAddress() + ":" + Integer.toString(client.getHostPort());
            ReliableProtocolMessage rmsg = this.createMessage(msgBytes, destURL);
            client.send(rmsg.getMessageBytes());
//            new ReliableUDPEnforcerThread(rmsg, client, timeout, this).start();
        }
        else throw new NetworkException("ReliableUDPClient: Could not send message: No remote host specified");
    }
    
    @Override
    public void send(String message, String url) throws NetworkException {
        ReliableProtocolMessage rmsg = this.createMessage(message.getBytes(), url);
        client.send(rmsg.getMessageBytes(), url);
//        new ReliableUDPEnforcerThread(rmsg, client, timeout, this).start();
    }

    @Override
    public void send(byte[] bytesMessage, String url) throws NetworkException {
        ReliableProtocolMessage rmsg = this.createMessage(bytesMessage, url);
        client.send(rmsg.getMessageBytes(), url);
//        new ReliableUDPEnforcerThread(rmsg, client, timeout, this).start();
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
     *Implementation of the MessageArrivedListener interface. Acknowledges messages received by subscribers to the
     *unicast client.
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
                    ReliableProtocolMessage ack = new ReliableProtocolMessage(rcvdMsg.getSequence());
                    try {
                        client.reply(ack.getMessageBytes());    // TODO: Implement robust reply mechanism
                    }
                    catch (NetworkException ne) {
                        Debug.printStackTrace(ne, "programming5.net.sockets.ReliableUDPClient");
                    }
                    MessageArrivedEvent messageEvent = new MessageArrivedEvent(rcvdMsg.getPayload());
                    this.fireEvent(messageEvent);
                    synchronized (receiveRequests) {
                        for (ReceiveRequest request : receiveRequests) {
                            request.setMessage(messageEvent.getContentBytes());
                        }
                        receiveRequests.clear();
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
     *closes the socket.
     */
    @Override
    public void endConnection() {
        client.endConnection();
        timer.cancel();
    }
    
    protected void signalFail(String destURL) {
        System.out.println("ReliableUDPClient: Cannot guarantee connection for " + destURL);
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
                }
                catch (MalformedMessageException mme) {
                    Debug.printStackTrace(mme);
                }
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
                    signalFail(message.getDestination());   // TODO: Improve failure reporting mechanism
                }
            }
        }

    }

}
