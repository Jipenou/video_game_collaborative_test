package view.videoGame.evaluation;

import controller.CEvaluationController;
import model.user.CPlayer;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;

public class CAddEvaluationView extends JFrame {
    private final CEvaluationController evaluationController;
    private final CVideoGame videoGame;

    private final JComboBox<CPlatform> platformBox;
    private final JTextArea textArea;
    private final JTextField scoreField;
    private final JTextField versionField;

    public CAddEvaluationView(CEvaluationController evaluationController, CVideoGame videoGame) {

        this.evaluationController = evaluationController;
        this.videoGame = videoGame;

        CPlayer player = (CPlayer) evaluationController.getController().getCurrentUser();


        setTitle("Ajouter une évaluation : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Choisir la plateforme :"));

        platformBox = new JComboBox<>(
                player.getPlatformsForGame(videoGame).toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        panel.add(new JLabel("Version / Build :"));
        versionField = new JTextField();
        panel.add(versionField);

        panel.add(new JLabel("Note (/10) :"));
        scoreField = new JTextField();
        panel.add(scoreField);

        panel.add(new JLabel("Commentaire :"));
        textArea = new JTextArea(5,20);
        panel.add(new JScrollPane(textArea));

        JButton submitButton = new JButton("Valider");
        panel.add(submitButton);

        submitButton.addActionListener(e -> submitEvaluation());

        add(panel);
    }

    /**
     * Submit an evaluation for the current game
     */
    private void submitEvaluation(){
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();
        String version = versionField.getText();
        String text = textArea.getText();
        double note = Integer.parseInt(scoreField.getText());

        CPlayer player = (CPlayer) evaluationController.getController().getCurrentUser();

        evaluationController.addNewEvaluation(player, videoGame, platform, text, version, note);

        dispose();
    }
}
