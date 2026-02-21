package model.data;

import model.videoGame.CVideoGame;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represent the database of the application
 */
public class CDatabase {
    /** the videos games as <name of the vg, the video game> */
    private final Map<String, CVideoGame> videoGames;

    public CDatabase(){
        videoGames = new HashMap<>();
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
        return (videoGames.get(name) != null);
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
     * @return the map of video games
     */
    public Map<String, CVideoGame> getVideoGames() {
        return videoGames;
    }
}
