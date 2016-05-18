package programming5.collections;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MapBuilderTest {

    @Test
    public void test() {
        assertEquals(5, MapBuilder.from("key", 5).get().get("key").intValue());
        assertEquals(3, MapBuilder.from(1, 2).put(2, 3).put(3, 4).get().size());

        assertEquals("value", MapBuilder.in(new HashMap<Integer, String>()).put(1, "value").get().get(1));
    }

}