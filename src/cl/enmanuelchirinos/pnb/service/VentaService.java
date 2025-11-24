package cl.enmanuelchirinos.pnb.service;

import cl.enmanuelchirinos.pnb.model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {
    List<Venta> listAll();
    Venta findById(int id);
    void add(Venta venta);
    void update(Venta venta);
    void deleteById(int id);
    // Nuevos métodos guía
    List<Venta> listByDate(LocalDate date);
    List<Venta> listToday();
    void annul(int id);
    double totalByDate(LocalDate date);
}
