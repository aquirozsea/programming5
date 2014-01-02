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
import programming5.math.DistanceFunction;
import static org.junit.Assert.*;

/**
 *
 * @author andresqh
 */
public class KeyboardCharDistanceFunctionTest {

    static DistanceFunction<Character> df;

    public KeyboardCharDistanceFunctionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        df = new KeyboardCharDistanceFunction();
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
        assertEquals(1, (int) df.distance('a', 'b'));
        assertEquals(1, (int) df.distance('X', 'y'));
        assertEquals(0.2, df.distance('a', 'z'), 0.0001);
        assertEquals(0.2, df.distance('a', 'A'), 0.0001);
        assertEquals(0.2, df.distance('q', '`'), 0.0001);
        assertEquals(0.4, df.distance('a', 'c'), 0.0001);
        assertEquals(0.4, df.distance('C', 'A'), 0.0001);
        assertEquals(0.4, df.distance('D', '$'), 0.0001);
        assertEquals(0.8, df.distance('q', 'r'), 0.0001);
        assertEquals(0.8, df.distance('a', 'D'), 0.0001);
        assertEquals(0.8, df.distance('u', ' '), 0.0001);
    }

    /**
     * Test equal strings always have distance zero
     */
    @Test
    public void testEquals() {
        assertEquals(0, df.distance('e', 'e'), 1e-5);
        assertEquals(0, df.distance(' ', ' '), 1e-5);
        assertFalse(df.distance('a', 'A') == 0);
    }

    /**
     * A distance function must always be non-negative
     */
    @Test
    public void testNonNegative() {
        assertTrue("Distance must always be non-negative", df.distance('s', 's') >= 0);
        assertTrue("Distance must always be non-negative", df.distance('o', '*') >= 0);
        assertTrue("Distance must always be non-negative", df.distance('B', '9') >= 0);
    }

    /**
     * A distance function must be commutative
     */
    @Test
    public void testCommutative() {
        assertEquals(df.distance('1', '2'), df.distance('2', '1'), 0);
    }

    /**
     * A distance function must obey the triangle inequality
     */
    @Test
    public void testTriangle() {
        assertTrue("Distance must obey the triangle inequality", df.distance('a', 'b') + df.distance('b', 'c') >= df.distance('a', 'c'));
    }

}