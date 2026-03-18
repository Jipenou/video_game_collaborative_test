package controller;

import controller.load.*;
import model.CSignalement;
import model.data.CDatabase;
import model.user.AUser;
import model.user.CPlayer;
import model.user.CPlayerGame;
import model.videoGame.CEvaluation;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;
import view.CMainMenuView;
import view.user.CLoginView;

/**
 * This class represent the main controller of the application
 */
public class CController {

    /** The current user in the application */
    private AUser currentUser;

    /** the controller for the loading of videos games */
    private final CControlLoadVideoGame loadVideoGameController;

    /** the controller for the loading and save of users */
    private final CControlLoadUser loadUserController;

    /** the controller for the loading and save of evaluations */
    private final CControlLoadEvaluation loadEvaluationController;

    /** the controller for the loading and save of tests */
    private final CControlLoadTest loadTestController;

    /** the controller for the loading and save of the games owned by users */
    private final CControlLoadGameOwned loadGameOwnedController;

    /** the controller for the loading and save of signalements */
    private final CControlLoadSignalement loadSignalementController;

    /** the controller for the loading and save of tokens */
    private final CControlLoadTokenOnGame loadTokenOnGameController;

    /** The User controller */
    private final CUserController userController;

    /** The video game controller */
    private final CVideoGameController videoGameController;

    /** The evaluation controller */
    private final CEvaluationController evaluationController;

    /** The test controller */
    private final CTestController testController;

    /** The admin controller */
    private final CAdminController adminController;

    /** the database of the application */
    private final CDatabase database;

    public CController(CDatabase database){
        this.database = database;

        this.userController = new CUserController(this);
        this.videoGameController = new CVideoGameController(this);
        this.evaluationController = new CEvaluationController(this);
        this.testController = new CTestController(this);
        this.adminController = new CAdminController(this);

        this.loadVideoGameController = new CControlLoadVideoGame(database);
        this.loadUserController = new CControlLoadUser(database);
        this.loadEvaluationController = new CControlLoadEvaluation(database);
        this.loadTestController = new CControlLoadTest(database);
        this.loadGameOwnedController = new CControlLoadGameOwned(database);
        this.loadSignalementController = new CControlLoadSignalement(database);
        this.loadTokenOnGameController = new CControlLoadTokenOnGame(database);

        // load all data from the csv
        loadAll();
    }

    /**
     * Load all data from csv
     */
    public void loadAll(){
        this.loadVideoGameController.loadVideoGames();
        this.loadUserController.load();
        this.loadEvaluationController.load();
        this.loadEvaluationController.loadEvaluationsUsers();
        this.loadTestController.load();
        this.loadGameOwnedController.load();
        this.loadSignalementController.load();
        this.loadTokenOnGameController.loadTokenOnGame();
    }

    /**
     * Clear all csv data
     */
    public void clearAllCSV(){
        this.loadUserController.clearCSV();
        this.loadEvaluationController.clearCSV();
        this.loadTestController.clearCSV();
        this.loadGameOwnedController.clearCSV();
        this.loadSignalementController.clearCSV();
        this.loadTokenOnGameController.clearCSV();
    }

    /**
     * Save all data to csv
     */
    public void saveAll() {
        saveAllUsers();
        saveAllEvaluations();
        saveAllTests();
        saveAllPlayerGames();
        saveAllSignalements();
        saveAllTokenOnGames();
    }

    /**
     * Save all users to csv
     */
    public void saveAllUsers(){
        for(CPlayer user : database.getUsers().values()){
            loadUserController.save(user);
        }
    }

    /**
     * Save all evaluations to csv
     */
    public void saveAllEvaluations(){
        for(CEvaluation evaluation : database.getEvaluations()){
            loadEvaluationController.save(evaluation);
            loadEvaluationController.saveEvaluationUser(evaluation);
        }
    }

    /**
     * Save all tests to csv
     */
    public void saveAllTests(){
        for(CTest test : database.getTests()){
            loadTestController.save(test);
        }
    }

    /**
     * Save all games owned by users to csv
     */
    public void saveAllPlayerGames(){
        for(CPlayerGame playerGame : database.getPlayerGames()){
            loadGameOwnedController.save(playerGame);
        }
    }

    /**
     * Save all signalements to csv
     */
    public void saveAllSignalements(){
        for(CSignalement signalement : database.getSignaledEvaluations()){
            loadSignalementController.save(signalement);
        }
    }

    /**
     * Save all tokens on games to csv
     */
    public void saveAllTokenOnGames(){
        for(CVideoGame videoGame : database.getAllVideoGames()){
            for(CPlayer player : videoGame.getTokenOnTheGame().keySet()){
                loadTokenOnGameController.saveTokenOnGame(videoGame, player, videoGame.getTokenOnTheGame().get(player));
            }
        }
    }

    /**
     * logout for a user connected
     */
    public void logout(){
        this.setCurrentUser(null);
        new CLoginView(this).setVisible(true);
    }

    /*
    ===================== FRAME =========================
     */

    /**
     * Open the main frame of the application
     */
    public void openMainFrame(){
        new CMainMenuView(this).setVisible(true);
    }

    /*
    ===================== GETTER =========================
     */

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
     *
     * @return the user controller
     */
    public CUserController getUserController() {
        return userController;
    }

    /**
     *
     * @return the video game controller
     */
    public CVideoGameController getVideoGameController() {
        return videoGameController;
    }

    /**
     *
     * @return the evaluation controller
     */
    public CEvaluationController getEvaluationController() {
        return evaluationController;
    }

    /**
     *
     * @return the test controller
     */
    public CTestController getTestController() {
        return testController;
    }

    /**
     *
     * @return the admin controller
     */
    public CAdminController getAdminController() {
        return adminController;
    }


    /*
    ===================== SETTER =========================
     */

    /**
     * Set the current user connected
     * @param currentUser the user
     */
    public void setCurrentUser(AUser currentUser) {
        this.currentUser = currentUser;
    }
}
