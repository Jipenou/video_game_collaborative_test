package model.user;

import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

public class CPlayerGame {

    private final CVideoGame videoGame;
    private final CPlatform platform;
    private float hoursPlayed;

    public CPlayerGame(CVideoGame videoGame, CPlatform platform){
        this.videoGame = videoGame;
        this.platform = platform;
        hoursPlayed = 0;
    }

    /** add hours to a game */
    public void addHours(float hoursPlayed){
        this.hoursPlayed+=hoursPlayed;
    }

    public CVideoGame getVideoGame() {
        return videoGame;
    }

    public CPlatform getPlatform() {
        return platform;
    }

    public float getHoursPlayed() {
        return hoursPlayed;
    }
}
