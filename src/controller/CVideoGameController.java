package controller;

import model.videoGame.CEvaluation;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;
import view.videoGame.evaluation.CAddEvaluationView;
import view.videoGame.evaluation.CEvaluationView;
import view.videoGame.test.CAddTestView;
import view.videoGame.test.CTestView;

public class CVideoGameController {

    /** The main controller */
    private final CController controller;

    public CVideoGameController(CController controller){
        this.controller = controller;
    }

    public void addEvaluationFrame(CVideoGame videoGame){
        new CAddEvaluationView(controller.getEvaluationController(), videoGame).setVisible(true);
    }

    public void addTestFrame(CVideoGame videoGame){
        new CAddTestView(controller.getTestController(), videoGame).setVisible(true);
    }

    public void viewInfoGameFrame(CVideoGame videoGame){
        new CVideoGameInfoView(this, videoGame).setVisible(true);
    }

    public void displayEvaluation(CEvaluation evaluation){
        new CEvaluationView(controller.getEvaluationController(), evaluation).setVisible(true);
    }

    public void displayTest(CTest test){
        new CTestView(controller.getTestController(), test).setVisible(true);
    }

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
