package report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PopularBooksReport extends Report {
    
    @Override
    public List<String> generateReport() {
        List<String> report = new ArrayList<>();
        
        String sql = "SELECT b.book_id, b.title, b.author, c.category_name, " +
                    "COUNT(br.borrow_id) as borrow_count " +
                    "FROM books b " +
                    "LEFT JOIN borrows br ON b.book_id = br.book_id " +
                    "JOIN categories c ON b.category_id = c.category_id " +
                    "GROUP BY b.book_id, b.title, b.author, c.category_name " +
                    "ORDER BY borrow_count DESC " +
                    "LIMIT 10";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            report.add("=== Most Popular Books Report ===");
            report.add(String.format("%-10s %-30s %-20s %-15s %-10s", 
                      "Book ID", "Title", "Author", "Category", "Borrows"));
            report.add("-".repeat(85));
            
            while (rs.next()) {
                String line = String.format("%-10d %-30s %-20s %-15s %-10d",
                    rs.getInt("book_id"),
                    truncateString(rs.getString("title"), 27),
                    truncateString(rs.getString("author"), 17),
                    rs.getString("category_name"),
                    rs.getInt("borrow_count")
                );
                report.add(line);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error generating popular books report: " + e.getMessage());
        } finally {
            closeConnection();
        }
        
        return report;
    }
    
    private String truncateString(String str, int length) {
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
} 