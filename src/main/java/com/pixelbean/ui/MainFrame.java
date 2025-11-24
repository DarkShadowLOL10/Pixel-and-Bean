package com.pixelbean.ui;

import com.pixelbean.models.Role;
import com.pixelbean.models.User;
import com.pixelbean.services.UserService;
import javax.swing.*;
import java.awt.*;

/**
 * Main application frame with navigation
 */
public class MainFrame extends JFrame {
    private final User currentUser;
    private final UserService userService;
    private JPanel contentPanel;
    
    public MainFrame(User user) {
        this.currentUser = user;
        this.userService = UserService.getInstance();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Pixel & Bean - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // Users menu (only for ADMIN)
        if (currentUser.getRole() == Role.ADMIN) {
            JMenu usersMenu = new JMenu("Usuarios");
            JMenuItem manageUsersItem = new JMenuItem("Gestionar Usuarios");
            manageUsersItem.addActionListener(e -> showUserManagement());
            usersMenu.add(manageUsersItem);
            menuBar.add(usersMenu);
        }
        
        // Products menu (only for ADMIN)
        if (currentUser.getRole() == Role.ADMIN) {
            JMenu productsMenu = new JMenu("Productos");
            JMenuItem manageProductsItem = new JMenuItem("Gestionar Productos");
            manageProductsItem.addActionListener(e -> showProductManagement());
            productsMenu.add(manageProductsItem);
            menuBar.add(productsMenu);
        }
        
        // Sales menu
        JMenu salesMenu = new JMenu("Ventas");
        JMenuItem newSaleItem = new JMenuItem("Nueva Venta");
        newSaleItem.addActionListener(e -> showNewSale());
        salesMenu.add(newSaleItem);
        menuBar.add(salesMenu);
        
        // Reports menu
        JMenu reportsMenu = new JMenu("Reportes");
        JMenuItem salesReportItem = new JMenuItem("Reporte de Ventas");
        salesReportItem.addActionListener(e -> showSalesReport());
        reportsMenu.add(salesReportItem);
        menuBar.add(reportsMenu);
        
        // System menu
        JMenu systemMenu = new JMenu("Sistema");
        JMenuItem logoutItem = new JMenuItem("Cerrar Sesión");
        logoutItem.addActionListener(e -> logout());
        systemMenu.add(logoutItem);
        menuBar.add(systemMenu);
        
        setJMenuBar(menuBar);
        
        // Main layout
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setBackground(new Color(70, 130, 180));
        
        JLabel titleLabel = new JLabel("Pixel & Bean - Gestión Café-Arcade");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel userLabel = new JLabel("Usuario: " + currentUser.getFullName() + " (" + currentUser.getRole().getDisplayName() + ")");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userLabel, BorderLayout.EAST);
        
        // Content panel
        contentPanel = new JPanel(new BorderLayout());
        showWelcomePanel();
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void showWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel welcomeLabel = new JLabel("Bienvenido a Pixel & Bean");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel, gbc);
        
        gbc.gridy = 1;
        JLabel infoLabel = new JLabel("Sistema de Gestión para Café-Arcade");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomePanel.add(infoLabel, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        JLabel instructionLabel = new JLabel("Seleccione una opción del menú para comenzar");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        welcomePanel.add(instructionLabel, gbc);
        
        setContentPanel(welcomePanel);
    }
    
    private void showUserManagement() {
        setContentPanel(new UserManagementPanel());
    }
    
    private void showProductManagement() {
        setContentPanel(new ProductManagementPanel());
    }
    
    private void showNewSale() {
        setContentPanel(new SalePanel(currentUser));
    }
    
    private void showSalesReport() {
        setContentPanel(new ReportPanel());
    }
    
    private void setContentPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            userService.logout();
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
                dispose();
            });
        }
    }
}
