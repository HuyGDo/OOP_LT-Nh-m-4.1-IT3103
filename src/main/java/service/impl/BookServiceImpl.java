package service.impl;

import java.util.List;

import dao.impl.BookDAOImpl;
import entity.Book;

public class BookServiceImpl {
    private final BookDAOImpl bookDAO = new BookDAOImpl();
    
    public void addBook(Book book) {
        validateBook(book);
        bookDAO.add(book);
    }
    
    public void updateBook(Book book) {
        validateBook(book);
        bookDAO.update(book);
    }
    
    public void deleteBook(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        bookDAO.delete(bookId);
    }
    
    public Book findBook(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        return bookDAO.getById(bookId);
    }
    
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