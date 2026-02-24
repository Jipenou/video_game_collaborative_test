package controller;

import model.user.CPlayer;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;
import view.videoGame.evaluation.CAddEvaluationView;

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

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
