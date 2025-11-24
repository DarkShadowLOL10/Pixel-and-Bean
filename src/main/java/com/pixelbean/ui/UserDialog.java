package com.pixelbean.ui;

import com.pixelbean.models.Role;
import com.pixelbean.models.Status;
import com.pixelbean.models.User;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for creating/editing users
 */
public class UserDialog extends JDialog {
    private User user;
    private boolean confirmed = false;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JComboBox<Role> roleComboBox;
    private JComboBox<Status> statusComboBox;
    private JButton saveButton, cancelButton;
    
    public UserDialog(Frame parent, User user) {
        super(parent, user == null ? "Nuevo Usuario" : "Editar Usuario", true);
        this.user = user;
        initComponents();
    }
    
    private void initComponents() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
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
        
        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Nombre Completo:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        fullNameField = new JTextField(20);
        formPanel.add(fullNameField, gbc);
        
        // Role
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Rol:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        roleComboBox = new JComboBox<>(Role.values());
        roleComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Role) {
                    setText(((Role) value).getDisplayName());
                }
                return this;
            }
        });
        formPanel.add(roleComboBox, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 4;
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
        
        // Load user data if editing
        if (user != null) {
            loadUserData();
        }
        
        getRootPane().setDefaultButton(saveButton);
    }
    
    private void loadUserData() {
        usernameField.setText(user.getUsername());
        // Note: In a real system, password should not be displayed
        // For this prototype, we show it for ease of testing
        passwordField.setText(user.getPassword());
        fullNameField.setText(user.getFullName());
        roleComboBox.setSelectedItem(user.getRole());
        statusComboBox.setSelectedItem(user.getStatus());
    }
    
    private void save() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (user == null) {
            user = new User();
        }
        
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setRole((Role) roleComboBox.getSelectedItem());
        user.setStatus((Status) statusComboBox.getSelectedItem());
        
        confirmed = true;
        dispose();
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public User getUser() {
        return user;
    }
}
