package dao;

import java.util.List;
import entity.Book;

public interface BookDAO {
    void add(Book book);
    void update(Book book);
    void delete(int bookId);
    Book getById(int bookId);
    List<Book> getAll();
} 