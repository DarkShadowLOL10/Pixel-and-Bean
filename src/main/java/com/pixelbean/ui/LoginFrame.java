package com.pixelbean.ui;

import com.pixelbean.models.User;
import com.pixelbean.services.UserService;
import javax.swing.*;
import java.awt.*;

/**
 * Login window for user authentication
 */
public class LoginFrame extends JFrame {
    private final UserService userService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public LoginFrame() {
        this.userService = UserService.getInstance();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Pixel & Bean - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Pixel & Bean");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestión Café-Arcade");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setPreferredSize(new Dimension(150, 30));
        loginButton.addActionListener(e -> login());
        buttonPanel.add(loginButton);
        
        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Usuarios de prueba"));
        infoPanel.add(new JLabel("Admin: admin / admin123"));
        infoPanel.add(new JLabel("Operador: operador / oper123"));
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Enter key support
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese usuario y contraseña",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = userService.authenticate(username, password);
        
        if (user != null) {
            // Open main window
            SwingUtilities.invokeLater(() -> {
                new MainFrame(user).setVisible(true);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
