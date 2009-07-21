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

/**
 * This class simply provides a wrapper for conditionally printing to System.out for debugging purposes
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class Debug {

    boolean debugging;

    /**
     * @param active Indicates if debug printing is active (true) or not (false)
     */
    public Debug(boolean active) {
        debugging = active;
    }

    /**
     * @param active Indicates if debug printing should be set to active (true) or not (false)
     */
    public void setActive(boolean active) {
        debugging = active;
    }

    public boolean isActive() {
        return debugging;
    }

    /**
     * Changes the current behavior of the debug object
     * @return true if the object is now active; false if it was previously active
     */
    public boolean toggleActive() {
        debugging = !debugging;
        return debugging;
    }

    /**
     * Prints the given message if the object is active
     * @param message the message to print (uses System.out.print())
     */
    public void print(Object message) {
        if (debugging) {
            System.out.print(message);
        }
    }

    /**
     * Prints the given message if the object is active
     * @param message the message to print (uses System.out.println())
     */
    public void println(Object message) {
        if (debugging) {
            System.out.println(message);
        }
    }

    /**
     * Prints the stack trace of the given exception if the object is active
     * @param ex the exception on which printStackTrace is called
     */
    public void stackTrace(Exception ex) {
        if (debugging) {
            ex.printStackTrace();
        }
    }

}
