package filmapp.manager;

import filmapp.model.Film;
import filmapp.tree.AVLTree;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CatalogManager {
    private final AVLTree<Film> filmTree = new AVLTree<>();

    public void addFilm(Scanner scanner, GenreManager genreManager, PopularityManager popManager) {
        System.out.println("\n=== Tambah Film (CatalogManager) ===");
        System.out.print("Masukkan ID Film: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Judul Film: ");
        String title = scanner.nextLine();
        System.out.print("Masukkan Tahun Rilis: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Masukkan Rating (0.0 - 10.0): ");
        double rating = Double.parseDouble(scanner.nextLine());
        System.out.print("Masukkan Skor Popularitas: ");
        double popularity = Double.parseDouble(scanner.nextLine());

        String genre = genreManager.chooseGenre(scanner);

        Film film = new Film(id, title, year, rating, popularity, genre);
        filmTree.insert(film);
        popManager.updateRanking(film);
        saveFilmToFile(film);

        System.out.println("Film disimpan ke katalog dan ranking popularitas diperbarui.");
    }

    public Optional<Film> searchFilm(String id) {
        return filmTree.search(id);
    }

    public void showAllFilms() {
        System.out.println("\n=== Daftar Semua Film ===");
        filmTree.inorderTraversal();
    }

    private void saveFilmToFile(Film film) {
        File file = new File(System.getProperty("user.dir"), "films.txt");
        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(film.getId() + "|" + film.getTitle() + "|" + film.getYear() + "|" +
                         film.getRating() + "|" + film.getPopularityScore() + "|" + film.getGenre() + "\n");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan ke file.");
        }
    }

    public void loadFilmsFromFile(PopularityManager popManager) {
        File file = new File(System.getProperty("user.dir"), "films.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 6) {
                    Film film = new Film(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        Double.parseDouble(data[3]),
                        Double.parseDouble(data[4]),
                        data[5]
                    );
                    filmTree.insert(film);
                    popManager.updateRanking(film);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat file katalog film.");
        }
    }
    public List<Film> getAllFilms() {
        return filmTree.getInorderList(); // atau getInorder() tergantung implementasi kamu
    }
}
