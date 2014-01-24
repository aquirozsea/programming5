/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.collections;

import java.util.Map.Entry;
import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
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
public class GeneralizedMapTest {

    GeneralizedMap<String, String> emptyMap;
    GeneralizedMap<String, String> filledMap;

    static MapKeyGenerator<String> fixedKeyGenerator;

    public GeneralizedMapTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        fixedKeyGenerator = new MapKeyGenerator<String>() {
            public String generateKey() {
                return "TEST_KEY";
            }
        };
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        emptyMap = new GeneralizedMap<String, String>();
        filledMap = new GeneralizedMap<String, String>();
        filledMap.put("name", "Tom Bombadil");
        filledMap.put("age", "1000");
        GeneralizedMap<String, String> powerMap = new GeneralizedMap<String, String>();
        powerMap.put("vision", "20/20");
        powerMap.put("hearing", "perfect");
        filledMap.putMap("powers", powerMap);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of safeGet method, of class GeneralizedMap.
     */
    @Test
    public void testSafeGet_2args_1() {
        // Safe on empty map and with null query
        assertEquals("defaultValue", emptyMap.safeGet(null, "defaultValue"));
        assertEquals("defaultValue", emptyMap.safeGet("anything", "defaultValue"));
        // Default value remains
        assertEquals("defaultValue", emptyMap.safeGet("anything", "otherValue"));
        // Correct retrieval
        assertEquals("1000", filledMap.safeGet("age", "defaultValue"));
        assertEquals("perfect", filledMap.safeGet("powers:hearing", "defaultValue"));
        // Safe on filled map and default value stays
        assertEquals("defaultValue", filledMap.safeGet("anything", "defaultValue"));
        assertEquals("defaultValue", filledMap.safeGet("powers:flight", "defaultValue"));
        assertEquals("defaultValue", filledMap.safeGet("powers:flight", "other value"));
        assertEquals("defaultValue", filledMap.safeGet("anything:anything", "defaultValue"));
        assertEquals("defaultValue", filledMap.safeGet("anything:anything", "other value"));
        // Correct after adding
        emptyMap.put("anything", "something");
        assertEquals("something", emptyMap.safeGet("anything", "defaultValue"));
        filledMap.put("anything", "something");
        filledMap.put("powers:flight", "no");
        filledMap.put("anything:anything", "another thing");
        assertEquals("something", filledMap.safeGet("anything", "defaultValue"));
        assertEquals("no", filledMap.safeGet("powers:flight", "defaultValue"));
        assertEquals("another thing", filledMap.safeGet("anything:anything", "defaultValue"));
    }

    /**
     * Test of safeGet method, of class GeneralizedMap.
     */
    @Test
    public void testSafeGet_List_GenericType() {
        List<String> q0 = CollectionUtils.listFromArray(new String[] {""});
        List<String> q1 = CollectionUtils.listFromArray(new String[] {"anything"});
        List<String> q2 = CollectionUtils.listFromArray(new String[] {"age"});
        List<String> q3 = CollectionUtils.listFromArray(new String[] {"powers", "hearing"});
        List<String> q4 = CollectionUtils.listFromArray(new String[] {"powers", "flight"});
        List<String> q5 = CollectionUtils.listFromArray(new String[] {"anything", "anything"});

        // Safe on empty map and with null query
        assertEquals("defaultValue", emptyMap.safeGetList(null, "defaultValue"));
        assertEquals("defaultValue", emptyMap.safeGetList(q0, "defaultValue"));
        assertEquals("defaultValue", emptyMap.safeGetList(q1, "defaultValue"));
        // Default value remains
        assertEquals("defaultValue", emptyMap.safeGetList(q1, "otherValue"));
        // Correct retrieval
        assertEquals("1000", filledMap.safeGetList(q2, "defaultValue"));
        assertEquals("perfect", filledMap.safeGetList(q3, "defaultValue"));
        // Safe on filled map and default value remains
        assertEquals("defaultValue", filledMap.safeGetList(q1, "defaultValue"));
        assertEquals("defaultValue", filledMap.safeGetList(q4, "defaultValue"));
        assertEquals("defaultValue", filledMap.safeGetList(q4, "other value"));
        assertEquals("defaultValue", filledMap.safeGetList(q5, "defaultValue"));
        assertEquals("defaultValue", filledMap.safeGetList(q5, "otherValue"));
        // Correct after adding
        emptyMap.put("anything", "something");
        assertEquals("something", emptyMap.safeGetList(q1, "defaultValue"));
        filledMap.put("anything", "something");
        filledMap.put("powers:flight", "no");
        filledMap.put("anything:anything", "another thing");
        assertEquals("something", filledMap.safeGetList(q1, "defaultValue"));
        assertEquals("no", filledMap.safeGetList(q4, "defaultValue"));
        assertEquals("another thing", filledMap.safeGetList(q5, "defaultValue"));
    }

