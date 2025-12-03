package cl.enmanuelchirinos.pnb.controller;

import cl.enmanuelchirinos.pnb.model.Usuario;
import cl.enmanuelchirinos.pnb.service.UsuarioService;
import java.util.List;

/**
 * Controlador para coordinar operaciones de Usuario
 * Actúa como intermediario entre la vista y el servicio
 */
public class UsuarioController {

    private final UsuarioService service;

    /**
     * Constructor con inyección de dependencias
     */
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    /**
     * Autentica un usuario
     */
    public Usuario autenticar(String username, String password) {
        return service.autenticar(username, password);
    }

    /**
     * Crea un nuevo usuario
     */
    public void crearUsuario(String username, String password,
                            String nombreCompleto, String rol) {
        service.crear(username, password, nombreCompleto, rol);
    }

    /**
     * Actualiza un usuario existente
     */
    public void actualizarUsuario(int id, String username, String password,
                                  String nombreCompleto, String rol, boolean activo) {
        service.actualizar(id, username, password, nombreCompleto, rol, activo);
    }

    /**
     * Elimina un usuario
     */
    public void eliminarUsuario(int id) {
        service.eliminar(id);
    }

    /**
     * Lista todos los usuarios
     */
    public List<Usuario> listarTodos() {
        return service.listarTodos();
    }

    /**
     * Lista usuarios activos
     */
    public List<Usuario> listarActivos() {
        return service.listarActivos();
    }

    /**
     * Busca usuarios por texto
     */
    public List<Usuario> buscar(String texto) {
        return service.buscar(texto);
    }
}

