package controller;

import model.user.CTester;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;
import view.videoGame.test.CTestInfoView;
import view.videoGame.test.CTestView;

import java.time.LocalDate;

/**
 * This class represent the test controller
 */
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

    /*
    ===================== FRAMES =========================
     */

    /**
     * Display all the tests present in the application
     */
    public void displayAllTestFrame(){
        new CTestView(this).setVisible(true);
    }

    /**
     * Display a test
     * @param test the test to display
     */
    public void displayTest(CTest test){
        new CTestInfoView(this, test).setVisible(true);
    }

    /*
    ===================== GETTER =========================
     */

    /**
     *
     * @return The main controller of the application
     */
    public CController getController() {
        return controller;
    }
}
