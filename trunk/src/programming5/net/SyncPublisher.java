/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.net;

/**
 * Overrides the default Publisher implementation, which fires each event in a new thread to avoid blocking the calling thread, using
 * instead the calling thread. This ensures that events are processed in the same order in which they are fired, but care must be
 * taken that listeners' signal event method is not long running and/or does not block the calling thread, to ensure all events
 * are processed.
 * @author andresqh
 */
public class SyncPublisher<E extends programming5.net.Event> extends Publisher<E> {

    /**
     *Calls the signal event methods of all subscribers, sequentially in the same thread.
     */
    @Override
    public <T extends E> void fireEvent(T event) {
        final T auxEvent = event;
        listenerLock.readLock().lock();
        try {
            for (Subscriber<E> listener : listeners) {
                listener.signalEvent(auxEvent);
            }
        }
        finally {
            listenerLock.readLock().unlock();
        }
    }

}
