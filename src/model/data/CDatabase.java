package model.data;

import model.CSignalement;
import model.user.AUser;
import model.user.CPlayer;
import model.user.CPlayerGame;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represent the database of the application
 */
public class CDatabase {

    /** the videos game as <name of the vg, the video game> */
    private final Map<String, CVideoGame> videoGames;

    /** the user registered as <pseudo, User> */
    private final Map<String, CPlayer> users;

    /** the evaluations */
    private final List<CEvaluation> evaluations;

    /** the tests */
    private final List<CTest> tests;

    /** The games owned by players */
    private final List<CPlayerGame> playerGames;

    private final List<CSignalement> signaledEvaluations;

    private final Map<CVideoGame, Map<CPlayer, Integer>> tokenOnGames;

    public CDatabase(){
        videoGames = new HashMap<>();
        users = new HashMap<>();
        evaluations = new ArrayList<>();
        tests = new ArrayList<>();
        playerGames = new ArrayList<>();
        signaledEvaluations = new ArrayList<>();
        tokenOnGames = new HashMap<>();
    }

    /**
     * Add a user to the database
     * @param user the user to add
     */
    public void addUser(CPlayer user){
        users.put(user.getPseudo(), user);
    }

    /**
     * Tell if a user is in the database based on the pseudo
     * @param pseudo the pseudo of the user to check
     * @return true if the user is present, else false
     */
    public boolean isUserPresent(String pseudo){
        return (users.containsKey(pseudo));
    }

    /**
     * Get a user by his pseudo
     * @param pseudo the pseudo of the user
     * @return the user if present or null
     */
    public AUser getUser(String pseudo){
        if(isUserPresent(pseudo)){
            return users.get(pseudo);
        }
        return null;
    }

    /**
     * add a video game to the database
     * @param vg the video game to add
     */
    public void addVideoGame(CVideoGame vg){
        videoGames.put(vg.getName(), vg);
    }

    /**
     *
     * @param name the name of the video game
     * @return true is the video game is in the database, else false
     */
    public boolean isVideoGameCreated(String name){
        return (videoGames.containsKey(name));
    }

    /**
     *
     * @param name the name of the video game to get
     * @return the video game
     */
    public CVideoGame getVideoGame(String name){
        if(isVideoGameCreated(name)){
            return videoGames.get(name);
        }
        return null;
    }

    /**
     *
     * @return all the video games present in the database
     */
    public List<CVideoGame> getAllVideoGames(){
        return new ArrayList<>(videoGames.values());
    }

    /**
     *
     * @return the map of video games
     */
    public Map<String, CVideoGame> getVideoGames() {
        return videoGames;
    }

    public Map<String, CPlayer> getUsers() {
        return users;
    }

    public List<CEvaluation> getEvaluations() {
        return evaluations;
    }

    public CEvaluation getEvaluationByPseudoVideoGameAndPlatform(LocalDateTime date, CPlayer player, CVideoGame videoGame, CPlatform platform){
        for(CEvaluation eval : evaluations){
            if(eval.getDate().equals(date) && eval.getPlayer() == player && eval.getVideoGame() == videoGame && eval.getPlatform() == platform){
                return eval;
            }
        }
        return null;
    }

    public List<CVideoGame> getVideoGamesSortedByTokens(CPlayer player) {
        return videoGames.values().stream()
                .filter(vg -> player.isGameInCollection(vg)
                        && player.getTotalHoursPlayedOnAGame(vg) >= CTest.NUMBER_HOURS_MINIMUM_PLAYED_TO_TEST)
                .sorted(Comparator.comparingInt(CVideoGame::getAllTokenOnGame).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Add an evaluation into the database
     * @param eval the evaluation to add
     */
    public void addEvaluation(CEvaluation eval){
        evaluations.add(eval);
    }

    public List<CTest> getTests() {
        return tests;
    }

    public void addTest(CTest test){
        tests.add(test);
    }

    public void addPlayerGame(CPlayerGame playerGame){
        playerGames.add(playerGame);
    }

    public List<CPlayerGame> getPlayerGames() {
        return playerGames;
    }

    public void addSignalement(CSignalement signalement) {
        signaledEvaluations.add(signalement);
    }

    public List<CSignalement> getSignaledEvaluations() {
        return signaledEvaluations;
    }

    public void removeEvaluation(CEvaluation evaluation){
        evaluations.remove(evaluation);
    }

    public void removeSignalementForEval(CEvaluation evaluation){
        List<CSignalement> toRemove = new ArrayList<>();

        for(CSignalement signalement : signaledEvaluations){
            if(signalement.getEvaluation() == evaluation){
                signalement.getReporter().removeSignaledEvaluation(evaluation);
                toRemove.add(signalement);
            }
        }
        signaledEvaluations.removeAll(toRemove);
    }

    public void removeAllEvaluationsForUser(CPlayer player) {
        List<CEvaluation> toRemove = new ArrayList<>();

        for (CEvaluation evaluation : evaluations) {
            if (evaluation.getPlayer() == player) {
                removeSignalementForEval(evaluation);
                evaluation.getVideoGame().removeEvaluation(evaluation);
                toRemove.add(evaluation);
            }
        }
        evaluations.removeAll(toRemove);
    }

    public void removeAllTestsForUser(CTester tester) {
        List<CTest> toRemove = new ArrayList<>();

        for (CTest test : tests) {
            if (test.getTester() == tester) {
                test.getVideoGame().removeTest(test);
                toRemove.add(test);
            }
        }
        tests.removeAll(toRemove);
    }

    public void removeAllSignalementsForUser(CTester tester) {
        signaledEvaluations.removeIf(signalement -> signalement.getReporter() == tester);
    }

    public void removeUser(AUser user){
        users.remove(user.getPseudo(), user);
    }

    public void removeAllPlayerGamesForUser(CPlayer player) {
        playerGames.removeIf(playerGame -> {
            if (playerGame.getPlayer() == player) {
                player.removeGamePlayed(playerGame);
                return true;
            }
            return false;
        });
    }

    public void removeAllForUser(AUser user) {
        if (user instanceof CPlayer player) {
            removeAllEvaluationsForUser(player);
            removeAllPlayerGamesForUser(player);
        }
        if (user instanceof CTester tester) {
            removeAllTestsForUser(tester);
            removeAllSignalementsForUser(tester);
        }
        removeUser(user);
    }

    public List<AUser> getBlockedUser(){
        List<AUser> blockedUsers = new ArrayList<>();
        for(AUser user : users.values()){
            if(user.isBlocked()){
                blockedUsers.add(user);
            }
        }
        return blockedUsers;
    }

    public List<AUser> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void replaceUser(CPlayer oldUser, CPlayer newUser) {
        users.remove(oldUser.getPseudo());
        users.put(newUser.getPseudo(), newUser);

        for (CPlayerGame playerGame : playerGames) {
            if (playerGame.getPlayer() == oldUser) {
                playerGame.setPlayer((CPlayer) newUser);
            }
        }

        for (CEvaluation eval : evaluations) {
            if (eval.getPlayer() == oldUser) {
                eval.setPlayer((CPlayer) newUser);
            }
        }

        if (oldUser instanceof CTester oldTester && newUser instanceof CTester newTester) {
            for (CTest test : tests) {
                if (test.getTester() == oldTester) {
                    test.setTester(newTester);
                }
            }
            for (CSignalement signalement : signaledEvaluations) {
                if (signalement.getReporter() == oldTester) {
                    signalement.setReporter(newTester);
                }
            }
        }
    }
}
