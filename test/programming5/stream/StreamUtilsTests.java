package programming5.stream;

import org.junit.Test;
import programming5.collections.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by andres on 6/17/16.
 */
public class StreamUtilsTests {

    @Test
    public void testLookaheadStream() {
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(2, 3),
                Arrays.asList(3, 4),
                Arrays.asList(4, 5),
                Arrays.asList(5)
        );
        List<List<Integer>> ls = StreamUtils.lookaheadStream(Stream.of(1, 2, 3, 4, 5) , 1).collect(Collectors.toList());
        CollectionUtils.synchronize(expected, ls).stream().forEach(pair -> assertEquals(pair.get(0), pair.get(1)));

        expected = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(2, 3, 4),
                Arrays.asList(3, 4, 5),
                Arrays.asList(4, 5)
        );
        ls = StreamUtils.lookaheadStream(Stream.of(1, 2, 3, 4, 5) , 2).collect(Collectors.toList());
        CollectionUtils.synchronize(expected, ls).stream().forEach(pair -> assertEquals(pair.get(0), pair.get(1)));

        expected = Arrays.asList(
                Arrays.asList(1),
                Arrays.asList(2),
                Arrays.asList(3),
                Arrays.asList(4),
                Arrays.asList(5)
        );
        ls = StreamUtils.lookaheadStream(Stream.of(1, 2, 3, 4, 5) , 0).collect(Collectors.toList());
        CollectionUtils.synchronize(expected, ls).stream().forEach(pair -> assertEquals(pair.get(0), pair.get(1)));
    }

}