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
    long waitTime = 0;
    
    public ConditionVariable() {
    }

    /**
     *Waits on this condition until signaled or interrupted
     */
    public synchronized void await() throws InterruptedException {
        waitTime = 0;
        if (!condition) {
            long startTime = System.currentTimeMillis();
            wait();
            waitTime = System.currentTimeMillis() - startTime;
        }
    }

    /**
     *Waits on the conditioned until signaled, even if interrupted
     */
    public synchronized void awaitUninterruptibly() {
        waitTime = 0;
        boolean waiting = !condition;
        while (waiting) {
            long startTime = System.currentTimeMillis();
            try {
                wait();
            }
            catch (InterruptedException ie) {
            }
            finally {
                waitTime += (System.currentTimeMillis() - startTime);
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
        waitTime = 0;
        if (!condition) {
            wait(timeoutTime);
            waitTime = System.currentTimeMillis() - time;
        }
        return 1000000 * (timeoutTime - System.currentTimeMillis() + time);
    }

    /**
     *Waits on the condition until signaled or a timeout of the given length in the given time unit occurs
     *@return true if the condition is true and false otherwise
     */
    public synchronized boolean await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        waitTime = 0;
        if (!condition) {
            long startTime = System.currentTimeMillis();
            wait(toMilliseconds(timeout, timeUnit));
            waitTime = System.currentTimeMillis() - startTime;
        }
        return condition;
    }

    /**
     *Waits on the condition until signaled or a timeout of the given length in the given time unit occurs.
     *If interrupted, will resume waiting for the remaining amount of time, or unitl signaled.
     *@return true if the condition is true and false otherwise
     */
    public synchronized boolean awaitUninterruptibly(long timeout, TimeUnit timeUnit) {
        long timeoutMillis = toMilliseconds(timeout, timeUnit);
        boolean waiting = !condition;
        waitTime = 0;
        while (waiting) {
            long startTime = System.currentTimeMillis();
            try {
                wait(timeoutMillis);
                waitTime += (System.currentTimeMillis() - startTime);
                waiting = false;
            }
            catch (InterruptedException ie) {
                long stopTime = System.currentTimeMillis();
                timeoutMillis -= (stopTime - startTime);
                if (timeoutMillis <= 0) {
                    waiting = false;
                }
            }
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
        waitTime = 0;
        if (!condition) {
            wait(timeoutTime);
            waitTime = System.currentTimeMillis() - time;
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

    /**
     * @return the time in milliseconds spent waiting during the last await method call
     */
    public long getTimeWaited() {
        return waitTime;
    }

    private long toMilliseconds(long time, TimeUnit timeUnit) {
        long ret;
        switch (timeUnit) {
            case MICROSECONDS: ret = time / 1000;
                break;
            case MILLISECONDS: ret = time;
                break;
            case NANOSECONDS: ret = time / 1000000;
                break;
            case SECONDS: ret = time * 1000;
                break;
            case MINUTES: ret = time * 60000;
                break;
            case HOURS: ret = time * 3600000;
                break;
            case DAYS: ret = time * 86400000;
                break;
            default: ret = 0;
        }
        return ret;
    }
    
}
