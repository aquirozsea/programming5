/*
 * UDPClient.java
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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import programming5.arrays.ArrayOperations;
import programming5.io.Debug;
import programming5.net.AsynchMessageArrivedEvent;
import programming5.net.MessageArrivedEvent;
import programming5.net.MessagingClient;
import programming5.net.NetworkException;
import programming5.net.Publisher;
import programming5.net.ReceiveRequest;

/**
 *This class is the UDP socket implementation of the MessagingClient. It can be instantiated as a 
 *free client (for sending to any host using the send to url method), directed to a specific host
 *destination (to use the default send methods, as well as the send to url method), or bound to a
 *remote client using a connection protocol and a UDPServer. There are changes in the connection
 *and messaging implementations from previous versions of this class, which will be pointed out
 *where they affect the API functionality.
 *@see programming5.net.MessagingClient
 *@see #send(String, String, int)
 *@author Andres Quiroz Hernandez
 *@version 6.19
 */
public class UDPClient extends Publisher<MessageArrivedEvent> implements MessagingClient {
    
    protected DatagramSocket socket;
    protected InetAddress hostAddress = null;
    protected int hostPort, localPort;
    protected UDPReceiver receiver;
    protected boolean listening = false;
    protected boolean fixedHost, connect;
    protected final List<ReceiveRequest> receiveRequests = new ArrayList<ReceiveRequest>();

    protected static final int MAX_SIZE = 65450;
    protected static final String SEPARATOR = ":";
    protected static final String SLASH = "/";
    protected static final String CONNECT_MSG = "CON";

    private Random random = new Random(System.currentTimeMillis());
    
    /**
     *Creates an unicast client for which the local port will be determined by an available port.
     *The new client will be listening on this port upon creation (no need to call establishConnection),
     *unlike previous implementations of this class.
     */
    public UDPClient() throws NetworkException {
        try {
            socket = new DatagramSocket();
            localPort = socket.getLocalPort();
            fixedHost = false;
            receiver = new UDPReceiver(this, socket);
            receiver.start();
        }
        catch (SocketException se) {
            throw new NetworkException("UDPClient: Could not create datagram socket: " + se.getMessage());
        }
    }
    
    /**
     *Creates an unbound unicast client, to be bound on the specified local port at the time of connection.
     *The new client will be listening on this port upon creation (no need to call establishConnection),
     *unlike previous implementations of this class.
     *@param localPort the local port on which the socket will be created to receive messages
     */
    public UDPClient(int localPort) throws NetworkException {
        try {
            this.localPort = localPort;
            socket = new DatagramSocket(localPort);
            fixedHost = false;
            receiver = new UDPReceiver(this, socket);
            receiver.start();
        }
        catch (SocketException se) {
            throw new NetworkException("UDPClient: Could not create datagram socket on given port: " + se.getMessage());
        }
    }
    
    /**
     *Creates a unicast client for the specified host address and remote port, to be bound on an available local port.
     *Will not use a connect protocol for use with a UDPServer class.
     *The new client will be listening on this port upon creation (no need to call establishConnection),
     *unlike previous implementations of this class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     */
    public UDPClient(String address, int remotePort) throws NetworkException {
        try {
            socket = new DatagramSocket();
            localPort = socket.getLocalPort();
            try {
                hostAddress = InetAddress.getByName(address);
                fixedHost = true;
                hostPort = remotePort;
            }
            catch (UnknownHostException uhe) {
                hostAddress = null;
                fixedHost = false;
            }
            connect = false;
            receiver = new UDPReceiver(this, socket);
            receiver.start();
        }
        catch (SocketException se) {
            throw new NetworkException("UDPClient: Could not create datagram socket: " + se.getMessage());
        }
    }
    
    /**
     *Creates a unicast client for the specified host address and the specified ports. 
     *Will not use a connect protocol for use with a UDPServer class.
     *The new client will be listening on this port upon creation (no need to call establishConnection),
     *unlike in previous implementations of this class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     *@param localPort the local port on which the socket will be created to receive messages
     */
    public UDPClient(String address, int remotePort, int localPort) throws NetworkException {
        try {
            this.localPort = localPort;
            socket = new DatagramSocket(localPort);
            try {
                hostAddress = InetAddress.getByName(address);
                fixedHost = true;
                hostPort = remotePort;
            }
            catch (UnknownHostException uhe) {
                hostAddress = null;
                fixedHost = false;
            }
            connect = false;
            receiver = new UDPReceiver(this, socket);
            receiver.start();
        }
        catch (SocketException se) {
            throw new NetworkException("UDPClient: Could not create datagram socket on given port: " + se.getMessage());
        }
    }
    
