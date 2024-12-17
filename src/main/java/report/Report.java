package report;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import utils.DatabaseConnection;

public abstract class Report {
    protected Connection connection;
    
    public Report() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error establishing database connection: " + e.getMessage(), e);
        }
    }
    
    public abstract List<String> generateReport();
    
    protected void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing connection: " + e.getMessage());
        }
    }
} 