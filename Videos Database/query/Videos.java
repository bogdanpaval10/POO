package query;

import auxiliary.AuxMethods;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Contine implementarile metodelor folosite pentru a implementa query-urile pentru video-uri.
 */
public class Videos {
    /**
     * Construieste un map cu media rating-urilor filmelor.
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param movies este lista cu filme
     * */
    public Map<String, Double> ratingMovies(final List<String> listYears,
                                            final List<String> listGenre,
                                            final List<MovieInputData> movies) {
        Map<String, Double> mapMoviesRating = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (MovieInputData currentMovie : movies) { // parcurg lista de filme
            int hasYear = aux.nrYearsMovie(currentMovie, listYears);
            int hasGenre = aux.nrGenresMovie(currentMovie, listGenre);
            // adaug daca are anul/genul cerut
            if (hasYear != 0 && hasGenre != 0 && currentMovie.averageMovie() != 0) {
                mapMoviesRating.put(currentMovie.getTitle(), currentMovie.averageMovie());
            }
        }
        return mapMoviesRating;
    }

    /**
     * Construieste un map cu media rating-urilor serialelor.
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param serials este lista cu seriale
     * */
    public Map<String, Double> ratingSerials(final List<String> listYears,
                                             final List<String> listGenre,
                                             final List<SerialInputData> serials) {
        Map<String, Double> mapSerialRating = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (SerialInputData currentSerial : serials) { // parcurg lista de seriale
            int hasYear = aux.nrYearsSerial(currentSerial, listYears);
            int hasGenre = aux.nrGenresSerial(currentSerial, listGenre);
            if (currentSerial.averageSerial() != 0) {
                double average = currentSerial.averageSerial();
                if (hasYear != 0 && hasGenre != 0) { // adaug daca are anul si genul cerut
                    mapSerialRating.put(currentSerial.getTitle(), average);
                } // adaug daca are anul cerut
                if (hasYear != 0 && hasGenre == 0  && listGenre.contains(null)) {
                    mapSerialRating.put(currentSerial.getTitle(), average);
                } // adaug daca are genul cerut
                if (hasYear == 0 && hasGenre != 0 && listYears.contains(null)) {
                    mapSerialRating.put(currentSerial.getTitle(), average);
                }
                if (hasYear == 0 && hasGenre == 0 && listGenre.contains(null)) {
                    if (listYears.contains(null)) {
                        mapSerialRating.put(currentSerial.getTitle(), average);
                    }
                }
            }
        }
        return mapSerialRating;
    }

    /**
     * Construieste un map cu filmele favorite ale utilizatorilor.
     * @param users este lista cu userii
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param movies este lista cu filme
     * */
    public Map<String, Double> favoriteMovie(final List<UserInputData> users,
                                             final List<String> listYears,
                                             final List<String> listGenre,
                                             final List<MovieInputData> movies) {
        List<String> listFavorites = new ArrayList<>();
        Map<String, Double> mapFavoriteMovie = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (MovieInputData currentMovie : movies) { // parcurg lista de filme
            int hasYear = aux.nrYearsMovie(currentMovie, listYears);
            int hasGenre = aux.nrGenresMovie(currentMovie, listGenre);
            if (hasYear != 0 && hasGenre != 0) { // adaug daca are anul si genul cerut
                listFavorites.add(currentMovie.getTitle());
            } // adaug daca are anul cerut
            if (hasYear != 0 && hasGenre == 0 && listGenre.contains(null)) {
                listFavorites.add(currentMovie.getTitle());
            } // adaug daca are genul cerut
            if (hasYear == 0 && hasGenre != 0 && listYears.contains(null)) {
                listFavorites.add(currentMovie.getTitle());
            }
            if (hasYear == 0 && hasGenre == 0) {
                if (listGenre.contains(null) && listYears.contains(null)) {
                    listFavorites.add(currentMovie.getTitle());
                }
            }
        } // in lista am toate filmele cu criteriile cerute

        for (UserInputData currentUser : users) { // parcurg lista de useri
            for (String currentMovie : currentUser.getFavoriteMovies()) { // parcurg lista de filme
                if (listFavorites.contains(currentMovie)) {               // favorite a userului
                    double total = 1; // filmul userului este in lista de filme cu criterii
                    if (mapFavoriteMovie.containsKey(currentMovie)) { // daca este deja adaugat
                        total += mapFavoriteMovie.get(currentMovie);
                    }
                    mapFavoriteMovie.put(currentMovie, total);
                }
            }
        }
        return mapFavoriteMovie;
    }

