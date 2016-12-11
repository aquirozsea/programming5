package programming5.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollectionUtilsTest {

    @Test
    public void toPrintableListTest() {
        List<String> singleList = Arrays.asList("one");
        List<String> multiList = Arrays.asList("one", "two", "three");
        assertEquals("one", CollectionUtils.toPrintableList(singleList));
        assertEquals("one", CollectionUtils.toPrintableList(singleList, "???"));
        assertEquals("one, two, three", CollectionUtils.toPrintableList(multiList));
        assertEquals("one::two::three", CollectionUtils.toPrintableList(multiList, "::"));
    }

    @Test
    public void synchronizeTest() {
        List<Integer> indices = Arrays.asList(1, 2, 3, 4);
        List<String> content = Arrays.asList("one", "two", "three", "four");
        List<Boolean> eval = new ArrayList<>();
        for (List tuple : CollectionUtils.synchronize(indices, content)) {
            int index = ((int) tuple.get(0));
            String word = ((String) tuple.get(1));
            eval.add(word.length() == index);
        }
        assertFalse(eval.get(0));
        assertFalse(eval.get(1));
        assertFalse(eval.get(2));
        assertTrue(eval.get(3));
    }

    @Test
    public void mapFromStringTest() {
        Map<String, String> testMap = CollectionUtils.mapFromString("key1:value1;key2: value2; key3 : value3");
        assertEquals(3, testMap.size());
        assertEquals("value1", testMap.get("key1"));
        assertEquals("value2", testMap.get("key2"));
        assertEquals("value3", testMap.get("key3"));
    }

    @Test
    public void mapFromPairsTest() {
        Map<String, String> testMap = CollectionUtils.mapFromPairs("key1:value1", "key2: value2", "key3 : value3");
        assertEquals(3, testMap.size());
        assertEquals("value1", testMap.get("key1"));
        assertEquals("value2", testMap.get("key2"));
        assertEquals("value3", testMap.get("key3"));
    }

    @Test
    public void listFromElementTest() {
        List<String> list = CollectionUtils.listFromElement("first");
        assertEquals(1, list.size());
        assertTrue(list.contains("first"));
    }

    @Test
    public void createArrayListTest() {
        List<String> list = CollectionUtils.arrayList("one", "two", "three");
        assertEquals(3, list.size());
        List<Integer> other = CollectionUtils.arrayList(1, 2, 3, 5, 6);
        assertEquals(5, other.size());
    }

}