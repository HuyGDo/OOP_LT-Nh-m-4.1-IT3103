package dao;

import entity.Category;
import java.util.List;

public interface CategoryDAO {
    void add(Category category);
    void update(Category category);
    void delete(int categoryId);
    Category getById(int categoryId);
    List<Category> getAll();
} 