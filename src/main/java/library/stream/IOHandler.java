package library.stream;

import java.io.Closeable;

public interface IOHandler extends Closeable {
    String next();
    int nextInt();
    void print(Object content);
    void println();
    void println(Object content);
}
