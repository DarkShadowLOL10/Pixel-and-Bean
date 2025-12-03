package cl.enmanuelchirinos.pnb.controller;

import cl.enmanuelchirinos.pnb.model.Venta;
import cl.enmanuelchirinos.pnb.service.VentaService;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para coordinar operaciones de Venta
 */
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    /**
     * Registra una nueva venta
     */
    public int registrarVenta(int usuarioId, String usuarioNombre, double total) {
        return service.registrarVenta(usuarioId, usuarioNombre, total);
    }

    /**
     * Anula una venta
     */
    public void anularVenta(int id) {
        service.anularVenta(id);
    }

    /**
     * Lista todas las ventas
     */
    public List<Venta> listarTodas() {
        return service.listarTodas();
    }

    /**
     * Lista ventas del día
     */
    public List<Venta> listarDelDia() {
        return service.listarDelDia();
    }

    /**
     * Lista ventas por rango de fechas
     */
    public List<Venta> listarPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return service.listarPorRango(desde, hasta);
    }

    /**
     * Lista ventas por usuario
     */
    public List<Venta> listarPorUsuario(int usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    /**
     * Calcula el total de ventas del día
     */
    public double calcularTotalDelDia() {
        return service.calcularTotalDelDia();
    }

    /**
     * Calcula el total de ventas por rango
     */
    public double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return service.calcularTotalPorRango(desde, hasta);
    }
}

