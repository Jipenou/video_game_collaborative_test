package model.videoGame;

import model.user.CPlayer;
import model.user.CPlayerGame;

import java.util.*;

/**
 * This class represent a video game
 */
public class CVideoGame {

    /** name of the video game */
    private final String name;

    /** the category of the video game */
    private final String category;

    /** the editor of the video game */
    private final String editor;

    /** the overall rating of the video game */
    private final Character rating;

    /** All the platforms that handle the video game */
    private final ArrayList<CPlatform> platforms;

    /** A map that contains the evaluation by platform as <platform, evaluation>*/
    private final Map<CPlatform, CEvaluation> evaluations;

    /** A map that contains the evaluation by platform as <platform, test>*/
    private final Map<CPlatform, CTest> tests;

    public CVideoGame(String name, String category, String editor, Character rating){
        this.name = (name != null)? name : "undefined";
        this.category = (category != null) ? category : "undefined";
        this.editor = (editor != null) ? editor : "undefined";
        this.rating = (rating != null) ? rating : '?';
        this.platforms = new ArrayList<>();
        evaluations = new HashMap<>();
        tests = new HashMap<>();
    }

    /**
     * Add a platform for a game
     * @param platform the platform to add
     */
    public void addPlatform(CPlatform platform){
        platforms.add(platform);
    }

    /**
     * Add an evaluation for a platform
     * @param platform the platform
     * @param evaluation the evaluation
     */
    public void addEvaluation(CPlatform platform, CEvaluation evaluation){
        if(!evaluations.containsKey(platform)){
            evaluations.put(platform, evaluation);
        }
    }

    /**
     * Add a test for a platform
     * @param platform the platform
     * @param test the test
     */
    public void addTest(CPlatform platform, CTest test){
        if(!tests.containsKey(platform)){
            tests.put(platform, test);
        }
    }

    /**
     *
     * @return the category of the video game
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @return the name of the video game
     */
    public String getName() {
        return name;
    }


    /**
     *
     * @return the editor of the video game
     */
    public String getEditor() {
        return editor;
    }

    /**
     *
     * @return the rating of the video game
     */
    public char getRating() {
        return rating;
    }

    /**
     *
     * @return all the platform that contain this video game
     */
    public ArrayList<CPlatform> getPlatforms() {
        return platforms;
    }

    public Map<CPlatform, CEvaluation> getEvaluations() {
        return evaluations;
    }

    public Map<CPlatform, CTest> getTests() {
        return tests;
    }

    /**
     * Get the platform with the name in parameter
     * @param platformName the platform to get
     * @return the platform or null
     */
    public CPlatform getPlatform(String platformName){
        for (CPlatform platform : platforms){
            if(Objects.equals(platform.getName(), platformName)){
                return platform;
            }
        }
        return null;
    }

    /**
     * @return the display of all the evaluations
     */
    public String displayAllEvaluation(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for(CEvaluation evaluation : evaluations.values()){
            sb.append("\t").append(evaluation).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public String toString(){
        return this.name;
    }
}
