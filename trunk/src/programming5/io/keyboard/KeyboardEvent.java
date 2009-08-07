/*
 * KeyboardEvent.java
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

package programming5.io.keyboard;

import java.util.HashSet;
import java.util.Set;
import programming5.io.Debug;
import programming5.net.Event;
import programming5.net.MalformedMessageException;
import programming5.net.Message;

/**
 *This event is meant to encapsulate a set of key codes that represent the currently pressed keys of a keyboard. It uses
 *the key codes defined in AWT and complements the AWT KeyEvent which only holds information about a single key.
 *This class is "castable" from events with empty or integer only payloads.
 *@see java.awt.event.KeyEvent
 *@see programming5.net.Event#castTo(java.lang.Class, programming5.net.Event)
 *@author Andres Quiroz Hernandez
 *@version 6.11
 */
public class KeyboardEvent extends Event {
    
    public static final String TYPE_STRING = "KBE";

    public KeyboardEvent() {
        super(TYPE_STRING);
    }
    
    /**
     *Creates a keyboard event for the given set of keys (key codes).
     */
    public KeyboardEvent(Set<Integer> keyCodes) {
        super(TYPE_STRING);
        for (Integer code : keyCodes) {
            this.addMessageItem(code);
        }
    }
    
    /**
     *Creates a keyboard event for the given event message, encoding the set of keys.
     */
    @Deprecated
    public KeyboardEvent(Message evtMsg) {
        super(evtMsg);
    }

    @Override
    public boolean assertFormat() {
        boolean ret = true;
        for (int i = 0; i < this.getMessageSize(); i++) {
            try {
                this.getItemAsInt(i);
            }
            catch (MalformedMessageException mme) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    /**
     *@return the set of key codes for which this object was created
     */
    public Set<Integer> getPressedKeys() {
        Set<Integer> ret = new HashSet<Integer>();
        try {
            for (int i = 0; i < this.getMessageSize(); i++) {
                ret.add(this.getItemAsInt(i));
            }
        }
        catch (MalformedMessageException mme) {
            Debug.printStackTrace(mme, "programming5.io.keyboard.KeyboardEvent");
        }
        return ret;
    }
    
}
