package com.pixelbean.ui;

import com.pixelbean.models.Sale;
import com.pixelbean.models.SaleItem;
import com.pixelbean.services.SaleService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel for sales reports with date range filters
 */
public class ReportPanel extends JPanel {
    private final SaleService saleService;
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> periodComboBox;
    private JLabel totalSalesLabel;
    private JLabel totalAmountLabel;
    
    public ReportPanel() {
        this.saleService = SaleService.getInstance();
        initComponents();
        loadReport("Hoy");
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Reporte de Ventas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        filterPanel.add(new JLabel("Período:"));
        periodComboBox = new JComboBox<>(new String[]{"Hoy", "Ayer", "Esta Semana", "Este Mes"});
        periodComboBox.addActionListener(e -> {
            String selectedPeriod = (String) periodComboBox.getSelectedItem();
            loadReport(selectedPeriod);
        });
        filterPanel.add(periodComboBox);
        
        JButton refreshButton = new JButton("Actualizar");
        refreshButton.addActionListener(e -> {
            String selectedPeriod = (String) periodComboBox.getSelectedItem();
            loadReport(selectedPeriod);
        });
        filterPanel.add(refreshButton);
        
        // Table
        String[] columns = {"Folio", "Fecha y Hora", "Usuario", "Items", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salesTable = new JTable(tableModel);
        salesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        salesTable.getTableHeader().setReorderingAllowed(false);
        
        // Add double-click listener to show sale details
        salesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showSaleDetails();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(salesTable);
        
        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Resumen"));
        
        summaryPanel.add(new JLabel("Total de Ventas:"));
        totalSalesLabel = new JLabel("0");
        totalSalesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(totalSalesLabel);
        
        summaryPanel.add(new JLabel("Monto Total:"));
        totalAmountLabel = new JLabel("$0.00");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalAmountLabel.setForeground(new Color(0, 128, 0));
        summaryPanel.add(totalAmountLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton detailsButton = new JButton("Ver Detalles");
        detailsButton.addActionListener(e -> showSaleDetails());
        buttonPanel.add(detailsButton);
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(summaryPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void loadReport(String period) {
        List<Sale> sales;
        
        switch (period) {
            case "Hoy":
                sales = saleService.findToday();
                break;
            case "Ayer":
                sales = saleService.findYesterday();
                break;
            case "Esta Semana":
                sales = saleService.findThisWeek();
                break;
            case "Este Mes":
                sales = saleService.findThisMonth();
                break;
            default:
                sales = saleService.findToday();
        }
        
        displaySales(sales);
    }
    
    private void displaySales(List<Sale> sales) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        for (Sale sale : sales) {
            Object[] row = {
                sale.getId(),
                sale.getDate().format(formatter),
                sale.getUser().getFullName(),
                sale.getItems().size(),
                String.format("$%.2f", sale.getTotal())
            };
            tableModel.addRow(row);
        }
        
        // Update summary
        totalSalesLabel.setText(String.valueOf(sales.size()));
        double totalAmount = saleService.calculateTotal(sales);
        totalAmountLabel.setText(String.format("$%.2f", totalAmount));
    }
    
    private void showSaleDetails() {
        int selectedRow = salesTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione una venta",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long saleId = (Long) tableModel.getValueAt(selectedRow, 0);
        Sale sale = saleService.findById(saleId);
        
        if (sale != null) {
            showSaleDetailsDialog(sale);
        }
    }
    
    private void showSaleDetailsDialog(Sale sale) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detalles de Venta", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header info
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        JPanel headerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        headerPanel.setBorder(BorderFactory.createTitledBorder("Información de la Venta"));
        
        headerPanel.add(new JLabel("Folio:"));
        headerPanel.add(new JLabel(sale.getId().toString()));
        
        headerPanel.add(new JLabel("Fecha y Hora:"));
        headerPanel.add(new JLabel(sale.getDate().format(formatter)));
        
        headerPanel.add(new JLabel("Usuario:"));
        headerPanel.add(new JLabel(sale.getUser().getFullName()));
        
        headerPanel.add(new JLabel("Total:"));
        JLabel totalLabel = new JLabel(String.format("$%.2f", sale.getTotal()));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(new Color(0, 128, 0));
        headerPanel.add(totalLabel);
        
        // Items table
        String[] columns = {"Producto", "Precio Unitario", "Cantidad", "Subtotal"};
        DefaultTableModel detailsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (SaleItem item : sale.getItems()) {
            Object[] row = {
                item.getProduct().getName(),
                String.format("$%.2f", item.getUnitPrice()),
                item.getQuantity(),
                String.format("$%.2f", item.getSubtotal())
            };
            detailsTableModel.addRow(row);
        }
        
        JTable detailsTable = new JTable(detailsTableModel);
        detailsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Items"));
        
        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
