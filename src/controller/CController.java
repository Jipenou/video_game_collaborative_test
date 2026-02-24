package controller;

import com.sun.tools.javac.Main;
import controller.load.CControlLoadEvaluation;
import controller.load.CControlLoadUser;
import controller.load.CControlLoadVideoGame;
import model.data.CDatabase;
import model.user.AUser;

/**
 * This class represent the main controller of the application
 */
public class CController {

    /** The current user in the application */
    private AUser currentUser;

    private final CControlLoadVideoGame loadVideoGameController;

    /** the controller for the loading and save of users */
    private final CControlLoadUser loadUserController;

    private final CControlLoadEvaluation loadEvaluationController;

    /** The User controller */
    private final CUserController userController;

    /** The video game controller */
    private final CVideoGameController videoGameController;

    /** The evaluation controller */
    private final CEvaluationController evaluationController;

    /** the database of the video games */
    private final CDatabase database;

    public CController(CDatabase database){
        this.database = database;
        this.userController = new CUserController(this);
        this.videoGameController = new CVideoGameController(this);
        this.evaluationController = new CEvaluationController(this);

        this.loadVideoGameController = new CControlLoadVideoGame(this);
        loadVideoGameController.loadVideoGames();

        this.loadUserController = new CControlLoadUser(database);
        this.loadUserController.loadUsers();

        this.loadEvaluationController = new CControlLoadEvaluation(database);
        this.loadEvaluationController.loadEvaluations();
    }

    /**
     *
     * @return the database of the application
     */
    public CDatabase getDatabase() {
        return database;
    }

    /**
     *
     * @return the current user connected
     */
    public AUser getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the current user connected
     * @param currentUser the user
     */
    public void setCurrentUser(AUser currentUser) {
        this.currentUser = currentUser;
    }

    public CUserController getUserController() {
        return userController;
    }

    public CControlLoadUser getLoadUserController() {
        return loadUserController;
    }

    public CControlLoadEvaluation getLoadEvaluationController() {
        return loadEvaluationController;
    }

    public CVideoGameController getVideoGameController() {
        return videoGameController;
    }

    public CEvaluationController getEvaluationController() {
        return evaluationController;
    }
}
