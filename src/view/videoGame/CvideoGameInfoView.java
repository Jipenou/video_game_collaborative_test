package view.videoGame;

import controller.CController;
import model.videoGame.CVideoGame;
import view.videoGame.evaluation.CAddEvaluationView;

import javax.swing.*;
import java.awt.*;

public class CvideoGameInfoView extends JFrame {
    private final CController controller;
    private final CVideoGame game;

    public CvideoGameInfoView(CController controller, CVideoGame game) {
        this.controller = controller;
        this.game = game;

        setTitle("Infos du jeu");
        setSize(400,300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Nom : " + game.getName()));
        panel.add(new JLabel("Genre : " + game.getCategory()));
        panel.add(new JLabel("Editeur : " + game.getEditor()));

        panel.add(new JLabel("Note moyenne : " + game.getRating()));

        if(!game.getTests().isEmpty()) {
            panel.add(new JLabel("Test : " + game.getTests()));
        } else {
            panel.add(new JLabel("Aucun test disponible"));
        }

        if(!game.getEvaluations().isEmpty()) {
            panel.add(new JLabel("Evaluation : " + game.getEvaluations()));
        } else {
            panel.add(new JLabel("Aucune évaluation disponible"));
        }

        JButton evalButton = new JButton("Ajouter une évaluation");
        panel.add(evalButton);

        evalButton.addActionListener(e -> addEvaluation());

        add(panel);
    }

    private void addEvaluation(){
        new CAddEvaluationView(controller, game).setVisible(true);
    }
}
