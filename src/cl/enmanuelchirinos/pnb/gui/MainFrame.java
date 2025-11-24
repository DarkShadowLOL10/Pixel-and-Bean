package cl.enmanuelchirinos.pnb.gui;

import cl.enmanuelchirinos.pnb.gui.panels.EventosPanel;
import cl.enmanuelchirinos.pnb.gui.panels.ProductosPanel;
import cl.enmanuelchirinos.pnb.gui.panels.ReportesPanel;
import cl.enmanuelchirinos.pnb.gui.panels.UsuariosPanel;
import cl.enmanuelchirinos.pnb.gui.panels.VentasPanel;
import cl.enmanuelchirinos.pnb.service.ProductoService;
import cl.enmanuelchirinos.pnb.service.UsuarioService;
import cl.enmanuelchirinos.pnb.service.VentaService;
import cl.enmanuelchirinos.pnb.service.impl.ProductoServiceStub;
import cl.enmanuelchirinos.pnb.service.impl.UsuarioServiceStub;
import cl.enmanuelchirinos.pnb.service.impl.VentaServiceStub;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Frame principal de la aplicación con menú completo
 * Incluye barra de estado con información del usuario
 */
public class MainFrame extends JFrame {

    private String currentUser;
    private String currentRole;

    // Componentes del menú
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuGestion, menuOperacion, menuReportes, menuEventos, menuAyuda;

    // Barra de estado
    private JLabel lblStatus;
    private JPanel statusPanel;

    // Panel central
    private JPanel mainPanel;
    private JPanel contentPanel; // Panel con CardLayout
    private CardLayout cardLayout;

    // Servicios stub
    private ProductoService productoService;
    private UsuarioService usuarioService;
    private VentaService ventaService;

    // Paneles (lazy init)
    private UsuariosPanel usuariosPanel;
    private ProductosPanel productosPanel;
    private VentasPanel ventasPanel;
    private ReportesPanel reportesPanel;
    private EventosPanel eventosPanel;

    // Claves de las vistas
    private static final String CARD_WELCOME = "WELCOME";
    private static final String CARD_USUARIOS = "USUARIOS";
    private static final String CARD_PRODUCTOS = "PRODUCTOS";
    private static final String CARD_VENTAS = "VENTAS";
    private static final String CARD_REPORTES = "REPORTES";
    private static final String CARD_EVENTOS = "EVENTOS";

    public MainFrame(String username, String role) {
        this.currentUser = username;
        this.currentRole = role;

        // Inicializar servicios stub
        this.productoService = new ProductoServiceStub();
        this.usuarioService = new UsuarioServiceStub();
        this.ventaService = new VentaServiceStub();

        initComponents();
        setupFrame();
        updateStatusBar();

        // Actualizar hora cada segundo
        startStatusBarTimer();
    }

    private void initComponents() {
        // Configurar CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Panel de bienvenida (reutilizamos el anterior createWelcomePanel)
        JPanel welcomePanel = createWelcomePanel();
        contentPanel.add(welcomePanel, CARD_WELCOME);

        // Crear menú y barra de estado
        createMenuBar();
        createStatusBar();

        setJMenuBar(menuBar);
        add(contentPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título de bienvenida
        JLabel lblWelcome = new JLabel("Bienvenido a Pixel & Bean");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblWelcome.setForeground(new Color(51, 51, 51));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblWelcome, gbc);

        // Subtítulo
        JLabel lblSubtitle = new JLabel("Sistema de Gestión para Café-Arcade");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitle.setForeground(new Color(102, 102, 102));
        gbc.gridy = 1;
        panel.add(lblSubtitle, gbc);

        // Información del usuario
        JLabel lblUserInfo = new JLabel(String.format("Usuario: %s | Rol: %s", currentUser, currentRole));
        lblUserInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUserInfo.setForeground(new Color(66, 133, 244));
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        panel.add(lblUserInfo, gbc);

        // Instrucciones
        JTextArea txtInstructions = new JTextArea(
            "Utilice el menú superior para navegar por las diferentes funcionalidades:\n\n" +
            "• Archivo: Cerrar sesión o salir del sistema\n" +
            "• Gestión: Administrar usuarios y productos\n" +
            "• Operación: Registrar ventas\n" +
            "• Reportes: Ver estadísticas y reportes\n" +
            "• Eventos: Gestión de torneos (próximamente)\n" +
            "• Ayuda: Información sobre la aplicación"
        );
        txtInstructions.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtInstructions.setEditable(false);
        txtInstructions.setBackground(new Color(245, 245, 245));
        txtInstructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(txtInstructions, gbc);

        return panel;
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Menú Archivo
        menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic('A');

        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar sesión");
        itemCerrarSesion.setAccelerator(KeyStroke.getKeyStroke("ctrl shift L"));
        itemCerrarSesion.addActionListener(e -> cerrarSesion());

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        itemSalir.addActionListener(e -> salirAplicacion());

        menuArchivo.add(itemCerrarSesion);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        // Menú Gestión
        menuGestion = new JMenu("Gestión");
        menuGestion.setMnemonic('G');

        JMenuItem itemUsuarios = new JMenuItem("Usuarios*");
        itemUsuarios.setEnabled(currentRole.equals("ADMIN"));
        itemUsuarios.addActionListener(e -> abrirModulo("Usuarios"));

        JMenuItem itemProductos = new JMenuItem("Productos");
        itemProductos.addActionListener(e -> abrirModulo("Productos"));

        menuGestion.add(itemUsuarios);
        menuGestion.add(itemProductos);

        // Menú Operación
        menuOperacion = new JMenu("Operación");
        menuOperacion.setMnemonic('O');

        JMenuItem itemVentas = new JMenuItem("Ventas");
        itemVentas.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        itemVentas.addActionListener(e -> abrirModulo("Ventas"));

        menuOperacion.add(itemVentas);

        // Menú Reportes
        menuReportes = new JMenu("Reportes");
        menuReportes.setMnemonic('R');

        JMenuItem itemVentasDia = new JMenuItem("Ventas del día");
        itemVentasDia.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
        itemVentasDia.addActionListener(e -> abrirModulo("Ventas del día"));

        JMenuItem itemTopProductos = new JMenuItem("Top productos");
        itemTopProductos.addActionListener(e -> abrirModulo("Top productos"));

        menuReportes.add(itemVentasDia);
        menuReportes.add(itemTopProductos);

        // Menú Eventos
        menuEventos = new JMenu("Eventos");
        menuEventos.setMnemonic('E');

        JMenuItem itemTorneos = new JMenuItem("Torneos");
        itemTorneos.addActionListener(e -> mostrarTorneos());

        menuEventos.add(itemTorneos);

        // Menú Ayuda
        menuAyuda = new JMenu("Ayuda");
        menuAyuda.setMnemonic('Y');

        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());

