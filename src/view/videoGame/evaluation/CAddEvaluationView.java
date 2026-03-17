package view.videoGame.evaluation;

import controller.CEvaluationController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;

import javax.swing.*;
import java.awt.*;

public class CAddEvaluationView extends JFrame {
    private final CEvaluationController evaluationController;
    private final CVideoGame videoGame;

    private final JComboBox<CPlatform> platformBox;
    private final JTextArea textArea;
    private final JTextField scoreField;
    private final JTextField versionField;

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
        scoreField = new JTextField();
        panel.add(scoreField);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.COMMENTAIRE) + " : "));
        textArea = new JTextArea(5,20);
        panel.add(new JScrollPane(textArea));

        JButton submitButton = new JButton( CTextPlaceHolder.capitalize(CTextPlaceHolder.VALIDER));
        panel.add(submitButton);

        submitButton.addActionListener(_ -> submitEvaluation(gameInfoView));

        add(panel);
    }

    /**
     * Submit an evaluation for the current game
     */
    private void submitEvaluation(CVideoGameInfoView gameInfoView){
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();
        String version = versionField.getText();
        String text = textArea.getText();
        double note = Integer.parseInt(scoreField.getText());

        CPlayer player = (CPlayer) evaluationController.getController().getCurrentUser();

        evaluationController.addNewEvaluation(player, videoGame, platform, text, version, note);

        dispose();
        gameInfoView.dispose();
        evaluationController.getController().getVideoGameController().viewInfoGameFrame(videoGame);
    }
}
