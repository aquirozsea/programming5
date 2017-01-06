package programming5.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConfigurableHashMapTest {

    @Test
    public void testRegular() {
        Map<String, Integer> map = MapBuilder.in(new ConfigurableHashMap<String, Integer>())
                .put("one", 1)
                .put("three", 3)
                .get();
        assertEquals(1, map.get("one").intValue());
        map.put("one", 0);
        assertEquals(0, map.get("one").intValue());
        assertEquals(3, map.getOrDefault("three", 0).intValue());
        assertEquals(2, map.getOrDefault("two", 2).intValue());
    }

    @Test
    public void testPutOnce() {
        Map<String, Integer> map = MapBuilder.in(new ConfigurableHashMap<String, Integer>(ConfigurableHashMap.Settings.PUT_ONCE))
                .put("one", 1)
                .get();
        map.put("one", 0);
        map.put("two", 2);
        assertEquals(1, map.get("one").intValue());
        assertEquals(2, map.get("two").intValue());
        assertEquals(3, map.getOrDefault("three", 3).intValue());
        assertEquals(3, map.get("three").intValue());
    }

    @Test
    public void testCombination() {
        Map<String, List<String>> map = MapBuilder.in(new ConfigurableHashMap<String, List<String>>(ConfigurableHashMap.Settings.PUT_ONCE, ConfigurableHashMap.Settings.GET_AND_REMOVE))
                .put("one", Arrays.asList("1", "first", "uno"))
                .put("three", Arrays.asList("3", "third", "tres"))
                .get();
        map.put("one", new ArrayList<>());
        assertEquals(3, map.get("one").size());
        assertEquals(3, map.getOrDefault("two", Arrays.asList("2", "second", "dos")).size());
        assertNull(map.get("two"));
        assertEquals("third", map.get("three").get(1));
        assertEquals(0, map.getOrDefault("three", new ArrayList<>()).size());
        assertNull(map.get("three"));
    }

    @Test
    public void testCustom() {
        Map<Integer, String> customMap = MapBuilder.in(new ConfigurableHashMap<Integer, String>())
                .put(1, "one")
                .put(10, "ten")
                .get();
        ((ConfigurableHashMap) customMap).setGetter(key -> customMap.get(key).toUpperCase());
        ((ConfigurableHashMap<Integer, String>) customMap).setPutter(((key, value) -> {
            if (customMap.containsKey(key)) {
                key = Collections.max(customMap.keySet()) + 1;
            }
            return customMap.put(key, value);
        }));
        assertEquals("ONE", customMap.get(1));
        MapBuilder.in(customMap).put(5, "five").put(10, "max");
        assertEquals(4, customMap.size());
        assertEquals("MAX", customMap.get(11));
    }

}