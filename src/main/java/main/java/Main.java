package main.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entity.Book;
import entity.Borrow;
import entity.Category;
import entity.Reader;
import entity.User;
import report.BorrowingReport;
import report.PopularBooksReport;
import service.BookService;
import service.BorrowService;
import service.CategoryService;
import service.ReaderService;
import service.UserService;
import service.impl.BookServiceImpl;
import service.impl.BorrowServiceImpl;
import service.impl.CategoryServiceImpl;
import service.impl.ReaderServiceImpl;
import service.impl.UserServiceImpl;

public class Main {
    static {
        try {
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
    private static final UserService userService = new UserServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final String HORIZONTAL_LINE = "-".repeat(85);
    
    private static User currentUser = null;
    
    public static void main(String[] args) {
        while (true) {
            try {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    showMainMenu();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }
    
    private static void showLoginMenu() {
        System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM - LOGIN ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private static void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        User user = userService.login(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Welcome, " + user.getFullName() + "!");
        } else {
            System.out.println("Invalid username or password!");
        }
    }
    
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("Logged in as: " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
            System.out.println("1. Book Management");
            System.out.println("2. Category Management");
            System.out.println("3. Reader Management");
            System.out.println("4. Borrow Management");
            if (currentUser.getRole() == User.UserRole.ADMIN) {
                System.out.println("5. Reports");
                System.out.println("6. User Management");
                System.out.println("7. Logout");
                System.out.println("8. Exit");
            } else {
                System.out.println("5. Logout");
                System.out.println("6. Exit");
            }
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (currentUser.getRole() == User.UserRole.ADMIN) {
                switch (choice) {
                    case 1: handleBookMenu(); break;
                    case 2: handleCategoryMenu(); break;
                    case 3: handleReaderMenu(); break;
                    case 4: handleBorrowMenu(); break;
                    case 5: handleReportsMenu(); break;
                    case 6: handleUserMenu(); break;
                    case 7: 
                        currentUser = null;
                        return;
                    case 8:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option!");
                }
            } else {
                switch (choice) {
                    case 1: handleLimitedBookMenu(); break;
                    case 2: handleLimitedCategoryMenu(); break;
                    case 3: handleLimitedReaderMenu(); break;
                    case 4: handleLimitedBorrowMenu(); break;
                    case 5: 
                        currentUser = null;
                        return;
                    case 6:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option!");
                }
            }
        }
    }
    
    // Limited menus for regular users
    private static void handleLimitedBookMenu() {
        while (true) {
            System.out.println("\n=== Book Management ===");
            System.out.println("1. View all books");
            System.out.println("2. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private static void handleLimitedCategoryMenu() {
        while (true) {
            System.out.println("\n=== Category Management ===");
            System.out.println("1. View all categories");
            System.out.println("2. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    viewAllCategories();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private static void handleLimitedReaderMenu() {
        while (true) {
            System.out.println("\n=== Reader Management ===");
            System.out.println("1. View all readers");
            System.out.println("2. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    viewAllReaders();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private static void handleLimitedBorrowMenu() {
        while (true) {
            System.out.println("\n=== Borrow Management ===");
            System.out.println("1. View all borrows");
            System.out.println("2. View reader's borrows");
            System.out.println("3. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    viewAllBorrows();
                    break;
                case 2:
                    viewReaderBorrows();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    // User Management Methods
    private static void handleUserMenu() {
        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Add new user");
            System.out.println("2. View all users");
            System.out.println("3. Update user");
            System.out.println("4. Delete user");
            System.out.println("5. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private static void addUser() {
        System.out.println("\n=== Add New User ===");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        
        System.out.print("Enter role (ADMIN/USER): ");
        User.UserRole role = User.UserRole.valueOf(scanner.nextLine().toUpperCase());
        
        User user = new User(0, username, password, fullName, role, new Date());
        userService.addUser(user);
        System.out.println("User added successfully!");
    }
    
    private static void viewAllUsers() {
        System.out.println("\n=== All Users ===");
        List<User> users = userService.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
    }
    
    private static void updateUser() {
        System.out.println("\n=== Update User ===");
        
        System.out.print("Enter user ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        
        User existingUser = userService.findUser(userId);
        if (existingUser == null) {
            System.out.println("User not found!");
            return;
        }
        
        System.out.print("Enter new username (current: " + existingUser.getUsername() + "): ");
        String username = scanner.nextLine();
        
        System.out.print("Enter new password (leave blank to keep current): ");
        String password = scanner.nextLine();
        if (password.trim().isEmpty()) {
            password = existingUser.getPassword();
        }
        
        System.out.print("Enter new full name (current: " + existingUser.getFullName() + "): ");
        String fullName = scanner.nextLine();
        
        System.out.print("Enter new role (current: " + existingUser.getRole() + "): ");
        User.UserRole role = User.UserRole.valueOf(scanner.nextLine().toUpperCase());
        
        User updatedUser = new User(userId, username, password, fullName, role, existingUser.getCreatedAt());
        userService.updateUser(updatedUser);
        System.out.println("User updated successfully!");
    }
    
    private static void deleteUser() {
        System.out.println("\n=== Delete User ===");
        
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        
        if (userId == currentUser.getUserId()) {
            System.out.println("Cannot delete your own account!");
            return;
        }
        
        User existingUser = userService.findUser(userId);
        if (existingUser == null) {
            System.out.println("User not found!");
            return;
        }
        
        System.out.print("Are you sure you want to delete this user? (y/n): ");
        String confirm = scanner.next();
        
        if (confirm.equalsIgnoreCase("y")) {
            userService.deleteUser(userId);
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Delete operation cancelled.");
        }
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
    
    // Reports Management Methods
    private static void handleReportsMenu() {
        while (true) {
            System.out.println("\n=== Reports ===");
            System.out.println("1. Borrowing Activity Report");
            System.out.println("2. Popular Books Report");
            System.out.println("3. Back to main menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    generateBorrowingReport();
                    break;
                case 2:
                    generatePopularBooksReport();
                    break;
                case 3:
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
        System.out.println("\n=== Borrow Books ===");
        
        // Get reader information
        System.out.print("Enter reader ID: ");
        int readerId = scanner.nextInt();
        Reader reader = readerService.findReader(readerId);
        if (reader == null) {
            System.out.println("Reader not found!");
            return;
        }
        
        // Get number of books to borrow
        System.out.print("How many books do you want to borrow? ");
        int numberOfBooks = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (numberOfBooks <= 0) {
            System.out.println("Invalid number of books!");
            return;
        }
        
        // Get expected return date (same for all books)
        System.out.print("Enter expected return date (dd/MM/yyyy): ");
        Date expectedReturnDate = parseDate(scanner.nextLine());
        
        // Process each book
        for (int i = 0; i < numberOfBooks; i++) {
            System.out.printf("\nEnter book ID for book %d of %d: ", (i + 1), numberOfBooks);
            int bookId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            Book book = bookService.findBook(bookId);
            if (book == null) {
                System.out.println("Book not found! Skipping this book.");
                continue;
            }
            
            if (book.getQuantity() <= 0) {
                System.out.println("Book is not available! Skipping this book.");
                continue;
            }
            
            try {
                // Create and process the borrow record
                Borrow borrow = new Borrow(0, reader, book, new Date(), expectedReturnDate, null);
                borrowService.borrowBook(borrow);
                
                // Update book quantity
                book.setQuantity(book.getQuantity() - 1);
                bookService.updateBook(book);
                
                System.out.printf("Successfully borrowed: %s\n", book.getTitle());
                
            } catch (Exception e) {
                System.out.printf("Error borrowing book '%s': %s\n", 
                                book.getTitle(), e.getMessage());
            }
        }
        
        // Show summary of reader's current borrows
        System.out.println("\n=== Current Borrows for " + reader.getFullName() + " ===");
        List<Borrow> borrows = borrowService.getBorrowsByReader(readerId);
        for (Borrow borrow : borrows) {
            if (borrow.getActualReturnDate() == null) { // Show only active borrows
                System.out.println(borrow);
            }
        }
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
    
    private static void generateBorrowingReport() {
        BorrowingReport report = new BorrowingReport();
        List<String> reportLines = report.generateReport();
        System.out.println();
        for (String line : reportLines) {
            System.out.println(line);
        }
    }
    
    private static void generatePopularBooksReport() {
        PopularBooksReport report = new PopularBooksReport();
        List<String> reportLines = report.generateReport();
        System.out.println();
        for (String line : reportLines) {
            System.out.println(line);
        }
    }
} 