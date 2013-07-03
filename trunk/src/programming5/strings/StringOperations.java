/*
 * StringOperations.java
 *
 * Copyright 2012 Andres Quiroz Hernandez
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

package programming5.strings;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import programming5.arrays.ArrayOperations;
import programming5.collections.CollectionUtils;
import programming5.math.NumberRange;

/**
 * Abstract class that provides utility methods for string manipulation that are not provided directly
 * by the String class
 * @author aquirozh
 * @version 6.01
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

    /**
     * Meant to decompose a string into a map of key/value pairs, where the keys are taken from special tags 
     * given in a regular-expression-like pattern, and the values as the parts of the string that correspond to the regions 
     * that the tags represent. The motivating idea is to extract mutable values or regions within a string where 
     * other parts of the string are immutable or follow a regular pattern. For example:
     * <p> decodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date> INFO: id=<id>, name=\"<name>\""), where <date>,
     * <id>, and <name> are the tags, returns
     * <p> {"date":"11/14/2003", "id":"1234", "name":"Something"}
     * <p> This method is a convenient alternative to using groups from the java regex implementation, although the 
     * implementation does not use that approach (e.g. to extract the same values using this approach, the regex pattern 
     * "(.*) INFO: id=(.*), name=\"(.*)\"" could be used, but would require slightly more complex and less intuitive code.
     * <p> Care must be taken to avoid ambiguity in the regular expression patterns, since the identifier tokens may be 
     * associated with unexpected values. It is not recommended (but still possible) to use wildcards in the immutable 
     * part of the pattern string, so the results may be unexpected. It is not defined to include tags in the scope of
     * wildcards (e.g. (<value>)*), and therefore the results will certainly be unexpected.
     * certainly be unexpected.
     * <p> TODO: Add specialized tags with internal regular expressions (e.g. <id:\d+>) in order to better control 
     * matching.
     * @param string the string to decode
     * @param regexPattern an enhanced regular expression that includes tags in the form of identifiers between angular 
     * brackets (e.g. <date>)
     * @return a map where the tag identifiers (e.g. date, id, name) are used as keys and the values are the variable 
     * elements in the string that the tags stand for.
     * @throws IllegalArgumentException if the string (i.e. the immutable portion outside the tags) does not match the 
     * given pattern (the pattern used for matching the string is equivalent to the pattern given, where the tags have 
     * been replaced by (.*)
     */
    public static Map<String, String> decodePattern(String string, String regexPattern) {
        Map<String, String> decodeElements = new HashMap<String, String>();
        String[] fields = extractAndReplace(regexPattern, "<\\w+>", ".*");
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
            throw new IllegalArgumentException("StringOperations: Cannot decode string: Line (" + string + ") does not match given pattern: " + regexPattern);
        }
        return decodeElements;
    }

    /**
     * One way to avoid ambiguities in the use of the decodePattern method is to use it multiple times with intermediate 
     * patterns that group and then progressively decompose complex expressions. The nestedDecodePattern method is a 
     * convenient way to apply this approach and obtain a final map that only keeps the lower-level (non-intermediate) 
     * tags. It works by taking additional patterns that are prefixed by the intermediate tag identifier followed by a 
     * colon.
     * <p> For example, an equivalent to decodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date> INFO: id=<id>, name=\"<name>\"") would be:
     * <p> nestedDecodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date> INFO: <pairs>", "pairs:id=<id>, name=\"<name>\"")
     * <p> Though this example does not clearly show the power of this method, it can be useful to avoid the ambiguities
     * of a single regular expression. It is possible to include nested patterns prefixed with tags that do not appear in 
     * the original pattern, as long as they appear in the previous nested pattern. For example:
     * <p> nestedDecodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date> INFO: <pairs>", "pairs:<idPart>, <namePart>", "idPart:id=<id>", "namePart:name=\"<name>\"") 
     * is possible and equivalent to the above examples.
     * <p> This method still does not take care of expressions where parts can appear in different orders, etc.
     * <p> TODO: How to approach this?
     * <p> TODO: Document and test keep intermediate tags with underscore prefix
     * @param string the string to decode
     * @param mainPattern an enhanced regular expression that includes tags in the form of identifiers between angular 
     * brackets (e.g. <date>)
     * @param nestedPatterns enhanced regular expressions like the main pattern, but prefixed by a tag name followed by 
     * a colon (e.g. "pairs:<idPart>"); the tag name must correspond to a tag present in a previous enhanced expression
     * @return a map where non-intermediate tag identifiers are used as keys and the values are the variable 
     * elements in the string that the tags stand for: in the example, the tags included in the result will be date, id, name
     */
    // TODO: Avoid double startsWith test
    public static Map<String, String> nestedDecodePattern(String string, String mainPattern, String... nestedPatterns) {
        Map<String, String> ret = decodePattern(string, mainPattern);
        for (String pattern : nestedPatterns) {
            try {
                String[] patternSplit = pattern.split(":", 2);
                String nestedID = patternSplit[0].startsWith("_") ? patternSplit[0].substring(1) : patternSplit[0];
                ret.putAll(decodePattern(ret.get(nestedID), patternSplit[1]));
                if (!nestedID.startsWith("_")) {
                    ret.remove(nestedID);
                }
            }
            catch (ArrayIndexOutOfBoundsException iobe) {
                throw new IllegalArgumentException("StringOperations: Cannot decode string: Nested pattern " + pattern + " does not follow the syntax id:enhancedRegex");
            }
        }
        return ret;
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
     *
     * @param items an input array of String
     * @param separator separator string that goes between list elements
     * @return a single string, with individual items separated by the given separator
     */
    public static String toList(String[] items, String separator) {
        String ret = "";
        for (String item : items) {
            ret = addToList(ret, separator, item);
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

    /**
     * @param intString the string to parse, representing an integer value
     * @param range the number range that must include the integer value
     * @return the integer value represented by the string, if it is contained in the given range
     * @throws NumberFormatException if the string does not represent an integer value, or if that value is not contained in the given range
     */
    public static int parseInt(String intString, NumberRange range) throws NumberFormatException {
        int ret = Integer.parseInt(intString);
        if (range.contains(ret)) {
            return ret;
        }
        else {
            throw new NumberFormatException("StringOperations: Error when parsing integer: " + intString + " is outside the range " + range.toString());
        }
    }

    /**
     * @return true if the given string contains only letters and numbers and false otherwise
     */
    public static boolean isAlnum(String string) {
        return string.matches("\\w+");
    }

    /**
     * @return true if the given string starts with a letter or underscore and contains only letters, numbers or underscores thereafter (typical identifier pattern)
     */
    public static boolean isIdentifier(String string) {
        return string.matches("_*[a-zA-z][\\w_]*");
    }

    /**
     * @return true if the string is a valid number
     */
    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (Exception e) {
            try {
                new BigInteger(string);
                return true;
            }
            catch (Exception e2) {
                return false;
            }
        }
    }

}
