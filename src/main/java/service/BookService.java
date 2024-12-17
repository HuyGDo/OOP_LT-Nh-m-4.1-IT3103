package service;

import entity.Book;
import java.util.List;

public interface BookService {
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int bookId);
    Book findBook(int bookId);
    List<Book> getAllBooks();
} 