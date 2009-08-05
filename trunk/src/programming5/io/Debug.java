/*
 * Debug.java
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

import java.util.HashSet;
import java.util.Set;

/**
 * This class simply provides a static wrapper for conditionally printing to System.out for debugging purposes.
 * (This class is incompatible with the Debug class up until release 18)
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public abstract class Debug {

    private static Set<String> activeSet = new HashSet<String>();

    /**
     * Identifies the default debug set
     */
    public static final String DEFAULT_SET = "Default";

    /**
     * Enables debug printing for debug statements identified with the given name
     * @param setName the identifier of the debug set
     */
    public static void enable(String setName) {
        activeSet.add(setName);
    }

    /**
     * Enables debug printing for debug statements using the default print methods
     */
    public static void enableDefault() {
        activeSet.add(DEFAULT_SET);
    }

    /**
     * @param setName the identifier of the debug set
     * @return whether printing is enabled for the given debug set
     */
    public static boolean isEnabled(String setName) {
        return activeSet.contains(setName);
    }
    
    /**
     * @return whether printing is enabled for the default debug set
     */
    public static boolean isEnabled() {
        return isEnabled(DEFAULT_SET);
    }

    /**
     * Disables debug printing for the given debug set
     * @param setName the identifier of the debug set
     */
    public static void disable(String setName) {
        activeSet.remove(setName);
    }

    /**
     * Disables printing for the default debug set
     */
    public static void disableDefault() {
        activeSet.remove(DEFAULT_SET);
    }

    /**
     * Disables printing for all currently enabled debug sets
     */
    public static void disableAll() {
        activeSet.clear();
    }

    /**
     * Sets the state of the given debug set to the state given by the indicator
     * @param setName the identifier of the debug set
     * @param enable true to enable the given set, and false to disable it
     */
    public static void setEnabled(String setName, boolean enable) {
        if (enable) {
            enable(setName);
        }
        else {
            disable(setName);
        }
    }

    /**
     * Sets the state of the default debug set to the state given by the indicator
     * @param enable true to enable the given set, and false to disable it
     */
    public static void setEnabledDefault(boolean enable) {
        setEnabled(DEFAULT_SET, enable);
    }

    /**
     * Changes the current behavior of the debug object for the given debug set
     * @param setName the identifier of the debug set
     * @return true if the set is now enabled; false if it was previously enabled
     */
    public static boolean toggleActive(String setName) {
        boolean ret;
        if (activeSet.contains(setName)) {
            activeSet.remove(setName);
            ret = false;
        }
        else {
            activeSet.add(setName);
            ret = true;
        }
        return ret;
    }

    /**
     * Changes the current behavior of the debug object for the default debug set
     * @return true if the set is now enabled; false if it was previously enabled
     */
    public static boolean toggleActive() {
        return toggleActive(DEFAULT_SET);
    }

    /**
     * Prints the given message if the given set is enabled
     * @param setName the identifier of the debug set
     * @param message the message to print (uses System.out.print())
     */
    public static void print(Object message, String setName) {
        if (isEnabled(setName)) {
            System.out.print(message);
        }
    }

    /**
     * Prints the given message if the default set is enabled
     * @param message the message to print (uses System.out.print())
     */
    public static void print(Object message) {
        if (isEnabled()) {
            System.out.print(message);
        }
    }

    /**
     * Prints the given message if the default set is enabled
     * @param message the message to print (uses System.out.println())
     */
    public static void println(Object message) {
        if (isEnabled()) {
            System.out.println(message);
        }
    }

    /**
     * Prints the given message if the given set is enabled
     * @param message the message to print (uses System.out.println())
     * @param setName the identifier of the debug set
     */
    public static void println(Object message, String setName) {
        if (isEnabled(setName)) {
            System.out.println(message);
        }
    }

    /**
     * Prints the stack trace of the given exception if the default set is active
     * @param ex the exception on which printStackTrace is called
     */
    public static void printStackTrace(Exception ex) {
        if (isEnabled()) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints the stack trace of the given exception if the given set is active
     * @param ex the exception on which printStackTrace is called
     * @param setName the identifier of the debug set
     */
    public static void printStackTrace(Exception ex, String setName) {
        if (isEnabled(setName)) {
            ex.printStackTrace();
        }
    }

}
