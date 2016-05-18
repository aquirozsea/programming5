package programming5.stream;

import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ConditionalPassCounterTest {

    @Test
    public void test() {
        PassCounter<Integer> intCounter = new ConditionalPassCounter<>(n -> (n % 2) == 0);
        IntStream.rangeClosed(1, 10).map(intCounter::apply).forEach(n -> {});
        assertEquals(5, intCounter.getCounter());

        PassCounter<String> stringCounter = new ConditionalPassCounter<>(s -> s.startsWith("t"));
        Stream.of("one", "two", "three").map(stringCounter).forEach(s -> {});
        assertEquals(2, stringCounter.getCounter());
    }

}