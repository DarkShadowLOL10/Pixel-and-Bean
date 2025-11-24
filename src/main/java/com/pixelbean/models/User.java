package com.pixelbean.models;

/**
 * User domain model
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private Role role;
    private Status status;
    
    public User() {
        this.status = Status.ACTIVE;
    }
    
    public User(Long id, String username, String password, String fullName, Role role, Status status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public boolean isActive() {
        return status == Status.ACTIVE;
    }
}
