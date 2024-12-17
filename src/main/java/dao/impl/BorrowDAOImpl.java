package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.BookDAO;
import dao.BorrowDAO;
import dao.ReaderDAO;
import entity.Book;
import entity.Borrow;
import entity.Category;
import entity.Reader;
import utils.DatabaseConnection;

public class BorrowDAOImpl implements BorrowDAO {
    private final BookDAO bookDAO = new BookDAOImpl();
    private final ReaderDAO readerDAO = new ReaderDAOImpl();

    @Override
    public void add(Borrow borrow) {
        String sql = "INSERT INTO borrows (reader_id, book_id, borrow_date, expected_return_date, actual_return_date) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, borrow.getReader().getReaderId());
            stmt.setInt(2, borrow.getBook().getBookId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getExpectedReturnDate().getTime()));
            stmt.setDate(5, borrow.getActualReturnDate() != null ? 
                        new java.sql.Date(borrow.getActualReturnDate().getTime()) : null);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating borrow record failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    borrow.setBorrowId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating borrow record failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding borrow record: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Borrow borrow) {
        String sql = "UPDATE borrows SET reader_id = ?, book_id = ?, borrow_date = ?, " +
                    "expected_return_date = ?, actual_return_date = ? WHERE borrow_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, borrow.getReader().getReaderId());
            stmt.setInt(2, borrow.getBook().getBookId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getExpectedReturnDate().getTime()));
            stmt.setDate(5, borrow.getActualReturnDate() != null ? 
                        new java.sql.Date(borrow.getActualReturnDate().getTime()) : null);
            stmt.setInt(6, borrow.getBorrowId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating borrow record failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating borrow record: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int borrowId) {
        String sql = "DELETE FROM borrows WHERE borrow_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, borrowId);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Deleting borrow record failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting borrow record: " + e.getMessage(), e);
        }
    }

    @Override
    public Borrow getById(int borrowId) {
        String sql = "SELECT b.*, r.full_name, r.birth_date, r.address, " +
                    "bk.title, bk.author, bk.publisher, bk.quantity, " +
                    "c.category_id, c.category_name " +
                    "FROM borrows b " +
                    "JOIN readers r ON b.reader_id = r.reader_id " +
                    "JOIN books bk ON b.book_id = bk.book_id " +
                    "JOIN categories c ON bk.category_id = c.category_id " +
                    "WHERE b.borrow_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, borrowId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractBorrowFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving borrow record: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Borrow> getAll() {
        List<Borrow> borrows = new ArrayList<>();
        String sql = "SELECT b.*, r.full_name, r.birth_date, r.address, " +
                    "bk.title, bk.author, bk.publisher, bk.quantity, " +
                    "c.category_id, c.category_name " +
                    "FROM borrows b " +
                    "JOIN readers r ON b.reader_id = r.reader_id " +
                    "JOIN books bk ON b.book_id = bk.book_id " +
                    "JOIN categories c ON bk.category_id = c.category_id " +
                    "ORDER BY b.borrow_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                borrows.add(extractBorrowFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving borrow records: " + e.getMessage(), e);
        }
        return borrows;
    }

    @Override
    public List<Borrow> getByReaderId(int readerId) {
        List<Borrow> borrows = new ArrayList<>();
        String sql = "SELECT b.*, r.full_name, r.birth_date, r.address, " +
                    "bk.title, bk.author, bk.publisher, bk.quantity, " +
                    "c.category_id, c.category_name " +
                    "FROM borrows b " +
                    "JOIN readers r ON b.reader_id = r.reader_id " +
                    "JOIN books bk ON b.book_id = bk.book_id " +
                    "JOIN categories c ON bk.category_id = c.category_id " +
                    "WHERE b.reader_id = ? " +
                    "ORDER BY b.borrow_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, readerId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    borrows.add(extractBorrowFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reader's borrow records: " + e.getMessage(), e);
        }
        return borrows;
    }

    private Borrow extractBorrowFromResultSet(ResultSet rs) throws SQLException {
        // Extract Reader
        Reader reader = new Reader(
            rs.getInt("reader_id"),
            rs.getString("full_name"),
            rs.getDate("birth_date"),
            rs.getString("address")
        );
        
        // Extract Category
        Category category = new Category(
            rs.getInt("category_id"),
            rs.getString("category_name")
        );
        
        // Extract Book
        Book book = new Book(
            rs.getInt("book_id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("publisher"),
            rs.getInt("quantity"),
            category
        );
        
        // Create Borrow
        return new Borrow(
            rs.getInt("borrow_id"),
            reader,
            book,
            rs.getDate("borrow_date"),
            rs.getDate("expected_return_date"),
            rs.getDate("actual_return_date")
        );
    }
} 