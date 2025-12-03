package cl.enmanuelchirinos.pnb;

import cl.enmanuelchirinos.pnb.gui.LoginFrame;
import cl.enmanuelchirinos.pnb.gui.MainFrame;
import cl.enmanuelchirinos.pnb.gui.ScreenshotUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Runner que abre la app y toma capturas de las vistas principales
 * para evidencias de ejecución.
 */
public class PixelAndBeanAutoCapture {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Login
                LoginFrame login = new LoginFrame();
                login.setVisible(true);
                Thread.sleep(800); // Esperar render
                ScreenshotUtil.captureWindowOnScreen(login, "docs/entregas/CLASE2-EJECUCION/screenshots/login.png");

                // Abrir MainFrame directamente como si fuera login exitoso
                login.dispose();
                MainFrame main = new MainFrame("admin", "ADMIN");
                main.setVisible(true);
                Thread.sleep(600);
                ScreenshotUtil.captureWindowOnScreen(main, "docs/entregas/CLASE2-EJECUCION/screenshots/main.png");

                // Capturar Usuarios (solo ADMIN)
                main.showUsuarios();
                Thread.sleep(600);
                ScreenshotUtil.captureWindowOnScreen(main, "docs/entregas/CLASE2-EJECUCION/screenshots/usuarios.png");

                // Capturar Productos
                main.showProductos();
                Thread.sleep(600);
                ScreenshotUtil.captureWindowOnScreen(main, "docs/entregas/CLASE2-EJECUCION/screenshots/productos.png");

                // Capturar Ventas
                main.showVentas();
                Thread.sleep(600);
                ScreenshotUtil.captureWindowOnScreen(main, "docs/entregas/CLASE2-EJECUCION/screenshots/ventas.png");

                // Capturar Reportes
                main.showReportes();
                Thread.sleep(600);
                ScreenshotUtil.captureWindowOnScreen(main, "docs/entregas/CLASE2-EJECUCION/screenshots/reportes.png");

                JOptionPane.showMessageDialog(main,
                    "Capturas automáticas generadas en docs/entregas/CLASE2-EJECUCION/screenshots/",
                    "AutoCapture",
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (InterruptedException ignored) {
            } catch (AWTException | IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error tomando capturas: " + e.getMessage());
            }
        });
    }
}
