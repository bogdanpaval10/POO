package recommendation;

import auxiliary.AuxMethods;
import common.Constants;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Contine implementarile metodelor folosite pentru a implementa recomandarile.
 */
public class Recommendations extends AuxRecommendation {
    /**
     * Construieste o lista cu toate filmele si serialele din baza de date.
     * @param movies este lista cu filme
     * @param serials este lista cu seriale
     * */
    public List<ShowInput> allFilms(final List<MovieInputData> movies,
                                    final List<SerialInputData> serials) {
        List<ShowInput> listAll = new ArrayList<>();
        listAll.addAll(movies);
        listAll.addAll(serials);
        return listAll;
    }

    /**
     * Gaseste primul video nevizualizat de utilizator.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * @param listAllFilms este lista cu toate filmele si serialele
     * */
    public String standard(final ActionInputData command, final List<UserInputData> users,
                           final List<ShowInput> listAllFilms) {
        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) {
                for (ShowInput currentShow : listAllFilms) { // parcurg lista din baza de date
                    if (!currentUser.getHistory().containsKey(currentShow.getTitle())) {
                        return "StandardRecommendation result: " + currentShow.getTitle();
                    } // daca nu a fost vazut
                }
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Gaseste cel mai bun video nevizualizat de utilizator.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * @param movies este lista cu filme
     * @param serials este lista cu seriale
     * */
    public String bestUnseen(final ActionInputData command, final List<UserInputData> users,
                             final List<MovieInputData> movies,
                             final List<SerialInputData> serials) {
        List<AuxRecommendation> listAuxiliary = new ArrayList<>();
        for (MovieInputData currentMovie : movies) { // parcurg lista de filme
            AuxRecommendation aux = new AuxRecommendation();
            aux.setName(currentMovie.getTitle());
            aux.setRating(currentMovie.averageMovie());
            listAuxiliary.add(aux);
        }
        AuxRecommendation aux = new AuxRecommendation();
        for (SerialInputData currentSerial : serials) { // parcurg lista de seriale
            aux.setName(currentSerial.getTitle());
            aux.setRating(currentSerial.averageSerial());
            listAuxiliary.add(aux);
        }
        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) {
                for (int i = 0; i < listAuxiliary.size() - 1; i++) { // sortez descrescator lista
                    for (int j = i + 1; j < listAuxiliary.size(); j++) { // de video-uri
                        double rating1 = listAuxiliary.get(i).getRating();
                        double rating2 = listAuxiliary.get(j).getRating();
                        if (Double.compare(rating1, rating2) < 0) {
                            if (!currentUser.getHistory().containsKey(
                                    listAuxiliary.get(j).getName())) {
                                aux.swap(listAuxiliary.get(i), listAuxiliary.get(j));
                            } // interschimba numele si valorile
                        }
                    }
                }
            }
        }
        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) {
                for (AuxRecommendation currentAux : listAuxiliary) { // parcurg lista de video-uri
                    if (!currentUser.getHistory().containsKey(currentAux.getName())) {
                        return "BestRatedUnseenRecommendation result: " + currentAux.getName();
                    } // daca nu a fost vazut
                }
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Gaseste cel mai popular video nevizualizat de utilizator.
     * Se aplica doar utilizatorilor premium.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * @param listAllFilms este lista cu toate filmele si serialele
     * */
    public String popular(final ActionInputData command, final List<UserInputData> users,
                          final List<ShowInput> listAllFilms) {
        AuxRecommendation auxRec = new AuxRecommendation();
        if (auxRec.verifyBasic(command, users)) {
            return "PopularRecommendation cannot be applied!";
        }
        Map<String, Double> mapPopularGenres = new HashMap<>();
        for (ShowInput currentShow : listAllFilms) { // parcurg lista cu videourile din baza de date
            for (String currentGenre : currentShow.getGenres()) { // parcurg lista de genuri
                double zero = 0;
                if (!mapPopularGenres.containsKey(currentGenre)) { // daca genul nu este, il adaug
                    mapPopularGenres.put(currentGenre, zero);
                }
            }
        }
        for (UserInputData currentUser : users) { // parcurg lista de useri
            for (ShowInput currentShow : listAllFilms) { // parcurg lista cu video-urile din baza
                if (currentUser.getHistory().containsKey(currentShow.getTitle())) { // daca e vazut
                    for (String currentGenre : currentShow.getGenres()) { // parcurg lista de genuri
                        mapPopularGenres.put(currentGenre, mapPopularGenres.get(currentGenre)
                                + currentUser.getHistory().get(currentShow.getTitle()));
                    }
                }
            }
        }
        AuxMethods aux = new AuxMethods();
        List<String> listGenres = aux.sortDescAMap(mapPopularGenres);

        for (String currentGenre : listGenres) { // parcurg lista de genuri
            for (ShowInput currentShow : listAllFilms) { // parcurg lista cu video-urile din baza
                for (UserInputData currentUser : users) { // parcurg lista de useri
                    if (currentUser.getUsername().equals(command.getUsername())) {
                        if (!currentUser.getHistory().containsKey(currentShow.getTitle())) {
                            if (currentShow.getGenres().contains(currentGenre)) { // daca are genul
                                return "PopularRecommendation result: " + currentShow.getTitle();
                            }
                        }
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Gaseste un video ce se gaseste cel mai des in lista de favorite a utilizatorilor.
     * Se aplica doar utilizatorilor premium.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * */
    public String favorite(final ActionInputData command, final List<UserInputData> users) {
        AuxRecommendation auxRec = new AuxRecommendation();
        if (auxRec.verifyBasic(command, users)) {
            return "FavoriteRecommendation cannot be applied!";
        }
        Map<String, Double> mapFavorite = new HashMap<>();
        for (UserInputData currentUser : users) { // parcurg lista de useri
            for (String currentMovie : currentUser.getFavoriteMovies()) { // parcurg lista de filme
                double one = 1;                                           // favorite
                if (!mapFavorite.containsKey(currentMovie)) { // daca nu este adaugat
                    mapFavorite.put(currentMovie, one);
                } else {
                    mapFavorite.put(currentMovie, mapFavorite.get(currentMovie) + 1);
                }
            }
        }

        AuxMethods aux = new AuxMethods();
        List<String> listFavorites = aux.sortDescAMap(mapFavorite);

        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) {
                for (String currentFavorite : listFavorites) { // parcurg lista de favorite
                    if (!currentUser.getHistory().containsKey(currentFavorite)) { // daca nu e vazut
                        return "FavoriteRecommendation result: " + currentFavorite;
                    }
                }
            }
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    /**
     * Returneaza o lista cu video-urile dintr-un gen nevizualizate de utilizator.
     * Se aplica doar utilizatorilor premium.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * @param movies este lista cu filme
     * @param serials este lista cu seriale
     * */
    public List<String> search(final ActionInputData command, final List<UserInputData> users,
                               final List<MovieInputData> movies,
                               final List<SerialInputData> serials) {
        List<String> listFinal = new ArrayList<>();
        AuxRecommendation auxRec = new AuxRecommendation();
        if (auxRec.verifyBasic(command, users)) {
            return null;
        }
        Map<String, Double> mapAll = new HashMap<>();
        for (MovieInputData currentMovie : movies) { // parcurg lista de filme
            if (currentMovie.getGenres().contains(command.getGenre())) {
                mapAll.put(currentMovie.getTitle(), currentMovie.averageMovie());
            }
        }
        for (SerialInputData currentSerial : serials) { // parcurg lista de seriale
            if (currentSerial.getGenres().contains(command.getGenre())) {
                mapAll.put(currentSerial.getTitle(), currentSerial.averageSerial());
            }
        }

        AuxMethods aux = new AuxMethods();
        List<String> listSearched = aux.sortAscAMap(mapAll);

        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) {
                for (String currentShow : listSearched) { // parcurg lista de video-uri cautate
                    if (!currentUser.getHistory().containsKey(currentShow)) { // daca nu este vazut
                        listFinal.add(currentShow);
                    }
                }
            }
        }
        if (listFinal.size() == Constants.ZERO) {
            listFinal = null;
        }
        return listFinal;
    }

    /**
     * Ajuta metoda anterioara search.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * @param movies este lista cu filme
     * @param serials este lista cu seriale
     * */
    public String auxSearch(final ActionInputData command, final List<UserInputData> users,
                            final List<MovieInputData> movies,
                            final List<SerialInputData> serials) {
        List<String> listSearches = search(command, users, movies, serials);
        if (listSearches == null) {
            return "SearchRecommendation cannot be applied!";
        } else {
            return "SearchRecommendation result: " + listSearches;
        }
    }
}
