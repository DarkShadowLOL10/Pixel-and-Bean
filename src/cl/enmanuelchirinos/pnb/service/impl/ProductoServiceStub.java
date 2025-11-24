package cl.enmanuelchirinos.pnb.service.impl;

import cl.enmanuelchirinos.pnb.model.Producto;
import cl.enmanuelchirinos.pnb.service.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoServiceStub implements ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    private int nextId = 1;

    public ProductoServiceStub() {
        seed();
    }

    private void seed() {
        productos.add(new Producto(nextId++, "Espresso", "BEBIDA", "CAFE", 2500, true));
        productos.add(new Producto(nextId++, "Cappuccino", "BEBIDA", "CAFE", 3000, true));
        productos.add(new Producto(nextId++, "Brownie", "SNACK", "POSTRE", 2000, true));
        productos.add(new Producto(nextId++, "15 minutos", "TIEMPO", "ARCADE", 1500, true));
        productos.add(new Producto(nextId++, "30 minutos", "TIEMPO", "ARCADE", 2500, true));
    }

    @Override
    public List<Producto> listAll() {
        return new ArrayList<>(productos);
    }

    @Override
    public Producto findById(int id) {
        return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Producto> searchByName(String name) {
        String n = name.toLowerCase();
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(n))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Producto producto) {
        producto.setId(nextId++);
        productos.add(producto);
    }

    @Override
    public void update(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == producto.getId()) {
                productos.set(i, producto);
                return;
            }
        }
    }

    @Override
    public void deleteById(int id) {
        productos.removeIf(p -> p.getId() == id);
    }

    @Override
    public void toggleActive(int id, boolean active) {
        Producto p = findById(id);
        if (p != null) {
            p.setActivo(active);
        }
    }

    @Override
    public List<Producto> listActive() {
        return productos.stream().filter(Producto::isActivo).collect(Collectors.toList());
    }

    @Override
    public List<Producto> filterByCategory(String category) {
        return productos.stream().filter(p -> p.getCategoria().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
}
