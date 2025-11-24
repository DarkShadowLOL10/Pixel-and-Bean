package com.pixelbean.ui;

import com.pixelbean.models.Product;
import com.pixelbean.models.Status;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for creating/editing products
 */
public class ProductDialog extends JDialog {
    private Product product;
    private boolean confirmed = false;
    
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JComboBox<Status> statusComboBox;
    private JButton saveButton, cancelButton;
    
    public ProductDialog(Frame parent, Product product) {
        super(parent, product == null ? "Nuevo Producto" : "Editar Producto", true);
        this.product = product;
        initComponents();
    }
    
    private void initComponents() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        // Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Descripción:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);
        
        // Price
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Precio:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        priceField = new JTextField(20);
        formPanel.add(priceField, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Estado:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        statusComboBox = new JComboBox<>(Status.values());
        statusComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Status) {
                    setText(((Status) value).getDisplayName());
                }
                return this;
            }
        });
        formPanel.add(statusComboBox, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> save());
        
        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Load product data if editing
        if (product != null) {
            loadProductData();
        }
        
        getRootPane().setDefaultButton(saveButton);
    }
    
    private void loadProductData() {
        nameField.setText(product.getName());
        descriptionField.setText(product.getDescription());
        priceField.setText(String.valueOf(product.getPrice()));
        statusComboBox.setSelectedItem(product.getStatus());
    }
    
    private void save() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceText = priceField.getText().trim();
        
        // Validation
        if (name.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El precio debe ser un número positivo",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (product == null) {
            product = new Product();
        }
        
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStatus((Status) statusComboBox.getSelectedItem());
        
        confirmed = true;
        dispose();
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Product getProduct() {
        return product;
    }
}
