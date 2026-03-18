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

/**
 * This class represent the frame to see an evaluation
 */
public class CEvaluationInfoView extends JFrame{

    /** the evaluation displayed */
    private final CEvaluation evaluation;

    /** the buttons to evaluate this evaluation */
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

        // if the current user is a player
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

        // if the current user is a tester and if this tester hasn't signaled this evaluation
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

        // if the current user is an admin
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

        // if the current user is a player and this player has played the minimum hours on the game to evaluate
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

    /**
     * Refresh the evaluation of this evaluation
     */
    private void refreshButtons() {
        buttonAddPlus.setText("Utile : " + evaluation.getUtiliteOui());
        buttonAddNeutral.setText("Neutre : " + evaluation.getUtiliteNeutre());
        buttonAddMoins.setText("Inutile : " + evaluation.getUtiliteNon());
    }
}
