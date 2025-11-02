package filmapp.model;

public class Film {
    private String id;
    private String title;
    private int year;
    private double rating;
    private double popularityScore;
    private String genre;

    public Film(String id, String title, int year, double rating, double popularityScore, String genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.popularityScore = popularityScore;
        this.genre = genre;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public double getRating() { return rating; }
    public double getPopularityScore() { return popularityScore; }
    public String getGenre() { return genre; }

    @Override
    public String toString() {
        return title + " (" + year + ") | Rating: " + rating +
               " | Popularity: " + popularityScore + " | Genre: " + genre;
    }
}
