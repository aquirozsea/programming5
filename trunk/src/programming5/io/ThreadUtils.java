/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.io;

/**
 * Abstract utility class for threads
 * @author aquirozh
 */
public abstract class ThreadUtils {

    /**
     * Wrapper method for the Thread.sleep method, to avoid code clutter from the Interrupted exception, which 
     * will be ignored.
     * @param timeMillis the length of time for the thread to sleep
     */
    public static void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        }
        catch (InterruptedException ie) {}
    }

    /**
     * Wrapper method for the Thread.sleep method, to avoid code clutter from the Interrupted exception, which
     * will be handled by the given exception handler.
     * @param timeMillis the length of time for the thread to sleep
     */
    public static void sleep(long timeMillis, ExceptionHandler exceptionHandler) {
        try {
            Thread.sleep(timeMillis);
        }
        catch (InterruptedException ie) {
            exceptionHandler.handleException(ie);
        }
    }

}
