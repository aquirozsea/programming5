/*
 * RobustThread.java
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

/**
 * A robust thread is a thread that catches all uncaught exceptions from its runnable target and, by
 * default, restarts the target to avoid fatal crashes. Although the thread terminates if its target
 * run method exits without exceptions, this class is meant for long running or daemon threads. An
 * exception handler interface is provided in order to do exception handling at thread level and/or
 * perform necessary cleanup before either restarting or terminating the thread.
 * <p> The RobustThread run method is declared final, so a subclass of RobustThread cannot itself be
 * used as a runnable object, as with the Thread base class. Instead, when started, an instance of this
 * class always runs an implementation of the Runnable interface as a target. This is because the run
 * method of the class implements the exception handling and restart functionality.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class RobustThread extends Thread {

    protected Runnable target;
    protected ExceptionHandler exceptionHandler = null;
    private boolean running;

    /**
     * @param target the class that implements the run method that will be called when this thread is 
     * started
     */
    public RobustThread(Runnable target) {
        super();
        this.target = target;
    }

    /**
     * @param target the class that implements the run method that will be called when this thread is 
     * started
     * @param name the name of the thread
     */
    public RobustThread(Runnable target, String name) {
        super(name);
        this.target = target;
    }

    /**
     * @param group the thread's group, as defined for the Thread class
     * @param target the class that implements the run method that will be called when this thread is 
     * started
     */
    public RobustThread(ThreadGroup group, Runnable target) {
        super(group, "");
        this.target = target;
    }

    /**
     * @param group the thread's group, as defined for the Thread class
     * @param target the class that implements the run method that will be called when this thread is
     * started
     * @param name the name of the thread
     */
    public RobustThread(ThreadGroup group, Runnable target, String name) {
        super(group, name);
        this.target = target;
    }

    /**
     * @param target the class that implements the run method that will be called when this thread is 
     * started
     * @param myExceptionHandler the class that implements the handleException method that will be 
     * called if the given target throws an uncaught exception
     */
    public RobustThread(Runnable target, ExceptionHandler myExceptionHandler) {
        super();
        this.target = target;
        exceptionHandler = myExceptionHandler;
    }

    /**
     * @param target the class that implements the run method that will be called when this thread is
     * started
     * @param name the name of the thread
     * @param myExceptionHandler the class that implements the handleException method that will be
     * called if the given target throws an uncaught exception
     */
    public RobustThread(Runnable target, String name, ExceptionHandler myExceptionHandler) {
        super(name);
        this.target = target;
        exceptionHandler = myExceptionHandler;
    }

    /**
     * @param group the thread's group, as defined for the Thread class
     * @param target the class that implements the run method that will be called when this thread is
     * started
     * @param myExceptionHandler the class that implements the handleException method that will be
     * called if the given target throws an uncaught exception
     */
    public RobustThread(ThreadGroup group, Runnable target, ExceptionHandler myExceptionHandler) {
        super(group, "");
        this.target = target;
        exceptionHandler = myExceptionHandler;
    }

    /**
     * @param group the thread's group, as defined for the Thread class
     * @param target the class that implements the run method that will be called when this thread is
     * started
     * @param name the name of the thread
     * @param myExceptionHandler the class that implements the handleException method that will be
     * called if the given target throws an uncaught exception
     */
    public RobustThread(ThreadGroup group, Runnable target, String name, ExceptionHandler myExceptionHandler) {
        super(group, name);
        this.target = target;
        exceptionHandler = myExceptionHandler;
    }

    /**
     * Called when the thread is started, an in turn calls the target's run method
     */
    @Override
    public final void run() {
        running = true;
        while (running) {
            try {
                target.run();
                running = false;
            }
            catch (Exception e) {
                if (exceptionHandler != null) {
                    ExceptionHandler.ThreadAction action = exceptionHandler.handleException(e);
                    if (action.equals(ExceptionHandler.ThreadAction.TERMINATE)) {
                        running = false;
                    }
                    else {
                        Debug.println("RobustThread: Restarting thread " + this.getName(), "programming5.io.RobustThread");
                    }
                }
                else {
                    Debug.println("Exception in thread " + this.getName() + ": " + e.toString(), "programming5.io.RobustThread");
                    Debug.println("RobustThread: Restarting thread " + this.getName(), "programming5.io.RobustThread");
                }
            }
        }
    }

}
