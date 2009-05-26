/*
 * BinaryOperation.java
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
 *providing the string representations that reflect all of the operations performed, using the toString method. A BinaryOperation defines 
 *an expression composed of two expressions and a binary operator. The operation must be defined in the corresponding enumeration.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class BinaryOperation extends Expression {
    
    public static enum Operation {
        ADDITION (" + "),
        SUBTRACTION (" - "),
        MULTIPLICATION (" * "),
        IMPLICIT_MULTIPLICATION (""),
        DIVISION (" / ");
        
        String operator;
        
        Operation(String myOperator) {
            operator = myOperator;
        }
    };
    
    protected Expression subexp1;
    protected Expression subexp2;
    protected Operation operation;
    
    /**
     *A binary operation is an arithmetic operation of expressions.
     *@see Expression
     */
    public BinaryOperation(Operation op, Expression e1, Expression e2) {
        subexp1 = e1;
        subexp2 = e2;
        operation = op;
        switch (operation) {
            
            case SUBTRACTION:
                subexp2.negate();   // Continues to do the same as for SUM
            
            case ADDITION:
                String aux = new String(subexp2.toString());
                if (subexp2.startsNegative()) {
                    if (!subexp1.isZero()) {
                        aux = aux.replaceFirst(NEGATIVE_SIGN, Operation.SUBTRACTION.operator);
                        string = new String(subexp1.toString() + aux);
                    }
                    else {
                        string = new String(aux);
                    }
                }
                else {
                    if (!subexp1.isZero()) {
                        string = string.concat(subexp1.toString());
                        if (!subexp2.isZero()) {
                            string = string.concat(Operation.ADDITION.operator + aux);
                        }
                    }
                    else {
                        if (!subexp2.isZero()) {
                            string = string.concat(aux);
                        }
                        else {
                            this.setZero(true);
                            string = "0";
                        }
                    }
                }
                break;
                
            case MULTIPLICATION:    // Continues to do the same as for IMPLICIT_MULTIPLICATION
            case IMPLICIT_MULTIPLICATION:
            case DIVISION:
                if (!subexp1.isZero() && !subexp2.isZero()) {
                    if (subexp2.isNegative()) {
                        subexp2.negate();
                        subexp1.negate();
                        subexp1.group();
                    }
                    else {
                        subexp2.group();
                        subexp1.group();
                    }
                    string = new String(subexp1.toString() + operation.operator + subexp2.toString());
                    grouped = true;
                }
                else {
                    this.setZero(true);
                    grouped = true;
                    if (subexp2.isZero() && operation == Operation.DIVISION) {
                        string = "Div. by zero";
                    }
                    else {
                        string = "0";
                    }
                }
                break;
                
        }
    }
    
    /**
     *Negates the given expression according to the rules of the operator, negating the constitutive expressions recursively.
     */
    public void negate() {
        if (this.isNegative()) {
            string = string.replaceFirst(NEGATIVE_SIGN, "");
        }
        else if (this.isGrouped()) {
            string = NEGATIVE_SIGN + string;
        }
        else {
            subexp1.negate();
            subexp2.negate();
            String aux = new String(subexp2.toString());
            if (subexp2.startsNegative()) {
                aux = aux.replaceFirst(NEGATIVE_SIGN, Operation.SUBTRACTION.operator);
                string = new String(subexp1.toString() + aux);
            }
            else {
                string = new String(subexp1.toString() + Operation.ADDITION.operator + aux);
            }
        }
    }
    
    public String toString() {
        return string;
    }
}
