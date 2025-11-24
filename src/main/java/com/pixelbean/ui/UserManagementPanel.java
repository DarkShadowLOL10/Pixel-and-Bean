package com.pixelbean.ui;

import com.pixelbean.models.Role;
import com.pixelbean.models.Status;
import com.pixelbean.models.User;
import com.pixelbean.services.UserService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for user management
 */
public class UserManagementPanel extends JPanel {
    private final UserService userService;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, activateButton, deactivateButton, refreshButton;
    
    public UserManagementPanel() {
        this.userService = UserService.getInstance();
        initComponents();
        loadUsers();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Gestión de Usuarios");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        
        // Table
        String[] columns = {"ID", "Usuario", "Nombre Completo", "Rol", "Estado"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(userTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        addButton = new JButton("Nuevo Usuario");
        addButton.addActionListener(e -> addUser());
        
        editButton = new JButton("Editar");
        editButton.addActionListener(e -> editUser());
        editButton.setEnabled(false);
        
        activateButton = new JButton("Activar");
        activateButton.addActionListener(e -> activateUser());
        activateButton.setEnabled(false);
        
        deactivateButton = new JButton("Desactivar");
        deactivateButton.addActionListener(e -> deactivateUser());
        deactivateButton.setEnabled(false);
        
        refreshButton = new JButton("Actualizar");
        refreshButton.addActionListener(e -> loadUsers());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(activateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(refreshButton);
        
        // Table selection listener
        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
        
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = userService.findAll();
        for (User user : users) {
            Object[] row = {
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole().getDisplayName(),
                user.getStatus().getDisplayName()
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateButtonStates() {
        int selectedRow = userTable.getSelectedRow();
        boolean selected = selectedRow >= 0;
        
        editButton.setEnabled(selected);
        
        if (selected) {
            Long userId = (Long) tableModel.getValueAt(selectedRow, 0);
            User user = userService.findById(userId);
            activateButton.setEnabled(user.getStatus() == Status.INACTIVE);
            deactivateButton.setEnabled(user.getStatus() == Status.ACTIVE);
        } else {
            activateButton.setEnabled(false);
            deactivateButton.setEnabled(false);
        }
    }
    
    private void addUser() {
        UserDialog dialog = new UserDialog((Frame) SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            User user = dialog.getUser();
            
            // Validate username uniqueness
            if (userService.usernameExists(user.getUsername(), null)) {
                JOptionPane.showMessageDialog(this,
                        "El nombre de usuario ya existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            userService.save(user);
            loadUsers();
            JOptionPane.showMessageDialog(this,
                    "Usuario creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long userId = (Long) tableModel.getValueAt(selectedRow, 0);
            User user = userService.findById(userId);
            
            UserDialog dialog = new UserDialog((Frame) SwingUtilities.getWindowAncestor(this), user);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                User updatedUser = dialog.getUser();
                
                // Validate username uniqueness
                if (userService.usernameExists(updatedUser.getUsername(), updatedUser.getId())) {
                    JOptionPane.showMessageDialog(this,
                            "El nombre de usuario ya existe",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                userService.save(updatedUser);
                loadUsers();
                JOptionPane.showMessageDialog(this,
                        "Usuario actualizado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void activateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long userId = (Long) tableModel.getValueAt(selectedRow, 0);
            userService.activate(userId);
            loadUsers();
            JOptionPane.showMessageDialog(this,
                    "Usuario activado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void deactivateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long userId = (Long) tableModel.getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea desactivar este usuario?",
                    "Confirmar desactivación",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                userService.deactivate(userId);
                loadUsers();
                JOptionPane.showMessageDialog(this,
                        "Usuario desactivado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