    /**
     * Construieste un map cu serialele favorite ale utilizatorilor.
     * @param users este lista cu userii
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param serials este lista cu seriale
     * */
    public Map<String, Double> favoriteSerial(final List<UserInputData> users,
                                              final List<String> listYears,
                                              final List<String> listGenre,
                                              final List<SerialInputData> serials) {
        List<String> listFavorites = new ArrayList<>();
        Map<String, Double> mapFavoriteSerial = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (SerialInputData currentSerial : serials) { // parcurg lista de seriale
            int hasYear = aux.nrYearsSerial(currentSerial, listYears);
            int hasGenre = aux.nrGenresSerial(currentSerial, listGenre);
            if (hasYear != 0 && hasGenre != 0) { // adaug daca are anul si genul cerut
                listFavorites.add(currentSerial.getTitle());
            } // adaug daca are anul cerut
            if (hasYear != 0 && hasGenre == 0 && listGenre.contains(null)) {
                listFavorites.add(currentSerial.getTitle());
            } // adaug daca are genul cerut
            if (hasYear == 0 && hasGenre != 0 && listYears.contains(null)) {
                listFavorites.add(currentSerial.getTitle());
            }
        } // in lista am toate serialele cu criteriile cerute

        for (UserInputData currentUser : users) { // parcurg lista de useri
            for (String currentSerial : currentUser.getFavoriteMovies()) { // parcurg lista de
                if (listFavorites.contains(currentSerial)) {              // seriale favorite a user
                    double total = 1; // serialul userului este in lista de seriale cu criterii
                    if (mapFavoriteSerial.containsKey(currentSerial)) { // daca este deja adaugat
                        total += mapFavoriteSerial.get(currentSerial);
                    }
                    mapFavoriteSerial.put(currentSerial, total);
                }
            }
        }
        return mapFavoriteSerial;
    }

    /**
     * Construieste un map cu filmele si durata lor.
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param movies este lista cu filme
     * */
    public Map<String, Double> longestMovie(final List<String> listYears,
                                            final List<String> listGenre,
                                            final List<MovieInputData> movies) {
        Map<String, Double> mapLongestMovie = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (MovieInputData currentMovie : movies) { // parcurg lista de filme
            int hasYear = aux.nrYearsMovie(currentMovie, listYears);
            int hasGenre = aux.nrGenresMovie(currentMovie, listGenre);
            double duration = currentMovie.getDuration();
            if (hasYear != 0 && hasGenre != 0) { // adaug daca are anul si genul cerut
                mapLongestMovie.put(currentMovie.getTitle(), duration);
            } // adaug daca are anul cerut
            if (hasYear != 0 && hasGenre == 0 && listGenre.contains(null)) {
                mapLongestMovie.put(currentMovie.getTitle(), duration);
            } // adaug daca are genul cerut
            if (hasYear == 0 && hasGenre != 0 && listYears.contains(null)) {
                mapLongestMovie.put(currentMovie.getTitle(), duration);
            }
            if (hasYear == 0 && hasGenre == 0) {
                if (listGenre.contains(null) && listYears.contains(null)) {
                    mapLongestMovie.put(currentMovie.getTitle(), duration);
                }
            }
        }
        return mapLongestMovie;
    }

    /**
     * Construieste un map cu serialele si durata lor.
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param serials este lista cu seriale
     * */
    public Map<String, Double> longestSerial(final List<String> listYears,
                                             final List<String> listGenre,
                                             final List<SerialInputData> serials) {
        Map<String, Double> mapLongestSerial = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (SerialInputData currentSerial : serials) { // parcurg lista de seriale
            int hasYear = aux.nrYearsSerial(currentSerial, listYears);
            int hasGenre = aux.nrGenresSerial(currentSerial, listGenre);
            double duration = currentSerial.totalDuration();
            if (hasYear != 0 && hasGenre != 0) { // adaug daca are anul si genul cerut
                mapLongestSerial.put(currentSerial.getTitle(), duration);
            }
        }
        return mapLongestSerial;
    }

