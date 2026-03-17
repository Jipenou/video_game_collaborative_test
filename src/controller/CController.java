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

    private final CControlLoadVideoGame loadVideoGameController;

    /** the controller for the loading and save of users */
    private final CControlLoadUser loadUserController;

    private final CControlLoadEvaluation loadEvaluationController;

    private final CControlLoadTest loadTestController;

    private final CControlLoadGameOwned loadGameOwnedController;

    private final CControlLoadSignalement loadSignalementController;

    private final CControlLoadTokenOnGame loadTokenOnGameController;

    /** The User controller */
    private final CUserController userController;

    /** The video game controller */
    private final CVideoGameController videoGameController;

    /** The evaluation controller */
    private final CEvaluationController evaluationController;

    private final CTestController testController;

    private final CAdminController adminController;

    /** the database of the video games */
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
        this.loadUserController.loadUsers();
        this.loadEvaluationController.loadEvaluations();
        this.loadEvaluationController.loadEvaluationsUsers();
        this.loadTestController.loadTests();
        this.loadGameOwnedController.loadPlayerGame();
        this.loadSignalementController.loadSignalement();
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
            loadUserController.saveUser(user);
        }
    }

    /**
     * Save all evaluations to csv
     */
    public void saveAllEvaluations(){
        for(CEvaluation evaluation : database.getEvaluations()){
            loadEvaluationController.saveEvaluation(evaluation);
            loadEvaluationController.saveEvaluationUser(evaluation);
        }
    }

    /**
     * Save all tests to csv
     */
    public void saveAllTests(){
        for(CTest test : database.getTests()){
            loadTestController.saveTest(test);
        }
    }

    public void saveAllPlayerGames(){
        for(CPlayerGame playerGame : database.getPlayerGames()){
            loadGameOwnedController.savePlayerGame(playerGame);
        }
    }

    public void saveAllSignalements(){
        for(CSignalement signalement : database.getSignaledEvaluations()){
            loadSignalementController.saveSignalement(signalement);
        }
    }

    public void saveAllTokenOnGames(){
        for(CVideoGame videoGame : database.getAllVideoGames()){
            for(CPlayer player : videoGame.getTokenOnTheGame().keySet()){
                loadTokenOnGameController.saveTokenOnGame(videoGame, player, videoGame.getTokenOnTheGame().get(player));
            }
        }
    }

    public void logout(){
        this.setCurrentUser(null);
        new CLoginView(this).setVisible(true);
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

    public void openMainFrame(){
        new CMainMenuView(this).setVisible(true);
    }

    public CVideoGameController getVideoGameController() {
        return videoGameController;
    }

    public CEvaluationController getEvaluationController() {
        return evaluationController;
    }

    public CTestController getTestController() {
        return testController;
    }

    public CAdminController getAdminController() {
        return adminController;
    }
}
