package model.videoGame.rating;

/**
 * This abstract class represent a user rating
 */
public abstract class AUserRating {
    /** the number of critics of the rating */
    private final int nbCritics;

    /** the average score of the rating */
    private final float averageScore;

    private final int ratingBase;

    public AUserRating(int nbCritics, float averageScore, int ratingBase){
        this.nbCritics = nbCritics;
        this.averageScore = averageScore;
        this.ratingBase = ratingBase;
    }

    public int getNbCritics() {
        return nbCritics;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public int getRatingBase() {
        return ratingBase;
    }
}
