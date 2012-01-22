/*
 * Notifier.java
 *
 * Copyright 2004 Andres Quiroz Hernandez
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

package programming5.net;

import java.util.ArrayList;
import java.util.List;

/**
 *This class implements a standard notifier that subscribes listeners that expect to be signaled. 
 *The listener objects must be of type Listener. Calls the signal methods of each listener in a separate thread to avoid hang-ups to 
 *the notifying application.
 *@see Listener
 *@author Andres Quiroz Hernandez
 *@version 6.09
 */
public abstract class Notifier implements INotifier {
    
    protected List<Listener> listeners = new ArrayList<Listener>();
    
    @Override
    public void addListener(Listener s) {
        listeners.add(s);
    }
    
    @Override
    public void removeListener(Listener s) {
        listeners.remove(s);
    }
    
    @Override
    public void fireSignal() {
        for (Listener listener : listeners) {
            final Listener auxListener = listener;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    auxListener.signal();
                }
            }).start();
        }
    }
    
}