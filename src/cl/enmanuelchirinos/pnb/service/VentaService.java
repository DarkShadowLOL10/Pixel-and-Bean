package cl.enmanuelchirinos.pnb.service;

import cl.enmanuelchirinos.pnb.model.Venta;
import cl.enmanuelchirinos.pnb.repository.IVentaRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de lógica de negocio para Venta
 */
public class VentaService {

    private final IVentaRepository repository;

    public VentaService(IVentaRepository repository) {
        this.repository = repository;
    }

    /**
     * Registra una nueva venta
     */
    public int registrarVenta(int usuarioId, String usuarioNombre, double total) {
        // Validaciones
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("Usuario inválido");
        }
        if (usuarioNombre == null || usuarioNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre de usuario es obligatorio");
        }
        if (total <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }

        // Crear venta
        Venta venta = new Venta();
        venta.setUsuarioId(usuarioId);
        venta.setUsuarioNombre(usuarioNombre);
        venta.setTotal(total);

        return repository.guardar(venta);
    }

    /**
     * Anula una venta
     */
    public void anularVenta(int id) {
        Venta venta = repository.buscarPorId(id);
        if (venta == null) {
            throw new RuntimeException("Venta no encontrada");
        }

        if ("ANULADA".equals(venta.getEstado())) {
            throw new RuntimeException("La venta ya está anulada");
        }

        repository.anular(id);
    }

    /**
     * Lista todas las ventas
     */
    public List<Venta> listarTodas() {
        return repository.listarTodas();
    }

    /**
     * Lista ventas del día
     */
    public List<Venta> listarDelDia() {
        return repository.listarDelDia();
    }

    /**
     * Lista ventas por rango de fechas
     */
    public List<Venta> listarPorRango(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas son obligatorias");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde debe ser anterior a la fecha hasta");
        }

        return repository.listarPorRangoFechas(desde, hasta);
    }

    /**
     * Lista ventas por usuario
     */
    public List<Venta> listarPorUsuario(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("Usuario inválido");
        }

        return repository.listarPorUsuario(usuarioId);
    }

    /**
     * Calcula el total de ventas del día
     */
    public double calcularTotalDelDia() {
        return repository.calcularTotalDelDia();
    }

    /**
     * Calcula el total de ventas por rango
     */
    public double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas son obligatorias");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde debe ser anterior a la fecha hasta");
        }

        return repository.calcularTotalPorRango(desde, hasta);
    }
}

