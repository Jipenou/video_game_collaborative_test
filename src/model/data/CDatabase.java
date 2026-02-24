package model.data;

import model.user.AUser;
import model.videoGame.CEvaluation;
import model.videoGame.CVideoGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent the database of the application
 */
public class CDatabase {

    /** the videos game as <name of the vg, the video game> */
    private final Map<String, CVideoGame> videoGames;

    /** the user registered as <pseudo, User> */
    private final Map<String, AUser> users;

    /** the evaluations */
    private final List<CEvaluation> evaluations;

    public CDatabase(){
        videoGames = new HashMap<>();
        users = new HashMap<>();
        evaluations = new ArrayList<>();
    }

    /**
     * Add a user to the database
     * @param user the user to add
     */
    public void addUser(AUser user){
        users.put(user.getPseudo(), user);
    }

    /**
     * Tell if a user is in the database based on the pseudo
     * @param pseudo the pseudo of the user to check
     * @return true if the user is present, else false
     */
    public boolean isUserPresent(String pseudo){
        return (users.containsKey(pseudo));
    }

    /**
     * Get a user by his pseudo
     * @param pseudo the pseudo of the user
     * @return the user if present or null
     */
    public AUser getUser(String pseudo){
        if(isUserPresent(pseudo)){
            return users.get(pseudo);
        }
        return null;
    }

    /**
     * add a video game to the database
     * @param vg the video game to add
     */
    public void addVideoGame(CVideoGame vg){
        videoGames.put(vg.getName(), vg);
    }

    /**
     *
     * @param name the name of the video game
     * @return true is the video game is in the database, else false
     */
    public boolean isVideoGameCreated(String name){
        return (videoGames.containsKey(name));
    }

    /**
     *
     * @param name the name of the video game to get
     * @return the video game
     */
    public CVideoGame getVideoGame(String name){
        if(isVideoGameCreated(name)){
            return videoGames.get(name);
        }
        return null;
    }

    /**
     *
     * @return all the video games present in the database
     */
    public List<CVideoGame> getAllVideoGames(){
        return new ArrayList<>(videoGames.values());
    }

    /**
     *
     * @return the map of video games
     */
    public Map<String, CVideoGame> getVideoGames() {
        return videoGames;
    }

    public Map<String, AUser> getUsers() {
        return users;
    }

    public List<CEvaluation> getEvaluations() {
        return evaluations;
    }

    /**
     * Add an evaluation into the database
     * @param eval the evaluation to add
     */
    public void addEvaluation(CEvaluation eval){
        evaluations.add(eval);
    }
}
