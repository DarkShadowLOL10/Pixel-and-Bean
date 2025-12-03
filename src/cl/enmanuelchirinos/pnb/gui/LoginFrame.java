package cl.enmanuelchirinos.pnb.gui;

import cl.enmanuelchirinos.pnb.app.ApplicationContext;
import cl.enmanuelchirinos.pnb.controller.UsuarioController;
import cl.enmanuelchirinos.pnb.model.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Frame de autenticación de usuarios
 * Ahora usa arquitectura MVC con ApplicationContext
 */
public class LoginFrame extends JFrame {

    // Componentes de la interfaz
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JLabel lblTitle;
    private JLabel lblUser;
    private JLabel lblPass;

    // Controlador (inyección de dependencias)
    private UsuarioController usuarioController;

    public LoginFrame() {
        // Obtener controlador del ApplicationContext
        usuarioController = ApplicationContext.getInstance().getUsuarioController();

        initComponents();
        setupFrame();
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        lblTitle = new JLabel("Pixel & Bean");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(51, 51, 51));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        // Subtítulo
        JLabel lblSubtitle = new JLabel("Sistema de Gestión");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(102, 102, 102));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 20, 10);
        mainPanel.add(lblSubtitle, gbc);

        // Label Usuario
        lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(lblUser, gbc);

        // Campo Usuario
        txtUser = new JTextField(20);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        mainPanel.add(txtUser, gbc);

        // Label Contraseña
        lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(lblPass, gbc);

        // Campo Contraseña
        txtPass = new JPasswordField(20);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        mainPanel.add(txtPass, gbc);

        // Botón Login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(66, 133, 244));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(btnLogin, gbc);

        // Información de prueba
        JLabel lblInfo = new JLabel("<html><center>Credenciales de prueba:<br/>admin / admin123<br/>operador1 / op123</center></html>");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(150, 150, 150));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(lblInfo, gbc);

        add(mainPanel);

        // Permitir login con Enter
        txtPass.addActionListener(e -> btnLogin.doClick());
    }

    private void setupFrame() {
        setTitle("Iniciar Sesión – Pixel & Bean");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String user = txtUser.getText().trim();
        char[] passChars = txtPass.getPassword();
        String pass = new String(passChars);

        // Validación de campos vacíos
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingrese usuario y contraseña",
                "Campos requeridos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Autenticar usando el controlador (arquitectura MVC)
            Usuario usuario = usuarioController.autenticar(user, pass);

            // Login exitoso
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido " + usuario.getNombreCompleto() + "!",
                "Login exitoso",
                JOptionPane.INFORMATION_MESSAGE);

            // Abrir ventana principal
            MainFrame main = new MainFrame(usuario.getUsername(), usuario.getRol());
            main.setVisible(true);

            // Cerrar login y liberar recursos
            this.dispose();

        } catch (Exception e) {
            // Login fallido - mostrar mensaje de error
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);

            // Limpiar campos
            txtPass.setText("");
            txtUser.requestFocus();
        }

        // Limpiar contraseña de memoria (seguridad)
        java.util.Arrays.fill(passChars, '0');
    }
}

