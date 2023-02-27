package auxiliary;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Sunt implementate metode auxiliare ce ajuta in rezolvare, cum ar fi cele de sortare.
 */
public class AuxMethods {
    /**
     * Sorteaza un map in functie de sortType
     * @param command este actiunea cu informatii
     * @param map este map-ul ce va fi sortat pe baza valorilor
     */
    public List<String> sort(final ActionInputData command, final Map<String, Double> map) {
        int n = command.getNumber(); // sorteaza map-ul alfabetic (asc sau desc), apoi sorteaza
        LinkedHashMap<String, Double> finalMap = new LinkedHashMap<>(); // dupa valoare double
        List<String> list = new ArrayList<>();
        if (command.getSortType().equals("asc")) {
            Map<String, Double> sortedMap = new TreeMap<>(map);
            sortedMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEachOrdered(x -> finalMap.put(x.getKey(), x.getValue()));
        } else {
            Map<String, Double> reverseSortedMap = new TreeMap<>(Collections.reverseOrder());
            reverseSortedMap.putAll(map);
            reverseSortedMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> finalMap.put(x.getKey(), x.getValue()));
        }
        for (Map.Entry<String, Double> entry : finalMap.entrySet()) {
            if (n == 0) {
                break;
            }
            list.add(entry.getKey());
            n--;
        }
        return list;
    }

    /**
     * Sorteaza un map crescator in functie de chei, apoi in functie de valori
     * @param map este map-ul ce va fi sortat
     */
    public List<String> sortAscAMap(final Map<String, Double> map) { // sorteaza map-ul alfabetic,
        LinkedHashMap<String, Double> finalMap = new LinkedHashMap<>(); // apoi asc dupa valoare
        List<String> list = new ArrayList<>();
        Map<String, Double> sortedMap = new TreeMap<>(map);
        sortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> finalMap.put(x.getKey(), x.getValue()));

        for (Map.Entry<String, Double> entry : finalMap.entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    /**
     * Sorteaza un map descrescator in functie de valori
     * @param map este map-ul ce va fi sortat
     */
    public List<String> sortDescAMap(final Map<String, Double> map) { // sorteaza mapul descrescator
        LinkedHashMap<String, Double> finalMap = new LinkedHashMap<>(); // dupa valoarea double
        List<String> list = new ArrayList<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> finalMap.put(x.getKey(), x.getValue()));

        for (Map.Entry<String, Double> entry : finalMap.entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    /**
     * Numara cate filme au aparut in anii din lista data.
     * @param currentMovie este filmul curent
     * @param listYears este lista cu anii de selectie
     * */
    public int nrYearsMovie(final MovieInputData currentMovie, final List<String> listYears) {
        int hasYear = 0;
        for (String currentYear : listYears) { // verific daca are anul cerut
            if (currentYear != null) {
                if (currentMovie.getYear() == Integer.parseInt(currentYear)) {
                    hasYear++;
                }
            }
        }
        return hasYear;
    }

    /**
     * Numara cate filme au genul din lista data.
     * @param currentMovie este filmul curent
     * @param listGenre este lista cu genurile de selectie
     * */
    public int nrGenresMovie(final MovieInputData currentMovie, final List<String> listGenre) {
        int hasGenre = 0;
        for (String currentGenre : listGenre) { // verific daca are genul cerut
            if (currentMovie.getGenres().contains(currentGenre)) {
                hasGenre++;
            }
        }
        return hasGenre;
    }

    /**
     * Numara cate seriale au aparut in anii din lista data.
     * @param currentSerial este serialul curent
     * @param listYears este lista cu anii de selectie
     * */
    public int nrYearsSerial(final SerialInputData currentSerial, final List<String> listYears) {
        int hasYear = 0;
        for (String currentYear : listYears) { // verific daca are anul cerut
            if (currentYear != null) {
                if (currentSerial.getYear() == Integer.parseInt(currentYear)) {
                    hasYear++;
                }
            }
        }
        return hasYear;
    }

    /**
     * Numara cate seriale au genul din lista data.
     * @param currentSerial este serialul curent
     * @param listGenre este lista cu genurile de selectie
     * */
    public int nrGenresSerial(final SerialInputData currentSerial, final List<String> listGenre) {
        int hasGenre = 0;
        for (String currentGenre : listGenre) { // verific daca are genul cerut
            if (currentGenre != null && (currentSerial.getGenres().contains(currentGenre))) {
                hasGenre++;
            }
        }
        return hasGenre;
    }
}
