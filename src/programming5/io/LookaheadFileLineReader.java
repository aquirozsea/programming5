package programming5.io;

import programming5.stream.StreamUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Special implementation of a lookahead stream for reading a file line by line. Useful when a file entry can span
 * multiple lines.
 * @see StreamUtils#lookaheadStream(Stream, int)
 */
public class LookaheadFileLineReader {

    /**
     * @param filePath the file path
     * @return a lookahead stream with lookaheadAmount = 1
     */
    public static Stream<List<String>> stream(String filePath) throws IOException {
        return StreamUtils.lookaheadStream(Files.lines(Paths.get(filePath)), 1);
    }

    /**
     * @param filePath the file path
     * @param lookaheadAmount the number of additional lines to return for each stream element
     * @return a lookahead stream with the given lookaheadAmount
     */
    public static Stream<List<String>> stream(String filePath, int lookaheadAmount) throws IOException {
        return StreamUtils.lookaheadStream(Files.lines(Paths.get(filePath)), lookaheadAmount);
    }

}
