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
 *
 * @author aquirozh
 */
public final class RobustThread extends Thread {

    boolean running;
    Runnable target;

    public RobustThread(Runnable target) {
        super();
        this.target = target;
    }

    public RobustThread(Runnable target, String name) {
        super(name);
        this.target = target;
    }

    public RobustThread(ThreadGroup group, Runnable target) {
        super(group, "");
        this.target = target;
    }

    public RobustThread(ThreadGroup group, Runnable target, String name) {
        super(group, name);
        this.target = target;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                target.run();
                System.out.println("Thread finished");
                running = false;
            }
            catch (Exception e) {
                Debug.println("Exception in thread " + this.getName() + ": " + e.toString(), "programming5.io.RobustThread");
                Debug.println("RobustThread: Restarting thread " + this.getName(), "programming5.io.RobustThread");
            }
        }
    }

    public void terminate() {
        running = false;
        this.stop();
    }

}
