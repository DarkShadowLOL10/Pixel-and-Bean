package com.pixelbean.models;

/**
 * Sale item representing a product in a sale
 */
public class SaleItem {
    private Product product;
    private int quantity;
    private double unitPrice;
    
    public SaleItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public double getSubtotal() {
        return quantity * unitPrice;
    }
}
