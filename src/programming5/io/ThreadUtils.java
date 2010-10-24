/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.io;

/**
 *
 * @author aquirozh
 */
public abstract class ThreadUtils {

    public static void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        }
        catch (InterruptedException ie) {}
    }

}
