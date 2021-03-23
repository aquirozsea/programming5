/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.net.sockets;

import programming5.net.NetworkException;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author andresqh
 */
public class SSLClient extends TCPClient {

    public SSLClient() throws IOException {
        super(SSLSocketFactory.getDefault().createSocket());
    }

    /**
     *Creates an SSL client for the specified host address at the specified port. Will bind the client to any available local port.
     */
    public SSLClient(String address, int remotePort) throws IOException {
        super(SSLSocketFactory.getDefault().createSocket(address, remotePort));
    }

    /**
     *Creates an SSL client on the given local port for the specified host address at the specified port.
     */
    public SSLClient(String address, int remotePort, int localPort) throws IOException {
        super(SSLSocketFactory.getDefault().createSocket(address, remotePort, InetAddress.getLocalHost(), localPort));
    }

    /**
     *Implementation of the PluggableClient interface. Opens a socket to each of the host
     *addresses with which the client has been created, gets their output streams, and starts their receiver
     *threads.
     */
    @Override
    public void establishConnection() throws NetworkException {
        // No need for implementation until pending connections is used
    }

}