    /**
     * Test of safeGet method, of class GeneralizedMap.
     */
    @Test
    public void testSafeGet_2args_2() {
        // Safe on empty map and with null query
        String[] nullArray = null;
        assertEquals("defaultValue", emptyMap.safeGetArray("defaultValue", nullArray));
        assertEquals("defaultValue", emptyMap.safeGetArray("defaultValue", new String[0]));
        assertEquals("defaultValue", emptyMap.safeGetArray("defaultValue", "anything"));
        // Default value remains
        assertEquals("defaultValue", emptyMap.safeGetArray("otherValue", "anything"));
        // Correct retrieval
        assertEquals("1000", filledMap.safeGetArray("defaultValue", "age"));
        assertEquals("perfect", filledMap.safeGetArray("defaultValue", "powers", "hearing"));
        assertEquals("perfect", filledMap.safeGetArray("defaultValue", "powers:hearing"));
        // Safe on filled map and default value remains
        assertEquals("defaultValue", filledMap.safeGetArray("defaultValue", "anything"));
        assertEquals("defaultValue", filledMap.safeGetArray("defaultValue", "powers", "flight"));
        assertEquals("defaultValue", filledMap.safeGetArray("other value", "powers", "flight"));
        assertEquals("defaultValue", filledMap.safeGetArray("defaultValue", "anything", "anything"));
        assertEquals("defaultValue", filledMap.safeGetArray("otherValue", "anything", "anything"));
        // Correct after adding
        emptyMap.put("anything", "something");
        assertEquals("something", emptyMap.safeGetArray("defaultValue", "anything"));
        filledMap.put("anything", "something");
        filledMap.put("powers:flight", "no");
        filledMap.put("anything:anything", "another thing");
        assertEquals("something", filledMap.safeGetArray("defaultValue", "anything"));
        assertEquals("no", filledMap.safeGetArray("defaultValue", "powers:flight"));
        assertEquals("another thing", filledMap.safeGetArray("defaultValue", "anything:anything"));
    }

    /**
     * Test of safePut method, of class GeneralizedMap.
     */
    @Test
    public void testSafePut_3args_1() {
        // Safe put with a null key should work (key should be null)
        try {
            assertNull(emptyMap.safePut(null, "nullValue", fixedKeyGenerator));
        }
        catch (Exception e) {
            fail("Safe put threw exception with null key");
        }
        // Safe put with an empty key should work
        try {
            assertEquals("", emptyMap.safePut("", "emptyValue", fixedKeyGenerator));
        }
        catch (Exception e) {
            fail("Safe put threw exception with empty string key");
        }
        // Safe put with regular and complex key
        assertEquals("one", emptyMap.safePut("one", "1", fixedKeyGenerator));
        assertEquals("one", emptyMap.safePut("embedded:one", "e1", fixedKeyGenerator));
        assertEquals("", emptyMap.safePut("embedded:", "master embedded", fixedKeyGenerator));
        // Retrieval of put elements
        assertEquals("nullValue", emptyMap.get(null));
        assertEquals("emptyValue", emptyMap.get(""));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("e1", emptyMap.get("embedded:one"));
        assertEquals("master embedded", emptyMap.get("embedded:"));
        // Invocation of key generator
        assertEquals(fixedKeyGenerator.generateKey(), emptyMap.safePut(null, "other null", fixedKeyGenerator));
        assertEquals(fixedKeyGenerator.generateKey(), filledMap.safePut("name", "This is not a name", fixedKeyGenerator));
        // Both still retrievable
        assertEquals("nullValue", emptyMap.get(null));
        assertEquals("other null", emptyMap.get(fixedKeyGenerator.generateKey()));
        assertEquals("Tom Bombadil", filledMap.get("name"));
        assertEquals("This is not a name", filledMap.get(fixedKeyGenerator.generateKey()));
        // End elements do not preclude map inserts
        assertEquals("type", emptyMap.safePut("one:type", "int", fixedKeyGenerator));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("int", emptyMap.get("one:type"));
    }

