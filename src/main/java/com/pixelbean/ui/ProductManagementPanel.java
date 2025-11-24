package com.pixelbean.ui;

import com.pixelbean.models.Product;
import com.pixelbean.models.Status;
import com.pixelbean.services.ProductService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for product management
 */
public class ProductManagementPanel extends JPanel {
    private final ProductService productService;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, activateButton, deactivateButton, refreshButton;
    
    public ProductManagementPanel() {
        this.productService = ProductService.getInstance();
        initComponents();
        loadProducts();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Gestión de Productos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        
        // Table
        String[] columns = {"ID", "Nombre", "Descripción", "Precio", "Estado"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(productTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        addButton = new JButton("Nuevo Producto");
        addButton.addActionListener(e -> addProduct());
        
        editButton = new JButton("Editar");
        editButton.addActionListener(e -> editProduct());
        editButton.setEnabled(false);
        
        activateButton = new JButton("Activar");
        activateButton.addActionListener(e -> activateProduct());
        activateButton.setEnabled(false);
        
        deactivateButton = new JButton("Desactivar");
        deactivateButton.addActionListener(e -> deactivateProduct());
        deactivateButton.setEnabled(false);
        
        refreshButton = new JButton("Actualizar");
        refreshButton.addActionListener(e -> loadProducts());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(activateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(refreshButton);
        
        // Table selection listener
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
        
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadProducts() {
        tableModel.setRowCount(0);
        List<Product> products = productService.findAll();
        for (Product product : products) {
            Object[] row = {
                product.getId(),
                product.getName(),
                product.getDescription(),
                String.format("$%.2f", product.getPrice()),
                product.getStatus().getDisplayName()
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateButtonStates() {
        int selectedRow = productTable.getSelectedRow();
        boolean selected = selectedRow >= 0;
        
        editButton.setEnabled(selected);
        
        if (selected) {
            Long productId = (Long) tableModel.getValueAt(selectedRow, 0);
            Product product = productService.findById(productId);
            activateButton.setEnabled(product.getStatus() == Status.INACTIVE);
            deactivateButton.setEnabled(product.getStatus() == Status.ACTIVE);
        } else {
            activateButton.setEnabled(false);
            deactivateButton.setEnabled(false);
        }
    }
    
    private void addProduct() {
        ProductDialog dialog = new ProductDialog((Frame) SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Product product = dialog.getProduct();
            productService.save(product);
            loadProducts();
            JOptionPane.showMessageDialog(this,
                    "Producto creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void editProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long productId = (Long) tableModel.getValueAt(selectedRow, 0);
            Product product = productService.findById(productId);
            
            ProductDialog dialog = new ProductDialog((Frame) SwingUtilities.getWindowAncestor(this), product);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                Product updatedProduct = dialog.getProduct();
                productService.save(updatedProduct);
                loadProducts();
                JOptionPane.showMessageDialog(this,
                        "Producto actualizado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void activateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long productId = (Long) tableModel.getValueAt(selectedRow, 0);
            productService.activate(productId);
            loadProducts();
            JOptionPane.showMessageDialog(this,
                    "Producto activado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void deactivateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long productId = (Long) tableModel.getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea desactivar este producto?",
                    "Confirmar desactivación",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                productService.deactivate(productId);
                loadProducts();
                JOptionPane.showMessageDialog(this,
                        "Producto desactivado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
