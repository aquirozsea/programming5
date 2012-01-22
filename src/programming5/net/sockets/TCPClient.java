/*
 * TCPClient.java
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

import java.net.Socket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import programming5.arrays.ArrayOperations;
import programming5.collections.MultiList;
import programming5.io.Debug;
import programming5.net.MessageArrivedEvent;
import programming5.net.MessagingClient;
import programming5.net.NetworkException;
import programming5.net.Publisher;
import programming5.net.ReceiveRequest;

/**
 *This class is the TCP socket implementation of a MessagingClient. Can be used to handle several TCP connections. Messages will be sent 
 *to all open connections if no address is specified (messages sent to a specific address will create connections that remain open until 
 *the object is terminated and all connections are closed), and will be received from any connection indistinguishably. If incoming 
 *messages need to be distinguished by origin, separate TCPClient objects should be used for each one.
 *@see programming5.net.MessagingClient
 *@see programming5.net.PluggableClient
 *@see java.net.Socket
 *@author Andres Quiroz Hernandez
 *@version 6.09
 */
public class TCPClient extends Publisher<MessageArrivedEvent> implements MessagingClient {
    
    protected List<String> pendingConnections = new ArrayList<String>();
    protected Map<String, Socket> connections = new HashMap<String, Socket>();
    protected Map<String, OutputStream> outStreams = new HashMap<String, OutputStream>();
    protected Map<String, Integer> localPorts = new HashMap<String, Integer>();
    protected Map<String, TCPReceiver> receivers = new HashMap<String, TCPReceiver>();
    protected final List<ReceiveRequest> receiveRequests = new ArrayList<ReceiveRequest>();
    
    private static final int ANYPORT = -1;
    private boolean useSeparator = true;
    protected static final byte[] SEPARATOR = ":".getBytes();
    
    /**
     *Creates an empty TCP client
     */
    public TCPClient() {
        
    }
    
    /**
     *Creates a TCP client for the specified host address at the specified port. Will bind the client to any available local port.
     */
    public TCPClient(String address, int remotePort) {
        String urlString = "//" + address + ":" + Integer.toString(remotePort);
        pendingConnections.add(urlString);
        localPorts.put(urlString, ANYPORT);
    }
    
    /**
     *Creates a TCP client on the given local port for the specified host address at the specified port.
     */
    public TCPClient(String address, int remotePort, int localPort) {
        String urlString = "//" + address + ":" + Integer.toString(remotePort);
        pendingConnections.add(urlString);
        localPorts.put(urlString, localPort);
    }
    
    /**
     *Creates a TCP client from an already open socket and immediately creates and
     *starts the receiver thread.
     */
    public TCPClient(Socket socket) throws NetworkException {
        try {
            String urlString = "//" + socket.getInetAddress().toString() + ":" + Integer.toString(socket.getPort());
            connections.put(urlString, socket);
            outStreams.put(urlString, socket.getOutputStream());
            TCPReceiver receiver = new TCPReceiver(socket, urlString);
            receivers.put(urlString, receiver);
            receiver.start();
        } 
        catch (IOException e) {
            throw new NetworkException("TCPClient: Could not initialize client: " + e.getMessage());
        }
    }
    
    /**
     *Creates a TCP client from an already open socket and immediately creates and starts the receiver thread
     *@param socket the client socket
     *@param compatible if true, starts the client in compatible mode, which allows it to communicate with non-programming5 clients by 
     *disabling the message separator signals in the send/receive stream; otherwise, equal to using TCPClient(Socket)
     */
    public TCPClient(Socket socket, boolean compatible) throws NetworkException {
        try {
            String urlString = "//" + socket.getInetAddress().toString() + ":" + Integer.toString(socket.getPort());
            connections.put(urlString, socket);
            outStreams.put(urlString, socket.getOutputStream());
            TCPReceiver receiver;
            if (compatible) {
                useSeparator = false;
                receiver = new CompatibleTCPReceiver(socket, urlString);
            }
            else {
                receiver = new TCPReceiver(socket, urlString);
            }
            receivers.put(urlString, receiver);
            receiver.start();
        } 
        catch (IOException e) {
            throw new NetworkException("TCPClient: Could not initialize client: " + e.getMessage());
        }
    }
    
