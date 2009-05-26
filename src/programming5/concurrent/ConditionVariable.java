/*
 * ConditionVariable.java
 *
 * Copyright 2007 Andres Quiroz Hernandez
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

package programming5.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * Implementation of the java.util.concurrent.locks.Condition interface, with additional functionality to test 
 * the condition to which the object is associated.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class ConditionVariable implements Condition {
    
    boolean condition = false;
    
    public ConditionVariable() {
    }

    /**
     *Waits on this condition until signaled or interrupted
     */
    public synchronized void await() throws InterruptedException {
        if (!condition) {
            wait();
        }
    }

    /**
     *Waits on the conditioned until signaled, even if interrupted
     */
    public synchronized void awaitUninterruptibly() {
        boolean waiting = !condition;
        while (waiting) {
            try {
                wait();
            }
            catch (InterruptedException ie) {
            }
            finally {
                waiting = !condition;
            }
        }
    }

    /**
     *Waits on the condition until signaled or a timeout at the given time occurs
     *@return the time remaining until the given timeout
     */
    public synchronized long awaitNanos(long timeout) throws InterruptedException {
        long timeoutTime = timeout/1000000;
        long time = System.currentTimeMillis();
        if (!condition) {
            wait(timeoutTime);
        }
        return 1000000 * (timeoutTime - System.currentTimeMillis() + time);
    }

    /**
     *Waits on the condition until signaled or a timeout of the given length in the given time unit occurs
     *@return true if the condition is true and false otherwise
     */
    public synchronized boolean await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        long timeoutTime;
        switch (timeUnit) {
            case MICROSECONDS: timeoutTime = timeout/1000;
                break;
            case MILLISECONDS: timeoutTime = timeout;
                break;
            case NANOSECONDS: timeoutTime = timeout/1000000;
                break;
            case SECONDS: timeoutTime = timeout*1000;
                break;
            default: timeoutTime = 0;
        }
        long time = System.currentTimeMillis();
        if (!condition) {
            wait(timeoutTime);
        }
        return condition;
    }

    /**
     *Waits on the condition until signaled or a timeout at the given date occurs
     *@return true if the condition is true and false otherwise
     */
    public synchronized boolean awaitUntil(Date date) throws InterruptedException {
        long time = System.currentTimeMillis();
        long timeoutTime = date.getTime() - time;
        if (!condition) {
            wait(timeoutTime);
        }
        return condition;
    }

    /**
     *Signals an object waiting on this condition, setting the condition to true
     */
    public synchronized void signal() {
        condition = true;
        notify();
    }

    /**
     *Signals all objects waiting on this condition, setting the condition to true
     */
    public synchronized void signalAll() {
        condition = true;
        notifyAll();
    }
    
    /**
     * A conditional call to signalAll that depends on the value of the given expression (will signal if the expression 
     * evaluates to true.
     */
    public synchronized void evaluateCondition(boolean expression) {
        condition = expression;
        if (condition) {
            notifyAll();
        }
    }
    
    /**
     *@return the value of the condition associated with this variable
     */
    public boolean isTrue() {
        return condition;
    }
    
    /**
     * Sets the condition associated with this variable to false
     */
    public void reset() {
        if (condition) {
            condition = false;
        }
    }
    
}
