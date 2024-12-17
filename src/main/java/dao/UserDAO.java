package dao;

import java.util.List;

import entity.User;

public interface UserDAO {
    void add(User user);
    void update(User user);
    void delete(int userId);
    User getById(int userId);
    User getByUsername(String username);
    List<User> getAll();
    User authenticate(String username, String password);
} 