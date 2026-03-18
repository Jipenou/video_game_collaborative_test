package model.user;

import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represent a player
 */
public class CPlayer extends AUser{

    /** number of token of the user */
    protected int nbJeton;

    /** List of game played with their time and platform */
    protected List<CPlayerGame> gamePlayed;

    /**
     * A list of evaluation of a user
     */
    protected List<CEvaluation> evaluations;

    /**
     * The role of the player
     */
    public static final String ROLE = "PLAYER";

    /** the number of initial tokens for a player */
    public static final int NB_JETON_INITIAL = 3;

    public CPlayer(String pseudo) {
        super(pseudo);
        this.nbJeton = NB_JETON_INITIAL;
        gamePlayed = new ArrayList<>();
        evaluations = new ArrayList<>();
    }


    /*
    ===================== ADD =========================
     */

    /**
     * Add hours for a game owned
     * @param videoGame the video game
     * @param platform the platform played on
     * @param hoursToAdd the number of hours played
     */
    public void addHoursToGame(CVideoGame videoGame, CPlatform platform, float hoursToAdd){
        for(CPlayerGame playerGame : gamePlayed){
            if(videoGame == playerGame.getVideoGame() && platform == playerGame.getPlatform()){
                playerGame.addHours(hoursToAdd);
            }
        }
    }

    /**
     * add a new game to the collection of games of the player
     * @param playerGame the game to add
     */
    public void addGamePlayed(CPlayerGame playerGame){
        gamePlayed.add(playerGame);
    }

    /**
     * Add an evaluation for the player
     * @param evaluation the evaluation to add
     */
    public void addEvaluation(CEvaluation evaluation){
        evaluations.add(evaluation);
    }

    /**
     * Add token to a player
     * @param nbJeton the number of token to add
     */
    public void addJeton(int nbJeton){
        this.nbJeton += nbJeton;
    }

    /*
    ===================== REMOVE =========================
     */

    /**
     * remove an evaluation for the player
     * @param evaluation the evaluation to remove for the player
     */
    public void removeEvaluation(CEvaluation evaluation){
        evaluations.remove(evaluation);
    }

    /**
     * remove a game played for a user
     * @param playerGame the game owned to remove
     */
    public void removeGamePlayed(CPlayerGame playerGame){
        gamePlayed.remove(playerGame);
    }

    /**
     * Remove token to a player
     * @param nbJeton the number oof token to remove
     */
    public void removeJeton(int nbJeton){
        this.nbJeton -= nbJeton;
    }

    /*
    ===================== GETTER =========================
     */

    /**
     *
     * @return A list of video games owned
     */
    public List<CVideoGame> getGamesOwned(){
        List<CVideoGame> videoGames = new ArrayList<>();
        for(CPlayerGame playerGame : gamePlayed){
            if(!videoGames.contains(playerGame.getVideoGame())){
                videoGames.add(playerGame.getVideoGame());
            }
        }
        return videoGames;
    }

    /**
     * get a map of <Platform,hoursPlayed> for a video game owned
     * @param videoGame the video game
     * @return a map of <Platform,hoursPlayed> for a video game owned
     */
    public Map<CPlatform, Float> getHoursPlayedOnAGame(CVideoGame videoGame){
        Map<CPlatform, Float> hoursPlayed = new HashMap<>();
        for(CPlayerGame playerGame : gamePlayed){
            if(playerGame.getVideoGame() == videoGame){
                hoursPlayed.put(playerGame.getPlatform(), playerGame.getHoursPlayed());
            }
        }
        return hoursPlayed;
    }

    /**
     * return the total hours played for a game
     * @param videoGame the video game concerned
     * @return the total hours played for a game
     */
    public float getTotalHoursPlayedOnAGame(CVideoGame videoGame){
        float total = 0;
        for(CPlayerGame playerGame : gamePlayed){
            if(playerGame.getVideoGame() == videoGame){
                total += playerGame.getHoursPlayed();
            }
        }
        return total;
    }

    /**
     * Get all the platform owned of a game owned
     * @param videoGame the video game
     * @return all the platform owned of a game owned
     */
    public List<CPlatform> getPlatformsForGame(CVideoGame videoGame){
        List<CPlatform> platforms = new ArrayList<>();
        for(CPlayerGame playerGame : gamePlayed){
            if(videoGame == playerGame.getVideoGame()){
                platforms.add(playerGame.getPlatform());
            }
        }
        return platforms;
    }

    /**
     * Get all the platform owned and not tested for a game owned
     * @param videoGame the video game
     * @return all the platform owned and not tested for a game owned
     */
    public ArrayList<CPlatform> getPlatformsNotTestedForGame(CVideoGame videoGame) {
        List<CPlatform> platformsOwned = getPlatformsForGame(videoGame);
        ArrayList<CPlatform> platformsNotTested = new ArrayList<>(platformsOwned);
        platformsNotTested.removeAll(videoGame.getTests().keySet());
        return platformsNotTested;
    }

    /**
     * Get all the platform not owned for a game owned
     * @param videoGame the video game
     * @return all the platform not owned for a game owned
     */
    public ArrayList<CPlatform> getPlatformNotOwnedForGame(CVideoGame videoGame) {
        List<CPlatform> platformsOwned = getPlatformsForGame(videoGame);
        ArrayList<CPlatform> platformsNotOwned = new ArrayList<>();

        for (CPlatform platform : videoGame.getPlatforms()) {
            if (!platformsOwned.contains(platform)) {
                platformsNotOwned.add(platform);
            }
        }

        return platformsNotOwned;
    }

    /**
     *
     * @return the number total of hours played on all games for the user
     */
    public float getTotalHoursPlayed(){
        float total = 0;
        for(CPlayerGame playerGame : gamePlayed){
            total += playerGame.getHoursPlayed();
        }
        return total;
    }

    /**
     *
     * @return the evaluations of this user
     */
    public List<CEvaluation> getEvaluations() {
        return evaluations;
    }

    /**
     *
     * @return the number of evaluation
     */
    public int getNbEvaluation(){
        return evaluations.size();
    }

    /**
     *
     * @return a list of all the game owned for the user
     */
    public List<CPlayerGame> getGamePlayed() {
        return gamePlayed;
    }

    /**
     *
     * @return a list of owned game sorted by number of hours played reversed (decroissant)
     */
    public List<CPlayerGame> getGamePlayedSortedDecroissant() {
        return gamePlayed.stream()
                .sorted((a, b) -> Float.compare(b.getHoursPlayed(), a.getHoursPlayed()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @return the number of token of the user
     */
    public int getNbToken() {
        return nbJeton;
    }

    /*
    ===================== SETTER =========================
     */

    /**
     * set a new number of token
     * @param nbJeton the number of token to set
     */
    public void setNbJeton(int nbJeton) {
        this.nbJeton = nbJeton;
    }

    /*
    ===================== BOOLEAN =========================
     */

    /**
     *
     * @param videoGame the video game to check
     * @return true if the video game is owned, else false
     */
    public boolean isGameInCollection(CVideoGame videoGame){
        List<CVideoGame> gamesOwned = getGamesOwned();
        for(CVideoGame game : gamesOwned){
            if(game == videoGame){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the role of the player
     */
    @Override
    public String getRole() {
        return ROLE;
    }
}
