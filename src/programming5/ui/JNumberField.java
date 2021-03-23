/*
 * JNumberField.java
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

package programming5.ui;

import programming5.math.NumberRangeException;

import javax.swing.*;

/**
 *This class is a swing text field which incorporates methods to parse, limit, and return its numeric content. There is
 *also an awt counterpart.
 *@see NumberField
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class JNumberField extends JTextField {
	
	private boolean hasRange = false;
	private double minimum = Double.MIN_VALUE;
	private double maximum = Double.MAX_VALUE;
	
	/**
         *Creates an unbounded number field
         */
        public JNumberField() {
        }
        
        /**
         *Creates a number field that accepts numbers in the given range.
         *@throws java.lang.IllegalArgumentException if the range is not valid
         */
        public JNumberField(double min_value, double max_value) {
            this.setRange(min_value, max_value);
        }
        
        /**
         *@return the integer input in the text field, checking for a valid integer within the range (if specified)
         */
        public int getInt() throws NumberFormatException, NumberRangeException {
		int ret = 0;
		ret = Integer.parseInt(this.getText());
		if (hasRange) {
			if (ret < minimum) {
				throw new NumberRangeException("JNumberField.getInt: Value less than minimum", NumberRangeException.ExceptionStatus.UNDER);
                        }
			else if (ret > maximum) {
				throw new NumberRangeException("JNumberField.getInt: Value greater than maximum", NumberRangeException.ExceptionStatus.OVER);
                        }
		}
		return ret;
	}
	
	/**
         *@return the float input in the text field, checking for a valid float within the range (if specified)
         */
        public float getFloat() throws NumberFormatException, NumberRangeException {
		float ret = 0;
		ret = Float.parseFloat(this.getText());
		if (hasRange) {
			if (ret < minimum)
				throw new NumberRangeException("JNumberField.getFloat: Value less than minimum", NumberRangeException.ExceptionStatus.UNDER);
			else if (ret > maximum)
				throw new NumberRangeException("JNumberField.getFloat: Value greater than maximum", NumberRangeException.ExceptionStatus.OVER);
		}
		return ret;
	} 

	/**
         *@return the double input in the text field, checking for a valid double within the range (if specified)
         */
        public double getDouble() throws NumberFormatException, NumberRangeException {
		double ret = 0;
		ret = Double.parseDouble(this.getText());
		if (hasRange) {
			if (ret < minimum)
				throw new NumberRangeException("JNumberField.getDouble: Value less than minimum", NumberRangeException.ExceptionStatus.UNDER);
			else if (ret > maximum)
				throw new NumberRangeException("JNumberField.getDouble: Value greater than maximum", NumberRangeException.ExceptionStatus.OVER);
		}
		return ret;
	} 
	
	/**
         *Sets the field to accept inputs in the given range.
         *@throws IllegalArgumentException if the range is not valid
         */
        public void setRange(double min_value, double max_value) {
		if (min_value <= max_value) {
			minimum = min_value;
			maximum = max_value;
			hasRange = true;
		}
		else throw new IllegalArgumentException("JNumberField.setRange: Minimum must be less than or equal to the maximum");
	}
}
