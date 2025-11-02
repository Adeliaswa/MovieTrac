package filmapp.gui;

import filmapp.manager.CatalogManager;
import filmapp.model.Film;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class DisplayAllPanel extends JPanel {

    public DisplayAllPanel(CatalogManager catalogManager) {
        setBackground(new Color(35, 35, 35));
        setLayout(new BorderLayout());

        // === Judul Panel ===
        JLabel title = new JLabel("🎬 Daftar Semua Film", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // === Panel Tengah ===
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(45, 45, 45));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 150, 30, 150),
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1)
        ));

        // === Tombol tampilkan ===
        JButton showBtn = new JButton("Tampilkan Semua Film");
        showBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        showBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        showBtn.setBackground(new Color(229, 9, 20));
        showBtn.setForeground(Color.WHITE);
        showBtn.setFocusPainted(false);
        showBtn.setBorderPainted(false);
        showBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showBtn.setMaximumSize(new Dimension(250, 40));

        // Efek hover
        showBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                showBtn.setBackground(new Color(200, 0, 10));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                showBtn.setBackground(new Color(229, 9, 20));
            }
        });

        // === Area hasil ===
        JTextArea outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        outputArea.setBackground(new Color(60, 60, 60));
        outputArea.setForeground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        // === Tambahkan komponen ===
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(showBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scrollPane);
        centerPanel.add(Box.createVerticalStrut(15));

        add(centerPanel, BorderLayout.CENTER);

        // === Event tombol tampilkan ===
        showBtn.addActionListener(e -> {
            List<Film> allFilms = catalogManager.getAllFilms();
            if (allFilms == null || allFilms.isEmpty()) {
                outputArea.setText("⚠️ Belum ada film yang tersimpan di katalog.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=== Daftar Semua Film ===\n\n");

            for (Film f : allFilms) {
                sb.append("🎬 ").append(f.getTitle()).append("\n")
                .append("ID Film     : ").append(f.getId()).append("\n")
                .append("Tahun Rilis : ").append(f.getYear()).append("\n")
                .append("Rating      : ").append(f.getRating()).append("\n")
                .append("Popularitas : ").append(f.getPopularityScore()).append("\n")
                .append("Genre       : ").append(f.getGenre()).append("\n\n");
            }

            outputArea.setText(sb.toString());
        });

    }
}

