/*
 * MultiRequestVariable.java
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

import java.util.Collection;
import java.util.Vector;

/**
 * This class represents a pending asynchronous request for which more than one response is expected, 
 * and provides blocking methods through the java.util.concurrent.locks.Condition interface for the invoking 
 * thread to wait until all of the expected results are available. Note that the signal* and evaluateCondition 
 * methods of the Condition should not be used directly, unless the application logic requires the awaiting 
 * threads to be signaled without the result being complete.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class MultiRequestVariable<E> extends ConditionVariable {
    
    protected final Vector<E> result = new Vector<E>();
    
    private int targetSize;
    private boolean exact = false; 
    
    /**
     * Creates a request variable for which the given number of responses are expected
     *@param expectedSize the expected number of responses
     */
    public MultiRequestVariable(int expectedSize) {
        super();
        targetSize = expectedSize;
        if (targetSize == 0) {
            this.evaluateCondition(true);
        }
    }
    
    /**
     * Creates a request variable for which the given number of responses are expected. 
     *@param expectedSize the expected number of responses
     *@param enforceExactly if true, the condition associated with this variable will only be true if the number of responses is 
     *exactly the expected value given by expectedSize; otherwise, the condition will be true if at least this number of responses 
     *is received. It is false by default.
     */
    public MultiRequestVariable(int expectedSize, boolean enforceExactly) {
        super();
        targetSize = expectedSize;
        exact = enforceExactly;
        if (targetSize == 0) {
            this.evaluateCondition(true);
        }
    }
    
    /**
     * Called by (one of) the thread(s) to which the request was made to make a result available. The threads that are waiting 
     * on this object for the result are not signaled unless the expected number of responses have been received.
     */
    public void addResult(E resultRef) {
        synchronized (result) {
            result.add(resultRef);
        }
        if (exact) {
            this.evaluateCondition(result.size() == targetSize);
        }
        else {
            this.evaluateCondition(result.size() >= targetSize);
        }
    }
    
    /**
     *@return the possibly empty collection of results
     */
    public Collection<E> getResult() {
        return result;
    }
    
    /**
     *@return true if the number of results received is at least the expected number. If enforceExactly was set to true 
     *in the constructor, then this method will return false if more than the expected number of results were added.
     */
    public boolean isDone() {
        return this.isTrue();
    }
    
}
