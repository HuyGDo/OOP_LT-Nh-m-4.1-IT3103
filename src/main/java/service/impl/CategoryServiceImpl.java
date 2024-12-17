package service.impl;

import dao.CategoryDAO;
import dao.impl.CategoryDAOImpl;
import entity.Category;
import service.CategoryService;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    
    @Override
    public void addCategory(Category category) {
        validateCategory(category);
        categoryDAO.add(category);
    }
    
    @Override
    public void updateCategory(Category category) {
        validateCategory(category);
        categoryDAO.update(category);
    }
    
    @Override
    public void deleteCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        categoryDAO.delete(categoryId);
    }
    
    @Override
    public Category findCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        return categoryDAO.getById(categoryId);
    }
    
    @Override
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