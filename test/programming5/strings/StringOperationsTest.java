/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.strings;

import java.util.List;
import programming5.io.Debug;
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
        Debug.enable(StringOperations.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Debug.disable(StringOperations.class);
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
     * Test of newDecodePattern method, of class StringOperations.
     */
    @Test
    public void testNewDecodePattern() {
        // Should decode the same as original decode pattern method
        Map<String, Object> decoded = StringOperations.newDecodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date> INFO: id=<id>, name=\"<name>\"");
        assertEquals("11/14/2003", decoded.get("date"));
        assertEquals("1234", decoded.get("id"));
        assertEquals("Something", decoded.get("name"));
        // Should allow for specifying inner regular expressions for labels
        decoded = StringOperations.newDecodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date:\\d{2}/\\d{2}/\\d{4}> INFO: <idpart:id=\\d+>, name=\"<name>\"");
        assertEquals("11/14/2003", decoded.get("date"));
        assertEquals("id=1234", decoded.get("idpart"));
        assertEquals("Something", decoded.get("name"));
        // Should allow for specifying java regular expressions and accessing by group number (-1)
        decoded = StringOperations.newDecodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "(\\d{2}/\\d{2}/\\d{4}) INFO: id=(\\d+), name=\"(\\w+)\"");
        assertEquals("11/14/2003", decoded.get("0"));
        assertEquals("1234", decoded.get("1"));
        assertEquals("Something", decoded.get("2"));
        // Should allow for list expressions
        decoded = StringOperations.newDecodePattern("11/14/2003 INFO: id=1234, name=\"Something\"", "<date:\\d{2}/\\d{2}/\\d{4}> INFO: <taglist:[],(\\w+=[\\w\"]+)>");
        assertEquals("11/14/2003", decoded.get("date"));
        assertEquals("id=1234", decoded.get("taglist:0"));
        assertEquals("name=\"Something\"", decoded.get("taglist:1"));
        assertTrue((decoded.get("taglist") instanceof List));
        assertEquals(2, ((List) decoded.get("taglist")).size());
        // With default
        decoded = StringOperations.newDecodePattern("List 3: One, Two, Three, 4,%, - *** GOOD", "List <listid:[]>: <list:[]> \\*\\*\\* <status>");
        assertEquals("3", decoded.get("listid:0"));   // List of one
        assertEquals("Two", decoded.get("list:1"));
        assertEquals("%", decoded.get("list:4"));
        List<String> list = (List<String>) decoded.get("list");
        assertEquals(decoded.get("list:0"), list.get(0));
        assertEquals(decoded.get("list:3"), list.get(3));
        assertEquals("-", decoded.get("list:" + Integer.toString(list.size()-1)));
        assertEquals("GOOD", decoded.get("status"));
        decoded = StringOperations.newDecodePattern("best(field3.rate(1&&10))", "<aggregator:\\w+(\\.\\w+)?>\\(<fieldselector:[^\\(\\)]+|([^\\(\\)]+\\([^\\(\\)]*\\))>\\)");
        assertEquals("best", decoded.get("aggregator"));
        assertEquals("field3.rate(1&&10)", decoded.get("fieldselector"));
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