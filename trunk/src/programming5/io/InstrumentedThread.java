/*
 * InstrumentedThread.java
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

package programming5.io;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * An instrumented thread is a derived thread object that provides methods to keep timing information during
 * the thread's runtime and to show the timing information, either individually or for all instrumented
 * threads.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class InstrumentedThread extends Thread {

    static final Vector<InstrumentedThread> threadList = new Vector<InstrumentedThread>();
    static final Hashtable<String, Vector<InstrumentedThread>> groupThreadLists = new Hashtable<String, Vector<InstrumentedThread>>();

    Hashtable<String, Long> callLog = new Hashtable<String, Long>();
    Runnable runnableClass = null;

    /**
     * Creates a new instrumented thread and adds it to a list of monitored threads
     * @param name the thread name (with which the corresponding timings can be referenced)
     */
    public InstrumentedThread(String name) {
        super(name);
        threadList.add(this);
    }

    /**
     * Creates a new instrumented thread with the given runnable target and adds it to a list of monitored
     * threads
     * @param target the runnable class that will be executed when this thread is started
     * @param name the thread name (with which the corresponding timings can be referenced)
     */
    public InstrumentedThread(Runnable target, String name) {
        super(name);
        runnableClass = target;
        threadList.add(this);
    }

    /**
     * Creates a new instrumented thread and adds it to a list of monitored threads of a particular group
     * @param group the group under which this thread will be created
     * @param name the thread name (with which the corresponding timings can be referenced)
     */
    public InstrumentedThread(ThreadGroup group, String name) {
        super(group, name);
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(group.getName());
        if (groupThreadList == null) {
            groupThreadList = new Vector<InstrumentedThread>();
            groupThreadLists.put(group.getName(), groupThreadList);
        }
        groupThreadList.add(this);
    }

    /**
     * Creates a new instrumented thread with the given runnable target and adds it to a list of monitored
     * threads of a particular group
     * @param group the group under which this thread will be created
     * @param target the runnable class that will be executed when this thread is started
     * @param name the thread name (with which the corresponding timings can be referenced)
     */
    public InstrumentedThread(ThreadGroup group, Runnable target, String name) {
        super(group, name);
        runnableClass = target;
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(group.getName());
        if (groupThreadList == null) {
            groupThreadList = new Vector<InstrumentedThread>();
            groupThreadLists.put(group.getName(), groupThreadList);
        }
        groupThreadList.add(this);
    }

    @Override
    /**
     * Runs the target, if any. Should be overridden by a derived class
     */
    public void run() {
        if (runnableClass != null) {
            runnableClass.run();
        }
    }

    /**
     * Prints the execution log of the referenced thread at the current moment of execution. Not recommended 
     * to use directly.
     */
    public void printLog() {
        System.out.println("Thread " + this.getName() + ": ");
        System.out.println(callLog);
    }

    /**
     * Starts the timing for the given method on the current thread. If called without a corresponding call to 
     * endInvocation, the information recorded will be the absolute start time of the method or block.
     * @param methodName the reference for the block to be measured. May be an actual method or reference to 
     * a block of code
     */
    public static void startInvocation(String methodName) {
        ((InstrumentedThread) Thread.currentThread()).callLog.put(methodName, System.currentTimeMillis());
    }

    /**
     * Completes and records the duration of the given method or code block on the current thread. Has no 
     * effect if called without a corresponding call to startInvocation on the current thread.
     * @param methodName the reference for the block to be measured; must correspond to the method name given 
     * for the corresponding call to startInvocation
     */
    public static void endInvocation(String methodName) {
        long endTime = System.currentTimeMillis();
        Long startTime = ((InstrumentedThread) Thread.currentThread()).callLog.get(methodName);
        if (startTime != null) {
            ((InstrumentedThread) Thread.currentThread()).callLog.put(methodName, endTime - startTime);
        }
    }
    
    /**
     * Waits until the thread with the given name exits and then prints the timing information for the methods 
     * recorded to System.out. Does nothing if the thread does not exist.
     * @param threadName the name of the thread to print
     */
    public static void printLog(String threadName) {
        for (InstrumentedThread t : threadList) {
            if (t.getName().equals(threadName)) {
                try {
                    t.join();
                }
                catch (InterruptedException ie) {}
                t.printLog();
            }
        }
    }

    /**
     * Waits until the thread in the given group with the given name exits and then prints the timing
     * information for the methods recorded to System.out. Does nothing if the thread does not exist.
     * @param group the group under which the thread name will be sought
     * @param threadName the name of the thread to print
     */
    public static void printLog(ThreadGroup group, String threadName) {
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(group.getName());
        if (groupThreadList != null) {
            for (InstrumentedThread t : groupThreadList) {
                if (t.getName().equals(threadName)) {
                    try {
                        t.join();
                    }
                    catch (InterruptedException ie) {}
                    t.printLog();
                }
            }
        }
    }

    /**
     * Waits until the thread in the given group with the given name exits and then prints the timing
     * information for the methods recorded to System.out. Does nothing if the thread does not exist.
     * @param groupName the name of the group under which the thread name will be sought
     * @param threadName the name of the thread to print
     */
    public static void printLog(String groupName, String threadName) {
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(groupName);
        if (groupThreadList != null) {
            for (InstrumentedThread t : groupThreadList) {
                if (t.getName().equals(threadName)) {
                    try {
                        t.join();
                    }
                    catch (InterruptedException ie) {}
                    t.printLog();
                }
            }
        }
    }

    /**
     * Waits until all ungrouped instrumented threads exit and then prints the timing
     * information for their methods recorded to System.out. Does nothing if no threads were started.
     */
    public static void printLogs() {
        for (InstrumentedThread t : threadList) {
            try {
                t.join();
            }
            catch (InterruptedException ie) {
            }
            t.printLog();
        }
    }

    /**
     * Waits until all instrumented threads from the given group exit and then prints the timing
     * information for their methods recorded to System.out. Does nothing if no threads were started.
     * @param group the group for which the thread logs will be printed
     */
    public static void printLogs(ThreadGroup group) {
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(group.getName());
        if (groupThreadList != null) {
            for (InstrumentedThread t : groupThreadList) {
                try {
                    t.join();
                }
                catch (InterruptedException ie) {}
                t.printLog();
            }
        }
    }

    /**
     * Waits until all instrumented threads from the given group exit and then prints the timing
     * information for their methods recorded to System.out. Does nothing if no threads were started.
     * @param groupName the name of the group for which the thread logs will be printed
     */
    public static void printLogs(String groupName) {
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(groupName);
        if (groupThreadList != null) {
            for (InstrumentedThread t : groupThreadList) {
                try {
                    t.join();
                }
                catch (InterruptedException ie) {}
                t.printLog();
            }
        }
    }

    /**
     * Waits until all ungrouped instrumented threads exit and then saves the timing
     * information for their methods recorded to a file with the given name. If the file does not exist, it
     * will be created, and if it does, it will be overwritten.
     * @param filename the name of the file
     */
    public static void saveLogs(String filename) throws IOException {
        FileHandler logFile = new FileHandler(filename, FileHandler.HandleMode.OVERWRITE);
        for (InstrumentedThread t : threadList) {
            try {
                t.join();
            }
            catch (InterruptedException ie) {
            }
            logFile.writeln("Thread " + t.getName() + ": ");
            logFile.writeln(t.callLog.toString());
        }
        logFile.close();
    }

    /**
     * Waits until all instrumented threads of the given group exit and then saves the timing
     * information for their methods recorded to a file with the given name. If the file does not exist, it
     * will be created, and if it does, it will be overwritten.
     * @param filename the name of the file
     * @param group the group for which the logs will be saved
     */
    public static void saveLogs(String filename, ThreadGroup group) throws IOException {
        FileHandler logFile = new FileHandler(filename, FileHandler.HandleMode.OVERWRITE);
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(group.getName());
        if (groupThreadList != null) {
            for (InstrumentedThread t : groupThreadList) {
                try {
                    t.join();
                }
                catch (InterruptedException ie) {
                }
                logFile.writeln("Thread " + t.getName() + ": ");
                logFile.writeln(t.callLog.toString());
            }
        }
        logFile.close();
    }

    /**
     * Waits until all instrumented threads of the given group exit and then saves the timing
     * information for their methods recorded to a file with the given name. If the file does not exist, it
     * will be created, and if it does, it will be overwritten.
     * @param filename the name of the file
     * @param groupName the name of the group for which the logs will be saved
     */
    public static void saveLogs(String filename, String groupName) throws IOException {
        FileHandler logFile = new FileHandler(filename, FileHandler.HandleMode.OVERWRITE);
        Vector<InstrumentedThread> groupThreadList = groupThreadLists.get(groupName);
        if (groupThreadList != null) {
            for (InstrumentedThread t : groupThreadList) {
                try {
                    t.join();
                }
                catch (InterruptedException ie) {
                }
                logFile.writeln("Thread " + t.getName() + ": ");
                logFile.writeln(t.callLog.toString());
            }
        }
        logFile.close();
    }

}