    /**
     * Test of safePut method, of class GeneralizedMap.
     */
    @Test
    public void testSafePut_Array() {
        // Safe put with no key should use the key generator (because it's not an explicit null key)
        try {
            assertEquals(fixedKeyGenerator.generateKey(), emptyMap.safePutArray("nullValue", fixedKeyGenerator));
        }
        catch (Exception e) {
            fail("Safe put threw exception with no key");
        }
        // Safe put with an empty key should work
        try {
            assertEquals("", emptyMap.safePutArray("emptyValue", fixedKeyGenerator, ""));
        }
        catch (Exception e) {
            fail("Safe put threw exception with empty string key");
        }
        // Safe put with regular and complex key
        assertEquals("one", emptyMap.safePutArray("1", fixedKeyGenerator, "one"));
        assertEquals("one", emptyMap.safePutArray("e1", fixedKeyGenerator, "embedded", "one"));
        assertEquals("", emptyMap.safePutArray("master embedded", fixedKeyGenerator, "embedded", ""));
        // Retrieval of put elements
        assertNull(emptyMap.get(null));
        assertEquals("nullValue", emptyMap.get(fixedKeyGenerator.generateKey()));
        assertEquals("emptyValue", emptyMap.get(""));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("e1", emptyMap.get("embedded:one"));
        assertEquals("master embedded", emptyMap.get("embedded:"));
        // Invocation of key generator
        assertEquals(fixedKeyGenerator.generateKey(), filledMap.safePutArray("This is not a name", fixedKeyGenerator, "name"));
        // Null still not assigned
        assertNull(emptyMap.get(null));
        // Generated and original keys are retrievable
        assertEquals("Tom Bombadil", filledMap.get("name"));
        assertEquals("This is not a name", filledMap.get(fixedKeyGenerator.generateKey()));
        // End elements do not preclude map inserts
        assertEquals("type", emptyMap.safePutArray("int", fixedKeyGenerator, "one:type"));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("int", emptyMap.get("one:type"));
    }

    /**
     * Test of safePutMap method, of class GeneralizedMap.
     */
    @Test
    public void testSafePutMap() {
        // Safe put with no key should use the key generator (because it's not an explicit null key)
        try {
            assertEquals(fixedKeyGenerator.generateKey(), emptyMap.safePutMap(new GeneralizedMap<String, String>(), fixedKeyGenerator));
        }
        catch (Exception e) {
            fail("Safe put threw exception with no key");
        }
        // Safe put with an empty key should work (also tests recursive put)
        try {
            assertEquals("", emptyMap.safePutMap(emptyMap, fixedKeyGenerator, ""));
        }
        catch (Exception e) {
            fail("Safe put threw exception with empty string key");
        }
        // Safe put with regular and complex key
        assertEquals("bombadil", emptyMap.safePutMap(filledMap, fixedKeyGenerator, "bombadil"));
        assertEquals("game", emptyMap.safePutMap(new GeneralizedMap<String, String>(), fixedKeyGenerator, "bombadil", "game"));
        assertEquals("", emptyMap.safePutMap(new GeneralizedMap<String, String>(), fixedKeyGenerator, "embedded", ""));
        // Retrieval of put elements
        assertNull(emptyMap.get(null));
        assertTrue(emptyMap.getMap(fixedKeyGenerator.generateKey()).isEmpty());
        assertEquals(emptyMap, emptyMap.getMap(""));
        assertEquals("Tom Bombadil", emptyMap.getArray("bombadil", "name"));
        assertEquals("20/20", emptyMap.get("bombadil:powers:vision"));
        assertTrue(emptyMap.getMap("embedded:").isEmpty());
        // Invocation of key generator
        assertEquals(fixedKeyGenerator.generateKey(), filledMap.safePutMap(new GeneralizedMap<String, String>(), fixedKeyGenerator, "powers"));
        // Null still not assigned
        assertNull(emptyMap.get(null));
        // Generated and original keys are retrievable
        assertEquals("Tom Bombadil", filledMap.get("name"));
        assertTrue(filledMap.getMap(fixedKeyGenerator.generateKey()).isEmpty());
        // End elements do not preclude map inserts
        assertEquals("quenya", filledMap.safePutMap(new GeneralizedMap<String, String>(), fixedKeyGenerator, "name", "quenya"));
        assertEquals("Tom Bombadil", filledMap.get("name"));
        assertTrue(filledMap.getMap("name:quenya").isEmpty());
    }

    /**
     * Test of safePutAll method, of class GeneralizedMap.
     */
    @Test
    public void testSafePutAll() {
        // Filling empty map (no clashes)
        assertTrue(emptyMap.safePutAll(filledMap, fixedKeyGenerator).isEmpty());
        // See clashes (and empty map now acts as filled map)
        GeneralizedMap<String, String> revPowers = new GeneralizedMap<String, String>();
        revPowers.put("magic", "ancient");
        revPowers.put("hearing", "extra-sensory");
        Map<String, String> clashes = emptyMap.getMap("powers").safePutAll(revPowers, fixedKeyGenerator);
        assertTrue(clashes.size() == 1);
        assertEquals(fixedKeyGenerator.generateKey(), clashes.get("hearing"));
        // Retrieving all powers
        assertEquals("20/20", emptyMap.getArray("powers", "vision"));
        assertEquals("perfect", emptyMap.getArray("powers", "hearing"));
        assertEquals("extra-sensory", emptyMap.getArray("powers", fixedKeyGenerator.generateKey()));
        assertEquals("ancient", emptyMap.get("powers:magic"));
    }

    /**
     * Test of randomPut method, of class GeneralizedMap.
     */
    @Test
    public void testRandomPut() {
        assertEquals(fixedKeyGenerator.generateKey(), emptyMap.randomPut("anyValue", fixedKeyGenerator));
    }

