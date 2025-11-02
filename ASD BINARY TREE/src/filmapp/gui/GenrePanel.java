package filmapp.gui;

import filmapp.manager.GenreManager;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.*;

public class GenrePanel extends JPanel {

    public GenrePanel(GenreManager genreManager) {
        setBackground(new Color(35, 35, 35));
        setLayout(new BorderLayout());

        // === Judul Panel ===
        JLabel title = new JLabel("🎞️ Daftar Genre Film", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // === Panel Tengah ===
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(45, 45, 45));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 200, 30, 200),
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1)
        ));

        // === Tombol ===
        JButton showBtn = new JButton("Lihat Semua Genre");
        JButton addBtn = new JButton("Tambah Genre Baru");

        // Style tombol
        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        Color red = new Color(229, 9, 20);

        JButton[] buttons = { showBtn, addBtn };
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFont(btnFont);
            btn.setBackground(red);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setMaximumSize(new Dimension(250, 40));

            // Efek hover
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(200, 0, 10));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(red);
                }
            });
        }

        // === Area Output ===
        JTextArea output = new JTextArea(10, 40);
        output.setEditable(false);
        output.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        output.setBackground(new Color(60, 60, 60));
        output.setForeground(Color.WHITE);
        output.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(500, 250));
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        // === Tambahkan ke panel utama ===
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(showBtn);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(addBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scroll);
        centerPanel.add(Box.createVerticalStrut(10));

        add(centerPanel, BorderLayout.CENTER);

        // === Aksi tombol "Lihat Genre" ===
        showBtn.addActionListener(e -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);
            genreManager.showGenres();
            System.out.flush();
            System.setOut(old);
            output.setText(baos.toString());
        });

        // === Aksi tombol "Tambah Genre Baru" ===
        addBtn.addActionListener(e -> {
            String g = JOptionPane.showInputDialog(this, 
                    "Masukkan nama genre baru:", 
                    "Tambah Genre", 
                    JOptionPane.PLAIN_MESSAGE);

            if (g != null && !g.trim().isEmpty()) {
                genreManager.addGenreDirect(g.trim());
                JOptionPane.showMessageDialog(this, 
                        "✅ Genre \"" + g + "\" berhasil ditambahkan!",
                        "Sukses", 
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (g != null) {
                JOptionPane.showMessageDialog(this, 
                        "⚠️ Nama genre tidak boleh kosong!", 
                        "Peringatan", 
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}