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
 * Event that represents the progress percentage of a given operation
 * @author Andres Quiroz Hernandez
 * @see programming5.net.Event
 */
public class ExecutionProgressEvent extends programming5.net.Event {
    
    public static final String TYPE = "EPE";
    
    protected float progress;
    protected int total;
    
    /** 
     * Creates a new instance of ExecutionProgressEvent with the current progress percentage and a total value (when other that 100pct is used)
     */
    public ExecutionProgressEvent(float currentProgress, int referenceTotal) {
        super(TYPE);
        progress = currentProgress;
        this.message.addMessageItem(progress);
        total = referenceTotal;
        this.message.addMessageItem(total);
    }
    
    /** 
     * Creates a new instance of ExecutionProgressEvent with the current progress percentage
     */
    public ExecutionProgressEvent(float currentProgress) {
        super(TYPE);
        progress = currentProgress;
        this.message.addMessageItem(progress);
        total = 1;
        this.message.addMessageItem(total);
    }
    
    /** 
     * Creates a new instance of ExecutionProgressEvent from a given message object
     *@see programming5.net.Message
     */
    public ExecutionProgressEvent(Message evtMsg) {
        super(evtMsg);
        try {
            progress = this.message.getItemAsFloat(0);
            total = this.message.getItemAsInt(1);
        }
        catch (MalformedMessageException mme) {
            throw new IllegalArgumentException("ExecutionProgressEvent: Bad event message: " + mme.getMessage());
        }
    }
    
    /**
     *@return the progress value contained in this event
     */
    public float getProgress() {
        return progress;
    }
    
    /**
     *@return the total with respect to which the progress is measured (default = 1)
     */
    public int getReferenceTotal() {
        return total;
    }
    
    /**
     *@return a percentage or ratio as represented by the event values
     */
    public String getProgressString() {
        String ret = this.message.getMessageItem(0);
        if (total != 1) {
            ret = ret + "/" + this.message.getMessageItem(1);
        }
        else {
            ret = ret + "%";
        }
        return ret;
    }
    
}
