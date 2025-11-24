package cl.enmanuelchirinos.pnb.service;

import cl.enmanuelchirinos.pnb.model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> listAll();
    Usuario findById(int id);
    List<Usuario> searchByUsername(String username);
    void add(Usuario usuario);
    void update(Usuario usuario);
    void deleteById(int id);
    void toggleActive(int id, boolean active);
    Usuario authenticate(String username, String password);
}
