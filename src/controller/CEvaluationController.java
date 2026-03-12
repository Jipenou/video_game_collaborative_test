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

public class CEvaluationController {

    /** The main controller */
    private final CController controller;

    public CEvaluationController(CController controller){
        this.controller = controller;
    }

    public void addNewEvaluation(CPlayer player, CVideoGame videoGame, CPlatform platform, String text,
                                 String version, double note){
        CEvaluation evaluation = new CEvaluation(LocalDateTime.now(), player, videoGame, platform, text, version, note);

        controller.getDatabase().addEvaluation(evaluation);
    }

    public void addSignalement(CEvaluation evaluation){
        CTester tester = (CTester) getController().getCurrentUser();
        CSignalement signalement = new CSignalement(tester, evaluation);
        tester.signalEvaluation(evaluation);
        getController().getDatabase().addSignalement(signalement);
    }

    public void deleteEvaluation(CEvaluation evaluation){
        evaluation.getPlayer().removeEvaluation(evaluation);
        controller.getDatabase().removeSignalementForEval(evaluation);
        controller.getDatabase().removeEvaluation(evaluation);
        evaluation.getVideoGame().removeEvaluation(evaluation);

    }

    public void openEvaluationFrame(){
        new CEvaluationView(this).setVisible(true);
    }

    public void displayEvaluation(CEvaluation evaluation){
        new CEvaluationInfoView(this, evaluation).setVisible(true);
    }

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
