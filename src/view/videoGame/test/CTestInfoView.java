package view.videoGame.test;

import controller.CTestController;
import model.videoGame.CTest;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CTestInfoView extends JFrame {
    private final CTestController testController;
    private final CTest test;

    public CTestInfoView(CTestController testController, CTest test) {

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

        JButton gameButton = new JButton("jeux vidéo : " + test.getVideoGame().getName());
        panel.add(gameButton);
        gameButton.addActionListener(e -> testController.getController().getVideoGameController().viewInfoGameFrame(test.getVideoGame()));

        panel.add(new JLabel("Platforme : " + test.getPlatform()));
        panel.add(new JLabel("texte : " + test.getText()));
        panel.add(new JLabel("Version : " + test.getNumVersion()));
        panel.add(new JLabel("Conditions : " + test.getConditions()));

        panel.add(new JLabel("Notes par catégorie"));
        for (Map.Entry<CTest.ECategory, Integer> entry : test.getNoteCategory().entrySet()) {
            panel.add(new JLabel(entry.getKey().toString() + " : " + entry.getValue() + "/10"));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }
}
