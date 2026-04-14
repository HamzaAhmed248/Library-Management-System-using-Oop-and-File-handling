package code;
import java.util.*;
public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\t=== WELCOME TO FUTURE OF LIBRARY MANAGEMENT SYSTEM ===");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Book");
            System.out.println("2. Lend Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            String input = sc.nextLine();

            try {
                switch (input) {
                    case "1":
                        manager.addBook();
                        break;

                    case "2":
                        manager.lendBook();
                        break;

                    case "3":
                        manager.returnBook();
                        break;

                    case "4":
                        manager.viewBooks();
                        break;

                    case "5":
                        System.out.print("Exiting system. Goodbye!\n");
                        return;

                    default:
                        System.out.print("Invalid choice! Try again.\n");
                }
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }
}


