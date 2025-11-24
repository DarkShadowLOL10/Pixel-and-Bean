package com.pixelbean.models;

/**
 * Product domain model
 */
public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Status status;
    
    public Product() {
        this.status = Status.ACTIVE;
    }
    
    public Product(Long id, String name, String description, double price, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
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
