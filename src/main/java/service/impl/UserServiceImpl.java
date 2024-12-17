package service.impl;

import java.util.List;

import dao.impl.UserDAOImpl;
import entity.User;

public class UserServiceImpl {
    private final UserDAOImpl userDAO = new UserDAOImpl();
    
    public void addUser(User user) {
        validateUser(user);
        userDAO.add(user);
    }
    
    public void updateUser(User user) {
        validateUser(user);
        userDAO.update(user);
    }
    
    public void deleteUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        userDAO.delete(userId);
    }
    
    public User findUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return userDAO.getById(userId);
    }
    
    public User findUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        return userDAO.getByUsername(username);
    }
    
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return userDAO.authenticate(username, password);
    }
    
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
    }
} 