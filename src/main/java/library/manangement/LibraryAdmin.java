package library.manangement;

import library.Book;
import library.stream.IOHandler;
import java.util.List;
import java.util.Map;

public class LibraryAdmin {
    private final User user;
    private final Library lib;
    private final IOHandler handler;
    private void writeListOfFoundBooks(Map<String, List<Book>> foundedBooks) {
        handler.println("It was found several books with name you requested:");
        handler.println("\t\t\t\t\t↓");
        handler.println("----------------------------------------------------");

        for (List<Book> books : foundedBooks.values()) {
            for (Book book : books) {
                handler.println(book);
            }
        }

        handler.println("----------------------------------------------------");
        handler.println("\t\t\t\t\t↑");
    }

    private String getAuthor(Map<String, List<Book>> foundBooks) {
        int counter = 0;
        String author = "";

        while (!foundBooks.containsKey(author)) {
            if (counter > 0) {
                handler.println("There is no author of selected book");
            }

            handler.print("Enter the author: ");
            author = handler.next();
            ++counter;
        }

        return author;
    }

    private int getYear(Map<String, List<Book>> foundBooks, String author) {
        List<Integer> years = foundBooks.get(author).stream().map(Book::getYear).toList();
        int year = -2;
        boolean flag = false;

        while (!years.contains(year)) {
            if (flag) {
                handler.println("There is no book with entered year");
            }

            System.out.print("Enter the year of book: ");

            try {
                year = Book.checkYear(handler.nextInt());
                flag = true;
            } catch (IllegalArgumentException arg) {
                System.out.println(arg.getMessage());
                flag = false;
            }
        }

        return year;
    }

    public LibraryAdmin(IOHandler handler) {
        this.handler = handler;
        user = new User();
        lib = new Library(this);
    }

    // It's guaranteed that foundBooks.size() > 1.
    public Book askToChooseUserBook(Map<String, List<Book>> foundBooks, String bookName) {
        var builderBook = Book.builder().title(bookName);
        writeListOfFoundBooks(foundBooks);
        String author = getAuthor(foundBooks);
        builderBook.authorName(author);

        // Books with different year can contain not equal content.
        if (foundBooks.get(author).size() > 1) {
            System.out.println();
            builderBook.year(getYear(foundBooks, author));
        }

        return builderBook.build();
    }

    public void addBookToUser(Book book) {
        user.addBook(book);
    }

    public void removeBookFromUserBooks(Book book) {
        user.removeBook(book);
    }

    public void put(String book) {
        lib.put(Book.parse(book));
    }

    public void put(Book book) {
        lib.put(book);
    }

    public void put(List<Book> books) {
        lib.put(books);
    }

    public Book get(String title) {
        return lib.getBook(title);
    }

    public List<Book> list() {
        return user.getBooks();
    }

    public List<Book> all() {
        return lib.getAllBooks();
    }

    public void fillLib() {
        lib.fillLibraryWithRandomGeneratedBooks();
    }
}

