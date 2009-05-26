/*
 * Expression.java
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

package programming5.ui.stringmath;

/**
 *The classes in the stringmath package allow defining and operating on mathematical expressions according to operator rules and precedence, 
 *providing the string representations that reflect all of the operations performed, using the toString method. This class is the base 
 *class for a mathematical expression.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public abstract class Expression {
    
    protected String string = "";
    protected boolean grouped = false;
    protected boolean zero = false;
    
    protected static String NEGATIVE_SIGN = "-";
    
    public abstract void negate();
    public abstract String toString();
    
    protected boolean startsNegative() {
        return (string.startsWith(NEGATIVE_SIGN));
    }
    
    protected boolean isNegative() {
        return (grouped && startsNegative());
    }
    
    /**
     *Surrounds the expression with parentheses, forcing precedence
     */
    public void group() {
        if (!grouped) {
            string = "(" + string + ")";
            grouped = true;
        }
    }
    
    /**
     *Surrounds the expression with the given grouping symbols, forcing precedence
     */
    public void group(String open, String close) {
        if (!grouped) {
            string = open + string + close;
            grouped = true;
        }
    }

    /**
     *@return true if the expression is grouped (has precedence)
     */
    public boolean isGrouped() {
        return grouped;
    }

    /**
     *Zero is a special expression, with special effect on the formatted string
     */
    public boolean isZero() {
        return zero;
    }

    protected void setZero(boolean zero) {
        this.zero = zero;
    }
    
}
