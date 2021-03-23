/*
 * ProgressConsolidator.java
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

package programming5.io;

import programming5.arrays.ArrayOperations;
import programming5.collections.CollectionUtils;

import java.util.List;

/**
 * General ad-hoc progress calculator that does not depend on knowing the number of stages of a process in advance. It relies on
 * calling a recalculate method with new progress values for each of currently known stages, so that the calculated progress is a sum of the
 * contribution of the progress within each stage (every stage is assumed to be an equal part of the overall progress). Since the number
 * of stages may grow over time (it should not decrease, but the implementation does not detect and is not affected by it), the calculated
 * progress value may decrease, but the consolidated value reported is guaranteed not to decrease (the maximum progress achieved is always
 * reported).
 * The implementation is synchronized, so multiple threads may call recalculate progress concurrently with the non-decreasing guarantee
 * being maintained for each.
 * @author andresqh
 */
public class ProgressConsolidator {

    int overallProgress = 0;

    /**
     * @param stageProgress array (var-arg) of progress values of different stages of a process, valued from 0 - 100
     * @return the largest value between previously achieved progress (possibly with a different number of stages) and the newly calculated progress value
     */
    public synchronized int recalculateProgress(int... stageProgress) {
        if (stageProgress.length > 0) {
            if (ArrayOperations.sum(stageProgress) == (100 * stageProgress.length)) {   // Boundary case:
                overallProgress = 100;
            }
            else {  // Some progress value < 100
                float stageFactor = 1.0f / stageProgress.length;
                float newProgress = 0;
                for (int stageValue : stageProgress) {
                    newProgress += stageFactor * (0.01f * stageValue);
                }
                int newProgressInt = (int) (100 * newProgress);
                if (newProgressInt > overallProgress) {
                    overallProgress = newProgressInt;
                }
            }
        }
        return overallProgress;
    }

    /**
     * @param stageProgress list of progress values of different stages of a process, valued from 0 - 100
     * @return the largest value between previously achieved progress (possibly with a different number of stages) and the newly calculated progress value
     */
    public synchronized int recalculateProgress(List<Integer> stageProgress) {
        if (!stageProgress.isEmpty()) {
            if (CollectionUtils.sum(stageProgress) == (100 * stageProgress.size())) {   // Boundary case:
                overallProgress = 100;
            }
            else {  // Some progress value < 100
                float stageFactor = 1.0f / stageProgress.size();
                float newProgress = 0;
                for (int stageValue : stageProgress) {
                    newProgress += stageFactor * (0.01f * stageValue);
                }
                int newProgressInt = (int) (100 * newProgress);
                if (newProgressInt > overallProgress) {
                    overallProgress = newProgressInt;
                }
            }
        }
        return overallProgress;
    }

    /**
     * @param stageProgress array (var-arg) of progress values of different stages of a process, valued from 0 - 1
     * @return the largest value between previously achieved progress (possibly with a different number of stages) and the newly calculated progress value
     */
    public synchronized float recalculateProgress_float(float... stageProgress) {
        if (ArrayOperations.sum(stageProgress) == stageProgress.length) {   // Boundary case:
            overallProgress = 100;
        }
        else {  // Some progress value < 100
            float stageFactor = 1.0f / stageProgress.length;
            float newProgress = 0;
            for (float stageValue : stageProgress) {
                newProgress += stageFactor * stageValue;
            }
            int newProgressInt = (int) (100 * newProgress);
            if (newProgressInt > overallProgress) {
                overallProgress = newProgressInt;
            }
        }
        return 0.01f * overallProgress;
    }

    /**
     * @param stageProgress list of progress values of different stages of a process, valued from 0 - 100
     * @return the largest value between previously achieved progress (possibly with a different number of stages) and the newly calculated progress value
     */
    public synchronized float recalculateProgress_float(List<Float> stageProgress) {
        if (CollectionUtils.sum(stageProgress) == stageProgress.size()) {   // Boundary case:
            overallProgress = 100;
        }
        else {  // Some progress value < 100
            float stageFactor = 1.0f / stageProgress.size();
            float newProgress = 0;
            for (float stageValue : stageProgress) {
                newProgress += stageFactor * stageValue;
            }
            int newProgressInt = (int) (100 * newProgress);
            if (newProgressInt > overallProgress) {
                overallProgress = newProgressInt;
            }
        }
        return 0.01f * overallProgress;
    }

}
