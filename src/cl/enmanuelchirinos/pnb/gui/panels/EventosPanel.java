package cl.enmanuelchirinos.pnb.gui.panels;

import javax.swing.*;
import java.awt.*;

public class EventosPanel extends JPanel {
    public EventosPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("""
        <html><center>
        <h1 style='color:#FF6B35;'>🎮 Eventos y Torneos</h1>
        <p style='font-size:14px;'>Módulo en desarrollo – funcionalidades planificadas:</p>
        <ul style='text-align:left; font-size:12px;'>
          <li>Gestión de torneos semanales</li>
          <li>Inscripción de participantes</li>
          <li>Registro de resultados</li>
          <li>Rankings y estadísticas</li>
        </ul>
        <p style='margin-top:20px; font-size:11px; color:#666;'>Versión Alpha UI – Clase 2</p>
        </center></html>
        """, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(lbl, BorderLayout.CENTER);
    }
    public void refresh() { /* placeholder sin lógica */ }
}