    /**
     * Test of randomPutMap method, of class GeneralizedMap.
     */
    @Test
    public void testRandomPutMap() {
        assertEquals(fixedKeyGenerator.generateKey(), emptyMap.randomPutMap(filledMap, fixedKeyGenerator));
    }

    /**
     * Test of size method, of class GeneralizedMap.
     */
    @Test
    public void testSize() {
        // Original sizes
        assertEquals(0, emptyMap.size());
        assertEquals(3, filledMap.size());
        // String elements and maps both contribute 1 to the size
        filledMap.put("one", "1");
        emptyMap.putMap("filledMap", filledMap);
        assertEquals(4, filledMap.size());
        assertEquals(1, emptyMap.size());
    }

    /**
     * Test of numPrimitiveElements method, of class GeneralizedMap.
     */
    @Test
    public void testNumPrimitiveElements() {
        // Original sizes
        assertEquals(0, emptyMap.numPrimitiveElements());
        assertEquals(4, filledMap.numPrimitiveElements());
        // Embedded maps only contribute their primitive elements to the size
        emptyMap.putMap("filledMap", filledMap);
        assertEquals(4, emptyMap.numPrimitiveElements());
        // Adding a primitive element
        filledMap.put("one", "1");
        assertEquals(5, filledMap.numPrimitiveElements());
        // New element also contributes to parent map
        assertEquals(5, emptyMap.numPrimitiveElements());
    }

    /**
     * Test of isEmpty method, of class GeneralizedMap.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(emptyMap.isEmpty());
        assertFalse(filledMap.isEmpty());
        // Empty embedded tables make map not empty
        emptyMap.putMap("empty", new GeneralizedMap<String, String>());
        assertFalse(emptyMap.isEmpty());
        // Make not empty with primitive elements
        assertTrue(emptyMap.getMap("empty").isEmpty());
        emptyMap.getMap("empty").put("primitive", "primitive");
        assertFalse(emptyMap.getMap("empty").isEmpty());
    }

    /**
     * Test of containsKey method, of class GeneralizedMap.
     */
    @Test
    public void testContainsKey() {
        assertFalse(emptyMap.containsKey("anything"));
        try {
            assertFalse(emptyMap.containsKey(this));
            assertFalse(filledMap.containsKey(emptyMap));
        }
        catch (Exception e) {
            fail("Contains key did not accept a random object type");
        }
        assertTrue(filledMap.containsKey("name"));
        assertTrue(filledMap.containsKey("vision"));
        // Full path key
        assertTrue(filledMap.containsKey("powers:vision"));
        assertFalse(filledMap.containsKey("other:vision"));
        // After adding
        emptyMap.putMap("bombadil", filledMap);
        assertTrue(emptyMap.containsKey("name"));
        assertTrue(emptyMap.containsKey("vision"));
        // Full path key
        assertFalse(emptyMap.containsKey("powers:vision"));
        assertTrue(emptyMap.containsKey("bombadil:powers:vision"));
        // Keys for maps work
        assertTrue(filledMap.containsKey("powers"));
        assertTrue(emptyMap.containsKey("powers"));
        assertTrue(emptyMap.containsKey("bombadil:powers"));
    }

    /**
     * Test of containsValue method, of class GeneralizedMap.
     */
    @Test
    public void testContainsValue() {
        assertFalse(emptyMap.containsValue("anything"));
        try {
            assertFalse(emptyMap.containsValue(this));
            assertFalse(filledMap.containsValue(emptyMap));
        }
        catch (Exception e) {
            fail("Contains value did not accept a random object type");
        }
        assertTrue(filledMap.containsValue("Tom Bombadil"));
        assertTrue(filledMap.containsValue("20/20"));
        assertFalse(filledMap.containsValue("x-ray"));
        // After adding
        emptyMap.putMap("bombadil", filledMap);
        assertTrue(emptyMap.containsValue("Tom Bombadil"));
        assertTrue(emptyMap.containsValue("20/20"));
        assertFalse(emptyMap.containsValue("x-ray"));
    }

    /**
     * Test of get method, of class GeneralizedMap.
     */
    @Test
    public void testGet_Object() {
        // Get on empty map and with null query
        assertNull(emptyMap.get(null));
        assertNull(emptyMap.get("anything"));
        // Correct retrieval
        assertEquals("1000", filledMap.get("age"));
        assertEquals("perfect", filledMap.get("powers:hearing"));
        // Get on filled map with not present query
        assertNull(filledMap.get("anything"));
        assertNull(filledMap.get("powers:flight"));
        assertNull(filledMap.get("anything:anything"));
        // Correct after adding
        emptyMap.put("anything", "something");
        assertEquals("something", emptyMap.get("anything"));
        filledMap.put("anything", "something");
        filledMap.put("powers:flight", "no");
        filledMap.put("anything:anything", "another thing");
        assertEquals("something", filledMap.get("anything"));
        assertEquals("no", filledMap.get("powers:flight"));
        assertEquals("another thing", filledMap.get("anything:anything"));
    }

