/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.arrays;

import org.junit.*;
import programming5.collections.NotFoundException;
import programming5.math.DistanceFunction;
import programming5.strings.*;
import programming5.strings.LexicographicDistanceFunction.Mode;

import java.math.BigInteger;
import java.util.*;

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
    public void testReplicate_GenericType_GenericType() {
        Date now = Calendar.getInstance().getTime();
        Date[] toReplicate = new Date[] {now};
        Date[] replica = ArrayOperations.replicate(toReplicate, date -> (Date) date.clone());
        assertNotSame(replica, toReplicate);
        assertArrayEquals(new Date[]{now}, replica);
        toReplicate = new Date[] {now, now, now};
        replica = ArrayOperations.replicate(toReplicate, date -> (Date) date.clone());
        assertArrayEquals(new Date[] {now, now, now}, replica);
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
        assertArrayEquals(new byte[]{'a', 'b', 'c'}, prefix);
        assertArrayEquals(new byte[]{'a', 'b', 'c'}, toPrefix);
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
        assertArrayEquals(new int[]{1, 2, 3}, prefix);
        assertArrayEquals(new int[]{1, 2, 3}, toPrefix);
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
        assertArrayEquals(new char[]{'a', 'b'}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new char[]{'a', 'b', 'c'}, prefix);
        assertArrayEquals(new char[]{'a', 'b', 'c'}, toPrefix);
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
        assertArrayEquals(new String[]{"a", "b"}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new String[]{"a", "b", "c"}, prefix);
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
        String one = "1";
        String two = "2";
        String three = "3";
        String[] toPrefix = new String[] {one, two, three};
        Object[] prefix = ArrayOperations.prefix(toPrefix, 0);
        assertEquals(0, prefix.length);
        prefix = ArrayOperations.prefix(toPrefix, 2);
        assertEquals(2, prefix.length);
        assertArrayEquals(new String[]{one, two}, prefix);
        prefix = ArrayOperations.prefix(toPrefix, 3);
        assertEquals(3, prefix.length);
        assertArrayEquals(new String[] {one, two, three}, prefix);
        assertArrayEquals(new String[] {one, two, three}, toPrefix);
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
        assertArrayEquals(new byte[]{'c'}, suffix);
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
        assertArrayEquals(new int[]{3}, suffix);
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
        assertArrayEquals(new char[]{'c'}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertArrayEquals(new char[]{'a', 'b', 'c'}, toSuffix);
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
        String one = "1";
        String two = "2";
        String three = "3";
        String[] toSuffix = new String[] {one, two, three};
        Object[] suffix = ArrayOperations.suffix(toSuffix, 0);
        assertEquals(3, suffix.length);
        assertArrayEquals(new String[] {one, two, three}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 2);
        assertEquals(1, suffix.length);
        assertArrayEquals(new String[] {three}, suffix);
        suffix = ArrayOperations.suffix(toSuffix, 3);
        assertEquals(0, suffix.length);
        assertArrayEquals(new String[] {one, two, three}, toSuffix);
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
        assertArrayEquals(new byte[]{'a', 'b', 'c'}, toCut);
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
        assertArrayEquals(new int[]{1, 2, 3}, toCut);
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
        assertArrayEquals(new char[]{'b'}, subArray);
        subArray = ArrayOperations.subArray(toCut, 2, 2);
        assertEquals(0, subArray.length);
        assertArrayEquals(new char[]{'a', 'b', 'c'}, toCut);
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
        String one = "1";
        String two = "2";
        String three = "3";
        String[] toCut = new String[] {one, two, three};
        Object[] subArray = ArrayOperations.subArray(toCut, 0, 3);
        assertEquals(3, subArray.length);
        assertArrayEquals(new String[] {one, two, three}, subArray);
        subArray = ArrayOperations.subArray(toCut, 1, 2);
        assertEquals(1, subArray.length);
        assertArrayEquals(new String[] {two}, subArray);
        subArray = ArrayOperations.subArray(toCut, 3, 3);
        assertEquals(0, subArray.length);
        assertArrayEquals(new String[]{one, two, three}, toCut);
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
        assertArrayEquals(new byte[]{'a', 'a', 'b', 'c'}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new byte[]{'a', 'a'}, joined);
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
        assertArrayEquals(new int[]{1, 1, 2, 3}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new int[]{1, 1}, joined);
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
        assertArrayEquals(new char[]{'a', 'a', 'b', 'c'}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new char[]{'a', 'a'}, joined);
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
        assertArrayEquals(new String[]{"a", "a"}, joined);
    }

    /**
     * Test of join method, of class ArrayOperations.
     */
    @Test
    public void testJoin_ObjectArrArr() {
        String one = "1";;
        String two = "2";
        String three = "3";
        String[] empty = new String[0];
        String[] single = new String[] {one};
        String[] multiple = new String[] {one, two, three};
        Object[] joined = ArrayOperations.join(empty);
        assertEquals(0, joined.length);
        assertNotSame(joined, empty);
        joined = ArrayOperations.join(empty, single, multiple, empty);
        assertEquals(4, joined.length);
        assertArrayEquals(new String[] {one, one, two, three}, joined);
        joined = ArrayOperations.join(single, single);
        assertEquals(2, joined.length);
        assertArrayEquals(new String[]{one, one}, joined);
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
        assertArrayEquals(new byte[]{'a', 'c'}, deleted);
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
        assertArrayEquals(new int[]{1, 3}, deleted);
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
        assertArrayEquals(new char[]{'b'}, deleted);
        deleted = ArrayOperations.delete(deleted, 0);
        assertEquals(0, deleted.length);
        deleted = ArrayOperations.delete(toDelete, 1);
        assertEquals(2, deleted.length);
        assertArrayEquals(new char[]{'a', 'c'}, deleted);
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
        assertArrayEquals(new String[]{"a", "c"}, deleted);
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
        assertArrayEquals(new Object[]{"a", "c"}, deleted);
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
        assertTrue(ArrayOperations.contradiction(new boolean[]{false}));
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
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_byte_byteArr() {
        byte[] findArray = new byte[] {'a', 'b', 'a', 'c'};
        try {
            assertEquals(0, ArrayOperations.findInSequence((byte) 'a', findArray));
            assertEquals(1, ArrayOperations.findInSequence((byte) 'b', findArray));
            assertEquals(3, ArrayOperations.findInSequence((byte) 'c', findArray));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
        try {
            ArrayOperations.findInSequence((byte) 'd', findArray);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_int_intArr() {
        int[] findArray = new int[] {1, 2, 1, 3};
        try {
            assertEquals(0, ArrayOperations.findInSequence(1, findArray));
            assertEquals(1, ArrayOperations.findInSequence(2, findArray));
            assertEquals(3, ArrayOperations.findInSequence(3, findArray));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
        try {
            ArrayOperations.findInSequence(4, findArray);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_float_floatArr() {
        float[] findArray = new float[] {1, 2, 1, 3};
        try {
            assertEquals(0, ArrayOperations.findInSequence(1, findArray));
            assertEquals(1, ArrayOperations.findInSequence(2, findArray));
            assertEquals(3, ArrayOperations.findInSequence(3, findArray));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
        try {
            ArrayOperations.findInSequence(4, findArray);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_double_doubleArr() {
        double[] findArray = new double[] {1, 2, 1, 3};
        try {
            assertEquals(0, ArrayOperations.findInSequence(1, findArray));
            assertEquals(1, ArrayOperations.findInSequence(2, findArray));
            assertEquals(3, ArrayOperations.findInSequence(3, findArray));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
        try {
            ArrayOperations.findInSequence(4, findArray);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_char_charArr() {
        char[] findArray = new char[] {'a', 'b', 'a', 'c'};
        try {
            assertEquals(0, ArrayOperations.findInSequence('a', findArray));
            assertEquals(1, ArrayOperations.findInSequence('b', findArray));
            assertEquals(3, ArrayOperations.findInSequence('c', findArray));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
        try {
            ArrayOperations.findInSequence('d', findArray);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_String_StringArr() {
        String[] findArray = new String[] {"a", "b", "a", "c"};
        try {
            assertEquals(0, ArrayOperations.findInSequence("a", findArray));
            assertEquals(1, ArrayOperations.findInSequence("b", findArray));
            assertEquals(3, ArrayOperations.findInSequence("c", findArray));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
        try {
            ArrayOperations.findInSequence("d", findArray);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_3args_1() {
        String[] findArray = new String[] {"first:a", "b:b", "other:a", "c:c"};
        KeyValuePairMatcher keyMatcher = new KeyValuePairMatcher();
        try {
            ArrayOperations.findInSequence("a", findArray, keyMatcher);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            assertEquals(2, ArrayOperations.findInSequence("other", findArray, keyMatcher));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_3args_2() {
        byte[] findArray = new byte[] {'a', 'b', 'a', 'c'};
        try {
            ArrayOperations.findInSequence((byte) 'a', findArray, 3);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            ArrayOperations.findInSequence((byte) 'd', findArray, 0);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            assertEquals(0, ArrayOperations.findInSequence((byte) 'a', findArray, 0));
            assertEquals(2, ArrayOperations.findInSequence((byte) 'a', findArray, 1));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_3args_3() {
        int[] findArray = new int[] {1, 2, 1, 3};
        try {
            ArrayOperations.findInSequence(1, findArray, 3);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            ArrayOperations.findInSequence(4, findArray, 0);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            assertEquals(0, ArrayOperations.findInSequence(1, findArray, 0));
            assertEquals(2, ArrayOperations.findInSequence(1, findArray, 1));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_3args_4() {
        float[] findArray = new float[] {1, 2, 1, 3};
        try {
            ArrayOperations.findInSequence(1, findArray, 3);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            ArrayOperations.findInSequence(4, findArray, 0);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            assertEquals(0, ArrayOperations.findInSequence(1, findArray, 0));
            assertEquals(2, ArrayOperations.findInSequence(1, findArray, 1));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_3args_5() {
        double[] findArray = new double[] {1, 2, 1, 3};
        try {
            ArrayOperations.findInSequence(1, findArray, 3);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            ArrayOperations.findInSequence(4, findArray, 0);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            assertEquals(0, ArrayOperations.findInSequence(1, findArray, 0));
            assertEquals(2, ArrayOperations.findInSequence(1, findArray, 1));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_3args_6() {
        char[] findArray = new char[] {'a', 'b', 'a', 'c'};
        try {
            ArrayOperations.findInSequence('a', findArray, 3);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            ArrayOperations.findInSequence('d', findArray, 0);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            assertEquals(0, ArrayOperations.findInSequence('a', findArray, 0));
            assertEquals(2, ArrayOperations.findInSequence('a', findArray, 1));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_3args_7() {
        String[] findArray = new String[] {"a", "b", "a", "c"};
        try {
            ArrayOperations.findInSequence("a", findArray, 3);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            ArrayOperations.findInSequence("d", findArray, 0);
            fail("findInSequence did not throw not found exception as expected");
        }
        catch (NotFoundException nfe) {}
        try {
            assertEquals(0, ArrayOperations.findInSequence("a", findArray, 0));
            assertEquals(2, ArrayOperations.findInSequence("a", findArray, 1));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
    }

    /**
     * Test of findInSequence method, of class ArrayOperations.
     */
    @Test
    public void testFindInSequence_4args() {
        String[] findArray = new String[] {"first:a", "b:b", "other:a", "c:c"};
        KeyValuePairMatcher keyMatcher = new KeyValuePairMatcher();
        try {
            assertEquals(2, ArrayOperations.findInSequence("other", findArray, 1, keyMatcher));
        }
        catch (NotFoundException nfe) {
            fail("findInSequence did not find existing element");
        }
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
        int[] array = new int[] {1, 50, 10, 15, Integer.MAX_VALUE};
        int[] array2 = new int[] {Integer.MIN_VALUE, -100, 0, 55};
        assertEquals(0, ArrayOperations.findClosestIndex(array, Integer.MIN_VALUE));
        assertEquals(0, ArrayOperations.findClosestIndex(array2, Integer.MIN_VALUE));
        assertEquals(0, ArrayOperations.findClosestIndex(array, -51));
        assertEquals(1, ArrayOperations.findClosestIndex(array2, -51));
        assertEquals(0, ArrayOperations.findClosestIndex(array, 2));
        assertEquals(2, ArrayOperations.findClosestIndex(array2, 2));
        assertEquals(2, ArrayOperations.findClosestIndex(array, 6));
        assertEquals(3, ArrayOperations.findClosestIndex(array, 15));
        assertEquals(3, ArrayOperations.findClosestIndex(array, 25));
        assertEquals(1, ArrayOperations.findClosestIndex(array, 40));
        assertEquals(1, ArrayOperations.findClosestIndex(array, 51));
        assertEquals(4, ArrayOperations.findClosestIndex(array, Integer.MAX_VALUE));
        assertEquals(3, ArrayOperations.findClosestIndex(array2, Integer.MAX_VALUE));
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_doubleArr_double() {
        double[] array = new double[] {1, 50, 10, 15, 9.9, 9.90000000000001};
        double[] array2 = new double[] {-Double.MAX_VALUE, Double.MAX_VALUE};
        assertEquals(1, ArrayOperations.findClosest(array, Double.MIN_VALUE), 0);
        assertEquals(1, ArrayOperations.findClosest(array, -51), 0);
        assertEquals(1, ArrayOperations.findClosest(array, 2), 0);
        assertEquals(9.9, ArrayOperations.findClosest(array, 6), 0);
        assertEquals(9.9, ArrayOperations.findClosest(array, 9.9), 0);
        assertEquals(9.90000000000001, ArrayOperations.findClosest(array, 9.91), 0);
        assertEquals(15, ArrayOperations.findClosest(array, 15), 0);
        assertEquals(15, ArrayOperations.findClosest(array, 25), 0);
        assertEquals(50, ArrayOperations.findClosest(array, 40), 0);
        assertEquals(50, ArrayOperations.findClosest(array, 51), 0);
        assertEquals(50, ArrayOperations.findClosest(array, Double.MAX_VALUE), 0);
        assertEquals(Double.MAX_VALUE, ArrayOperations.findClosest(array2, 5), 0);
    }

    /**
     * Test of findClosestIndex method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_doubleArr_double() {
        double[] array = new double[] {1, 50, 10, 15, 9.9, 9.90000000000001};
        assertEquals(0, ArrayOperations.findClosestIndex(array, Double.MIN_VALUE), 0);
        assertEquals(0, ArrayOperations.findClosestIndex(array, -51), 0);
        assertEquals(0, ArrayOperations.findClosestIndex(array, 2), 0);
        assertEquals(4, ArrayOperations.findClosestIndex(array, 6), 0);
        assertEquals(4, ArrayOperations.findClosestIndex(array, 9.9), 0);
        assertEquals(5, ArrayOperations.findClosestIndex(array, 9.91), 0);
        assertEquals(3, ArrayOperations.findClosestIndex(array, 15), 0);
        assertEquals(3, ArrayOperations.findClosestIndex(array, 25), 0);
        assertEquals(1, ArrayOperations.findClosestIndex(array, 40), 0);
        assertEquals(1, ArrayOperations.findClosestIndex(array, 51), 0);
        assertEquals(1, ArrayOperations.findClosestIndex(array, Double.MAX_VALUE), 0);
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_floatArr_float() {
        float[] array = new float[] {1, 50, 10, 15, 9.9f, 9.9000001f};
        assertEquals(1, ArrayOperations.findClosest(array, Float.MIN_VALUE), 0);
        assertEquals(1, ArrayOperations.findClosest(array, -51), 0);
        assertEquals(1, ArrayOperations.findClosest(array, 2), 0);
        assertEquals(9.9f, ArrayOperations.findClosest(array, 6), 0);
        assertEquals(9.9f, ArrayOperations.findClosest(array, 9.9f), 0);
        assertEquals(9.9000001f, ArrayOperations.findClosest(array, 9.91f), 0);
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
        float[] array = new float[] {1, 50, 10, 15, 9.9f, 9.9000001f};
        assertEquals(0, ArrayOperations.findClosestIndex(array, Float.MIN_VALUE), 0);
        assertEquals(0, ArrayOperations.findClosestIndex(array, -51), 0);
        assertEquals(0, ArrayOperations.findClosestIndex(array, 2), 0);
        assertEquals(4, ArrayOperations.findClosestIndex(array, 6), 0);
        assertEquals(4, ArrayOperations.findClosestIndex(array, 9.9f), 0);
        assertEquals(5, ArrayOperations.findClosestIndex(array, 9.91f), 0);
        assertEquals(3, ArrayOperations.findClosestIndex(array, 15), 0);
        assertEquals(3, ArrayOperations.findClosestIndex(array, 25), 0);
        assertEquals(1, ArrayOperations.findClosestIndex(array, 40), 0);
        assertEquals(1, ArrayOperations.findClosestIndex(array, 51), 0);
        assertEquals(1, ArrayOperations.findClosestIndex(array, Float.MAX_VALUE), 0);
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_StringArr_String() {
        String[] array = new String[] {"string", "array", "new", "int", "assert", "equals"};
        assertEquals("new", ArrayOperations.findClosest(array, "a"));
        assertEquals("array", ArrayOperations.findClosest(array, "artsy"));
        assertEquals("equals", ArrayOperations.findClosest(array, "because"));
        assertEquals("new", ArrayOperations.findClosest(array, "d"));
        assertEquals("new", ArrayOperations.findClosest(array, "new"));
        assertEquals("new", ArrayOperations.findClosest(array, "kind"));
        assertEquals("array", ArrayOperations.findClosest(array, "sassy"));  // returns new, which is not intuitive
        assertEquals("new", ArrayOperations.findClosest(array, "suit"));
        assertEquals("new", ArrayOperations.findClosest(array, "z"));
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosest_ObjectArr_Object() {
        String[] array = new String[] {"string", "array", "new", "int", "assert", "equals"};
        DistanceFunction dfc = new LexicographicDistanceFunction(Mode.COUNT_DIFF_CHARS);
        assertEquals("new", ArrayOperations.findClosest(array, "nes", dfc));
        assertEquals("array", ArrayOperations.findClosest(array, "artsy", dfc));
        assertEquals("new", ArrayOperations.findClosest(array, "newly", dfc));
        assertEquals("new", ArrayOperations.findClosest(array, "dessert", dfc));
        assertEquals("int", ArrayOperations.findClosest(array, "int", dfc));
        assertEquals("string", ArrayOperations.findClosest(array, "strong", dfc));
        assertEquals("string", ArrayOperations.findClosest(array, "attire", dfc));
        assertEquals("array", ArrayOperations.findClosest(array, "sassy", dfc));
        DistanceFunction dfd = new LexicographicDistanceFunction(Mode.DEFAULT);
        assertEquals("new", ArrayOperations.findClosest(array, "nes", dfd));
        assertEquals("array", ArrayOperations.findClosest(array, "artsy", dfd));
        assertEquals("array", ArrayOperations.findClosest(array, "newly", dfd));
        assertEquals("equals", ArrayOperations.findClosest(array, "dessert", dfd));
        assertEquals("int", ArrayOperations.findClosest(array, "int", dfd));
        assertEquals("string", ArrayOperations.findClosest(array, "strong", dfd));
        assertEquals("assert", ArrayOperations.findClosest(array, "attire", dfd));
        assertEquals("array", ArrayOperations.findClosest(array, "sassy", dfd));
        DistanceFunction edf = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.DEFAULT);
        assertEquals("new", ArrayOperations.findClosest(array, "nes", edf));
        assertEquals("array", ArrayOperations.findClosest(array, "artsy", edf));
        assertEquals("new", ArrayOperations.findClosest(array, "newly", edf));
        assertEquals("assert", ArrayOperations.findClosest(array, "dessert", edf));
        assertEquals("int", ArrayOperations.findClosest(array, "int", edf));
        assertEquals("string", ArrayOperations.findClosest(array, "strong", edf));
        assertEquals("string", ArrayOperations.findClosest(array, "attire", edf));
        assertEquals("equals", ArrayOperations.findClosest(array, "because", edf));
        assertEquals("array", ArrayOperations.findClosest(array, "sassy", edf));
        DistanceFunction edfa = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.ALPHABETIC_REPLACE);
        assertEquals("new", ArrayOperations.findClosest(array, "nes", edfa));
        assertEquals("array", ArrayOperations.findClosest(array, "artsy", edfa));
        assertEquals("new", ArrayOperations.findClosest(array, "newly", edfa));
        assertEquals("assert", ArrayOperations.findClosest(array, "dessert", edfa));
        assertEquals("int", ArrayOperations.findClosest(array, "int", edfa));
        assertEquals("string", ArrayOperations.findClosest(array, "strong", edfa));
        assertEquals("assert", ArrayOperations.findClosest(array, "attire", edfa));
        assertEquals("assert", ArrayOperations.findClosest(array, "because", edfa));
        assertEquals("array", ArrayOperations.findClosest(array, "sassy", edfa));
        DistanceFunction edfk = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.KEYBOARD_REPLACE);
        assertEquals("new", ArrayOperations.findClosest(array, "nes", edfk));
        assertEquals("array", ArrayOperations.findClosest(array, "artsy", edfk));
        assertEquals("new", ArrayOperations.findClosest(array, "newly", edfk));
        assertEquals("assert", ArrayOperations.findClosest(array, "dessert", edfk));
        assertEquals("int", ArrayOperations.findClosest(array, "int", edfk));
        assertEquals("string", ArrayOperations.findClosest(array, "strong", edfk));
        assertEquals("string", ArrayOperations.findClosest(array, "attire", edfk));
        assertEquals("equals", ArrayOperations.findClosest(array, "because", edfk));
        assertEquals("array", ArrayOperations.findClosest(array, "sassy", edfk));
        AlphabeticDistanceFunction adf = new AlphabeticDistanceFunction();
        assertEquals("array", ArrayOperations.findClosest(array, "a", adf));
        assertEquals("array", ArrayOperations.findClosest(array, "artsy", adf));
        assertEquals("assert", ArrayOperations.findClosest(array, "because", adf));
        assertEquals("equals", ArrayOperations.findClosest(array, "d", adf));
        assertEquals("new", ArrayOperations.findClosest(array, "new", adf));
        assertEquals("int", ArrayOperations.findClosest(array, "kind", adf));
        assertEquals("string", ArrayOperations.findClosest(array, "sassy", adf));
        assertEquals("new", ArrayOperations.findClosest(array, "newly", adf));
        assertEquals("equals", ArrayOperations.findClosest(array, "dessert", adf));
    }

    /**
     * Test of findClosestIndex method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_StringArr_String() {
        String[] array = new String[] {"string", "array", "new", "int", "assert", "equals"};
        assertEquals(2, ArrayOperations.findClosestIndex(array, "a"));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "artsy"));
        assertEquals(5, ArrayOperations.findClosestIndex(array, "because"));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "d"));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "new"));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "kind"));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "sassy"));  // returns new, which is not intuitive
        assertEquals(2, ArrayOperations.findClosestIndex(array, "suit"));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "z"));
    }

    /**
     * Test of findClosest method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndex_ObjectArr_Object() {
        String[] array = new String[] {"string", "array", "new", "int", "assert", "equals"};
        DistanceFunction dfc = new LexicographicDistanceFunction(Mode.COUNT_DIFF_CHARS);
        assertEquals(2, ArrayOperations.findClosestIndex(array, "nes", dfc));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "artsy", dfc));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "newly", dfc));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "dessert", dfc));
        assertEquals(3, ArrayOperations.findClosestIndex(array, "int", dfc));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "strong", dfc));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "attire", dfc));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "sassy", dfc));
        DistanceFunction dfd = new LexicographicDistanceFunction(Mode.DEFAULT);
        assertEquals(2, ArrayOperations.findClosestIndex(array, "nes", dfd));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "artsy", dfd));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "newly", dfd));
        assertEquals(5, ArrayOperations.findClosestIndex(array, "dessert", dfd));
        assertEquals(3, ArrayOperations.findClosestIndex(array, "int", dfd));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "strong", dfd));
        assertEquals(4, ArrayOperations.findClosestIndex(array, "attire", dfd));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "sassy", dfd));
        DistanceFunction edf = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.DEFAULT);
        assertEquals(2, ArrayOperations.findClosestIndex(array, "nes", edf));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "artsy", edf));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "newly", edf));
        assertEquals(4, ArrayOperations.findClosestIndex(array, "dessert", edf));
        assertEquals(3, ArrayOperations.findClosestIndex(array, "int", edf));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "strong", edf));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "attire", edf));
        assertEquals(5, ArrayOperations.findClosestIndex(array, "because", edf));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "sassy", edf));
        DistanceFunction edfa = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.ALPHABETIC_REPLACE);
        assertEquals(2, ArrayOperations.findClosestIndex(array, "nes", edfa));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "artsy", edfa));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "newly", edfa));
        assertEquals(4, ArrayOperations.findClosestIndex(array, "dessert", edfa));
        assertEquals(3, ArrayOperations.findClosestIndex(array, "int", edfa));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "strong", edfa));
        assertEquals(4, ArrayOperations.findClosestIndex(array, "attire", edfa));
        assertEquals(4, ArrayOperations.findClosestIndex(array, "because", edfa));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "sassy", edfa));
        DistanceFunction edfk = new StringEditDistanceFunction(StringEditDistanceFunction.Mode.KEYBOARD_REPLACE);
        assertEquals(2, ArrayOperations.findClosestIndex(array, "nes", edfk));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "artsy", edfk));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "newly", edfk));
        assertEquals(4, ArrayOperations.findClosestIndex(array, "dessert", edfk));
        assertEquals(3, ArrayOperations.findClosestIndex(array, "int", edfk));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "strong", edfk));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "attire", edfk));
        assertEquals(5, ArrayOperations.findClosestIndex(array, "because", edfk));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "sassy", edfk));
        AlphabeticDistanceFunction adf = new AlphabeticDistanceFunction();
        assertEquals(1, ArrayOperations.findClosestIndex(array, "a", adf));
        assertEquals(1, ArrayOperations.findClosestIndex(array, "artsy", adf));
        assertEquals(4, ArrayOperations.findClosestIndex(array, "because", adf));
        assertEquals(5, ArrayOperations.findClosestIndex(array, "d", adf));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "new", adf));
        assertEquals(3, ArrayOperations.findClosestIndex(array, "kind", adf));
        assertEquals(0, ArrayOperations.findClosestIndex(array, "sassy", adf));
        assertEquals(2, ArrayOperations.findClosestIndex(array, "newly", adf));
        assertEquals(5, ArrayOperations.findClosestIndex(array, "dessert", adf));
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
        char[] array = new char[] {'s', 'A', 'n', 'i', 'a', 'e'};
        assertEquals(4, ArrayOperations.findClosestIndex(array, 'a'));
        assertEquals(4, ArrayOperations.findClosestIndex(array, 'b'));
        assertEquals(5, ArrayOperations.findClosestIndex(array, 'd'));
        assertEquals(2, ArrayOperations.findClosestIndex(array, 'n'));
        assertEquals(3, ArrayOperations.findClosestIndex(array, 'k'));
        assertEquals(0, ArrayOperations.findClosestIndex(array, 's'));
        assertEquals(0, ArrayOperations.findClosestIndex(array, 'z'));
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_intArr_int() {
        int[] array = new int[] {1, 10, 15, 50, Integer.MAX_VALUE};
        int[] array2 = new int[] {Integer.MIN_VALUE, -100, 0, 55};
        assertEquals(1, ArrayOperations.findClosestInOrder(array, Integer.MIN_VALUE));
        assertEquals(Integer.MIN_VALUE, ArrayOperations.findClosestInOrder(array2, Integer.MIN_VALUE));
        assertEquals(1, ArrayOperations.findClosestInOrder(array, -51));
        assertEquals(-100, ArrayOperations.findClosestInOrder(array2, -51));
        assertEquals(1, ArrayOperations.findClosestInOrder(array, 2));
        assertEquals(0, ArrayOperations.findClosestInOrder(array2, 2));
        assertEquals(10, ArrayOperations.findClosestInOrder(array, 6));
        assertEquals(15, ArrayOperations.findClosestInOrder(array, 15));
        assertEquals(15, ArrayOperations.findClosestInOrder(array, 25));
        assertEquals(50, ArrayOperations.findClosestInOrder(array, 40));
        assertEquals(50, ArrayOperations.findClosestInOrder(array, 51));
        assertEquals(Integer.MAX_VALUE, ArrayOperations.findClosestInOrder(array, Integer.MAX_VALUE));
        assertEquals(55, ArrayOperations.findClosestInOrder(array2, Integer.MAX_VALUE));
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     * TODO: FIX
     */
    @Test
    public void testFindClosestIndexInOrder_intArr_int() {
        int[] array = new int[] {1, 10, 15, 50, Integer.MAX_VALUE};
        int[] array2 = new int[] {Integer.MIN_VALUE, -100, 0, 55};
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, Integer.MIN_VALUE));
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array2, Integer.MIN_VALUE));
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, -51));
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array2, -51));
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, 2));
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array2, 2));
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, 6));
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array, 15));
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array, 25));
        assertEquals(3, ArrayOperations.findClosestIndexInOrder(array, 40));
        assertEquals(3, ArrayOperations.findClosestIndexInOrder(array, 51));
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, Integer.MAX_VALUE));
        assertEquals(3, ArrayOperations.findClosestIndexInOrder(array2, Integer.MAX_VALUE));
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_doubleArr_double() {
        double[] array = new double[] {1, 9.9, 9.90000000000001, 10, 15, 50, Double.MAX_VALUE};
        double[] array2 = new double[] {-Double.MAX_VALUE, Double.MAX_VALUE};
//        assertEquals(Double.MAX_VALUE, ArrayOperations.findClosestInOrder(array2, 2), 0);
//        assertTrue(Math.abs(-Double.MAX_VALUE - 2) > Math.abs(Double.MAX_VALUE - 2));
        assertEquals(1, ArrayOperations.findClosestInOrder(array, Double.MIN_VALUE), 0);
        assertEquals(1, ArrayOperations.findClosestInOrder(array, -51), 0);
        assertEquals(1, ArrayOperations.findClosestInOrder(array, 2), 0);
        assertEquals(9.9, ArrayOperations.findClosestInOrder(array, 6), 0);
        assertEquals(9.9, ArrayOperations.findClosestInOrder(array, 9.9), 0);
        assertEquals(9.90000000000001, ArrayOperations.findClosestInOrder(array, 9.91), 0);
        assertEquals(15, ArrayOperations.findClosestInOrder(array, 15), 0);
        assertEquals(15, ArrayOperations.findClosestInOrder(array, 25), 0);
        assertEquals(50, ArrayOperations.findClosestInOrder(array, 40), 0);
        assertEquals(50, ArrayOperations.findClosestInOrder(array, 51), 0);
        assertEquals(Double.MAX_VALUE, ArrayOperations.findClosestInOrder(array, Double.MAX_VALUE), 0);
        assertEquals(Double.MAX_VALUE, ArrayOperations.findClosestInOrder(array, Double.MAX_VALUE - 1), 0);
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_doubleArr_double() {
        double[] array = new double[] {1, 9.9, 9.90000000000001, 10, 15, 50};
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, Double.MIN_VALUE), 0);
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, -51), 0);
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, 2), 0);
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, 6), 0);
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, 9.9), 0);
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array, 9.91), 0);
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, 15), 0);
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, 25), 0);
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, 40), 0);
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, 51), 0);
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, Double.MAX_VALUE), 0);
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_floatArr_float() {
        float[] array = new float[] {1, 9.9f, 9.9000001f, 10, 15, 50};
        assertEquals(1, ArrayOperations.findClosestInOrder(array, Float.MIN_VALUE), 0);
        assertEquals(1, ArrayOperations.findClosestInOrder(array, -51), 0);
        assertEquals(1, ArrayOperations.findClosestInOrder(array, 2), 0);
        assertEquals(9.9f, ArrayOperations.findClosestInOrder(array, 6), 0);
        assertEquals(9.9f, ArrayOperations.findClosestInOrder(array, 9.9f), 0);
        assertEquals(9.9000001f, ArrayOperations.findClosestInOrder(array, 9.91f), 0);
        assertEquals(15, ArrayOperations.findClosestInOrder(array, 15), 0);
        assertEquals(15, ArrayOperations.findClosestInOrder(array, 25), 0);
        assertEquals(50, ArrayOperations.findClosestInOrder(array, 40), 0);
        assertEquals(50, ArrayOperations.findClosestInOrder(array, 51), 0);
        assertEquals(50, ArrayOperations.findClosestInOrder(array, Float.MAX_VALUE), 0);
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_floatArr_float() {
        float[] array = new float[] {1, 9.9f, 9.9000001f, 10, 15, 50};
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, Float.MIN_VALUE), 0);
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, -51), 0);
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, 2), 0);
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, 6), 0);
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, 9.9f), 0);
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array, 9.91f), 0);
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, 15), 0);
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, 25), 0);
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, 40), 0);
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, 51), 0);
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, Float.MAX_VALUE), 0);
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_charArr_char() {
        char[] array = new char[] {'A', 'a', 'e', 'i', 'n', 's'};
        assertEquals('a', ArrayOperations.findClosestInOrder(array, 'a'));
        assertEquals('a', ArrayOperations.findClosestInOrder(array, 'b'));
        assertEquals('e', ArrayOperations.findClosestInOrder(array, 'd'));
        assertEquals('n', ArrayOperations.findClosestInOrder(array, 'n'));
        assertEquals('i', ArrayOperations.findClosestInOrder(array, 'k'));
        assertEquals('s', ArrayOperations.findClosestInOrder(array, 's'));
        assertEquals('s', ArrayOperations.findClosestInOrder(array, 'z'));
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_charArr_char() {
        char[] array = new char[] {'A', 'a', 'e', 'i', 'n', 's'};
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, 'a'));
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, 'b'));
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array, 'd'));
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, 'n'));
        assertEquals(3, ArrayOperations.findClosestIndexInOrder(array, 'k'));
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, 's'));
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, 'z'));
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_ObjectArr_Object() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        DistanceFunction<String> adf = new AlphabeticDistanceFunction();
        Comparator<String> ac = new AlphabeticStringComparator();
        assertEquals("array", ArrayOperations.findClosestInOrder(array, "a", adf, ac));
        assertEquals("array", ArrayOperations.findClosestInOrder(array, "artsy", adf, ac));
        assertEquals("assert", ArrayOperations.findClosestInOrder(array, "because", adf, ac));
        assertEquals("equals", ArrayOperations.findClosestInOrder(array, "d", adf, ac));
        assertEquals("new", ArrayOperations.findClosestInOrder(array, "new", adf, ac));
        assertEquals("int", ArrayOperations.findClosestInOrder(array, "kind", adf, ac));
        assertEquals("string", ArrayOperations.findClosestInOrder(array, "sassy", adf, ac));
        assertEquals("string", ArrayOperations.findClosestInOrder(array, "suit", adf, ac));
        assertEquals("string", ArrayOperations.findClosestInOrder(array, "z", adf, ac));
    }

    /**
     * Test of findClosestInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestInOrder_StringArr_String() {
//        System.out.println("Output for testFindClosestInOrder_StringArr_String");
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        assertEquals("array", ArrayOperations.findClosestInOrder(array, "a"));
        assertEquals("array", ArrayOperations.findClosestInOrder(array, "artsy"));
        assertEquals("equals", ArrayOperations.findClosestInOrder(array, "because"));
        assertEquals("assert", ArrayOperations.findClosestInOrder(array, "d"));
        assertEquals("new", ArrayOperations.findClosestInOrder(array, "new"));
        assertEquals("int", ArrayOperations.findClosestInOrder(array, "kind"));
        assertEquals("new", ArrayOperations.findClosestInOrder(array, "sassy"));
        assertEquals("string", ArrayOperations.findClosestInOrder(array, "suit"));
        assertEquals("string", ArrayOperations.findClosestInOrder(array, "z"));
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_ObjectArr_Object() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        DistanceFunction<String> adf = new AlphabeticDistanceFunction();
        Comparator<String> ac = new AlphabeticStringComparator();
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, "a", adf, ac));
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, "artsy", adf, ac));
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, "because", adf, ac));
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array, "d", adf, ac));
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, "new", adf, ac));
        assertEquals(3, ArrayOperations.findClosestIndexInOrder(array, "kind", adf, ac));
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, "sassy", adf, ac));
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, "suit", adf, ac));
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, "z", adf, ac));
    }

    /**
     * Test of findClosestIndexInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindClosestIndexInOrder_StringArr_String() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, "a"));
        assertEquals(0, ArrayOperations.findClosestIndexInOrder(array, "artsy"));
        assertEquals(2, ArrayOperations.findClosestIndexInOrder(array, "because"));
        assertEquals(1, ArrayOperations.findClosestIndexInOrder(array, "d"));
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, "new"));
        assertEquals(3, ArrayOperations.findClosestIndexInOrder(array, "kind"));
        assertEquals(4, ArrayOperations.findClosestIndexInOrder(array, "sassy"));
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, "suit"));
        assertEquals(5, ArrayOperations.findClosestIndexInOrder(array, "z"));
    }

    /**
     * Test of findNextInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindNextInOrder_GenericType_GenericType() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        assertEquals("array", ArrayOperations.findNextInOrder(array, "a"));
        assertEquals("assert", ArrayOperations.findNextInOrder(array, "artsy"));
        assertEquals("equals", ArrayOperations.findNextInOrder(array, "because"));
        assertEquals("equals", ArrayOperations.findNextInOrder(array, "d"));
        assertEquals("new", ArrayOperations.findNextInOrder(array, "new"));
        assertEquals("new", ArrayOperations.findNextInOrder(array, "kind"));
        assertEquals("string", ArrayOperations.findNextInOrder(array, "sassy"));
        assertNull(ArrayOperations.findNextInOrder(array, "suit"));
        assertNull(ArrayOperations.findNextInOrder(array, "z"));
    }

    /**
     * Test of findPositionInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindPositionInOrder_GenericType_GenericType() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        assertEquals(0, ArrayOperations.findPositionInOrder(array, "a"));
        assertEquals(1, ArrayOperations.findPositionInOrder(array, "artsy"));
        assertEquals(2, ArrayOperations.findPositionInOrder(array, "because"));
        assertEquals(2, ArrayOperations.findPositionInOrder(array, "d"));
        assertEquals(4, ArrayOperations.findPositionInOrder(array, "new"));
        assertEquals(4, ArrayOperations.findPositionInOrder(array, "kind"));
        assertEquals(5, ArrayOperations.findPositionInOrder(array, "sassy"));
        assertEquals(6, ArrayOperations.findPositionInOrder(array, "suit"));
        assertEquals(6, ArrayOperations.findPositionInOrder(array, "z"));
    }

    /**
     * Test of findPositionInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindPositionInOrder_intArr_int() {
        int[] array = new int[] {1, 10, 15, 50, Integer.MAX_VALUE};
        int[] array2 = new int[] {Integer.MIN_VALUE, -100, 0, 55};
        assertEquals(0, ArrayOperations.findPositionInOrder(array, Integer.MIN_VALUE));
        assertEquals(0, ArrayOperations.findPositionInOrder(array2, Integer.MIN_VALUE));
        assertEquals(0, ArrayOperations.findPositionInOrder(array, -51));
        assertEquals(2, ArrayOperations.findPositionInOrder(array2, -51));
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 2));
        assertEquals(3, ArrayOperations.findPositionInOrder(array2, 2));
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 6));
        assertEquals(2, ArrayOperations.findPositionInOrder(array, 15));
        assertEquals(3, ArrayOperations.findPositionInOrder(array, 25));
        assertEquals(3, ArrayOperations.findPositionInOrder(array, 40));
        assertEquals(4, ArrayOperations.findPositionInOrder(array, 51));
        assertEquals(4, ArrayOperations.findPositionInOrder(array, Integer.MAX_VALUE));
        assertEquals(4, ArrayOperations.findPositionInOrder(array2, Integer.MAX_VALUE));
    }

    /**
     * Test of findPositionInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindPositionInOrder_floatArr_float() {
        float[] array = new float[] {1, 9.9f, 9.9000001f, 10, 15, 50};
        assertEquals(0, ArrayOperations.findPositionInOrder(array, Float.MIN_VALUE), 0);
        assertEquals(0, ArrayOperations.findPositionInOrder(array, -51), 0);
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 2), 0);
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 6), 0);
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 9.9f), 0);
        assertEquals(3, ArrayOperations.findPositionInOrder(array, 9.91f), 0);
        assertEquals(4, ArrayOperations.findPositionInOrder(array, 15), 0);
        assertEquals(5, ArrayOperations.findPositionInOrder(array, 25), 0);
        assertEquals(5, ArrayOperations.findPositionInOrder(array, 40), 0);
        assertEquals(6, ArrayOperations.findPositionInOrder(array, 51), 0);
        assertEquals(6, ArrayOperations.findPositionInOrder(array, Float.MAX_VALUE), 0);
    }

    /**
     * Test of findPositionInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindPositionInOrder_doubleArr_double() {
        double[] array = new double[] {1, 9.9, 9.90000000000002, 10, 15, 50};
        assertEquals(0, ArrayOperations.findPositionInOrder(array, Double.MIN_VALUE), 0);
        assertEquals(0, ArrayOperations.findPositionInOrder(array, -51), 0);
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 2), 0);
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 6), 0);
        assertEquals(1, ArrayOperations.findPositionInOrder(array, 9.9), 0);
        assertEquals(3, ArrayOperations.findPositionInOrder(array, 9.91), 0);
        assertEquals(2, ArrayOperations.findPositionInOrder(array, 9.90000000000001), 0);
        assertEquals(4, ArrayOperations.findPositionInOrder(array, 15), 0);
        assertEquals(5, ArrayOperations.findPositionInOrder(array, 25), 0);
        assertEquals(5, ArrayOperations.findPositionInOrder(array, 40), 0);
        assertEquals(6, ArrayOperations.findPositionInOrder(array, 51), 0);
        assertEquals(6, ArrayOperations.findPositionInOrder(array, Double.MAX_VALUE), 0);
    }

    /**
     * Test of findNextInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindNextInOrder_3args() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        AlphabeticStringComparator asc = new AlphabeticStringComparator();
        assertEquals("array", ArrayOperations.findNextInOrder(array, "a", asc));
        assertEquals("assert", ArrayOperations.findNextInOrder(array, "artsy", asc));
        assertEquals("equals", ArrayOperations.findNextInOrder(array, "because", asc));
        assertEquals("equals", ArrayOperations.findNextInOrder(array, "d", asc));
        assertEquals("new", ArrayOperations.findNextInOrder(array, "new", asc));
        assertEquals("new", ArrayOperations.findNextInOrder(array, "kind", asc));
        assertEquals("string", ArrayOperations.findNextInOrder(array, "sassy", asc));
        assertNull(ArrayOperations.findNextInOrder(array, "suit", asc));
        assertNull(ArrayOperations.findNextInOrder(array, "z", asc));
    }

    /**
     * Test of findPositionInOrder method, of class ArrayOperations.
     */
    @Test
    public void testFindPositionInOrder_3args() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        AlphabeticStringComparator asc = new AlphabeticStringComparator();
        assertEquals(0, ArrayOperations.findPositionInOrder(array, "a", asc));
        assertEquals(1, ArrayOperations.findPositionInOrder(array, "artsy", asc));
        assertEquals(2, ArrayOperations.findPositionInOrder(array, "because", asc));
        assertEquals(2, ArrayOperations.findPositionInOrder(array, "d", asc));
        assertEquals(4, ArrayOperations.findPositionInOrder(array, "new", asc));
        assertEquals(4, ArrayOperations.findPositionInOrder(array, "kind", asc));
        assertEquals(5, ArrayOperations.findPositionInOrder(array, "sassy", asc));
        assertEquals(6, ArrayOperations.findPositionInOrder(array, "suit", asc));
        assertEquals(6, ArrayOperations.findPositionInOrder(array, "z", asc));
    }

    // Not testing normalize methods

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_intArr() {
        int[] array = new int[] {1, 10, 15, 50, Integer.MAX_VALUE};
        int[] reversedArray = ArrayOperations.reverse(array);
        assertEquals(5, reversedArray.length);
        assertEquals(Integer.MAX_VALUE, reversedArray[0]);
        assertEquals(50, reversedArray[1]);
        assertEquals(15, reversedArray[2]);
        assertEquals(10, reversedArray[3]);
        assertEquals(1, reversedArray[4]);
        ArrayOperations.reverseInPlace(array);
        assertEquals(5, array.length);
        assertEquals(Integer.MAX_VALUE, array[0]);
        assertEquals(50, array[1]);
        assertEquals(15, array[2]);
        assertEquals(10, array[3]);
        assertEquals(1, array[4]);
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_floatArr() {
        float[] array = new float[] {1, 9.9f, 9.9000001f, 10, 15, 50};
        float[] reversedArray = ArrayOperations.reverse(array);
        assertEquals(6, reversedArray.length);
        assertEquals(50, reversedArray[0], 0);
        assertEquals(15, reversedArray[1], 0);
        assertEquals(10, reversedArray[2], 0);
        assertEquals(9.9000001f, reversedArray[3], 0);
        assertEquals(9.9f, reversedArray[4], 0);
        assertEquals(1, reversedArray[5], 0);
        ArrayOperations.reverseInPlace(array);
        assertEquals(6, array.length);
        assertEquals(50, array[0], 0);
        assertEquals(15, array[1], 0);
        assertEquals(10, array[2], 0);
        assertEquals(9.9000001f, array[3], 0);
        assertEquals(9.9f, array[4], 0);
        assertEquals(1, array[5], 0);
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_doubleArr() {
        double[] array = new double[] {1, 9.9, 9.90000000000002, 10, 15, 50};
        double[] reversedArray = ArrayOperations.reverse(array);
        assertEquals(6, reversedArray.length);
        assertEquals(50, reversedArray[0], 0);
        assertEquals(15, reversedArray[1], 0);
        assertEquals(10, reversedArray[2], 0);
        assertEquals(9.90000000000002, reversedArray[3], 0);
        assertEquals(9.9, reversedArray[4], 0);
        assertEquals(1, reversedArray[5], 0);
        ArrayOperations.reverseInPlace(array);
        assertEquals(6, array.length);
        assertEquals(50, array[0], 0);
        assertEquals(15, array[1], 0);
        assertEquals(10, array[2], 0);
        assertEquals(9.90000000000002, array[3], 0);
        assertEquals(9.9, array[4], 0);
        assertEquals(1, array[5], 0);
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_charArr() {
        char[] array = new char[] {'A', 'a', 'e', 'i', 'n', 's'};
        char[] reversedArray = ArrayOperations.reverse(array);
        assertEquals(6, reversedArray.length);
        assertEquals('s', reversedArray[0]);
        assertEquals('n', reversedArray[1]);
        assertEquals('i', reversedArray[2]);
        assertEquals('e', reversedArray[3]);
        assertEquals('a', reversedArray[4]);
        assertEquals('A', reversedArray[5]);
        ArrayOperations.reverseInPlace(array);
        assertEquals(6, array.length);
        assertEquals('s', array[0]);
        assertEquals('n', array[1]);
        assertEquals('i', array[2]);
        assertEquals('e', array[3]);
        assertEquals('a', array[4]);
        assertEquals('A', array[5]);
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_StringArr() {
        String[] array = new String[] {"array", "assert", "equals", "int", "new", "string"};
        String[] reversedArray = ArrayOperations.reverse(array);
        assertEquals(6, reversedArray.length);
        assertEquals("string", reversedArray[0]);
        assertEquals("new", reversedArray[1]);
        assertEquals("int", reversedArray[2]);
        assertEquals("equals", reversedArray[3]);
        assertEquals("assert", reversedArray[4]);
        assertEquals("array", reversedArray[5]);
        ArrayOperations.reverseInPlace(array);
        assertEquals(6, array.length);
        assertEquals("string", array[0]);
        assertEquals("new", array[1]);
        assertEquals("int", array[2]);
        assertEquals("equals", array[3]);
        assertEquals("assert", array[4]);
        assertEquals("array", array[5]);
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_booleanArr() {
        boolean[] array = new boolean[] {true, false, true, true};
        boolean[] reversedArray = ArrayOperations.reverse(array);
        assertEquals(4, reversedArray.length);
        assertTrue(reversedArray[0]);
        assertTrue(reversedArray[1]);
        assertFalse(reversedArray[2]);
        assertTrue(reversedArray[3]);
        ArrayOperations.reverseInPlace(array);
        assertEquals(4, array.length);
        assertTrue(array[0]);
        assertTrue(array[1]);
        assertFalse(array[2]);
        assertTrue(array[3]);
    }

    /**
     * Test of reverse method, of class ArrayOperations.
     */
    @Test
    public void testReverse_ObjectArr() {
        String one = "1";
        String ten = "10";
        String fifteen = "15";
        String fifty = "50";
        String max = Integer.toString(Integer.MAX_VALUE);
        String[] array = new String[] {one, ten, fifteen, fifty, max};
        Object[] reversedArray = ArrayOperations.reverse(array);
        assertEquals(5, reversedArray.length);
        assertEquals(max, reversedArray[0]);
        assertEquals(fifty, reversedArray[1]);
        assertEquals(fifteen, reversedArray[2]);
        assertEquals(ten, reversedArray[3]);
        assertEquals(one, reversedArray[4]);
        ArrayOperations.reverseInPlace(array);
        assertEquals(5, array.length);
        assertEquals(max, array[0]);
        assertEquals(fifty, array[1]);
        assertEquals(fifteen, array[2]);
        assertEquals(ten, array[3]);
        assertEquals(one, array[4]);
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
     * Test of sortedOrder method, of class ArrayOperations.
     */
    @Test
    public void testSortedOrder_intArr() {
        int[] array = new int[] {3, 1, 2};
        int[] sorted = new int[] {1, 2, 3};
        int[] so = ArrayOperations.sortedOrder(array);
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], array[so[i]]);
        }
        array = new int[] {3, 2, 0, 2, 1, 3};
        sorted = new int[] {0, 1, 2, 2, 3, 3};
        so = ArrayOperations.sortedOrder(array);
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], array[so[i]]);
        }
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
        String[] array = {"c", "a", "b"};
        String[] sorted = new String[] {"a", "b", "c"};
        int[] so = ArrayOperations.sortedOrder(array);
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], array[so[i]]);
        }
        array = new String[] {"c", "b", "a", "b", "aa", "c"};
        sorted = new String[] {"a", "aa", "b", "b", "c", "c"};
        so = ArrayOperations.sortedOrder(array);
        for (int i = 0; i < array.length; i++) {
            assertEquals(sorted[i], array[so[i]]);
        }
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

    @Test
    public void testGenerateEnumeration() {
        int[] reverseEnum = ArrayOperations.generateEnumeration(10, 5);
        assertEquals(6, reverseEnum.length);
        for (int i = 0; i < reverseEnum.length; i++) {
            assertEquals(10 - i, reverseEnum[i]);
        }
    }

}