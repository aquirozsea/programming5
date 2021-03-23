/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.concurrent;

import programming5.io.Debug;
import programming5.io.DebugExceptionHandler;
import programming5.io.ExceptionHandler;
import programming5.io.RobustThread;

import java.util.concurrent.TimeUnit;

/**
 * TODO: Avoid thrashing between backoff and recover on regress (when period should be stable)
 * @author andresqh
 */
public abstract class BackoffTestThread {

    public static enum BackoffStrategy {LINEAR, EXPONENTIAL};
    public static enum RecoveryStrategy {RESET, REGRESS};

    private BackoffStrategy backoffStrategy = BackoffStrategy.LINEAR;
    private RecoveryStrategy recoveryStrategy = RecoveryStrategy.RESET;

    private RobustThread testThread = null;
    private ExceptionHandler exceptionHandler = new DebugExceptionHandler();
    private long testingPeriod;
    private long basePeriod;
    private boolean active = true;

    public BackoffTestThread(long myTestingPeriod) {
        basePeriod = testingPeriod = myTestingPeriod;
    }

    public BackoffTestThread(long myTestingPeriod, TimeUnit timeUnit) {
        basePeriod = testingPeriod = TimeUtils.toMilliseconds(myTestingPeriod, timeUnit);
    }

    public final void setBackoffStrategy(BackoffStrategy strategy) {
        if (testThread == null) {   // TODO: Explore possibility of allowing the change
            backoffStrategy = strategy;
        }
        else {
            throw new IllegalStateException("BackoffTester: Cannot change strategy after thread has started");
        }
    }

    public final void setRecoveryStrategy(RecoveryStrategy strategy) {
        if (testThread == null) {   // TODO: Explore possibility of allowing the change
            recoveryStrategy = strategy;
        }
        else {
            throw new IllegalStateException("BackoffTester: Cannot change strategy after thread has started");
        }
    }

    // Note: If the exception handler is set to restart, the action when starting method will be called again
    // TODO: Separate action starting method? Putting it outside the robust thread run would violate the start() thread contract
    public final void setExceptionHandler(ExceptionHandler handler) {
        if (testThread == null) {   // TODO: Explore possibility of allowing the change
            exceptionHandler = handler;
        }
        else {
            throw new IllegalStateException("BackoffTester: Cannot change handler after thread has started");
        }
    }

    public abstract boolean test();

    public void actionWhenStarting() {}

    public void actionWhenPosTest() {}

    public void actionWhenNegTest() {}

    public void actionWhenCancelled() {}

    public final void start() {
        if (testThread == null) {
            testThread = new RobustThread(
                new Runnable() {    // First parameter of RobustThread constructor

                    @Override
                    public void run() {
                        actionWhenStarting();
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
                                Debug.println("BackoffThread interrupted (cancelled)", "programming5.concurrent.BackoffTestThread");
                            }
                        }
                        actionWhenCancelled();
                    }
                },
                exceptionHandler    // Second parameter of RobustThread constructor
            );
            testThread.start();
        }
        else {
            throw new IllegalStateException("BackoffTester: Already started");
        }
    }

    public final void cancel() {
        if (testThread != null) {
            active = false;
            testThread.interrupt();
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
