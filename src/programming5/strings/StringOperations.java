/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import programming5.arrays.ArrayOperations;
import programming5.collections.CollectionUtils;

/**
 * Abstract class that provides utility methods for string manipulation that are not provided directly
 * by the String class
 * @author aquirozh
 * @version 6.0
 */
public abstract class StringOperations {

    /**
     * Extracts all* the matches for the given regular expression found in the given string.
     * <p> For example, extract("extract", ".t") returns the array {"xt", "ct"}
     * <p>(*) For nested or overlapping matches to the expression, only the leftmost starting and longest
     * match is returned
     * @param string the string to evaluate
     * @param regex the regular expression to check the string against
     * @return an array (possibly of zero length) of all substrings of the string that match the given regular
     * expression
     */
    public static String[] extract(String string, String regex) {
        List<String> matches = new ArrayList<String>();
        String[] bracket = string.split(regex, 2);
        while (bracket.length == 2) {
            matches.add(string.substring(bracket[0].length(), string.length() - bracket[1].length()));
            string = bracket[1];
            bracket = string.split(regex, 2);
        }
        return matches.toArray(new String[] {});
    }

    /**
     * Extracts the first match found for the given regular expression in the given string.
     * <p> For example, extractFirst("extract", ".t") returns the string "xt"
     * @param string the string to evaluate
     * @param regex the regular expression to check the string against
     * @return the first substring that matches the given regular expression, or null if no match is found
     */
    public static String extractFirst(String string, String regex) {
        String match = null;
        String[] bracket = string.split(regex, 2);
        if (bracket.length == 2) {
            match = string.substring(bracket[0].length(), string.length() - bracket[1].length());
        }
        return match;
    }
    
    /**
     * Finds the starting position of all* the matches for the given regular expression found in the given string.
     * <p> For example, findRegex("extract", ".t") returns the array {1, 5}
     * <p>(*) For nested or overlapping matches to the expression, only the index of the leftmost starting and
     * longest match is returned
     * @param string the string to evaluate
     * @param regex the regular expression to check the string against
     * @return an array (possibly of zero length) of indices of all substrings of the string that match the given
     * regular expression
     */
    public static int[] findRegex(String string, String regex) {
        List<Integer> matches = new ArrayList<Integer>();
        int start = 0;
        String[] bracket = string.split(regex, 2);
        while (bracket.length == 2) {
            matches.add(start + bracket[0].length());
            start += string.length() - bracket[1].length();
            string = bracket[1];
            bracket = string.split(regex, 2);
        }
        return CollectionUtils.toIntArray(matches);
    }

    /**
     * Finds the starting position of the first match found for the given regular expression in the given string.
     * <p> For example, extractFirst("extract", ".t") returns 1.
     * @param string the string to evaluate
     * @param regex the regular expression to check the string against
     * @return the index of the first substring that matches the given regular expression, or null if no match is
     * found
     */
    public static int findFirstRegex(String string, String regex) {
        int match = -1;
        String[] bracket = string.split(regex, 2);
        if (bracket.length == 2) {
            match = bracket[0].length();
        }
        return match;
    }

    /**
     * Returns all* matches of the given regular expression; the last element in the returned array is the original string with all
     * instances of regex replaced by the given string
     * <p>(*) For nested or overlapping matches to the expression, only leftmost starting and 
     * longest match is returned and replaced
     * @param string the string to evaluate
     * @param regex the regular expression to check the string against
     * @param replacement the string to put in place of the regular expression matches
     */
    public static String[] extractAndReplace(String string, String regex, String replacement) {
        List<String> matches = new ArrayList<String>();
        String ret = "";
        String[] bracket = string.split(regex, 2);
        while (bracket.length == 2) {
            matches.add(string.substring(bracket[0].length(), string.length() - bracket[1].length()));
            ret += (bracket[0] + replacement);
            string = bracket[1];
            bracket = string.split(regex, 2);
        }
        ret += bracket[0];
        return ArrayOperations.addElement(ret, matches.toArray(new String[] {}));
    }

    public static Map<String, String> decodePattern(String string, String regexPattern) {
        Map<String, String> decodeElements = new HashMap<String, String>();
        String[] fields = extractAndReplace(regexPattern, "<\\w+>", ".+");
        String[] regexSeparators = regexPattern.split("<\\w+>", -1);
        if (string.matches(fields[fields.length-1])) {
//            List<String> decodeElements = new ArrayList<String>();
            for (int i = 0; i < regexSeparators.length - 1; i++) {
                if (regexSeparators[i].length() > 0) {
                    string = string.replaceFirst(regexSeparators[i], "");
                }
                int next = regexSeparators[i+1].length() > 0 ?
                    StringOperations.findFirstRegex(string, regexSeparators[i+1]) :
                    string.length();
                decodeElements.put(fields[i].substring(1, fields[i].length() - 1), string.substring(0, next));
                if (next < string.length()) {
                    string = string.substring(next);
                }
            }
        }
        else {
            throw new IllegalArgumentException("StringOperations: Cannot decode string: Line does not match given pattern");
        }
        return decodeElements;
    }

