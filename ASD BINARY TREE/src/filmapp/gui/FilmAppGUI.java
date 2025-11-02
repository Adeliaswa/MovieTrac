package filmapp.gui;

import javax.swing.*;

public class FilmAppGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}