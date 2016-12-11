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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author andresqh
 */
public class AlphabeticDistanceFunctionTest {

    static AlphabeticDistanceFunction adf;
    static String arbitrary1, arbitrary2, arbitrary3;

    public AlphabeticDistanceFunctionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        adf = new AlphabeticDistanceFunction();
        RandomStringGenerator rsg = new RandomStringGenerator();
        arbitrary1 = rsg.generateAlphabeticMixedCaseString(10);
        arbitrary2 = rsg.generateAlphabeticMixedCaseString(10);
        arbitrary3 = rsg.generateAlphabeticMixedCaseString(10);
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
     * For known specific distance values
     */
    @Test
    public void testAlphabeticDistance() {
        assertEquals(1, (int) adf.distance("a", "b"));
        assertEquals(1, (int) adf.distance("X", "y"));
        assertEquals(25, (int) adf.distance("a", "z"));
        assertEquals(1, (int) adf.distance("", "a"));
        assertEquals(1d/27, adf.distance("aa", "ab"), 0.0001);
        assertTrue(adf.distance("a", "b") > adf.distance("a", "alphabetic"));
        assertTrue(adf.distance("alphabetic", "b") > adf.distance("a", "alphabetic"));
        assertTrue(adf.distance("alphabetic", "b") > adf.distance("b", "beesewax"));
        assertTrue(adf.distance("preconceive", "prefix") < adf.distance("prefix", "prescribe"));
    }

    /**
     * Test equal strings always have distance zero
     */
    @Test
    public void testEquals() {
        assertEquals(0, (int) adf.distance("equal", "equal"));
        assertEquals(0, (int) adf.distance("", ""));
        assertEquals(0, (int) adf.distance("capsinsensitive", "CAPSINSENSITIVE"));
        assertEquals(0, adf.distance(arbitrary1, arbitrary1), 1e-15);
        assertEquals(0, adf.distance(arbitrary2, arbitrary2), 1e-15);
        assertEquals(0, adf.distance(arbitrary3, arbitrary3), 1e-15);
    }

    /**
     * A distance function must always be non-negative
     */
    @Test
    public void testNonNegative() {
        assertTrue("Distance must always be non-negative", adf.distance("s1", "s2") >= 0);
        assertTrue("Distance must always be non-negative", adf.distance("", "s2") >= 0);
        assertTrue("Distance must always be non-negative", adf.distance("other", "") >= 0);
        assertTrue("Distance must always be non-negative", adf.distance(arbitrary1, arbitrary2) >= 0);
        assertTrue("Distance must always be non-negative", adf.distance(arbitrary2, arbitrary3) >= 0);
        assertTrue("Distance must always be non-negative", adf.distance(arbitrary3, arbitrary1) >= 0);
    }

    /**
     * A distance function must be commutative
     */
    @Test
    public void testCommutative() {
        assertEquals(adf.distance("s1", "s2"), adf.distance("s2", "s1"), 0);
        assertEquals(adf.distance("s1", ""), adf.distance("", "s1"), 0);
        assertEquals(adf.distance(arbitrary1, arbitrary2), adf.distance(arbitrary2, arbitrary1), 0);
        assertEquals(adf.distance(arbitrary2, arbitrary3), adf.distance(arbitrary3, arbitrary2), 0);
        assertEquals(adf.distance(arbitrary1, arbitrary3), adf.distance(arbitrary3, arbitrary1), 0);
    }

    /**
     * A distance function must obey the triangle inequality
     */
    @Test
    public void testTriangle() {
        assertTrue("Distance must obey the triangle inequality", adf.distance("sa", "sb") + adf.distance("sb", "sc") >= adf.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", adf.distance("sa", "") + adf.distance("", "sc") >= adf.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality (Failed with " + arbitrary1 + ", " + arbitrary2 + ", " + arbitrary3 + ")", adf.distance(arbitrary1, arbitrary2) + adf.distance(arbitrary2, arbitrary3) + 0.00001 >= adf.distance(arbitrary1, arbitrary3));
    }

}