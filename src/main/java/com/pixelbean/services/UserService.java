package com.pixelbean.services;

import com.pixelbean.models.Role;
import com.pixelbean.models.Status;
import com.pixelbean.models.User;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Stub service for user management with in-memory storage
 */
public class UserService {
    private static UserService instance;
    private final Map<Long, User> users;
    private Long nextId;
    private User currentUser;
    
    private UserService() {
        this.users = new HashMap<>();
        this.nextId = 1L;
        this.currentUser = null;
        initializeDefaultUsers();
    }
    
    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    
    private void initializeDefaultUsers() {
        // Create default admin user
        User admin = new User(nextId++, "admin", "admin123", "Administrador Principal", Role.ADMIN, Status.ACTIVE);
        users.put(admin.getId(), admin);
        
        // Create default operator user
        User operator = new User(nextId++, "operador", "oper123", "Operador Turno 1", Role.OPERADOR, Status.ACTIVE);
        users.put(operator.getId(), operator);
    }
    
    /**
     * Authenticates a user with username and password.
     * 
     * NOTE: This is a prototype implementation with plain text passwords.
     * In a production system, passwords should be hashed using BCrypt or Argon2.
     * 
     * @param username the username
     * @param password the password in plain text
     * @return the authenticated User or null if authentication fails
     */
    public User authenticate(String username, String password) {
        Optional<User> user = users.values().stream()
                .filter(u -> u.getUsername().equals(username) 
                        && u.getPassword().equals(password)
                        && u.isActive())
                .findFirst();
        
        if (user.isPresent()) {
            currentUser = user.get();
            return currentUser;
        }
        return null;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    public List<User> findByStatus(Status status) {
        return users.values().stream()
                .filter(u -> u.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    public User findById(Long id) {
        return users.get(id);
    }
    
    public User findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        users.put(user.getId(), user);
        return user;
    }
    
    public void delete(Long id) {
        users.remove(id);
    }
    
    public void activate(Long id) {
        User user = users.get(id);
        if (user != null) {
            user.setStatus(Status.ACTIVE);
        }
    }
    
    public void deactivate(Long id) {
        User user = users.get(id);
        if (user != null) {
            user.setStatus(Status.INACTIVE);
        }
    }
    
    public boolean usernameExists(String username, Long excludeId) {
        return users.values().stream()
                .anyMatch(u -> u.getUsername().equals(username) 
                        && !u.getId().equals(excludeId));
    }
}
