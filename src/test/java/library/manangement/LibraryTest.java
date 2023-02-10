package library.manangement;

import library.Book;
import library.stream.ConsoleHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class LibraryTest {

    @Test
    void put() {
        // И тут я понял, зачем нужно было делать прогу максимально несвязанной, но мне лень переделывать).

        Library libOk;
        Library libTest = new Library(new LibraryAdmin(new ConsoleHandler()));
        Map<String, List<Book>> booksToOkLibrary = new HashMap<>();
        List<Book> books = new ArrayList<>();

        // Test: adding two correct books with different fields.
        Book book = Book.builder().title("title").authorName("author").build();
        Book book1 = Book.builder().title("title1").authorName("author1").build();
        books.add(book);
        books.add(book1);
        booksToOkLibrary.put(book.getAuthor(), new ArrayList<>());
        booksToOkLibrary.get(book.getAuthor()).add(book);
        booksToOkLibrary.put(book1.getAuthor(), new ArrayList<>());
        booksToOkLibrary.get(book1.getAuthor()).add(book1);
        libOk = new Library(booksToOkLibrary);
        libTest.put(books);
        assertEquals(libOk.getAllBooks(), libTest.getAllBooks());

        // Test: add book to existing author.
        Book bookWithExistingAuthor = Book.builder().title("title1").authorName("author").build();
        booksToOkLibrary.get("author").add(bookWithExistingAuthor);
        books.add(bookWithExistingAuthor);
        libOk = new Library(booksToOkLibrary);
        libTest.put(bookWithExistingAuthor);
        assertEquals(libOk.getAllBooks(), libTest.getAllBooks());

        // Test: add existing book.
        Book existingBook = Book.builder().title("title").authorName("author").build();
        booksToOkLibrary.get("author").add(existingBook);
        books.add(existingBook);
        libOk = new Library(booksToOkLibrary);
        libTest.put(existingBook);
        assertEquals(libOk.getAllBooks(), libTest.getAllBooks());

        // Test: add existing book with different field
        Book existingBookWithYear = Book.builder().title("title").authorName("author").year(2019).build();
        booksToOkLibrary.get("author").add(existingBookWithYear);
        books.add(existingBookWithYear);
        libOk = new Library(booksToOkLibrary);
        libTest.put(existingBookWithYear);
        assertEquals(libOk.getAllBooks(), libTest.getAllBooks());
    }
}