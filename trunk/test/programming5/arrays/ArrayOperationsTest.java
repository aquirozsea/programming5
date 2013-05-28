/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.arrays;

import programming5.math.DistanceFunction;
import programming5.strings.LexicographicDistanceFunction;
import programming5.strings.KeyValuePairMatcher;
import java.util.Arrays;
import java.util.Random;
import programming5.strings.NumericStringComparator;
import java.math.BigInteger;
import programming5.code.ReplicableObject;
import java.util.Calendar;
import java.util.Date;
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
public class ArrayOperationsTest {

    NumericStringComparator comparator = new NumericStringComparator();
    Random random = new Random(System.currentTimeMillis());

    public ArrayOperationsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_byteArr() {
        byte[] toReplicate = null;
        assertNull(ArrayOperations.replicate(toReplicate));
        toReplicate = new byte[0];
        byte[] replica = ArrayOperations.replicate(toReplicate);
        assertEquals(0, replica.length);
        toReplicate = new byte[] {'a'};
        replica = ArrayOperations.replicate(toReplicate);
        assertNotSame(replica, toReplicate);
        assertArrayEquals(new byte[] {'a'}, replica);
        toReplicate = new byte[] {'a', 'b', 'c'};
        replica = ArrayOperations.replicate(toReplicate);
        assertArrayEquals(new byte[] {'a', 'b', 'c'}, replica);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_intArr() {
        int[] toReplicate = null;
        assertNull(ArrayOperations.replicate(toReplicate));
        toReplicate = new int[0];
        int[] replica = ArrayOperations.replicate(toReplicate);
        assertEquals(0, replica.length);
        toReplicate = new int[] {1};
        replica = ArrayOperations.replicate(toReplicate);
        assertNotSame(replica, toReplicate);
        assertArrayEquals(new int[] {1}, replica);
        toReplicate = new int[] {1, 2, 3};
        replica = ArrayOperations.replicate(toReplicate);
        assertArrayEquals(new int[] {1, 2, 3}, replica);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_floatArr() {
        float[] toReplicate = null;
        assertNull(ArrayOperations.replicate(toReplicate));
        toReplicate = new float[0];
        float[] replica = ArrayOperations.replicate(toReplicate);
        assertEquals(0, replica.length);
        toReplicate = new float[] {1.0f};
        replica = ArrayOperations.replicate(toReplicate);
        assertNotSame(replica, toReplicate);
        assertEquals(1.0f, replica[0], 0);
        toReplicate = new float[] {1.0f, 1.999f, 3.5f};
        replica = ArrayOperations.replicate(toReplicate);
        assertEquals(1.0f, replica[0], 0);
        assertEquals(1.999f, replica[1], 0);
        assertEquals(3.5f, replica[2], 0);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_doubleArr() {
        double[] toReplicate = null;
        assertNull(ArrayOperations.replicate(toReplicate));
        toReplicate = new double[0];
        double[] replica = ArrayOperations.replicate(toReplicate);
        assertEquals(0, replica.length);
        toReplicate = new double[] {1.0d};
        replica = ArrayOperations.replicate(toReplicate);
        assertNotSame(replica, toReplicate);
        assertEquals(1.0d, replica[0], 0);
        toReplicate = new double[] {1.0d, 1.999d, 3.5d};
        replica = ArrayOperations.replicate(toReplicate);
        assertEquals(1.0d, replica[0], 0);
        assertEquals(1.999d, replica[1], 0);
        assertEquals(3.5d, replica[2], 0);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_charArr() {
        char[] toReplicate = null;
        assertNull(ArrayOperations.replicate(toReplicate));
        toReplicate = new char[0];
        char[] replica = ArrayOperations.replicate(toReplicate);
        assertEquals(0, replica.length);
        toReplicate = new char[] {'a'};
        replica = ArrayOperations.replicate(toReplicate);
        assertNotSame(replica, toReplicate);
        assertArrayEquals(new char[] {'a'}, replica);
        toReplicate = new char[] {'a', 'b', 'c'};
        replica = ArrayOperations.replicate(toReplicate);
        assertArrayEquals(new char[] {'a', 'b', 'c'}, replica);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_StringArr() {
        String[] toReplicate = null;
        assertNull(ArrayOperations.replicate(toReplicate));
        toReplicate = new String[0];
        String[] replica = ArrayOperations.replicate(toReplicate);
        assertEquals(0, replica.length);
        toReplicate = new String[] {"a"};
        replica = ArrayOperations.replicate(toReplicate);
        assertNotSame(replica, toReplicate);
        assertArrayEquals(new String[] {"a"}, replica);
        toReplicate = new String[] {"a", "b", "c"};
        replica = ArrayOperations.replicate(toReplicate);
        assertArrayEquals(new String[] {"a", "b", "c"}, replica);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_longArr() {
        long[] toReplicate = null;
        assertNull(ArrayOperations.replicate(toReplicate));
        toReplicate = new long[0];
        long[] replica = ArrayOperations.replicate(toReplicate);
        assertEquals(0, replica.length);
        toReplicate = new long[] {1};
        replica = ArrayOperations.replicate(toReplicate);
        assertNotSame(replica, toReplicate);
        assertArrayEquals(new long[] {1}, replica);
        toReplicate = new long[] {1, 2, 3};
        replica = ArrayOperations.replicate(toReplicate);
        assertArrayEquals(new long[] {1, 2, 3}, replica);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_GenericType_GenericType() {
        Date[] toReplicate = new Date[0];
        Date[] replica = new Date[0];
        ArrayOperations.replicate(toReplicate, replica);
        assertEquals(0, replica.length);
        Date now = Calendar.getInstance().getTime();
        toReplicate = new Date[] {now};
        replica = new Date[1];
        ArrayOperations.replicate(toReplicate, replica);
        assertNotSame(replica, toReplicate);
        assertArrayEquals(new Date[] {now}, replica);
        toReplicate = new Date[] {now, now, now};
        replica = new Date[3];
        ArrayOperations.replicate(toReplicate, replica);
        assertSame(toReplicate[1], replica[1]);
    }

    /**
     * Test of replicate method, of class ArrayOperations.
     */
    @Test
    public void testReplicate_ReplicableType() {
        ReplicableObject[] toReplicate = new ReplicableObject[0];
        ReplicableObject[] replica = new ReplicableObject[0];
        ArrayOperations.replicate(toReplicate, replica);
        assertEquals(0, replica.length);
        ReplicableObject one = new ReplicableObject(1);
        toReplicate = new ReplicableObject[] {one};
        replica = new ReplicableObject[1];
        ArrayOperations.replicate(toReplicate, replica);
        assertNotSame(replica, toReplicate);
        assertNotSame(replica[0], toReplicate[0]);
        assertArrayEquals(new ReplicableObject[] {one}, replica);
        ReplicableObject two = new ReplicableObject(2);
        ReplicableObject three = new ReplicableObject(3);
        toReplicate = new ReplicableObject[] {one, two, three};
        replica = new ReplicableObject[3];
        ArrayOperations.replicate(toReplicate, replica);
        two.setField(20);
        assertEquals(20, toReplicate[1].getField());
        assertEquals(2, replica[1].getField());
    }

    /**
     * Test of sum method, of class ArrayOperations.
     */
    @Test
    public void testSum_intArr() {
        int[] array = new int[0];
        assertEquals(0, ArrayOperations.sum(array));
        array = new int[] {1, 2, 3};
        assertEquals(6, ArrayOperations.sum(array));
        array = new int[] {1, 2, -3};
        assertEquals(0, ArrayOperations.sum(array));
    }

    /**
     * Test of sum method, of class ArrayOperations. Testing a precision of 4 decimal digits
     */
    @Test
    public void testSum_floatArr() {
        float[] array = new float[0];
        assertEquals(0, ArrayOperations.sum(array), 0);
        array = new float[] {1.0f, 1.9999f, 3.5f};
        assertEquals(6.4999f, ArrayOperations.sum(array), 0.00001);
        array[0] = -array[0];
        assertEquals(4.4999f, ArrayOperations.sum(array), 0.00001);
    }

    /**
     * Test of sum method, of class ArrayOperations. Testing a precision of eight decimal digits.
     */
    @Test
    public void testSum_doubleArr() {
        double[] array = new double[0];
        assertEquals(0, ArrayOperations.sum(array), 0);
        array = new double[] {1.0d, 1.99999999d, 3.5d};
        assertEquals(6.49999999d, ArrayOperations.sum(array), 0.000000001d);
        array[2] = -array[2];
        assertEquals(-0.50000001d, ArrayOperations.sum(array), 0.000000001d);
    }

    /**
     * Test of product method, of class ArrayOperations.
     */
    @Test
    public void testProduct_intArr() {
        int[] array = new int[0];
        assertEquals(0, ArrayOperations.product(array));
        array = new int[] {1, 2, 3};
        assertEquals(6l, ArrayOperations.product(array));
        array = new int[] {2, Integer.MAX_VALUE};
        assertEquals((2l * Integer.MAX_VALUE), ArrayOperations.product(array));
    }

    /**
     * Test of bigProduct method, of class ArrayOperations.
     */
    @Test
    public void testBigProduct_intArr() {
        int[] array = new int[0];
        assertEquals(BigInteger.ZERO, ArrayOperations.bigProduct(array));
        array = new int[] {1, 2, 3};
        assertEquals(BigInteger.valueOf(6l), ArrayOperations.bigProduct(array));
        array = new int[] {2, Integer.MAX_VALUE};
        assertEquals(BigInteger.valueOf(2).multiply(BigInteger.valueOf(Integer.MAX_VALUE)), ArrayOperations.bigProduct(array));
    }

    /**
     * Test of bigProduct method, of class ArrayOperations.
     */
    @Test
    public void testBigProduct_longArr() {
        long[] array = new long[0];
        assertEquals(BigInteger.ZERO, ArrayOperations.bigProduct(array));
        array = new long[] {1, 2, 3};
        assertEquals(BigInteger.valueOf(6l), ArrayOperations.bigProduct(array));
        array = new long[] {2, Long.MAX_VALUE};
        assertEquals(BigInteger.valueOf(2).multiply(BigInteger.valueOf(Long.MAX_VALUE)), ArrayOperations.bigProduct(array));
    }

    /**
     * Test of product method, of class ArrayOperations.
     */
    @Test
    public void testProduct_floatArr() {
        float[] array = new float[0];
        assertEquals(0, ArrayOperations.product(array), 0);
        array = new float[] {1.0f, 0.0f};
        assertEquals(0.0f, ArrayOperations.product(array), 0.00001);
        array = new float[] {2, Float.MAX_VALUE};
        assertEquals((2d * Float.MAX_VALUE), ArrayOperations.product(array), 0.000000001);
    }

    /**
     * Test of product method, of class ArrayOperations.
     */
    @Test
    public void testProduct_doubleArr() {
        double[] array = new double[0];
        assertEquals(0, ArrayOperations.product(array), 0);
        array = new double[] {1.0d, 0.0d};
        assertEquals(0.0d, ArrayOperations.product(array), 0.00001);
        array = new double[] {2, Float.MAX_VALUE};
        assertEquals((2d * Float.MAX_VALUE), ArrayOperations.product(array), 0.000000001);
    }

    /**
     * Test of avg method, of class ArrayOperations.
     */
    @Test
    public void testAvg_intArr() {
        int[] array = new int[0];
        assertEquals(0, (int) ArrayOperations.avg(array));
        array = new int[] {1, 2, 3};
        assertEquals(2, (int) ArrayOperations.avg(array));
        array = new int[] {2};
        assertEquals(2, (int) ArrayOperations.avg(array));
    }

    /**
     * Test of avg method, of class ArrayOperations.
     */
    @Test
    public void testAvg_floatArr() {
        float[] array = new float[0];
        assertEquals(0, (int) ArrayOperations.avg(array));
    }

    /**
     * Test of avg method, of class ArrayOperations.
     */
    @Test
    public void testAvg_doubleArr() {
        double[] array = new double[0];
        assertEquals(0, (int) ArrayOperations.avg(array));
    }

    /**
     * Test of max method, of class ArrayOperations.
     */
    @Test
    public void testMax_intArr() {
        int[] array = new int[] {1, 2, 3};
        assertEquals(3, ArrayOperations.max(array));
        array = new int[] {2};
        assertEquals(2, ArrayOperations.max(array));
    }

    /**
     * Test of max method, of class ArrayOperations.
     */
    @Test
    public void testMax_floatArr() {
        float[] array = new float[] {1.999f, 1.9999f, 1f};
        assertEquals(1.9999f, ArrayOperations.max(array), 0.00001);
    }

    /**
     * Test of max method, of class ArrayOperations.
     */
    @Test
    public void testMax_doubleArr() {
        double[] array = new double[] {1.9999999f, 1.99999999f, 1f};
        assertEquals(1.99999999f, ArrayOperations.max(array), 0.000000001);
    }

    /**
     * Test of max method, of class ArrayOperations.
     */
    @Test
    public void testMax_GenericType_Comparator() {
        String[] array = new String[] {"1", "2.00001", "2"};
        assertEquals("2.00001", ArrayOperations.max(array, comparator));
        array = new String[] {"2"};
        assertEquals("2", ArrayOperations.max(array, comparator));
    }

    /**
     * Test of maxIndex method, of class ArrayOperations.
     */
    @Test
    public void testMaxIndex_intArr() {
        int[] array = new int[] {1, 2, 3};
        assertEquals(2, ArrayOperations.maxIndex(array));
        array = new int[] {2};
        assertEquals(0, ArrayOperations.maxIndex(array));
    }

    /**
     * Test of maxIndex method, of class ArrayOperations.
     */
    @Test
    public void testMaxIndex_floatArr() {
        float[] array = new float[] {1.999f, 1.9999f, 1f};
        assertEquals(1, ArrayOperations.maxIndex(array));
    }

    /**
     * Test of maxIndex method, of class ArrayOperations.
     */
    @Test
    public void testMaxIndex_doubleArr() {
        double[] array = new double[] {1.9999999f, 1.99999999f, 1f};
        assertEquals(1, ArrayOperations.maxIndex(array));
    }

    /**
     * Test of maxIndex method, of class ArrayOperations.
     */
    @Test
    public void testMaxIndex_GenericType_Comparator() {
        String[] array = new String[] {"1", "2.00001", "2"};
        assertEquals(1, ArrayOperations.maxIndex(array, comparator));
        array = new String[] {"2"};
        assertEquals(0, ArrayOperations.maxIndex(array, comparator));
    }

    /**
     * Test of min method, of class ArrayOperations.
     */
    @Test
    public void testMin_intArr() {
        int[] array = new int[] {1, 2, 3};
        assertEquals(1, ArrayOperations.min(array));
        array = new int[] {2};
        assertEquals(2, ArrayOperations.min(array));
    }

    /**
     * Test of min method, of class ArrayOperations.
     */
    @Test
    public void testMin_floatArr() {
        float[] array = new float[] {1.999f, 1.9999f, 1f};
        assertEquals(1.0f, ArrayOperations.min(array), 0.00001);
    }

    /**
     * Test of min method, of class ArrayOperations.
     */
    @Test
    public void testMin_doubleArr() {
        double[] array = new double[] {1.9999999f, 1.99999999f, 1f};
        assertEquals(1.0f, ArrayOperations.min(array), 0.000000001);
    }

    /**
     * Test of min method, of class ArrayOperations.
     */
    @Test
    public void testMin_GenericType_Comparator() {
        String[] array = new String[] {"1", "2.00001", "2"};
        assertEquals("1", ArrayOperations.min(array, comparator));
        array = new String[] {"2"};
        assertEquals("2", ArrayOperations.min(array, comparator));
    }

    /**
     * Test of minIndex method, of class ArrayOperations.
     */
    @Test
    public void testMinIndex_intArr() {
        int[] array = new int[] {1, 2, 3};
        assertEquals(0, ArrayOperations.minIndex(array));
        array = new int[] {2};
        assertEquals(0, ArrayOperations.minIndex(array));
    }

    /**
     * Test of minIndex method, of class ArrayOperations.
     */
    @Test
    public void testMinIndex_floatArr() {
        float[] array = new float[] {1.999f, 1.9999f, 1f};
        assertEquals(2, ArrayOperations.minIndex(array));
    }

    /**
     * Test of minIndex method, of class ArrayOperations.
     */
    @Test
    public void testMinIndex_doubleArr() {
        double[] array = new double[] {1.9999999f, 1.99999999f, 1f};
        assertEquals(2, ArrayOperations.minIndex(array));
    }

    /**
     * Test of minIndex method, of class ArrayOperations.
     */
    @Test
    public void testMinIndex_GenericType_Comparator() {
        String[] array = new String[] {"1", "2.00001", "2"};
        assertEquals(0, ArrayOperations.minIndex(array, comparator));
        array = new String[] {"2"};
        assertEquals(0, ArrayOperations.minIndex(array, comparator));
    }

    /**
     * Test of prefix method, of class ArrayOperations.
     */
    @Test
    public void testPrefix_byteArr_int() {
        byte[] toPrefix = new byte[] {'a', 'b', 'c'};
        byte[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        assertArrayEquals(new byte[] {'a', 'b'}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new byte[] {'a', 'b', 'c'}, prefix);
        assertArrayEquals(new byte[] {'a', 'b', 'c'}, toPrefix);
        try {
            prefix = ArrayOperations.prefix(toPrefix, 4);
            fail("Prefix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
    }

    /**
     * Test of prefix method, of class ArrayOperations.
     */
    @Test
    public void testPrefix_intArr_int() {
        int[] toPrefix = new int[] {1, 2, 3};
        int[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        assertArrayEquals(new int[] {1, 2}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new int[] {1, 2, 3}, prefix);
        assertArrayEquals(new int[] {1, 2, 3}, toPrefix);
        try {
            prefix = ArrayOperations.prefix(toPrefix, 4);
            fail("Prefix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
    }

    /**
     * Test of prefix method, of class ArrayOperations.
     */
    @Test
    public void testPrefix_floatArr_int() {
        float[] toPrefix = new float[] {1, 2, 3};
        float[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertEquals(3, toPrefix[2], 0);
        try {
            prefix = ArrayOperations.prefix(toPrefix, 4);
            fail("Prefix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
    }

    /**
     * Test of prefix method, of class ArrayOperations.
     */
    @Test
    public void testPrefix_doubleArr_int() {
        double[] toPrefix = new double[] {1, 2, 3};
        double[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertEquals(3, toPrefix[2], 0);
        try {
            prefix = ArrayOperations.prefix(toPrefix, 4);
            fail("Prefix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
    }

    /**
     * Test of prefix method, of class ArrayOperations.
     */
    @Test
    public void testPrefix_charArr_int() {
        char[] toPrefix = new char[] {'a', 'b', 'c'};
        char[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        assertArrayEquals(new char[] {'a', 'b'}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new char[] {'a', 'b', 'c'}, prefix);
        assertArrayEquals(new char[] {'a', 'b', 'c'}, toPrefix);
        try {
            prefix = ArrayOperations.prefix(toPrefix, 4);
            fail("Prefix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
    }

    /**
     * Test of prefix method, of class ArrayOperations.
     */
    @Test
    public void testPrefix_StringArr_int() {
        String[] toPrefix = new String[] {"a", "b", "c"};
        String[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        assertArrayEquals(new String[] {"a", "b"}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new String[] {"a", "b", "c"}, prefix);
        assertArrayEquals(new String[] {"a", "b", "c"}, toPrefix);
        try {
            prefix = ArrayOperations.prefix(toPrefix, 4);
            fail("Prefix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
    }

    /**
     * Test of prefix method, of class ArrayOperations.
     */
    @Test
    public void testPrefix_ObjectArr_int() {
        ReplicableObject one = new ReplicableObject(1);
        ReplicableObject two = new ReplicableObject(2);
        ReplicableObject three = new ReplicableObject(3);
        ReplicableObject[] toPrefix = new ReplicableObject[] {one, two, three};
        Object[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        assertArrayEquals(new ReplicableObject[] {one, two}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new ReplicableObject[] {one, two, three}, prefix);
        assertArrayEquals(new ReplicableObject[] {one, two, three}, toPrefix);
        try {
            prefix = ArrayOperations.prefix(toPrefix, 4);
            fail("Prefix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
    }

    /**
     * Test of suffix method, of class ArrayOperations.
     */
    @Test
    public void testSuffix_byteArr_int() {
        byte[] toSuffix = new byte[] {'a', 'b', 'c'};
        byte[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        assertArrayEquals(new byte[] {'a', 'b', 'c'}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        assertArrayEquals(new byte[] {'c'}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertArrayEquals(new byte[] {'a', 'b', 'c'}, toSuffix);
        try {
            suffix = ArrayOperations.suffix(toSuffix, 4);
            fail("Suffix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of suffix method, of class ArrayOperations.
     */
    @Test
    public void testSuffix_intArr_int() {
        int[] toSuffix = new int[] {1, 2, 3};
        int[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        assertArrayEquals(new int[] {1, 2, 3}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        assertArrayEquals(new int[] {3}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertArrayEquals(new int[] {1, 2, 3}, toSuffix);
        try {
            suffix = ArrayOperations.suffix(toSuffix, 4);
            fail("Suffix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of suffix method, of class ArrayOperations.
     */
    @Test
    public void testSuffix_floatArr_int() {
        float[] toSuffix = new float[] {1, 2, 3};
        float[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertEquals(1, toSuffix[0], 0);
        try {
            suffix = ArrayOperations.suffix(toSuffix, 4);
            fail("Suffix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of suffix method, of class ArrayOperations.
     */
    @Test
    public void testSuffix_doubleArr_int() {
        double[] toSuffix = new double[] {1, 2, 3};
        double[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertEquals(1, toSuffix[0], 0);
        try {
            suffix = ArrayOperations.suffix(toSuffix, 4);
            fail("Suffix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of suffix method, of class ArrayOperations.
     */
    @Test
    public void testSuffix_charArr_int() {
        char[] toSuffix = new char[] {'a', 'b', 'c'};
        char[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        assertArrayEquals(new char[] {'a', 'b', 'c'}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        assertArrayEquals(new char[] {'c'}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertArrayEquals(new char[] {'a', 'b', 'c'}, toSuffix);
        try {
            suffix = ArrayOperations.suffix(toSuffix, 4);
            fail("Suffix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of suffix method, of class ArrayOperations.
     */
    @Test
    public void testSuffix_StringArr_int() {
        String[] toSuffix = new String[] {"a", "b", "c"};
        String[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        assertArrayEquals(new String[] {"a", "b", "c"}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        assertArrayEquals(new String[] {"c"}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertArrayEquals(new String[] {"a", "b", "c"}, toSuffix);
        try {
            suffix = ArrayOperations.suffix(toSuffix, 4);
            fail("Suffix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of suffix method, of class ArrayOperations.
     */
    @Test
    public void testSuffix_ObjectArr_int() {
        ReplicableObject one = new ReplicableObject(1);
        ReplicableObject two = new ReplicableObject(2);
        ReplicableObject three = new ReplicableObject(3);
        ReplicableObject[] toSuffix = new ReplicableObject[] {one, two, three};
        Object[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        assertArrayEquals(new ReplicableObject[] {one, two, three}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        assertArrayEquals(new ReplicableObject[] {three}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertArrayEquals(new ReplicableObject[] {one, two, three}, toSuffix);
        try {
            suffix = ArrayOperations.suffix(toSuffix, 4);
            fail("Suffix failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of subArray method, of class ArrayOperations.
     */
    @Test
    public void testSubArray_3args_1() {
        byte[] toCut = new byte[] {'a', 'b', 'c'};
        byte[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        assertArrayEquals(new byte[] {'a', 'b', 'c'}, subArray);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        assertArrayEquals(new byte[] {'b'}, subArray);
        subArray = ArrayOperations.subArray(toCut, 2, 2);
        assertEquals(0, subArray.length);
        assertArrayEquals(new byte[] {'a', 'b', 'c'}, toCut);
        try {
            subArray = ArrayOperations.subArray(toCut, 0, 4);
            fail("SubArray failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            subArray = ArrayOperations.subArray(toCut, 2, 1);
            fail("SubArray failed to negative array size exception");
        }
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of subArray method, of class ArrayOperations.
     */
    @Test
    public void testSubArray_3args_2() {
        int[] toCut = new int[] {1, 2, 3};
        int[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        assertArrayEquals(new int[] {1, 2, 3}, subArray);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        assertArrayEquals(new int[] {2}, subArray);
        subArray = ArrayOperations.subArray(toCut, 2, 2);
        assertEquals(0, subArray.length);
        assertArrayEquals(new int[] {1, 2, 3}, toCut);
        try {
            subArray = ArrayOperations.subArray(toCut, 0, 4);
            fail("SubArray failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            subArray = ArrayOperations.subArray(toCut, 2, 1);
            fail("SubArray failed to negative array size exception");
        }
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of subArray method, of class ArrayOperations.
     */
    @Test
    public void testSubArray_3args_3() {
        float[] toCut = new float[] {1, 2, 3};
        float[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        subArray = ArrayOperations.subArray(toCut, 2, 2);
        assertEquals(0, subArray.length);
        assertEquals(1, toCut[0], 0);
        try {
            subArray = ArrayOperations.subArray(toCut, 0, 4);
            fail("SubArray failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            subArray = ArrayOperations.subArray(toCut, 2, 1);
            fail("SubArray failed to negative array size exception");
        }
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of subArray method, of class ArrayOperations.
     */
    @Test
    public void testSubArray_3args_4() {
        double[] toCut = new double[] {1, 2, 3};
        double[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        subArray = ArrayOperations.subArray(toCut, 2, 2);
        assertEquals(0, subArray.length);
        assertEquals(1, toCut[0], 0);
        try {
            subArray = ArrayOperations.subArray(toCut, 0, 4);
            fail("SubArray failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            subArray = ArrayOperations.subArray(toCut, 2, 1);
            fail("SubArray failed to negative array size exception");
        }
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of subArray method, of class ArrayOperations.
     */
    @Test
    public void testSubArray_3args_5() {
        char[] toCut = new char[] {'a', 'b', 'c'};
        char[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        assertArrayEquals(new char[] {'a', 'b', 'c'}, subArray);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        assertArrayEquals(new char[] {'b'}, subArray);
        subArray = ArrayOperations.subArray(toCut, 2, 2);
        assertEquals(0, subArray.length);
        assertArrayEquals(new char[] {'a', 'b', 'c'}, toCut);
        try {
            subArray = ArrayOperations.subArray(toCut, 0, 4);
            fail("SubArray failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            subArray = ArrayOperations.subArray(toCut, 2, 1);
            fail("SubArray failed to negative array size exception");
        }
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of subArray method, of class ArrayOperations.
     */
    @Test
    public void testSubArray_3args_6() {
        String[] toCut = new String[] {"a", "b", "c"};
        String[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        assertArrayEquals(new String[] {"a", "b", "c"}, subArray);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        assertArrayEquals(new String[] {"b"}, subArray);
        subArray = ArrayOperations.subArray(toCut, 2, 2);
        assertEquals(0, subArray.length);
        assertArrayEquals(new String[] {"a", "b", "c"}, toCut);
        try {
            subArray = ArrayOperations.subArray(toCut, 0, 4);
            fail("SubArray failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            subArray = ArrayOperations.subArray(toCut, 2, 1);
            fail("SubArray failed to negative array size exception");
        }
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of subArray method, of class ArrayOperations.
     */
    @Test
    public void testSubArray_3args_7() {
        ReplicableObject one = new ReplicableObject(1);
        ReplicableObject two = new ReplicableObject(2);
        ReplicableObject three = new ReplicableObject(3);
        ReplicableObject[] toCut = new ReplicableObject[] {one, two, three};
        Object[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        assertArrayEquals(new ReplicableObject[] {one, two, three}, subArray);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        assertArrayEquals(new ReplicableObject[] {two}, subArray);
        subArray = ArrayOperations.subArray(toCut, 3, 3);
        assertEquals(0, subArray.length);
        assertArrayEquals(new ReplicableObject[] {one, two, three}, toCut);
        try {
            subArray = ArrayOperations.subArray(toCut, 0, 4);
            fail("SubArray failed to throw out of bounds exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            subArray = ArrayOperations.subArray(toCut, 2, 1);
            fail("SubArray failed to negative array size exception");
        }
        catch (NegativeArraySizeException nas) {}
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_byteArrArr() {
        byte[] empty = new byte[0];
        byte[] single = new byte[] {'a'};
        byte[] multiple = new byte[] {'a', 'b', 'c'};
        byte[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        assertArrayEquals(new byte[] {'a', 'a', 'b', 'c'}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new byte[] {'a', 'a'}, joined);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_intArrArr() {
        int[] empty = new int[0];
        int[] single = new int[] {1};
        int[] multiple = new int[] {1, 2, 3};
        int[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        assertArrayEquals(new int[] {1, 1, 2, 3}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new int[] {1, 1}, joined);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_floatArrArr() {
        float[] empty = new float[0];
        float[] single = new float[] {1};
        float[] multiple = new float[] {1, 2, 3};
        float[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_doubleArrArr() {
        double[] empty = new double[0];
        double[] single = new double[] {1};
        double[] multiple = new double[] {1, 2, 3};
        double[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_charArrArr() {
        char[] empty = new char[0];
        char[] single = new char[] {'a'};
        char[] multiple = new char[] {'a', 'b', 'c'};
        char[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        assertArrayEquals(new char[] {'a', 'a', 'b', 'c'}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new char[] {'a', 'a'}, joined);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_booleanArrArr() {
        boolean[] empty = new boolean[0];
        boolean[] single = new boolean[] {true};
        boolean[] multiple = new boolean[] {true, false, true};
        boolean[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_StringArrArr() {
        String[] empty = new String[0];
        String[] single = new String[] {"a"};
        String[] multiple = new String[] {"a", "b", "c"};
        String[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        assertArrayEquals(new String[] {"a", "a", "b", "c"}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new String[] {"a", "a"}, joined);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_ObjectArrArr() {
        ReplicableObject one = new ReplicableObject(1);
        ReplicableObject two = new ReplicableObject(2);
        ReplicableObject three = new ReplicableObject(3);
        ReplicableObject[] empty = new ReplicableObject[0];
        ReplicableObject[] single = new ReplicableObject[] {one};
        ReplicableObject[] multiple = new ReplicableObject[] {one, two, three};
        Object[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        assertArrayEquals(new ReplicableObject[] {one, one, two, three}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new ReplicableObject[] {one, one}, joined);
    }

    /**
     * Test of delete method, of class ArrayOperations.
     */
    @Test
    public void testDelete_byteArr_int() {
        try {
            ArrayOperations.delete(new byte[0], 0);
            fail("Delete failed to throw index exception for empty array");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            ArrayOperations.delete(new byte[] {'a', 'b', 'c'}, 3);
            fail("Delete failed to throw index exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        byte[] toDelete = new byte[] {'a', 'b', 'c'};
        byte[] deleted = ArrayOperations.delete(toDelete, 0);
        assertEquals(2, deleted.length);
        assertArrayEquals(new byte[] {'b', 'c'}, deleted);
        deleted = ArrayOperations.delete(deleted, 1);
        assertEquals(1, deleted.length);
        assertArrayEquals(new byte[] {'b'}, deleted);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertArrayEquals(new byte[] {'a', 'c'}, deleted);
    }

    /**
     * Test of delete method, of class ArrayOperations.
     */
    @Test
    public void testDelete_intArr_int() {
        try {
            ArrayOperations.delete(new int[0], 0);
            fail("Delete failed to throw index exception for empty array");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            ArrayOperations.delete(new int[] {1, 2, 3}, 3);
            fail("Delete failed to throw index exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        int[] toDelete = new int[] {1, 2, 3};
        int[] deleted = ArrayOperations.delete(toDelete, 0);
        assertEquals(2, deleted.length);
        assertArrayEquals(new int[] {2, 3}, deleted);
        deleted = ArrayOperations.delete(deleted, 1);
        assertEquals(1, deleted.length);
        assertArrayEquals(new int[] {2}, deleted);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertArrayEquals(new int[] {1, 3}, deleted);
    }

    /**
     * Test of delete method, of class ArrayOperations.
     */
    @Test
    public void testDelete_floatArr_int() {
        try {
            ArrayOperations.delete(new float[0], 0);
            fail("Delete failed to throw index exception for empty array");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            ArrayOperations.delete(new float[] {1, 2, 3}, 3);
            fail("Delete failed to throw index exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        float[] toDelete = new float[] {1, 2, 3};
        float[] deleted = ArrayOperations.delete(toDelete, 0);
        assertEquals(2, deleted.length);
        deleted = ArrayOperations.delete(deleted, 1);
        assertEquals(1, deleted.length);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertEquals(3, deleted[1], 0);
    }

    /**
     * Test of delete method, of class ArrayOperations.
     */
    @Test
    public void testDelete_doubleArr_int() {
        try {
            ArrayOperations.delete(new double[0], 0);
            fail("Delete failed to throw index exception for empty array");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            ArrayOperations.delete(new double[] {1, 2, 3}, 3);
            fail("Delete failed to throw index exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        double[] toDelete = new double[] {1, 2, 3};
        double[] deleted = ArrayOperations.delete(toDelete, 0);
        assertEquals(2, deleted.length);
        deleted = ArrayOperations.delete(deleted, 1);
        assertEquals(1, deleted.length);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertEquals(3, deleted[1], 0);
    }

    /**
     * Test of delete method, of class ArrayOperations.
     */
    @Test
    public void testDelete_charArr_int() {
        try {
            ArrayOperations.delete(new char[0], 0);
            fail("Delete failed to throw index exception for empty array");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            ArrayOperations.delete(new char[] {'a', 'b', 'c'}, 3);
            fail("Delete failed to throw index exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        char[] toDelete = new char[] {'a', 'b', 'c'};
        char[] deleted = ArrayOperations.delete(toDelete, 0);
        assertEquals(2, deleted.length);
        assertArrayEquals(new char[] {'b', 'c'}, deleted);
        deleted = ArrayOperations.delete(deleted, 1);
        assertEquals(1, deleted.length);
        assertArrayEquals(new char[] {'b'}, deleted);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertArrayEquals(new char[] {'a', 'c'}, deleted);
    }

    /**
     * Test of delete method, of class ArrayOperations.
     */
    @Test
    public void testDelete_StringArr_int() {
        try {
            ArrayOperations.delete(new String[0], 0);
            fail("Delete failed to throw index exception for empty array");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            ArrayOperations.delete(new String[] {"a", "b", "c"}, 3);
            fail("Delete failed to throw index exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        String[] toDelete = new String[] {"a", "b", "c"};
        String[] deleted = ArrayOperations.delete(toDelete, 0);
        assertEquals(2, deleted.length);
        assertArrayEquals(new String[] {"b", "c"}, deleted);
        deleted = ArrayOperations.delete(deleted, 1);
        assertEquals(1, deleted.length);
        assertArrayEquals(new String[] {"b"}, deleted);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertArrayEquals(new String[] {"a", "c"}, deleted);
    }

    /**
     * Test of delete method, of class ArrayOperations.
     */
    @Test
    public void testDelete_ObjectArr_int() {
        try {
            ArrayOperations.delete(new Object[0], 0);
            fail("Delete failed to throw index exception for empty array");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        try {
            ArrayOperations.delete(new Object[] {"a", "b", "c"}, 3);
            fail("Delete failed to throw index exception");
        }
        catch (ArrayIndexOutOfBoundsException iobe) {}
        Object[] toDelete = new Object[] {"a", "b", "c"};
        Object[] deleted = ArrayOperations.delete(toDelete, 0);
        assertEquals(2, deleted.length);
        assertArrayEquals(new Object[] {"b", "c"}, deleted);
        deleted = ArrayOperations.delete(deleted, 1);
        assertEquals(1, deleted.length);
        assertArrayEquals(new Object[] {"b"}, deleted);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertArrayEquals(new Object[] {"a", "c"}, deleted);
    }

    /**
     * Test of tautology method, of class ArrayOperations.
     */
    @Test
    public void testTautology() {
        assertFalse(ArrayOperations.tautology(new boolean[0]));
        assertTrue(ArrayOperations.tautology(new boolean[] {true}));
        int size = random.nextInt(100);
        boolean[] array = new boolean[size];
        Arrays.fill(array, true);
        assertTrue(ArrayOperations.tautology(array));
        array[random.nextInt(size)] = false;
        assertFalse(ArrayOperations.tautology(array));
        Arrays.fill(array, false);
        assertFalse(ArrayOperations.tautology(array));
    }

    /**
     * Test of contradiction method, of class ArrayOperations.
     */
    @Test
    public void testContradiction() {
        assertTrue(ArrayOperations.contradiction(new boolean[0]));
        assertTrue(ArrayOperations.contradiction(new boolean[] {false}));
        int size = random.nextInt(100);
        boolean[] array = new boolean[size];
        Arrays.fill(array, false);
        assertTrue(ArrayOperations.contradiction(array));
        array[random.nextInt(size)] = true;
        assertFalse(ArrayOperations.contradiction(array));
        Arrays.fill(array, true);
        assertFalse(ArrayOperations.contradiction(array));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_byte_byteArr() {
        byte[] findArray = new byte[] {'a', 'b', 'a', 'c'};
        assertEquals(0, ArrayOperations.seqFind((byte) 'a', findArray));
        assertEquals(1, ArrayOperations.seqFind((byte) 'b', findArray));
        assertEquals(3, ArrayOperations.seqFind((byte) 'c', findArray));
        assertEquals(-1, ArrayOperations.seqFind((byte) 'd', findArray));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_int_intArr() {
        int[] findArray = new int[] {1, 2, 1, 3};
        assertEquals(0, ArrayOperations.seqFind(1, findArray));
        assertEquals(1, ArrayOperations.seqFind(2, findArray));
        assertEquals(3, ArrayOperations.seqFind(3, findArray));
        assertEquals(-1, ArrayOperations.seqFind(4, findArray));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_float_floatArr() {
        float[] findArray = new float[] {1, 2, 1, 3};
        assertEquals(0, ArrayOperations.seqFind(1, findArray));
        assertEquals(1, ArrayOperations.seqFind(2, findArray));
        assertEquals(3, ArrayOperations.seqFind(3, findArray));
        assertEquals(-1, ArrayOperations.seqFind(4, findArray));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_double_doubleArr() {
        double[] findArray = new double[] {1, 2, 1, 3};
        assertEquals(0, ArrayOperations.seqFind(1, findArray));
        assertEquals(1, ArrayOperations.seqFind(2, findArray));
        assertEquals(3, ArrayOperations.seqFind(3, findArray));
        assertEquals(-1, ArrayOperations.seqFind(4, findArray));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_char_charArr() {
        char[] findArray = new char[] {'a', 'b', 'a', 'c'};
        assertEquals(0, ArrayOperations.seqFind( 'a', findArray));
        assertEquals(1, ArrayOperations.seqFind('b', findArray));
        assertEquals(3, ArrayOperations.seqFind('c', findArray));
        assertEquals(-1, ArrayOperations.seqFind('d', findArray));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_Object_ObjectArr() {
        String[] findArray = new String[] {"a", "b", "a", "c"};
        assertEquals(0, ArrayOperations.seqFind("a", findArray));
        assertEquals(1, ArrayOperations.seqFind("b", findArray));
        assertEquals(3, ArrayOperations.seqFind("c", findArray));
        assertEquals(-1, ArrayOperations.seqFind("d", findArray));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_3args_1() {
        String[] findArray = new String[] {"first:a", "b:b", "other:a", "c:c"};
        KeyValuePairMatcher keyMatcher = new KeyValuePairMatcher();
        assertEquals(-1, ArrayOperations.seqFind("a", findArray, keyMatcher));
        assertEquals(2, ArrayOperations.seqFind("other", findArray, keyMatcher));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_3args_2() {
        byte[] findArray = new byte[] {'a', 'b', 'a', 'c'};
        assertEquals(0, ArrayOperations.seqFind((byte) 'a', findArray, 0));
        assertEquals(2, ArrayOperations.seqFind((byte) 'a', findArray, 1));
        assertEquals(-1, ArrayOperations.seqFind((byte) 'a', findArray, 3));
        assertEquals(-1, ArrayOperations.seqFind((byte) 'd', findArray, 0));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_3args_3() {
        int[] findArray = new int[] {1, 2, 1, 3};
        assertEquals(0, ArrayOperations.seqFind(1, findArray, 0));
        assertEquals(2, ArrayOperations.seqFind(1, findArray, 1));
        assertEquals(-1, ArrayOperations.seqFind(1, findArray, 3));
        assertEquals(-1, ArrayOperations.seqFind(4, findArray, 0));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_3args_4() {
        float[] findArray = new float[] {1, 2, 1, 3};
        assertEquals(0, ArrayOperations.seqFind(1, findArray, 0));
        assertEquals(2, ArrayOperations.seqFind(1, findArray, 1));
        assertEquals(-1, ArrayOperations.seqFind(1, findArray, 3));
        assertEquals(-1, ArrayOperations.seqFind(4, findArray), 0);
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_3args_5() {
        double[] findArray = new double[] {1, 2, 1, 3};
        assertEquals(0, ArrayOperations.seqFind(1, findArray, 0));
        assertEquals(2, ArrayOperations.seqFind(1, findArray, 1));
        assertEquals(-1, ArrayOperations.seqFind(1, findArray, 3));
        assertEquals(-1, ArrayOperations.seqFind(4, findArray, 0));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_3args_6() {
        char[] findArray = new char[] {'a', 'b', 'a', 'c'};
        assertEquals(0, ArrayOperations.seqFind( 'a', findArray, 0));
        assertEquals(2, ArrayOperations.seqFind('a', findArray, 1));
        assertEquals(-1, ArrayOperations.seqFind('a', findArray, 3));
        assertEquals(-1, ArrayOperations.seqFind('d', findArray, 0));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_3args_7() {
        String[] findArray = new String[] {"a", "b", "a", "c"};
        assertEquals(0, ArrayOperations.seqFind("a", findArray, 0));
        assertEquals(2, ArrayOperations.seqFind("a", findArray, 1));
        assertEquals(-1, ArrayOperations.seqFind("a", findArray, 3));
        assertEquals(-1, ArrayOperations.seqFind("d", findArray, 0));
    }

    /**
     * Test of seqFind method, of class ArrayOperations.
     */
    @Test
    public void testSeqFind_4args() {
        String[] findArray = new String[] {"first:a", "b:b", "other:a", "c:c"};
        KeyValuePairMatcher keyMatcher = new KeyValuePairMatcher();
        assertEquals(2, ArrayOperations.seqFind("other", findArray, 1, keyMatcher));
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_intArr_int() {
        int[] array = new int[] {1, 50, 10, 15, Integer.MAX_VALUE};
        int[] array2 = new int[] {Integer.MIN_VALUE, -100, 0, 55};
        assertEquals(1, ArrayOperations.findClosest(array, Integer.MIN_VALUE));
        assertEquals(Integer.MIN_VALUE, ArrayOperations.findClosest(array2, Integer.MIN_VALUE));
        assertEquals(1, ArrayOperations.findClosest(array, -51));
        assertEquals(-100, ArrayOperations.findClosest(array2, -51));
        assertEquals(1, ArrayOperations.findClosest(array, 2));
        assertEquals(0, ArrayOperations.findClosest(array2, 2));
        assertEquals(10, ArrayOperations.findClosest(array, 6));
        assertEquals(15, ArrayOperations.findClosest(array, 15));
        assertEquals(15, ArrayOperations.findClosest(array, 25));
        assertEquals(50, ArrayOperations.findClosest(array, 40));
        assertEquals(50, ArrayOperations.findClosest(array, 51));
        assertEquals(Integer.MAX_VALUE, ArrayOperations.findClosest(array, Integer.MAX_VALUE));
        assertEquals(55, ArrayOperations.findClosest(array2, Integer.MAX_VALUE));
    }

    /**
     * Test of findClosestIndex method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_intArr_int() {
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_doubleArr_double() {
        double[] array = new double[] {1, 50, 10, 15};
        assertEquals(1, ArrayOperations.findClosest(array, Double.MIN_VALUE), 0);
        assertEquals(1, ArrayOperations.findClosest(array, -51), 0);
        assertEquals(1, ArrayOperations.findClosest(array, 2), 0);
        assertEquals(10, ArrayOperations.findClosest(array, 6), 0);
        assertEquals(15, ArrayOperations.findClosest(array, 15), 0);
        assertEquals(15, ArrayOperations.findClosest(array, 25), 0);
        assertEquals(50, ArrayOperations.findClosest(array, 40), 0);
        assertEquals(50, ArrayOperations.findClosest(array, 51), 0);
        assertEquals(50, ArrayOperations.findClosest(array, Double.MAX_VALUE), 0);
    }

    /**
     * Test of findClosestIndex method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_doubleArr_double() {
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_floatArr_float() {
        float[] array = new float[] {1, 50, 10, 15};
        assertEquals(1, ArrayOperations.findClosest(array, Float.MIN_VALUE), 0);
        assertEquals(1, ArrayOperations.findClosest(array, -51), 0);
        assertEquals(1, ArrayOperations.findClosest(array, 2), 0);
        assertEquals(10, ArrayOperations.findClosest(array, 6), 0);
        assertEquals(15, ArrayOperations.findClosest(array, 15), 0);
        assertEquals(15, ArrayOperations.findClosest(array, 25), 0);
        assertEquals(50, ArrayOperations.findClosest(array, 40), 0);
        assertEquals(50, ArrayOperations.findClosest(array, 51), 0);
        assertEquals(50, ArrayOperations.findClosest(array, Float.MAX_VALUE), 0);
    }

    /**
     * Test of findClosestIndex method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_floatArr_float() {
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_StringArr_String() {
        String[] array = new String[] {"string", "array", "new", "int", "assert", "equals"};
        assertEquals("array", ArrayOperations.findClosest(array, "a"));
        assertEquals("assert", ArrayOperations.findClosest(array, "artsy"));
        assertEquals("array", ArrayOperations.findClosest(array, "because"));
        assertEquals("equals", ArrayOperations.findClosest(array, "d"));
        assertEquals("new", ArrayOperations.findClosest(array, "new"));
        assertEquals("int", ArrayOperations.findClosest(array, "kind"));
//        assertEquals("string", ArrayOperations.findClosest(array, "sassy"));  // returns new, which is
        assertEquals("string", ArrayOperations.findClosest(array, "suit"));
        assertEquals("string", ArrayOperations.findClosest(array, "z"));
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_ObjectArr_Object() {
        String[] array = new String[] {"string", "array", "new", "int", "assert", "equals"};
        DistanceFunction df = new LexicographicDistanceFunction();
        assertEquals("array", ArrayOperations.findClosest(array, "a", df));
        assertEquals("assert", ArrayOperations.findClosest(array, "artsy", df));
        assertEquals("array", ArrayOperations.findClosest(array, "because", df));
        assertEquals("equals", ArrayOperations.findClosest(array, "d", df));
        assertEquals("new", ArrayOperations.findClosest(array, "new", df));
        assertEquals("int", ArrayOperations.findClosest(array, "kind", df));
//        assertEquals("string", ArrayOperations.findClosest(array, "sassy"));  // returns new, which is
        assertEquals("string", ArrayOperations.findClosest(array, "suit", df));
        assertEquals("string", ArrayOperations.findClosest(array, "z", df));
    }

    /**
     * Test of findClosestIndex method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_StringArr_String() {
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_charArr_char() {
        char[] array = new char[] {'s', 'A', 'n', 'i', 'a', 'e'};
        assertEquals('a', ArrayOperations.findClosest(array, 'a'));
        assertEquals('a', ArrayOperations.findClosest(array, 'b'));
        assertEquals('e', ArrayOperations.findClosest(array, 'd'));
        assertEquals('n', ArrayOperations.findClosest(array, 'n'));
        assertEquals('i', ArrayOperations.findClosest(array, 'k'));
        assertEquals('s', ArrayOperations.findClosest(array, 's'));
        assertEquals('s', ArrayOperations.findClosest(array, 'z'));
    }

    /**
     * Test of findClosestIndex method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_charArr_char() {
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_intArr_int() {
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_intArr_int() {
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_doubleArr_double() {
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_doubleArr_double() {
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_floatArr_float() {
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_floatArr_float() {
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_charArr_char() {
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_charArr_char() {
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_StringArr_String() {
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_StringArr_String() {
    }

    /**
     * Test of findNextInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindNextInOrder_GenericType_GenericType() {
    }

    /**
     * Test of findPositionInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindPositionInOrder_GenericType_GenericType() {
    }

    /**
     * Test of findNextInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindNextInOrder_3args() {
    }

    /**
     * Test of findPositionInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindPositionInOrder_3args() {
    }

    /**
     * Test of normalizeMax method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeMax_intArr() {
    }

    /**
     * Test of normalizeSum method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeSum_intArr() {
    }

    /**
     * Test of normalizeVal method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeVal_intArr_double() {
    }

    /**
     * Test of normalizeMax method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeMax_floatArr() {
    }

    /**
     * Test of normalizeSum method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeSum_floatArr() {
    }

    /**
     * Test of normalizeVal method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeVal_floatArr_double() {
    }

    /**
     * Test of normalizeMax method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeMax_doubleArr() {
    }

    /**
     * Test of normalizeSum method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeSum_doubleArr() {
    }

    /**
     * Test of normalizeVal method, of class ArrayOperations.
     */
    @Test
    public void testNormalizeVal_doubleArr_double() {
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_intArr() {
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_floatArr() {
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_doubleArr() {
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_charArr() {
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_StringArr() {
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_booleanArr() {
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_ObjectArr() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_1() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_int_intArr() {
    }

    /**
     * Test of insertSorted method, of class ArrayOperations.
     */
    @Test
    public void testInsertSorted_int_intArr() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_2() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_byte_byteArr() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_3() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_float_floatArr() {
    }

    /**
     * Test of insertSorted method, of class ArrayOperations.
     */
    @Test
    public void testInsertSorted_float_floatArr() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_4() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_double_doubleArr() {
    }

    /**
     * Test of insertSorted method, of class ArrayOperations.
     */
    @Test
    public void testInsertSorted_double_doubleArr() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_5() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_char_charArr() {
    }

    /**
     * Test of insertSorted method, of class ArrayOperations.
     */
    @Test
    public void testInsertSorted_char_charArr() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_6() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_String_StringArr() {
    }

    /**
     * Test of insertSorted method, of class ArrayOperations.
     */
    @Test
    public void testInsertSorted_String_StringArr() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_7() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_Object_ObjectArr() {
    }

    /**
     * Test of insertSorted method, of class ArrayOperations.
     */
    @Test
    public void testInsertSorted_GenericType_GenericType() {
    }

    /**
     * Test of insertSorted method, of class ArrayOperations.
     */
    @Test
    public void testInsertSorted_3args() {
    }

    /**
     * Test of insert method, of class ArrayOperations.
     */
    @Test
    public void testInsert_3args_8() {
    }

    /**
     * Test of addElement method, of class ArrayOperations.
     */
    @Test
    public void testAddElement_boolean_booleanArr() {
    }

    /**
     * Test of generateEnumeration method, of class ArrayOperations.
     */
    @Test
    public void testGenerateEnumeration_int() {
    }

    /**
     * Test of generateEnumeration method, of class ArrayOperations.
     */
    @Test
    public void testGenerateEnumeration_3args() {
    }

    /**
     * Test of initialize method, of class ArrayOperations.
     */
    @Test
    public void testInitialize_intArr_int() {
    }

    /**
     * Test of initialize method, of class ArrayOperations.
     */
    @Test
    public void testInitialize_floatArr_float() {
    }

    /**
     * Test of initialize method, of class ArrayOperations.
     */
    @Test
    public void testInitialize_doubleArr_double() {
    }

    /**
     * Test of initialize method, of class ArrayOperations.
     */
    @Test
    public void testInitialize_charArr_char() {
    }

    /**
     * Test of initialize method, of class ArrayOperations.
     */
    @Test
    public void testInitialize_booleanArr_boolean() {
    }

    /**
     * Test of initialize method, of class ArrayOperations.
     */
    @Test
    public void testInitialize_StringArr_String() {
    }

    /**
     * Test of arrayCast method, of class ArrayOperations.
     */
    @Test
    public void testArrayCast() {
    }

    /**
     * Test of toString method, of class ArrayOperations.
     */
    @Test
    public void testToString_intArr() {
    }

    /**
     * Test of toString method, of class ArrayOperations.
     */
    @Test
    public void testToString_doubleArr() {
    }

    /**
     * Test of toString method, of class ArrayOperations.
     */
    @Test
    public void testToString_longArr() {
    }

    /**
     * Test of toString method, of class ArrayOperations.
     */
    @Test
    public void testToString_floatArr() {
    }

    /**
     * Test of toString method, of class ArrayOperations.
     */
    @Test
    public void testToString_booleanArr() {
    }

    /**
     * Test of toIntArray method, of class ArrayOperations.
     */
    @Test
    public void testToIntArray() {
    }

    /**
     * Test of toDoubleArray method, of class ArrayOperations.
     */
    @Test
    public void testToDoubleArray() {
    }

    /**
     * Test of toLongArray method, of class ArrayOperations.
     */
    @Test
    public void testToLongArray() {
    }

    /**
     * Test of toFloatArray method, of class ArrayOperations.
     */
    @Test
    public void testToFloatArray() {
    }

    /**
     * Test of toBooleanArray method, of class ArrayOperations.
     */
    @Test
    public void testToBooleanArray() {
    }

    /**
     * Test of areEqual method, of class ArrayOperations.
     */
    @Test
    public void testAreEqual_intArr_intArr() {
    }

    /**
     * Test of areEqual method, of class ArrayOperations.
     */
    @Test
    public void testAreEqual_longArr_longArr() {
    }

    /**
     * Test of areEqual method, of class ArrayOperations.
     */
    @Test
    public void testAreEqual_floatArr_floatArr() {
    }

    /**
     * Test of areEqual method, of class ArrayOperations.
     */
    @Test
    public void testAreEqual_doubleArr_doubleArr() {
    }

    /**
     * Test of areEqual method, of class ArrayOperations.
     */
    @Test
    public void testAreEqual_byteArr_byteArr() {
    }

    /**
     * Test of areEqual method, of class ArrayOperations.
     */
    @Test
    public void testAreEqual_charArr_charArr() {
    }

    /**
     * Test of areEqual method, of class ArrayOperations.
     */
    @Test
    public void testAreEqual_ObjectArr_ObjectArr() {
    }

    /**
     * Test of isEmpty method, of class ArrayOperations.
     */
    @Test
    public void testIsEmpty() {
    }

    /**
     * Test of box method, of class ArrayOperations.
     */
    @Test
    public void testBox_intArr() {
    }

    /**
     * Test of box method, of class ArrayOperations.
     */
    @Test
    public void testBox_longArr() {
    }

    /**
     * Test of box method, of class ArrayOperations.
     */
    @Test
    public void testBox_floatArr() {
    }

    /**
     * Test of box method, of class ArrayOperations.
     */
    @Test
    public void testBox_doubleArr() {
    }

    /**
     * Test of box method, of class ArrayOperations.
     */
    @Test
    public void testBox_booleanArr() {
    }

    /**
     * Test of unbox method, of class ArrayOperations.
     */
    @Test
    public void testUnbox_IntegerArr() {
    }

    /**
     * Test of unbox method, of class ArrayOperations.
     */
    @Test
    public void testUnbox_LongArr() {
    }

    /**
     * Test of unbox method, of class ArrayOperations.
     */
    @Test
    public void testUnbox_FloatArr() {
    }

    /**
     * Test of unbox method, of class ArrayOperations.
     */
    @Test
    public void testUnbox_DoubleArr() {
    }

    /**
     * Test of unbox method, of class ArrayOperations.
     */
    @Test
    public void testUnbox_BooleanArr() {
    }

    /**
     * Test of countTrue method, of class ArrayOperations.
     */
    @Test
    public void testCountTrue() {
    }

    /**
     * Test of createArithSeries method, of class ArrayOperations.
     */
    @Test
    public void testCreateArithSeries() {
    }

    /**
     * Test of createEnumeration method, of class ArrayOperations.
     */
    @Test
    public void testCreateEnumeration() {
    }

    /**
     * Test of findFirstIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindFirstIndexInOrder_intArr_int() {
    }

    /**
     * Test of findFirstIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindFirstIndexInOrder_floatArr_float() {
    }

    /**
     * Test of findFirstIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindFirstIndexInOrder_longArr_long() {
    }

    /**
     * Test of findFirstIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindFirstIndexInOrder_doubleArr_double() {
    }

    /**
     * Test of findFirstIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindFirstIndexInOrder_ComparableArr_Comparable() {
    }

    /**
     * Test of findFirstIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindFirstIndexInOrder_3args() {
    }

    /**
     * Test of sort method, of class ArrayOperations.
     */
    @Test
    public void testSort_intArr() {
    }

    /**
     * Test of sort method, of class ArrayOperations.
     */
    @Test
    public void testSort_floatArr() {
    }

    /**
     * Test of sort method, of class ArrayOperations.
     */
    @Test
    public void testSort_longArr() {
    }

    /**
     * Test of sort method, of class ArrayOperations.
     */
    @Test
    public void testSort_doubleArr() {
    }

    /**
     * Test of sort method, of class ArrayOperations.
     */
    @Test
    public void testSort_ComparableArr() {
    }

    /**
     * Test of sortedOrder method, of class ArrayOperations.
     */
    @Test
    public void testSortedOrder_intArr() {
    }

    /**
     * Test of sortedOrder method, of class ArrayOperations.
     */
    @Test
    public void testSortedOrder_floatArr() {
    }

    /**
     * Test of sortedOrder method, of class ArrayOperations.
     */
    @Test
    public void testSortedOrder_longArr() {
    }

    /**
     * Test of sortedOrder method, of class ArrayOperations.
     */
    @Test
    public void testSortedOrder_doubleArr() {
    }

    /**
     * Test of sortedOrder method, of class ArrayOperations.
     */
    @Test
    public void testSortedOrder_ComparableArr() {
    }

    /**
     * Test of binarySearch method, of class ArrayOperations.
     */
    @Test
    public void testBinarySearch_3args_1() {
    }

    /**
     * Test of binarySearch method, of class ArrayOperations.
     */
    @Test
    public void testBinarySearch_3args_2() {
    }

    /**
     * Test of binarySearch method, of class ArrayOperations.
     */
    @Test
    public void testBinarySearch_3args_3() {
    }

    /**
     * Test of binarySearch method, of class ArrayOperations.
     */
    @Test
    public void testBinarySearch_3args_4() {
    }

    /**
     * Test of binarySearch method, of class ArrayOperations.
     */
    @Test
    public void testBinarySearch_3args_5() {
    }

    /**
     * Test of castArray method, of class ArrayOperations.
     */
    @Test
    public void testCastArray() {
    }

}