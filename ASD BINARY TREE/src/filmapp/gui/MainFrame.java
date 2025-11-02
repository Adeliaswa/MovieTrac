package filmapp.gui;

import filmapp.manager.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private final CatalogManager catalogManager = new CatalogManager();
    private final PopularityManager popularityManager = new PopularityManager();
    private final WatchlistManager watchlistManager = new WatchlistManager();
    private final GenreManager genreManager = new GenreManager();

    public MainFrame() {
        setTitle("🎬 FilmApp Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(28, 28, 28));

        catalogManager.loadFilmsFromFile(popularityManager);

        // ===== HEADER =====
        JLabel header = new JLabel("FILMAPP DASHBOARD", SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(20, 20, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 10, 10));
        sidebar.setBackground(new Color(18, 18, 18));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        String[] menu = {
            "Tambah Film", "Cari Film", "Film Terpopuler",
            "Genre", "Watchlist", "Tampilkan Semua Film", "Keluar"
        };

        for (String item : menu) {
            JButton btn = createRoundedButton(item);
            btn.addActionListener(e -> handleMenu(item));
            sidebar.add(btn);
        }

        // ===== MAIN PANEL =====
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(new Color(40, 40, 40));
        JLabel welcome = new JLabel("🎬 Selamat Datang di FilmApp!", SwingConstants.CENTER);
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        homePanel.add(welcome, BorderLayout.CENTER);

        mainPanel.add(homePanel, "HOME");
        mainPanel.add(new AddFilmPanel(catalogManager, genreManager, popularityManager), "ADD");
        mainPanel.add(new SearchFilmPanel(catalogManager), "SEARCH");
        mainPanel.add(new PopularityPanel(popularityManager), "POPULAR");
        mainPanel.add(new GenrePanel(genreManager), "GENRE");
        mainPanel.add(new WatchlistPanel(watchlistManager), "WATCHLIST");
        mainPanel.add(new DisplayAllPanel(catalogManager), "DISPLAY");

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "HOME");

    }

    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // background warna dasar
                Color base = getModel().isPressed() ? new Color(200, 0, 0)
                              : getModel().isRollover() ? new Color(240, 30, 30)
                              : new Color(229, 9, 20);
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // teks
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 3;
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setForeground(Color.WHITE);

        // efek hover lembut
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setToolTipText("Buka menu " + text);
            }
        });
        return btn;
    }

    private void handleMenu(String item) {
        switch (item) {
            case "Tambah Film" -> cardLayout.show(mainPanel, "ADD");
            case "Cari Film" -> cardLayout.show(mainPanel, "SEARCH");
            case "Film Terpopuler" -> cardLayout.show(mainPanel, "POPULAR");
            case "Genre" -> cardLayout.show(mainPanel, "GENRE");
            case "Watchlist" -> cardLayout.show(mainPanel, "WATCHLIST");
            case "Tampilkan Semua Film" -> cardLayout.show(mainPanel, "DISPLAY");
            case "Keluar" -> System.exit(0);
        }
    }
}