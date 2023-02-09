package library.stream;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleHandler implements IOHandler {
    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleHandler() {
        scanner = new Scanner(System.in);
        out = new PrintStream(System.out);
    }
    @Override
    public String next() {
        return scanner.nextLine();
    }

    @Override
    public int nextInt() {
        return scanner.nextInt();
    }

    @Override
    public void print(Object content) {
        out.print(content);
    }

    @Override
    public void println() {
        out.println();
    }

    @Override
    public void println(Object content) {
        out.println(content);
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