    @Test
    public void testGet_Array() {
        // Get on empty map and with null query
        assertNull(emptyMap.getArray());
        assertNull(emptyMap.getArray("anything"));
        // Correct retrieval
        assertEquals("1000", filledMap.getArray("age"));
        assertEquals("perfect", filledMap.getArray("powers", "hearing"));
        // Get on filled map with not present query
        assertNull(filledMap.getArray("anything"));
        assertNull(filledMap.getArray("powers", "flight"));
        assertNull(filledMap.getArray("anything:anything"));
        // Correct after adding
        emptyMap.put("anything", "something");
        assertEquals("something", emptyMap.getArray("anything"));
        filledMap.put("anything", "something");
        filledMap.put("powers:flight", "no");
        filledMap.put("anything:anything", "another thing");
        assertEquals("something", filledMap.getArray("anything"));
        assertEquals("no", filledMap.getArray("powers", "flight"));
        assertEquals("another thing", filledMap.getArray("anything:anything"));
    }

    /**
     * Test of get method, of class GeneralizedMap.
     */
    @Test
    public void testGet_List() {
        List<String> q0 = CollectionUtils.listFromArray(new String[] {""});
        List<String> q1 = CollectionUtils.listFromArray(new String[] {"anything"});
        List<String> q2 = CollectionUtils.listFromArray(new String[] {"age"});
        List<String> q3 = CollectionUtils.listFromArray(new String[] {"powers", "hearing"});
        List<String> q4 = CollectionUtils.listFromArray(new String[] {"powers", "flight"});
        List<String> q5 = CollectionUtils.listFromArray(new String[] {"anything", "anything"});

        // Get on empty map and with null query
        assertNull(emptyMap.getList(new ArrayList<String>()));
        assertNull(emptyMap.getList(q0));
        assertNull(emptyMap.getList(q1));
        // Correct retrieval
        assertEquals("1000", filledMap.getList(q2));
        assertEquals("perfect", filledMap.getList(q3));
        // Get on filled map with not present query
        assertNull(filledMap.getList(q1));
        assertNull(filledMap.getList(q4));
        assertNull(filledMap.getList(q5));
        // Correct after adding
        emptyMap.put("anything", "something");
        assertEquals("something", emptyMap.getList(q1));
        filledMap.put("anything", "something");
        filledMap.put("powers:flight", "no");
        filledMap.put("anything:anything", "another thing");
        assertEquals("something", filledMap.getList(q1));
        assertEquals("no", filledMap.getList(q4));
        assertEquals("another thing", filledMap.getList(q5));
    }

    /**
     * Test of getMap method, of class GeneralizedMap.
     */
    @Test
    public void testGetMap_GenericType() {
        // Get map returns an empty map for non-existent key (TODO: Ok?)
        assertTrue(emptyMap.getMap("anything").isEmpty());
        assertTrue(filledMap.getMap().isEmpty());
        assertEquals("perfect", filledMap.getMap("powers").get("hearing"));
        // After embedding
        assertTrue(emptyMap.getMap("bombadil").isEmpty());
        emptyMap.putMap("bombadil", filledMap);
        assertEquals("1000", emptyMap.getMap("bombadil").get("age"));
        assertEquals("20/20", emptyMap.getMap("bombadil", "powers").get("vision"));
        assertEquals("perfect", emptyMap.getMap("bombadil:powers").get("hearing"));
    }

    /**
     * Test of getMap method, of class GeneralizedMap.
     */
    @Test
    public void testGetMap_List() {
        List<String> q0 = CollectionUtils.listFromArray(new String[] {""});
        List<String> q1 = CollectionUtils.listFromArray(new String[] {"anything"});
        List<String> q2 = CollectionUtils.listFromArray(new String[] {"powers"});
        List<String> q3 = CollectionUtils.listFromArray(new String[] {"bombadil"});
        List<String> q4 = CollectionUtils.listFromArray(new String[] {"bombadil", "powers"});
        List<String> q5 = CollectionUtils.listFromArray(new String[] {"bombadil:powers"});

        // Get map returns an empty map for non-existent key (TODO: Ok?)
        assertTrue(emptyMap.getMapList(q1).isEmpty());
        assertTrue(filledMap.getMapList(q0).isEmpty());
        assertEquals("perfect", filledMap.getMapList(q2).get("hearing"));
        // After embedding
        assertTrue(emptyMap.getMapList(q3).isEmpty());
        emptyMap.putMap("bombadil", filledMap);
        assertEquals("1000", emptyMap.getMapList(q3).get("age"));
        assertEquals("20/20", emptyMap.getMapList(q4).get("vision"));
        assertEquals("perfect", emptyMap.getMapList(q5).get("hearing"));
    }

