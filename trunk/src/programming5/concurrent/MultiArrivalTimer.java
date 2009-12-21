/*
 * MultiArrivalTimer.java
 *
 * Copyright 2009 Andres Quiroz Hernandez
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

/**
 * A MultiArrivalTimer is used for waiting on a set of events expected to occur in a burst of unspecified
 * duration. A thread waiting on a MultiArrivalTimer will return when a sufficiently large amount of
 * time elapses after the last arrival, which is taken to mean that the last event from the burst has
 * been received. A different instance of the MultiArrivalTimer class should be used for waiting on
 * separate event bursts.
 * <p>There are two types of waiting conditions. The linear condition expects bursts where events have
 * linearly increasing interarrival times toward the end of the burst. The timer will measure this increase
 * in time and wait for a constant multiple of this time before signaling a waiting thread. The
 * exponential condition, on the other hand, expects bursts with geometrically increasing interarrival
 * times between the last events. Wait times between events increase exponentially if events with increasing
 * interarrival times are received.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class MultiArrivalTimer {

    protected ConditionVariable linearCondition = new ConditionVariable();
    protected ConditionVariable exponentialCondition = new ConditionVariable();

    private Thread linearWaitThread = null;
    private Thread exponentialWaitThread = null;

    private long interarrivalTime;
    private long lastArrivalTime = -1;
    private int exponentialMultiplier = 2;

    /**
     * Creates an instance of MultiArrivalTimer with an initial estimate or average interarrival time
     * @param expectedIATime the reference time used to calculate the wait time between arrivals
     */
    public MultiArrivalTimer(long expectedIATime) {
        interarrivalTime = expectedIATime;
    }

    /**
     * Starts a thread that measures the time between events (determined by calls to signalArrival) and 
     * signals waiting threads when enough time since the last arrival. A linear timer waits two times 
     * for twice the expected interarrival time before signaling waiting threads. If the interarrival 
     * time increases after the first wait period, a new interarrival time is calculated as an average 
     * with the current expected IA time.
     * <p>This method should only be called once per instance and excludes calls to startExponentialTimer
     * @see #signalArrival() 
     * @see #startExponentialTimer() 
     */
    public void startLinearTimer() {
        if (linearWaitThread == null) {
            linearWaitThread = new Thread() {
                public void run() {
                    boolean through = false;
                    while (!through) {
                        long toSleep = 2 * interarrivalTime;
                        while (toSleep > 0) {
                            try {
                                Thread.sleep(toSleep);
                                if (through && lastArrivalTime > 0) {
                                    toSleep = 0;
                                }
                                else {
                                    toSleep = 2 * interarrivalTime;
                                    through = (lastArrivalTime > 0);
                                }
                            }
                            catch (InterruptedException ie) {
                                if (lastArrivalTime > 0) {
                                    long newIAT = System.currentTimeMillis()-lastArrivalTime;
                                    if (newIAT > interarrivalTime) {
                                        interarrivalTime = (long) (0.5f*interarrivalTime + 0.5f*newIAT) + 1;
                                    }
                                }
                                lastArrivalTime = System.currentTimeMillis();
                                toSleep = 2 * interarrivalTime;
                                through = false;
                            }
                        }
                    }
                    linearCondition.signalAll();
                }
            };
            linearWaitThread.start();
        }
    }

    /**
     * Starts a thread that measures the time between events (determined by calls to signalArrival) and
     * signals waiting threads when enough time since the last arrival. An exponential timer waits two times
     * for an increasing multiple of the expected interarrival time before signaling waiting threads. If
     * the interarrival time increases after the first wait period, a new interarrival time is
     * calculated as an average with the current expected IA time.
     * <p>This method should only be called once per instance and excludes calls to startLinearTimer
     * @see #signalArrival()
     * @see #startLinearTimer()
     */
    public void startExponentialTimer() {
        if (exponentialWaitThread == null) {
            exponentialWaitThread = new Thread() {
                public void run() {
                    boolean through = false;
                    while (!through) {
                        long toSleep = exponentialMultiplier * interarrivalTime;
                        while (toSleep > 0) {
                            try {
                                Thread.sleep(toSleep);
                                if (through && lastArrivalTime > 0) {
                                    toSleep = 0;
                                }
                                else {
                                    toSleep = exponentialMultiplier * interarrivalTime;
                                    through = (lastArrivalTime > 0);
                                }
                            }
                            catch (InterruptedException ie) {
                                if (lastArrivalTime > 0) {
                                    long newIAT = System.currentTimeMillis()-lastArrivalTime;
                                    if (newIAT > interarrivalTime) {
                                        interarrivalTime = (long) (0.5f*interarrivalTime + 0.5f*newIAT) + 1;
                                    }
                                }
                                lastArrivalTime = System.currentTimeMillis();
                                if (through) {
                                    exponentialMultiplier++;
                                }
                                toSleep = exponentialMultiplier * interarrivalTime;
                                through = false;
                            }
                        }
                    }
                    exponentialCondition.signalAll();
                }
            };
            exponentialWaitThread.start();
        }
    }

    /**
     * Should be called for every event (arrival), after starting the appropriate timer thread
     */
    public void signalArrival() {
        if (linearWaitThread != null) {
            linearWaitThread.interrupt();
        }
        if (exponentialWaitThread != null) {
            exponentialWaitThread.interrupt();
        }
    }

    /**
     * Called by threads that want to block until the linear condition is reached (after a call to 
     * startLinearTimer.
     * @see #startLinearTimer() 
     */
    public void waitOnLinearCondition() {
        linearCondition.awaitUninterruptibly();
    }

    /**
     * Called by threads that want to block until the exponential condition is reached (after a call to
     * startExponentialTimer.
     * @see #startExponentialTimer()
     */
    public void waitOnExponentialCondition() {
        exponentialCondition.awaitUninterruptibly();
    }

    /**
     * @return the current average event interarrival time, based on the expected IA time and measured 
     * by subsequent calls to signalArrival.
     */
    public long getInterarrivalTime() {
        return interarrivalTime;
    }

}
