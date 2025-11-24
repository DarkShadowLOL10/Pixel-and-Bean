package com.pixelbean.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Sale domain model
 */
public class Sale {
    private Long id;
    private LocalDateTime date;
    private User user;
    private List<SaleItem> items;
    private double total;
    
    public Sale() {
        this.date = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.total = 0.0;
    }
    
    public Sale(Long id, LocalDateTime date, User user) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }
    
    public void addItem(SaleItem item) {
        this.items.add(item);
        calculateTotal();
    }
    
    public void removeItem(SaleItem item) {
        this.items.remove(item);
        calculateTotal();
    }
    
    public void calculateTotal() {
        this.total = items.stream()
                .mapToDouble(SaleItem::getSubtotal)
                .sum();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<SaleItem> getItems() {
        return items;
    }
    
    public void setItems(List<SaleItem> items) {
        this.items = items;
        calculateTotal();
    }
    
    public double getTotal() {
        return total;
    }
}
