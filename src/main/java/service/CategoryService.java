package service;

import entity.Category;
import java.util.List;

public interface CategoryService {
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int categoryId);
    Category findCategory(int categoryId);
    List<Category> getAllCategories();
} 