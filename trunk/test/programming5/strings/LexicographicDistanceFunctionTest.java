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
public class LexicographicDistanceFunctionTest {

    public LexicographicDistanceFunctionTest() {
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
     * Test of distance method, of class LexicographicDistanceFunction.
     */
    @Test
    public void testDistance() {
        LexicographicDistanceFunction ldf = new LexicographicDistanceFunction();
        assertEquals(0, (int) ldf.distance("equal", "equal"));
        assertEquals(Math.abs("monkey".compareTo("gorilla")), (int) ldf.distance("monkey", "gorilla"));
        assertEquals(Math.abs("long".compareTo("longer")), (int) ldf.distance("long", "longer"));
        assertEquals(Math.abs("weird".compareTo("wierd")), (int) ldf.distance("weird", "wierd"));
        assertEquals(Math.abs("n6mb3rs t00".compareTo("4ls0 n6mb3rs")), (int) ldf.distance("n6mb3rs t00", "4ls0 n6mb3rs"));
    }

}