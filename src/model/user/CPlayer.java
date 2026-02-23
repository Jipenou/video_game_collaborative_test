package model.user;

import model.videoGame.CEvaluation;
import model.videoGame.CVideoGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPlayer extends AUser{

    /**
     * A map that define the playtime of a user as <video game, hours of playtime
     */
    protected Map<CVideoGame, Integer> playTime;

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
        playTime = new HashMap<>();
        evaluations = new ArrayList<>();
    }

    /**
     * Add a game to the playtime map
     * @param vg the video game to add
     */
    public void addGame(CVideoGame vg) {
        playTime.putIfAbsent(vg, 0);
    }

    /**
     * Add playtime for a game
     * @param vg the video game concerned
     * @param hours the number of hours played on a video game
     */
    public void addPlayTime(CVideoGame vg, int hours) {
        playTime.put(vg, playTime.getOrDefault(vg, 0) + hours);
    }

    /**
     *
     * @return the play time map
     */
    public Map<CVideoGame, Integer> getPlayTime() {
        return playTime;
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
     * @return the total playtime of a the player
     */
    public int getTotalPlayTime(){
        int total = 0;
        for (int playtime : playTime.values()) {
            total += playtime;
        }
        return total;
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
