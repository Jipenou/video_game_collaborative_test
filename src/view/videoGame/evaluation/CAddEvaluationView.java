package view.videoGame.evaluation;

import controller.CController;
import controller.CEvaluationController;
import controller.CVideoGameController;
import model.user.AUser;
import model.user.CPlayer;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;

public class CAddEvaluationView extends JFrame {
    private final CEvaluationController evaluationController;
    private final CVideoGame videoGame;

    private JComboBox<CPlatform> platformBox;
    private JTextArea textArea;
    private JTextField scoreField;
    private JTextField versionField;

    public CAddEvaluationView(CEvaluationController evaluationController, CVideoGame videoGame) {

        this.evaluationController = evaluationController;
        this.videoGame = videoGame;

        setTitle("Ajouter une évaluation : " + videoGame.getName());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Choisir la plateforme :"));

        platformBox = new JComboBox<>(
                videoGame.getPlatforms().toArray(new CPlatform[0])
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
