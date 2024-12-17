package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Category;
import utils.DatabaseConnection;

public class CategoryDAOImpl {
    public void add(Category category) {
        String sql = "INSERT INTO categories (category_name) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getCategoryName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding category", e);
        }
    }

    public void update(Category category) {
        String sql = "UPDATE categories SET category_name=? WHERE category_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getCategoryName());
            stmt.setInt(2, category.getCategoryId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating category", e);
        }
    }

    public void delete(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category", e);
        }
    }

    public Category getById(int categoryId) {
        String sql = "SELECT * FROM categories WHERE category_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractCategoryFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting category by ID", e);
        }
        return null;
    }

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Print table header
            System.out.println("\n" + "=".repeat(40));
            System.out.printf("| %-10s | %-25s |\n", "ID", "Category Name");
            System.out.println("-".repeat(40));
            
            while (rs.next()) {
                Category category = extractCategoryFromResultSet(rs);
                categories.add(category);
                // Print each row
                System.out.printf("| %-10d | %-25s |\n", 
                    category.getCategoryId(), 
                    category.getCategoryName());
            }
            System.out.println("=".repeat(40));
            
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all categories", e);
        }
        return categories;
    }

    private Category extractCategoryFromResultSet(ResultSet rs) throws SQLException {
        return new Category(
            rs.getInt("category_id"),
            rs.getString("category_name")
        );
    }
} 