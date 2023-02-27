package query;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contine implementarile metodelor folosite pentru a implementa query-urile pentru actori.
 */
public class Actor {
    /**
     * Construieste un map cu media rating-urilor filmelor in care actorii au jucat.
     * @param actors este lista cu actorii
     * @param movies este lista cu filme
     * @param serials este lista cu seriale
     * */
    public Map<String, Double> average(final List<ActorInputData> actors,
                                       final List<MovieInputData> movies,
                                       final List<SerialInputData> serials) {
        Map<String, Double> mapActors = new HashMap<>();
        for (ActorInputData currentActor : actors) {
            double totalMovie = 0;
            double totalSerial = 0;
            int nrMovies = 0;
            int nrSerials = 0;
            for (String currentSeen : currentActor.getFilmography()) { // parcurg lista de filme
                for (MovieInputData currentMovie : movies) { // in care a jucat actorul
                    if (currentSeen.equals(currentMovie.getTitle())) { // daca a jucat in film
                        if (currentMovie.averageMovie() != 0) { // daca rating != 0
                            totalMovie += currentMovie.averageMovie();
                            nrMovies++;
                        }
                    }
                }
                if (nrMovies != 0) { // adaug in lista nume si media
                    mapActors.put(currentActor.getName(), totalMovie / nrMovies);
                }
                for (SerialInputData currentSerial : serials) {
                    if (currentSeen.equals(currentSerial.getTitle())) { // daca a jucat in serial
                        if (currentSerial.averageSerial() != 0) { // daca rating != 0
                            totalSerial += currentSerial.averageSerial();
                            nrSerials++;
                        }
                    }
                }
                if (nrSerials != 0) { // adaug in lista nume si media
                    mapActors.put(currentActor.getName(), totalSerial / nrSerials);
                }
            }
        }
        return mapActors;
    }

    /**
     * Construieste un map cu numele actorilor si numarul de premii castigate.
     * @param actors este lista cu actorii
     * @param listAwards este lista cu premiile ce trebuie gasite
     * */
    public Map<String, Double> award(final List<ActorInputData> actors,
                                     final List<String> listAwards) {
        Map<String, Double> mapActorsAwards = new HashMap<>();
        for (ActorInputData currentActor : actors) { // parcurg lista de actori
            int nrAwards = 0;
            double total = 0;
            double totalCommon = 0;
            for (String currentAward : listAwards) { // parcurg lista premii cerute
                if (currentActor.getAwards().containsKey(ActorsAwards.valueOf(currentAward))) {
                    nrAwards++; // daca are acel premiu
                    totalCommon += currentActor.getAwards().get(ActorsAwards.valueOf(currentAward));
                } else {
                    nrAwards = -1;
                    break;
                }

            }
            if (nrAwards > 0) { // daca are toate premiile din lista, aduna toate premiile actorului
                for (Map.Entry<ActorsAwards, Integer> entry : currentActor.getAwards().entrySet()) {
                    total += entry.getValue();
                }
                mapActorsAwards.put(currentActor.getName(), total);
            }
            if (nrAwards == listAwards.size() && total == 0) {
                mapActorsAwards.put(currentActor.getName(), totalCommon);
            }
        }
        return mapActorsAwards;
    }

    /**
     * Construieste o lista cu numele actorilor ce au in descriere anumite cuvinte date.
     * @param command este actiunea cu informatii
     * @param actors este lista cu actorii
     * @param listWords este lista cu cuvintele ce trebuie sa fie gasite in descrierea actorilor
     * */
    public List<String> filter(final ActionInputData command, final List<ActorInputData> actors,
                                final List<String> listWords) {
        List<String> listActorsWords = new ArrayList<>();
        for (ActorInputData currentActor : actors) { // parcurg lista de actori
            String description = currentActor.getCareerDescription().toLowerCase();
            List<String> listWordsDesc = Arrays.asList(description.split("[ -.,!?:;]"));
            if (listWordsDesc.containsAll(listWords)) { // daca in descriere apar toate cuvintele
                listActorsWords.add(currentActor.getName());
            }
        }
        if (command.getSortType().equals("asc")) { // sortare alfabetica a listei cu actori
            Collections.sort(listActorsWords);
        } else {
            listActorsWords.sort(Collections.reverseOrder());
        }
        return listActorsWords;
    }
}
