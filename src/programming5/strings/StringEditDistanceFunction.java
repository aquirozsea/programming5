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
 *
 * @author andresqh
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

//    private class MinDist {
//
//        double[][] distMatrix;
//        String s1;
//        String s2;
//
//        public MinDist(String obj1, String obj2) {
//            s1 = obj1;
//            s2 = obj2;
//            distMatrix = new double[s1.length()+1][s2.length()+1];
//            distMatrix[0][0] = 0;
//            for (int j = 1; j < distMatrix[0].length; j++) {
//                distMatrix[0][j] = distMatrix[0][j-1] + insertPenaltyFunction.score(s2.charAt(j-1));
//            }
//            for (int i = 1; i < distMatrix.length; i++) {
//                distMatrix[i][0] = distMatrix[i-1][0] + deletePenaltyFunction.score(s1.charAt(i-1));
//            }
//        }
//
//        public double evaluate(int i, int j) {
//            distMatrix[i+1][j+1] = ArrayOperations.min(
//                distMatrix[i][j+1] + insertPenaltyFunction.score(s2.charAt(j)),
//                distMatrix[i+1][j] + deletePenaltyFunction.score(s1.charAt(i)),
//                distMatrix[i][j] + replacePenaltyFunction.distance(s1.charAt(i), s2.charAt(j))
//            );
//            return distMatrix[i+1][j+1];
//        }
//    }

}
