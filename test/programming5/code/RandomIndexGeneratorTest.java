/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.code;

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
public class RandomIndexGeneratorTest {

    public RandomIndexGeneratorTest() {
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
     * Test of getRandomIndex method, of class RandomIndexGenerator.
     */
    @Test
    public void testGetRandomIndex() {
        RandomIndexGenerator single1 = new RandomIndexGenerator(new int[] {9, 9});
        assertEquals(9, single1.getRandomIndex());
        assertEquals(9, single1.getRandomIndex());
        RandomIndexGenerator single2 = new RandomIndexGenerator(new int[] {1001});
        assertEquals(1001, single2.getRandomIndex());
        assertEquals(1001, single2.getRandomIndex());
        RandomIndexGenerator inRange = new RandomIndexGenerator(new int[] {15}, new int[] {5, 10}, new int[] {20, 25});
        assertEquals(5, inRange.getElement(0));
        assertEquals(15, inRange.getElement(6));
        assertEquals(22, inRange.getElement(9));
        for (int i = 0; i < 100; i++) {
            int index = inRange.getRandomIndex();
            assertTrue((index >= 5 && index <= 10)
                    || (index == 15)
                    || (index >=20 && index <= 25)
            );
        }
        RandomIndexGenerator stringRange = new RandomIndexGenerator("20-25, 15, 5-10");
        assertEquals(5, stringRange.getElement(0));
        assertEquals(15, stringRange.getElement(6));
        assertEquals(22, stringRange.getElement(9));
        for (int i = 0; i < 100; i++) {
            int index = stringRange.getRandomIndex();
            assertTrue((index >= 5 && index <= 10)
                    || (index == 15)
                    || (index >=20 && index <= 25)
            );
        }
    }

}