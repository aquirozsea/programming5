/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;
import programming5.math.NumberRange;

/**
 *
 * @author andresqh
 */
public class DateDecoder {

    protected String datePattern;

    private static enum PatternCode {dd, ddd, dddd, mm, mmm, mmmm, yy, yyyy, HH, HHH, MM, SS, SSS};

    public DateDecoder(String myPattern) {
        datePattern = myPattern;
    }

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

    public static Calendar decodeDateWithPattern(String dateString, String pattern) {
        DateDecoder decoder = new DateDecoder(pattern);
        return decoder.decode(dateString);
    }

}
