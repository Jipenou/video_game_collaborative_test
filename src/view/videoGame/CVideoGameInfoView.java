package view.videoGame;

import controller.CVideoGameController;
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

    public CVideoGameInfoView(CVideoGameController videoGameController, CVideoGame game) {
        this.videoGameController = videoGameController;
        this.game = game;

        setTitle("Infos du jeu");
        setSize(700,600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Nom : " + game.getName()));
        panel.add(new JLabel("Genre : " + game.getCategory()));
        panel.add(new JLabel("Editeur : " + game.getEditor()));

        panel.add(new JLabel("Note moyenne : " + game.getRating()));

        if(!game.getTests().isEmpty()) {
            panel.add(new JLabel("Tests : " + game.getTests()));
        } else {
            panel.add(new JLabel("Aucun test disponible"));
        }

        if(!game.getEvaluations().isEmpty()) {
            // html because JLabel canot support \n, \t and other
            panel.add(new JLabel(
                    "<html>Evaluations : <br>" +
                            game.displayAllEvaluation()
                                    .replace("\n", "<br>")
                                    .replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;") +
                            "</html>"
            ));
            //panel.add(new JLabel("Evaluations : " + game.displayAllEvaluation()));
        } else {
            panel.add(new JLabel("Aucune évaluation disponible"));
        }

        JButton evalButton = new JButton("Ajouter une évaluation");
        panel.add(evalButton);

        evalButton.addActionListener(e -> addEvaluation());

        add(panel);
    }

    /** add an evaluation to the game */
    private void addEvaluation(){
        videoGameController.addEvaluationFrame(game);
    }
}
