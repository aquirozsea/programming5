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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import programming5.arrays.ArrayOperations;
import programming5.net.MessageArrivedEvent;
import programming5.net.MessagingClient;
import programming5.net.NetworkException;
import programming5.net.Publisher;
import programming5.net.ReceiveRequest;

/**
 *This class is the UDP socket implementation of the MessagingClient. Following its design, it can be instantiated 
 *for a specific host destination; since datagram sockets aren't meant to be opened for a single destination, an 
 *additional send method is provided that has the destination as a parameter to override the predefined host. If a 
 *host is not specified in the object's instantiation, a default server address and port will be held for communication, 
 *until a message is received, in which case the MessagingClient send method will reply to the last host from which the 
 *message was received; again, this behavior can be overridden with the specific send method.
 *@see programming5.net.MessagingClient
 *@see #send(String, String, int)
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class UDPClient extends Publisher<MessageArrivedEvent> implements MessagingClient {
    
    protected DatagramSocket socket;
    protected InetAddress hostAddress;
    protected int hostPort, localPort;
    protected UDPReceiver receiver;
    protected boolean listening = false;
    protected boolean fixedHost, connect;
    protected Queue<byte[]> msgQueue = new LinkedList<byte[]>();
    protected ReceiveRequest currentRequest = new ReceiveRequest();
    private Timer timeoutTimer = new Timer();
    
    private static final String DEFAULTHOST = "localhost";
    private static final int DEFAULTREMOTEPORT = 4445;
    private static final int ANYPORT = -1;
    private static final int MAX_SIZE = 65507;
    
    /**
     *Creates a unicast client for which the local port will be determined by an available port at the time of connection.
     *Messages will be sent by default to the localhost on port 4445.
     */
    public UDPClient() throws NetworkException {
        try {
            hostAddress = InetAddress.getByName(DEFAULTHOST);
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Constructor: Unknown host");
        }
        hostPort = DEFAULTREMOTEPORT;
        localPort = ANYPORT;
        fixedHost = false;
    }
    
    /**
     *Creates a unicast client on the specified local port. Messages will be sent by default to the localhost on port 4445.
     */
    public UDPClient(int localPort) throws NetworkException {
        try {
            hostAddress = InetAddress.getByName(DEFAULTHOST);
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Constructor: Unknown host");
        }
        this.localPort = localPort;
        hostPort = DEFAULTREMOTEPORT;
        fixedHost = false;
    }
    
    /**
     *Creates a unicast client for the specified host address and remote port, to be bound on an available local port.
     *When establishConnection is called, no special connect message will be sent.
     */
    public UDPClient(String address, int remotePort) throws NetworkException {
        try {
            hostAddress = InetAddress.getByName(address);
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Constructor: Unknown host");
        }
        hostPort = remotePort;
        localPort = ANYPORT;
        fixedHost = true;
        connect = false;
    }
    
    /**
     *Creates a unicast client for the specified host address and the specified ports. When establishConnection is
     *called, no special connect message will be sent.
     */
    public UDPClient(String address, int remotePort, int localPort) throws NetworkException {
        try {
            hostAddress = InetAddress.getByName(address);
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Constructor: Unknown host");
        }
        hostPort = remotePort;
        this.localPort = localPort;
        fixedHost = true;
        connect = false;
    }
    
    /**
     *Creates a unicast client for the specified host address and remote port, to be bound on an available local port.
     *When establishConnection is called, a special connect message will be sent if useConnectMsg parameter is true.
     */
    public UDPClient(String address, int remotePort, boolean useConnectMsg) throws NetworkException {
        try {
            hostAddress = InetAddress.getByName(address);
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Constructor: Unknown host");
        }
        hostPort = remotePort;
        localPort = ANYPORT;
        fixedHost = true;
        connect = useConnectMsg;
    }
    
    /**
     *Creates a unicast client for the specified host address and the specified ports. When establishConnection is
     *called, a special connect message will be sent if useConnectMsg parameter is true.
     */
    public UDPClient(String address, int remotePort, int localPort, boolean useConnectMsg) throws NetworkException {
        try {
            hostAddress = InetAddress.getByName(address);
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Constructor: Unknown host");
        }
        hostPort = remotePort;
        this.localPort = localPort;
        fixedHost = true;
        connect = useConnectMsg;
    }
    
    /**
     *Implementation of the PluggableClient interface. Opens the datagram socket and
     *starts the receiver thread. Will send a special connect message if specified in the instantiation.
     */
    public void establishConnection() throws NetworkException {
        try {
            if (localPort == ANYPORT) {
                socket = new DatagramSocket();
                localPort = socket.getLocalPort();
            } 
            else {
                socket = new DatagramSocket(localPort);
            }
            receiver = new UDPReceiver(this, socket);
            receiver.start();
            if (fixedHost && connect) {
                fixedHost = false;
                send("Connect");
                String portMsg = receive();
                fixedHost = true;
                try {
                    hostPort = Integer.parseInt(portMsg);
                } 
                catch (Exception e) {
                    throw new NetworkException("UDPClient: Problem in connection with host: Bad port message");
                }
            }
        } 
        catch (IOException e) {
            throw new NetworkException("UDPClient: Can't establish connection: " + e.getMessage());
        }
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param msg the message string to send to the host
     */
    public void send(String msg) throws NetworkException {
        byte[][] packets = packetize(msg.getBytes());
        for (byte[] packet : packets) {
            DatagramPacket p = new DatagramPacket(packet, packet.length, hostAddress, hostPort);
            try {
                socket.send(p);
            } 
            catch (IOException e) {
                throw new NetworkException("UDPClient: Couldn't send message: " + e.getMessage());
            }
            
        }
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
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Can't send message: Unknown host");
        }
        byte[][] packets = packetize(msg.getBytes());
        for (byte[] packet : packets) {
            DatagramPacket p = new DatagramPacket(packet, packet.length, dest, port);
            try {
                socket.send(p);
            } 
            catch (IOException e) {
                System.out.println("UDPClient: Couldn't send message: " + e.getMessage());
            }
        }
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param bytesMessage the packet of bytes to send to the host
     */
    public void send(byte[] bytesMessage) throws NetworkException {
        byte[][] packets = packetize(bytesMessage);
        for (byte[] packet : packets) {
            DatagramPacket p = new DatagramPacket(packet, packet.length, hostAddress, hostPort);
            try {
                socket.send(p);
            } 
            catch (IOException e) {
                throw new NetworkException("UDPClient: Couldn't send message: " + e.getMessage());
            }
        }
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
        } 
        catch (UnknownHostException e) {
            throw new NetworkException("UDPClient: Can't send message: Unknown host");
        }
        byte[][] packets = packetize(bytesMessage);
        for (byte[] packet : packets) {
            DatagramPacket p = new DatagramPacket(packet, packet.length, dest, port);
            try {
                socket.send(p);
            } 
            catch (IOException e) {
                System.out.println("UDPClient: Couldn't send message: " + e.getMessage());
            }
        }
    }

    /**
     *Implementation of the MessagingClient interface. Sends the given message to the given url, which must be in the format 
     *[protocol:]//host:port[/...]
     */
    public void send(String message, String url) throws NetworkException {
        try {
            URL urlObj = new URL(url);
            this.send(message, urlObj.getHost(), urlObj.getPort());
        }
        catch (MalformedURLException murle) {
            throw new NetworkException("UDPClient: Cannot send message: " + murle.getMessage());
        }
    }

    /**
     *Implementation of the MessagingClient interface. Sends the given message to the given url, which must be in the format 
     *[protocol:]//host:port[/...]
     */
    public void send(byte[] bytesMessage, String url) throws NetworkException {
        try {
            URL urlObj = new URL(url);
            this.send(bytesMessage, urlObj.getHost(), urlObj.getPort());
        }
        catch (MalformedURLException murle) {
            throw new NetworkException("UDPClient: Cannot send message: " + murle.getMessage());
        }
    }
    
    /**
     *Implementation of the MessagingClient interface. Blocking receive until message arrives.
     *@return the message bytes
     */
    public byte[] receiveBytes() {
        byte[] ret = null;
        if (msgQueue.size() > 0) {
            ret = msgQueue.poll();
        } 
        else {
            ReceiveRequest myRequest;
            synchronized (currentRequest) {
                currentRequest.activate();
                myRequest = currentRequest;
            }
            myRequest.awaitUninterruptibly();
            if (myRequest.isDone()) {
                ret = myRequest.getMessage();
            }
        }
        return ret;
    }
    
    /**
     *Implementation of the MessagingClient interface. Blocking receive until message arrives.
     *@return the message string
     */
    public String receive() {
        return new String(this.receiveBytes());
    }
    
    /**
     *Implementation of the MessagingClient interface. Waits for an incoming message for limited time.
     *@param timeout wait time in milliseconds
     *@return the message bytes
     */
    public byte[] receiveBytes(long timeout) throws InterruptedException {
        byte[] ret = null;
        if (msgQueue.size() > 0) {
            ret = msgQueue.poll();
        } 
        else {
            ReceiveRequest myRequest;
            synchronized (currentRequest) {
                currentRequest.activate();
                myRequest = currentRequest;
            }
            myRequest.await(timeout, TimeUnit.MILLISECONDS);
            if (myRequest.isDone()) {
                ret = myRequest.getMessage();
            }
        }
        return ret;
    }
    
    /**
     *Implementation of the MessagingClient interface. Waits for an incoming message for limited time.
     *@param timeout wait time in milliseconds
     *@return the message string
     */
    public String receive(long timeout) throws InterruptedException {
        return new String(this.receiveBytes(timeout));
    }
    
    /**
     *Implementation of the PluggableClient interface. Stops the receiver thread and
     *closes the socket.
     */
    public void endConnection() {
        receiver.end();
        socket.close();
    }
    
    /**
     *Overrides method in Publisher to include the response to the receive methods
     */
    public void fireEvent(MessageArrivedEvent event) {
        if (event != null) {
            if (!connect) {
                super.fireEvent(event);
            }
            else {
                connect = false;
            }
            byte[] messageBytes = event.getMessageBytes();
            synchronized (currentRequest) {
                if (currentRequest.isActive()) {
                    currentRequest.setMessage(event.getMessageBytes());
                    currentRequest = new ReceiveRequest();
                }
                else {
                    msgQueue.add(messageBytes);
                }
            }
            if (!fixedHost) {
                hostAddress = receiver.getLastAddress();
                hostPort = receiver.getLastPort();
            }
        }
    }
    
    /**
     *@return the local host address
     */
    public String getHostAddress() {
        return hostAddress.getHostAddress();
    }
    
    /**
     *@return the port to which the client is currently sending
     */
    public int getHostPort() {
        return hostPort;
    }
    
    /**
     *@return the local port on which the client is listening
     */
    public int getLocalPort() {
        return localPort;
    }
    
    private byte[][] packetize(byte[] bytesMsg) {
        int msgSize = bytesMsg.length;
        int numPackets = (int) (msgSize / MAX_SIZE) + 1;
        byte[][] ret = new byte[numPackets][];
        for (int i = 0; i < numPackets - 1; i++) {
            ret[i] = ArrayOperations.subArray(bytesMsg, i*MAX_SIZE, (i+1)*MAX_SIZE);
        }
        ret[numPackets-1] = ArrayOperations.suffix(bytesMsg, (numPackets-1)*MAX_SIZE);
        return ret;
    }
}
