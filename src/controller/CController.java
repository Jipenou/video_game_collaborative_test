package controller;

import controller.load.CControlLoadUser;
import model.data.CDatabase;
import model.user.AUser;

/**
 * This class represent the main controller of the application
 */
public class CController {

    /** The current user in the application */
    private AUser currentUser;

    /** the controller for the loading and save of users */
    private final CControlLoadUser loadUserController;

    /**
     * The User controller
     */
    private final CUserController userController;

    /** the database of the video games */
    private final CDatabase database;

    public CController(CDatabase database){
        this.database = database;
        this.userController = new CUserController(this);
        this.loadUserController = new CControlLoadUser(database);
        this.loadUserController.loadUsers();
    }

    public CDatabase getDatabase() {
        return database;
    }

    public AUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AUser currentUser) {
        this.currentUser = currentUser;
    }

    public CUserController getUserController() {
        return userController;
    }

    public CControlLoadUser getLoadUserController() {
        return loadUserController;
    }
}
