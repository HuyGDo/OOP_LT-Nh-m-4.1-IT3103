package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.BookDAO;
import entity.Book;
import entity.Category;
import utils.DatabaseConnection;

public class BookDAOImpl implements BookDAO {
    @Override
    public void add(Book book) {
        String sql = "INSERT INTO books (title, author, publisher, quantity, category_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getQuantity());
            stmt.setInt(5, book.getCategory().getCategoryId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding book: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, quantity = ?, category_id = ? " +
                    "WHERE book_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getQuantity());
            stmt.setInt(5, book.getCategory().getCategoryId());
            stmt.setInt(6, book.getBookId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int bookId) {
        // First check if book is currently borrowed
        String checkBorrowsSql = "SELECT COUNT(*) FROM borrows WHERE book_id = ? AND actual_return_date IS NULL";
        String deleteSql = "DELETE FROM books WHERE book_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check borrows
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBorrowsSql)) {
                checkStmt.setInt(1, bookId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("Cannot delete book: it is currently borrowed");
                }
            }
            
            // Delete book
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, bookId);
                int affectedRows = deleteStmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Deleting book failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book: " + e.getMessage(), e);
        }
    }

    @Override
    public Book getById(int bookId) {
        String sql = "SELECT b.*, c.category_name " +
                    "FROM books b " +
                    "JOIN categories c ON b.category_id = c.category_id " +
                    "WHERE b.book_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractBookFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.category_name " +
                    "FROM books b " +
                    "JOIN categories c ON b.category_id = c.category_id " +
                    "ORDER BY b.book_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print table header
            System.out.println("\n" + "=".repeat(100));
            System.out.printf("| %-5s | %-25s | %-20s | %-15s | %-8s | %-15s |\n",
                    "ID", "Title", "Author", "Publisher", "Quantity", "Category");
            System.out.println("-".repeat(100));
            
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
                // Print each row
                System.out.printf("| %-5d | %-25s | %-20s | %-15s | %-8d | %-15s |\n",
                    book.getBookId(),
                    truncateString(book.getTitle(), 25),
                    truncateString(book.getAuthor(), 20),
                    truncateString(book.getPublisher(), 15),
                    book.getQuantity(),
                    truncateString(book.getCategory().getCategoryName(), 15));
            }
            System.out.println("=".repeat(100));
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving books: " + e.getMessage(), e);
        }
        return books;
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category(
            rs.getInt("category_id"),
            rs.getString("category_name")
        );
        
        return new Book(
            rs.getInt("book_id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("publisher"),
            rs.getInt("quantity"),
            category
        );
    }

    private String truncateString(String str, int length) {
        if (str == null) return "";
        return str.length() <= length ? str : str.substring(0, length - 3) + "...";
    }
} 