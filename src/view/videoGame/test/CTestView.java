package view.videoGame.test;

import controller.CEvaluationController;
import controller.CTestController;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CTest;

import javax.swing.*;
import java.awt.*;

public class CTestView extends JFrame {
    private final CTestController testController;
    private final CTest test;

    public CTestView(CTestController testController, CTest test) {

        this.testController = testController;
        this.test = test;

        setTitle("Voir test : " + test.getTester().getPseudo());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Date : " + test.getDate()));
        JButton playerButton = new JButton("Testeur : " + test.getTester().getPseudo());
        panel.add(playerButton);
        playerButton.addActionListener(e -> testController.getController().getUserController().openProfile(test.getTester()));
        panel.add(new JLabel("Platforme : " + test.getPlatform()));
        panel.add(new JLabel("texte : " + test.getText()));
        panel.add(new JLabel("Version : " + test.getNumVersion()));
        panel.add(new JLabel("Conditions : " + test.getConditions()));

        add(panel);
    }
}
