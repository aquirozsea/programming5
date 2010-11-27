/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.math;

import java.util.Comparator;

/**
 * Implementation of the comparator interface for arbitrary numbers
 * @author aquirozh
 */
public class NumberComparator implements Comparator<Number> {

    /**
     * Uses the MathOperations.compare method to compare the two given numbers
     */
    @Override
    public int compare(Number n, Number m) {
        return MathOperations.compare(n, m);
    }

}
