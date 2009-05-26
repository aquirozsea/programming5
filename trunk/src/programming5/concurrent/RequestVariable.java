/*
 * RequestVariable.java
 *
 * Copyright 2008 Andres Quiroz Hernandez
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
 * This class represents a pending asynchronous request, and provides blocking methods through the 
 * java.util.concurrent.locks.Condition interface for the invoking thread to wait until the result is available. 
 * Note that the signal* and evaluateCondition methods of the Condition interface should not be used directly, 
 * unless the application logic requires the awaiting threads to be signaled without the result being set.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class RequestVariable<E> extends ConditionVariable {
    
    protected E result = null;
    
    public RequestVariable() {
        super();
    }
    
    /**
     * Called by the thread to which the request was made to make the result available. The threads that are waiting 
     * on this object for the result are signaled.
     */
    public void setResult(E resultRef) {
        result = resultRef;
        this.signalAll();
    }
    
    /**
     *@return the result if it has been set, or null otherwise
     */
    public E getResult() {
        return result;
    }
    
    /**
     *@return true if the setResult method has been called
     */
    public boolean isDone() {
        return this.isTrue();
    }
    
}
