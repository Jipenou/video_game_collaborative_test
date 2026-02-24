package controller;

import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

public class CTestController {
    /** The main controller */
    private final CController controller;

    public CTestController(CController controller){
        this.controller = controller;
    }

    public void addNewTest(CTester tester, CVideoGame videoGame, CPlatform platform, String text,
                           String version, String conditions){
        CTest test = new CTest(tester, videoGame, platform, text, version, conditions);

        controller.getDatabase().addTest(test);
    }

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
