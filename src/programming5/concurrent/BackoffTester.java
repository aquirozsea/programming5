/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * TODO: Avoid thrashing between backoff and recover on regress (when period should be stable)
 * @author andresqh
 */
public abstract class BackoffTester {

    public static enum BackoffStrategy {LINEAR, EXPONENTIAL};
    public static enum RecoveryStrategy {RESET, REGRESS};

    private BackoffStrategy backoffStrategy = BackoffStrategy.LINEAR;
    private RecoveryStrategy recoveryStrategy = RecoveryStrategy.RESET;

    private Thread waitThread = null;
    private long testingPeriod;
    private long basePeriod;
    private boolean active = true;

    public BackoffTester(long myTestingPeriod) {
        basePeriod = testingPeriod = myTestingPeriod;
    }

    public BackoffTester(long myTestingPeriod, TimeUnit timeUnit) {
        basePeriod = testingPeriod = TimeUtils.toMilliseconds(myTestingPeriod, timeUnit);
    }

    public final void setBackoffStrategy(BackoffStrategy strategy) {
        if (waitThread == null) {   // TODO: Explore possibility of allowing the change
            backoffStrategy = strategy;
        }
        else {
            throw new IllegalStateException("BackoffTester: Cannot change strategy after thread has started");
        }
    }

    public final void setRecoveryStrategy(RecoveryStrategy strategy) {
        if (waitThread == null) {   // TODO: Explore possibility of allowing the change
            recoveryStrategy = strategy;
        }
        else {
            throw new IllegalStateException("BackoffTester: Cannot change strategy after thread has started");
        }
    }

    public abstract boolean test();

    public void actionWhenPosTest() {}

    public void actionWhenNegTest() {}

    public void actionWhenCancelled() {}

    public final void start() {
        if (waitThread == null) {
            waitThread = new Thread() {

                @Override
                public void run() {
                    while (active) {
                        try {
                            Thread.sleep(testingPeriod);
                            if (test()) {
                                actionWhenPosTest();
                                recover();
                            }
                            else {
                                actionWhenNegTest();
                                backoff();
                            }
                        }
                        catch (InterruptedException ie) {
                            // Should exit
                        }
                    }
                    actionWhenCancelled();
                }

            };
        }
        else {
            throw new IllegalStateException("BackoffTester: Already started");
        }
    }

    public final void cancel() {
        if (waitThread != null) {
            active = false;
            waitThread.interrupt();
        }
        else {
            throw new IllegalStateException("BackoffTester: Cannot cancel thread: Not started");
        }
    }

    private void recover() {
        switch (recoveryStrategy) {
            case RESET: testingPeriod = basePeriod;
            break;
            case REGRESS: {
                if (testingPeriod > basePeriod) {
                    switch (backoffStrategy) {
                        case LINEAR: testingPeriod -= basePeriod;
                        break;
                        case EXPONENTIAL: testingPeriod /= 2;
                        break;
                    }
                }
            }
            break;
        }
    }

    private void backoff() {
        switch (backoffStrategy) {
            case LINEAR: testingPeriod += basePeriod;
            break;
            case EXPONENTIAL: testingPeriod *= 2;
            break;
        }
    }


}
