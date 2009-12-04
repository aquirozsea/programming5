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
 *
 * @author Andres Quiroz
 */
public class MultiArrivalTimer {

//    protected Vector<Thread> waitingThreadsLinear = new Vector<Thread>();
//    protected Vector<Thread> waitingThreadsExponential = new Vector<Thread>();
    protected ConditionVariable linearCondition = new ConditionVariable();
    protected ConditionVariable exponentialCondition = new ConditionVariable();

    private Thread linearWaitThread = null;
    private Thread exponentialWaitThread = null;

    private long interarrivalTime;
    private long lastArrivalTime = -1;
    private int exponentialMultiplier = 2;

    public MultiArrivalTimer(long expectedIATime) {
        interarrivalTime = expectedIATime;
    }

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

    public void signalArrival() {
        if (linearWaitThread != null) {
            linearWaitThread.interrupt();
        }
        if (exponentialWaitThread != null) {
            exponentialWaitThread.interrupt();
        }
    }

    public void waitOnLinearCondition() {
        linearCondition.awaitUninterruptibly();
    }

    public void waitOnExponentialCondition() {
        exponentialCondition.awaitUninterruptibly();
    }

    public long getInterarrivalTime() {
        return interarrivalTime;
    }

}
