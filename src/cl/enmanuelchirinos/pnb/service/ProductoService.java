package cl.enmanuelchirinos.pnb.service;

import cl.enmanuelchirinos.pnb.model.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listAll();
    List<Producto> listActive();
    Producto findById(int id);
    List<Producto> searchByName(String name);
    List<Producto> filterByCategory(String category);
    void add(Producto producto);
    void update(Producto producto);
    void deleteById(int id);
    void toggleActive(int id, boolean active);
}
