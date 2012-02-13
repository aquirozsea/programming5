/*
 * TimeUtils.java
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

package programming5.math;

/**
 * Abstract utility class to deal with time-based numeric values and time-based math
 * @author Andres Quiroz
 * @version 6.0
 */
public abstract class TimeUtils {
    
    public static final long SECOND_MILLIS = 1000;
    public static final long MINUTE_MILLIS = 60000;
    public static final long HOUR_MILLIS = 3600000;
    public static final long DAY_MILLIS = 86400000;

    /**
     * @param minuteValue minutes to convert to milliseconds
     * @return the millisecond equivalent of the minute value
     */
    public static long minutesToMillis(int minuteValue) {
        return MINUTE_MILLIS * minuteValue;
    }

    /**
     * @param millisecondValue milliseconds to convert to minutes
     * @return the equivalent, in minutes, of the millisecond value
     */
    public static double millisToMinutes(long millisecondValue) {
        return (double) millisecondValue / MINUTE_MILLIS;
    }

}
