/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andresqh
 */
public class NumericStringComparatorTest {

    public NumericStringComparatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compare method, of class NumericStringComparator.
     */
    @Test
    public void testCompare() {
        NumericStringComparator comparator = new NumericStringComparator();
        assertTrue(comparator.compare("1", "2") < 0);
        assertTrue(comparator.compare("10", "10") == 0);
        assertTrue(comparator.compare("1000", "10") > 0);
        assertTrue(comparator.compare("1.99999999", "1.99999999") == 0);
        assertTrue(comparator.compare("1.9999999", "1.99999999") < 0);
        assertTrue(comparator.compare("1.99999999", "1.9999999") > 0);
        try {
            comparator.compare("Not number", "2");
            comparator.compare("2", "Not number");
            fail("Failed to catch illegal argument exception");
        }
        catch (IllegalArgumentException iae) {}
    }

}