    /**
     * Test of put method, of class GeneralizedMap.
     */
    @Test
    public void testPut_2args_1() {
        // Safe put with a null key should work (key should be null)
        try {
            assertNull(emptyMap.put(null, "nullValue"));
        }
        catch (Exception e) {
            fail("Safe put threw exception with null key");
        }
        // Safe put with an empty key should work
        try {
            assertNull(emptyMap.put("", "emptyValue"));
        }
        catch (Exception e) {
            fail("Safe put threw exception with empty string key");
        }
        // Safe put with regular and complex key
        assertNull(emptyMap.put("one", "1"));
        assertNull(emptyMap.put("embedded:one", "e1"));
        assertNull(emptyMap.put("embedded:", "master embedded"));
        // Retrieval of put elements
        assertEquals("nullValue", emptyMap.get(null));
        assertEquals("emptyValue", emptyMap.get(""));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("e1", emptyMap.get("embedded:one"));
        assertEquals("master embedded", emptyMap.get("embedded:"));
        // Invocation of key generator
        assertEquals("nullValue", emptyMap.put(null, "other null"));
        assertEquals("Tom Bombadil", filledMap.put("name", "This is not a name"));
        // Only new value retrievable
        assertEquals("other null", emptyMap.get(null));
        assertEquals("This is not a name", filledMap.get("name"));
        // End elements do not preclude map inserts
        assertNull(emptyMap.put("one:type", "int"));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("int", emptyMap.get("one:type"));
    }

    /**
     * Test of put method, of class GeneralizedMap.
     */
    @Test
    public void testPutArray_2args_2() {
        // Safe put with no key should use the key generator (because it's not an explicit null key)
        try {
            emptyMap.putArray("nullValue");
            fail("putArray did not throw exception with no key");
        }
        catch (Exception e) {
        }
        // Safe put with an empty key should work
        try {
            assertNull(emptyMap.putArray("emptyValue", ""));
        }
        catch (Exception e) {
            fail("Safe put threw exception with empty string key");
        }
        // Safe put with regular and complex key
        assertNull(emptyMap.putArray("1", "one"));
        assertNull(emptyMap.putArray("e1", "embedded", "one"));
        assertNull(emptyMap.putArray("master embedded", "embedded", ""));
        // Retrieval of put elements
        assertNull(emptyMap.get(null));
        assertEquals("emptyValue", emptyMap.get(""));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("e1", emptyMap.get("embedded:one"));
        assertEquals("master embedded", emptyMap.get("embedded:"));
        // Invocation of key generator
        assertEquals("Tom Bombadil", filledMap.putArray("This is not a name", "name"));
        // Null still not assigned
        assertNull(emptyMap.get(null));
        // Only new keys are retrievable
        assertEquals("This is not a name", filledMap.get("name"));
        // End elements do not preclude map inserts
        assertNull(emptyMap.putArray("int", "one:type"));
        assertEquals("1", emptyMap.get("one"));
        assertEquals("int", emptyMap.get("one:type"));
    }

    /**
     * Test of putMap method, of class GeneralizedMap.
     */
    @Test
    public void testPutMap_GenericType_GeneralizedMap() {
        // Put with no key should work
        try {
            assertNull(emptyMap.putMap(null, filledMap));
        }
        catch (Exception e) {
            fail("Safe put threw exception with no key");
        }
        // Safe put with an empty key should work (also tests recursive put)
        try {
            assertNull(emptyMap.putMap("", emptyMap));
        }
        catch (Exception e) {
            fail("Safe put threw exception with empty string key");
        }
        // Safe put with regular and complex key
        assertNull(emptyMap.putMap("bombadil", filledMap));
        assertNull(emptyMap.putMap("bombadil:game", new GeneralizedMap<String, String>()));
        assertNull(emptyMap.putMap("embedded:", new GeneralizedMap<String, String>()));
        // Retrieval of put elements
        assertEquals(filledMap, emptyMap.getMap());
        assertEquals(filledMap, emptyMap.getMap(null));
        assertEquals(emptyMap, emptyMap.getMap(""));
        assertEquals("Tom Bombadil", emptyMap.getArray("bombadil", "name"));
        assertEquals("20/20", emptyMap.get("bombadil:powers:vision"));
        assertTrue(emptyMap.getMap("embedded:").isEmpty());
        // Replaced map
        assertEquals("perfect", filledMap.putMap("powers", new GeneralizedMap<String, String>()).get("hearing"));
        // Only new keys are retrievable
        assertTrue(filledMap.getMap("powers").isEmpty());
        // End elements do not preclude map inserts
        assertNull(filledMap.putMap("name:quenya", new GeneralizedMap<String, String>()));
        assertEquals("Tom Bombadil", filledMap.get("name"));
        assertTrue(filledMap.getMap("name:quenya").isEmpty());
    }

