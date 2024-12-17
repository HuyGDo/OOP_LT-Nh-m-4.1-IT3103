package service.impl;

import java.util.List;

import dao.impl.CategoryDAOImpl;
import entity.Category;

public class CategoryServiceImpl {
    private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    
    public void addCategory(Category category) {
        validateCategory(category);
        categoryDAO.add(category);
    }
    
    public void updateCategory(Category category) {
        validateCategory(category);
        categoryDAO.update(category);
    }
    
    public void deleteCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        categoryDAO.delete(categoryId);
    }
    
    public Category findCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        return categoryDAO.getById(categoryId);
    }
    
    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }
    
    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
    }
} 