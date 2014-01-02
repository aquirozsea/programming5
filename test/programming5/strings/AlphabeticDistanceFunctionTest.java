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
public class AlphabeticDistanceFunctionTest {

    static AlphabeticDistanceFunction adf;

    public AlphabeticDistanceFunctionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        adf = new AlphabeticDistanceFunction();
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
        assertEquals(0, (int) adf.distance("also equal", "also equal"));
        assertEquals(0, (int) adf.distance("", ""));
        assertEquals(0, (int) adf.distance("capsinsensitive", "CAPSINSENSITIVE"));
    }

    /**
     * A distance function must always be non-negative
     */
    @Test
    public void testNonNegative() {
        assertTrue("Distance must always be non-negative", adf.distance("s1", "s2") >= 0);
        assertTrue("Distance must always be non-negative", adf.distance("", "s2") >= 0);
        assertTrue("Distance must always be non-negative", adf.distance("other", "") >= 0);
        assertTrue("Distance must always be non-negative", adf.distance("longer strings", "adding to length") >= 0);
        assertTrue("Distance must always be non-negative", adf.distance("adding to length", "longer strings") >= 0);
    }

    /**
     * A distance function must be commutative
     */
    @Test
    public void testCommutative() {
        assertEquals(adf.distance("s1", "s2"), adf.distance("s2", "s1"), 0);
        assertEquals(adf.distance("s1", ""), adf.distance("", "s1"), 0);
        assertEquals(adf.distance("longer strings", "adding to length"), adf.distance("adding to length", "longer strings"), 0);
    }

    /**
     * A distance function must obey the triangle inequality
     */
    @Test
    public void testTriangle() {
        assertTrue("Distance must obey the triangle inequality", adf.distance("sa", "sb") + adf.distance("sb", "sc") >= adf.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", adf.distance("sa", "") + adf.distance("", "sc") >= adf.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", adf.distance("arbitrary", "other") + adf.distance("other", "sobering") >= adf.distance("arbitrary", "sobering"));
    }

}