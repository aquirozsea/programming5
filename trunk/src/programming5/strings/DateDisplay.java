/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import java.util.Calendar;
import programming5.strings.DateDecoder.PatternCode;

/**
 *
 * @author andresqh
 */
public class DateDisplay {

    protected Calendar date;

    public DateDisplay(Calendar myDate) {
        date = myDate;
    }

    public String display(String displayPattern) {
        String ret = displayPattern;
        String codeString = StringOperations.extractFirst(ret, "<\\w+>");
        while (codeString != null) {
            PatternCode code = DateDecoder.PatternCode.valueOf(codeString.substring(1, codeString.length()-1));
            String encodedElement;
            switch (code) {
                case dd: {
                    encodedElement = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
                    if (encodedElement.length() == 1) {
                        encodedElement = "0" + encodedElement;
                    }
                }
                break;
                case mm: {
                    encodedElement = Integer.toString(date.get(Calendar.MONTH) + 1);
                    if (encodedElement.length() == 1) {
                        encodedElement = "0" + encodedElement;
                    }
                }
                break;
                case yy: encodedElement = Integer.toString(date.get(Calendar.YEAR)).substring(2, 4);
                break;
                case yyyy: encodedElement = Integer.toString(date.get(Calendar.YEAR));
                break;
                case ddd: encodedElement = DateDecoder.DayName.values()[date.get(Calendar.DAY_OF_WEEK)].toString().substring(0, 3);
                break;
                case dddd: encodedElement = DateDecoder.DayName.values()[date.get(Calendar.DAY_OF_WEEK)].toString();
                break;
                case mmm: encodedElement = DateDecoder.DayName.values()[date.get(Calendar.MONTH)].toString().substring(0, 3);
                break;
                case mmmm: encodedElement = DateDecoder.DayName.values()[date.get(Calendar.MONTH)].toString();
                break;
                default:
                    throw new IllegalArgumentException("DateDisplay: Pattern " + codeString + " does not exist or is not implemented yet");
            }
            ret = ret.replaceFirst(codeString, encodedElement);
            codeString = StringOperations.extractFirst(ret, "<\\w+>");
        }
        return ret;
    }

    public static String displayWithPattern(Calendar date, String displayPattern) {
        return new DateDisplay(date).display(displayPattern);
    }

}
