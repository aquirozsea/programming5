/*
 * RequestVariable.java
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
 * Abstract class that defines utility methods for thread pool management
 * @author aquirozh
 * @version 6.0
 */
public abstract class ThreadPool {

    /**
     * Creates a new thread with the given Runnable instance. If there is not enough memory to create the thread, 
     * will invoke garbage collection and keep trying until the thread can be created.
     */
    public static Thread memSafeThreadStart(Runnable threadObject) {
        boolean success = false;
        Thread ret = null;
        do {
            try {
                ret = new Thread(threadObject);
                ret.start();
                success = true;
            }
            catch (OutOfMemoryError oome) {
                ret = null;
                Runtime.getRuntime().gc();
            }
        }
        while (!success);
        return ret;
    }

}
