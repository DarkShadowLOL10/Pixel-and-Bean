package com.pixelbean.test;

import com.pixelbean.models.*;
import com.pixelbean.services.*;

/**
 * Simple test runner to verify basic functionality
 * Run with: java -cp bin com.pixelbean.test.BasicTest
 */
public class BasicTest {
    
    public static void main(String[] args) {
        System.out.println("=== Pixel & Bean - Basic Functionality Tests ===\n");
        
        int passed = 0;
        int failed = 0;
        
        // Test 1: UserService authentication
        System.out.print("Test 1: UserService authentication... ");
        try {
            UserService userService = UserService.getInstance();
            User admin = userService.authenticate("admin", "admin123");
            if (admin != null && admin.getRole() == Role.ADMIN) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 2: UserService find all
        System.out.print("Test 2: UserService find all users... ");
        try {
            UserService userService = UserService.getInstance();
            if (userService.findAll().size() == 2) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 3: ProductService find active products
        System.out.print("Test 3: ProductService find active products... ");
        try {
            ProductService productService = ProductService.getInstance();
            if (productService.findActiveProducts().size() == 10) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 4: Create and save a sale
        System.out.print("Test 4: SaleService create sale... ");
        try {
            UserService userService = UserService.getInstance();
            ProductService productService = ProductService.getInstance();
            SaleService saleService = SaleService.getInstance();
            
            User user = userService.findById(1L);
            Product product = productService.findById(1L);
            
            Sale sale = new Sale();
            sale.setUser(user);
            SaleItem item = new SaleItem(product, 2);
            sale.addItem(item);
            
            saleService.save(sale);
            
            if (sale.getId() != null && sale.getTotal() == 70.0) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 5: Sale total calculation
        System.out.print("Test 5: Sale automatic total calculation... ");
        try {
            ProductService productService = ProductService.getInstance();
            
            Sale sale = new Sale();
            Product product1 = productService.findById(1L); // $35
            Product product2 = productService.findById(2L); // $45
            
            sale.addItem(new SaleItem(product1, 2)); // $70
            sale.addItem(new SaleItem(product2, 1)); // $45
            
            if (sale.getTotal() == 115.0) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 6: User activation/deactivation
        System.out.print("Test 6: User activation/deactivation... ");
        try {
            UserService userService = UserService.getInstance();
            User user = new User();
            user.setUsername("testuser");
            user.setPassword("test123");
            user.setFullName("Test User");
            user.setRole(Role.OPERADOR);
            user.setStatus(Status.ACTIVE);
            
            User savedUser = userService.save(user);
            Long userId = savedUser.getId();
            
            userService.deactivate(userId);
            User deactivated = userService.findById(userId);
            
            if (deactivated == null) {
                System.out.println("FAILED - User not found after deactivation");
                failed++;
            } else if (deactivated.getStatus() != Status.INACTIVE) {
                System.out.println("FAILED - Status not INACTIVE: " + deactivated.getStatus());
                failed++;
            } else {
                userService.activate(userId);
                User activated = userService.findById(userId);
                
                if (activated.getStatus() == Status.ACTIVE) {
                    System.out.println("PASSED");
                    passed++;
                } else {
                    System.out.println("FAILED - Status not ACTIVE after activation");
                    failed++;
                }
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            e.printStackTrace();
            failed++;
        }
        
        // Test 7: Product creation and retrieval
        System.out.print("Test 7: Product creation and retrieval... ");
        try {
            ProductService productService = ProductService.getInstance();
            Product product = new Product();
            product.setName("Test Product");
            product.setDescription("Test Description");
            product.setPrice(99.99);
            product.setStatus(Status.ACTIVE);
            
            productService.save(product);
            Product retrieved = productService.findById(product.getId());
            
            if (retrieved != null && retrieved.getPrice() == 99.99) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }
        
        // Test 8: SaleService find today
        System.out.print("Test 8: SaleService find today sales... ");
        try {
            SaleService saleService = SaleService.getInstance();
            int todaySales = saleService.findToday().size();
            // Should have the one sale from test 4
            if (todaySales >= 1) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
        
        if (failed == 0) {
            System.out.println("\n✅ All tests passed!");
            System.exit(0);
        } else {
            System.out.println("\n❌ Some tests failed!");
            System.exit(1);
        }
    }
}
