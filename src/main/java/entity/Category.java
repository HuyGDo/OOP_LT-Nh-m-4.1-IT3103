package entity;

public class Category {
    private int categoryId;
    private String categoryName;
    
    // Constructor
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    
    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Override toString() method to display category information properly
    @Override
    public String toString() {
        return "Category ID: " + categoryId + ", Name: " + categoryName;
    }
} 