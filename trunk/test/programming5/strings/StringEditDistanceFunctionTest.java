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
public class StringEditDistanceFunctionTest {

    static DistanceFunction<String> df1, df2, df3;
    static String arbitrary1, arbitrary2, arbitrary3;

    public StringEditDistanceFunctionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        df1 = new StringEditDistanceFunction();
        df2 = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.ALPHABETIC_REPLACE);
        df3 = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.KEYBOARD_REPLACE);
        RandomStringGenerator rsg = new RandomStringGenerator();
        arbitrary1 = rsg.generateString();
        arbitrary2 = rsg.generateString();
        arbitrary3 = rsg.generateString();
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
    public void testDistance() {
        // Test default mode
        assertEquals(1, (int) df1.distance("a", "b"));
        assertEquals(1, (int) df1.distance("X", "y"));
        assertEquals(2, (int) df1.distance("assert", "dessert"));
        assertEquals(3, (int) df1.distance("abc", ""));
        // Test alphabetic mode
        assertEquals(1d/26, df2.distance("a", "b"), 1e-15);
        assertEquals(1d/26, df2.distance("X", "y"), 1e-15);
        assertEquals(1.5, df2.distance("abc", ""), 0.0001);
        // Test keyboard mode
        assertEquals(1, df3.distance("a", "b"), 1e-15);
        assertEquals(1, df3.distance("X", "y"), 1e-15);
        assertEquals(1.8, df3.distance("abc", "bca"), 0.0001);
        assertEquals(3, df3.distance("", "bca"), 0.0001);
    }

    /**
     * Test equal strings always have distance zero
     */
    @Test
    public void testEquals() {
        // Test default mode
        assertEquals(0, df1.distance("e", "e"), 1e-5);
        assertEquals(0, df1.distance(" ", " "), 1e-5);
        assertFalse(df1.distance("a", "A") == 0);
        assertEquals(0, df1.distance(arbitrary1, arbitrary1), 1e-15);
        assertEquals(0, df1.distance(arbitrary2, arbitrary2), 1e-15);
        assertEquals(0, df1.distance(arbitrary3, arbitrary3), 1e-15);
        // Test alphabetic mode
        assertEquals(0, df2.distance("e", "e"), 1e-5);
        assertEquals(0, df2.distance(" ", " "), 1e-5);
        assertEquals(0, df2.distance("a", "A"), 1e-5);
        assertEquals(0, df2.distance(arbitrary1, arbitrary1), 1e-15);
        assertEquals(0, df2.distance(arbitrary2, arbitrary2), 1e-15);
        assertEquals(0, df2.distance(arbitrary3, arbitrary3), 1e-15);
        // Test keyboard mode
        assertEquals(0, df3.distance("e", "e"), 1e-5);
        assertEquals(0, df3.distance(" ", " "), 1e-5);
        assertFalse(df3.distance("a", "A") == 0);
        assertEquals(0, df3.distance(arbitrary1, arbitrary1), 1e-15);
        assertEquals(0, df3.distance(arbitrary2, arbitrary2), 1e-15);
        assertEquals(0, df3.distance(arbitrary3, arbitrary3), 1e-15);
    }

    /**
     * A distance function must always be non-negative
     */
    @Test
    public void testNonNegative() {
        // Test default mode
        assertTrue("Distance must always be non-negative", df1.distance("s", "s") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("o", "*") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("B", "9") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("ollie", "") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance("", "9door") >= 0);
        assertTrue("Distance must always be non-negative", df1.distance(arbitrary1, arbitrary2) >= 0);
        assertTrue("Distance must always be non-negative", df1.distance(arbitrary2, arbitrary3) >= 0);
        assertTrue("Distance must always be non-negative", df1.distance(arbitrary3, arbitrary1) >= 0);
        // Test alphabetic mode
        assertTrue("Distance must always be non-negative", df2.distance("s", "s") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("o", "*") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("B", "9") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("ollie", "") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance("", "9er") >= 0);
        assertTrue("Distance must always be non-negative", df2.distance(arbitrary1, arbitrary2) >= 0);
        assertTrue("Distance must always be non-negative", df2.distance(arbitrary2, arbitrary3) >= 0);
        assertTrue("Distance must always be non-negative", df2.distance(arbitrary3, arbitrary1) >= 0);
        // Test keyboard mode
        assertTrue("Distance must always be non-negative", df3.distance("s", "s") >= 0);
        assertTrue("Distance must always be non-negative", df3.distance("o", "*") >= 0);
        assertTrue("Distance must always be non-negative", df3.distance("B", "9") >= 0);
        assertTrue("Distance must always be non-negative", df3.distance("ollie", "") >= 0);
        assertTrue("Distance must always be non-negative", df3.distance("", "9er") >= 0);
        assertTrue("Distance must always be non-negative", df3.distance(arbitrary1, arbitrary2) >= 0);
        assertTrue("Distance must always be non-negative", df3.distance(arbitrary2, arbitrary3) >= 0);
        assertTrue("Distance must always be non-negative", df3.distance(arbitrary3, arbitrary1) >= 0);
    }

    /**
     * A distance function must be commutative
     */
    @Test
    public void testCommutative() {
        // Test default mode
        assertEquals(df1.distance("1", "2"), df1.distance("2", "1"), 0);
        assertEquals(df1.distance("", "s1"), df1.distance("s1", ""), 0);
        assertEquals(df1.distance(arbitrary1, arbitrary2), df1.distance(arbitrary2, arbitrary1), 0);
        assertEquals(df1.distance(arbitrary2, arbitrary3), df1.distance(arbitrary3, arbitrary2), 0);
        assertEquals(df1.distance(arbitrary1, arbitrary3), df1.distance(arbitrary3, arbitrary1), 0);
        // Test alphabetic mode
        assertEquals(df2.distance("1", "2"), df2.distance("2", "1"), 0);
        assertEquals(df2.distance("", "s1"), df2.distance("s1", ""), 0);
        assertEquals(df2.distance(arbitrary1, arbitrary2), df2.distance(arbitrary2, arbitrary1), 0);
        assertEquals(df2.distance(arbitrary2, arbitrary3), df2.distance(arbitrary3, arbitrary2), 0);
        assertEquals(df2.distance(arbitrary1, arbitrary3), df2.distance(arbitrary3, arbitrary1), 0);
        // Test keyboard mode
        assertEquals(df3.distance("1", "2"), df3.distance("2", "1"), 0);
        assertEquals(df3.distance("", "s1"), df3.distance("s1", ""), 0);
        assertEquals(df3.distance(arbitrary1, arbitrary2), df3.distance(arbitrary2, arbitrary1), 0);
        assertEquals(df3.distance(arbitrary2, arbitrary3), df3.distance(arbitrary3, arbitrary2), 0);
        assertEquals(df3.distance(arbitrary1, arbitrary3), df3.distance(arbitrary3, arbitrary1), 0);
    }

    /**
     * A distance function must obey the triangle inequality
     */
    @Test
    public void testTriangle() {
        // Test default mode
        assertTrue("Distance must obey the triangle inequality", df1.distance("sa", "sb") + df1.distance("sb", "sc") >= df1.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df1.distance("sa", "") + df1.distance("", "sc") >= df1.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df1.distance(arbitrary1, arbitrary2) + df1.distance(arbitrary2, arbitrary3) >= df1.distance(arbitrary1, arbitrary3));
        // Test alphabetic mode
        assertTrue("Distance must obey the triangle inequality", df2.distance("sa", "sb") + df2.distance("sb", "sc") >= df2.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df2.distance("sa", "") + df2.distance("", "sc") >= df2.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df2.distance(arbitrary1, arbitrary2) + df2.distance(arbitrary2, arbitrary3) >= df2.distance(arbitrary1, arbitrary3));
        // Test keyboard mode
        assertTrue("Distance must obey the triangle inequality", df3.distance("sa", "sb") + df3.distance("sb", "sc") >= df3.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df3.distance("sa", "") + df3.distance("", "sc") >= df3.distance("sa", "sc"));
        assertTrue("Distance must obey the triangle inequality", df3.distance(arbitrary1, arbitrary2) + df3.distance(arbitrary2, arbitrary3) >= df3.distance(arbitrary1, arbitrary3));
    }

}