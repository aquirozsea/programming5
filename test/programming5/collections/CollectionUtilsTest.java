package programming5.collections;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

}