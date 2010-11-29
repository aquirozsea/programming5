/*
 * Publisher.java
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

import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import programming5.concurrent.ThreadPool;


/**
 *This class implements a standard publisher that subscribes listeners to a given 
 *event type. The listener objects must be of type Subscriber. Calls the signalEvent methods of each subscriber in a separate thread to avoid hang-ups to 
 *the publishing application.
 *@see Subscriber
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class Publisher<E extends programming5.net.Event> implements IPublisher<E> {
    
    protected Vector<Subscriber<E>> listeners = new Vector<Subscriber<E>>();
    protected ReentrantReadWriteLock listenerLock = new ReentrantReadWriteLock();
    
    public void addListener(Subscriber<E> s) {
        try {
            listenerLock.writeLock().lock();
            listeners.add(s);
        }
        finally {
            listenerLock.writeLock().unlock();
        }
    }
    
    public void removeListener(Subscriber<E> s) {
        try {
            listenerLock.writeLock().lock();
            listeners.remove(s);
        }
        finally {
            listenerLock.writeLock().unlock();
        }
    }
    
    /**
     *Calls the signal event methods of all subscribers, each in a new thread.
     */
    public <T extends E> void fireEvent(T event) {
        final T auxEvent = event;
        listenerLock.readLock().lock();
        try {
            for (Subscriber<E> listener : listeners) {
                final Subscriber<E> auxListener = listener;
//                new Thread(new Runnable() {
//                    public void run() {
//                        auxListener.signalEvent(auxEvent);
//                    }
//                }).start();
                ThreadPool.memSafeThreadStart(new Runnable() {
                    public void run() {
                        auxListener.signalEvent(auxEvent);
                    }
                });
            }
        }
        finally {
            listenerLock.readLock().unlock();
        }
    }
    
    /**
     *Announces to termination aware subscribers that no more events are available from this source
     *@see programming5.net.TerminationAwareSubscriber
     */
    public void announceNoMoreEvents() {
        listenerLock.readLock().lock();
        try {
            for (Subscriber<E> listener : listeners) {
                if (listener instanceof TerminationAwareSubscriber) {
                    ((TerminationAwareSubscriber) listener).noMoreEvents();
                }
            }
        }
        finally {
            listenerLock.readLock().unlock();
        }
    }
    
    /**
     *Unilaterally terminates all subscriptions and notifies termination aware subscribers
     *@see programming5.net.TerminationAwareSubscriber
     */
    public void terminateAllSubscriptions() {
        listenerLock.readLock().lock();
        try {
            for (Subscriber<E> listener : listeners) {
                if (listener instanceof TerminationAwareSubscriber) {
                    ((TerminationAwareSubscriber) listener).subscriptionTerminated();
                }
            }
        }
        finally {
            listenerLock.readLock().unlock();
        }
        try {
            listenerLock.writeLock().lock();
            listeners.removeAllElements();
        }
        finally {
            listenerLock.writeLock().unlock();
        }
    }
}