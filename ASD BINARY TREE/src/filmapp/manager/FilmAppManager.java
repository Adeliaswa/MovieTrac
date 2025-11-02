package filmapp.manager;

import filmapp.model.Film;
import java.util.Optional;
import java.util.Scanner;

public class FilmAppManager {
    private final CatalogManager catalogManager = new CatalogManager();
    private final PopularityManager popularityManager = new PopularityManager();
    private final WatchlistManager watchlistManager = new WatchlistManager();
    private final GenreManager genreManager = new GenreManager();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        catalogManager.loadFilmsFromFile(popularityManager);

        int choice;
        do {
            System.out.println("\n=== FilmApp Manager ===");
            System.out.println("1. Tambahkan Film");
            System.out.println("2. Cari Film");
            System.out.println("3. Tampilkan Film Terpopuler");
            System.out.println("4. Kelola Watchlist");
            System.out.println("5. Lihat Daftar Genre");
            System.out.println("6. Tambah Genre");
            System.out.println("7. Tampilkan Semua Film");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> catalogManager.addFilm(scanner, genreManager, popularityManager);
                case 2 -> {
                    System.out.print("Masukkan ID Film yang dicari: ");
                    String id = scanner.nextLine();
                    Optional<Film> found = catalogManager.searchFilm(id);
                    if (found.isPresent()) {
                        Film film = found.get();
                        System.out.println("Film ditemukan: " + film);
                    } else {
                        System.out.println("Film dengan ID tersebut tidak ditemukan.");
                    }
                }
                case 3 -> popularityManager.showRanking();
                case 4 -> watchlistManager.manage(scanner);
                case 5 -> genreManager.showGenres();
                case 6 -> genreManager.addGenre(scanner);
                case 7 -> catalogManager.showAllFilms();
                case 0 -> System.out.println("Keluar...");
                default -> System.out.println("Pilihan tidak valid!");
            }
        } while (choice != 0);
    }
}
