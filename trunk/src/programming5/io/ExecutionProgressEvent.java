/*
 * ExecutionProgressEvent.java
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

package programming5.io;

import programming5.net.MalformedMessageException;
import programming5.net.Message;

/**
 * Event that represents the progress percentage of a given operation. This class is not "castable" from other
 * event types.
 * @author Andres Quiroz Hernandez
 * @version 6.11
 * @see programming5.net.Event
 * @see programming5.net.Event#castTo(java.lang.Class, programming5.net.Event)
 */
public class ExecutionProgressEvent extends programming5.net.Event {
    
    public static final String TYPE = "EPE";

    /** 
     * Creates a new instance of ExecutionProgressEvent with the current progress percentage and a total
     * value (when other that 100pct is used)
     */
    public ExecutionProgressEvent(float currentProgress, int referenceTotal) {
        super(TYPE);
        this.addMessageItem(currentProgress);
        this.addMessageItem(referenceTotal);
    }
    
    /** 
     * Creates a new instance of ExecutionProgressEvent with the current progress percentage
     */
    public ExecutionProgressEvent(float currentProgress) {
        super(TYPE);
        this.addMessageItem(currentProgress);
        this.addMessageItem(1);
    }
    
    /**
     *@deprecated this constructor is no longer supported
     *@throws UnsupportedOperationException
     */
    @Deprecated
    public ExecutionProgressEvent(Message evtMsg) {
        super(evtMsg);
    }

    /**
     * Creates an execution progress event by decoding the given byte array
     * @param eventBytes the encoded event message, which must follow the Message class syntax
     * @throws programming5.net.MalformedMessageException if the encoded message does not follow the correct
     * syntax or is of an incorrect type
     */
    public ExecutionProgressEvent(byte[] eventBytes) throws MalformedMessageException {
        super(eventBytes);
        if (!this.getType().equals(TYPE)) {
            throw new IllegalArgumentException("ExecutionProgressEvent: Cannot create from byte array: Incorrect type (found " + this.getType() + ")");
        }
        if (this.getMessageSize() != 2) {
            throw new MalformedMessageException("ExecutionProgressEvent: Cannot create from byte array: Incorrect number of items");
        }
    }

    @Override
    public boolean assertFormat() {
        boolean ret = true;
        try {
            this.getItemAsFloat(0);
            this.getItemAsInt(1);
        }
        catch (MalformedMessageException mme) {
            ret = false;
        }
        return ret;
    }
    
    /**
     *@return the progress value contained in this event
     */
    public float getProgress() {
        float ret = 0f;
        try {
            ret = this.getItemAsFloat(0);
        }
        catch (MalformedMessageException mme) {
            Debug.printStackTrace(mme, "programming5.io.ExecutionProgressEvent");
        }
        finally {
            return ret;
        }
    }
    
    /**
     *@return the total with respect to which the progress is measured (default = 1)
     */
    public int getReferenceTotal() {
        int total = 1;
        try {
            total = this.getItemAsInt(1);
        }
        catch (MalformedMessageException mme) {
            Debug.printStackTrace(mme, "programming5.io.ExecutionProgressEvent");
        }
        finally {
            return total;
        }
    }
    
    /**
     *@return a percentage or ratio as represented by the event values
     */
    public String getProgressString() {
        String currentProgress = this.getMessageItem(0);
        int total = 1;
        try {
            total = this.getItemAsInt(1);
        }
        catch (MalformedMessageException mme) {
            Debug.printStackTrace(mme, "programming5.io.ExecutionProgressEvent");
        }
        if (total != 1) {
            currentProgress = currentProgress + "/" + Integer.toString(total);
        }
        else {
            currentProgress = currentProgress + "%";
        }
        return currentProgress;
    }
    
}
