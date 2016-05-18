package programming5.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountingTableTests {

    @Test
    public void mergeTest() {
        CountingTable<String> table1 = new CountingTable<>();
        table1.increaseCount("only1");
        table1.increaseCount("1and2");
        CountingTable<String> table2 = new CountingTable<>();
        table2.increaseCount("1and2");
        table2.increaseCount("only2");
        table1.mergeWith(table2);
        assertEquals(1, table1.getCount("only1"));
        assertEquals(2, table1.getCount("1and2"));
        assertEquals(1, table1.getCount("only2"));
        assertEquals(1, table2.getCount("1and2"));
    }

}