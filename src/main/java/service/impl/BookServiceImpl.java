package service.impl;

import dao.BookDAO;
import dao.impl.BookDAOImpl;
import entity.Book;
import service.BookService;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO = new BookDAOImpl();
    
    @Override
    public void addBook(Book book) {
        validateBook(book);
        bookDAO.add(book);
    }
    
    @Override
    public void updateBook(Book book) {
        validateBook(book);
        bookDAO.update(book);
    }
    
    @Override
    public void deleteBook(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        bookDAO.delete(bookId);
    }
    
    @Override
    public Book findBook(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        return bookDAO.getById(bookId);
    }
    
    @Override
    public List<Book> getAllBooks() {
        return bookDAO.getAll();
    }
    
    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be empty");
        }
        if (book.getQuantity() < 0) {
            throw new IllegalArgumentException("Book quantity cannot be negative");
        }
        if (book.getCategory() == null) {
            throw new IllegalArgumentException("Book category cannot be null");
        }
    }
} 