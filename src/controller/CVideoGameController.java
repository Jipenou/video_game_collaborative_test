package controller;

import model.data.CDatabase;
import model.user.CPlayer;
import model.user.CPlayerGame;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;
import view.videoGame.CAddGameView;
import view.videoGame.CAddHoursToGameView;
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

    public void addEvaluationFrame(CVideoGameInfoView gameInfoView, CVideoGame videoGame){
        new CAddEvaluationView(controller.getEvaluationController(), videoGame, gameInfoView).setVisible(true);
    }

    public void addTestFrame(CVideoGameInfoView gameInfoView, CVideoGame videoGame){
        new CAddTestView(controller.getTestController(), videoGame, gameInfoView).setVisible(true);
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

    public void displayAddGameFrame(CVideoGameInfoView gameInfoView, CVideoGame videoGame){
        new CAddGameView(this, videoGame,gameInfoView).setVisible(true);
    }

    public void addHoursFrame(CVideoGameInfoView gameInfoView, CVideoGame videoGame){
        new CAddHoursToGameView(this, videoGame, gameInfoView).setVisible(true);
    }

    public void addHoursToGame(CVideoGame videoGame, CPlatform platform, int hours){
        CPlayer player = (CPlayer) controller.getCurrentUser();

        player.addHoursToGame(videoGame, platform, hours);
    }

    public void addGameToCollection(CVideoGame videoGame, CPlatform platform){
        CPlayer player = (CPlayer) controller.getCurrentUser();

        CPlayerGame playerGame = new CPlayerGame(player, videoGame, platform);
        controller.getDatabase().addPlayerGame(playerGame);
    }

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