    /**
     * Test of putMap method, of class GeneralizedMap.
     */
    @Test
    public void testPutMapArray_GeneralizedMap_GenericType() {
        // Put with no key should not work
        try {
            assertNull(emptyMap.putMapArray(filledMap));
            fail("putArray did not throw exception with no key");
        }
        catch (Exception e) {
        }
        // Safe put with an empty key should work (also tests recursive put)
        try {
            assertNull(emptyMap.putMapArray(emptyMap, ""));
        }
        catch (Exception e) {
            fail("Safe put threw exception with empty string key");
        }
        // Safe put with regular and complex key
        assertNull(emptyMap.putMapArray(filledMap, "bombadil"));
        assertNull(emptyMap.putMapArray(new GeneralizedMap<String, String>(), "bombadil", "game"));
        assertNull(emptyMap.putMapArray(new GeneralizedMap<String, String>(), "embedded", ""));
        // Retrieval of put elements
        assertEquals(emptyMap, emptyMap.getMap(""));
        assertEquals("Tom Bombadil", emptyMap.getArray("bombadil", "name"));
        assertEquals("20/20", emptyMap.get("bombadil:powers:vision"));
        assertTrue(emptyMap.getMap("embedded:").isEmpty());
        // Replaced map
        assertEquals("perfect", filledMap.putMapArray(new GeneralizedMap<String, String>(), "powers").get("hearing"));
        // Only new keys are retrievable
        assertTrue(filledMap.getMap("powers").isEmpty());
        // End elements do not preclude map inserts
        assertNull(filledMap.putMapArray(new GeneralizedMap<String, String>(), "name:quenya"));
        assertEquals("Tom Bombadil", filledMap.get("name"));
        assertTrue(filledMap.getMap("name", "quenya").isEmpty());
    }

    /**
     * Test of remove method, of class GeneralizedMap.
     */
    @Test
    public void testRemove() {
        // Cannot remove what is not there (no effect)
        assertNull(emptyMap.remove("anything"));
        assertTrue(emptyMap.isEmpty());
        // Element removal
        int pre_size = filledMap.numPrimitiveElements();
        assertEquals("1000", filledMap.remove("age"));
        assertFalse(filledMap.containsKey("age"));
        assertEquals(pre_size-1, filledMap.numPrimitiveElements());
        // Cannot remove twice
        pre_size = filledMap.numPrimitiveElements();
        assertNull(filledMap.remove("age"));
        assertEquals(pre_size, filledMap.numPrimitiveElements());
        // Does not remove maps
        assertNull(filledMap.remove("powers"));
        assertTrue(filledMap.containsKey("powers"));
        // Nested remove
        assertEquals("20/20", filledMap.remove("powers:vision"));
        assertFalse(filledMap.containsKey("vision"));
        assertEquals(pre_size-1, filledMap.numPrimitiveElements());
    }

    /**
     * Test of generalizedRemove method, of class GeneralizedMap.
     */
    @Test
    public void testGeneralizedRemove() {
        // Cannot remove what is not there (no effect)
        assertFalse(emptyMap.generalizedRemove("anything"));
        assertTrue(emptyMap.isEmpty());
        // Element removal
        int pre_size = filledMap.numPrimitiveElements();
        assertTrue(filledMap.generalizedRemove("age"));
        assertFalse(filledMap.containsKey("age"));
        assertEquals(pre_size-1, filledMap.numPrimitiveElements());
        // Cannot remove twice
        pre_size = filledMap.numPrimitiveElements();
        assertFalse(filledMap.generalizedRemove("age"));
        assertEquals(pre_size, filledMap.numPrimitiveElements());
        // Nested generalizedRemove
        assertTrue(filledMap.generalizedRemove("powers:vision"));
        assertFalse(filledMap.containsKey("vision"));
        assertEquals(pre_size-1, filledMap.numPrimitiveElements());
        // Removes maps
        assertTrue(filledMap.generalizedRemove("powers"));
        assertFalse(filledMap.containsKey("powers"));
        assertEquals(pre_size-2, filledMap.numPrimitiveElements());
        // Removal with null key
        emptyMap.putMap(null, filledMap);
        assertTrue(emptyMap.containsKey(null));
        assertTrue(emptyMap.generalizedRemove(null));
        assertFalse(emptyMap.containsKey(null));
        emptyMap.put(null, "nullValue");
        assertTrue(emptyMap.containsKey(null));
        assertTrue(emptyMap.generalizedRemove(null));
        assertFalse(emptyMap.containsKey(null));

    }

    /**
     * Test of putAll method, of class GeneralizedMap.
     */
    @Test
    public void testPutAll() {
        // Filling empty map (no clashes)
        emptyMap.putAll(filledMap);
        assertEquals(filledMap.size(), emptyMap.size());
        // See replace (and empty map now acts as filled map)
        assertEquals("perfect", emptyMap.get("powers:hearing"));
        GeneralizedMap<String, String> revPowers = new GeneralizedMap<String, String>();
        revPowers.put("magic", "ancient");
        revPowers.put("hearing", "extra-sensory");
        emptyMap.getMap("powers").putAll(revPowers);
        assertEquals("extra-sensory", emptyMap.get("powers:hearing"));
        // Retrieving all powers
        assertEquals("20/20", emptyMap.getArray("powers", "vision"));
        assertEquals("ancient", emptyMap.get("powers:magic"));
    }

