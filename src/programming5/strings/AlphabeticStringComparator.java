package programming5.strings;

/**
 * This class should be used only with word strings, as it represents a lexicographic string comparator that compares
 * strings ignoring case. If non-word strings are used, the results are undefined.
 */
public class AlphabeticStringComparator extends LexicographicStringComparator {

    public AlphabeticStringComparator() {
        super(true);    // Constructor for a lexicographic string comparator that ignores case
    }

}
