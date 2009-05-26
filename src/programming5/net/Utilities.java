/*
 * Utilities.java
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

package programming5.net;

import java.net.DatagramSocket;
import java.net.NetworkInterface;
import java.net.InetAddress;
import java.util.Enumeration;

/**
 * Contains utility methods for network programs.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public abstract class Utilities {
    
    /**
     *@return an unused port network port
     */
    public static final int getAvailablePort() {
        int ret = -1;
        try {
            DatagramSocket s = new DatagramSocket();
            ret = s.getLocalPort();
            s.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     *@return the host IP address of the default network interface
     */
    public static final String getLocalAddress() {
        String ret = "127.0.0.1";
        try {
            NetworkInterface ni = NetworkInterface.getByName("eth0");
            Enumeration<InetAddress> e = ni.getInetAddresses();
            if (e.hasMoreElements()) {
                InetAddress address = e.nextElement();
                ret = address.getHostAddress();
            }
        } 
        catch (Exception e) {}
        return ret;
    }
    
    /**
     *@return the host IP address of the given network interface
     */
    public static final String getLocalAddress(String NetIfaceName) {
        String ret = "127.0.0.1";
        try {
            NetworkInterface ni = NetworkInterface.getByName(NetIfaceName);
            Enumeration<InetAddress> e = ni.getInetAddresses();
            if (e.hasMoreElements()) {
                InetAddress address = e.nextElement();
                ret = address.getHostAddress();
            }
        } 
        catch (Exception e) {}
        return ret;
    }
    
}
