/*
 * RandomIndexGenerator.java
 *
 * Copyright 2014 Andres Quiroz Hernandez
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

package programming5.code;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import programming5.arrays.ArrayOperations;
import programming5.strings.StringOperations;

/**
 * This class uses a java Random object to generate integers included in one or more given ranges. If the ranges are
 * provided properly, the generator guarantees that every integer (index) belonging to the union of the ranges will have the
 * same probability of being generated in each call to the getRandomIndex method. These integers can then,
 * for example, be used to index subsets of an array or collection, while guaranteeing that no elements outside of the
 * given index ranges will be selected. This implementation may save memory with respect to generating the desired 
 * index subset to then select a random element, if the ranges are large.
 * @author Andres Quiroz Hernandez
 * @version 6.9
 */
public class RandomIndexGenerator {

    protected int[] lowerBounds;
    protected int[] upperBounds;

    private final Random random;
    private int rangeSize;

    /**
     * Creates a random index generator that will produce numbers within the given ranges. The ranges must not overlap; 
     * otherwise, calls to getRandomIndex will not be guaranteed to be within any range or to produce all indices therein 
     * with equal probability (within the limits of the Random implementation).
     * @param ranges each range is given as a two element array, where the first element is the lower bound of the range 
     * and the second element is the upper bound of the range.
     */
    public RandomIndexGenerator(int[]... ranges) {
        random = new Random(System.currentTimeMillis());
        init(ranges);
    }

    /**
     * Creates a random index generator that will produce numbers within the given ranges. The ranges must not overlap;
     * otherwise, calls to getRandomIndex will not be guaranteed to be within any range or to produce all indices therein
     * with equal probability.
     * @param seed a seed used to create a Random object
     * @param ranges each range is given as a two element array, where the first element is the lower bound of the range
     * and the second element is the upper bound of the range; optionally, a range may be given as a single-element array,
     * denoting a single index (lower and upper bounds are equal)
     */
    public RandomIndexGenerator(long seed, int[]... ranges) {
        random = new Random(seed);
        init(ranges);
    }

    /**
     * Creates a random index generator that will produce numbers within the ranges denoted by the given string. The
     * ranges must not overlap; otherwise, calls to getRandomIndex will not be guaranteed to be within any range or to
     * produce all indices therein with equal probability.
     * @param rangeString a comma separated list of ranges, where each range is either a string of the form \d+-\d+ or
     * simply of the form \d+. The digit strings are then taken as the bounds of their respective ranges.
     */
    public RandomIndexGenerator(String rangeString) {
        random = new Random(System.currentTimeMillis());
        init(decodeRangeString(rangeString));
    }

    /**
     * Creates a random index generator that will produce numbers within the ranges denoted by the given string. The
     * ranges must not overlap; otherwise, calls to getRandomIndex will not be guaranteed to be within any range or to
     * produce all indices therein with equal probability.
     * @param seed a seed used to create a Random object
     * @param rangeString a comma separated list of ranges, where each range is either a string of the form \d+-\d+ or
     * simply of the form \d+. The digit strings are then taken as the bounds of their respective ranges.
     */
    public RandomIndexGenerator(long seed, String rangeString) {
        random = new Random(seed);
        init(decodeRangeString(rangeString));
    }

    /**
     * Calls to this method are stateless (i.e. indices are generated with replacement)
     * @return with equal probability, any number within the union of the ranges used to create this object
     */
    public int getRandomIndex() {
        return getElement(random.nextInt(rangeSize));
    }

    // Protected to test
    protected int getElement(int order) {
        int prevSize = 0;
        int whichRange = 0;
        while (whichRange < lowerBounds.length) {
            int subrangeSize = upperBounds[whichRange] - lowerBounds[whichRange] + 1;
            if (order < prevSize + subrangeSize) {
                break;
            }
            prevSize += subrangeSize;
            whichRange++;
        }
        return lowerBounds[whichRange] + (order - prevSize);

    }

    private void init(int[][] ranges) {
        lowerBounds = new int[ranges.length];
        upperBounds = new int[ranges.length];
        rangeSize = 0;  // Range size calculation will be correct in the following loop if input ranges do not overlap
        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i].length == 1) {
                lowerBounds[i] = upperBounds[i] = ranges[i][0];
                rangeSize++;
            }
            else if (ranges[i].length == 2) {
                if (ranges[i][0] <= ranges[i][1]) {
                    lowerBounds[i] = ranges[i][0];
                    upperBounds[i] = ranges[i][1];
                }
                else {  // Leniency for swapping range boundaries
                    lowerBounds[i] = ranges[i][1];
                    upperBounds[i] = ranges[i][0];
                }
                rangeSize += upperBounds[i] - lowerBounds[i] + 1;
            }
            else {
                throw new IllegalArgumentException("RandomIndexGenerator: Cannot create: [" + StringOperations.toList(ArrayOperations.toString(ranges[i])) + " is not a valid range (consisting of a single integer or integer pair)");
            }
        }
        Arrays.sort(lowerBounds);
        Arrays.sort(upperBounds);   // Correct after sorting if input ranges do not overlap
    }

    private int[][] decodeRangeString(String rangeString) {
        Pattern p = Pattern.compile("(\\d+)(\\s*\\-\\s*(\\d+))?");
        String[] ranges = rangeString.split("\\s*,\\s*");
        int[][] ret = new int[ranges.length][];
        for (int i = 0; i < ranges.length; i++) {
            Matcher m = p.matcher(ranges[i]);
            if (m.matches()) {
                ret[i] = new int[2];
                ret[i][0] = Integer.parseInt(m.group(1));
                if (m.group(3) != null) {
                    ret[i][1] = Integer.parseInt(m.group(3));
                }
                else {
                    ret[i][1] = ret[i][0];
                }
            }
            else {
                throw new IllegalArgumentException("RandomIndexGenerator: Error decoding range string on " + ranges[i]);
            }
        }
        return ret;
    }

}
