/*
 * ReliableUDPEnforcerThread.java
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

import programming5.net.MalformedMessageException;
import programming5.net.NetworkException;
import programming5.net.ReliableProtocolMessage;

/**
 *This class complements the ReliableUDPClient. It keeps sending a message until it obtains an acknowledgement.
 *@see ReliableUDPClient
 *@author Andres Quiroz Hernandez
 *@version 6.0
 *@deprecated This class is no longer used by the ReliableUDPClient class
 */
@Deprecated
public class ReliableUDPEnforcerThread extends Thread {
    
    ReliableProtocolMessage enforcedMsg;
    ReliableProtocolMessage receivedMsg;
    ReliableUDPClient ownerRef;
    UDPClient client;
    int timeout;
    int failCount = 0;
    boolean done = false;
    
    public static int MAX_WAIT = 3;
    
    public ReliableUDPEnforcerThread(ReliableProtocolMessage myEnforcedMsg, UDPClient myClient, int myTimeout, ReliableUDPClient myOwner) {
        enforcedMsg = myEnforcedMsg;
        client = myClient;
        timeout = myTimeout;
        ownerRef = myOwner;
    }
    
    public void run() {
        String message = null;
        while (!done) {
            try {
                message = client.receive(timeout);
                if (message != null) {
                    try {
                        receivedMsg = new ReliableProtocolMessage(message.getBytes());
                        if (receivedMsg.isAcknowledge() && receivedMsg.getSequence() == enforcedMsg.getSequence()) {
                            done = true;
                        }
                    } 
                    catch (MalformedMessageException mme) {
                        failCount = 0;
                        throw new InterruptedException();
                    }
                }
            } 
            catch (InterruptedException ie) {
                failCount++;
                if (failCount == MAX_WAIT) {
                    ownerRef.signalFail("Fail");
                    done = true;
                } 
                else {
                    try {client.send(enforcedMsg.getMessage());} 
                    catch (MalformedMessageException mme) {}
                    catch (NetworkException ne) {}
                }
            }
        }
    }
}
