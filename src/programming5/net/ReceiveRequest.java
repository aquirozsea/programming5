/*
 * ReceiveRequest.java
 *
 * Copyright 2008 Andres Quiroz Hernandez
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

import programming5.concurrent.ConditionVariable;

/**
 * Synchronization object for calls to the blocking receive methods of MessagingClient implementations.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class ReceiveRequest extends ConditionVariable {
    
    protected byte[] message = null;
    private boolean active = false;
    
    public ReceiveRequest() {
        super();
    }
    
    /**
     *Called once a receive method has been called (waiting for a message)
     */
    public void activate() {
        active = true;
    }
    
    /**
     *@return true if the request is outstanding
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     *Sets the message that is the signaling condition for this object
     */
    public void setMessage(byte[] rvdMessage) {
        message = rvdMessage;
        this.signalAll();
    }
    
    /**
     *@return the message that was set for this object
     */
    public byte[] getMessage() {
        return message;
    }
    
    /**
     *Equivalent to the isTrue method of the parent class.
     *@see programming5.concurrent.ConditionVariable#isTrue
     */
    public boolean isDone() {
        return this.isTrue();
    }
    
}
