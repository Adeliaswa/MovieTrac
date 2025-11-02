package filmapp.gui;

import filmapp.manager.WatchlistManager;
import filmapp.model.WatchlistItem;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class WatchlistPanel extends JPanel {

    public WatchlistPanel(WatchlistManager manager) {
        setBackground(new Color(35, 35, 35));
        setLayout(new BorderLayout());

        // === Judul Panel ===
        JLabel title = new JLabel("🎞️ Watchlist Pengguna", SwingConstants.CENTER);
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

        // === Input field ===
        JTextField idField = new JTextField();
        JTextField urgencyField = new JTextField();

        idField.setMaximumSize(new Dimension(300, 30));
        urgencyField.setMaximumSize(new Dimension(300, 30));

        JLabel idLabel = new JLabel("ID Film:");
        JLabel urgLabel = new JLabel("Urgensi (semakin kecil = lebih prioritas):");
        idLabel.setForeground(Color.WHITE);
        urgLabel.setForeground(Color.WHITE);
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        urgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);
        urgencyField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Tombol ===
        JButton addBtn = new JButton("Tambah ke Watchlist");
        JButton showBtn = new JButton("Tampilkan Watchlist");

        for (JButton btn : new JButton[]{addBtn, showBtn}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBackground(new Color(229, 9, 20));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 40));
        }

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
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // === Tambahkan ke panel ===
        centerPanel.add(idLabel);
        centerPanel.add(idField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(urgLabel);
        centerPanel.add(urgencyField);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(addBtn);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(showBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        // === Aksi tombol ===
        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String urgText = urgencyField.getText().trim();
            if (id.isEmpty() || urgText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi ID film dan urgensi terlebih dahulu!");
                return;
            }

            try {
                double urgency = Double.parseDouble(urgText);
                manager.addToWatchlist(id, urgency);
                JOptionPane.showMessageDialog(this, "Film berhasil ditambahkan ke watchlist!");
                idField.setText("");
                urgencyField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Urgensi harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        showBtn.addActionListener(e -> {
            if (manager.isEmpty()) {
                outputArea.setText("⚠️ Watchlist kosong.");
                return;
            }

            List<WatchlistItem> items = manager.getAllWatchlist();
            StringBuilder sb = new StringBuilder("=== Urutan Watchlist Berdasarkan Prioritas ===\n\n");
            int index = 1;
            for (WatchlistItem item : items) {
                sb.append(index++).append(". ")
                        .append(item.getFilmID())
                        .append(" (Urgensi: ").append(item.getUrgency()).append(")\n");
            }
            outputArea.setText(sb.toString());
        });
    }
}
