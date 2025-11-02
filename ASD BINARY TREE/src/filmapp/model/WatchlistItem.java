package filmapp.model;

import java.time.LocalDate;

public class WatchlistItem implements Comparable<WatchlistItem> {
    private final String filmID;
    private final LocalDate dateAdded;
    private final double urgency;

    public WatchlistItem(String filmID, double urgency) {
        this.filmID = filmID;
        this.urgency = urgency;
        this.dateAdded = LocalDate.now();
    }

    public String getFilmID() {
        return filmID;
    }

    public double getUrgency() {
        return urgency;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    @Override
    public int compareTo(WatchlistItem other) {
        // semakin kecil urgency, semakin tinggi prioritas
        return Double.compare(this.urgency, other.urgency);
    }

    @Override
    public String toString() {
        return filmID + " | Urgency: " + urgency + " | Added: " + dateAdded;
    }
}
