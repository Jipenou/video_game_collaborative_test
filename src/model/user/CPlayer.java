package model.user;

import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent a player
 */
public class CPlayer extends AUser{

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

    public CPlayer(String pseudo) {
        super(pseudo);
        this.nbJeton = 3;
        gamePlayed = new ArrayList<>();
        evaluations = new ArrayList<>();
    }

    /**
     * Add a game in the user collection
     * @param videoGame the video game to add
     * @param platform the platform concerned
     */
    public void addGameToCollection(CPlayer player, CVideoGame videoGame, CPlatform platform){
        new CPlayerGame(player, videoGame, platform);
    }

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

    public void addGamePlayed(CPlayerGame playerGame){
        gamePlayed.add(playerGame);
    }

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
     * Add an evaluation for the player
     * @param evaluation the evaluation to add
     */
    public void addEvaluation(CEvaluation evaluation){
        evaluations.add(evaluation);
    }

    /**
     * @return the role of the player
     */
    @Override
    public String getRole() {
        return ROLE;
    }
}
