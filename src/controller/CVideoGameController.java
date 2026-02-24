package controller;

import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;
import view.videoGame.evaluation.CAddEvaluationView;

public class CVideoGameController {

    /** The main controller */
    private final CController controller;

    public CVideoGameController(CController controller){
        this.controller = controller;
    }

    public void addEvaluationFrame(CVideoGame videoGame){
        new CAddEvaluationView(controller.getEvaluationController(), videoGame).setVisible(true);
    }

    public void viewInfoGameFrame(CVideoGame videoGame){
        new CVideoGameInfoView(this, videoGame).setVisible(true);
    }

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
