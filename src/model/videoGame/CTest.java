package model.videoGame;

import model.user.CTester;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /** List of strengths */
    private final List<String> strengths;

    /** List of weaknesses */
    private final List<String> weakness;

    /** condition of the test */
    private final String conditions;

    /** List of similar videoGames */
    private final List<CVideoGame> similarGames;

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

        strengths = new ArrayList<>();
        weakness = new ArrayList<>();
        similarGames = new ArrayList<>();

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

    public void addStrength(String strength){
        strengths.add(strength);
    }

    public void addWeakness(String weakness){
        this.weakness.add(weakness);
    }

    public void addSimilarGame(CVideoGame videoGame){
        similarGames.add(videoGame);
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

    public List<String> getStrengths() {
        return strengths;
    }

    public List<String> getWeakness() {
        return weakness;
    }

    public String getConditions() {
        return conditions;
    }

    public List<CVideoGame> getSimilarGames() {
        return similarGames;
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
