package model.videoGame;

import model.user.CPlayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent an evaluation
 */
public class CEvaluation {

    /** The player that made the evaluation */
    private final CPlayer player;

    /** The video game evaluated */
    private final CVideoGame videoGame;

    /** The platform used */
    private final CPlatform platform;

    /** date of the evaluation */
    private final LocalDate date;

    /** text of the evaluation */
    private final String text;

    /** the number of version of the game */
    private final String numVersion;

    /** the global grade for this evaluation */
    private final double globalScore;

    /** utilities for this evaluation */
    private int utiliteOui;
    private int utiliteNon;

    /** Map that contains the utilities votes as <player that vote, his vote> */
    private final Map<CPlayer, Integer> votants;

    public CEvaluation(CPlayer player, CVideoGame videoGame, CPlatform platform,
                      String text, String numVersion, double globalScore) {

        this.player = player;
        this.videoGame = videoGame;
        this.platform = platform;
        this.text = text;
        this.numVersion = numVersion;
        this.globalScore = globalScore;
        this.date = LocalDate.now();

        votants = new HashMap<>();

        player.addEvaluation(this);
        videoGame.addEvaluation(platform, this);
    }

    /**
     * Add a vote of the other player for this evaluation
     * @param player the player that vote
     * @param value the value of the rating (1 for yes, 0 for no)
     */
    public void addUtilities(CPlayer player, int value){
        if (votants.containsKey(player)){
            return;
        }

        votants.put(player, value);
        if (value == 1){
            utiliteOui++;
        }
        else{
            utiliteNon++;
        }
    }

    public CPlayer getPlayer() {
        return player;
    }

    public CVideoGame getVideoGame() {
        return videoGame;
    }

    public CPlatform getPlatform() {
        return platform;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getNumVersion() {
        return numVersion;
    }

    public double getGlobalScore() {
        return globalScore;
    }

    public int getUtiliteOui() {
        return utiliteOui;
    }

    public int getUtiliteNon() {
        return utiliteNon;
    }

    public Map<CPlayer, Integer> getVotants() {
        return votants;
    }

    public String toString(){
        return "[Platform : " + this.platform + ",date : " + this.date + ",version: " + this.numVersion + ", note: " + this.globalScore +
                ", description : " + this.text + "]";
    }
}
