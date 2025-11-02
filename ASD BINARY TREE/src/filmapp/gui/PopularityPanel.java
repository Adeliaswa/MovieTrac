package filmapp.gui;

import filmapp.manager.PopularityManager;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.*;

public class PopularityPanel extends JPanel {
    public PopularityPanel(PopularityManager popManager) {
        setBackground(new Color(35, 35, 35));
        setLayout(new BorderLayout());

        // === Judul Panel ===
        JLabel title = new JLabel("🔥 Film Terpopuler", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // === Panel Utama ===
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(45, 45, 45));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 200, 30, 200),
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1)
        ));

        // === Tombol ===
        JButton showBtn = new JButton("Tampilkan Film Terpopuler");
        showBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        showBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        showBtn.setBackground(new Color(229, 9, 20));
        showBtn.setForeground(Color.WHITE);
        showBtn.setFocusPainted(false);
        showBtn.setBorderPainted(false);
        showBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showBtn.setMaximumSize(new Dimension(250, 40));

        // Efek hover biar hidup
        showBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                showBtn.setBackground(new Color(200, 0, 10));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                showBtn.setBackground(new Color(229, 9, 20));
            }
        });

        // === Area hasil ===
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        outputArea.setBackground(new Color(60, 60, 60));
        outputArea.setForeground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(500, 250));
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        // === Tambahkan ke panel utama ===
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(showBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scroll);
        centerPanel.add(Box.createVerticalStrut(10));

        add(centerPanel, BorderLayout.CENTER);

        // === Aksi tombol ===
        showBtn.addActionListener(e -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);
            popManager.showRanking();
            System.out.flush();
            System.setOut(old);
            outputArea.setText(baos.toString());
        });
    }
}