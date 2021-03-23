/*
 * RandomStringGenerator.java
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

package programming5.strings;

import programming5.arrays.ArrayOperations;
import programming5.code.RandomIndexGenerator;
import programming5.io.ConfigHandler;

import java.util.Random;

/**
 * This class allows the generation of arbitrary strings of particular or random length. It provides different methods
 * for generating the strings, each of which draws characters from different character sets to produce different types
 * of strings. It is also possible to create strings from custom character sets.
 * <p> Note this class has an external parameter that limits the maximum length of strings when generating strings of
 * random length. It is set to 50 by default, but can be changed at runtime using the property programming5.strings.RandomStringGenerator.MAX_LENGTH
 * <p> Note also that when using the constructor that accepts a seed, all methods depend on that seed, so that they should all
 * be called in a consistent order for a single object to obtain reproducible results.
 * @author Andres Quiroz Hernandez
 * @version 6.9
 */
public class RandomStringGenerator {

    private static final int MAX_LENGTH = Integer.parseInt(ConfigHandler.getProperty("programming5.strings.RandomStringGenerator.MAX_LENGTH", "50"));

    protected RandomIndexGenerator generalRandom;
    protected RandomIndexGenerator numericRandom;
    protected RandomIndexGenerator alphanumericRandom;
    protected RandomIndexGenerator alphabeticRandom;
    protected RandomIndexGenerator alphanumericMixedCaseRandom;
    protected RandomIndexGenerator alphabeticMixedCaseRandom;
    protected Random lengthRandom;

