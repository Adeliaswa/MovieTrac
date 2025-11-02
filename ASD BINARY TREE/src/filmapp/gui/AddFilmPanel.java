package filmapp.gui;

import filmapp.manager.*;
import java.awt.*;
import javax.swing.*;

public class AddFilmPanel extends JPanel {
    public AddFilmPanel(CatalogManager catalogManager, GenreManager genreManager, PopularityManager popManager) {
        setBackground(new Color(35, 35, 35));
        setLayout(new BorderLayout());

        // === Judul Panel ===
        JLabel title = new JLabel("🎥 Tambah Film Baru", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // === Panel Form ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(45, 45, 45));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 150, 30, 150),
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color textColor = Color.WHITE;

        // ====== Field ======
        JLabel lblId = createStyledLabel("ID Film:", labelFont, textColor);
        JTextField idField = createStyledField();
        JLabel lblTitle = createStyledLabel("Judul Film:", labelFont, textColor);
        JTextField titleField = createStyledField();
        JLabel lblYear = createStyledLabel("Tahun Rilis:", labelFont, textColor);
        JTextField yearField = createStyledField();
        JLabel lblRating = createStyledLabel("Rating (0.0 - 10.0):", labelFont, textColor);
        JTextField ratingField = createStyledField();
        JLabel lblPopularity = createStyledLabel("Skor Popularitas:", labelFont, textColor);
        JTextField popularityField = createStyledField();

        JLabel lblGenre = createStyledLabel("Genre:", labelFont, textColor);
        JComboBox<String> genreCombo = new JComboBox<>(
                genreManager.getGenreList().toArray(new String[0])
        );
        genreCombo.setBackground(new Color(60, 60, 60));
        genreCombo.setForeground(Color.WHITE);
        genreCombo.setFocusable(true);

        // Tambahkan field satu per satu
        gbc.gridx = 0; gbc.gridy = 0;
        addRow(formPanel, gbc, lblId, idField);
        addRow(formPanel, gbc, lblTitle, titleField);
        addRow(formPanel, gbc, lblYear, yearField);
        addRow(formPanel, gbc, lblRating, ratingField);
        addRow(formPanel, gbc, lblPopularity, popularityField);
        addRow(formPanel, gbc, lblGenre, genreCombo);

        // ====== Tombol Tambah ======
        JButton addBtn = new JButton("Tambah Film");
        styleButton(addBtn);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(addBtn, gbc);

        addBtn.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String titleFilm = titleField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                double rating = Double.parseDouble(ratingField.getText().trim());
                double pop = Double.parseDouble(popularityField.getText().trim());
                String genre = (String) genreCombo.getSelectedItem();

                filmapp.model.Film film = new filmapp.model.Film(id, titleFilm, year, rating, pop, genre);
                catalogManager.showAllFilms();
                popManager.updateRanking(film);

                java.lang.reflect.Method saveMethod = catalogManager.getClass().getDeclaredMethod("saveFilmToFile", filmapp.model.Film.class);
                saveMethod.setAccessible(true);
                saveMethod.invoke(catalogManager, film);

                JOptionPane.showMessageDialog(this, 
                    "✅ Film berhasil ditambahkan dan disimpan ke 'films.txt'!", 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // Reset field
                idField.setText("");
                titleField.setText("");
                yearField.setText("");
                ratingField.setText("");
                popularityField.setText("");
                genreCombo.setSelectedIndex(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ Gagal menambahkan film, periksa input!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });


        add(formPanel, BorderLayout.CENTER);
    }

    // ====== Helper Methods ======
    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JTextField createStyledField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFocusable(true);
        field.setOpaque(true);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, JLabel label, Component input) {
        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(input, gbc);
        gbc.gridy++;
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(229, 9, 20));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));
    }
}

