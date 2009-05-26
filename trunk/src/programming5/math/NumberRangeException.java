/*
 * NumberRangeException.java
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

package programming5.math;

/**
 *This runtime exception can be used by the programmer to control a value that must be within a specific range. It 
 *contains an indicator that can be used to control the nature of the transgression.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class NumberRangeException extends RuntimeException {
	
	public static enum ExceptionStatus {
		UNDETERMINED,
		UNDER,
		OVER
	};
	
	private ExceptionStatus status = ExceptionStatus.UNDETERMINED;
	
	/**
         *Creates a default number range exception.
         */
        public NumberRangeException() {
		super();
	}
	
	/**
         *Creates a number range exception with the given explanation message.
         */
        public NumberRangeException(String message) {
		super(message);
	}
	
	/**
         *Creates a number range exception. The exception status indicates whether the exception was because the value that triggered the exception was below or over the range, 
         *if this is specified by the method throwing the exception.
         */
        public NumberRangeException(ExceptionStatus detail) {
		super();
		status = detail;
	}
	
	/**
         *Creates a number range exception with the given explanation message. The exception status indicates whether the exception was 
         *because the value that triggered the exception was below or over the range, if this is specified by the method throwing the 
         *exception.
         */
        public NumberRangeException(String message, ExceptionStatus detail) {
		super(message);
		status = detail;
	}
	
	/**
         *@return true if the exception was caused by a value below the range, when this is specified by the method throwing the exception
         */
        public boolean isUnder() {
		return (status == ExceptionStatus.UNDER);
	}
	
	/**
         *@return true if the exception was caused by a value above the range, when this is specified by the method throwing the exception
         */
	public boolean isOver() {
		return (status == ExceptionStatus.OVER);
	}
}
