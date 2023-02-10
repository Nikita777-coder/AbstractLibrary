package library.menu;

import library.Book;
import library.manangement.LibraryAdmin;
import library.stream.ConsoleHandler;
import library.stream.IOHandler;

public class ConsoleLibMenu implements LibMenu {
    private final IOHandler handler;
    private final LibraryAdmin admin;
    public ConsoleLibMenu() {
        handler = new ConsoleHandler();
        admin = new LibraryAdmin(handler);
    }
    public void start() {
        String ans = "";

        while (!ans.equals("exit")) {
            handler.print("Enter the command you would like to use: ");
            ans = handler.next();

            switch (ans) {
                case "fillLib" -> fillLib();
                case "get" -> get();
                case "put" -> put();
                case "list" -> list();
                case "all" -> all();
                case "exit" -> {}
                default -> handler.println("Unknown command! ");
            }

            handler.println();
        }
    }
    @Override
    public void get() {
        if (admin.all().isEmpty()) {
            handler.println("There is no book in library");
            return;
        }

        handler.print("Enter the name of book: ");
        String bookName = handler.next();
        Book book = admin.get(bookName);

        if (book.instance() == 0) {
            handler.println("There is no book with this name");
            return;
        }

        handler.println(book);
    }

    @Override
    public void put() {
        handler.print("What book you receive? ");
        String book = handler.next();
        admin.put(book);
    }

    @Override
    public void list() {
        if (admin.list().isEmpty()) {
            handler.println("You don't have books yet");
            return;
        }

        handler.println("Your books are:");
        handler.println("\t\t\t\t\t↓");
        handler.println("----------------------------------------------------");

        for (Book book : admin.list()) {
            handler.println(book);
        }

        handler.println("----------------------------------------------------");
        handler.println("\t\t\t\t\t↑");
    }

    @Override
    public void all() {
        var allBooks = admin.all();

        if (allBooks.isEmpty()) {
            handler.println("There is no books in library");
        } else {
            handler.println("\t\t\t\t\t↓");
            handler.println("----------------------------------------------------");

            for (Book book : allBooks) {
                handler.println(book);
            }

            handler.println("----------------------------------------------------");
            handler.println("\t\t\t\t\t↑");
        }
    }

    public void fillLib() {
        admin.fillLib();
        handler.println("Library was filled");
    }
}

