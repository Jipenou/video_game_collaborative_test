package view.videoGame;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the view info of a game
 */
public class CVideoGameInfoView extends JFrame {

    /** the controller of the video game */
    private final CVideoGameController videoGameController;

    /** the game to display */
    private final CVideoGame game;

    private final JPanel panel;

    public CVideoGameInfoView(CVideoGameController videoGameController, CVideoGame game) {
        this.videoGameController = videoGameController;
        this.game = game;

        setTitle("Infos du jeu");
        setSize(700,600);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Nom : " + game.getName()));
        panel.add(new JLabel("Genre : " + game.getCategory()));
        panel.add(new JLabel("Editeur : " + game.getEditor()));

        panel.add(new JLabel("Note moyenne : " + game.getRating()));

        if(!game.getTests().isEmpty()) {
            panel.add(new JLabel("Tests : "));
            for(CTest test : game.getTests().values()){
                JButton testButton = new JButton(test.getDate() + ", " + test.getTester().getPseudo());
                panel.add(testButton);

                testButton.addActionListener(e -> displayTest(test));
            }
        } else {
            panel.add(new JLabel("Aucun test disponible"));
        }

        if(!game.getEvaluations().isEmpty()) {
            panel.add(new JLabel("Evaluations : "));
            for(CEvaluation evaluation : game.getEvaluations().values()){
                JButton evalButton = new JButton(evaluation.getDate() + ", " + evaluation.getPlayer().getPseudo());
                panel.add(evalButton);

                evalButton.addActionListener(e -> displayEvaluation(evaluation));
            }
        } else {
            panel.add(new JLabel("Aucune évaluation disponible"));
        }

        if(videoGameController.getController().getCurrentUser() instanceof CPlayer){
            JButton evalButton = new JButton("Ajouter une évaluation");
            panel.add(evalButton);
            evalButton.addActionListener(e -> addEvaluation());
        }
        if(videoGameController.getController().getCurrentUser() instanceof CTester){
            JButton testButton = new JButton("Ajouter un test");
            panel.add(testButton);
            testButton.addActionListener(e -> addTest());

        }
        add(panel);
    }

    /** add an evaluation to the game */
    private void addEvaluation(){
        videoGameController.addEvaluationFrame(game);
    }

    /** add a test to the game */
    private void addTest(){
        videoGameController.addTestFrame(game);
    }

    private void displayEvaluation(CEvaluation evaluation){
        videoGameController.displayEvaluation(evaluation);
    }

    private void displayTest(CTest test){
        videoGameController.displayTest(test);
    }
}
