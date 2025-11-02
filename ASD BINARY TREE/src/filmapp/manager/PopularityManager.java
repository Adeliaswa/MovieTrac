package filmapp.manager;

import filmapp.heap.MaxHeap;
import filmapp.model.Film;
import java.util.Comparator;
import java.util.List;

public class PopularityManager {
    private final MaxHeap<Film> ranking = new MaxHeap<>(Comparator.comparingDouble(Film::getPopularityScore));

    public void updateRanking(Film film) {
        ranking.insert(film);
    }

    public void showRanking() {
        System.out.println("\n=== Film Terpopuler ===");
        if (ranking.isEmpty()) {
            System.out.println("Belum ada film dalam ranking popularitas.");
            return;
        }
        List<Film> top = ranking.topN(5);
        for (int i = 0; i < top.size(); i++) {
            System.out.println((i + 1) + ". " + top.get(i));
        }
    }

}
