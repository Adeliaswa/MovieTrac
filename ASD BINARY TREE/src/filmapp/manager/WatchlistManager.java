package filmapp.manager;

import filmapp.heap.MinHeap;
import filmapp.model.WatchlistItem;
import java.util.List;
import java.util.Scanner;

public class WatchlistManager {
    private final MinHeap<WatchlistItem> watchlist = new MinHeap<>();

    public void manage(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== Watchlist Pengguna ===");
            System.out.println("1. Tambah ke Watchlist");
            System.out.println("2. Tampilkan Urutan Prioritas");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addToWatchlist(scanner);
                case 2 -> showWatchlist();
                case 0 -> System.out.println("Kembali ke menu utama...");
                default -> System.out.println("Pilihan tidak valid!");
            }
        } while (choice != 0);
    }

    private void addToWatchlist(Scanner scanner) {
        System.out.print("Masukkan ID Film: ");
        String filmID = scanner.nextLine();
        System.out.print("Masukkan Urgensi Tontonan (semakin kecil = lebih prioritas): ");
        double urgency = scanner.nextDouble();
        scanner.nextLine();

        WatchlistItem item = new WatchlistItem(filmID, urgency);
        watchlist.insert(item);
        System.out.println("Film ditambahkan ke watchlist!");
    }

    private void showWatchlist() {
        System.out.println("\n=== Urutan Watchlist Berdasarkan Prioritas ===");
        watchlist.printHeap();
    }

    public void addToWatchlist(String filmID, double urgency) {
        WatchlistItem item = new WatchlistItem(filmID, urgency);
        watchlist.insert(item);
    }

    public List<WatchlistItem> getAllWatchlist() {
        return watchlist.getAllElements(); // method ini harus ada di MinHeap kamu
    }

    public boolean isEmpty() {
        return watchlist.isEmpty();
    }
}
