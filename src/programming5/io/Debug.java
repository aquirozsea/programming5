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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class simply provides a static wrapper for conditionally printing to System.out for debugging purposes.
 * (This class is incompatible with the Debug class up until release 18)
 * <p>Release 93: Added functionality for hierarchical debug sets, so that groups of debug statements can be enabled using class
 * hierarchy names. The semantics of the default print methods (those which do not specify a set name) changes from
 * previous releases in that they are assigned to a debug set equal to a concatenation of the class and method
 * name from which the debug print is called (auto-detected). These statements can still be enabled with
 * enableDefault, so programs that relied on the previous semantics may remain the same. The convenience of the
 * new semantics is that set names do not have to be assigned unless the class hierarchy grouping is not desirable
 * or sufficient, and that enabling of debug prints can be managed at the package, class or method level by enabling
 * the appropriate set prefix.
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public abstract class Debug {

    protected static Set<String> activeSet = new HashSet<String>();
    protected static Set<String> disabledSet = new HashSet<String>();
    protected static boolean autodetectPath = true;

    /**
     * Identifies the default debug set
     */
    public static final String DEFAULT_SET = "Default";

    private static final int DISABLED = 0;
    private static final int ENABLED = 1;
    private static final String ALT_PATH = "#";

    static {
        try {
            String debugClasses = ConfigHandler.getProperty("debug");
            try {
                String[] classNames = debugClasses.split(";");
                for (String className : classNames) {
                    Debug.enable(Class.forName(className));
                }
            }
            catch (Exception e) {
                LogUtil.warn("Problem enabling debug classes: " + e.getMessage());
            }
        }
        catch (IllegalArgumentException iae) {
            // debug property not set, no classes to pre-enable
        }
    }

    /**
     * Enables debug printing for debug statements identified with the given name or derived names
     * @param setName the identifier of the debug set
     */
    public static void enable(String setName) {
        if (!isEnabled(setName)) {
            activeSet.add(setName);
        }
        List<String> toRemove = new ArrayList<String>();
        for (String disabled : disabledSet) {
            if (disabled.startsWith(setName) || setName.startsWith(disabled)) {
                toRemove.add(disabled);
            }
        }
        disabledSet.removeAll(toRemove);
    }

    /**
     * Enables debug printing for debug statements in the given class, unless they explicitly identify a different
     * debug set.
     * @param classObject the class object
     */
    public static void enable(Class classObject) {
        enable(classObject.getName());
    }

    /**
     * Enables printing of all debug statements that do not explicitly identify a debug set.
     */
    public static void enableDefault() {
        autodetectPath = false;
    }

    /**
     * @param setName the identifier of the debug set
     * @return whether printing is enabled for the given debug set, either explicitly or for some prefix
     */
    public static boolean isEnabled(String setName) {
        if (!activeSet.isEmpty()) {
            String[] levels = setName.split("\\.");
            String prefix = levels[0];
            boolean found = activeSet.contains(prefix);
            int i = 1;
            while (!found && i < levels.length) {
                prefix += "." + levels[i++];
                found = activeSet.contains(prefix);
            }
            boolean disabled = false;
            if (found && i < levels.length) {
                do {
                    prefix += "." + levels[i++];
                    disabled = disabledSet.contains(prefix);
                }
                while (!disabled && i < levels.length);
            }
            return (found && !disabled);
        }
        else {
            return false;
        }
    }

    /**
     * @param classObject the class object
     * @return whether printing is enabled for the given class, either explicitly or for its package hierarchy
     */
    public static boolean isEnabled(Class classObject) {
        return isEnabled(classObject.getName());
    }
    
    /**
     * @return whether printing is enabled for the calling method, class, or package; also returns true is default
     * printing is enabled
     */
    public static boolean isEnabled() {
        boolean isEnabled = true;
        if (autodetectPath) {
            isEnabled = isEnabled(detectCallPath());
        }
        return isEnabled;
    }

    /**
     * Disables debug printing for the given debug set and all derived sets
     * @param setName the identifier of the debug set
     */
    public static void disable(String setName) {
        String[] levels = setName.split("\\.");
        String prefix = levels[0];
        boolean found = activeSet.contains(prefix);
        int i = 1;
        while (!found && i < levels.length) {
            prefix += "." + levels[i++];
            found = activeSet.contains(prefix);
        }
        if (found) {
            if (i < levels.length) {
                disabledSet.add(setName);
            }
            else {
                activeSet.remove(setName);
            }
        }
        else {
            List<String> toRemove = new ArrayList<String>();
            for (String active : activeSet) {
                if (active.startsWith(setName)) {
                    toRemove.add(active);
                }
            }
            activeSet.removeAll(toRemove);
        }
    }

    /**
     * Disables debug printing for the given class
     * @param classObject the class object
     */
    public static void disable(Class classObject) {
        disable(classObject.getName());
    }

    /**
     * Disables printing by default. Explicitly and hierarchically enabled statements will still print.
     */
    public static void disableDefault() {
        autodetectPath = true;
    }

    /**
     * Disables printing for all currently enabled debug sets
     */
    public static void disableAll() {
        autodetectPath = true;
        activeSet.clear();
        disabledSet.clear();
    }

    /**
     * Sets the state of the given debug set and derived sets to the state given by the indicator
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
     * Sets the state of the given class to the state given by the indicator
     * @param classObject the class object
     * @param enable true to enable the given class, and false to disable it
     */
    public static void setEnabled(Class classObject, boolean enable) {
        if (enable) {
            enable(classObject);
        }
        else {
            disable(classObject);
        }
    }

    /**
     * Enables or disables default printing according to the indicator
     * @param enable true to enable default printing, and false to disable it
     */
    public static void setEnabledDefault(boolean enable) {
        autodetectPath = enable;
    }

    /**
     * Changes the current behavior of the debug object for the given debug set and derived sets
     * @param setName the identifier of the debug set
     * @return true if the set is now enabled; false if it was previously enabled
     */
    public static boolean toggleActive(String setName) {
        boolean ret;
        if (isEnabled(setName)) {
            disable(setName);
            ret = false;
        }
        else {
            enable(setName);
            ret = true;
        }
        return ret;
    }

    /**
     * Changes the current behavior of the debug object for the given class
     * @param classObject the class object
     * @return true if the class is now enabled; false if it was previously enabled
     */
    public static boolean toggleActive(Class classObject) {
        boolean ret;
        if (isEnabled(classObject.getName())) {
            disable(classObject.getName());
            ret = false;
        }
        else {
            enable(classObject.getName());
            ret = true;
        }
        return ret;
    }

    /**
     * Enables or disables default printing depending on its current state
     * @return true if the default printing is now enabled; false if it was previously enabled
     */
    public static boolean toggleDefault() {
        autodetectPath = !autodetectPath;
        return !autodetectPath;
    }

    /**
     * Prints the given message if the given set or prefix is enabled
     * @param setName the identifier of the debug set
     * @param message the message to print (uses System.out.print())
     */
    public static void print(Object message, String setName) {
        if (isEnabled(setName)) {
            System.out.print(message);
        }
    }

    /**
     * Prints the given message if the encapsulating method, class, or package names have been enabled, either
     * explicitly or with default printing.
     * @param message the message to print (uses System.out.print())
     */
    public static void print(Object message) {
        if (isEnabled()) {
            System.out.print(message);
        }
    }

    /**
     * Prints the given message if the encapsulating method, class, or package names have been enabled, either
     * explicitly or with default printing.
     * @param message the message to print (uses System.out.println())
     */
    public static void println(Object message) {
        if (isEnabled()) {
            System.out.println(message);
        }
    }

    /**
     * Prints the given message if the given set or prefix is enabled
     * @param message the message to print (uses System.out.println())
     * @param setName the identifier of the debug set
     */
    public static void println(Object message, String setName) {
        if (isEnabled(setName)) {
            System.out.println(message);
        }
    }

    /**
     * Prints the stack trace of the given exception if the encapsulating method, class, or package names have been
     * enabled, either explicitly or with default printing.
     * @param ex the exception on which printStackTrace is called
     */
    public static void printStackTrace(Exception ex) {
        if (isEnabled()) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints the stack trace of the given exception if the given set or prefix is active
     * @param ex the exception on which printStackTrace is called
     * @param setName the identifier of the debug set
     */
    public static void printStackTrace(Exception ex, String setName) {
        if (isEnabled(setName)) {
            ex.printStackTrace();
        }
    }

    private static String detectCallPath() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int i = 0;
        while (trace[i].getClassName().equals(Debug.class.getName()) || trace[i].getClassName().equals(Thread.class.getName())) {
            i++;
        }
        return trace[i].getClassName() + "." + trace[i].getMethodName();
    }

}
