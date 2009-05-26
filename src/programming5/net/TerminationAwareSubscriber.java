/*
 * TerminationAwareSubscriber.java
 *
 * Copyright 2005 Andres Quiroz Hernandez
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

/**
 * An event subscriber that expects notification when the event flow to which it 
 * has subscribed or its subscription itself runs out or when it is forcibly removed from a publisher's subscription list. 
 * The publisher interfaces in this framework do not guarantee that such a notification will be made, however, so that a 
 * class that implements TerminationAwareSubscriber is implementation dependent. The default implementation of Publisher does NOT call 
 * these methods to avoid unnecessary notifications, as it cannot tell the end of messages or if the subscriber itself is 
 * removing itself as a listener. As a recommendation for implementations that make use of the TerminationAwareSubscriber interface, any 
 * object different than the subscriber that calls removeListener on a publisher should call the subscriptionTerminated 
 * method on the TerminationAwareSubscriber, while the implementation of a publisher should call the noMoreEvents method only if it can 
 * guarantee that such a notification is true.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public interface TerminationAwareSubscriber<E extends programming5.net.Event> extends Subscriber<E> {
    
    /**
     *It is meant to be called by an event publisher when it has run out (indefinitely) of events to publish. The 
     *subscriber can then end its subscription if it so chooses.
     */
    public void noMoreEvents();
    
    /**
     *It is meant to be called when a subscriber is forcibly removed from a publisher's subscription list, for any reason, 
     *so that it can take any appropriate action.
     */
    public void subscriptionTerminated();
    
}
