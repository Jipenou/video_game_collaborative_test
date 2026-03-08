package model.videoGame;

import model.user.CPlayer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent an evaluation
 */
public class CEvaluation {

    /** The player that made the evaluation */
    private CPlayer player;

    /** The video game evaluated */
    private final CVideoGame videoGame;

    /** The platform used */
    private final CPlatform platform;

    /** date of the evaluation */
    private final LocalDateTime date;

    /** text of the evaluation */
    private final String text;

    /** the number of version of the game */
    private final String numVersion;

    /** the global grade for this evaluation */
    private final double globalScore;

    /** utilities for this evaluation */
    private int utiliteOui;
    private int utiliteNon;
    private int utiliteNeutre;

    /** used to give 1 token to the player that published to evaluation every 10 positiveCOmment */
    private int nbPositiveUtilities;

    private final List<CPlayer> hasVoted;

    private static final int VAL_EVAL_POSITIVE = 1;
    private static final int VAL_EVAL_NEGATIVE = -1;
    private static final int VAL_EVAL_NEUTRE = 0;

    /** Map that contains the utilities votes as <player that vote, his vote> */
    private final Map<CPlayer, Integer> votants;

    public CEvaluation(LocalDateTime date, CPlayer player, CVideoGame videoGame, CPlatform platform,
                      String text, String numVersion, double globalScore) {

        this.player = player;
        this.videoGame = videoGame;
        this.platform = platform;
        this.text = text;
        this.numVersion = numVersion;
        this.globalScore = globalScore;
        this.date = date;

        votants = new HashMap<>();
        hasVoted = new ArrayList<>();

        player.addEvaluation(this);
        videoGame.addEvaluation(this);
    }

    /**
     * Add a vote of the other player for this evaluation
     * @param player the player that vote
     * @param value the value of the rating (1 for yes, 0 for neutral, -1 for no)
     */
    public void addUtilities(CPlayer player, int value) {

        if (!hasVoted.contains(player)) {
            hasVoted.add(player);
        }

        Integer ancienVote = votants.get(player);

        if (ancienVote == null) {
            votants.put(player, value);
            incrementVote(value);
        } else if (ancienVote == value) {
            votants.remove(player);
            decrementVote(value);
        } else {
            decrementVote(ancienVote);
            incrementVote(value);
            votants.put(player, value);
        }

        calculNbEvaluationPositives();
    }

    private void incrementVote(int value) {
        if (value == VAL_EVAL_POSITIVE)       utiliteOui++;
        else if (value == VAL_EVAL_NEUTRE)  utiliteNeutre++;
        else                  utiliteNon++;
    }

    private void decrementVote(int value) {
        if (value == VAL_EVAL_POSITIVE)       utiliteOui--;
        else if (value == VAL_EVAL_NEUTRE)  utiliteNeutre--;
        else                  utiliteNon--;
    }

    private void calculNbEvaluationPositives(){
        System.out.println("Dans calcul");
        int votePositifs = (int) votants.values().stream().filter(v -> v == 1).count();
        int palier = votePositifs / 10;
        System.out.println("Palier : " + palier);

        if (palier > nbPositiveUtilities) {
            int jetonsAajouter = palier - nbPositiveUtilities;
            this.player.addJeton(jetonsAajouter);
            System.out.println("token ajouté");
            nbPositiveUtilities = palier;
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

    public LocalDateTime getDate() {
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

    public void setUtiliteOui(int utiliteOui) {
        this.utiliteOui = utiliteOui;
    }

    public void setUtiliteNon(int utiliteNon) {
        this.utiliteNon = utiliteNon;
    }

    public int getUtiliteNeutre() {
        return utiliteNeutre;
    }

    public String toStringSimple(){
        return "Evaluation de " + player.getPseudo() + ", jeu : " + videoGame.getName() + ", platforme : " + platform.getName();
    }

    public void setPlayer(CPlayer player) {
        this.player = player;
    }
}
