package cl.enmanuelchirinos.pnb.service.impl;

import cl.enmanuelchirinos.pnb.model.Usuario;
import cl.enmanuelchirinos.pnb.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioServiceStub implements UsuarioService {
    private final List<Usuario> usuarios = new ArrayList<>();
    private int nextId = 1;

    public UsuarioServiceStub() {
        seed();
    }

    private void seed() {
        usuarios.add(new Usuario(nextId++, "admin", "1234", "Administrador", "ADMIN", true));
        usuarios.add(new Usuario(nextId++, "operador", "op123", "Operador Local", "OPERADOR", true));
    }

    @Override
    public List<Usuario> listAll() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Usuario findById(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Usuario> searchByUsername(String username) {
        String un = username.toLowerCase();
        return usuarios.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(un))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Usuario usuario) {
        usuario.setId(nextId++);
        usuarios.add(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
                return;
            }
        }
    }

    @Override
    public void deleteById(int id) {
        usuarios.removeIf(u -> u.getId() == id);
    }

    @Override
    public void toggleActive(int id, boolean active) {
        Usuario u = findById(id);
        if (u != null) {
            u.setActivo(active);
        }
    }

    @Override
    public Usuario authenticate(String username, String password) {
        return usuarios.stream().filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password) && u.isActivo()).findFirst().orElse(null);
    }
}
