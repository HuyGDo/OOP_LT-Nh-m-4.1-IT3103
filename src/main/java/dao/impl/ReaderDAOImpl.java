package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entity.Reader;
import utils.DatabaseConnection;

public class ReaderDAOImpl {
    public void add(Reader reader) {
        String sql = "INSERT INTO readers (full_name, birth_date, address) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, reader.getFullName());
            stmt.setDate(2, new java.sql.Date(reader.getBirthDate().getTime()));
            stmt.setString(3, reader.getAddress());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating reader failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reader.setReaderId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating reader failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding reader: " + e.getMessage(), e);
        }
    }

    public void update(Reader reader) {
        String sql = "UPDATE readers SET full_name=?, birth_date=?, address=? WHERE reader_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, reader.getFullName());
            stmt.setDate(2, new java.sql.Date(reader.getBirthDate().getTime()));
            stmt.setString(3, reader.getAddress());
            stmt.setInt(4, reader.getReaderId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Reader with ID " + reader.getReaderId() + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating reader: " + e.getMessage(), e);
        }
    }

    public void delete(int readerId) {
        // First check if reader has any active borrows
        String checkBorrows = "SELECT COUNT(*) FROM borrows WHERE reader_id = ? AND actual_return_date IS NULL";
        String deleteReader = "DELETE FROM readers WHERE reader_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check active borrows
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBorrows)) {
                checkStmt.setInt(1, readerId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new RuntimeException("Cannot delete reader with active borrows");
                }
            }
            
            // Delete reader
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteReader)) {
                deleteStmt.setInt(1, readerId);
                int affectedRows = deleteStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new RuntimeException("Reader with ID " + readerId + " not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting reader: " + e.getMessage(), e);
        }
    }

    public Reader getById(int readerId) {
        String sql = "SELECT * FROM readers WHERE reader_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, readerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractReaderFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting reader by ID", e);
        }
        return null;
    }

    public List<Reader> getAll() {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM readers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print table header
            System.out.println("\n" + "=".repeat(85));
            System.out.printf("| %-5s | %-25s | %-15s | %-30s |\n",
                    "ID", "Full Name", "Birth Date", "Address");
            System.out.println("-".repeat(85));
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                Reader reader = extractReaderFromResultSet(rs);
                readers.add(reader);
                // Print each row
                System.out.printf("| %-5d | %-25s | %-15s | %-30s |\n",
                    reader.getReaderId(),
                    truncateString(reader.getFullName(), 25),
                    dateFormat.format(reader.getBirthDate()),
                    truncateString(reader.getAddress(), 30));
            }
            System.out.println("=".repeat(85));
            
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all readers", e);
        }
        return readers;
    }

    private Reader extractReaderFromResultSet(ResultSet rs) throws SQLException {
        return new Reader(
            rs.getInt("reader_id"),
            rs.getString("full_name"),
            rs.getDate("birth_date"),
            rs.getString("address")
        );
    }

    private String truncateString(String str, int length) {
        if (str == null) return "";
        return str.length() <= length ? str : str.substring(0, length - 3) + "...";
    }
} 