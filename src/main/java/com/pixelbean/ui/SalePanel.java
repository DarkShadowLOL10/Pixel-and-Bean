package com.pixelbean.ui;

import com.pixelbean.models.Product;
import com.pixelbean.models.Sale;
import com.pixelbean.models.SaleItem;
import com.pixelbean.models.User;
import com.pixelbean.services.ProductService;
import com.pixelbean.services.SaleService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel for registering sales
 */
public class SalePanel extends JPanel {
    private final User currentUser;
    private final ProductService productService;
    private final SaleService saleService;
    private Sale currentSale;
    
    private JComboBox<Product> productComboBox;
    private JSpinner quantitySpinner;
    private JButton addItemButton;
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JButton completeSaleButton, clearButton;
    
    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 100;
    
    public SalePanel(User currentUser) {
        this.currentUser = currentUser;
        this.productService = ProductService.getInstance();
        this.saleService = SaleService.getInstance();
        this.currentSale = new Sale();
        this.currentSale.setUser(currentUser);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Registro de Venta");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        
        // Product selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));
        
        selectionPanel.add(new JLabel("Producto:"));
        productComboBox = new JComboBox<>();
        productComboBox.setPreferredSize(new Dimension(250, 25));
        productComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Product) {
                    Product p = (Product) value;
                    setText(p.getName() + " - $" + String.format("%.2f", p.getPrice()));
                }
                return this;
            }
        });
        loadProducts();
        selectionPanel.add(productComboBox);
        
        selectionPanel.add(new JLabel("Cantidad:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(MIN_QUANTITY, MIN_QUANTITY, MAX_QUANTITY, 1));
        quantitySpinner.setPreferredSize(new Dimension(60, 25));
        selectionPanel.add(quantitySpinner);
        
        addItemButton = new JButton("Agregar");
        addItemButton.addActionListener(e -> addItem());
        selectionPanel.add(addItemButton);
        
        // Items table
        String[] columns = {"Producto", "Precio Unitario", "Cantidad", "Subtotal"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        itemsTable = new JTable(tableModel);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        
        // Items panel with table and remove button
        JPanel itemsPanel = new JPanel(new BorderLayout(5, 5));
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Items de la Venta"));
        itemsPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel itemButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton removeItemButton = new JButton("Quitar Item");
        removeItemButton.addActionListener(e -> removeItem());
        itemButtonPanel.add(removeItemButton);
        itemsPanel.add(itemButtonPanel, BorderLayout.SOUTH);
        
        // Total panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        totalPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel totalTextLabel = new JLabel("TOTAL:");
        totalTextLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalTextLabel);
        
        totalLabel = new JLabel("$0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setForeground(new Color(0, 128, 0));
        totalPanel.add(totalLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        clearButton = new JButton("Limpiar");
        clearButton.addActionListener(e -> clearSale());
        
        completeSaleButton = new JButton("Completar Venta");
        completeSaleButton.addActionListener(e -> completeSale());
        completeSaleButton.setPreferredSize(new Dimension(150, 30));
        completeSaleButton.setBackground(new Color(34, 139, 34));
        completeSaleButton.setForeground(Color.WHITE);
        
        buttonPanel.add(clearButton);
        buttonPanel.add(completeSaleButton);
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(selectionPanel, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(itemsPanel, BorderLayout.CENTER);
        centerPanel.add(totalPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadProducts() {
        productComboBox.removeAllItems();
        List<Product> products = productService.findActiveProducts();
        for (Product product : products) {
            productComboBox.addItem(product);
        }
    }
    
    private void addItem() {
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un producto",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int quantity = (Integer) quantitySpinner.getValue();
        SaleItem item = new SaleItem(selectedProduct, quantity);
        currentSale.addItem(item);
        
        updateItemsTable();
        updateTotal();
        
        // Reset spinner
        quantitySpinner.setValue(MIN_QUANTITY);
    }
    
    private void removeItem() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow >= 0) {
            currentSale.getItems().remove(selectedRow);
            currentSale.calculateTotal();
            updateItemsTable();
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un item para quitar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updateItemsTable() {
        tableModel.setRowCount(0);
        for (SaleItem item : currentSale.getItems()) {
            Object[] row = {
                item.getProduct().getName(),
                String.format("$%.2f", item.getUnitPrice()),
                item.getQuantity(),
                String.format("$%.2f", item.getSubtotal())
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateTotal() {
        totalLabel.setText(String.format("$%.2f", currentSale.getTotal()));
    }
    
    private void completeSale() {
        if (currentSale.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe agregar al menos un producto a la venta",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirmar venta por $" + String.format("%.2f", currentSale.getTotal()) + "?",
                "Confirmar venta",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            saleService.save(currentSale);
            
            // Show success message with sale details
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String message = String.format(
                    "Venta registrada exitosamente\n\n" +
                    "Folio: %d\n" +
                    "Fecha: %s\n" +
                    "Usuario: %s\n" +
                    "Total: $%.2f",
                    currentSale.getId(),
                    currentSale.getDate().format(formatter),
                    currentUser.getFullName(),
                    currentSale.getTotal()
            );
            
            JOptionPane.showMessageDialog(this,
                    message,
                    "Venta Completada",
                    JOptionPane.INFORMATION_MESSAGE);
            
            clearSale();
        }
    }
    
    private void clearSale() {
        currentSale = new Sale();
        currentSale.setUser(currentUser);
        updateItemsTable();
        updateTotal();
        quantitySpinner.setValue(MIN_QUANTITY);
    }
}
