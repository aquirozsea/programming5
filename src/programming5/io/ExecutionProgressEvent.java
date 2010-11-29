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

    public static enum ExecutionAction {START, PROGRESS, COMPLETE};

    /** 
     * Creates a new instance of ExecutionProgressEvent with the current progress percentage and a total
     * value (when other that 100pct is used)
     */
    public ExecutionProgressEvent(String operation, ExecutionAction action, float executionParameter) {
        super(TYPE);
        this.addMessageItem(operation);
        this.addMessageItem(action.toString());
        this.addMessageItem(executionParameter);
    }
    
    /** 
     * Creates a new instance of ExecutionProgressEvent with the current progress percentage
     */
    public ExecutionProgressEvent(String operation, ExecutionAction action) {
        super(TYPE);
        this.addMessageItem(operation);
        this.addMessageItem(action.toString());
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
     * syntax
     */
    public ExecutionProgressEvent(byte[] eventBytes) throws MalformedMessageException {
        super(eventBytes);
        if (!checkFormat()) {
            throw new MalformedMessageException("ExecutionProgressEvent: Event of incorrect type or payload format");
        }
    }

    @Override
    public boolean assertFormat() {
        return checkFormat();
    }
    
    private boolean checkFormat() {
        boolean ret = this.getType().equals(TYPE) && (this.getMessageSize() == 2 || this.getMessageSize() == 3);
        if (ret) {
            try {
                ExecutionAction.valueOf(this.getMessageItem(1));
            }
            catch (Exception mme) {
                ret = false;
            }
        }
        return ret;
    }

    public String getOperation() {
        return this.getMessageItem(0);
    }

    public boolean isExecutionStartEvent() {
        return ExecutionAction.valueOf(this.getMessageItem(1)).equals(ExecutionAction.START);
    }

    public boolean isExecutionProgressReport() {
        return ExecutionAction.valueOf(this.getMessageItem(1)).equals(ExecutionAction.PROGRESS);
    }

    public boolean isExecutionCompleteEvent() {
        return ExecutionAction.valueOf(this.getMessageItem(1)).equals(ExecutionAction.COMPLETE);
    }

    public ExecutionAction getActionType() {
        return ExecutionAction.valueOf(this.getMessageItem(1));
    }

    public boolean hasExecutionParameter() {
        return (this.getMessageSize() == 3);
    }

    public float getExecutionParameter() {
        try {
            return this.getItemAsFloat(2);
        }
        catch (MalformedMessageException mme) {
            throw new UnsupportedOperationException("ExecutionProgressEvent: getExecutionParameter not supported for this event type (" + this.getMessageItem(1) + ")");
        }
    }
    
    /**
     *@return the progress value contained in this event
     */
    public float getProgress() {
        return getExecutionParameter();
    }
    
    /**
     *@return the total with respect to which the progress is measured (default = 1)
     */
    public float getReferenceTotal() {
        return getExecutionParameter();
    }
    
    /**
     *@return a percentage or ratio as represented by the event values
     */
    public String getProgressString() {
        ExecutionAction action = this.getActionType();
        switch (action) {
            case START: return this.getOperation() + " started";
            case PROGRESS: return this.getOperation() + " executing " + Float.toString(this.getProgress()) + "%";
            case COMPLETE: return this.getOperation() + " completed";
            default: return "Undefined";
        }
    }
    
}
