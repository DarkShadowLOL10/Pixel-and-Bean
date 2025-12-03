package cl.enmanuelchirinos.pnb;

import cl.enmanuelchirinos.pnb.gui.LoginFrame;
import cl.enmanuelchirinos.pnb.gui.MainFrame;
import cl.enmanuelchirinos.pnb.gui.ScreenshotUtil;

import javax.swing.*;
import java.io.IOException;

/**
 * Runner que abre la app y toma capturas de las vistas principales
 * para evidencias de ejecución.
 */
public class PixelAndBeanAutoCapture {

    private static void stabilizeUI() throws InterruptedException {
        // pequeñas pausas para permitir render
        Thread.sleep(500);
        Toolkit.getDefaultToolkit().sync();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Login
                LoginFrame login = new LoginFrame();
                login.pack();
                login.setVisible(true);
                login.repaint();
                stabilizeUI();
                // Captura en memoria del contenido del frame
                ScreenshotUtil.capture(login.getContentPane(), "docs/entregas/CLASE3-EVIDENCIAS/EJECUCION-APP/login.png");

                // Abrir MainFrame directamente como si fuera login exitoso
                login.dispose();
                MainFrame main = new MainFrame("admin", "ADMIN");
                main.pack();
                main.setVisible(true);
                main.repaint();
                stabilizeUI();
                ScreenshotUtil.capture(main.getContentPane(), "docs/entregas/CLASE3-EVIDENCIAS/EJECUCION-APP/main.png");

                // Capturar Usuarios (solo ADMIN)
                main.showUsuarios();
                main.getContentPane().revalidate();
                main.getContentPane().repaint();
                stabilizeUI();
                ScreenshotUtil.capture(main.getContentPane(), "docs/entregas/CLASE3-EVIDENCIAS/EJECUCION-APP/usuarios.png");

                // Capturar Productos
                main.showProductos();
                main.getContentPane().revalidate();
                main.getContentPane().repaint();
                stabilizeUI();
                ScreenshotUtil.capture(main.getContentPane(), "docs/entregas/CLASE3-EVIDENCIAS/EJECUCION-APP/productos.png");

                // Capturar Ventas
                main.showVentas();
                main.getContentPane().revalidate();
                main.getContentPane().repaint();
                stabilizeUI();
                ScreenshotUtil.capture(main.getContentPane(), "docs/entregas/CLASE3-EVIDENCIAS/EJECUCION-APP/ventas.png");

                // Capturar Reportes
                main.showReportes();
                main.getContentPane().revalidate();
                main.getContentPane().repaint();
                stabilizeUI();
                ScreenshotUtil.capture(main.getContentPane(), "docs/entregas/CLASE3-EVIDENCIAS/EJECUCION-APP/reportes.png");

                JOptionPane.showMessageDialog(main,
                    "Capturas automáticas generadas en docs/entregas/CLASE3-EVIDENCIAS/EJECUCION-APP/",
                    "AutoCapture",
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (InterruptedException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error tomando capturas: " + e.getMessage());
            }
        });
    }
}
