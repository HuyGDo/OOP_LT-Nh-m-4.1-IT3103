package main.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import entity.*;
import service.*;
import service.impl.*;

public class Main {
    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC Driver: " + e.getMessage());
            System.exit(1);
        }
    }

    private static final BookService bookService = new BookServiceImpl();
    private static final CategoryService categoryService = new CategoryServiceImpl();
    private static final ReaderService readerService = new ReaderServiceImpl();
    private static final BorrowService borrowService = new BorrowServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) {
        while (true) {
            try {
                showMainMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        handleBookMenu();
                        break;
                    case 2:
                        handleCategoryMenu();
                        break;
                    case 3:
                        handleReaderMenu();
                        break;
                    case 4:
                        handleBorrowMenu();
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
        System.out.println("1. Book Management");
        System.out.println("2. Category Management");
        System.out.println("3. Reader Management");
        System.out.println("4. Borrow Management");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }
    
    // Book Management Methods
    private static void handleBookMenu() {
        while (true) {
            System.out.println("\n=== Book Management ===");
            System.out.println("1. Add new book");
            System.out.println("2. View all books");
            System.out.println("3. Update book");
            System.out.println("4. Delete book");
            System.out.println("5. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    // Category Management Methods
    private static void handleCategoryMenu() {
        while (true) {
            System.out.println("\n=== Category Management ===");
            System.out.println("1. Add new category");
            System.out.println("2. View all categories");
            System.out.println("3. Update category");
            System.out.println("4. Delete category");
            System.out.println("5. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    viewAllCategories();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    // Reader Management Methods
    private static void handleReaderMenu() {
        while (true) {
            System.out.println("\n=== Reader Management ===");
            System.out.println("1. Add new reader");
            System.out.println("2. View all readers");
            System.out.println("3. Update reader");
            System.out.println("4. Delete reader");
            System.out.println("5. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addReader();
                    break;
                case 2:
                    viewAllReaders();
                    break;
                case 3:
                    updateReader();
                    break;
                case 4:
                    deleteReader();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    // Borrow Management Methods
    private static void handleBorrowMenu() {
        while (true) {
            System.out.println("\n=== Borrow Management ===");
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. View all borrows");
            System.out.println("4. View reader's borrows");
            System.out.println("5. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    viewAllBorrows();
                    break;
                case 4:
                    viewReaderBorrows();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    // Book Operations
    private static void addBook() {
        System.out.println("\n=== Add New Book ===");
        
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        
        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();
        
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        
        System.out.print("Enter category ID: ");
        int categoryId = scanner.nextInt();
        
        Category category = categoryService.findCategory(categoryId);
        if (category == null) {
            System.out.println("Category not found!");
            return;
        }
        
        Book book = new Book(0, title, author, publisher, quantity, category);
        bookService.addBook(book);
        System.out.println("Book added successfully!");
    }
    
    private static void viewAllBooks() {
        System.out.println("\n=== All Books ===");
        List<Book> books = bookService.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        
        for (Book book : books) {
            System.out.println(book);
        }
    }
    
    private static void updateBook() {
        System.out.println("\n=== Update Book ===");
        
        System.out.print("Enter book ID to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        Book existingBook = bookService.findBook(bookId);
        if (existingBook == null) {
            System.out.println("Book not found!");
            return;
        }
        
        System.out.print("Enter new title (current: " + existingBook.getTitle() + "): ");
        String title = scanner.nextLine();
        
        System.out.print("Enter new author (current: " + existingBook.getAuthor() + "): ");
        String author = scanner.nextLine();
        
        System.out.print("Enter new publisher (current: " + existingBook.getPublisher() + "): ");
        String publisher = scanner.nextLine();
        
        System.out.print("Enter new quantity (current: " + existingBook.getQuantity() + "): ");
        int quantity = scanner.nextInt();
        
        Book updatedBook = new Book(bookId, title, author, publisher, quantity, existingBook.getCategory());
        bookService.updateBook(updatedBook);
        System.out.println("Book updated successfully!");
    }
    
    private static void deleteBook() {
        System.out.println("\n=== Delete Book ===");
        
        System.out.print("Enter book ID to delete: ");
        int bookId = scanner.nextInt();
        
        Book existingBook = bookService.findBook(bookId);
        if (existingBook == null) {
            System.out.println("Book not found!");
            return;
        }
        
        System.out.print("Are you sure you want to delete this book? (y/n): ");
        String confirm = scanner.next();
        
        if (confirm.equalsIgnoreCase("y")) {
            bookService.deleteBook(bookId);
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
    
    // Category Operations
    private static void addCategory() {
        System.out.println("\n=== Add New Category ===");
        
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        
        Category category = new Category(0, name);
        categoryService.addCategory(category);
        System.out.println("Category added successfully!");
    }
    
    private static void viewAllCategories() {
        System.out.println("\n=== All Categories ===");
        List<Category> categories = categoryService.getAllCategories();
        
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        
        for (Category category : categories) {
            System.out.println(category);
        }
    }
    
    private static void updateCategory() {
        System.out.println("\n=== Update Category ===");
        
        System.out.print("Enter category ID to update: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        
        Category existingCategory = categoryService.findCategory(categoryId);
        if (existingCategory == null) {
            System.out.println("Category not found!");
            return;
        }
        
        System.out.print("Enter new name (current: " + existingCategory.getCategoryName() + "): ");
        String name = scanner.nextLine();
        
        Category updatedCategory = new Category(categoryId, name);
        categoryService.updateCategory(updatedCategory);
        System.out.println("Category updated successfully!");
    }
    
    private static void deleteCategory() {
        System.out.println("\n=== Delete Category ===");
        
        System.out.print("Enter category ID to delete: ");
        int categoryId = scanner.nextInt();
        
        Category existingCategory = categoryService.findCategory(categoryId);
        if (existingCategory == null) {
            System.out.println("Category not found!");
            return;
        }
        
        System.out.print("Are you sure you want to delete this category? (y/n): ");
        String confirm = scanner.next();
        
        if (confirm.equalsIgnoreCase("y")) {
            categoryService.deleteCategory(categoryId);
            System.out.println("Category deleted successfully!");
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
    
    // Reader Operations
    private static void addReader() {
        System.out.println("\n=== Add New Reader ===");
        
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        
        System.out.print("Enter birth date (dd/MM/yyyy): ");
        Date birthDate = parseDate(scanner.nextLine());
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        Reader reader = new Reader(0, fullName, birthDate, address);
        readerService.addReader(reader);
        System.out.println("Reader added successfully!");
    }
    
    private static void viewAllReaders() {
        System.out.println("\n=== All Readers ===");
        List<Reader> readers = readerService.getAllReaders();
        
        if (readers.isEmpty()) {
            System.out.println("No readers found.");
            return;
        }
        
        for (Reader reader : readers) {
            System.out.println(reader);
        }
    }
    
    private static void updateReader() {
        System.out.println("\n=== Update Reader ===");
        
        System.out.print("Enter reader ID to update: ");
        int readerId = scanner.nextInt();
        scanner.nextLine();
        
        Reader existingReader = readerService.findReader(readerId);
        if (existingReader == null) {
            System.out.println("Reader not found!");
            return;
        }
        
        System.out.print("Enter new full name (current: " + existingReader.getFullName() + "): ");
        String fullName = scanner.nextLine();
        
        System.out.print("Enter new birth date (current: " + dateFormat.format(existingReader.getBirthDate()) + "): ");
        Date birthDate = parseDate(scanner.nextLine());
        
        System.out.print("Enter new address (current: " + existingReader.getAddress() + "): ");
        String address = scanner.nextLine();
        
        Reader updatedReader = new Reader(readerId, fullName, birthDate, address);
        readerService.updateReader(updatedReader);
        System.out.println("Reader updated successfully!");
    }
    
    private static void deleteReader() {
        System.out.println("\n=== Delete Reader ===");
        
        System.out.print("Enter reader ID to delete: ");
        int readerId = scanner.nextInt();
        
        Reader existingReader = readerService.findReader(readerId);
        if (existingReader == null) {
            System.out.println("Reader not found!");
            return;
        }
        
        System.out.print("Are you sure you want to delete this reader? (y/n): ");
        String confirm = scanner.next();
        
        if (confirm.equalsIgnoreCase("y")) {
            readerService.deleteReader(readerId);
            System.out.println("Reader deleted successfully!");
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
    
    // Borrow Operations
    private static void borrowBook() {
        System.out.println("\n=== Borrow Book ===");
        
        System.out.print("Enter reader ID: ");
        int readerId = scanner.nextInt();
        Reader reader = readerService.findReader(readerId);
        if (reader == null) {
            System.out.println("Reader not found!");
            return;
        }
        
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        Book book = bookService.findBook(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (book.getQuantity() <= 0) {
            System.out.println("Book is not available!");
            return;
        }
        
        scanner.nextLine();
        System.out.print("Enter expected return date (dd/MM/yyyy): ");
        Date expectedReturnDate = parseDate(scanner.nextLine());
        
        Borrow borrow = new Borrow(0, reader, book, new Date(), expectedReturnDate, null);
        borrowService.borrowBook(borrow);
        
        // Update book quantity
        book.setQuantity(book.getQuantity() - 1);
        bookService.updateBook(book);
        
        System.out.println("Book borrowed successfully!");
    }
    
    private static void returnBook() {
        System.out.println("\n=== Return Book ===");
        
        System.out.print("Enter borrow ID: ");
        int borrowId = scanner.nextInt();
        
        Borrow borrow = borrowService.findBorrow(borrowId);
        if (borrow == null) {
            System.out.println("Borrow record not found!");
            return;
        }
        
        if (borrow.getActualReturnDate() != null) {
            System.out.println("Book already returned!");
            return;
        }
        
        borrowService.returnBook(borrowId);
        
        // Update book quantity
        Book book = borrow.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookService.updateBook(book);
        
        System.out.println("Book returned successfully!");
    }
    
    private static void viewAllBorrows() {
        System.out.println("\n=== All Borrows ===");
        List<Borrow> borrows = borrowService.getAllBorrows();
        
        if (borrows.isEmpty()) {
            System.out.println("No borrow records found.");
            return;
        }
        
        for (Borrow borrow : borrows) {
            System.out.println(borrow);
        }
    }
    
    private static void viewReaderBorrows() {
        System.out.println("\n=== Reader's Borrows ===");
        
        System.out.print("Enter reader ID: ");
        int readerId = scanner.nextInt();
        
        Reader reader = readerService.findReader(readerId);
        if (reader == null) {
            System.out.println("Reader not found!");
            return;
        }
        
        List<Borrow> borrows = borrowService.getBorrowsByReader(readerId);
        if (borrows.isEmpty()) {
            System.out.println("No borrow records found for this reader.");
            return;
        }
        
        for (Borrow borrow : borrows) {
            System.out.println(borrow);
        }
    }
    
    private static Date parseDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy");
        }
    }
} 