package code;


public class Book {
    protected String category;
    protected String title;
    protected String author;
    protected String location;
    protected boolean status;
    protected int issuedTo;

    public Book(String category, String title, String author, String location, boolean status, int issuedTo) {
        this.category = category;
        this.title = title;
        this.author = author;
        this.location = location;
        this.status = status;
        this.issuedTo = issuedTo;
    }

    public String toFileString() {
        return category + "|" + title + "|" + author + "|" + location + "|" + status + "|" + issuedTo;
    }

    public static Book fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Book(
            parts[0], parts[1], parts[2], parts[3],
            Boolean.parseBoolean(parts[4]),
            Integer.parseInt(parts[5])
        );
    }
}

