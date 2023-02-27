package command;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;


import java.util.List;

/**
 * Contine implementarile metodelor folosite pentru a implementa comenzile pe care le poate
 * avea un utilizator.
 */
public class Command {
    /**
     * Adauga un video in lista de favorite.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * */
    public String addFavorite(final ActionInputData command, final List<UserInputData> users) {
        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) { // se cauta userul cerut
                if (currentUser.getFavoriteMovies().contains(command.getTitle())) {
                    return "error -> " + command.getTitle() + " is already in favourite list";
                } // se verifica daca a vazut video-ul
                if (!currentUser.getHistory().containsKey(command.getTitle())) {
                    return "error -> " + command.getTitle() + " is not seen";
                }
                currentUser.getFavoriteMovies().add(command.getTitle());
                return "success -> " + command.getTitle() + " was added as favourite";
            }
        }
        return null;
    }

    /**
     * Se vizualizeaza un video de catre utilizator.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * */
    public String addView(final ActionInputData command, final List<UserInputData> users) {
        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) { // se cauta userul cerut
                int views = 1;
                if (currentUser.getHistory().containsKey(command.getTitle())) { // se verifica daca
                    views += currentUser.getHistory().get(command.getTitle()); // a vazut videoul
                } // se adauga in map nume video + nr vizualizari
                currentUser.getHistory().put(command.getTitle(), views);
                return "success -> " + command.getTitle() + " was viewed with total views of "
                        + views;
            }
        }
        return null;
    }

    /**
     * Pune rating unui video deja vizualizat.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * @param serials este lista cu seriale
     * @param movies este lista cu filme
     * */
    public String addRating(final ActionInputData command, final List<UserInputData> users,
                             final List<SerialInputData> serials,
                             final List<MovieInputData> movies) {
        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (currentUser.getUsername().equals(command.getUsername())) { // se cauta userul cerut
                if (!currentUser.getHistory().containsKey(command.getTitle())) { // se verifica daca
                    return "error -> " + command.getTitle() + " is not seen";    // a vazut video-ul
                }
                if (command.getSeasonNumber() == 0) { // este film
                    if (currentUser.getRatings().containsKey(command.getTitle())) {
                        return "error -> " + command.getTitle() + " has been already rated";
                    } // adaug in map nume si nota
                    currentUser.getRatings().put(command.getTitle(), command.getGrade());
                    for (MovieInputData currentMovie : movies) { // daca este filmul cerut
                        if (currentMovie.getTitle().equals(command.getTitle())) {
                            double grade = command.getGrade();
                            currentMovie.getRatingsBy().put(command.getUsername(), grade);
                            return "success -> " + command.getTitle() + " was rated with "
                                    + grade + " by " + command.getUsername();
                        }
                    }
                } else { // este serial
                    int nrSeason = command.getSeasonNumber();
                    if (currentUser.getRatings().containsKey(command.getTitle() + nrSeason)) {
                        return "error -> " + command.getTitle() + " has been already rated";
                    } // adaug in map nume + nr sezon si nota
                    currentUser.getRatings().put(command.getTitle() + nrSeason, command.getGrade());
                    for (SerialInputData currentSerial : serials) {                // daca este
                        if (currentSerial.getTitle().equals(command.getTitle())) { // serialul cerut
                            Season currentSeason = currentSerial.getSeasons().get(nrSeason - 1);
                            currentSeason.getRatings().add(command.getGrade());
                            return "success -> " + command.getTitle() + " was rated with "
                                    + command.getGrade() + " by " + command.getUsername();
                        }
                    }
                }
            }
        }
        return null;
    }
}
