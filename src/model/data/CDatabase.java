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

    /** the signaled evaluation */
    private final List<CSignalement> signaledEvaluations;

    public CDatabase(){
        videoGames = new HashMap<>();
        users = new HashMap<>();
        evaluations = new ArrayList<>();
        tests = new ArrayList<>();
        playerGames = new ArrayList<>();
        signaledEvaluations = new ArrayList<>();
    }

    public void replaceUser(CPlayer oldUser, CPlayer newUser) {
        users.remove(oldUser.getPseudo());
        users.put(newUser.getPseudo(), newUser);

        for (CPlayerGame playerGame : playerGames) {
            if (playerGame.getPlayer() == oldUser) {
                playerGame.setPlayer(newUser);
            }
        }

        for (CEvaluation eval : evaluations) {
            if (eval.getPlayer() == oldUser) {
                eval.setPlayer(newUser);
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

    /**
     * Tell if a user is in the database based on the pseudo
     * @param pseudo the pseudo of the user to check
     * @return true if the user is present, else false
     */
    public boolean isUserPresent(String pseudo){
        return (users.containsKey(pseudo));
    }

    /**
     *
     * @param name the name of the video game
     * @return true is the video game is in the database, else false
     */
    public boolean isVideoGameCreated(String name){
        return (videoGames.containsKey(name));
    }


    /*
    ===================== ADD =========================
     */

    /**
     * add a video game to the database
     * @param vg the video game to add
     */
    public void addVideoGame(CVideoGame vg){
        videoGames.put(vg.getName(), vg);
    }

    /**
     * Add a user to the database
     * @param user the user to add
     */
    public void addUser(CPlayer user){
        users.put(user.getPseudo(), user);
    }

    /**
     * Add an evaluation into the database
     * @param eval the evaluation to add
     */
    public void addEvaluation(CEvaluation eval){
        evaluations.add(eval);
    }

    /**
     * Add a test in the database
     * @param test the test to add
     */
    public void addTest(CTest test){
        tests.add(test);
    }

    /**
     * add a game owned for a player in the database
     * @param playerGame the game owned for a player
     */
    public void addPlayerGame(CPlayerGame playerGame){
        playerGames.add(playerGame);
    }

    /**
     * Add a signalement in the database
     * @param signalement the signalement to add
     */
    public void addSignalement(CSignalement signalement) {
        signaledEvaluations.add(signalement);
    }



    /*
    ===================== REMOVE =========================
     */

    /**
     * Remove an evaluation from the database
     * @param evaluation the evaluation to remove
     */
    public void removeEvaluation(CEvaluation evaluation){
        evaluations.remove(evaluation);
    }

    /**
     * Remove a signalement for an evaluation
     * @param evaluation the evaluation concerned
     */
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

    /**
     * Remove all the evaluations made by a user
     * @param player the player concerned
     */
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

    /**
     * Remove all the tests made by a tester
     * @param tester the tester concerned
     */
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

    /**
     * Remove all the signalement made by a tester
     * @param tester the tester concerned
     */
    public void removeAllSignalementsForUser(CTester tester) {
        signaledEvaluations.removeIf(signalement -> signalement.getReporter() == tester);
    }

    /**
     * Remove all the game owned for a player
     * @param player the player concerned
     */
    public void removeAllPlayerGamesForUser(CPlayer player) {
        playerGames.removeIf(playerGame -> {
            if (playerGame.getPlayer() == player) {
                player.removeGamePlayed(playerGame);
                return true;
            }
            return false;
        });
    }

    /**
     * Remove all the informations/tests/signalements/evaluation for a user
     * @param user the user concerned
     */
    public void removeAllForUser(AUser user) {
        if (user instanceof CPlayer player) {
            removeAllEvaluationsForUser(player);
            removeAllPlayerGamesForUser(player);
        }
        if (user instanceof CTester tester) {
            removeAllTestsForUser(tester);
            removeAllSignalementsForUser(tester);
        }
        users.remove(user.getPseudo());
    }

    /*
    ===================== GETTER =========================
     */

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
     * @return all the users of the application
     */
    public Map<String, CPlayer> getUsers() {
        return users;
    }

    /**
     *
     * @return all the evaluations of the application
     */
    public List<CEvaluation> getEvaluations() {
        return evaluations;
    }

    /**
     *
     * @param date the date of the evaluation
     * @param player the evaluator
     * @param videoGame the video game concerned
     * @param platform the platform concerned
     * @return an evaluation based on the date, evaluator, video game and platform of the evaluation
     */
    public CEvaluation getEvaluationByPseudoVideoGameAndPlatform(LocalDateTime date, CPlayer player, CVideoGame videoGame, CPlatform platform){
        for(CEvaluation eval : evaluations){
            if(eval.getDate().equals(date) && eval.getPlayer() == player && eval.getVideoGame() == videoGame && eval.getPlatform() == platform){
                return eval;
            }
        }
        return null;
    }

    /**
     * Return a list of video games sorted by the number of token on it (and also if the user has played on the game a minimum time)
     * @param player the player concerned
     * @return a list of video games sorted by the number of token on it (and also if the user has played on the game a minimum time)
     */
    public List<CVideoGame> getVideoGamesSortedByTokens(CPlayer player) {
        return videoGames.values().stream()
                .filter(vg -> player.isGameInCollection(vg)
                        && player.getTotalHoursPlayedOnAGame(vg) >= CTest.NUMBER_HOURS_MINIMUM_PLAYED_TO_TEST
                        && !vg.platformNotTested().isEmpty())
                .sorted(Comparator.comparingInt(CVideoGame::getAllTokenOnGame).reversed())
                .collect(Collectors.toList());
    }

    /**
     *
     * @return  all the tests of the application
     */
    public List<CTest> getTests() {
        return tests;
    }

    /**
     *
     * @return all the games owned by a user
     */
    public List<CPlayerGame> getPlayerGames() {
        return playerGames;
    }

    /**
     *
     * @return all the signalement of the application
     */
    public List<CSignalement> getSignaledEvaluations() {
        return signaledEvaluations;
    }

    /**
     *
     * @return a list of user blocked
     */
    public List<AUser> getBlockedUser(){
        List<AUser> blockedUsers = new ArrayList<>();
        for(AUser user : users.values()){
            if(user.isBlocked()){
                blockedUsers.add(user);
            }
        }
        return blockedUsers;
    }

    /**
     *
     * @return a list of all the users of the application
     */
    public List<AUser> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
