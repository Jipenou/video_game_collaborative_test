package controller;

import controller.load.CControlLoadEvaluation;
import controller.load.CControlLoadTest;
import controller.load.CControlLoadUser;
import controller.load.CControlLoadVideoGame;
import model.data.CDatabase;
import model.user.AUser;
import model.videoGame.CEvaluation;
import model.videoGame.CTest;

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

    /** The User controller */
    private final CUserController userController;

    /** The video game controller */
    private final CVideoGameController videoGameController;

    /** The evaluation controller */
    private final CEvaluationController evaluationController;

    private final CTestController testController;

    /** the database of the video games */
    private final CDatabase database;

    public CController(CDatabase database){
        this.database = database;

        this.userController = new CUserController(this);
        this.videoGameController = new CVideoGameController(this);
        this.evaluationController = new CEvaluationController(this);
        this.testController = new CTestController(this);

        this.loadVideoGameController = new CControlLoadVideoGame(this);
        this.loadUserController = new CControlLoadUser(database);
        this.loadEvaluationController = new CControlLoadEvaluation(database);
        this.loadTestController = new CControlLoadTest(database);

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
    }

    /**
     * Clear all csv data
     */
    public void clearAllCSV(){
        this.loadUserController.clearCSV();
        this.loadEvaluationController.clearCSV();
        this.loadTestController.clearCSV();
    }

    /**
     * Save all data to csv
     */
    public void saveAll() {
        saveAllUsers();
        saveAllEvaluations();
        saveAllTests();
    }

    /**
     * Save all users to csv
     */
    public void saveAllUsers(){
        for(AUser user : database.getUsers().values()){
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

    public CControlLoadVideoGame getLoadVideoGameController() {
        return loadVideoGameController;
    }

    public CTestController getTestController() {
        return testController;
    }

    public CControlLoadTest getLoadTestController() {
        return loadTestController;
    }
}