    /**
     *Creates a unicast client for the specified host address and remote port, to be bound on an available 
     *local port. If specified, will use a connect protocol to establish connection with a UDPServer class
     *(requires calling establishConnection method).
     *The new client will be listening on this port upon creation, unlike previous implementations of this
     *class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     *@param useConnectMsg if true, the connect protocol will be used with a UDP server at the given remote
     *address and port
     *@see #establishConnection
     */
    public UDPClient(String address, int remotePort, boolean useConnectMsg) throws NetworkException {
        try {
            socket = new DatagramSocket();
            localPort = socket.getLocalPort();
            try {
                hostAddress = InetAddress.getByName(address);
                fixedHost = true;
                hostPort = remotePort;
                connect = useConnectMsg;
            }
            catch (UnknownHostException uhe) {
                hostAddress = null;
                fixedHost = false;
                connect = false;
            }
            receiver = new UDPReceiver(this, socket);
            receiver.start();
        }
        catch (SocketException se) {
            throw new NetworkException("UDPClient: Could not create datagram socket: " + se.getMessage());
        }
    }
    
    /**
     *Creates a unicast client for the specified host address and the specified ports. When establishConnection is
     *called, a special connect message will be sent if useConnectMsg parameter is true.
     *The new client will be listening on this port upon creation, unlike previous implementations of this
     *class.
     *@param address the host address to which messages will be sent by default; if not valid, the default
     *address will not be set and the client will only function as a free client
     *@param remotePort the port to which messages will be sent by default
     *@param localPort the local port on which the socket will be created to receive messages
     *@param useConnectMsg if true, the connect protocol will be used with a UDP server at the given remote
     *address and port
     */
    public UDPClient(String address, int remotePort, int localPort, boolean useConnectMsg) throws NetworkException {
        try {
            this.localPort = localPort;
            socket = new DatagramSocket(localPort);
            try {
                hostAddress = InetAddress.getByName(address);
                fixedHost = true;
                hostPort = remotePort;
                connect = useConnectMsg;
            }
            catch (UnknownHostException uhe) {
                hostAddress = null;
                fixedHost = false;
                connect = false;
            }
            receiver = new UDPReceiver(this, socket);
            receiver.start();
        }
        catch (SocketException se) {
            throw new NetworkException("UDPClient: Could not create datagram socket on the given port: " + se.getMessage());
        }
    }
    
