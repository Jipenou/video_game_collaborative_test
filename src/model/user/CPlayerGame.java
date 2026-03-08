package model.user;

import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

public class CPlayerGame {

    private CPlayer player;
    private final CVideoGame videoGame;
    private final CPlatform platform;
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

    public CPlayer getPlayer() {
        return player;
    }

    public void setPlayer(CPlayer player) {
        this.player = player;
    }
}
