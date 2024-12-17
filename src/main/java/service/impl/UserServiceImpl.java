package service.impl;

import java.util.List;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAOImpl();
    
    @Override
    public void addUser(User user) {
        validateUser(user);
        userDAO.add(user);
    }
    
    @Override
    public void updateUser(User user) {
        validateUser(user);
        userDAO.update(user);
    }
    
    @Override
    public void deleteUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        userDAO.delete(userId);
    }
    
    @Override
    public User findUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return userDAO.getById(userId);
    }
    
    @Override
    public User findUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        return userDAO.getByUsername(username);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    
    @Override
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