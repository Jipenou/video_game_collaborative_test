package controller;

import model.data.CDatabase;
import model.user.AUser;
import model.user.CPlayer;
import view.user.CLoginView;
import view.user.CProfileView;

import java.awt.*;

/**
 * This class represent the controller of the users
 */
public class CUserController {

    /** The main controller */
    private final CController controller;

    public CUserController(CController controller){
        this.controller = controller;
    }

    /**
     * login for a user
     * @param pseudo the pseudo of the user
     * @return true if the pseudo is correct, else false
     */
    public boolean login(String pseudo){
        CDatabase database = controller.getDatabase();
        if(database.isUserPresent(pseudo)){
            AUser user = database.getUser(pseudo);
            controller.setCurrentUser(user);
            return true;
        }
        return false;
    }

    /**
     * Register a player
     * @param pseudo the pseudo of the user
     */
    public boolean register(String pseudo){
        CDatabase database = controller.getDatabase();
        if(isPseudoAvailable(pseudo) && !pseudo.isEmpty()){
            CPlayer player = new CPlayer(pseudo);
            database.addUser(player);
            return true;
        }
        return false;
    }

    /**
     * Tell if a pseudo is available
     * @param pseudo the pseudo to check
     * @return true if the pseudo is available, else false
     */
    public boolean isPseudoAvailable(String pseudo){
        CDatabase database = controller.getDatabase();
        return (!database.isUserPresent(pseudo));
    }

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }

    public void desinscrire(AUser user){
        controller.getDatabase().removeAllForUser(user);

        for(Window window : Window.getWindows()){
            window.dispose();
        }

        new CLoginView(controller).setVisible(true);
    }

    public void deleteAccount(AUser user){
        controller.getDatabase().removeAllForUser(user);
    }

    public void blockAccount(AUser user){
        user.block();
    }

    public void unblockAccount(AUser user){
        user.unblock();
    }

    public void openProfile(AUser user){
        new CProfileView(this, user).setVisible(true);
    }
}
