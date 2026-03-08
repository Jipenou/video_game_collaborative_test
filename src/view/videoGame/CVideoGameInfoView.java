package view.videoGame;

import controller.CVideoGameController;
import model.user.AUser;
import model.user.CGuest;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * This class represent the view info of a game
 */
public class CVideoGameInfoView extends JFrame {

    /** the controller of the video game */
    private final CVideoGameController videoGameController;

    /** the game to display */
    private final CVideoGame game;

    /** main panel */
    private final JPanel panel;

    public CVideoGameInfoView(CVideoGameController videoGameController, CVideoGame game) {
        this.videoGameController = videoGameController;
        this.game = game;
        AUser currentUser = videoGameController.getController().getCurrentUser();


        setTitle("Infos du jeu");
        setSize(1000,800);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Nom : " + game.getName()));
        panel.add(new JLabel("Genre : " + game.getCategory()));
        panel.add(new JLabel("Editeur : " + game.getEditor()));

        panel.add(new JLabel("Note moyenne : " + game.getRating()));

        panel.add(new JLabel("Platformes supportées :"));

        for(CPlatform platform : game.getPlatforms()){
            panel.add(new JLabel("<html>&nbsp;&nbsp;" + platform.getName() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date de sortie : " + platform.getReleaseYear() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Développeur : " + platform.getDeveloper() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ventes globales : " + platform.getGlobalSale() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Note moyenne des testeurs : " + platform.getTesterRating() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Note moyenne des joueurs : " + platform.getPlayerRating() + "</html>"));
        }

        if(currentUser instanceof CPlayer player){
            // if the player have this game in our collection
            if(player.isGameInCollection(game)) {
                Map<CPlatform, Float> hoursPlayedOnThisGame = player.getHoursPlayedOnAGame(game);
                if(!hoursPlayedOnThisGame.isEmpty()){
                    JLabel nbHoursPlayed = new JLabel("Nombre d'heures jouées : ");
                    panel.add(nbHoursPlayed);

                    for(CPlatform platform : hoursPlayedOnThisGame.keySet()){
                        JLabel display = new JLabel(platform.getName() + " : " + hoursPlayedOnThisGame.get(platform) + "h");
                        panel.add(display);
                    }
                }
            }
        }

        // if we have some tests
        if(!game.getTests().isEmpty() && currentUser instanceof CPlayer) {
            panel.add(new JLabel("Tests : "));
            for(CTest test : game.getTests().values()){
                JButton testButton = new JButton(test.getDate() + ", " + test.getTester().getPseudo());
                panel.add(testButton);

                testButton.addActionListener(e -> displayTest(test));
            }
        } else {
            panel.add(new JLabel("Aucun test disponible"));
        }

        // if there is evaluations
        if(!game.getEvaluations().isEmpty()) {
            panel.add(new JLabel("Evaluations : "));
            for(CEvaluation evaluation : game.getEvaluations()){
                JButton evalButton = new JButton(evaluation.getDate() + ", " + evaluation.getPlayer().getPseudo());
                panel.add(evalButton);

                evalButton.addActionListener(e -> displayEvaluation(evaluation));
            }
        } else {
            panel.add(new JLabel("Aucune évaluation disponible"));
        }

        if(currentUser instanceof CPlayer player && player.isGameInCollection(game) && !player.isBlocked()){
            JButton evalButton = new JButton("Ajouter une évaluation");
            panel.add(evalButton);
            evalButton.addActionListener(e -> addEvaluation());
        }
        if(currentUser instanceof CTester tester && tester.isGameInCollection(game) && !tester.getPlatformsNotTestedForGame(game).isEmpty() && !tester.isBlocked()){
            JButton testButton = new JButton("Ajouter un test");
            panel.add(testButton);
            testButton.addActionListener(e -> addTest());
        }
        if(currentUser instanceof CPlayer player){
            // if we dont owned this game on all platform
            if(!player.getPlatformNotOwnedForGame(game).isEmpty()) {
                // if do not have this game
                if (!player.isGameInCollection(game)) {
                    JButton addToCollectionButton = new JButton("Ajouter le jeu à ma collection");
                    panel.add(addToCollectionButton);
                    addToCollectionButton.addActionListener(e -> addToCollection());
                } else { // if we have get this game at least on 1 platform
                    JButton addToCollectionButton = new JButton("Ajouter le jeu à ma collection pour une autre platforme");
                    panel.add(addToCollectionButton);
                    addToCollectionButton.addActionListener(e -> addToCollection());
                }
            }
            // if we have get this game at least on 1 platform
            //if(!player.getPlatformsForGame(game).isEmpty()){
            if(player.isGameInCollection(game)){
                JButton addHoursButton = new JButton("Ajouter du temps de jeu");
                panel.add(addHoursButton);
                addHoursButton.addActionListener(e -> addHours());
            }
        }
        add(panel);
    }

    /** display the add hours to the game frame */
    private void addHours(){
        videoGameController.addHoursFrame(this, game);
    }

    /** add an evaluation to the game */
    private void addEvaluation(){
        videoGameController.addEvaluationFrame(this, game);
    }

    /** add a test to the game */
    private void addTest(){
        videoGameController.addTestFrame(this, game);
    }

    /**
     * Display an evaluation
     * @param evaluation the evaluation to display
     */
    private void displayEvaluation(CEvaluation evaluation){
        videoGameController.displayEvaluation(evaluation);
    }

    /**
     * Display a test
     * @param test the test to display
     */
    private void displayTest(CTest test){
        videoGameController.displayTest(test);
    }

    /**
     * Display the frame to add to your collection a game with a specific platform
     */
    private void addToCollection(){
        videoGameController.displayAddGameFrame(this, game);
    }
}