    /**
     *Implementation of the PluggableClient interface. Implements a connection protocol for use with a
     *UDPServer class if this behavior has been specified upon instantiation of the UDP client. If not, a
     *call to this method will do nothing.
     */
    @Override
    public void establishConnection() throws NetworkException {
        if (fixedHost && connect) {
            try {
                sendConnectMessage();
                String portMsg = receive();
                try {
                    hostPort = Integer.parseInt(portMsg);
                }
                catch (Exception e) {
                    fixedHost = false;
                    throw new NetworkException("UDPClient: Problem in connection with host: Bad port message: " + e.getMessage());
                }
            }
            catch (IOException ioe) {
                fixedHost = false;
                throw new NetworkException("UDPClient: Could not establish connection: " + ioe.getMessage());
            }
            finally {
                connect = false;
            }
        }
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param msg the message string to send to the host
     */
    @Override
    public void send(String msg) throws NetworkException {
        if (fixedHost) {
            byte[][] packets = packetize(msg.getBytes());
            for (byte[] packet : packets) {
                DatagramPacket p = new DatagramPacket(packet, packet.length, hostAddress, hostPort);
                try {
                    socket.send(p);
                }
                catch (IOException e) {
                    throw new NetworkException("UDPClient: Could not send message: " + e.getMessage());
                }
            }
        }
        else throw new NetworkException("UDPClient: Could not send message: No remote host specified");
    }

    /**
     * Allows a client to send the message to the host from which the given message was received.
     * This method is particular to UDPClient (not a MessagingClient method)
     * @param event the event containing the origin of the message, to which the reply will be
     * sent
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
     *Sends a message to the specified destination (Particular to the UDPClient)
     *@param msg the message to send
     *@param address the URL string
     *@param port the port number
     */
    public void send(String msg, String address, int port) throws NetworkException {
        InetAddress dest = null;
        try {
            dest = InetAddress.getByName(address);
            byte[][] packets = packetize(msg.getBytes());
            for (byte[] packet : packets) {
                DatagramPacket p = new DatagramPacket(packet, packet.length, dest, port);
                try {
                    socket.send(p);
                }
                catch (IOException e) {
                    throw new NetworkException("UDPClient: Could not send message: " + e.getMessage());
                }
            }
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Cannot send message: Unknown host");
        }
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param bytesMessage the packet of bytes to send to the host
     */
    @Override
    public void send(byte[] bytesMessage) throws NetworkException {
        if (fixedHost) {
            byte[][] packets = packetize(bytesMessage);
            for (byte[] packet : packets) {
                DatagramPacket p = new DatagramPacket(packet, packet.length, hostAddress, hostPort);
                try {
                    socket.send(p);
                }
                catch (IOException e) {
                    throw new NetworkException("UDPClient: Could not send message: " + e.getMessage());
                }
            }
        }
        else throw new NetworkException("UDPClient: Could not send message: No remote host specified");
    }

    /**
     * Allows a client to send the message to the host from which the given message was received.
     * This method is particular to UDPClient (not a MessagingClient method)
     * @param event the event containing the origin of the message, to which the reply will be
     * sent
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
     *Sends a packet of bytes to the specified destination (Particular to the UDPClient)
     *@param bytesMessage the packet to send
     *@param address the URL string
     *@param port the port number
     */
    public void send(byte[] bytesMessage, String address, int port) throws NetworkException {
        InetAddress dest = null;
        try {
            dest = InetAddress.getByName(address);
            byte[][] packets = packetize(bytesMessage);
            Debug.println("Sending " + packets.length + " UDP packets", "programming5.net.sockets.UDPClient");
            for (byte[] packet : packets) {
                DatagramPacket p = new DatagramPacket(packet, packet.length, dest, port);
                Debug.println("UDP packet length: " + Integer.toString(packet.length), "programming5.net.sockets.UDPClient");
                try {
                    socket.send(p);
                }
                catch (IOException e) {
                    throw new NetworkException("UDPClient: Could not send message: " + e.getMessage());
                }
            }
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Cannot send message: Unknown host");
        }
    }

    /**
     *Implementation of the MessagingClient interface. Sends the given message to the given uri.
     *@param message the message to send
     *@param uri the destination uri, which must be in the format [protocol:]//host:port[/...]
     */
    @Override
    public void send(String message, String uri) throws NetworkException {
        try {
            URI urlObj = new URI(uri);
            this.send(message, urlObj.getHost(), urlObj.getPort());
        }
        catch (URISyntaxException use) {
            throw new NetworkException("UDPClient: Cannot send message: " + use.getMessage());
        }
    }

    /**
     *Implementation of the MessagingClient interface. Sends the given message to the given uri.
     *@param message the message to send
     *@param uri the destination uri, which must be in the format [protocol:]//host:port[/...]
     */
    @Override
    public void send(byte[] bytesMessage, String uri) throws NetworkException {
        try {
            URI urlObj = new URI(uri);
            this.send(bytesMessage, urlObj.getHost(), urlObj.getPort());
        }
        catch (URISyntaxException use) {
            throw new NetworkException("UDPClient: Cannot send message: " + use.getMessage());
        }
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
     *Implementation of the PluggableClient interface. Stops the receiver thread and
     *closes the socket.
     */
    @Override
    public void endConnection() {
        receiver.end();
        socket.close();
    }
    
    /**
     *Overrides method in Publisher to include the response to the receive methods
     */
    @Override
    public void fireEvent(MessageArrivedEvent event) {
        if (event != null) {
            if (!connect) {
                super.fireEvent(event);
            }
            else {
                connect = false;
            }
            synchronized (receiveRequests) {
                for (ReceiveRequest request : receiveRequests) {
                    request.setMessage(event.getContentBytes());
                }
                receiveRequests.clear();
            }
        }
    }
    
    /**
     *@return the local host address
     */
    public String getHostAddress() {
        String ret = null;
        if (hostAddress != null) {
            ret = hostAddress.getHostAddress();
        }
        return ret;
    }
    
    /**
     *@return the port to which the client is currently sending
     */
    public int getHostPort() {
        return hostPort;
    }

    /**
     * @return the url of the host from which the last message was received; null if no messages 
     * have been received
     */
    public String getReplyAddress() {
        return receiver.getReplyAddress();
    }
    
    /**
     *@return the local port on which the client is listening
     */
    public int getLocalPort() {
        return localPort;
    }

    private void sendConnectMessage() throws IOException {
        byte[] connectMsg = (SEPARATOR + CONNECT_MSG).getBytes();
        DatagramPacket packet = new DatagramPacket(connectMsg, connectMsg.length, hostAddress, hostPort);
        socket.send(packet);
    }
    
    private byte[][] packetize(byte[] bytesMsg) {
        int msgSize = bytesMsg.length;
        int numPackets = (int) (msgSize / MAX_SIZE) + 1;
        String npString = Integer.toString(numPackets);
        String messageID = Long.toString(random.nextLong());
        byte[][] ret = new byte[numPackets][];
        for (int i = 0; i < numPackets - 1; i++) {
            String index = Integer.toString(i+1);
            byte[] header = (messageID + SLASH + index + SLASH + npString + SEPARATOR).getBytes();
            byte[] packet = ArrayOperations.subArray(bytesMsg, i*MAX_SIZE, (i+1)*MAX_SIZE);
            ret[i] = ArrayOperations.join(header, packet);
            Debug.println(new String(ret[i]), "programming5.net.sockets.UDPClient#packetize");
        }
        byte[] header = (messageID + SLASH + npString + SLASH + npString + SEPARATOR).getBytes();
        byte[] packet = ArrayOperations.suffix(bytesMsg, (numPackets-1)*MAX_SIZE);
        ret[numPackets-1] = ArrayOperations.join(header, packet);
        Debug.println(new String(ret[numPackets-1]), "programming5.net.sockets.UDPClient#packetize");
        return ret;
    }
    
}
