package controller;

import model.CSignalement;
import model.data.CDatabase;
import model.user.*;
import model.videoGame.CEvaluation;
import model.videoGame.CTest;
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

    public void promoteUser(AUser user) {
        CDatabase db = controller.getDatabase();

        if (user instanceof CAdmin) {
            return;
        }

        AUser userPromoted;

        if (user instanceof CTester tester) {
            CAdmin admin = new CAdmin(tester.getPseudo());
            copyPlayerData(tester, admin);
            copyTesterData(tester, admin);
            userPromoted = admin;

        } else if (user instanceof CPlayer player) {
            CTester newTester = new CTester(player.getPseudo());
            copyPlayerData(player, newTester);
            userPromoted = newTester;

        } else {
            return;
        }

        db.replaceUser(user, userPromoted);
    }

    private void copyPlayerData(CPlayer oldPlayer, CPlayer newPlayer) {
        newPlayer.setNbJeton(oldPlayer.getNbToken());
        for (CPlayerGame pg : oldPlayer.getGamePlayed()) {
            newPlayer.addGamePlayed(pg);
        }
        for (CEvaluation eval : oldPlayer.getEvaluations()) {
            newPlayer.addEvaluation(eval);
        }
    }

    private void copyTesterData(CTester oldTester, CTester newTester) {
        for (CTest test : oldTester.getTests()) {
            newTester.addTest(test);
        }
        for (CEvaluation eval : oldTester.getSignaledEvaluation()) {
            newTester.signalEvaluation(eval);
        }
    }
}