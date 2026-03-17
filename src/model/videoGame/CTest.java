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

    public enum ECategory {
        INTERFACE,
        GAMEPLAY,
        OPTIMISATION,
        GRAPHICS,
        SOUND
    }

    public void addCategoryScore(ECategory category, int score) {
        noteCategory.put(category, score);
    }

    public CTester getTester() {
        return tester;
    }

    public CVideoGame getVideoGame() {
        return videoGame;
    }

    public CPlatform getPlatform() {
        return platform;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getNumVersion() {
        return numVersion;
    }

    public String getConditions() {
        return conditions;
    }

    public Map<ECategory, Integer> getNoteCategory() {
        return noteCategory;
    }

    public void setTester(CTester tester) {
        this.tester = tester;
    }

    public String toString(){
        return "[User : " + tester.getPseudo() + ", Jeux vidéo : " + videoGame.getName() +", Plateforme : " + this.platform + ", date : " + this.date + "]";
    }
}
