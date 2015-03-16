package programming5.strings;

import java.util.Comparator;

/**
 * Wraps the standard String.compareTo method to use with generic methods that require a comparator object.
 */
// Tested in ArrayOperations
public class LexicographicStringComparator implements Comparator<String> {

    boolean ignoreCase;

    public LexicographicStringComparator() {
        ignoreCase = false;
    }

    public LexicographicStringComparator(boolean doIgnoreCase) {
        ignoreCase = doIgnoreCase;
    }

    @Override
    public int compare(String o1, String o2) {
        if (ignoreCase) {
            o1 = o1.toLowerCase();
            o2 = o2.toLowerCase();
        }
        return o1.compareTo(o2);
    }

}
