import java.util.ArrayList;
import java.util.Scanner;

// Book class
class Book {
    String title;
    String author;
    int year;

    Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}

// Library class
class Library {
    ArrayList<Book> books = new ArrayList<>();

    void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully!");
    }

    void displayBooks() {
        System.out.println("\nBook List:");

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.printf("%-20s %-20s %-5s%n", "Title", "Author", "Year");

        for (Book book : books) {
            System.out.printf("%-20s %-20s %-5d%n",
                    book.title, book.author, book.year);
        }
    }

    void searchBook(String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                System.out.println("\nBook found!");
                System.out.println("Title: " + book.title);
                System.out.println("Author: " + book.author);
                System.out.println("Year: " + book.year);
                return;
            }
        }

        System.out.println("Book not found!");
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        System.out.println("========================================================");
        System.out.println("             WELCOME TO THE LIBRARY SYSTEM");
        System.out.println("========================================================");
        System.out.println("Welcome! This Library Management System helps you");
        System.out.println("organize your books with ease. You can add new books,");
        System.out.println("view all the books you've entered, search for a");
        System.out.println("specific book by its title, or exit the program");
        System.out.println("whenever you're done.");
        System.out.println();
        System.out.println("Please choose an option from the menu below to get started.");
        System.out.println("========================================================");
        
        System.out.print("\nWould you like to start the Library Management System? (Y/N): ");
        String start = sc.nextLine();

        if (!start.equalsIgnoreCase("Y") && !start.equalsIgnoreCase("Yes")) {
            System.out.println("\nThank you! The program has been closed.");
            sc.close();
            return;
        }

        int choice;

        do {
            System.out.println("\nMenu");
            System.out.println("1 - Add Book");
            System.out.println("2 - Display Book");
            System.out.println("3 - Search Book");
            System.out.println("4 - Exit");
            System.out.print("Choose an option: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter year: ");
                    int year = sc.nextInt();
                    sc.nextLine();

                    library.addBook(new Book(title, author, year));
                    break;

                case 2:
                    library.displayBooks();
                    break;

                case 3:
                    System.out.print("Enter a book to search: ");
                    String search = sc.nextLine();
                    library.searchBook(search);
                    break;

                case 4:
                    System.out.println("Thank you for using the Library Management System!");
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}
