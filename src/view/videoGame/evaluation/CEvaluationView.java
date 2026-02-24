package view.videoGame.evaluation;

import controller.CEvaluationController;
import model.user.CPlayer;
import model.videoGame.CEvaluation;

import javax.swing.*;
import java.awt.*;

public class CEvaluationView extends JFrame{
    private final CEvaluationController evaluationController;
    private final CEvaluation evaluation;

    private JButton buttonAddPlus;
    private JButton buttonAddMoins;

    public CEvaluationView(CEvaluationController evaluationController, CEvaluation evaluation) {

        this.evaluationController = evaluationController;
        this.evaluation = evaluation;

        setTitle("Voir évaluation : " + evaluation.getPlayer().getPseudo());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Date : " + evaluation.getDate()));
        panel.add(new JLabel("Player : " + evaluation.getPlayer().getPseudo()));
        panel.add(new JLabel("Platforme : " + evaluation.getPlatform()));
        panel.add(new JLabel("texte : " + evaluation.getText()));
        panel.add(new JLabel("Version : " + evaluation.getNumVersion()));
        panel.add(new JLabel("Score : " + evaluation.getGlobalScore()));

        buttonAddPlus = new JButton("Utile : " + evaluation.getUtiliteOui());
        buttonAddMoins = new JButton("Inutile : " + evaluation.getUtiliteNon());

        panel.add(buttonAddPlus);
        panel.add(buttonAddMoins);

        buttonAddPlus.addActionListener(e -> addPlus());
        buttonAddMoins.addActionListener(e -> addMoins());

        add(panel);
    }

    private void addPlus(){
        evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), 1);
        refreshButtons();
    }

    private void addMoins(){
        evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), 0);
        refreshButtons();
    }

    private void refreshButtons() {
        buttonAddPlus.setText("Utile : " + evaluation.getUtiliteOui());
        buttonAddMoins.setText("Inutile : " + evaluation.getUtiliteNon());
    }
}