    /**
     * Test of clear method, of class GeneralizedMap.
     */
    @Test
    public void testClear() {
        assertFalse(filledMap.isEmpty());
        filledMap.clear();
        assertTrue(filledMap.isEmpty());
        emptyMap.clear();
        assertTrue(emptyMap.isEmpty());
    }

    /**
     * Test of isMap method, of class GeneralizedMap.
     */
    @Test
    public void testIsMap() {
        assertFalse(emptyMap.isMap(null));
        assertFalse(filledMap.isMap("name"));
        assertTrue(filledMap.isMap("powers"));
        emptyMap.putMap("bombadil", filledMap);
        assertTrue(emptyMap.isMap("bombadil"));
        assertTrue(emptyMap.isMap("bombadil:powers"));
        // Make sure map is not changed on a negative query
        assertFalse(emptyMap.isMap("gandalf:powers"));
        assertFalse(emptyMap.containsKey("gandalf"));
    }

    /**
     * Test of keySet method, of class GeneralizedMap.
     */
    @Test
    public void testKeySet() {
        assertTrue(emptyMap.keySet().isEmpty());
        Set<String> filledKeys = filledMap.keySet();
        assertEquals(3, filledKeys.size());
        assertTrue(filledKeys.contains("name"));
        assertTrue(filledKeys.contains("age"));
        assertTrue(filledKeys.contains("powers"));
        assertFalse(filledKeys.contains("vision")); // TODO: Extend to make analogous to containsKey?
    }

    /**
     * Test of compoundKeySet method, of class GeneralizedMap.
     */
    @Test
    public void testCompoundKeySet() {
        assertTrue(emptyMap.compoundKeySet().isEmpty());
        Set<List<String>> filledKeys = filledMap.compoundKeySet();
        assertEquals(4, filledKeys.size());
        List<String> elements = CollectionUtils.listFromArray(new String[] {"Tom Bombadil", "1000", "perfect", "20/20"});
        for (List<String> key : filledKeys) {
            assertTrue(elements.contains(filledMap.getList(key)));
        }
    }

    /**
     * Test of values method, of class GeneralizedMap.
     */
    @Test
    public void testValues() {
        assertTrue(emptyMap.values().isEmpty());
        Collection<String> values = filledMap.values();
        assertEquals(4, values.size());
        List<String> elements = CollectionUtils.listFromArray(new String[] {"Tom Bombadil", "1000", "perfect", "20/20"});
        for (String value : values) {
            assertTrue(elements.contains(value));
        }
    }

    /**
     * Test of entrySet method, of class GeneralizedMap.
     */
    @Test
    public void testEntrySet() {
        assertTrue(emptyMap.entrySet().isEmpty());
        Set<Entry<String, String>> entries = filledMap.entrySet();
        assertEquals(4, entries.size());
        for (Entry<String, String> entry : entries) {
            if (entry.getValue().equals("Tom Bombadil")) {
                assertEquals("name", entry.getKey());
            }
            else if (entry.getValue().equals("1000")) {
                assertEquals("age", entry.getKey());
            }
            else if (entry.getValue().equals("perfect")) {
                assertEquals("powers", entry.getKey());
            }
            else if (entry.getValue().equals("20/20")) {
                assertEquals("powers", entry.getKey());
            }
            else {
                fail("Bad value entry in entrySet: " + entry.getKey() + "=" + entry.getValue());
            }
        }
    }

    /**
     * Test of compoundEntrySet method, of class GeneralizedMap.
     */
    @Test
    public void testCompoundEntrySet() {
        assertTrue(emptyMap.entrySet().isEmpty());
        Set<Entry<List<String>, String>> entries = filledMap.compoundEntrySet();
        assertEquals(4, entries.size());
        List<String> elements = CollectionUtils.listFromArray(new String[] {"Tom Bombadil", "1000", "perfect", "20/20"});
        for (Entry<List<String>, String> entry : entries) {
            if (entry.getValue().equals("Tom Bombadil")) {
                assertEquals("name", entry.getKey().get(0));
                assertEquals(1, entry.getKey().size());
            }
            else if (entry.getValue().equals("1000")) {
                assertEquals("age", entry.getKey().get(0));
                assertEquals(1, entry.getKey().size());
            }
            else if (entry.getValue().equals("perfect")) {
                assertEquals("powers", entry.getKey().get(0));
                assertEquals("hearing", entry.getKey().get(1));
                assertEquals(2, entry.getKey().size());
            }
            else if (entry.getValue().equals("20/20")) {
                assertEquals("powers", entry.getKey().get(0));
                assertEquals("vision", entry.getKey().get(1));
                assertEquals(2, entry.getKey().size());
            }
            else {
                fail("Bad value entry in entrySet: " + entry.getKey() + "=" + entry.getValue());
            }
        }
    }

}