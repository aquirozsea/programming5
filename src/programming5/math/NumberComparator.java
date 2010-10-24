/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.math;

import java.util.Comparator;

/**
 *
 * @author aquirozh
 */
public class NumberComparator implements Comparator<Number> {

    @Override
    public int compare(Number n, Number m) {
        return MathOperations.compare(n, m);
    }

}
