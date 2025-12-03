package cl.enmanuelchirinos.pnb.app;

import cl.enmanuelchirinos.pnb.controller.ProductoController;
import cl.enmanuelchirinos.pnb.controller.UsuarioController;
import cl.enmanuelchirinos.pnb.controller.VentaController;
import cl.enmanuelchirinos.pnb.repository.IProductoRepository;
import cl.enmanuelchirinos.pnb.repository.IUsuarioRepository;
import cl.enmanuelchirinos.pnb.repository.IVentaRepository;
import cl.enmanuelchirinos.pnb.repository.mock.ProductoRepositoryMock;
import cl.enmanuelchirinos.pnb.repository.mock.UsuarioRepositoryMock;
import cl.enmanuelchirinos.pnb.repository.mock.VentaRepositoryMock;
import cl.enmanuelchirinos.pnb.service.ProductoService;
import cl.enmanuelchirinos.pnb.service.UsuarioService;
import cl.enmanuelchirinos.pnb.service.VentaService;

/**
 * Contenedor de Inversión de Control (IoC Container)
 * Gestiona la creación e inyección de dependencias de toda la aplicación
 *
 * Patrón Singleton: Solo existe una instancia en toda la aplicación
 */
public class ApplicationContext {

    // Instancia única (Singleton)
    private static ApplicationContext instance;

    // ===== CAPA DE REPOSITORIOS =====
    private IUsuarioRepository usuarioRepository;
    private IProductoRepository productoRepository;
    private IVentaRepository ventaRepository;

    // ===== CAPA DE SERVICIOS =====
    private UsuarioService usuarioService;
    private ProductoService productoService;
    private VentaService ventaService;

    // ===== CAPA DE CONTROLADORES =====
    private UsuarioController usuarioController;
    private ProductoController productoController;
    private VentaController ventaController;

    /**
     * Constructor privado (Singleton)
     * Inicializa todas las capas de la aplicación
     */
    private ApplicationContext() {
        inicializarRepositorios();
        inicializarServicios();
        inicializarControladores();
    }

    /**
     * Inicializa la capa de repositorios
     * Por ahora usamos implementaciones Mock
     * En Clase 4 cambiaremos a implementaciones JDBC
     */
    private void inicializarRepositorios() {
        System.out.println("[ApplicationContext] Inicializando repositorios Mock...");

        // Implementaciones Mock (en memoria)
        usuarioRepository = new UsuarioRepositoryMock();
        productoRepository = new ProductoRepositoryMock();
        ventaRepository = new VentaRepositoryMock();

        System.out.println("[ApplicationContext] ✓ Repositorios inicializados");
    }

    /**
     * Inicializa la capa de servicios
     * Inyecta los repositorios en los servicios
     */
    private void inicializarServicios() {
        System.out.println("[ApplicationContext] Inicializando servicios...");

        // Inyección de dependencias por constructor
        usuarioService = new UsuarioService(usuarioRepository);
        productoService = new ProductoService(productoRepository);
        ventaService = new VentaService(ventaRepository);

        System.out.println("[ApplicationContext] ✓ Servicios inicializados");
    }

    /**
     * Inicializa la capa de controladores
     * Inyecta los servicios en los controladores
     */
    private void inicializarControladores() {
        System.out.println("[ApplicationContext] Inicializando controladores...");

        // Inyección de dependencias por constructor
        usuarioController = new UsuarioController(usuarioService);
        productoController = new ProductoController(productoService);
        ventaController = new VentaController(ventaService);

        System.out.println("[ApplicationContext] ✓ Controladores inicializados");
    }

    /**
     * Obtiene la instancia única del ApplicationContext (Singleton)
     */
    public static ApplicationContext getInstance() {
        if (instance == null) {
            System.out.println("[ApplicationContext] Creando instancia única...");
            instance = new ApplicationContext();
            System.out.println("[ApplicationContext] ✓ Aplicación inicializada completamente\n");
        }
        return instance;
    }

    // ===== GETTERS PARA CONTROLADORES =====
    // Las vistas solo deben usar controladores, no servicios ni repositorios

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public ProductoController getProductoController() {
        return productoController;
    }

    public VentaController getVentaController() {
        return ventaController;
    }

    // ===== MÉTODOS PARA TESTING (opcional) =====

    /**
     * Reinicia el contexto (útil para tests)
     */
    public static void reset() {
        instance = null;
    }
}

