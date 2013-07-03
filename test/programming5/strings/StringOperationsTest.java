/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import java.util.Map;
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
public class StringOperationsTest {

    public StringOperationsTest() {
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
     * Test of extract method, of class StringOperations.
     */
    @Test
    public void testExtract() {
    }

    /**
     * Test of extractFirst method, of class StringOperations.
     */
    @Test
    public void testExtractFirst() {
    }

    /**
     * Test of findRegex method, of class StringOperations.
     */
    @Test
    public void testFindRegex() {
    }

    /**
     * Test of findFirstRegex method, of class StringOperations.
     */
    @Test
    public void testFindFirstRegex() {
    }

    /**
     * Test of extractAndReplace method, of class StringOperations.
     */
    @Test
    public void testExtractAndReplace() {
    }

    /**
     * Test of decodePattern method, of class StringOperations.
     */
    @Test
    public void testDecodePattern() {
        Map<String, String> decoded = StringOperations.decodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date> INFO: id=<id>, name=\"<name>\"");
        assertEquals("11/14/2003", decoded.get("date"));
        assertEquals("1234", decoded.get("id"));
        assertEquals("Something", decoded.get("name"));
    }

    /**
     * Test of nestedDecodePattern method, of class StringOperations.
     */
    @Test
    public void testNestedDecodePattern() {
        String string = "Building jar: /home/andresqh/NetBeansProjects/Programming5/dist/Programming5.jar";
        String pattern1 = "Building <what>: <path>";
        Map<String, String> noNesting = StringOperations.nestedDecodePattern(string, pattern1);
        assertEquals("jar", noNesting.get("what"));
        assertEquals("/home/andresqh/NetBeansProjects/Programming5/dist/Programming5.jar", noNesting.get("path"));
        String pattern2 = "path:/home/<user>/NetBeansProjects/<project>/dist/<jar>";
        Map<String, String> nested1 = StringOperations.nestedDecodePattern(string, pattern1, pattern2);
        assertEquals("jar", nested1.get("what"));
        assertEquals("andresqh", nested1.get("user"));
        assertEquals("Programming5", nested1.get("project"));
        assertEquals("Programming5.jar", nested1.get("jar"));
        assertFalse(nested1.containsKey("path"));
        String pattern3 = "jar:<filename>\\.jar";
        String pattern4 = "what:j<letter>r";
        Map<String, String> nested2 = StringOperations.nestedDecodePattern(string, pattern1, pattern2, pattern3, pattern4);
        assertEquals("andresqh", nested2.get("user"));
        assertEquals("Programming5", nested2.get("project"));
        assertEquals("Programming5", nested2.get("filename"));
        assertEquals("a", nested2.get("letter"));
        assertFalse(nested2.containsKey("path"));
        assertFalse(nested2.containsKey("jar"));
        assertFalse(nested2.containsKey("what"));
    }

    /**
     * Test of addToList method, of class StringOperations.
     */
    @Test
    public void testAddToList_3args() {
    }

    /**
     * Test of addToList method, of class StringOperations.
     */
    @Test
    public void testAddToList_String_String() {
    }

    /**
     * Test of toList method, of class StringOperations.
     */
    @Test
    public void testToList_StringArr() {
    }

    /**
     * Test of toList method, of class StringOperations.
     */
    @Test
    public void testToList_StringArr_String() {
    }

    /**
     * Test of increment method, of class StringOperations.
     */
    @Test
    public void testIncrement() {
    }

    /**
     * Test of decrement method, of class StringOperations.
     */
    @Test
    public void testDecrement() {
    }

    /**
     * Test of incrementBy method, of class StringOperations.
     */
    @Test
    public void testIncrementBy_String_int() {
    }

    /**
     * Test of incrementBy method, of class StringOperations.
     */
    @Test
    public void testIncrementBy_String_long() {
    }

    /**
     * Test of incrementBy method, of class StringOperations.
     */
    @Test
    public void testIncrementBy_String_float() {
    }

    /**
     * Test of incrementBy method, of class StringOperations.
     */
    @Test
    public void testIncrementBy_String_double() {
    }

    /**
     * Test of incrementBy method, of class StringOperations.
     */
    @Test
    public void testIncrementBy_String_String() {
    }

    /**
     * Test of insert method, of class StringOperations.
     */
    @Test
    public void testInsert_3args_1() {
    }

    /**
     * Test of insert method, of class StringOperations.
     */
    @Test
    public void testInsert_3args_2() {
    }

    /**
     * Test of parseInt method, of class StringOperations.
     */
    @Test
    public void testParseInt() {
    }

    /**
     * Test of isAlnum method, of class StringOperations.
     */
    @Test
    public void testIsAlnum() {
    }

    /**
     * Test of isIdentifier method, of class StringOperations.
     */
    @Test
    public void testIsIdentifier() {
    }

    /**
     * Test of isNumeric method, of class StringOperations.
     */
    @Test
    public void testIsNumeric() {
    }

}