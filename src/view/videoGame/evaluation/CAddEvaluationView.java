package view.videoGame.evaluation;

import controller.CController;
import model.user.AUser;
import model.user.CPlayer;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;

public class CAddEvaluationView extends JFrame {
    private final CController controller;
    private final CVideoGame videoGame;

    private JComboBox<String> platformBox;
    private JTextArea textArea;
    private JTextField scoreField;
    private JTextField versionField;

    public CAddEvaluationView(CController controller, CVideoGame videoGame) {

        this.controller = controller;
        this.videoGame = videoGame;

        setTitle("Ajouter une évaluation : " + videoGame.getName());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        AUser user = controller.getCurrentUser();

        panel.add(new JLabel("Choisir la plateforme :"));

        platformBox = new JComboBox<>(
                videoGame.getPlatforms()
                        .stream()
                        .map(CPlatform::getName)
                        .toArray(String[]::new)
        );

        panel.add(platformBox);

        panel.add(new JLabel("Version / Build :"));
        panel.add(new JTextField());

        panel.add(new JLabel("Note (/10) :"));
        scoreField = new JTextField();
        panel.add(scoreField);

        panel.add(new JLabel("Commentaire :"));
        textArea = new JTextArea(5,20);
        panel.add(new JScrollPane(textArea));

        JButton submitButton = new JButton("Valider");
        panel.add(submitButton);

        //submitButton.addActionListener(e -> submitEvaluation());

        add(panel);
    }
}
