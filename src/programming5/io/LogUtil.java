/*
 * LogUtil.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for abbreviating calls to java.util.logging functions while keeping the caller info
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public abstract class LogUtil {

    /**
     * Calls the logger for the calling class with INFO level
     */
    public static void info(String message) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerClassName = trace[2].getClassName();
        String callerMethodName = trace[2].getMethodName();
        Logger.getLogger(callerClassName).logp(Level.INFO, callerClassName, callerMethodName, message);
    }

    /**
     * Calls the logger for the calling class with WARNING level
     */
    public static void warn(String message) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerClassName = trace[2].getClassName();
        String callerMethodName = trace[2].getMethodName();
        Logger.getLogger(callerClassName).logp(Level.WARNING, callerClassName, callerMethodName, message);
    }

    /**
     * Calls the logger for the calling class with SEVERE level
     */
    public static void logError(String message) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerClassName = trace[2].getClassName();
        String callerMethodName = trace[2].getMethodName();
        Logger.getLogger(callerClassName).logp(Level.SEVERE, callerClassName, callerMethodName, message);
    }

    /**
     * Calls the logger for the calling class with SEVERE level and re-throws the given exception
     */
    public static <E extends Exception> void throwException(E exception) throws E {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerClassName = trace[2].getClassName();
        String callerMethodName = trace[2].getMethodName();
        Logger.getLogger(callerClassName).logp(Level.SEVERE, callerClassName, callerMethodName, exception.getMessage(), exception);
        throw exception;
    }

    /**
     * Calls the logger for the calling class with SEVERE level and re-throws the given exception
     */
    public static <E extends Exception> void throwException(E exception, String message) throws E {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerClassName = trace[2].getClassName();
        String callerMethodName = trace[2].getMethodName();
        Logger.getLogger(callerClassName).logp(Level.SEVERE, callerClassName, callerMethodName, message, exception);
        throw exception;
    }

}
