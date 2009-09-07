/*
 * IncompleteResultException.java
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

package programming5.net;

/**
 * This exception is meant to be used with synchronous methods that rely on asynchronous (timeout based)
 * processes to construct a composite return object, such as a collection of objects, for example, from a
 * distributed query or an iterative approximation algorithm. The result object contained in the exception
 * should be of the same type as the return object of the method throwing the exception (note: this would be
 * enforced as a generic class, but generics are not allowed for Throwable objects), and should contain the
 * part of the result that was received or generated during the execution time of the method. The purpose of
 * this exception is to introduce additional semantics to the return of such methods, so that a normal
 * return guarantees a complete response, while catching this exception indicates that a complete result
 * is not guaranteed, prompting action by the caller if warranted.
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class IncompleteResultException extends Exception {

    Object result;

    /**
     * @param partialResult the result object containing the partial result of the throwing method; the object
     * type should be the same as the result type of this method.
     */
    public IncompleteResultException(Object partialResult) {
        super();
        result = partialResult;
    }

    /**
     * @param partialResult the result object containing the partial result of the throwing method; the object
     * type should be the same as the result type of this method.
     * @param message the exception message
     */
    public IncompleteResultException(Object partialResult, String message) {
        super(message);
        result = partialResult;
    }

    /**
     * @param partialResult the result object containing the partial result of the throwing method; the object
     * type should be the same as the result type of this method.
     * @param causingException an exception that caused the incomplete result
     */
    public IncompleteResultException(Object partialResult, Throwable causingException) {
        super(causingException);
        result = partialResult;
    }

    /**
     * @param partialResult the result object containing the partial result of the throwing method; the object
     * type should be the same as the result type of this method.
     * @param message the exception message
     * @param causingException an exception that caused the incomplete result
     */
    public IncompleteResultException(Object partialResult, String message, Throwable causingException) {
        super(message, causingException);
        result = partialResult;
    }

    /**
     * @return the partial result contained in the exception object
     */
    public Object getResult() {
        return result;
    }

}