        menuAyuda.add(itemAcercaDe);

        // Agregar menús a la barra
        menuBar.add(menuArchivo);
        menuBar.add(menuGestion);
        menuBar.add(menuOperacion);
        menuBar.add(menuReportes);
        menuBar.add(menuEventos);
        menuBar.add(menuAyuda);
    }

    private void createStatusBar() {
        statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.setPreferredSize(new Dimension(getWidth(), 25));

        lblStatus = new JLabel();
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        statusPanel.add(lblStatus, BorderLayout.WEST);
    }

    private void updateStatusBar() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateTime = sdf.format(new Date());
        lblStatus.setText(String.format("Usuario: %s | Rol: %s | %s",
            currentUser, currentRole, dateTime));
    }

    private void startStatusBarTimer() {
        Timer timer = new Timer(1000, e -> updateStatusBar());
        timer.start();
    }

    private void setupFrame() {
        setTitle("Pixel & Bean – Sistema de Gestión");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ícono de la aplicación (opcional)
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/resources/icons/logo.png")));
        } catch (Exception e) {
            // Si no existe el ícono, continuar sin él
        }
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }

    private void salirAplicacion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea salir de la aplicación?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void abrirModulo(String modulo) {
        // Reemplazado por navegación directa con CardLayout
        switch (modulo) {
            case "Usuarios":
                if (!currentRole.equals("ADMIN")) {
                    JOptionPane.showMessageDialog(this,
                        "Acceso restringido: solo ADMIN puede gestionar usuarios",
                        "Permisos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ensureUsuariosPanel();
                showCard(CARD_USUARIOS);
                break;
            case "Productos":
                ensureProductosPanel();
                showCard(CARD_PRODUCTOS);
                break;
            case "Ventas":
                ensureVentasPanel();
                showCard(CARD_VENTAS);
                break;
            case "Ventas del día":
            case "Top productos":
                ensureReportesPanel();
                showCard(CARD_REPORTES);
                break;
            case "Torneos":
                ensureEventosPanel();
                showCard(CARD_EVENTOS);
                break;
            default:
                showCard(CARD_WELCOME);
        }
    }

    private void showCard(String card) {
        cardLayout.show(contentPanel, card);
    }

    private void ensureUsuariosPanel() {
        if (usuariosPanel == null) {
            usuariosPanel = new UsuariosPanel(usuarioService);
            contentPanel.add(usuariosPanel, CARD_USUARIOS);
        }
        usuariosPanel.refresh();
    }

    private void ensureProductosPanel() {
        if (productosPanel == null) {
            productosPanel = new ProductosPanel(productoService);
            contentPanel.add(productosPanel, CARD_PRODUCTOS);
        }
        productosPanel.refresh();
    }

    private void ensureVentasPanel() {
        if (ventasPanel == null) {
            ventasPanel = new VentasPanel(ventaService, productoService, currentUser);
            contentPanel.add(ventasPanel, CARD_VENTAS);
        }
        ventasPanel.refresh();
    }

    private void ensureReportesPanel() {
        if (reportesPanel == null) {
            reportesPanel = new ReportesPanel(ventaService, productoService);
            contentPanel.add(reportesPanel, CARD_REPORTES);
        }
        reportesPanel.refresh();
    }

    private void ensureEventosPanel() {
        if (eventosPanel == null) {
            eventosPanel = new EventosPanel();
            contentPanel.add(eventosPanel, CARD_EVENTOS);
        }
        eventosPanel.refresh();
    }

    private void mostrarTorneos() {
        JOptionPane.showMessageDialog(this,
            "El módulo de Torneos es un placeholder.\n" +
            "Esta funcionalidad se implementará como trabajo autónomo.",
            "Eventos - Torneos",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "Pixel & Bean - Sistema de Gestión v1.0\n\n" +
            "Desarrollado por: Enmanuel Chirinos\n" +
            "Proyecto: Café-Arcade Management System\n\n" +
            "Tecnologías:\n" +
            "• Java 17\n" +
            "• Swing GUI\n" +
            "• MySQL + JDBC\n" +
            "• Patrón MVC\n\n" +
            "© 2024 - Todos los derechos reservados",
            "Acerca de Pixel & Bean",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
