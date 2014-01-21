/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.collections;

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

    public GeneralizedMapTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
        filledMap.putMap("self", filledMap);
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
    }

    /**
     * Test of safePut method, of class GeneralizedMap.
     */
    @Test
    public void testSafePut_3args_2() {
    }

    /**
     * Test of safePutMap method, of class GeneralizedMap.
     */
    @Test
    public void testSafePutMap() {
    }

    /**
     * Test of safePutAll method, of class GeneralizedMap.
     */
    @Test
    public void testSafePutAll() {
    }

    /**
     * Test of randomPut method, of class GeneralizedMap.
     */
    @Test
    public void testRandomPut() {
    }

    /**
     * Test of randomPutMap method, of class GeneralizedMap.
     */
    @Test
    public void testRandomPutMap() {
    }

    /**
     * Test of size method, of class GeneralizedMap.
     */
    @Test
    public void testSize() {
    }

    /**
     * Test of numPrimitiveElements method, of class GeneralizedMap.
     */
    @Test
    public void testNumPrimitiveElements() {
    }

    /**
     * Test of isEmpty method, of class GeneralizedMap.
     */
    @Test
    public void testIsEmpty() {
    }

    /**
     * Test of containsKey method, of class GeneralizedMap.
     */
    @Test
    public void testContainsKey() {
    }

    /**
     * Test of containsValue method, of class GeneralizedMap.
     */
    @Test
    public void testContainsValue() {
    }

    /**
     * Test of get method, of class GeneralizedMap.
     */
    @Test
    public void testGet_Object() {
    }

    /**
     * Test of get method, of class GeneralizedMap.
     */
    @Test
    public void testGet_List() {
    }

    /**
     * Test of getMap method, of class GeneralizedMap.
     */
    @Test
    public void testGetMap_GenericType() {
    }

    /**
     * Test of getMap method, of class GeneralizedMap.
     */
    @Test
    public void testGetMap_List() {
    }

    /**
     * Test of put method, of class GeneralizedMap.
     */
    @Test
    public void testPut_2args_1() {
    }

    /**
     * Test of put method, of class GeneralizedMap.
     */
    @Test
    public void testPut_2args_2() {
    }

    /**
     * Test of putMap method, of class GeneralizedMap.
     */
    @Test
    public void testPutMap_GenericType_GeneralizedMap() {
    }

    /**
     * Test of putMap method, of class GeneralizedMap.
     */
    @Test
    public void testPutMap_GeneralizedMap_GenericType() {
    }

    /**
     * Test of remove method, of class GeneralizedMap.
     */
    @Test
    public void testRemove() {
    }

    /**
     * Test of generalizedRemove method, of class GeneralizedMap.
     */
    @Test
    public void testGeneralizedRemove() {
    }

    /**
     * Test of putAll method, of class GeneralizedMap.
     */
    @Test
    public void testPutAll() {
    }

    /**
     * Test of clear method, of class GeneralizedMap.
     */
    @Test
    public void testClear() {
    }

    /**
     * Test of isMap method, of class GeneralizedMap.
     */
    @Test
    public void testIsMap() {
    }

    /**
     * Test of keySet method, of class GeneralizedMap.
     */
    @Test
    public void testKeySet() {
    }

    /**
     * Test of compoundKeySet method, of class GeneralizedMap.
     */
    @Test
    public void testCompoundKeySet() {
    }

    /**
     * Test of values method, of class GeneralizedMap.
     */
    @Test
    public void testValues() {
    }

    /**
     * Test of entrySet method, of class GeneralizedMap.
     */
    @Test
    public void testEntrySet() {
    }

    /**
     * Test of compoundEntrySet method, of class GeneralizedMap.
     */
    @Test
    public void testCompoundEntrySet() {
    }

}