/*
 * Term.java
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

import java.util.ArrayList;
import java.util.List;
import programming5.math.MathOperations;

/**
 *The classes in the stringmath package allow defining and operating on mathematical expressions according to operator rules and precedence, 
 *providing the string representations that reflect all of the operations performed, using the toString method. This class defines an 
 *expression of the form factor*variables which is the building block of all other expressions.
 *@author Andres Quiroz Hernandez
 *@version 6.09
 */
public class Term extends Expression {
    
    private static final int NO_FIX = -1;
    
    private double number = 1;
    private List<String> variables = new ArrayList<String>();
    private boolean operator = false;
    private int fix = NO_FIX;
    
    /**
     *Creates a numeric term with the value given.
     */
    public Term(int numFactor) {
        number = (double) numFactor;
        if (number == 0) {
            setZero(true);
        }
        grouped = true;
        fix = 0;
    }
    
    /**
     *Creates a numeric term with the value given.
     */
    public Term(double numFactor) {
        number = numFactor;
        if (number == 0) {
            setZero(true);
        }
        grouped = true;
    }
    
    /**
     *Creates a numeric term with the value given, using the number of decimal places given by fixValue.
     */
    public Term(double numFactor, int fixValue) {
        number = numFactor;
        if (number == 0) {
            setZero(true);
        }
        fix = fixValue;
        grouped = true;
    }
    
    /**
     *Creates a literal term as a multiplication of the variables given. By default, does not use a multiplication sign between 
     *variables.
     */
    public Term(String... vars) {
        for (String var : vars) {
            variables.add(var);
        }
        grouped = true;
    }
    
    
    /**
     *Creates a complete term as a multiplication of the factor and variables given. By default, does not use a multiplication sign 
     *between the factors.
     */
    public Term(int numFactor, String... vars) {
        number = (double) numFactor;
        fix = 0;
        if (number == 0) {
            setZero(true);
        }
        for (String var : vars) {
            variables.add(var);
        }
        grouped = true;
    }

    /**
     *Creates a complete term as a multiplication of the factor and variables given. By default, does not use a multiplication sign 
     *between the factors.
     */
    public Term(double numFactor, String... vars) {
        number = numFactor;
        if (number == 0) {
            setZero(true);
        }
        for (String var : vars) {
            variables.add(var);
        }
        grouped = true;
    }
    
    /**
     *Creates a complete term as a multiplication of the factor and variables given, using for the former the number of decimal 
     *places given by fixValue. By default, does not use a multiplication sign between the factors.
     */
    public Term(double numFactor, int fixValue, String... vars) {
        number = numFactor;
        if (number == 0) {
            setZero(true);
        }
        for (String var : vars) {
            variables.add(var);
        }
        fix = fixValue;
        grouped = true;
    }
    
    /**
     *Uses a multiplication sign when formatting the string that represents the expression
     */
    public void useOperator() {
        operator = true;
    }
    
    /**
     *Makes the number factor of the term negative
     */
    public void negate() {
        number *= -1;
    }
    
    public String toString() {
        string = new String("");
        if (!isZero()) {
            if (java.lang.Math.abs(number) != 1 || variables.size() == 0) {
                double aux = number;
                try {
                    aux = doFix(number);
                }
                catch (IllegalArgumentException iae) {}
                String numString = Double.toString(aux);
                if (fix == 0) {
                    numString = Integer.toString((int) aux);
                }
                string = string.concat(numString);
                if (operator && variables.size() > 0) {
                    string = string.concat("*");
                }
            }
            boolean first = true;
            for (String var : variables) {
                if (!first && operator) {
                    string = string.concat("*");
                } 
                else {
                    first = false;
                    if (number == -1) {
                        string = string.concat(NEGATIVE_SIGN);
                    }
                }
                string = string.concat(var);
            }
        }
        else {
            string = "0";
        }
        return string;
    }
    
    protected boolean startsNegative() {
        return (number < 0);
    }
    
    protected boolean isNegative() {
        return (number < 0);
    }
    
    private double doFix(double value) {
        return MathOperations.fix(value, fix);
    }
}
