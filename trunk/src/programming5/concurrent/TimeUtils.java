/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.concurrent;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author andresqh
 */
public abstract class TimeUtils {

    public static long toMilliseconds(long time, TimeUnit timeUnit) {
        long ret;
        switch (timeUnit) {
            case MICROSECONDS: ret = time / 1000;
                break;
            case MILLISECONDS: ret = time;
                break;
            case NANOSECONDS: ret = time / 1000000;
                break;
            case SECONDS: ret = time * 1000;
                break;
            case MINUTES: ret = time * 60000;
                break;
            case HOURS: ret = time * 3600000;
                break;
            case DAYS: ret = time * 86400000;
                break;
            default: ret = 0;
        }
        return ret;
    }

}
