/*
 * Function.java
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
 *providing the string representations that reflect all of the operations performed, using the toString method. This class defines a 
 *functional expression f(x), where x is an expression.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class Function extends Expression {
    
    /**
     *A function is composed of a function name and enclosed expression
     */
    public Function(String functionName, Expression exp) {
        string = new String(functionName + "(" + exp.toString() + ")");
        grouped = true;
    }
    
    /**
     *A function is negated as a block
     */
    public void negate() {
        if (!this.isNegative()) {
            string = NEGATIVE_SIGN + string;
        }
        else {
            string = string.replaceFirst(NEGATIVE_SIGN, "");
        }
    }
    
    public String toString() {
        return string;
    }
    
}
