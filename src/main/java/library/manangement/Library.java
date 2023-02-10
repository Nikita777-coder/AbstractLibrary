package library.manangement;

import library.Book;
import library.stream.ConsoleHandler;

import java.util.*;

public class Library {
    private final Map<String, List<Book>> booksGroupedByAuthor;
    private final LibraryAdmin admin;
    private Map<String, List<Book>> getBooks(String bookName) {
        Map<String, List<Book>> foundBooks = new HashMap<>();

        for (List<Book> books : booksGroupedByAuthor.values()) {
            for (Book book : books) {
                if (book.getTitle().equals(bookName)) {
                    if (!foundBooks.containsKey(book.getAuthor())) {
                        foundBooks.put(book.getAuthor(), new ArrayList<>());
                    }

                    foundBooks.get(book.getAuthor()).add(book);
                }
            }
        }

        return foundBooks;
    }

    private void takeBook(Book book) {
        booksGroupedByAuthor.get(book.getAuthor()).remove(book);
        admin.addBookToUser(book);

        if (booksGroupedByAuthor.get(book.getAuthor()).isEmpty()) {
            booksGroupedByAuthor.remove(book.getAuthor());
        }
    }

    private List<String> getBaseTitles() {
        List<String> titles = new ArrayList<>();

        titles.add("Cute bones");
        titles.add("The elegance of hedgehog");
        titles.add("The blue lard");
        titles.add("The glass books dream eaters");
        titles.add("Hard explanation of simple things in Java");
        titles.add("Fun telling of evil true stories");
        titles.add("Ahah stories");
        titles.add("Desperate people and how make your life according to one scheme");
        titles.add("Not Nike or how make your life with title <<just do it>>");
        titles.add("How make your program affective by building million graphics in one Algosы KDZ?");
        titles.add("Сame in and didn't come out: how to travel to metro depot by inattention");

        return titles;
    }

    private List<String> getBaseAuthors() {
        List<String> authors = new ArrayList<>();

        authors.add("Denis Kulikov");
        authors.add("My coresh");
        authors.add("Brothers for ages");
        authors.add("Anton Sidorov");
        authors.add("Mikle MakGi");
        authors.add("Andrash Arato");
        authors.add("Nik Yang");
        authors.add("Kabosu");
        authors.add("Sasha Phokin");
        authors.add("Wasted man");
        authors.add("Press F");

        return authors;
    }

    private List<Integer> getBaseYears() {
        List<Integer> years = new ArrayList<>();

        years.add(2023);
        years.add(2022);
        years.add(2021);
        years.add(2020);
        years.add(2019);
        years.add(2018);
        years.add(2017);
        years.add(2016);
        years.add(2015);
        years.add(2014);
        years.add(2013);

        return years;
    }

    private List<String> getBaseDescriptions() {
        List<String> descriptions = new ArrayList<>();

        descriptions.add("\"On the sixth of December, nineteen hundred and seventy-three, when I was killed, I was " +
                "fourteen years old\" - this is how this tragic story begins. The deceased, the main character " +
                "Susie Salmon, adapts to life in heaven and watches from above as her killer tries to cover his tracks, " +
                "and the family tries to get used to the loss... But this strong, dramatic book is not about murder, " +
                "not about violence, but about life. Life after death. The lives of those who remained. That's probably" +
                " why it's written in such a surprisingly light language.");
        descriptions.add("“The Elegance of the hedgehog\", the second novel by the French writer Muriel Barberi " +
                "(b. 1969), glorified her name not only in France, but also in many other countries. She is passionately" +
                " in love with the work of Leo Tolstoy and the culture of Japan, and she expressed both of these " +
                "passions in her book. A teenage girl, smart and educated beyond her years, an elderly concierge " +
                "studying philosophical works and listening to Mozart, a rich Japanese man who settled in his declining" +
                " years in a luxurious Paris apartment… What connects these people, how their lives change after they " +
                "accidentally find each other — the reader will find out about this by opening this beautiful, subtle, " +
                "fascinating novel.");
        descriptions.add("The most scandalous of Vladimir Sorokin's novels. Fascinating plot, witty language finds, " +
                "stylizations of Russian classics, sometimes murderous for the original.");
        descriptions.add("New York playwright Gordon Dahlquist woke up famous, as they say, overnight — when the Penguin" +
                " publishing house offered him a contract worth two million dollars for the debut novel \"Glass Books " +
                "of Dream Eaters\" and its future sequel. So, the characters: Miss Temple did not come to town to look" +
                " for adventures at all — she is looking for a husband. But when her fiance Roger Bascombe, a rising " +
                "star in the secretariat of the Ministry of Foreign Affairs, suddenly breaks off their engagement, " +
                "Miss Temple decides to find out why. She secretly follows Roger to an ominous masquerade ball, and " +
                "she is dragged headlong into a bloody-in the most literal sense of the word—whirlpool...");

        return descriptions;
    }

