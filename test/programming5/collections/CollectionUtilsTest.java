package programming5.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
    public void synchrnizeTest() {
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

}