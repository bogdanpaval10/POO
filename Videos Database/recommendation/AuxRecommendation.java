package recommendation;

import fileio.ActionInputData;
import fileio.UserInputData;

import java.util.List;

/**
 * Sunt implementate metode auxiliare ce ajuta in rezolvarea recomandarilor.
 */
public class AuxRecommendation {
    private String name;
    private double rating;

    /**
     * Seteaza numele.
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * Seteaza rating-ul.
     */
    public void setRating(final double rating) {
        this.rating = rating;
    }
    /**
     * Returneaza numele.
     */
    public String getName() {
        return name;
    }
    /**
     * returneaza rating-ul.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Interschimba valorile.
     * @param x obiect 1
     * @param y obiect 2
     * */
    public void swap(final AuxRecommendation x, final AuxRecommendation y) {
        String copyName = x.getName();
        double copyRating = x.getRating();
        x.setName(y.getName());
        x.setRating(y.getRating());
        y.setName(copyName);
        y.setRating(copyRating);
    }
    /**
     * Verifica daca utilizatorul are varianta premium.
     * @param command este actiunea cu informatii
     * @param users este lista cu userii
     * */
    public boolean verifyBasic(final ActionInputData command, final List<UserInputData> users) {
        for (UserInputData currentUser : users) {
            if (currentUser.getUsername().equals(command.getUsername())) {
                if (currentUser.getSubscriptionType().equals("BASIC")) {
                    return true;
                }
            }
        }
        return false;
    }
}
