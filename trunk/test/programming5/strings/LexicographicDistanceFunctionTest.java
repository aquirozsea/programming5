/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import programming5.math.DistanceFunction;
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
public class LexicographicDistanceFunctionTest {

    static DistanceFunction<String> df1;
    static DistanceFunction<String> df2;

    public LexicographicDistanceFunctionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        df1 = new LexicographicDistanceFunction();
        df2 = new LexicographicDistanceFunction(LexicographicDistanceFunction.Mode.COUNT_DIFF_CHARS);
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
        assertEquals(1, (int) df1.distance("a", "b"));
        assertEquals(33, (int) df1.distance("X", "y"));
        assertEquals(25, (int) df1.distance("a", "z"));
        assertEquals(97, (int) df1.distance("", "a"));
        assertEquals(1, df1.distance("aa", "ab"), 0.0001);
        assertTrue(df1.distance("a", "b") < df1.distance("a", "alphabetic"));
        assertTrue(df1.distance("alphabetic", "b") > df1.distance("a", "alphabetic"));
        assertEquals(1, (int) df2.distance("a", "b"));
        assertEquals(1, (int) df2.distance("X", "y"));
        assertEquals(1, (int) df2.distance("a", "z"));
        assertEquals(1, (int) df2.distance("", "a"));
        assertEquals(1, df2.distance("aa", "ab"), 0.0001);
        assertTrue(df2.distance("a", "b") < df2.distance("a", "alphabetic"));
        assertTrue(df2.distance("alphabetic", "b") > df2.distance("a", "alphabetic"));
    }

    /**
     * Test equal strings always have distance zero
     */
    @Test
    public void testEquals() {
        assertEquals(0, (int) df1.distance("equal", "equal"));
        assertEquals(0, (int) df1.distance("also equal", "also equal"));
        assertEquals(0, (int) df1.distance("", ""));
        assertFalse((int) df1.distance("capssensitive", "CAPSSENSITIVE") == 0);
        assertEquals(0, (int) df2.distance("equal", "equal"));
        assertEquals(0, (int) df2.distance("also equal", "also equal"));
        assertEquals(0, (int) df2.distance("", ""));
        assertFalse((int) df2.distance("capssensitive", "CAPSSENSITIVE") == 0);
    }

    /**
     * A distance function must always be non-negative
     */
    @Test
    public void testNonNegative() {
        assertTrue("Distance must always be non-negative", df1.distance("s1", "s2") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("", "s2") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("other", "") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("longer strings", "adding to length") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("adding to length", "longer strings") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("s1", "s2") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("", "s2") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("other", "") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("longer strings", "adding to length") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("adding to length", "longer strings") >= 0);
    }

    /**
     * A distance function must be commutative
     */
    @Test
    public void testCommutative() {
        assertEquals(df1.distance("s1", "s2"), df1.distance("s2", "s1"), 0);
        assertEquals(df1.distance("s1", ""), df1.distance("", "s1"), 0);
        assertEquals(df1.distance("longer strings", "adding to length"), df1.distance("adding to length", "longer strings"), 0);
        assertEquals(df2.distance("s1", "s2"), df2.distance("s2", "s1"), 0);
        assertEquals(df2.distance("s1", ""), df2.distance("", "s1"), 0);
        assertEquals(df2.distance("longer strings", "adding to length"), df2.distance("adding to length", "longer strings"), 0);
    }

    /**
     * A distance function must obey the triangle inequality
     */
    @Test
    public void testTriangle() {
        assertTrue("Distance must obey the triangle inequality", df1.distance("sa", "sb") + df1.distance("sb", "sc") >= df1.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df1.distance("sa", "") + df1.distance("", "sc") >= df1.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df1.distance("arbitrary", "other") + df1.distance("other", "sobering") >= df1.distance("arbitrary", "sobering"));
        assertTrue("Distance must obey the triangle inequality", df2.distance("sa", "sb") + df2.distance("sb", "sc") >= df2.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df2.distance("sa", "") + df2.distance("", "sc") >= df2.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df2.distance("arbitrary", "other") + df2.distance("other", "sobering") >= df2.distance("arbitrary", "sobering"));
    }

}