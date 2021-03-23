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

import programming5.arrays.ArrayOperations;
import programming5.collections.NotFoundException;
import programming5.math.NumberRange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *This class is meant to receive the argument array of an application to provide methods to get arguments of different 
 *primitive types, either by specifying tags or the position of the array item.
 *@author Andres Quiroz Hernandez
 *@version 6.09
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

    public int getArgIndex(String tag) throws NotFoundException {
        return ArrayOperations.findInSequence(tag, args);
    }

    public int getArgIndex(String tag, int from) throws NotFoundException {
        return ArrayOperations.findInSequence(tag, args, from);
    }
    
    /**
     *@return the string argument preceded by tag
     */
    public String getStringArg(String tag) {
        String ret = null;
        boolean notFound = false;
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = args[index + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                notFound = true;
            }
        }
        catch (NotFoundException nfe) {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT -> throw new IllegalArgumentException(
                        Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected string: " + tag));
                case PROMPT -> {
                    System.out.print("ArgHandler: Enter string value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = in.readLine();
                        notFound = false;
                    } catch (java.io.IOException ignored) {
                    }
                }
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
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = args[index + 1];
                usingDefault = false;
                if (usingDefault && warnDefault) {
                    System.out.println("ArgHandler: Using default value of " + defVal + " for " + tag);
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        catch (NotFoundException ignored) {}
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
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage,
                                    () -> "ArgHandler: Expected string argument " + index));
                    
                case PROMPT:
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter string value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = in.readLine();
                            notFound = false;
                        } 
                        catch (java.io.IOException ignored) {}
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
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = Integer.parseInt(args[index + 1]);
            } catch (Exception e) {
                notFound = true;
            }
        }
        catch (NotFoundException nfe) {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT:
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected int: " + tag));
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter int value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = Integer.parseInt(in.readLine());
                        notFound = false;
                    } 
                    catch (Exception ignored) {}
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
                    catch (Exception ignored) {}
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
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = Integer.parseInt(args[index + 1]);
                usingDefault = false;
            }
            catch (Exception ignored) {}
        }
        catch (NotFoundException ignored) {}
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
            try {
                int index = ArrayOperations.findInSequence(tag, args);
                try {
                    int aux = Integer.parseInt(args[index + 1]);
                    if (range.contains(aux)) {
                        ret = aux;
                        usingDefault = false;
                    }
                }
                catch (Exception ignored) {}
            }
            catch (NotFoundException ignored) {}
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
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected int argument " + index));
                    
                case PROMPT:
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter int value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = Integer.parseInt(in.readLine());
                            notFound = false;
                        } 
                        catch (Exception ignored) {}
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
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage,
                                    () -> "ArgHandler: Expected int argument " + index + " in the range " + range.toString()));
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter int value for argument " + index + " in the range " + range.toString() + ": ");
                    try {
                        ret = Integer.parseInt(in.readLine());
                    } 
                    catch (Exception ignored) {}
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
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = Float.parseFloat(args[index + 1]);
            } catch (Exception e) {
                notFound = true;
            }
        }
        catch (NotFoundException nfe) {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT:
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected float: " + tag));
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter float value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = Float.parseFloat(in.readLine());
                        notFound = false;
                    } 
                    catch (Exception ignored) {}
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
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage,
                                    () -> "ArgHandler: Expected float for " + tag + " in the range " + range.toString()));
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter float value for " + tag + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Float.parseFloat(in.readLine());
                    } 
                    catch (Exception ignored) {}
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
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = Float.parseFloat(args[index + 1]);
                usingDefault = false;
            }
            catch (Exception ignored) {}
        }
        catch (NotFoundException ignored) {}
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
            try {
                int index = ArrayOperations.findInSequence(tag, args);
                try {
                    float aux = Float.parseFloat(args[index + 1]);
                    if (range.contains(aux)) {
                        ret = aux;
                        usingDefault = false;
                    }
                }
                catch (Exception ignored) {}
            }
            catch (NotFoundException ignored) {}
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
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected float argument " + index));
                    
                case PROMPT:
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter float value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = Float.parseFloat(in.readLine());
                            notFound = false;
                        } 
                        catch (Exception ignored) {}
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
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage,
                                    () -> "ArgHandler: Expected float argument " + index + " in the range " + range.toString()));
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter float value for argument " + index + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Float.parseFloat(in.readLine());
                    } 
                    catch (Exception ignored) {}
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
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = Double.parseDouble(args[index + 1]);
            } catch (Exception e) {
                notFound = true;
            }
        }
        catch (NotFoundException nfe) {
            notFound = true;
        }
        while (notFound) {
            switch (strategy) {
                case EXIT:
                    throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected double: " + tag));
                    
                case PROMPT:
                    System.out.print("ArgHandler: Enter double value for " + tag + ": ");
                    System.out.flush();
                    try {
                        ret = Double.parseDouble(in.readLine());
                        notFound = false;
                    } 
                    catch (Exception ignored) {}
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
                case EXIT -> throw new IllegalArgumentException(
                        Objects.requireNonNullElseGet(usage,
                                () -> "ArgHandler: Expected double for " + tag + " in the range " + range.toString()));
                case PROMPT -> {
                    System.out.print("ArgHandler: Enter double value for " + tag + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Double.parseDouble(in.readLine());
                    } catch (Exception ignored) {
                    }
                }
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
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            try {
                ret = Double.parseDouble(args[index + 1]);
                usingDefault = false;
            } catch (Exception ignored) {}
        }
        catch (NotFoundException ignored) {}
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
            try {
                int index = ArrayOperations.findInSequence(tag, args);
                try {
                    double aux = Double.parseDouble(args[index + 1]);
                    if (range.contains(aux)) {
                        ret = aux;
                        usingDefault = false;
                    }
                } catch (Exception ignored) {}
            }
            catch (NotFoundException ignored) {}
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
                case EXIT -> throw new IllegalArgumentException(
                        Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected double argument " + index));
                case PROMPT -> {
                    boolean notFound = true;
                    while (notFound) {
                        System.out.print("ArgHandler: Enter double value for argument " + index + ": ");
                        System.out.flush();
                        try {
                            ret = Double.parseDouble(in.readLine());
                            notFound = false;
                        } catch (Exception ignored) {
                        }
                    }
                }
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
                case EXIT -> throw new IllegalArgumentException(
                        Objects.requireNonNullElseGet(usage,
                                () -> "ArgHandler: Expected double argument " + index + " in the range " + range.toString()));
                case PROMPT -> {
                    System.out.print("ArgHandler: Enter double value for argument " + index + " in the range " + range.toString() + ": ");
                    System.out.flush();
                    try {
                        ret = Double.parseDouble(in.readLine());
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return ret;
    }
    
    /**
     *@return true if the tag is found and false otherwise
     */
    public boolean getSwitchArg(String tag) {
        try {
            ArrayOperations.findInSequence(tag, args);
            return true;
        }
        catch (NotFoundException nfe) {
            return false;
        }
    }

    /**
     * Finds zero or more occurrences of a string argument, each preceded by the given tag
     * @return a possibly empty vector of strings that follow each occurrence of the given tag
     */
    public List<String> getMultipleStringArg(String tag) {
        List<String> ret = new ArrayList<String>();
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            while (index >= 0) {
                try {
                    ret.add(args[index + 1]);
                } catch (Exception ignored) {}
                index = ArrayOperations.findInSequence(tag, args, index + 1);
            }
        }
        catch (NotFoundException ignored) {}
        return ret;
    }

    /**
     * Finds at least the given number of occurrences of a string argument, each preceded by the given tag
     * @param tag the identifier for the argument
     * @param minOccurrences the minimum number of occurrences of the given tag
     * @return a possibly empty vector of strings that follow each occurrence of the given tag
     */
    public List<String> getMultipleStringArg(String tag, int minOccurrences) {
        List<String> ret = new ArrayList<String>();
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            while (index >= 0) {
                try {
                    ret.add(args[index + 1]);
                } catch (Exception ignored) {}
                index = ArrayOperations.findInSequence(tag, args, index + 1);
            }
        }
        catch (NotFoundException ignored) {}
        while (ret.size() < minOccurrences) {
            switch (strategy) {
                case EXIT -> throw new IllegalArgumentException(
                        Objects.requireNonNullElseGet(usage, () -> "ArgHandler: Expected " + Integer.toString(minOccurrences) + " of : " + tag));
                case PROMPT -> {
                    System.out.print("ArgHandler: Enter string value for " + tag + " (" + Integer.toString(minOccurrences - ret.size()) + " left): ");
                    System.out.flush();
                    try {
                        ret.add(in.readLine());
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Finds zero or more occurrences of an integer argument, each preceded by the given tag
     * @return a possibly empty vector of integers that follow each occurrence of the given tag
     */
    public List<Integer> getMultipleIntegerArg(String tag) {
        List<Integer> ret = new ArrayList<Integer>();
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            while (index >= 0) {
                try {
                    ret.add(Integer.parseInt(args[index + 1]));
                } catch (Exception ignored) {}
                index = ArrayOperations.findInSequence(tag, args, index + 1);
            }
        }
        catch (NotFoundException ignored) {}
        return ret;
    }

    /**
     * Finds at least the given number of occurrences of an integer argument, each preceded by the given tag
     * @param tag the identifier for the argument
     * @param minOccurrences the minimum number of occurrences of the given tag
     * @return a possibly empty vector of inetegers that follow each occurrence of the given tag
     */
    public List<Integer> getMultipleIntegerArg(String tag, int minOccurrences) {
        List<Integer> ret = new ArrayList<Integer>();
        try {
            int index = ArrayOperations.findInSequence(tag, args);
            while (index >= 0) {
                try {
                    ret.add(Integer.parseInt(args[index + 1]));
                } catch (Exception ignored) {}
                index = ArrayOperations.findInSequence(tag, args, index + 1);
            }
        }
        catch (NotFoundException ignored) {}
        while (ret.size() < minOccurrences) {
            switch (strategy) {
                case EXIT -> throw new IllegalArgumentException(
                        Objects.requireNonNullElseGet(usage,
                                () -> "ArgHandler: Expected " + minOccurrences + " of : " + tag));
                case PROMPT -> {
                    System.out.print("ArgHandler: Enter string value for " + tag + " (" + (minOccurrences - ret.size()) + " left): ");
                    System.out.flush();
                    try {
                        ret.add(Integer.parseInt(in.readLine()));
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Looks for one (and only one) of a list of options in the argument string and returns the option found, or 
     * executes the appropriate exception strategy if none or multiple options are found
     * @param options the list of possible options, one of which should be in the argument string
     * @return the option found
     */
    public String getChoiceArg(String... options) {
        if (options.length > 0) {
            boolean error = false;
            String choice = null;
            for (String option : options) {
                try {
                    ArrayOperations.findInSequence(option, args);
                    if (choice == null) {
                        choice = option;
                    }
                    else {
                        error = true;
                    }
                }
                catch (NotFoundException ignored) {}
            }
            if (choice == null || error) {
                switch (strategy) {
                    case EXIT -> throw new IllegalArgumentException(
                            Objects.requireNonNullElseGet(usage,
                                    () -> "ArgHandler: Expected a single choice between " + Arrays.toString(options)));
                    case PROMPT -> {
                        boolean invChoice = true;
                        while (invChoice) {
                            System.out.print("ArgHandler: Please choose one of " + Arrays.toString(options) + ": ");
                            System.out.flush();
                            try {
                                choice = in.readLine();
                                try {
                                    ArrayOperations.findInSequence(choice, options);
                                    invChoice = false;
                                }
                                catch (NotFoundException ignored) {}
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
            return choice;
        }
        else {
            throw new IllegalArgumentException("ArgHandler: Invalid parameter for getChoiceArg: Must specify at least one option");
        }
    }
    
    /**
     * Equivalent to getChoiceArg, where the list of options is automatically obtained from an enum type definition,
     * and the return value is automatically cast to the given enumeration type.
     * @param enumType Class object for the enum type
     * @return Enum instance corresponding to the choice in the argument string
     */
    public <E extends Enum<E>> E getEnumArg(Class<E> enumType) {
        E[] enumSet = enumType.getEnumConstants();
        String[] options = new String[enumSet.length];
        for (int i = 0; i < enumSet.length; i++) {
            options[i] = enumSet[i].toString();
        }
        return Enum.valueOf(enumType, getChoiceArg(options));
    }

    /**
     * Looks for a value among options from an enumeration class following the given tag,
     * so that the return value is automatically cast to the given enumeration type.
     * @param tag the tag string that precedes the enumeration value sought
     * @param enumType Class object for the enum type
     * @return Enum instance corresponding to the choice in the argument string
     */
    public <E extends Enum<E>> E getEnumArg(String tag, Class<E> enumType) {
        E ret = null;
        String choice = getStringArg(tag);
        try {
            ret = Enum.valueOf(enumType, choice);
        }
        catch (IllegalArgumentException iae) {
            switch (strategy) {
                case EXIT:
                    if (usage != null) {
                        throw new IllegalArgumentException(usage);
                    }
                    else {
                        throw iae;
                    }

                case PROMPT:
                    boolean invChoice = true;
                    while (invChoice) {
                        System.out.print("ArgHandler: Please choose one of " + Arrays.toString(enumType.getEnumConstants()) + " for " + tag + ": ");
                        System.out.flush();
                        try {
                            ret = Enum.valueOf(enumType, in.readLine());
                            invChoice = false;
                        }
                        catch (Exception ignored) {}
                    }
                    break;
            }
        }
        return ret;
    }

    /**
     * Looks for a value among options from an enumeration class following the given tag,
     * so that the return value is automatically cast to the given enumeration type. If the tag is not
     * present, or its value does not correspond to an enumeration constant, the given default value
     * is returned.
     * @param tag the tag string that precedes the enumeration value sought
     * @param defaultValue the value to return upon a fault
     * @return Enum instance corresponding to the choice in the argument string or to the default value
     */
    public <E extends Enum<E>> E getEnumArg(String tag, E defaultValue) {
        E ret;
        try {
            ret = Enum.valueOf(defaultValue.getDeclaringClass(), getStringArg(tag));
        }
        catch (IllegalArgumentException iae) {
            ret = defaultValue;
        }
        return ret;
    }

    /**
     * Equivalent to getChoiceArg, where the list of options is automatically obtained from an enum type definition,
     * and the return value is automatically cast to the given enumeration type.
     * @param defaultValue the value to return if no valid choice from the enumeration is found
     * @return the enumeration value found, or the default value if none is found
     */
    public <E extends Enum<E>> E getEnumArg(E defaultValue) {
        E ret;
        try {
            ret = getEnumArg(defaultValue.getDeclaringClass());
        }
        catch (Exception e) {
            ret = defaultValue;
        }
        return ret;
    }

    /**
     * Pairs up all arguments and returns a key/value new map with even arguments as keys (tags) and odd arguments as values.
     * If the number of arguments is odd, the last argument is ignored
     */
    public Map<String, String> collect() {
        return collect(new HashMap<String, String>());
    }

    /**
     * Same as collect method, but adds arguments to the existing given map. The input map is modified by the method call and its reference is returned (a new map is not created)
     */
    public Map<String, String> collect(Map<String, String> mapIn) {
        for (int i = 0; i < args.length-1; i += 2) {
            mapIn.put(args[i], args[i+1]);
        }
        return mapIn;
    }

    /**
     * Same as collectOptional method, but adds arguments to the existing given map. The input map is modified by the method call and its reference is returned (a new map is not created)
     */
    public Map<String, String> collectOptional(Map<String, String> mapIn, String... tags) {
        for (String tag : tags) {
            String[] from;
            String to;
            if (tag.contains(":")) {
                String[] aux = tag.split(":");
                to = aux[1];    // collect name
                from = aux[0].split(";");
            }
            else {
                from = tag.split(";");
                to = from[0];
            }
            for (String s : from) {
                if (this.getSwitchArg(s)) {
                    mapIn.put(to, this.getStringArg(s));
                    break;
                }
            }
        }
        return mapIn;
    }

    /**
     * Puts input arguments into a new map, given the argument names. If any arguments do not exist, they are ignored
     * @param tags Argument tag names, where each tag is of the form <tag-name>{;<tag-name>}[:<collect-name>],
     * where tag-name(s) are used in the input (some tags have aliases for user convenience) and collect-name is the
     * key to use in the collect map (if different). For tags with multiple aliases, first alias will be used as
     * collect-name if none is given.
     * @return a new map with the arguments that exist in the input
     */
    public Map<String, String> collectOptional(String... tags) {
        return collectOptional(new HashMap<>(), tags);
    }

    /**
     * Same as collectRequired method, but adds arguments to the existing given map. The input map is modified by the method call and its reference is returned (a new map is not created)
     */
    public Map<String, String> collectRequired(Map<String, String> mapIn, String... tags) {
        for (String tag : tags) {
            String[] from;
            String to;
            if (tag.contains(":")) {
                String[] aux = tag.split(":");
                to = aux[1];    // collect name
                from = aux[0].split(";");
            }
            else {
                from = tag.split(";");
                to = from[0];
            }
            boolean found = false;
            for (int i = 1; i < from.length; i++) {
                if (this.getSwitchArg(from[i])) {
                    mapIn.put(to, this.getStringArg(from[i]));
                    found = true;
                    break;
                }
            }
            if (!found) {
                mapIn.put(to, this.getStringArg(from[0]));
            }
        }
        return mapIn;
    }

    /**
     * Puts input arguments into a new map, given the argument names. If any arguments do not exist, the exception strategy is applied
     * @param tags Argument tag names, where each tag is of the form <tag-name>{;<tag-name>}[:<collect-name>],
     * where tag-name(s) are used in the input (some tags have aliases for user convenience) and collect-name is the
     * key to use in the collect map (if different). For tags with multiple aliases, first alias will be used as
     * collect-name if none is given.
     * @return a new map with the arguments that exist in the input
     */
    public Map<String, String> collectRequired(String... tags) {
        return collectRequired(new HashMap<>(), tags);
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

    public void printUsage() {
        System.out.println(usage);
    }
}
