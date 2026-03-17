package view.videoGame.evaluation;

import controller.CEvaluationController;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;
import model.utils.CTextPlaceHolder;
import model.videoGame.CEvaluation;

import javax.swing.*;
import java.awt.*;

public class CEvaluationInfoView extends JFrame{
    private final CEvaluation evaluation;

    private final JButton buttonAddPlus;
    private final JButton buttonAddMoins;
    private final JButton buttonAddNeutral;

    public CEvaluationInfoView(CEvaluationController evaluationController, CEvaluation evaluation) {

        this.evaluation = evaluation;

        AUser currentUser = evaluationController.getController().getCurrentUser();

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR) + " l'" + CTextPlaceHolder.EVALUATION + " de : "
                + evaluation.getPlayer().getPseudo());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.DATE) + " : " + evaluation.getDate().toLocalDate()));

        if(currentUser instanceof CPlayer){
            JButton playerButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.JOUEUR) + " : " + evaluation.getPlayer().getPseudo());
            panel.add(playerButton);
            playerButton.addActionListener(_ -> evaluationController.getController().getUserController().openProfile(evaluation.getPlayer()));
        }
        else {
            panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.JOUEUR) + " : " + evaluation.getPlayer().getPseudo()));
        }

        JButton gameButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.JEU) + " " + CTextPlaceHolder.VIDEO + " : "
                                        + evaluation.getVideoGame().getName());
        panel.add(gameButton);
        gameButton.addActionListener(_ -> evaluationController.getController().getVideoGameController().viewInfoGameFrame(evaluation.getVideoGame()));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.PLATEFORME) + " : " + evaluation.getPlatform()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.TEXTE) + " : " + evaluation.getText()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.VERSION) + " : " + evaluation.getNumVersion()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.SCORE) + " : " + evaluation.getGlobalScore()));

        buttonAddPlus = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.UTILE) + " : " + evaluation.getUtiliteOui());
        buttonAddNeutral = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.NEUTRE) + " : " + evaluation.getUtiliteNeutre());
        buttonAddMoins = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.INUTILE) + " : " + evaluation.getUtiliteNon());

        if(currentUser instanceof CTester tester && !tester.isEvaluationSignaled(evaluation)){
            JButton buttonSignaler = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.SIGNALER) + " cette " +
                                                CTextPlaceHolder.EVALUATION);
            panel.add(buttonSignaler);
            buttonSignaler.addActionListener(_ -> {
                evaluationController.addSignalement(evaluation);
                dispose();
                evaluationController.getController().getVideoGameController().displayEvaluation(evaluation);
            });
        }

        if(currentUser instanceof CAdmin){
            JButton buttonSupprimer = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.SUPPRIMER) + " cette " +
                                                    CTextPlaceHolder.EVALUATION);
            panel.add(buttonSupprimer);
            buttonSupprimer.addActionListener(_ -> {
                evaluationController.deleteEvaluation(evaluation);
                dispose();
                evaluationController.getController().getVideoGameController().viewInfoGameFrame(evaluation.getVideoGame());
            });
        }

        panel.add(new JSeparator());

        if(currentUser instanceof CPlayer player && player.getTotalHoursPlayedOnAGame(evaluation.getVideoGame()) >= CEvaluation.NUMBER_HOURS_MINIMUM_PLAYED_TO_EVALUATE){
            panel.add(buttonAddPlus);
            panel.add(buttonAddNeutral);
            panel.add(buttonAddMoins);

            buttonAddPlus.addActionListener(_ -> {
                evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), 1);
                refreshButtons();
            });
            buttonAddNeutral.addActionListener(_ -> {
                evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), 0);
                refreshButtons();
            });
            buttonAddMoins.addActionListener(_ -> {
                evaluation.addUtilities((CPlayer) evaluationController.getController().getCurrentUser(), -1);
                refreshButtons();
            });
        }

        add(panel);
    }

    private void refreshButtons() {
        buttonAddPlus.setText("Utile : " + evaluation.getUtiliteOui());
        buttonAddNeutral.setText("Neutre : " + evaluation.getUtiliteNeutre());
        buttonAddMoins.setText("Inutile : " + evaluation.getUtiliteNon());
    }
}
