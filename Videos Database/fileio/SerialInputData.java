package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }

    /**
     * Returneaza media rating-urilor unui serial.
     * */
    public Double averageSerial() { // returneaza media unui serial (cu toate sezoanele)
        double rating = 0;
        int nr = 0;
        for (Season currentSeason : getSeasons()) {
            rating += currentSeason.averageSeason();
            nr++;
        }
        if (rating != 0) {
            return rating / nr;
        }
        return rating;
    }

    /**
     * Returneaza durata totala unui serial.
     * */
    public int totalDuration() {
        int total = 0;
        for (Season currentSeason : getSeasons()) {
            total += currentSeason.getDuration();
        }
        return total;
    }
}