    /**
     * Construieste un map cu filmele si numarul de vizualizari.
     * @param users este lista cu userii
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param movies este lista cu filme
     * */
    public Map<String, Double> mostViewedMovie(final List<UserInputData> users,
                                               final List<String> listYears,
                                               final List<String> listGenre,
                                               final List<MovieInputData> movies) {
        List<String> listFavorites = new ArrayList<>();
        Map<String, Double> mapMostViewedMovie = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (MovieInputData currentMovie : movies) { // parcurg lista de filme
            int hasYear = aux.nrYearsMovie(currentMovie, listYears);
            int hasGenre = aux.nrGenresMovie(currentMovie, listGenre);
            if (hasYear != 0 && hasGenre != 0) { // adaug daca are anul si genul cerut
                listFavorites.add(currentMovie.getTitle());
            } // adaug daca are anul cerut
            if (hasYear != 0 && hasGenre == 0 && listGenre.contains(null)) {
                listFavorites.add(currentMovie.getTitle());
            } // adaug daca are genul cerut
            if (hasYear == 0 && hasGenre != 0 && listYears.contains(null)) {
                listFavorites.add(currentMovie.getTitle());
            }
        } // in lista am toate filmele cu criteriile cerute

        for (UserInputData currentUser : users) { // parcurg lista de useri
            for (String currentMovie : listFavorites) { // parcurg lista de filme cu conditii
                if (currentUser.getHistory().containsKey(currentMovie)) {
                    double total = 1; // filmul vazut este in lista de filme cu criterii
                    if (mapMostViewedMovie.containsKey(currentMovie)) { // daca este deja adaugat
                        total = mapMostViewedMovie.get(currentMovie);
                        mapMostViewedMovie.put(currentMovie, total
                                + currentUser.getHistory().get(currentMovie));
                    } else { // daca nu este deja adaugat
                        mapMostViewedMovie.put(currentMovie, total);
                    }
                }
            }
        }
        return mapMostViewedMovie;
    }

    /**
     * Construieste un map cu serialele si numarul de vizualizari.
     * @param users este lista cu userii
     * @param listYears este lista cu anii de selectie
     * @param listGenre este lista cu genurile de selectie
     * @param serials este lista cu seriale
     * */
    public Map<String, Double> mostViewedSerial(final List<UserInputData> users,
                                                final List<String> listYears,
                                                final List<String> listGenre,
                                                final List<SerialInputData> serials) {
        List<String> listFavorites = new ArrayList<>();
        Map<String, Double> mapMostViewedSerial = new HashMap<>();
        AuxMethods aux = new AuxMethods();
        for (SerialInputData currentSerial : serials) { // parcurg lista de seriale
            int hasYear = aux.nrYearsSerial(currentSerial, listYears);
            int hasGenre = aux.nrGenresSerial(currentSerial, listGenre);
            if (hasYear != 0 && hasGenre != 0) { // adaug daca are anul si genul cerut
                listFavorites.add(currentSerial.getTitle());
            } // adaug daca are anul cerut
            if (hasYear != 0 && hasGenre == 0 && listGenre.contains(null)) {
                listFavorites.add(currentSerial.getTitle());
            } // adaug daca are genul cerut
            if (hasYear == 0 && hasGenre != 0 && listYears.contains(null)) {
                listFavorites.add(currentSerial.getTitle());
            }
        } // in lista am toate serialele cu criteriile cerute

        for (UserInputData currentUser : users) { // parcurg lista de useri
            for (String currentSerial : listFavorites) { // parcurg lista de filme cu conditii
                if (currentUser.getHistory().containsKey(currentSerial)) {
                    double total = 1; // filmul vazut este in lista de seriale cu criterii
                    if (mapMostViewedSerial.containsKey(currentSerial)) { // daca este deja adaugat
                        total += mapMostViewedSerial.get(currentSerial);
                    }
                    mapMostViewedSerial.put(currentSerial, total);
                }
            }
        }
        return mapMostViewedSerial;
    }
}