    /**
     *Creates a TCPClient that can be used to communicate with all the hosts addressed by the given urls.
     */
    public TCPClient(Collection<String> hostUrls) {
        for (String url : hostUrls) {
            pendingConnections.add(url);
            localPorts.put(url, ANYPORT);
        }
    }
    
    /**
     *Creates a TCPClient that can be used to communicate with all the hosts addressed by the given urls. The clients will be 
     *bound to the local ports specified with the urls.
     */
    public TCPClient(MultiList<String, Integer> socketDescriptors) {
        for (String url : socketDescriptors.first()) {
            pendingConnections.add(url);
            localPorts.put(url, socketDescriptors.getInSecond(url));
        }
    }
    
    /**
     *Implementation of the PluggableClient interface. Opens a socket to each of the host
     *addresses with which the client has been created, gets their output streams, and starts their receiver
     *threads.
     */
    @Override
    public void establishConnection() throws NetworkException {
        if (pendingConnections.size() > 0) {
            MultiList<String, String> failedConnections = new MultiList<String, String>();
            for (String host : pendingConnections) {
                int localPort = localPorts.get(host);
                try {
                    Socket socket = connections.get(host);
                    if (socket == null) {
                        URI hostUrl = new URI(host);
                        if (localPort == ANYPORT) {
                            socket = new Socket(InetAddress.getByName(hostUrl.getHost()), hostUrl.getPort());
                        }
                        else {
                            socket = new Socket(InetAddress.getByName(hostUrl.getHost()), hostUrl.getPort(), InetAddress.getLocalHost(), localPort);
                        }
                        connections.put(host, socket);
                        outStreams.put(host, socket.getOutputStream());
                        TCPReceiver receiver = new TCPReceiver(socket, host);
                        receivers.put(host, receiver);
                        receiver.start();
                    }
                }
                catch (Exception e) {
                    failedConnections.add(host, e.getMessage());
                }
            }
            pendingConnections.clear();
            if (failedConnections.size() > 0) {
                String message = "TCPClient: The following connections could not be established:\n";
                for (String connection : failedConnections.first()) {
                    message = message + connection + ": " + failedConnections.getInSecond(connection) + "\n";
                }
                throw new NetworkException(message);
            }
        }
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param bytesMessage the packet of bytes to send to the hosts for which sockets have been opened
     */
    public void send(byte[] bytesMessage) throws NetworkException {
        if (useSeparator) {
            byte[] header = ArrayOperations.join(Integer.toString(bytesMessage.length).getBytes(), SEPARATOR);
            bytesMessage = ArrayOperations.join(header, bytesMessage);
        }
        MultiList<String, String> failedSend = new MultiList<String, String>();
        for (String host : connections.keySet()) {
            try {
                OutputStream out = outStreams.get(host);
                if (out != null) {
                    synchronized (out) {
                        out.write(bytesMessage);
                        out.flush();
                    }
                }
            }
            catch (Exception e) {
                failedSend.add(host, e.getMessage());
            }
        } 
        if (failedSend.size() > 0) {
            String errorMessage = "TCPClient: The following connections could not be established:\n";
            for (String send : failedSend.first()) {
                errorMessage = errorMessage + send + ": " + failedSend.getInSecond(send) + "\n";
            }
            throw new NetworkException(errorMessage);
        }
    }

    /**
     *Implementation of the MessagingClient interface
     *@param msg the message string to send to the hosts for which sockets have been opened
     */
    public void send(String msg) throws NetworkException {
        this.send(msg.getBytes());
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param bytesMessage the message to send to the given host
     *@param url the address the message will be sent to; if a socket hasn't been opened to this address, it will be created and started 
     */
    public void send(byte[] bytesMessage, String url) throws NetworkException {
        if (useSeparator) {
            byte[] header = ArrayOperations.join(Integer.toString(bytesMessage.length).getBytes(), SEPARATOR);
            bytesMessage = ArrayOperations.join(header, bytesMessage);
        }
        OutputStream out = outStreams.get(url);
        try {
            if (out == null) {
                URI hostUrl = new URI(url);
                Socket socket = new Socket(InetAddress.getByName(hostUrl.getHost()), hostUrl.getPort());
                connections.put(url, socket);
                out = socket.getOutputStream();
                outStreams.put(url, out);
                TCPReceiver receiver = new TCPReceiver(socket, url);
                receivers.put(url, receiver);
                receiver.start();
            }
            synchronized (out) {
                out.write(bytesMessage);
                out.flush();
            }
        }
        catch (Exception e) {
            throw new NetworkException("TCPClient: Could not send message to " + url + ": " + e.getMessage());
        }
    }
    
    /**
     *Implementation of the MessagingClient interface
     *@param message the message string to send to the given host
     *@param url the address the message will be sent to; if a socket hasn't been opened to this address, it will be created and started 
     */
    public void send(String message, String url) throws NetworkException {
        this.send(message.getBytes(), url);
    }
    
    /**
     *Implementation of the MessagingClient interface. Blocking receive until packet
     *arrives from any of the open connections.
     *@return the packet of bytes
     */
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
     *Implementation of the MessagingClient interface. Blocking receive until message
     *arrives from any of the open connections.
     *@return the message string
     */
    public String receive() {
        return new String(this.receiveBytes());
    }
    
    /**
     *Implementation of the MessagingClient interface. Blocking receive until packet
     *arrives from any of the open connections or timeout occurs.
     *@return the packetof bytes
     */
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
     *Implementation of the MessagingClient interface. Blocking receive until message
     *arrives from any of the open connections or timeout occurs.
     *@return the message string
     */
    public String receive(long timeout) throws InterruptedException {
        return new String(this.receiveBytes(timeout));
    }
    
    /**
     *Implementation of the PluggableClient interface. Stops the receiver threads and
     *closes open sockets.
     */
    public synchronized void endConnection() {
        for (String host : outStreams.keySet()) {
            sendTerminationMessage(host);
        }
        for (String host : receivers.keySet()) {
            receivers.get(host).end();
        } 
    }
    
    /**
     *Overrides method in Publisher to include the response to the receive methods
     */
    @Override
    public void fireEvent(MessageArrivedEvent event) {
        super.fireEvent(event);
        synchronized (receiveRequests) {
            for (ReceiveRequest request : receiveRequests) {
                request.setMessage(event.getContentBytes());
            }
            receiveRequests.clear();
        }
    }
    
    private synchronized void signalConnectionError(String host) {
        Debug.println("Ending connections", "programming5.net.sockets.TCPClient");
        try {outStreams.remove(host)/*.close()*/;} catch (Exception e) {e.printStackTrace();}
        try {connections.remove(host)/*.close()*/;} catch (Exception e) {e.printStackTrace();}
        this.terminateAllSubscriptions();
    }
    
    private void sendTerminationMessage(String host) {
        byte[] bytesMessage = "EOF".getBytes();
        if (useSeparator) {
            byte[] header = ArrayOperations.join(Integer.toString(-1).getBytes(), SEPARATOR);
            bytesMessage = ArrayOperations.join(header, bytesMessage);
        }
        OutputStream out = outStreams.get(host);
        if (out != null) {
            try {
                synchronized (out) {
                    out.write(bytesMessage);
                    out.flush();
                }
            }
            catch (IOException ioe) {}
        }
    }
    
    protected class TCPReceiver extends Thread {
        
        Socket socketRef;
        InputStream in;
        boolean listening = true;
        String hostname;
        
        protected static final int BUFFER_SIZE = 65536;
        
        public TCPReceiver(Socket mySocket, String myHost) throws NetworkException {
            try {
                socketRef = mySocket;
                in = mySocket.getInputStream();
                hostname = myHost;
            } 
            catch (IOException e) {
                throw new NetworkException("TCPReceiver: Constructor: " + e.getMessage());
            }
        }
        
        @Override
        public void run() {
            byte[] fromServer = new byte[BUFFER_SIZE];
            byte[] bytesMsg = null; // = new byte[0];
            int bytesRead, headerPos, msgStart, msgLength, msgEnd;
            try {
                while (listening) {
                    msgStart = 0;
                    msgLength = -1;
                    msgEnd = -1;
                    headerPos = -1;
                    bytesRead = in.read(fromServer);
                    if (bytesRead > 0) {
                        bytesMsg = ArrayOperations.prefix(fromServer, bytesRead);
                    }
                    while (bytesRead > 0) {
                        try {
                            headerPos = ArrayOperations.seqFind(TCPClient.SEPARATOR[0], bytesMsg, msgStart);
                            while (headerPos <= msgStart) {
                                bytesRead += in.read(fromServer);
                                if (bytesRead > 0) {
                                    bytesMsg = ArrayOperations.join(bytesMsg, ArrayOperations.prefix(fromServer, bytesRead));
                                }
                                headerPos = ArrayOperations.seqFind(TCPClient.SEPARATOR[0], bytesMsg, msgStart);
                            }
                            bytesRead -= (headerPos - msgStart + 1);
                            msgLength = Integer.parseInt(new String(ArrayOperations.subArray(bytesMsg, msgStart, headerPos)));
                            if (msgLength >= 0) {
                                while (msgLength > bytesRead) {
                                    int moreBytesRead = in.read(fromServer);
                                    bytesRead += moreBytesRead;
                                    if (moreBytesRead > 0) {
                                        bytesMsg = ArrayOperations.join(bytesMsg, ArrayOperations.prefix(fromServer, moreBytesRead));
                                    }
                                }
                                msgStart = headerPos + 1;
                                msgEnd = msgStart + msgLength;
                                try {
                                    fireEvent(new MessageArrivedEvent(ArrayOperations.subArray(bytesMsg, msgStart, msgEnd)));
                                    bytesRead -= msgLength;
                                    msgStart = msgEnd;
                                }
                                catch (ArrayIndexOutOfBoundsException iobe) {
                                    Debug.println("TCPReceiver: Bad message received");
                                    bytesRead = -1;
                                }
                                catch (Exception e) {
                                }
                            }
                            else {
                                listening = false;
                                bytesRead = 0;
                                Debug.println("Termination message received at " + hostname, "programming5.net.sockets.TCPClient");
                                signalConnectionError(hostname);
                            }
                        }
                        catch (OutOfMemoryError oome) {
                            Runtime.getRuntime().gc();
                            Debug.println("Out of memory after reading:");
                            Debug.println("Bytes msg length: " + bytesMsg.length);
                            Debug.println("Bytes read: " + bytesRead);
                            Debug.println("Header pos: " + headerPos);
                            Debug.println("Msg start: " + msgStart);
                            Debug.println("Msg length: " + msgLength);
                            Debug.println("Msg end: " + msgEnd);
                        }
                        catch (Exception e) {
                            Debug.println("TCPReceiver: Bad message received");
                            bytesRead = -1;
                        }
                    }
                }
                end();
            } 
            catch (IOException io) {
                Debug.println("TCPReceiver: Exception while receiving: " + io.getMessage());
                signalConnectionError(hostname);
            }
        }
        
        public void end() {
            try {
                socketRef.close();
                listening = false;
            }
            catch (IOException ioe) {
                Debug.printStackTrace(ioe);
            }
        }
        
    }
    
    protected class CompatibleTCPReceiver extends TCPReceiver {
        
        public CompatibleTCPReceiver(Socket mySocket, String myHost) throws NetworkException {
            super(mySocket, myHost);
        }
        
        @Override
        public void run() {
            byte[] fromServer = new byte[BUFFER_SIZE];
            byte[] bytesMsg = null;
            int bytesRead;
            try {
                while (listening) {
                    bytesRead = in.read(fromServer);
                    if (bytesRead > 0) {
                        bytesMsg = ArrayOperations.prefix(fromServer, bytesRead);
                        fireEvent(new MessageArrivedEvent(bytesMsg));
                    }
                }
                end();
            } 
            catch (IOException io) {
                Debug.println("TCPReceiver: Exception while receiving: " + io.getMessage());
                signalConnectionError(hostname);
            }
        }
    }
    
}
