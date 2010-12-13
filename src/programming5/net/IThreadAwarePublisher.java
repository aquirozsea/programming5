/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.net;

/**
 *Extends the interface for event publishers, IPublisher, to add an alternative fireEvent method.
 *Implementations of this interface should implement the original fireEvent method so that the calls to
 *listeners are done in independent threads, while the call to synchronousFireEvent should make sequential
 *calls from the same thread. This way, classes that need control over the execution of listener calls can
 *use the latter method. Created for backward compatibility for classes that depend on a particular implementation
 *of the fireEvent method.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public interface IThreadAwarePublisher<E extends programming5.net.Event> extends IPublisher<E> {
    public <T extends E> void synchronousFireEvent(T event);
}
