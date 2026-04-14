package code;
import java.io.*;
import java.util.*;

class LibraryManager implements Lendable, Returnable, Displayable {
    private final String FILE_NAME = "LibraryData.txt";
    private List<Book> books;

    public LibraryManager() {
        books = new ArrayList<>();
        loadBooks();
        saveBooks();
    }

    private void loadBooks() {
        try {
            FileHandler fh = new FileHandler(FILE_NAME);
            String[] lines = fh.readLines();
            if (lines != null && lines.length > 0) {
                for (String line : lines) {
                    books.add(Book.fromFileString(line));
                }
            } else {
                addDefaultBooks();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing book file found. A new one will be created.");
            addDefaultBooks();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveBooks() {
        try {
            FileHandler fh = new FileHandler(FILE_NAME);
            StringBuilder sb = new StringBuilder();
            for (Book b : books) {
                sb.append(b.toFileString()).append("\n");
            }
            fh.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void addBook() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter category: ");
            String category = sc.nextLine();
            System.out.print("Enter title: ");
            String title = sc.nextLine();
            System.out.print("Enter author: ");
            String author = sc.nextLine();
            System.out.println("Select location:\n1. Rack 1\n2. Rack 2\n3. Rack 3");
            int loc = Integer.parseInt(sc.nextLine());

            String location;
            switch (loc) {
                case 1:
                    location = "Rack 1";
                    break;
                case 2:
                    location = "Rack 2";
                    break;
                case 3:
                    location = "Rack 3";
                    break;
                default:
                    location = "Unknown Rack";
            }

            Book newBook = new Book(category, title, author, location, true, 0);
            books.add(newBook);
            saveBooks();
            System.out.println("Book added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    @Override
    public void lendBook() throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Integer> availableIndexes = new ArrayList<>();

        System.out.println("\nAvailable Books:");
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).status) {
                availableIndexes.add(i);
                Book b = books.get(i);
                System.out.printf("%d. %s | %s | %s | %s\n", availableIndexes.size(), b.category, b.title, b.author, b.location);
            }
        }

        if (availableIndexes.isEmpty()) {
            System.out.println("No available books to lend.");
            return;
        }

        System.out.print("Enter the serial number to lend: ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice < 1 || choice > availableIndexes.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Enter last 4 digits of your ARID: ");
        int arid = Integer.parseInt(sc.nextLine());
        if (String.valueOf(arid).length() != 4) {
            System.out.println("ARID must be exactly 4 digits.");
            return;
        }

        for (Book b : books) {
            if (b.issuedTo == arid) {
                System.out.println("You already have a book issued.");
                return;
            }
        }

        Book selected = books.get(availableIndexes.get(choice - 1));
        selected.status = false;
        selected.issuedTo = arid;
        saveBooks();

        System.out.println("Book issued successfully!\nTitle: " + selected.title);
    }

    @Override
    public void returnBook() throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Integer> issuedIndexes = new ArrayList<>();

        System.out.println("\nIssued Books:");
        for (int i = 0; i < books.size(); i++) {
            if (!books.get(i).status) {
                issuedIndexes.add(i);
                Book b = books.get(i);
                System.out.printf("%d. %s | %s | %s | %s\n", issuedIndexes.size(), b.category, b.title, b.author, b.location);
            }
        }

        if (issuedIndexes.isEmpty()) {
            System.out.println("No books to return.");
            return;
        }

        System.out.print("Enter serial number to return: ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice < 1 || choice > issuedIndexes.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Book returning = books.get(issuedIndexes.get(choice - 1));
        int fine = 0;

        System.out.print("Is the book damaged? (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) fine += 50;

        System.out.print("Is the book returned late? (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Enter number of late days: ");
            int days = Integer.parseInt(sc.nextLine());
            fine += days * 5;
        }

        returning.status = true;
        returning.issuedTo = 0;
        saveBooks();

        System.out.println("\n=== Return Receipt ===");
        System.out.println("Fine: Rs. " + fine);
        System.out.println("Book returned successfully.");
    }

    @Override
    public void viewBooks() throws IOException {
        System.out.println("\nAll Books:");
        int count = 1;
        for (Book b : books) {
            System.out.printf("\nBook %d:\n", count++);
            System.out.println("Category: " + b.category);
            System.out.println("Title: " + b.title);
            System.out.println("Author: " + b.author);
            System.out.println("Location: " + b.location);
            if (!b.status) {
                System.out.println("Status: Already Lent to ARID: " + b.issuedTo);
            } else {
                System.out.println("Status: Available");
            }
        }
    }

    public void addDefaultBooks() {
        books.add(new Book("English", "To Kill a Mockingbird", "Harper Lee", "Rack 3", false, 9012));
        books.add(new Book("English", "Pride and Prejudice", "Jane Austen", "Rack 2", false, 5678));
        books.add(new Book("Programming", "C++ Programming", "Bjarne Stroustrup", "Rack 1", true, 0));
        books.add(new Book("Development", "Clean Code", "Robert C. Martin", "Rack 2", true, 0));
        books.add(new Book("Architecture", "Design Patterns", "Erich Gamma", "Rack 3", true, 0));
    }
}


