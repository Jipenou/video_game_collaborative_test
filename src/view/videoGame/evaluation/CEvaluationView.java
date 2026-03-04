package view.videoGame.evaluation;

import controller.CEvaluationController;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;

import javax.swing.*;
import java.awt.*;

public class CEvaluationView extends JFrame{
    private final CEvaluationController evaluationController;
    private final CEvaluation evaluation;

    private final JButton buttonAddPlus;
    private final JButton buttonAddMoins;
    private final JButton buttonAddNeutral;

    private final AUser user;

    public CEvaluationView(CEvaluationController evaluationController, CEvaluation evaluation) {

        this.evaluationController = evaluationController;
        this.evaluation = evaluation;

        user = evaluationController.getController().getCurrentUser();

        setTitle("Voir évaluation : " + evaluation.getPlayer().getPseudo());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Date : " + evaluation.getDate()));
        JButton playerButton = new JButton("Joueur : " + evaluation.getPlayer().getPseudo());
        panel.add(playerButton);
        playerButton.addActionListener(e -> evaluationController.getController().getUserController().openProfile(evaluation.getPlayer()));
        panel.add(new JLabel("Platforme : " + evaluation.getPlatform()));
        panel.add(new JLabel("texte : " + evaluation.getText()));
        panel.add(new JLabel("Version : " + evaluation.getNumVersion()));
        panel.add(new JLabel("Score : " + evaluation.getGlobalScore()));

        buttonAddPlus = new JButton("Utile : " + evaluation.getUtiliteOui());
        buttonAddNeutral = new JButton("Neutre : " + evaluation.getUtiliteNeutre());
        buttonAddMoins = new JButton("Inutile : " + evaluation.getUtiliteNon());

        if(user instanceof CTester tester && !tester.isEvaluationSignaled(evaluation)){
            JButton buttonSignaler = new JButton("Signaler cette évaluation");
            panel.add(buttonSignaler);
            buttonSignaler.addActionListener(e -> signalEvaluation());
        }

        if(user instanceof CAdmin){
            JButton buttonSupprimer = new JButton("Supprimer cette évaluation");
            panel.add(buttonSupprimer);
            buttonSupprimer.addActionListener(e -> deleteEvaluation());
        }

        panel.add(buttonAddPlus);
        panel.add(buttonAddNeutral);
        panel.add(buttonAddMoins);

        buttonAddPlus.addActionListener(e -> addPlus());
        buttonAddNeutral.addActionListener(e -> addNeutral());
        buttonAddMoins.addActionListener(e -> addMoins());

        add(panel);
    }

    private void addPlus(){
        evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), 1);
        refreshButtons();
    }

    private void addMoins(){
        evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), -1);
        refreshButtons();
    }

    private void addNeutral(){
        evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), 0);
        refreshButtons();
    }

    private void refreshButtons() {
        buttonAddPlus.setText("Utile : " + evaluation.getUtiliteOui());
        buttonAddNeutral.setText("Neutre : " + evaluation.getUtiliteNeutre());
        buttonAddMoins.setText("Inutile : " + evaluation.getUtiliteNon());
    }

    private void signalEvaluation(){
        evaluationController.addSignalement(evaluation);
        dispose();
        evaluationController.getController().getVideoGameController().displayEvaluation(evaluation);
    }

    private void deleteEvaluation(){
        evaluationController.deleteEvaluation(evaluation);
        dispose();
        evaluationController.getController().getVideoGameController().viewInfoGameFrame(evaluation.getVideoGame());
    }
}