    /**
     * Used to progressively construct a list string with a given separator, so that the separator only goes 
     * between items and not at the ends
     */
    public static String addToList(String list, String separator, String item) {
        return (list.length() > 0) ? list + separator + item : item;
    }

    /**
     * Used to progressively construct a list string with a default comma separator, so that the separator only goes
     * between items and not at the ends
     */
    public static String addToList(String list, String item) {
        return addToList(list, ", ", item);
    }

    /**
     * @param items an input array of String
     * @return a single string, with individual items separated by commas
     */
    public static String toList(String[] items) {
        String ret = "";
        for (String item : items) {
            ret = addToList(ret, ",", item);
        }
        return ret;
    }

    /**
     * @param value a string representation of an integer
     * @return equivalent to Integer.toString(Integer.parseInt(value) + 1);
     */
    public static String increment(String value) {
        return Integer.toString(Integer.parseInt(value) + 1);
    }

    /**
     * @param value a string representation of an integer
     * @return equivalent to Integer.toString(Integer.parseInt(value) - 1);
     */
    public static String decrement(String value) {
        return Integer.toString(Integer.parseInt(value) - 1);
    }

    /**
     * @param value a string representation of an integer
     * @param increment an integer value to add to the string's value
     * @return equivalent to Integer.toString(Integer.parseInt(value) + increment);
     */
    public static String incrementBy(String value, int increment) {
        return Integer.toString(Integer.parseInt(value) + increment);
    }

    /**
     * @param value a string representation of a long integer
     * @param increment a long integer value to add to the string's value
     * @return equivalent to Long.toString(Long.parseLong(value) + increment);
     */
    public static String incrementBy(String value, long increment) {
        return Long.toString(Long.parseLong(value) + increment);
    }

    /**
     * @param value a string representation of a floating point number
     * @param increment a floating point value to add to the string's value
     * @return equivalent to Float.toString(Float.parseFloat(value) + increment);
     */
    public static String incrementBy(String value, float increment) {
        return Float.toString(Float.parseFloat(value) + increment);
    }

    /**
     * @param value a string representation of a double floating point number
     * @param increment a double floating point value to add to the string's value
     * @return equivalent to Double.toString(Double.parseDouble(value) + increment);
     */
    public static String incrementBy(String value, double increment) {
        return Double.toString(Double.parseDouble(value) + increment);
    }

    /**
     * Adds the numeric values of the two strings and returns the string representation of the result, selecting
     * the appropriate numeric type
     * @param value a string representation of a number
     * @param increment a string representation of a value to add to the first
     */
    public static String incrementBy(String value, String increment) {
        try {
            int addint = Integer.parseInt(increment);
            return incrementBy(value, addint);
        }
        catch (NumberFormatException nfe) {/*Continue}*/}
        try {
            float addfloat = Float.parseFloat(increment);
            return incrementBy(value, addfloat);
        }
        catch (NumberFormatException nfe) {/*Continue}*/}
        try {
            long addlong = Long.parseLong(increment);
            return incrementBy(value, addlong);
        }
        catch (NumberFormatException nfe) {/*Continue}*/}
        return incrementBy(value, Double.parseDouble(increment));
    }

    /**
     * Inserts a string snippet into a string destination at the given position
     * @param dest the destination string
     * @param insertee the snippet to insert
     * @param position the position in the destination string where the snippet will be inserted
     * @return equivalent to dest.substring(0, position) + insertee + dest.substring(position);
     */
    public static String insert(String dest, String insertee, int position) {
        return dest.substring(0, position) + insertee + dest.substring(position);
    }

    /**
     * Inserts a string snippet into a string destination at the given positions
     * @param dest the destination string
     * @param insertee the snippet to insert
     * @param positions the positions in the destination string where the snippet will be inserted
     */
    public static String insert(String dest, String insertee, int[] positions) {
        String ret = insert(dest, insertee, positions[0]);
        for (int i = 1; i < positions.length; i++) {
            ret = insert(ret, insertee, positions[i] + (i * insertee.length()));
        }
        return ret;
    }

}
