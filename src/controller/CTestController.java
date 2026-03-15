package controller;

import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;
import view.videoGame.test.CTestInfoView;
import view.videoGame.test.CTestView;

import java.time.LocalDate;

public class CTestController {
    /** The main controller */
    private final CController controller;

    public CTestController(CController controller){
        this.controller = controller;
    }

    /**
     * Add a new test
     * @param tester the tester
     * @param videoGame the video game
     * @param platform the platform
     * @param text the text
     * @param version the version
     * @param conditions the conditions of the test
     */
    public CTest addNewTest(CTester tester, CVideoGame videoGame, CPlatform platform, String text,
                           String version, String conditions){
        CTest test = new CTest(LocalDate.now(), tester, videoGame, platform, text, version, conditions);
        tester.addJeton(CTester.NB_JETONS_PER_TEST);
        videoGame.removeAllTokens();

        controller.getDatabase().addTest(test);
        return test;
    }

    public void displayAllTestFrame(){
        new CTestView(this).setVisible(true);
    }

    public void displayTest(CTest test){
        new CTestInfoView(this, test).setVisible(true);
    }

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
