package model.videoGame;

import model.user.CPlayer;

import java.util.*;
import java.util.stream.Collectors;

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
    private final List<CEvaluation> evaluations;

    /** A map that contains the evaluation by platform as <platform, test>*/
    private final Map<CPlatform, CTest> tests;

    private final Map<CPlayer, Integer> tokenOnTheGame;

    public CVideoGame(String name, String category, String editor, Character rating){
        this.name = (name != null)? name : "undefined";
        this.category = (category != null) ? category : "undefined";
        this.editor = (editor != null) ? editor : "undefined";
        this.rating = (rating != null) ? rating : '?';
        this.platforms = new ArrayList<>();
        evaluations = new ArrayList<>();
        tests = new HashMap<>();
        tokenOnTheGame = new HashMap<>();
    }

    /**
     * Add a platform for a game
     * @param platform the platform to add
     */
    public void addPlatform(CPlatform platform){
        platforms.add(platform);
    }

    public void addTokenToGame(CPlayer player, int nbToken){
        if(hasUserPlacedToken(player)){
            int newToken = tokenOnTheGame.get(player) + nbToken;
            tokenOnTheGame.put(player, newToken);
        }
        else {
            tokenOnTheGame.put(player, nbToken);
        }
    }

    public void removeTokenToGame(CPlayer player, int nbToken){
        if(hasUserPlacedToken(player)){
            if(nbToken >= tokenOnTheGame.get(player)){
                tokenOnTheGame.remove(player);
            }
            else{
                int newToken = tokenOnTheGame.get(player) - nbToken;
                tokenOnTheGame.put(player, newToken);
            }
        }
    }

    public void removeAllTokens(){
        tokenOnTheGame.clear();
    }

    public boolean hasUserPlacedToken(CPlayer player){
        return tokenOnTheGame.containsKey(player);
    }

    public int getTokenPlacedForUser(CPlayer player){
        if(hasUserPlacedToken(player)){
            return tokenOnTheGame.get(player);
        }
        return 0;
    }

    public List<CEvaluation> getEvaluationsSortedByScoreThenDate() {
        return evaluations.stream()
                .sorted(Comparator
                        .comparingInt(CEvaluation::getUtiliteOui).reversed()
                        .thenComparing(CEvaluation::getDate))
                .collect(Collectors.toList());
    }

    public int getAllTokenOnGame(){
        int totalToken = 0;
        for(int nbToken : tokenOnTheGame.values()){
            totalToken += nbToken;
        }
        return totalToken;
    }

    public boolean areAllPossibleTestsDone(){
        return tests.size() == platforms.size();
    }

    /**
     *
     * @return the platforms not tested on this game
     */
    public ArrayList<CPlatform> platformNotTested() {
        ArrayList<CPlatform> notTested = new ArrayList<>(platforms);
        notTested.removeAll(tests.keySet());
        return notTested;
    }

    /**
     * Add an evaluation
     * @param evaluation the evaluation
     */
    public void addEvaluation(CEvaluation evaluation){
        if(!evaluations.contains(evaluation)){
            evaluations.add(evaluation);
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

    public List<CEvaluation> getEvaluations() {
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

    public void removeEvaluation(CEvaluation evaluation){
        evaluations.remove(evaluation);
    }

    public void removeTest(CTest test){
        tests.values().remove(test);
    }

    public Map<CPlayer, Integer> getTokenOnTheGame() {
        return tokenOnTheGame;
    }

    public String toString(){
        return this.name + " - " + getAllTokenOnGame() + " token(s)";
    }
}
