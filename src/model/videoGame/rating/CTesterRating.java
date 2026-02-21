package model.videoGame.rating;

/**
 * This class represent the rating from testers for a game
 */
public class CTesterRating extends AUserRating{

    private static final int RATING_BASE = 100;

    public CTesterRating(int nbCritics, float averageScore){
        super(nbCritics, averageScore, RATING_BASE);
    }
}
