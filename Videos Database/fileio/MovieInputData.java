package fileio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * The ratings a user gave for that movie
     */
    private final Map<String, Double> ratingsBy;

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratingsBy = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    public Map<String, Double> getRatingsBy() {
        return ratingsBy;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }

    /**
     * Returneaza media rating-urilor unui film.
     * */
    public Double averageMovie() { // returneaza media unui film
        double rating = 0;
        int nr = 0;
        for (Map.Entry<String, Double> entry : getRatingsBy().entrySet()) {
            rating += entry.getValue();
            nr++;
        }
        if (rating != 0) {
            return rating / nr;
        }
        return rating;
    }
}
