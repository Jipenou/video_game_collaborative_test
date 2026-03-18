package view.videoGame.evaluation;

import controller.CEvaluationController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the form to add an evaluation
 */
public class CAddEvaluationView extends JFrame {
    /** the evaluation controler */
    private final CEvaluationController evaluationController;

    /** the video game concerned by the evaluation */
    private final CVideoGame videoGame;

    /** the box that display the platform to evaluate */
    private final JComboBox<CPlatform> platformBox;

    /** the text area to write the text of the evaluation */
    private final JTextArea textArea;

    /** the spinner to write the score */
    private final JSpinner scoreSpinner;

    /** the text area to write the version of the game tested */
    private final JTextField versionField;

    /** The label that react to the actions */
    private final JLabel messageLabel;

    public CAddEvaluationView(CEvaluationController evaluationController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.evaluationController = evaluationController;
        this.videoGame = videoGame;

        CPlayer player = (CPlayer) evaluationController.getController().getCurrentUser();


        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " une " + CTextPlaceHolder.EVALUATION + " : "
                                            + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CHOISIR) + " la "  + CTextPlaceHolder.PLATEFORME));

        platformBox = new JComboBox<>(
                player.getPlatformsForGame(videoGame).toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.VERSION) + " / " +
                                CTextPlaceHolder.capitalize(CTextPlaceHolder.BUILD) + " : "));
        versionField = new JTextField();
        panel.add(versionField);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.NOTE) + " (/10) : "));
        scoreSpinner = new JSpinner(new SpinnerNumberModel(5.0, 0.0, 10.0, 0.5));
        panel.add(scoreSpinner);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.COMMENTAIRE) + " : "));
        textArea = new JTextArea(5,20);
        panel.add(new JScrollPane(textArea));

        JButton submitButton = new JButton( CTextPlaceHolder.capitalize(CTextPlaceHolder.VALIDER));
        panel.add(submitButton);
        submitButton.addActionListener(_ -> submitEvaluation(gameInfoView));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);
    }

    /**
     * Submit an evaluation for the current game
     */
    private void submitEvaluation(CVideoGameInfoView gameInfoView){
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();

        if (platform == null) {
            messageLabel.setText("Veuillez sélectionner une " + CTextPlaceHolder.PLATEFORME);
            return;
        }

        String version = versionField.getText().trim();
        if (version.isEmpty()) {
            messageLabel.setText("Veuillez entrer une " + CTextPlaceHolder.VERSION);
            return;
        }

        String text = textArea.getText().trim();
        if (text.isEmpty()) {
            messageLabel.setText("Veuillez entrer un " + CTextPlaceHolder.COMMENTAIRE);
            return;
        }

        double note = (double) scoreSpinner.getValue();

        CPlayer player = (CPlayer) evaluationController.getController().getCurrentUser();

        evaluationController.addNewEvaluation(player, videoGame, platform, text, version, note);

        dispose();
        gameInfoView.dispose();
        evaluationController.getController().getVideoGameController().viewInfoGameFrame(videoGame);
    }
}
