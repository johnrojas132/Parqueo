package main;

import presentacion.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada de la aplicacion.
 */
public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, se usa el Look and Feel por defecto de Java.
        }
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
