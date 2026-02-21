package model.videoGame.rating;

/**
 * This class represent the player rating for a game
 */
public class CPlayerRating extends AUserRating{

    private static final int RATING_BASE = 10;

    public CPlayerRating(int nbCritics, float averageScore){
        super(nbCritics, averageScore, RATING_BASE);
    }
}
