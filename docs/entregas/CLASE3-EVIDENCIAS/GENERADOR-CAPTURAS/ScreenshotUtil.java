package cl.enmanuelchirinos.pnb.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utilitario para tomar capturas de componentes Swing y ventanas.
 */
public class ScreenshotUtil {

    /**
     * Captura una ventana o componente y guarda como PNG.
     * @param comp componente (Frame, Panel, etc.)
     * @param filePath ruta de salida (PNG)
     */
    public static void capture(Component comp, String filePath) throws IOException {
        Rectangle bounds = comp.getBounds();
        if (bounds.width <= 0 || bounds.height <= 0) {
            Dimension pref = comp.getPreferredSize();
            bounds = new Rectangle(pref.width > 0 ? pref.width : 800, pref.height > 0 ? pref.height : 600);
        }
        BufferedImage img = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        comp.paint(g2);
        g2.dispose();
        File out = new File(filePath);
        out.getParentFile().mkdirs();
        ImageIO.write(img, "png", out);
    }

    /**
     * Captura la pantalla de la región de una ventana usando Robot (incluye bordes).
     */
    public static void captureWindowOnScreen(Window window, String filePath) throws AWTException, IOException {
        Rectangle rect = window.getBounds();
        // Convertir a posición en pantalla
        Point p = window.getLocationOnScreen();
        rect.setLocation(p);
        Robot robot = new Robot();
        BufferedImage img = robot.createScreenCapture(rect);
        File out = new File(filePath);
        out.getParentFile().mkdirs();
        ImageIO.write(img, "png", out);
    }
}