    public Library(LibraryAdmin admin) {
        booksGroupedByAuthor = new HashMap<>();
        this.admin = admin;
    }

    public Library(Map<String, List<Book>> booksGroupedByAuthor) {
        this.booksGroupedByAuthor = booksGroupedByAuthor;
        admin = new LibraryAdmin(new ConsoleHandler());
    }
    public void fillLibraryWithRandomGeneratedBooks() {
        booksGroupedByAuthor.clear();

        Random random = new Random();
        List<String> baseBookTitles = getBaseTitles();
        List<String> baseBookAuthors = getBaseAuthors();
        List<Integer> baseBookYears = getBaseYears();
        List<String> baseBookDescriptions = getBaseDescriptions();
        int n = baseBookTitles.size();

        for (int i = 0; i < n; ++i) {
            int ind = random.nextInt(0, baseBookTitles.size());
            String title = baseBookTitles.get(ind);
            baseBookTitles.remove(ind);

            ind = random.nextInt(0, baseBookAuthors.size());
            String author = baseBookAuthors.get(ind);
            baseBookAuthors.remove(ind);

            ind = random.nextInt(0, baseBookYears.size());
            int year = baseBookYears.get(ind);
            baseBookYears.remove(ind);

            String desc = "";
            if (!baseBookDescriptions.isEmpty()) {
                ind = random.nextInt(0, baseBookDescriptions.size());
                desc = baseBookDescriptions.get(ind);
                baseBookDescriptions.remove(ind);
            }

            Book book = Book.builder().title(title).authorName(author).description(desc).year(year).build();
            put(book);
        }
    }

    public Book getBook(String bookName) {
        Book userBook = new Book();
        Map<String, List<Book>> foundBooks = getBooks(bookName);

        if (foundBooks.size() > 0) {
            List<String> keys = foundBooks.keySet().stream().toList();
            if (keys.size() > 1) {
                // If realize interactive menu then param can be removed.
                userBook = admin.askToChooseUserBook(foundBooks, bookName);
            } else if (keys.size() == 1) {
                if (foundBooks.get(keys.get(0)).size() > 1) {
                    userBook = admin.askToChooseUserBook(foundBooks, bookName);
                } else if (foundBooks.get(keys.get(0)).size() == 1) {
                    userBook = foundBooks.get(keys.get(0)).get(0);
                }
            }

            takeBook(userBook);
        }

        return userBook;
    }

    public void put(Book book) {
        if (!booksGroupedByAuthor.containsKey(book.getAuthor())) {
            booksGroupedByAuthor.put(book.getAuthor(), new ArrayList<>());
        }

        booksGroupedByAuthor.get(book.getAuthor()).add(book);
        admin.removeBookFromUserBooks(book);
    }

    public void put(List<Book> books) {
        for (Book book : books) {
            put(book);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();

        for (List<Book> value : booksGroupedByAuthor.values()) {
            allBooks.addAll(value);
        }

        return allBooks;
    }
}
