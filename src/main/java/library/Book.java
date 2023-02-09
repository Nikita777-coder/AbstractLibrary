package library;

public class Book {
    private String title;
    private String authorName;
    private String description;
    private int year = -1;

    private static Builder defineAndFillInfo(String data, Builder builder) {
        var result = data.split("(\\w)=");

        if (result.length == 2) {
            switch (result[0]) {
                case "titl" -> builder.title(result[1]);
                case "autho" -> builder.authorName(result[1]);
                case "yea" -> builder.year(checkYear(result[1]));
                case "descriptio" -> builder.description(result[1]);
                default -> throw new IllegalArgumentException(String.format("param %s was not found in book data", result[1]));
            }
        } else {
            throw new IllegalArgumentException("Incorrect presentation of book");
        }

        return builder;
    }
    public static Book parse(String book) {
        String[] data = book.split(", ");
        Builder builder = Book.builder();

        for (String d : data) {
            builder = defineAndFillInfo(d, builder);
        }

        return builder.build();
    }

    public static int checkYear(Object year) {
        if (!year.getClass().getSimpleName().equals("String") && !year.getClass().getSimpleName().equals("Integer")) {
            throw new ClassCastException("illegal class argument");
        }

        int yearInt;
        if (year.getClass().getSimpleName().equals("String")) {
            yearInt = Integer.parseInt((String) year);
        } else {
            yearInt = (Integer) year;
        }

        if (yearInt < 0) {
            throw new IllegalArgumentException("year must be positive!");
        }

        return yearInt;
    }

    public class Builder {
        public Builder title(String title) {
            Book.this.title = title;
            return this;
        }

        public Builder authorName(String authorName) {
            Book.this.authorName = authorName;
            return this;
        }

        public Builder description(String description) {
            Book.this.description = description;
            return this;
        }

        public Builder year(int year) {
            Book.this.year = Book.checkYear(year);
            return this;
        }

        public Book build() {
            return Book.this;
        }
    }

    public Book() {
    }

    public Book(String title, String authorName) {
        this.title = title;
        this.authorName = authorName;
    }

    public static Builder builder() {
        return new Book().new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {return authorName;}

    public int getYear() {return year;}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Book anotherBook = (Book) obj;
        return anotherBook.title.equals(title) && anotherBook.authorName.equals(authorName) && year == anotherBook.year;
    }

    @Override
    public int hashCode() {
        return authorName.hashCode() + title.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Book: {");
        sb.append("title = ");
        sb.append(title);
        sb.append(", author = ");
        sb.append(authorName);

        if (year != -1) {
            sb.append(", year = ");
            sb.append(year);
        }

        if (description != null && !description.equals("")) {
            sb.append(", description = ");
            sb.append(description);
        }

        sb.append("}");

        return sb.toString();
    }
}