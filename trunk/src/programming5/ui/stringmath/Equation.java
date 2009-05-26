/*
 * Equation.java
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
 *providing the string representations that reflect all of the operations performed, using the toString method. This class provides a way 
 *to construct and hold mathematical equations or inequalities of expressions.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class Equation {
    
    public static enum Relation {
        EQUALS (" = "),
        NOT_EQUAL (" <> "),
        GREATER_THAN (" > "),
        GREATER_EQUALS (" >= "),
        LESS_THAN (" < "),
        LESS_EQUALS (" <= ");
        
        String symbol;
        
        Relation(String mySymbol) {
            symbol = mySymbol;
        }
    }
    
    protected Expression exp1, exp2;
    protected Relation relation;
    
    /**
     *An equation is a logical relation of expressions.
     *@see Expression
     */
    public Equation(Expression e1, Relation rel, Expression e2) {
        exp1 = e1;
        exp2 = e2;
        relation = rel;
    }
    
    public String toString() {
        return new String(exp1.toString() + relation.symbol + exp2.toString());
    }
    
}
