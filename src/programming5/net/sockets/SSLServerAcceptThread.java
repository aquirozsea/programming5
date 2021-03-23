/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.net.sockets;

import programming5.net.NetworkException;
import programming5.net.ServerAcceptThread;
import programming5.net.ServiceObject;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author andresqh
 */
public class SSLServerAcceptThread extends ServerAcceptThread {

    protected ServerSocket accepter;
    private boolean listening = true;

    private int acceptFailCount = 0;
    private static final int ACCEPT_FAIL_LIMIT = 10;

    /**
     *Creates an accept thread that listens on an available port (which can be retrieved with the getLocalPort method).
     *@see #getLocalPort
     */
    public SSLServerAcceptThread(ServiceObject myServer) throws NetworkException {
        super(myServer);
        try {
            accepter = SSLServerSocketFactory.getDefault().createServerSocket(0);
        }
	catch (IOException ioe) {
            throw new NetworkException("TCPServerAcceptThread: Couldn't create server socket: " + ioe.getMessage());
        }
    }

    /**
     *Creates an accept thread that listens on the given port.
     */
    public SSLServerAcceptThread(ServiceObject myServer, int port) throws NetworkException {
        super(myServer);
        try {
            if (port < 0) {
                port = 0;
            }
            accepter = SSLServerSocketFactory.getDefault().createServerSocket(port);
        }
	catch (IOException ioe) {
            throw new NetworkException("TCPServerAcceptThread: Couldn't create server socket: " + ioe.getMessage());
        }
    }

    /**
     *@return the port on which the thread is listening (on which the server socket was created)
     */
    public int getLocalPort() {
        return accepter.getLocalPort();
    }

    /**
     *Accepts new clients until the thread is stopped
     */
    @Override
    public void run() {
        while (listening) {
            try {
                final Socket socket = accepter.accept();
                acceptFailReset();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            serverRef.newClient(new TCPClient(socket));
                        }
                        catch (NetworkException ne) {
                            System.err.println("TCPServerAcceptThread: Couldn't create TCPClient: " + ne.getMessage());
                        }
                    }
                }).start();
            }
            catch (IOException ioe) {
                if (listening) {
                    System.err.println("TCPServerAcceptThread: Couldn't accept connection: " + ioe.getMessage());
                    acceptFailTest();
                }
            }
        }
    }

    /**
     *Stops the thread and closes the socket
     */
    public void end() {
        listening = false;
        try {
            accepter.close();
        }
        catch (IOException ioe) {
            throw new RuntimeException("TCPServerAcceptThread: Couldn't close server socket");
        }
    }

    private void acceptFailTest() {
        if (++acceptFailCount > ACCEPT_FAIL_LIMIT) {
            this.end();
        }
    }

    private void acceptFailReset() {
        acceptFailCount = 0;
    }

}
