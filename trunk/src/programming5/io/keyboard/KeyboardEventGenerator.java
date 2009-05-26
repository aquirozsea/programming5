/*
 * KeyboardEventGenerator.java
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

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import programming5.net.Publisher;

/**
 *This class generates KeyboardEvents to interested subscribers. It allows enabling and disabling keys so that it only 
 *reports keys of interest to subscribers; by default, all keys are enabled. If different subscribers have interest in 
 *different sets of keys, then separate instances of this class should be used. Objects of this class can be used both 
 *with direct queries, which return a possibly empty set of currently pressed keys, or as a running thread, which 
 *signals subscribers when one or more keys have been pressed. Subscription to an instance of this class is meant to 
 *replace the direct subscription as an AWT component key listener if there is interest of interpreting concurrent 
 *pressing of keys.
 *@see KeyboardEvent
 *@see java.awt.event.KeyListener
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class KeyboardEventGenerator extends Publisher<KeyboardEvent> implements KeyListener, Runnable {
    
    protected HashSet<Integer> userKeys = null;
    protected HashSet<Integer> pressedKeys = new HashSet<Integer>();
    protected DefaultKeyState defKeyState = DefaultKeyState.ENABLED;
    
    private boolean running = false;
    private long pollPeriod;
    private static final long DEF_POLL_PERIOD = 20;
    
    private static enum DefaultKeyState {
        ENABLED, DISABLED
    };
    
    /**
     *Creates a new KeyboardEventGenerator for which, by default, all keys are enabled. In order for this object to 
     *generate keyboard events, it must be subscribed to a component that generates key events.
     */
    public KeyboardEventGenerator() {
        pollPeriod = DEF_POLL_PERIOD;
    }
    
    /**
     *Creates a new KeyboardEventGenerator that receives key events from the given generator of key events. By default, 
     *all keys are enabled. 
     */
    public KeyboardEventGenerator(Component myKeyEventGenerator) {
        myKeyEventGenerator.addKeyListener(this);
        pollPeriod = DEF_POLL_PERIOD;
    }
    
    /**
     *Sets a desired polling period for the generation of keyboard events, when used as a thread.
     */
    public void setPollPeriod(long myPollPeriod) {
        pollPeriod = myPollPeriod;
    }
    
    /**
     *Adds this object as a key listener to the given component.
     */
    public void subscribeTo(Component keyEventGenerator) {
        keyEventGenerator.addKeyListener(this);
    }
    
    /**
     *Makes the given key codes the only keys for which this object generates keyboard events; all other keys are 
     *disabled by default, even if they had been previously enabled for this same object. After this method is called, 
     *additional individual keys can be enabled using the enableKey method, and already enabled keys can be disabled 
     *with the converse method.
     *@see #enableKey(int)
     *@see #disableKey(int)
     */
    public void setEnabledKeys(Vector<Integer> keyCodes) {
        userKeys = new HashSet<Integer>();
        defKeyState = DefaultKeyState.DISABLED;
        for (Integer code : keyCodes) {
            userKeys.add(code);
        }
    }
    
    /**
     *Enables an individual key so that it can be included in keyboard events. If called before any other enabling 
     *method, it will cause all other keys to become disabled, so that additional keys may only be enabled with 
     *successive calls to this method.
     */
    public void enableKey(int keyCode) {
        if (userKeys == null) {
            userKeys = new HashSet<Integer>();
            defKeyState = DefaultKeyState.DISABLED;
        }
        if (defKeyState == DefaultKeyState.DISABLED) {
            userKeys.add(keyCode);
        }
        else {
            userKeys.remove(keyCode);
        }
    }
    
    /**
     *Makes the given key codes the only keys for which this object does not generate keyboard events; all other keys 
     *are enabled by default, even if they had been previously disabled for this same object. After this method is 
     *called, additional individual keys can be disabled using the disableKey method, and already disabled keys can be 
     *enabled with the converse method.
     *@see #disableKey(int)
     *@see #enableKey(int)
     */
    public void setDisabledKeys(Vector<Integer> keyCodes) {
        userKeys = new HashSet<Integer>();
        defKeyState = DefaultKeyState.ENABLED;
        for (Integer code : keyCodes) {
            userKeys.add(code);
        }
    }
    
    /**
     *Disables an individual key so that it will not be included in keyboard events. If called before any other enabling 
     *method, it will cause all other keys to become enabled, so that additional keys may only be disabled with 
     *successive calls to this method.
     */
    public void disableKey(int keyCode) {
        if (userKeys == null) {
            userKeys = new HashSet<Integer>();
            defKeyState = DefaultKeyState.ENABLED;
        }
        if (defKeyState == DefaultKeyState.ENABLED) {
            userKeys.add(keyCode);
        }
        else {
            userKeys.remove(keyCode);
        }
    }

    /**
     *Empty implementation of the KeyListener interface. Should not be called on directly.
     */
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     *Implementation of the KeyListener interface. Should not be called on directly.
     */
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean addKey;
        if (userKeys != null) {
            switch (defKeyState) {
                case ENABLED:
                    if (!userKeys.contains(keyCode)) {
                        pressedKeys.add(keyCode);
                    }
                    break;
                case DISABLED:
                    if (userKeys.contains(keyCode)) {
                        pressedKeys.add(keyCode);
                    }
                    break;
            }
        }
        else {
            pressedKeys.add(keyCode);
        }
    }
    
    /**
     *Implementation of the KeyListener interface. Should not be called on directly.
     */
    public void keyReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getKeyCode());
    }
    
    /**
     *This method can be used when not running this object as an independent thread and/or by objects that do
     *not implement the subscriber interface for KeyboardEvents. The getPressedKeys method can also be used for 
     *the same purpose.
     *@return a keyboard event that encapsulates the set of currently pressed keys
     *@see programming5.net.ISubscriber
     *@see #getPressedKeys
     */
    public KeyboardEvent getKeyboardEvent() {
        return new KeyboardEvent(pressedKeys);
    }
    
    /**
     *This method can be used when not running this object as an independent thread and/or by objects that do
     *not implement the subscriber interface for KeyboardEvents. It can be faster than using the getKeyboardEvent 
     *method because of the overhead for encoding the event.
     *@return a set of currently pressed keys
     *@see programming5.net.ISubscriber
     *@see #getKeyboardEvent
     */
    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }
    
    /**
     *Causes this object to be run as a thread to generate keyboard events to listeners
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            new Thread(this).start();
        }
    }
    
    /**
     *Stops the automatic generation of events if previously started.
     *@see #start
     */
    public void stop() {
        running = false;
    }
    
    /**
     *Implementation of the Runnable interface. Should not be called directly.
     */
    public void run() {
        while (running) {
            if (pressedKeys.size() > 0) {
                this.fireEvent(new KeyboardEvent(pressedKeys));
            }
            try {
                Thread.sleep(pollPeriod);
            }
            catch (InterruptedException ie) {
            }
        }
    }

}