    public RandomStringGenerator() {
        lengthRandom = new Random(System.currentTimeMillis());
        generalRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "32-126");   // TODO: What is a good code range for all?
        numericRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "48-57");
        alphanumericRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "48-57, 97-122");
        alphabeticRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "97 - 122");
        alphanumericMixedCaseRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "48-57, 97-122, 65-90");
        alphabeticMixedCaseRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "65-90, 97-122");
    }
    
    /**
     * @param seed seed for random number generators
     */
    public RandomStringGenerator(long seed) {
        lengthRandom = new Random(seed);
        generalRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "32-126");
        numericRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "48-57");
        alphanumericRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "48-57, 97-122");
        alphabeticRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "97 - 122");
        alphanumericMixedCaseRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "48-57, 97-122, 65-90");
        alphabeticMixedCaseRandom = new RandomIndexGenerator(lengthRandom.nextLong(), "65-90, 97-122");
    }
    
    /**
     * @return a string of the given length constructed from printable characters (ASCII codes 32 - 126)
     */
    public String generateString(int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += Character.toString((char) generalRandom.getRandomIndex());
        }
        return ret;
    }

    /**
     * @return a string of random length constructed from printable characters (ASCII codes 32 - 126)
     */
    public String generateString() {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        return generateString(length);
    }

    /**
     * @param characters each character of the given string can be used to construct the random string; character classes
     * from java regular expressions may also be used (for example, to construct a string from numbers and dashes, use "\\d-")
     * <p> Note that only character classes are escaped; other characters that would need to be escaped in a regular expression
     * can be included as is, since they have no other meaning in this case.
     * @return a string of the given length constructed from the given character set
     */
    public String generateString(String characters, int length) {
        // Construct the ranges
        Object[] ranges = new Object[0];
        for (int i = 0; i < characters.length(); i++) {
            if (characters.charAt(i) != '\\') {
                ranges = addExpandedCode(characters.substring(i, i+1), ranges);
            }
            else {
                ranges = addExpandedCode(characters.substring(i, i+2), ranges);
                i++;
            }
        }
        int[][] aux = new int[ranges.length][];
        for (int i = 0; i < ranges.length; i++) {
            aux[i] = (int[]) ranges[i];
        }
        RandomIndexGenerator customRandom = new RandomIndexGenerator(lengthRandom.nextLong(), aux);
        // Generate string
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += Character.toString((char) customRandom.getRandomIndex());
        }
        return ret;
    }

    /**
     * @param characters each character of the given string can be used to construct the random string; character classes
     * from java regular expressions may also be used (for example, to construct a string from numbers and dashes, use "\\d-")
     * <p> Note that only character classes are escaped; other characters that would need to be escaped in a regular expression
     * can be included as is, since they have no other meaning in this case.
     * @return a string of random length constructed from the given character set
     */
    public String generateString(String characters) {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        return generateString(characters, length);
    }

    /**
     * @return a numeric only string of the given length
     */
    public String generateNumericString(int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += Character.toString((char) numericRandom.getRandomIndex());
        }
        return ret;
    }

    /**
     * @return a numeric only string of random length
     */
    public String generateNumericString() {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        return generateNumericString(length);
    }

    /**
     * @return an alphanumeric string (constructed from lower-case letters of the alphabet and numbers) of the given length
     */
    public String generateAlphanumericString(int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += Character.toString((char) alphanumericRandom.getRandomIndex());
        }
        return ret;
    }

    /**
     * @return an alphanumeric string (constructed from lower-case letters of the alphabet and numbers) of random length
     */
    public String generateAlphanumericString() {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        return generateAlphanumericString(length);
    }

    /**
     * @return a string of the given length constructed from lower-case letters of the alphabet
     */
    public String generateAlphabeticString(int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += Character.toString((char) alphabeticRandom.getRandomIndex());
        }
        return ret;
    }

    /**
     * @return a string of random length constructed from letters of the alphabet
     */
    public String generateAlphabeticString() {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        return generateAlphabeticString(length);
    }

    /**
     * @return an alphanumeric string (constructed from upper and lower-case letters of the alphabet and numbers) of the given length
     */
    public String generateAlphanumericMixedCaseString(int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += Character.toString((char) alphanumericMixedCaseRandom.getRandomIndex());
        }
        return ret;
    }

    /**
     * @return an alphanumeric string (constructed from upper and lower-case letters of the alphabet and numbers) of random length
     */
    public String generateAlphanumericMixedCaseString() {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        return generateAlphanumericMixedCaseString(length);
    }

    /**
     * @return a string of the given length constructed from upper and lower-case letters of the alphabet
     */
    public String generateAlphabeticMixedCaseString(int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += Character.toString((char) alphabeticMixedCaseRandom.getRandomIndex());
        }
        return ret;
    }

    /**
     * @return a string of random length constructed from upper and lower-case letters of the alphabet
     */
    public String generateAlphabeticMixedCaseString() {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        return generateAlphabeticMixedCaseString(length);
    }

    /**
     * @return an identifier of the given length (an identifier consists of a letter followed by letters and numbers)
     */
    public String generateIdentifierString(int length) {
        return generateAlphabeticString(1) + generateAlphanumericString(length-1);
    }

    /**
     * @return an identifier of random length (an identifier consists of a letter followed by letters and numbers)
     */
    public String generateIdentifierString() {
        int length = lengthRandom.nextInt(MAX_LENGTH) + 1;
        String ret = generateAlphabeticString(1);
        if (length > 1) {
            ret += generateAlphanumericString(length-1);
        }
        return ret;
    }

    private static Object[] addExpandedCode(String charCode, Object[] rangeArray) {
        if (charCode.startsWith("\\")) {
            // Decode character class
            if (charCode.charAt(1) == 'd') {    // Digit
                rangeArray = ArrayOperations.addElement(new int[] {48, 57}, rangeArray);
            }
            else if (charCode.charAt(1) == 'D') { // Non-digit
                rangeArray = ArrayOperations.addElement(new int[] {32, 47}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {58, 126}, rangeArray);
            }
            else if (charCode.charAt(1) == 's') {   // White space
                rangeArray = ArrayOperations.addElement(new int[] {32}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {9, 13}, rangeArray);
            }
            else if (charCode.charAt(1) == 'S') {   // Non-white space
                rangeArray = ArrayOperations.addElement(new int[] {33, 126}, rangeArray);
            }
            else if (charCode.charAt(1) == 'w') {   // Word
                rangeArray = ArrayOperations.addElement(new int[] {48, 57}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {97, 122}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {65, 90}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {95}, rangeArray);
            }
            else if (charCode.charAt(1) == 'W') {   // Non-word
                rangeArray = ArrayOperations.addElement(new int[] {32, 47}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {96}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {58, 64}, rangeArray);
                rangeArray = ArrayOperations.addElement(new int[] {91, 94}, rangeArray);
            }
        }
        else {  // Single character
            rangeArray = ArrayOperations.addElement(new int[] {(int) charCode.charAt(0)}, rangeArray);
        }
        return rangeArray;
    }

}
