package controller;

import model.CSignalement;
import view.administration.*;

public class CAdminController {

    /** The main controller */
    private final CController controller;

    public CAdminController(CController controller){
        this.controller = controller;
    }

    public void openAdministrationFrame(){
        new CAdministrationView(this).setVisible(true);
    }

    public void openUsersFrame(){
        new CAllUsersView(this).setVisible(true);
    }

    public void openSignalementFrame(){
        new CSignalementView(this).setVisible(true);
    }

    public void viewInfoSignalement(CSignalement signalement){
        new CSignalementInfoView(this, signalement).setVisible(true);
    }

    public void openBlockedUsersFrame(){
        new CUsersBlockedView(this).setVisible(true);
    }

    public CController getController() {
        return controller;
    }
}