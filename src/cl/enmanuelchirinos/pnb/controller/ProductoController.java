package cl.enmanuelchirinos.pnb.controller;

import cl.enmanuelchirinos.pnb.model.Producto;
import cl.enmanuelchirinos.pnb.service.ProductoService;
import java.util.List;

/**
 * Controlador para coordinar operaciones de Producto
 */
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * Crea un nuevo producto
     */
    public void crearProducto(String nombre, String categoria, String tipo, double precio) {
        service.crear(nombre, categoria, tipo, precio);
    }

    /**
     * Actualiza un producto existente
     */
    public void actualizarProducto(int id, String nombre, String categoria,
                                   String tipo, double precio, boolean activo) {
        service.actualizar(id, nombre, categoria, tipo, precio, activo);
    }

    /**
     * Elimina un producto
     */
    public void eliminarProducto(int id) {
        service.eliminar(id);
    }

    /**
     * Cambia el estado de un producto
     */
    public void cambiarEstadoProducto(int id) {
        service.cambiarEstado(id);
    }

    /**
     * Lista todos los productos
     */
    public List<Producto> listarTodos() {
        return service.listarTodos();
    }

    /**
     * Lista productos activos
     */
    public List<Producto> listarActivos() {
        return service.listarActivos();
    }

    /**
     * Lista productos por categoría
     */
    public List<Producto> listarPorCategoria(String categoria) {
        return service.listarPorCategoria(categoria);
    }

    /**
     * Busca productos por nombre
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return service.buscarPorNombre(nombre);
    }
}

