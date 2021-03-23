/*
 * DateDecoder.java
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

import programming5.math.NumberRange;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class to create date (Calendar) objects from date strings, given a date pattern string with a specific format
 * TODO: Document format
 * @author Andres Quiroz
 * @version 6.0
 */
public class DateDecoder {

    protected String datePattern;

    protected static enum PatternCode {dd, ddd, dddd, mm, mmm, mmmm, yy, yyyy, HH, HHH, MM, SS, SSS};

    public static enum DayName {Monday, Tuesday, Wednesday, Thursday, Saturday, Sunday};
    public static enum MonthName {January, February, March, April, May, June, July, August, September, October, November, December};

    /**
     * Creates a decoder for a given pattern. Can be used to decode multiple date strings that follow the same pattern.
     */
    public DateDecoder(String myPattern) {
        datePattern = myPattern;
    }

    /**
     * @param dateString the date to decode
     * @return a Calendar object set to the time/date given in the string, if the date is consistent and well-formatted
     * @throws IllegalArgumentException if the date does not match the pattern for this object, or if the date is inconsistent or badly formatted
     */
    public Calendar decode(String dateString) throws IllegalArgumentException {
        Calendar ret = Calendar.getInstance();
        Map<String, String> dateElements = StringOperations.decodePattern(dateString, datePattern);
        for (Entry<String, String> element : dateElements.entrySet()) {
            try {
                PatternCode code = PatternCode.valueOf(element.getKey());
                switch (code) {
                    case dd: ret.set(Calendar.DAY_OF_MONTH, StringOperations.parseInt(element.getValue(), NumberRange.create("[1, 31]")));
                    break;
                    case mm: ret.set(Calendar.MONTH, StringOperations.parseInt(element.getValue(), NumberRange.create("[1, 12]")) - 1);
                    break;
                    case yy: {
                        int decade = StringOperations.parseInt(element.getValue(), NumberRange.create("[0, 99]"));
                        if (decade > 50) {
                            ret.set(Calendar.YEAR, 1900 + decade);
                        }
                        else {
                            ret.set(Calendar.YEAR, 2000 + decade);
                        }
                    }
                    break;
                    case yyyy: ret.set(Calendar.YEAR, Integer.parseInt(element.getValue()));
                    break;
                    case HHH: ret.set(Calendar.HOUR_OF_DAY, StringOperations.parseInt(element.getValue(), NumberRange.create("[0, 24)")));
                    break;
                    case MM: ret.set(Calendar.MINUTE, StringOperations.parseInt(element.getValue(), NumberRange.create("[0, 60)")));
                    break;
                    case SS: ret.set(Calendar.SECOND, StringOperations.parseInt(element.getValue(), NumberRange.create("[0, 60)")));
                    break;
                    case SSS: ret.set(Calendar.MILLISECOND, StringOperations.parseInt(element.getValue(), NumberRange.create("[0, 1000)")));
                    break;
                    default: System.err.println("DateDecoder: Warning: Pattern code " + code + " is not implemented yet");
                }
            }
            catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("DateDecoder: Cannot decode date string: Incorrect field/value combination: " + element.getKey() + " with " + element.getValue(), nfe);
            }
            catch (IllegalArgumentException iae) {
                throw new IllegalArgumentException("DateDecoder: Cannot decode date string: Unknown date tag: " + element.getKey());
            }
        }
        return ret;
    }

    /**
     * Convenience method to decode a single date with a particular pattern
     * @param dateString the date string to decode
     * @param pattern the pattern to use for decoding
     * @return a Calendar object set to the time/date given in the string, if the date is consistent and well-formatted
     * @throws IllegalArgumentException if the date does not match the pattern for this object, or if the date is inconsistent or badly formatted
     */
    public static Calendar decodeDateWithPattern(String dateString, String pattern) {
        DateDecoder decoder = new DateDecoder(pattern);
        return decoder.decode(dateString);
    }

}
