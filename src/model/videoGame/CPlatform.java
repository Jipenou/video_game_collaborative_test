package model.videoGame;

import model.videoGame.rating.CPlayerRating;
import model.videoGame.rating.CTesterRating;

/**
 * This class represent a platform (PS3, Wii, etc..)
 * In the CSV file, the format is 1 line per platform
 */
public class CPlatform {

    /** The name of the platform */
    private final String name;

    /** the year of release */
    private final Integer releaseYear;

    /** the developer of the platform */
    private final String developer;

    /** the global sales of the platform for a game */
    private final Float globalSale;

    /** the ratings from the testers */
    private final CTesterRating testerRating;

    /** the ratings from the players */
    private final CPlayerRating playerRating;

    public CPlatform(String name, Integer releaseYear, String developer, Float globalSale,
                     CTesterRating testerRating, CPlayerRating playerRating){
        this.name = name;
        this.releaseYear = releaseYear;
        this.developer = developer;
        this.globalSale = globalSale;
        this.testerRating = testerRating;
        this.playerRating = playerRating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDeveloper() {
        return developer;
    }

    public float getGlobalSale() {
        return globalSale;
    }

    public CTesterRating getTesterRating() {
        return testerRating;
    }

    public CPlayerRating getPlayerRating() {
        return playerRating;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return this.name;
    }
}
