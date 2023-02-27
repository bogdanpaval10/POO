package query;

import fileio.UserInputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Contine implementarea pentru a implementa query-ul pentru utilizatori.
 */
public class Users {
    /**
     * Construieste un map cu numele userilor si rating-urile pe care le-au dat.
     * @param users este lista cu userii
     * */
    public Map<String, Double> numRatings(final List<UserInputData> users) {
        Map<String, Double> map = new HashMap<>();
        double zero = 0;
        for (UserInputData currentUser : users) { // parcurg lista de useri
            if (Double.compare(currentUser.getRatings().size(), zero) != 0) { // daca a dat rating
                map.put(currentUser.getUsername(), (double) currentUser.getRatings().size());
            }
        }
        return map;
    }
}
