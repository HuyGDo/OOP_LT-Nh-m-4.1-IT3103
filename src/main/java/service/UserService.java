package service;

import java.util.List;

import entity.User;

public interface UserService {
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int userId);
    User findUser(int userId);
    User findUserByUsername(String username);
    List<User> getAllUsers();
    User login(String username, String password);
} 