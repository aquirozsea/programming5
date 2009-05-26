/*
 * ArgHandler.java
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

package programming5.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import programming5.math.NumberRange;
import programming5.arrays.ArrayOperations;

/**
 *This class is meant to receive the argument array of an application to provide methods to get arguments of different 
 *primitive types, either by specifying tags or the position of the array item.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class ArgHandler {
    
    /**
     *An ExceptionStrategy is executed if a required argument value is not found or is of the wrong type. <p>
     *EXIT: Throws an IllegalArgumentException <p>
     *PROMPT: Prompts the user for the required value until a valid value is entered <p>
     */
    public enum ExceptionStrategy {EXIT, PROMPT};
    
    private String[] args;
    private String usage = null;
    private ExceptionStrategy strategy = ExceptionStrategy.EXIT;
    private BufferedReader in = null;
    private boolean warnDefault = false;
    
    /**
     *Creates an argument handler for the given args with an EXIT exception strategy.
     */
    public ArgHandler(String[] args) {
        this.args = args;
    }
    
    /**
     *Creates an argument handler for the given args with the given exception strategy.
     */
    public ArgHandler(String[] args, ExceptionStrategy es) {
        this.args = args;
        strategy = es;
        if (strategy == ExceptionStrategy.PROMPT) {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
    }
    
    /**
     *@return the string argument preceded by tag
     */
    public String getStringArg(String tag) {
        String ret = null;
        boolean notFound = false;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = args[index+1];
            } 
            catch (ArrayIndexOutOfBoundsException e) {
                notFound = true;
            }
        } 
        else {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected string: " + tag);
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter string value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = in.readLine();
                        notFound = false;
                    } 
                    catch (java.io.IOException ioe) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the string argument preceded by tag or the given default value, if tag is not found or the value given is
     *not valid; when the default is used, will output a warning to the user if this behavior has been set
     *@see #setWarningForDefaults(boolean)
     */
    public String getStringArg(String tag, String defVal) {
        String ret = defVal;
        boolean usingDefault = true;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = args[index+1];
                usingDefault = false;
            } 
            catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        if (usingDefault && warnDefault) {
            System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
        }
        return ret;
    }
    
    /**
     *@return the string argument at the given index
     */
    public String getStringArg(int index) {
        String ret = null;
        try {
            ret = args[index];
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected string argument " + index);
                    }
                    
                case PROMPT:
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter string value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = in.readLine();
                            notFound = false;
                        } 
                        catch (java.io.IOException ioe) {}
                    }
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the int argument preceded by tag
     */
    public int getIntArg(String tag) {
        int ret = 0;
        boolean notFound = false;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = Integer.parseInt(args[index+1]);
            } 
            catch (Exception e) {
                notFound = true;
            }
        } 
        else {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected int: " + tag);
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter int value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = Integer.parseInt(in.readLine());
                        notFound = false;
                    } 
                    catch (Exception e) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the int argument preceded by tag, which must be within the given range
     */
    public int getIntArg(String tag, NumberRange range) {
        int ret = this.getIntArg(tag);
        while (!range.contains(ret)) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected int for " + tag + " in the range " + range.toString());
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter int value for " + tag + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Integer.parseInt(in.readLine());
                    } 
                    catch (Exception e) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the int argument preceded by tag or the given default value, if tag is not found or the value given is
     *not valid; when the default is used, will output a warning to the user if this behavior has been set
     *@see #setWarningForDefaults(boolean)
     */
    public int getIntArg(String tag, int defVal) {
        int ret = defVal;
        boolean usingDefault = true;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = Integer.parseInt(args[index+1]);
                usingDefault = false;
            } 
            catch (Exception e) {
            }
        }
        if (usingDefault && warnDefault) {
            System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
        }
        return ret;
    }
    
    /**
     *@return the int argument preceded by tag, which must be in the given range, or the given default value if tag is 
     *not found or the value given is not valid; when the default is used, will output a warning to the user if this 
     *behavior has been set
     *@see #setWarningForDefaults(boolean)
     */
    public int getIntArg(String tag, int defVal, NumberRange range) {
        int ret = defVal;
        if (range.contains(defVal)) {
            boolean usingDefault = true;
            int index = ArrayOperations.seqFind(tag, args);
            if (index != -1) {
                try {
                    int aux = Integer.parseInt(args[index+1]);
                    if (range.contains(aux)) {
                        ret = aux;
                        usingDefault = false;
                    }
                } 
                catch (Exception e) {
                }
            }
            if (usingDefault && warnDefault) {
                System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
            }
        }
        else throw new IllegalArgumentException("ArgHandler: The given default value is not within the specified range");
        return ret;
    }
    
    /**
     *@return the int argument at the given index
     */
    public int getIntArg(int index) {
        int ret = 0;
        try {
            ret = Integer.parseInt(args[index]);
        } catch (Exception e) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected int argument " + index);
                    }
                    
                case PROMPT:
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter int value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = Integer.parseInt(in.readLine());
                            notFound = false;
                        } 
                        catch (Exception e2) {}
                    }
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the int argument at the given index, which must be within the given range
     */
    public int getIntArg(int index, NumberRange range) {
        int ret = this.getIntArg(index);
        while (!range.contains(ret)) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected int argument " + index + " in the range " + range.toString());
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter int value for argument " + index + " in the range " + range.toString() + ": ");
                    try {
                        ret = Integer.parseInt(in.readLine());
                    } 
                    catch (Exception e2) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the float argument preceded by tag
     */
    public float getFloatArg(String tag) {
        float ret = 0;
        boolean notFound = false;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = Float.parseFloat(args[index+1]);
            } catch (Exception e) {
                notFound = true;
            }
        } else {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected float: " + tag);
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter float value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = Float.parseFloat(in.readLine());
                        notFound = false;
                    } 
                    catch (Exception e) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the float argument preceded by tag, which must be within the given range
     */
    public float getFloatArg(String tag, NumberRange range) {
        float ret = this.getFloatArg(tag);
        while (!range.contains(ret)) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected float for " + tag + " in the range " + range.toString());
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter float value for " + tag + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Float.parseFloat(in.readLine());
                    } 
                    catch (Exception e) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the float argument preceded by tag or the given default value, if tag is not found or the value given is
     *not valid; when the default is used, will output a warning to the user if this behavior has been set
     *@see #setWarningForDefaults(boolean)
     */
    public float getFloatArg(String tag, float defVal) {
        float ret = defVal;
        boolean usingDefault = true;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = Float.parseFloat(args[index+1]);
                usingDefault = false;
            } 
            catch (Exception e) {
            }
        }
        if (usingDefault && warnDefault) {
            System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
        }
        return ret;
    }
    
    /**
     *@return the float argument preceded by tag, which must be in the given range, or the given default value if tag is 
     *not found or the value given is not valid; when the default is used, will output a warning to the user if this 
     *behavior has been set
     *@see #setWarningForDefaults(boolean)
     */
    public float getFloatArg(String tag, float defVal, NumberRange range) {
        float ret = defVal;
        if (range.contains(defVal)) {
            boolean usingDefault = true;
            int index = ArrayOperations.seqFind(tag, args);
            if (index != -1) {
                try {
                    float aux = Float.parseFloat(args[index+1]);
                    if (range.contains(aux)) {
                        ret = aux;
                        usingDefault = false;
                    }
                } 
                catch (Exception e) {
                }
            }
            if (usingDefault && warnDefault) {
                System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
            }
        }
        else throw new IllegalArgumentException("ArgHandler: The given default value is not within the specified range");
        return ret;
    }
    
    /**
     *@return the float argument at the given index
     */
    public float getFloatArg(int index) {
        float ret = 0;
        try {
            ret = Float.parseFloat(args[index]);
        } catch (Exception e) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected float argument " + index);
                    }
                    
                case PROMPT:
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter float value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = Float.parseFloat(in.readLine());
                            notFound = false;
                        } 
                        catch (Exception e2) {}
                    }
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the float argument at the given index, which must be within the given range
     */
    public float getFloatArg(int index, NumberRange range) {
        float ret = this.getFloatArg(index);
        while (!range.contains(ret)) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected float argument " + index + " in the range " + range.toString());
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter float value for argument " + index + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Float.parseFloat(in.readLine());
                    } 
                    catch (Exception e2) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the double argument preceded by tag
     */
    public double getDoubleArg(String tag) {
        double ret = 0;
        boolean notFound = false;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = Double.parseDouble(args[index+1]);
            } catch (Exception e) {
                notFound = true;
            }
        } else {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected double: " + tag);
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter double value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = Double.parseDouble(in.readLine());
                        notFound = false;
                    } 
                    catch (Exception e) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the double argument preceded by tag, which must be within the given range
     */
    public double getDoubleArg(String tag, NumberRange range) {
        double ret = this.getDoubleArg(tag);
        while (!range.contains(ret)) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected double for " + tag + " in the range " + range.toString());
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter double value for " + tag + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Double.parseDouble(in.readLine());
                    } 
                    catch (Exception e) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the double argument preceded by tag or the given default value, if tag is not found or the value given is
     *not valid; when the default is used, will output a warning to the user if this behavior has been set
     *@see #setWarningForDefaults(boolean)
     */
    public double getDoubleArg(String tag, double defVal) {
        double ret = defVal;
        boolean usingDefault = true;
        int index = ArrayOperations.seqFind(tag, args);
        if (index != -1) {
            try {
                ret = Double.parseDouble(args[index+1]);
                usingDefault = false;
            } 
            catch (Exception e) {
            }
        }
        if (usingDefault && warnDefault) {
            System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
        }
        return ret;
    }
    
    /**
     *@return the double argument preceded by tag, which must be in the given range, or the given default value if tag is 
     *not found or the value given is not valid; when the default is used, will output a warning to the user if this 
     *behavior has been set
     *@see #setWarningForDefaults(boolean)
     */
    public double getDoubleArg(String tag, double defVal, NumberRange range) {
        double ret = defVal;
        if (range.contains(defVal)) {
            boolean usingDefault = true;
            int index = ArrayOperations.seqFind(tag, args);
            if (index != -1) {
                try {
                    double aux = Double.parseDouble(args[index+1]);
                    if (range.contains(aux)) {
                        ret = aux;
                        usingDefault = false;
                    }
                } 
                catch (Exception e) {
                }
            }
            if (usingDefault && warnDefault) {
                System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
            }
        }
        else throw new IllegalArgumentException("ArgHandler: The given default value is not within the specified range");
        return ret;
    }
    
    /**
     *@return the double argument at the given index
     */
    public double getDoubleArg(int index) {
        double ret = 0;
        try {
            ret = Double.parseDouble(args[index]);
        } catch (Exception e) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected double argument " + index);
                    }
                    
                case PROMPT:
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter double value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = Double.parseDouble(in.readLine());
                            notFound = false;
                        } 
                        catch (Exception e2) {}
                    }
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return the double argument at the given index, which must be within the given range
     */
    public double getDoubleArg(int index, NumberRange range) {
        double ret = this.getDoubleArg(index);
        while (!range.contains(ret)) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    } 
                    else {
                        throw new IllegalArgumentException("ArgHandler: Expected double argument " + index + " in the range " + range.toString());
                    }
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter double value for argument " + index + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Double.parseDouble(in.readLine());
                    } 
                    catch (Exception e2) {}
                    break;
            }
        }
        return ret;
    }
    
    /**
     *@return true if the tag is found and false otherwise
     */
    public boolean getSwitchArg(String tag) {
        return (ArrayOperations.seqFind(tag, args) != -1);
    }
    
    /**
     *Sets the message for an IllegalArgumentException thrown for the EXIT exception strategy
     */
    public void setUsage(String myUsage) {
        usage = "ArgHandler: \n" + myUsage;
    }
    
    /**
     *Sets the message for an IllegalArgumentException thrown for the EXIT exception strategy, including each possible
     *argument
     */
    public void setUsage(String myUsage, String... args) {
        usage = "ArgHandler: \nUsage: " + myUsage + "\n";
        if (args.length != 0) {
            usage = usage.concat("\nWith:\n");
            for (String arg : args) {
                usage = usage.concat(arg + "\n");
            }
        }
    }
    
    /**
     *Indicates if the handler should output a warning message when a default value is used for a missing or illegal 
     *argument.
     *@param doWarn true if the handler should output a warning, false otherwise
     */
    public void setWarningForDefaults(boolean doWarn) {
        warnDefault = doWarn;
    }
}
