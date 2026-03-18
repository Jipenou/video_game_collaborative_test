package controller;

import model.CSignalement;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;
import view.videoGame.evaluation.CEvaluationInfoView;
import view.videoGame.evaluation.CEvaluationView;

import java.time.LocalDateTime;

/**
 * This class represent the evaluation controller
 */
public class CEvaluationController {

    /** The main controller */
    private final CController controller;

    public CEvaluationController(CController controller){
        this.controller = controller;
    }

    /**
     * Add a new evaluation
     * @param player the player that made the evaluation
     * @param videoGame the video game associated
     * @param platform the platform concerned
     * @param text the text of the evaluation
     * @param version the version of the game
     * @param note the global grade of the evaluation
     */
    public void addNewEvaluation(CPlayer player, CVideoGame videoGame, CPlatform platform, String text,
                                 String version, double note){
        CEvaluation evaluation = new CEvaluation(LocalDateTime.now(), player, videoGame, platform, text, version, note);

        controller.getDatabase().addEvaluation(evaluation);
    }

    /**
     * Add a signalement on an evaluation
     * @param evaluation the evaluation concerned
     */
    public void addSignalement(CEvaluation evaluation){
        CTester tester = (CTester) getController().getCurrentUser();
        CSignalement signalement = new CSignalement(tester, evaluation);
        tester.signalEvaluation(evaluation);
        getController().getDatabase().addSignalement(signalement);
    }

    /**
     * Delete an evaluation (admin only)
     * @param evaluation the evaluation to delete
     */
    public void deleteEvaluation(CEvaluation evaluation){
        evaluation.getPlayer().removeEvaluation(evaluation);
        controller.getDatabase().removeSignalementForEval(evaluation);
        controller.getDatabase().removeEvaluation(evaluation);
        evaluation.getVideoGame().removeEvaluation(evaluation);

    }

    /*
    ===================== FRAMES =========================
     */

    /**
     * open the evaluation frame to see all evaluations
     */
    public void openEvaluationFrame(){
        new CEvaluationView(this).setVisible(true);
    }

    /**
     * display an evaluation
     * @param evaluation the evaluation to display
     */
    public void displayEvaluation(CEvaluation evaluation){
        new CEvaluationInfoView(this, evaluation).setVisible(true);
    }

    /*
    ===================== GETTER =========================
     */

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
