/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.io;

import java.lang.reflect.Method;
import programming5.net.Publisher;

/**
 *
 * @author aquirozh
 */
public class ProgressReportingMethod extends Publisher<ExecutionProgressEvent> implements Runnable {

    Object targetObject;
    Method targetMethod;
    boolean reporting = false;

    protected long reportingPeriod = 2000;

    public ProgressReportingMethod(Object myTargetObject, Method myTargetMethod) {
        targetObject = myTargetObject;
        targetMethod = myTargetMethod;
    }

    public ProgressReportingMethod(Object myTargetObject, Method myTargetMethod, long myReportingPeriod) {
        targetObject = myTargetObject;
        targetMethod = myTargetMethod;
        reportingPeriod = myReportingPeriod;
    }

    public void setReportingPeriod(long newPeriod) {
        reportingPeriod = newPeriod;
    }

    public Object execute(Object... params) {
        Object ret = null;
        reporting = true;
        new Thread(this).start();
        try {
            ret = targetMethod.invoke(targetObject, params);
            reporting = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void run() {
        this.synchronousFireEvent(new ExecutionProgressEvent(targetMethod.getName(), ExecutionProgressEvent.ExecutionAction.START));
        while (reporting) {
            ThreadUtils.sleep(reportingPeriod);
            if (reporting) {
                System.out.println(reporting);
                this.synchronousFireEvent(new ExecutionProgressEvent(targetMethod.getName(), ExecutionProgressEvent.ExecutionAction.PROGRESS));
            }
        }
        this.synchronousFireEvent(new ExecutionProgressEvent(targetMethod.getName(), ExecutionProgressEvent.ExecutionAction.COMPLETE));
    }

}
