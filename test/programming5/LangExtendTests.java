package programming5;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.*;
import static programming5.code.LangExtend.safeOp;
import static programming5.code.LangExtend.safeTest;

public class LangExtendTests {

    @Test
    public void testSafeTest() {
        Predicate<String> test = (string -> string.equals("value"));
        String s = null;
        assertFalse(safeTest(s, test));
        s = "value";
        assertTrue(safeTest(s, test));
        s = "not value";
        assertFalse(safeTest(s, test));
    }

    @Test
    public void testSafeOp() {
        Function<String, String> toUpper = (string -> string.toUpperCase());
        String s = null;
        assertTrue(safeOp(s, "", toUpper).isEmpty());
        s = "value";
        assertEquals("VALUE", safeOp(s, "", toUpper));
    }

}