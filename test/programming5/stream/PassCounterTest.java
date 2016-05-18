package programming5.stream;

import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class PassCounterTest {

    @Test
    public void test() {
        PassCounter<Integer> counter = new PassCounter<>();
        IntStream.rangeClosed(1, 10).map(counter::apply).forEach(n -> {});
        assertEquals(10, counter.getCounter());

        PassCounter<String> stringCounter = new PassCounter<>();
        Stream.of("one", "two", "three").map(stringCounter).forEach(s -> {});
        assertEquals(3, stringCounter.getCounter());
    }

}