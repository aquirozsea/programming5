/*
 * StringEditDistanceFunction.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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

import programming5.arrays.ArrayOperations;
import programming5.io.Debug;
import programming5.math.DistanceFunction;
import programming5.math.ScoreFunction;

/**
 * Implements a family of functions for strings that finds a distance by optimizing their alignment in terms of the
 * aggregate cost of edit operations (replacing one character for another, i.e. aligning them, and inserting or
 * deleting characters, i.e. shifting alignment). The class implements the classical dynamic programming algorithm
 * for finding the minimum string edit distance, and has parameterizable cost functions (using function interfaces
 * defined in programming5.math). Currently, there are three pre-defined modes:
 * <p> DEFAULT: By default, all edit operations cost one unit (1) each; the distance will thus be the total count
 * of edit operations (e.g. "assert" and "dessert" have a distance of 2, for one insertion and one replacement).
 * This mode is case sensitive.
 * <p> ALPHABETIC_REPLACE: Reduces the cost of character replacements between alphabetic characters, relative to
 * the inverse distance between them in the alphabet. Insertions and deletions have an average cost with respect
 * to the distribution of replacement costs (this ensures that these operations are favored over replacements for
 * "far" character pairs). This mode will produce distances that are not well defined for strings containing
 * non-alphabetic characters, and is not case sensitive.
 * <p> KEYBOARD_REPLACE: Reduces the cost of character replacements for pairs that are "close" in terms of their
 * distance on the keyboard (uses the @link{programming5.strings.KeyboardCharDistanceFunction}). "Far" replacements,
 * as well as insertions and deletions, are still given a unit (1) cost. This mode is case sensitive.
 * @author Andres Quiroz Hernandez
 * @version 6.9
 */
public class StringEditDistanceFunction implements DistanceFunction<String> {

    public static enum Mode {DEFAULT, ALPHABETIC_REPLACE, KEYBOARD_REPLACE};

    protected DistanceFunction<Character> replacePenaltyFunction;
    protected ScoreFunction<Character> insertPenaltyFunction;
    protected ScoreFunction<Character> deletePenaltyFunction;

    public StringEditDistanceFunction() {

        replacePenaltyFunction = new DistanceFunction<Character>() {

            public double distance(Character obj1, Character obj2) {
                return (obj1 == obj2) ? 0 : 1;
            }

        };

        insertPenaltyFunction = deletePenaltyFunction = new ScoreFunction<Character>() {

            public double score(Character obj) {
                return 1;
            }

        };

    }

    public StringEditDistanceFunction(Mode mode) {
        switch (mode) {
            case ALPHABETIC_REPLACE: {
                replacePenaltyFunction = new DistanceFunction<Character>() {

                    public double distance(Character obj1, Character obj2) {
                        return (double) Math.abs(Character.toLowerCase(obj1) - Character.toLowerCase(obj2)) / 26;
                    }

                };
                insertPenaltyFunction = deletePenaltyFunction = new ScoreFunction<Character>() {

                    public double score(Character obj) {
                        return 0.5;    // Expected value of a character in the alphabet (1-26)
                    }

                };
            }
            break;

            case KEYBOARD_REPLACE: {
                replacePenaltyFunction = new KeyboardCharDistanceFunction();
                insertPenaltyFunction = deletePenaltyFunction = new ScoreFunction<Character>() {

                    public double score(Character obj) {
                        return 1;    // Expected value of a character in the alphabet (1-26)
                    }

                };
            }
            break;

            default: {
                replacePenaltyFunction = new DistanceFunction<Character>() {

                    public double distance(Character obj1, Character obj2) {
                        return (obj1 == obj2) ? 0 : 1;
                    }

                };
                insertPenaltyFunction = deletePenaltyFunction = new ScoreFunction<Character>() {

                    public double score(Character obj) {
                        return 1;    // Expected value of a character in the alphabet (1-26)
                    }

                };
            }
        }

    }

    public StringEditDistanceFunction(DistanceFunction<Character> myReplacePenaltyFunction,
                                      ScoreFunction<Character> myInsertPenaltyFunction,
                                      ScoreFunction<Character> myDeletePenaltyFunction)
    {
        replacePenaltyFunction = myReplacePenaltyFunction;
        insertPenaltyFunction = myInsertPenaltyFunction;
        deletePenaltyFunction = myDeletePenaltyFunction;
    }

    public double distance(String obj1, String obj2) {
        // Check trivial and edge cases
        if (obj1.equals(obj2)) {
            return 0;
        }
        else if (obj1.isEmpty()) {
            double dist = 0;
            for (int j = 0; j < obj2.length(); j++) {
                dist += insertPenaltyFunction.score(obj2.charAt(j));
            }
            return dist;
        }
        else if (obj2.isEmpty()) {
            double dist = 0;
            for (int i = 0; i < obj1.length(); i++) {
                dist += deletePenaltyFunction.score(obj1.charAt(i));
            }
            return dist;
        }
        else {
            MinDist md = new MinDist(obj1, obj2);
            for (int i = 0; i < obj1.length(); i++) {
                for (int j = 0; j < obj2.length(); j++) {
                    if (i < obj1.length()-1 || j < obj2.length()-1) {
                        double aux = md.evaluate(i, j);
                        Debug.print(aux + " ");
                    }
                }
                Debug.println("");
            }
            return md.evaluate(obj1.length()-1, obj2.length()-1);
        }
    }

    private class MinDist {

        double[][] distMatrix;
        String s1;
        String s2;

        public MinDist(String obj1, String obj2) {
            s1 = obj1;
            s2 = obj2;
            distMatrix = new double[2][s2.length()+1];
            distMatrix[0][0] = 0;
            for (int j = 1; j < distMatrix[0].length; j++) {
                distMatrix[0][j] = distMatrix[0][j-1] + insertPenaltyFunction.score(s2.charAt(j-1));
            }
            distMatrix[1][0] = deletePenaltyFunction.score(s1.charAt(0));
        }

        public double evaluate(int i, int j) {
            distMatrix[1][j+1] = ArrayOperations.min(
                distMatrix[0][j+1] + insertPenaltyFunction.score(s2.charAt(j)),
                distMatrix[1][j] + deletePenaltyFunction.score(s1.charAt(i)),
                distMatrix[0][j] + replacePenaltyFunction.distance(s1.charAt(i), s2.charAt(j))
            );
            double ret = distMatrix[1][j+1];
            if (j == s2.length()-1 && i < s1.length()-1) {
                double[] aux = distMatrix[0];
                distMatrix[0] = distMatrix[1];
                distMatrix[1] = aux;
                distMatrix[1][0] = distMatrix[0][0] + deletePenaltyFunction.score(s1.charAt(i+1));
            }
            return ret;
        }
    }

}
