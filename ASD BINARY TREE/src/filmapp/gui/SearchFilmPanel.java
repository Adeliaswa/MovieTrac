package filmapp.gui;

import filmapp.manager.CatalogManager;
import filmapp.model.Film;
import java.awt.*;
import java.util.Optional;
import javax.swing.*;

public class SearchFilmPanel extends JPanel {

    public SearchFilmPanel(CatalogManager catalogManager) {
        setBackground(new Color(35, 35, 35));
        setLayout(new BorderLayout());

        // === Judul ===
        JLabel title = new JLabel("🔍 Cari Film Berdasarkan ID", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // === Panel Tengah ===
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(45, 45, 45));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 250, 30, 250),
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1)
        ));

        // Label & TextField
        JLabel lblId = new JLabel("Masukkan ID Film:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblId.setForeground(Color.WHITE);
        lblId.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField idField = new JTextField(15);
        idField.setMaximumSize(new Dimension(300, 35));
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField.setBackground(new Color(60, 60, 60));
        idField.setForeground(Color.WHITE);
        idField.setCaretColor(Color.WHITE);
        idField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Tombol Cari
        JButton searchBtn = new JButton("Cari");
        searchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchBtn.setBackground(new Color(229, 9, 20));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.setMaximumSize(new Dimension(120, 40));

        // Efek hover
        searchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchBtn.setBackground(new Color(200, 0, 10));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchBtn.setBackground(new Color(229, 9, 20));
            }
        });

        // Area hasil
        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultArea.setBackground(new Color(60, 60, 60));
        resultArea.setForeground(Color.WHITE);
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        scroll.setPreferredSize(new Dimension(500, 180));
        scroll.setMaximumSize(new Dimension(600, 200));

        // === Tambahkan ke panel ===
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(lblId);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(idField);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(searchBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scroll);
        centerPanel.add(Box.createVerticalStrut(10));

        add(centerPanel, BorderLayout.CENTER);

        // === Aksi Tombol Cari ===
        searchBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Masukkan ID film terlebih dahulu!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Optional<Film> found = catalogManager.searchFilm(id);
            if (found.isPresent()) {
                Film f = found.get();
                resultArea.setText(
                        "🎬 Film Ditemukan!\n\n" +
                        "ID Film     : " + f.getId() + "\n" +
                        "Judul       : " + f.getTitle() + "\n" +
                        "Tahun Rilis : " + f.getYear() + "\n" +
                        "Rating      : " + f.getRating() + "\n" +
                        "Popularitas : " + f.getPopularityScore() + "\n" +
                        "Genre       : " + f.getGenre()
                );
            } else {
                resultArea.setText("❌ Film dengan ID \"" + id + "\" tidak ditemukan.");
            }
        });
    }
}
