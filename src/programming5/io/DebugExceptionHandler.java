/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.io;

/**
 *
 * @author andresqh
 */
public class DebugExceptionHandler implements ExceptionHandler {

    public ThreadAction handleException(Exception e) {
        Debug.printStackTrace(e);
        return ThreadAction.TERMINATE;
    }

}
