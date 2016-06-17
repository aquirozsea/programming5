package programming5.stream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Collection of stream operations and convenience constructors
 */
public abstract class StreamUtils {

    /**
     * Creates a stream of lists of elements from an existing stream, where each list represents a sliding window of
     * size lookaheadAmount + 1. For example, if lookaheadAmount is 1 and the original stream is {1, 2, 3, 4, 5}, the
     * resulting stream will be {[1, 2], [2, 3], [3, 4], [4, 5], [5]}. Note that the last element of the stream is
     * short and serves as an early indicator of the end of the stream.
     * @param stream the original stream
     * @param lookaheadAmount the number of additional elements to return in each list
     * @return the new lookahead stream
     */
    public static <T> Stream<List<T>> lookaheadStream(Stream<T> stream, int lookaheadAmount) {
        final Spliterator<T> originalSpliterator = stream.spliterator();
        final List<T> accummulator = new LinkedList<>();
        // Pre-fill accummulator
        for (int i = 0; i <= lookaheadAmount; i++) {
            if (!originalSpliterator.tryAdvance(accummulator::add)) {
                break;
            }
        }
        // If original stream is not large enough, return the accummulated list as a single array
        if (accummulator.size() <= lookaheadAmount) return Stream.of(new ArrayList<>(accummulator));
        // Create a list stream with a new spliterator
        return StreamSupport.stream(new Spliterator<List<T>>() {

            boolean done = false;

            @Override
            public boolean tryAdvance(Consumer<? super List<T>> action) {
                if (accummulator.isEmpty()) return false;
                action.accept(new ArrayList<>(accummulator));
                if (!done) {
                    accummulator.remove(0);
                    done = !originalSpliterator.tryAdvance(accummulator::add);
                }
                else {
                    accummulator.clear();
                }
                return true;
            }

            @Override
            public Spliterator<List<T>> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return originalSpliterator.estimateSize();
            }

            @Override
            public int characteristics() {
                return originalSpliterator.characteristics();
            }
        }, false);  // This spliterator does not create a parallel stream
    }

}
