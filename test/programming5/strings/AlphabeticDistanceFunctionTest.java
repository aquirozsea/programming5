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

    public AlphabeticDistanceFunctionTest() {
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
     * Test of distance method, of class AlphabeticDistanceFunction.
     */
    @Test
    public void testDistance() {
        AlphabeticDistanceFunction adf = new AlphabeticDistanceFunction();
        assertEquals(0, (int) adf.distance("equal", "equal"));
        assertEquals(6, (int) adf.distance("monkey", "gorilla"));
        assertEquals(0.0005, adf.distance("long", "longer"), 0.00001);
        assertEquals(0.4, adf.distance("weird", "wierd"), 0.01);
    }

}