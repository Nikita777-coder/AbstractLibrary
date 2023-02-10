package library.manangement;

import library.Book;
import library.stream.ConsoleHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryAdminTest {
    private static final LibraryAdmin libraryAdmin = new LibraryAdmin(new ConsoleHandler());

    @Test
    void addBookToUser() {
        // Test: add not existing book.
        libraryAdmin.addBookToUser(Book.builder().title("title").authorName("auth").build());
        List<Book> ans = new ArrayList<>();
        ans.add(Book.builder().title("title").authorName("auth").build());
        assertEquals(ans, libraryAdmin.list());

        // Test: add existing book in user collection.
        libraryAdmin.addBookToUser(Book.builder().title("title").authorName("auth").build());
        ans.add(Book.builder().title("title").authorName("auth").build());
        assertEquals(ans, libraryAdmin.list());
    }

    @Test
    void removeBookFromUserBooks() {
        libraryAdmin.put(Book.builder().authorName("author").title("title").build());

        // Test: remove existing book.
        List<Book> books = new ArrayList<>();
        libraryAdmin.removeBookFromUserBooks(Book.builder().authorName("author").title("title").build());
        assertEquals(books, libraryAdmin.list());

        // Test: remove not existing book.
        libraryAdmin.removeBookFromUserBooks(Book.builder().authorName("author1").title("title1").build());

        // Test: remove from empty list.
        libraryAdmin.removeBookFromUserBooks(Book.builder().authorName("author1").title("title1").build());
    }

    @Test
    void put() {
        // Test: put book with not existing author in library.
        List<Book> okBooks = new ArrayList<>();
        String book = "author=author, title=My age";
        okBooks.add(Book.builder().title("My age").authorName("author").build());
        libraryAdmin.put(book);
        assertEquals(okBooks, libraryAdmin.all());

        // Test: put book with existing author.
        String book1 = "author=author, title=Another book";
        libraryAdmin.put(book1);
        okBooks.add(Book.builder().title("Another book").authorName("author").build());
        assertEquals(okBooks, libraryAdmin.all());
    }

    @Test
    void fillLib() {
        libraryAdmin.fillLib();
        assertEquals(11, libraryAdmin.all().size());
    }
}