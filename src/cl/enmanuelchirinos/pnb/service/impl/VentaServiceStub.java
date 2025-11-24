package cl.enmanuelchirinos.pnb.service.impl;

import cl.enmanuelchirinos.pnb.model.Venta;
import cl.enmanuelchirinos.pnb.service.VentaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaServiceStub implements VentaService {
    private final List<Venta> ventas = new ArrayList<>();
    private int nextId = 1;

    public VentaServiceStub() {
        seed();
    }

    private void seed() {
        ventas.add(new Venta(nextId++, LocalDateTime.now().minusMinutes(15), 1, "Administrador", 7500, "ACTIVA"));
        ventas.add(new Venta(nextId++, LocalDateTime.now().minusHours(1), 2, "Operador Local", 4500, "ACTIVA"));
        ventas.add(new Venta(nextId++, LocalDateTime.now().minusDays(1), 1, "Administrador", 12500, "ANULADA"));
    }

    @Override
    public List<Venta> listAll() {
        return new ArrayList<>(ventas);
    }

    @Override
    public Venta findById(int id) {
        return ventas.stream().filter(v -> v.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void add(Venta venta) {
        venta.setId(nextId++);
        ventas.add(venta);
    }

    @Override
    public void update(Venta venta) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getId() == venta.getId()) {
                ventas.set(i, venta);
                return;
            }
        }
    }

    @Override
    public void deleteById(int id) {
        ventas.removeIf(v -> v.getId() == id);
    }

    @Override
    public List<Venta> listByDate(LocalDate date) {
        return ventas.stream().filter(v -> v.getFechaHora().toLocalDate().equals(date)).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Venta> listToday() {
        return listByDate(LocalDate.now());
    }

    @Override
    public void annul(int id) {
        Venta v = findById(id);
        if (v != null) v.setEstado("ANULADA");
    }

    @Override
    public double totalByDate(LocalDate date) {
        return listByDate(date).stream().filter(v -> "ACTIVA".equals(v.getEstado())).mapToDouble(Venta::getTotal).sum();
    }
}
