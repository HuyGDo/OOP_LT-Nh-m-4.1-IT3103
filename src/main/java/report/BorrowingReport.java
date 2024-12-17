package report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowingReport extends Report {
    
    @Override
    public List<String> generateReport() {
        List<String> report = new ArrayList<>();
        
        String sql = "SELECT r.reader_id, r.full_name, " +
                    "COUNT(b.borrow_id) as total_borrows, " +
                    "SUM(CASE WHEN b.actual_return_date IS NULL THEN 1 ELSE 0 END) as current_borrows, " +
                    "SUM(CASE WHEN b.actual_return_date > b.expected_return_date THEN 1 ELSE 0 END) as late_returns " +
                    "FROM readers r " +
                    "LEFT JOIN borrows b ON r.reader_id = b.reader_id " +
                    "GROUP BY r.reader_id, r.full_name " +
                    "ORDER BY total_borrows DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            report.add("=== Borrowing Activity Report ===");
            report.add(String.format("%-10s %-20s %-15s %-15s %-15s", 
                      "Reader ID", "Reader Name", "Total Borrows", "Current Borrows", "Late Returns"));
            report.add("-".repeat(75));
            
            while (rs.next()) {
                String line = String.format("%-10d %-20s %-15d %-15d %-15d",
                    rs.getInt("reader_id"),
                    rs.getString("full_name"),
                    rs.getInt("total_borrows"),
                    rs.getInt("current_borrows"),
                    rs.getInt("late_returns")
                );
                report.add(line);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error generating borrowing report: " + e.getMessage());
        } finally {
            closeConnection();
        }
        
        return report;
    }
} 