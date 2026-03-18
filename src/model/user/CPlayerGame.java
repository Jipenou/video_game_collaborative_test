package model.user;

import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

/**
 * This class represent the link for a user to own a game
 */
public class CPlayerGame {

    /** the player that own the game */
    private CPlayer player;

    /** the video game owned */
    private final CVideoGame videoGame;

    /** the platform concerned */
    private final CPlatform platform;

    /** the hours played on the video game */
    private float hoursPlayed;

    public CPlayerGame(CPlayer player, CVideoGame videoGame, CPlatform platform){
        this.player = player;
        this.videoGame = videoGame;
        this.platform = platform;
        this.hoursPlayed = 0;

        player.addGamePlayed(this);
    }

    public CPlayerGame(CPlayer player, CVideoGame videoGame, CPlatform platform, float hoursPlayed){
        this.player = player;
        this.videoGame = videoGame;
        this.platform = platform;
        this.hoursPlayed = hoursPlayed;

        player.addGamePlayed(this);
    }

    /*
    ===================== ADD =========================
     */

    /**
     * Add hours to the game
     * @param hoursPlayed the hours played
     */
    public void addHours(float hoursPlayed){
        this.hoursPlayed+=hoursPlayed;
    }

    /*
    ===================== GETTER =========================
     */

    /**
     *
     * @return the video game
     */
    public CVideoGame getVideoGame() {
        return videoGame;
    }

    /**
     *
     * @return the platform
     */
    public CPlatform getPlatform() {
        return platform;
    }

    /**
     *
     * @return the hours played
     */
    public float getHoursPlayed() {
        return hoursPlayed;
    }

    /**
     *
     * @return the player
     */
    public CPlayer getPlayer() {
        return player;
    }

    /*
    ===================== SETTER =========================
     */

    /**
     * Set a new player
     * @param player the nes player
     */
    public void setPlayer(CPlayer player) {
        this.player = player;
    }
}
