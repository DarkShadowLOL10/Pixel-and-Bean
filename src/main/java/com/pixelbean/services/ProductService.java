package com.pixelbean.services;

import com.pixelbean.models.Product;
import com.pixelbean.models.Status;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Stub service for product management with in-memory storage
 */
public class ProductService {
    private static ProductService instance;
    private final Map<Long, Product> products;
    private Long nextId;
    
    private ProductService() {
        this.products = new HashMap<>();
        this.nextId = 1L;
        initializeDefaultProducts();
    }
    
    public static synchronized ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }
    
    private void initializeDefaultProducts() {
        // Café products
        products.put(nextId, new Product(nextId++, "Café Americano", "Café negro tradicional", 35.00, Status.ACTIVE));
        products.put(nextId, new Product(nextId++, "Café Latte", "Café con leche espumosa", 45.00, Status.ACTIVE));
        products.put(nextId, new Product(nextId++, "Cappuccino", "Café con espuma de leche", 45.00, Status.ACTIVE));
        products.put(nextId, new Product(nextId++, "Espresso", "Café concentrado", 30.00, Status.ACTIVE));
        
        // Food products
        products.put(nextId, new Product(nextId++, "Croissant", "Pan francés de mantequilla", 25.00, Status.ACTIVE));
        products.put(nextId, new Product(nextId++, "Sandwich", "Sandwich de jamón y queso", 55.00, Status.ACTIVE));
        products.put(nextId, new Product(nextId++, "Muffin", "Muffin de chocolate", 30.00, Status.ACTIVE));
        
        // Arcade credits
        products.put(nextId, new Product(nextId++, "10 Fichas Arcade", "10 fichas para juegos", 50.00, Status.ACTIVE));
        products.put(nextId, new Product(nextId++, "25 Fichas Arcade", "25 fichas para juegos", 100.00, Status.ACTIVE));
        products.put(nextId, new Product(nextId++, "50 Fichas Arcade", "50 fichas para juegos", 180.00, Status.ACTIVE));
    }
    
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
    
    public List<Product> findByStatus(Status status) {
        return products.values().stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    public List<Product> findActiveProducts() {
        return findByStatus(Status.ACTIVE);
    }
    
    public Product findById(Long id) {
        return products.get(id);
    }
    
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(nextId++);
        }
        products.put(product.getId(), product);
        return product;
    }
    
    public void delete(Long id) {
        products.remove(id);
    }
    
    public void activate(Long id) {
        Product product = products.get(id);
        if (product != null) {
            product.setStatus(Status.ACTIVE);
        }
    }
    
    public void deactivate(Long id) {
        Product product = products.get(id);
        if (product != null) {
            product.setStatus(Status.INACTIVE);
        }
    }
}
