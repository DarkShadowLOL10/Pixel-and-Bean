package cl.enmanuelchirinos.pnb;

import cl.enmanuelchirinos.pnb.gui.LoginFrame;

/**
 * Pixel & Bean - Sistema de Gestión para Café-Arcade
 * @author Enmanuel Chirinos
 * @version 1.0
 */
public class PixelAndBean {

    public static void main(String[] args) {
        // Ejecuta en el Event Dispatch Thread (EDT) para garantizar thread-safety
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}

