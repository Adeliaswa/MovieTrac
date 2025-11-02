package filmapp.manager;

import filmapp.tree.GenreBST;
import java.util.List;
import java.util.Scanner;

public class GenreManager {
    private final GenreBST genreTree = new GenreBST();

    public GenreManager() {
        addDefaultGenres();
    }

    private void addDefaultGenres() {
        genreTree.insert("Action");
        genreTree.insert("Drama");
        genreTree.insert("Comedy");
        genreTree.insert("Romance");
        genreTree.insert("Horror");
        genreTree.insert("Thriller");
        genreTree.insert("Fantasy");
        genreTree.insert("Sci-Fi");
    }

    public void showGenres() {
        System.out.println("=== Daftar Genre (Terurut Alfabetis) ===");
        List<String> ordered = genreTree.getInorderList();
        for (int i = 0; i < ordered.size(); i++) {
            System.out.println((i + 1) + ". " + ordered.get(i));
        }
    }

    public void addGenre(Scanner scanner) {
        System.out.print("Masukkan nama genre baru: ");
        String genre = scanner.nextLine();
        genreTree.insert(genre);
        System.out.println("🎞️ Genre berhasil ditambahkan!");
    }

    public String chooseGenre(Scanner scanner) {
        List<String> ordered = genreTree.getInorderList();
        System.out.println("\n=== Pilih Genre ===");
        for (int i = 0; i < ordered.size(); i++) {
            System.out.println((i + 1) + ". " + ordered.get(i));
        }
        System.out.print("Pilih genre (nomor): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > ordered.size()) {
            System.out.println("Pilihan tidak valid, default ke 'Drama'.");
            return "Drama";
        }
        return ordered.get(choice - 1);
    }

    public java.util.List<String> getGenreList() {
        return genreTree.getInorderList();
    }

    public void addGenreDirect(String genre) {
        if (genre == null || genre.trim().isEmpty()) return;
        genreTree.insert(genre.trim());
        System.out.println("🎞️ Genre \"" + genre + "\" berhasil ditambahkan!");
    }
}
