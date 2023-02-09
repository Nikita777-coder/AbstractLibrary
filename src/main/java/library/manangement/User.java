package library.manangement;

import library.Book;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final List<Book> userBooks;

    public User() {
        userBooks = new ArrayList<>();
    }

    public void addBook(Book book) {
        userBooks.add(book);
    }

    public void removeBook(Book book) {
        userBooks.remove(book);
    }

    public List<Book> getBooks() {
        return userBooks;
    }
}
