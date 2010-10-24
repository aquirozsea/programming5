/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import java.util.ArrayList;
import java.util.List;
import programming5.arrays.ArrayOperations;
import programming5.collections.CollectionUtils;

/**
 *
 * @author aquirozh
 */
public abstract class StringOperations {

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

    public static String extractFirst(String string, String regex) {
        String match = null;
        String[] bracket = string.split(regex, 2);
        if (bracket.length == 2) {
            match = string.substring(bracket[0].length(), string.length() - bracket[1].length());
        }
        return match;
    }
    
    public static int[] findRegex(String string, String regex) {
        List<Integer> matches = new ArrayList<Integer>();
        String[] bracket = string.split(regex, 2);
        while (bracket.length == 2) {
            matches.add(bracket[0].length());
            string = bracket[1];
            bracket = string.split(regex, 2);
        }
        return CollectionUtils.toIntArray(matches);
    }

    public static int findFirstRegex(String string, String regex) {
        int match = -1;
        String[] bracket = string.split(regex, 2);
        if (bracket.length == 2) {
            match = bracket[0].length();
        }
        return match;
    }

    /**
     * Returns all matches of the given regular expression; the last element in the returned array is the original string with all
     * instances of regex replaced by the given string
     * @param string
     * @param regex
     * @return
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

}
