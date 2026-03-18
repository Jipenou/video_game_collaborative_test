package model.videoGame;

import model.user.CTester;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent a test
 */
public class CTest {

    /** the tester that made this test */
    private CTester tester;

    /** the video game tested */
    private final CVideoGame videoGame;

    /** the platform used */
    private final CPlatform platform;

    /** the date of the test */
    private final LocalDate date;

    /** the text of this test */
    private final String text;

    /** the version of the game */
    private final String numVersion;

    /** condition of the test */
    private final String conditions;

    /** note per category */
    private final Map<ECategory, Integer> noteCategory;

    /** the number minimum of hours to played on the game to evaluate it */
    public static final int NUMBER_HOURS_MINIMUM_PLAYED_TO_TEST = 20;

    public CTest(LocalDate date, CTester tester, CVideoGame videoGame, CPlatform platform, String text, String numVersion, String conditions){
        this.tester = tester;
        this.videoGame = videoGame;
        this.platform = platform;
        this.text = text;
        this.numVersion = numVersion;
        this.conditions = conditions;
        this.date = date;

        noteCategory = new HashMap<>();

        tester.addTest(this);
        videoGame.addTest(this.platform, this);
    }

    /**
     * An enum to set category to evaluate for a test
     */
    public enum ECategory {
        INTERFACE,
        GAMEPLAY,
        OPTIMISATION,
        GRAPHICS,
        SOUND
    }

    /*
    ===================== ADD =========================
     */

    /**
     * add a score for a category
     * @param category the category concerned
     * @param score the score to add for the category
     */
    public void addCategoryScore(ECategory category, int score) {
        noteCategory.put(category, score);
    }

    /*
    ===================== GETTER =========================
     */

    /**
     *
     * @return the tester of the test
     */
    public CTester getTester() {
        return tester;
    }

    /**
     *
     * @return the video game tested
     */
    public CVideoGame getVideoGame() {
        return videoGame;
    }

    /**
     *
     * @return the platform played on
     */
    public CPlatform getPlatform() {
        return platform;
    }

    /**
     *
     * @return the date of the test
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     *
     * @return the text of the test
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @return the version of the game tested
     */
    public String getNumVersion() {
        return numVersion;
    }

    /**
     *
     * @return the conditions of the test
     */
    public String getConditions() {
        return conditions;
    }

    /**
     *
     * @return a map as <category, grade for the category>
     */
    public Map<ECategory, Integer> getNoteCategory() {
        return noteCategory;
    }

    /*
    ===================== SETTER =========================
     */

    /**
     * set a new tester for this test
     * @param tester the new tester
     */
    public void setTester(CTester tester) {
        this.tester = tester;
    }

    /*
    ===================== TO STRING =========================
     */

    public String toString(){
        return "[User : " + tester.getPseudo() + ", Jeux vidéo : " + videoGame.getName() +", Plateforme : " + this.platform + ", date : " + this.date + "]";
    }
}
