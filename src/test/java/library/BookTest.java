package library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void parse() {
        // Test: parse simple correct book with all fields in right order and only with required fields.
        String test1 = "title=title, author=author";
        Book ans1 = Book.builder().title("title").authorName("author").build();

        // Test1: parse simple correct book with all fields in right order with additional fields.
        // Test2: parse simple correct book with fields in not right order with additional fields.
        String test2 = "title=title, year=0, author=author, description=desc";
        String test21 = "year=23, description=desc, author=author, title=title";
        Book ans2 = Book.builder().title("title").authorName("author").year(0).description("desc").build();
        Book ans21 = Book.builder().title("title").authorName("author").year(23).description("desc").build();

        // Test1: parse book with incorrect type of year.
        // Test2: parse book with incorrect value of year.
        String test3 = "title=title, year=year, author=author, description=desc";
        String test31 = "title=title, year=-20, author=author, description=desc";
        NumberFormatException ans3 = new NumberFormatException();
        IllegalArgumentException ans31 = new IllegalArgumentException("year must be positive!");

        // Test: parse book with incorrect field.
        String test4 = "title=title, year=23, author=author, description=desc, f= f";
        IllegalArgumentException ans4 = new IllegalArgumentException("param f was not found in book data");

        // Test: parse book with empty title.
        String test5 = "title= , author=auth";
        NullPointerException ans5 = new NullPointerException();

        // Test: parse book with empty author.
        String test6 = "title=KJLewd dwpwdw, author=";
        NullPointerException ans6 = new NullPointerException();

        // Test: parse book with empty author.

        assertAll(() -> assertEquals(ans1, Book.parse(test1)),
                  () -> assertEquals(ans2, Book.parse(test2)),
                  () -> assertEquals(ans21, Book.parse(test21)));

        try {
            Book.parse(test3);
        } catch (RuntimeException var16) {
            assertEquals(ans3.getClass().getSimpleName(), var16.getClass().getSimpleName());
        }

        try {
            Book.parse(test31);
        } catch (RuntimeException var15) {
           assertEquals(ans31.getClass().getSimpleName(), var15.getClass().getSimpleName());
        }

        try {
            Book.parse(test4);
        } catch (RuntimeException var14) {
            assertEquals(ans4.getClass().getSimpleName(), var14.getClass().getSimpleName());
        }

        try {
            Book.parse(test5);
        } catch (RuntimeException ex) {
            assertEquals(ans5.getClass().getSimpleName(), ex.getClass().getSimpleName());
        }

        try {
            Book.parse(test6);
        } catch (RuntimeException ex) {
            assertEquals(ans6.getClass().getSimpleName(), ex.getClass().getSimpleName());
        }
    }

    @Test
    void checkYear() {
        // Test: correct number
        int i = 10;
        assertEquals(i, Book.checkYear(i));

        // Test: parse string with positive integer value.
        String num = "235";
        assertEquals(235, Book.checkYear(num));

        // Test: reaction on unvalid class.
        Object obj = new Object();
        ClassCastException ans = new ClassCastException();
        try {
            Book.checkYear(obj);
        } catch (RuntimeException ex) {
            assertEquals(ans.getClass().getSimpleName(), ex.getClass().getSimpleName());
        }

        // Test: parse string which is not a number.
        String s = "pqoekdo[c";
        NumberFormatException ans2 = new NumberFormatException();
        try {
            Book.checkYear(s);
        } catch (RuntimeException ex) {
            assertEquals(ans2.getClass().getSimpleName(), ex.getClass().getSimpleName());
        }

        // Test: parse negative number.
        i = -100;
        IllegalArgumentException ans3 = new IllegalArgumentException();
        try {
            Book.checkYear(i);
        } catch (RuntimeException ex) {
            assertEquals(ans3.getClass().getSimpleName(), ex.getClass().getSimpleName());
        }
    }

    @Test
    void build() {
        // Test: build correct book.
        Book b = Book.builder().title("lwdk").authorName("lakla").build();
        String ans = "Book: {title = lwdk, author = lakla}";
        assertEquals(ans, b.toString());

        // Test: build book with empty title.
        NullPointerException ans2 = new NullPointerException();
        try {
            Book.builder().title("").authorName("sldksd").build();
        } catch (RuntimeException ex) {
            assertEquals(ans2.getClass().getSimpleName(), ex.getClass().getSimpleName());
        }

        // Test: build book with empty author.
        NullPointerException ans3 = new NullPointerException();
        try {
            Book.builder().title("kwmdwd").authorName("").build();
        } catch (RuntimeException ex) {
            assertEquals(ans3.getClass().getSimpleName(), ex.getClass().getSimpleName());
        }
    }

    @Test
    void testEquals() {
        // Test: books with equal authors, titles.
        Book first = Book.builder().authorName("a").title("t").build();
        Book second = Book.builder().authorName("a").title("t").build();
        boolean ans = true;
        assertEquals(ans, first.equals(second));

        // Test: books with equal authors, titles, years.
        first = Book.builder().authorName("a").title("t").year(2019).build();
        second = Book.builder().authorName("a").title("t").year(2019).build();
        assertEquals(ans, first.equals(second));

        // Test: books with equal authors and different titles.
        first = Book.builder().authorName("a").title("t").year(2019).build();
        second = Book.builder().authorName("a").title("ti").year(2019).build();
        ans = false;
        assertEquals(ans, first.equals(second));

        // Test: books with equal titles and different authors.
        first = Book.builder().authorName("a").title("t").year(2019).build();
        second = Book.builder().authorName("ai").title("t").year(2019).build();
        assertEquals(ans, first.equals(second));

        // Test: books with equal authors, titles and different years.
        first = Book.builder().authorName("a").title("t").year(2018).build();
        second = Book.builder().authorName("a").title("t").year(2019).build();
        assertEquals(ans, first.equals(second));
    }

    @Test
    void checkOnEmpty() {
        // Test: not empty string.
        String test1 = "lakdjfk";
        String ans1 = "lakdjfk";
        assertEquals(ans1, Book.checkOnEmpty(test1));

        // Test: null and empty string.

        String s = null, s1 = "";
        NullPointerException ans2 = new NullPointerException();
        try {
            Book.checkOnEmpty(s);
        } catch (RuntimeException re) {
            assertEquals(ans2.getClass().getSimpleName(), re.getClass().getSimpleName());
        }
        try {
            Book.checkOnEmpty(s1);
        } catch (RuntimeException re) {
            assertEquals(ans2.getClass().getSimpleName(), re.getClass().getSimpleName());
        }

        // Test: shift string.
        s = "             ";
        try {
            Book.checkOnEmpty(s);
        } catch (RuntimeException re) {
            assertEquals(ans2.getClass().getSimpleName(), re.getClass().getSimpleName());
        }

        // Test: string without letters and digits.
        s = "'{}`/? ";
        try {
            Book.checkOnEmpty(s);
        } catch (RuntimeException re) {
            assertEquals(ans2.getClass().getSimpleName(), re.getClass().getSimpleName());
        }
    }
}