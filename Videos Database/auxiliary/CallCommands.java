package auxiliary;

import command.Command;
import common.Constants;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import query.Actor;
import query.Users;
import query.Videos;
import recommendation.Recommendations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Se apeleaza din main, avand metode implementate pentru fiecare tip de actiuni.
 */
public class CallCommands {
    /**
     * Verifica ce tip de actiune este.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * @param movies este lista cu filme
     * @param serial este lista cu seriale
     * @param fileWriter pentru afisare
     * @throws IOException in case of exceptions to reading / writing
     * */
    public JSONObject command(final ActionInputData command, final List<UserInputData> users,
                              final List<MovieInputData> movies, final List<SerialInputData> serial,
                              final Writer fileWriter) throws IOException {
        Command thisCommand = new Command();
        if (command.getType().equals(Constants.FAVORITE)) {
            return fileWriter.writeFile(command.getActionId(), null,
                    thisCommand.addFavorite(command, users));
        }
        if (command.getType().equals(Constants.VIEW)) {
            return fileWriter.writeFile(command.getActionId(), null,
                    thisCommand.addView(command, users));
        }
        if (command.getType().equals(Constants.RATING)) {
            return fileWriter.writeFile(command.getActionId(), null,
                    thisCommand.addRating(command, users, serial, movies));
        }
        return null;
    }

    /**
     * Verifica ce tip de query este.
     * @param command este actiunea cu informatii
     * @param actors este lista cu actorii
     * @param users este lista cu userii
     * @param movies este lista cu filme
     * @param serials este lista cu seriale
     * @param fileWriter pentru afisare
     * @throws IOException in case of exceptions to reading / writing
     * */
    public JSONObject query(final ActionInputData command, final List<ActorInputData> actors,
                            final List<UserInputData> users, final List<MovieInputData> movies,
                            final List<SerialInputData> serials, final Writer fileWriter)
            throws IOException {
        AuxMethods aux = new AuxMethods();
        Map<String, Double> map;
        List<String> list = new ArrayList<>();
        if (command.getObjectType().equals(Constants.ACTORS)) {
            Actor thisActor = new Actor();
            if (command.getCriteria().equals(Constants.AVERAGE)) {
                map = thisActor.average(actors, movies, serials);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.AWARDS)) {
                List<String> listAwards = command.getFilters().get(Constants.LARGE_TEST);
                map = thisActor.award(actors, listAwards);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.FILTER_DESCRIPTIONS)) {
                List<String> listWords = command.getFilters().get(Constants.SINGLE_TEST);
                list = thisActor.filter(command, actors, listWords);
            }
        }
        if (command.getObjectType().equals(Constants.MOVIES)) {
            List<String> listYears = command.getFilters().get(Constants.ZERO);
            List<String> listGenre = command.getFilters().get(Constants.ONE);
            Videos thisVideo = new Videos();
            if (command.getCriteria().equals(Constants.RATINGS)) {
                map = thisVideo.ratingMovies(listYears, listGenre, movies);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.FAVORITE)) {
                map = thisVideo.favoriteMovie(users, listYears, listGenre, movies);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.LONGEST)) {
                map = thisVideo.longestMovie(listYears, listGenre, movies);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.MOST_VIEWED)) {
                map = thisVideo.mostViewedMovie(users, listYears, listGenre, movies);
                list = aux.sort(command, map);
            }
        }
        if (command.getObjectType().equals(Constants.SHOWS)) {
            List<String> listYears = command.getFilters().get(Constants.ZERO);
            List<String> listGenre = command.getFilters().get(Constants.ONE);
            Videos thisVideo = new Videos();
            if (command.getCriteria().equals(Constants.RATINGS)) {
                map = thisVideo.ratingSerials(listYears, listGenre, serials);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.FAVORITE)) {
                map = thisVideo.favoriteSerial(users, listYears, listGenre, serials);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.LONGEST)) {
                map = thisVideo.longestSerial(listYears, listGenre, serials);
                list = aux.sort(command, map);
            }
            if (command.getCriteria().equals(Constants.MOST_VIEWED)) {
                map = thisVideo.mostViewedSerial(users, listYears, listGenre, serials);
                list = aux.sort(command, map);
            }
        }
        if (command.getObjectType().equals(Constants.USERS)) {
            if (command.getCriteria().equals(Constants.NUM_RATINGS)) {
                Users thisUser = new Users();
                map = thisUser.numRatings(users);
                list = aux.sort(command, map);
            }
        }
        return fileWriter.writeFile(command.getActionId(), null, "Query result: "
                + list);
    }

    /**
     * Verifica ce tip de recomandare este.
     * @param command este actiunea cu informatii
     * @param user este lista cu userii
     * @param movies este lista cu filme
     * @param serials este lista cu seriale
     * @param fileWriter pentru afisare
     * @throws IOException in case of exceptions to reading / writing
     * */
    public JSONObject recommendations(final ActionInputData command, final List<UserInputData> user,
                                      final List<MovieInputData> movies,
                                      final List<SerialInputData> serials,
                                      final Writer fileWriter) throws IOException {
        Recommendations thisRecommendation = new Recommendations();
        List<ShowInput> listAllFilms = thisRecommendation.allFilms(movies, serials);
        String result = null;
        if (command.getType().equals(Constants.STANDARD)) {
            result = thisRecommendation.standard(command, user, listAllFilms);
        }
        if (command.getType().equals(Constants.BEST)) {
            result = thisRecommendation.bestUnseen(command, user, movies, serials);
        }
        if (command.getType().equals(Constants.POPULAR)) {
            result = thisRecommendation.popular(command, user, listAllFilms);
        }
        if (command.getType().equals(Constants.FAVORITE)) {
            result = thisRecommendation.favorite(command, user);
        }
        if (command.getType().equals(Constants.SEARCH)) {
            result = thisRecommendation.auxSearch(command, user, movies, serials);
        }
        return fileWriter.writeFile(command.getActionId(), null, result);
    }
}
