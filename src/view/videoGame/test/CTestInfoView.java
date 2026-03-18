package view.videoGame.test;

import controller.CTestController;
import model.utils.CTextPlaceHolder;
import model.videoGame.CTest;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * This class represent the frame that display a test
 */
public class CTestInfoView extends JFrame {

    public CTestInfoView(CTestController testController, CTest test) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR) + " le " + CTextPlaceHolder.TEST + " de " +
                " : " + test.getTester().getPseudo());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.DATE) + " : " + test.getDate()));
        JButton playerButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.TESTEUR) + " : " + test.getTester().getPseudo());
        panel.add(playerButton);
        playerButton.addActionListener(_ -> testController.getController().getUserController().openProfile(test.getTester()));

        panel.add(new JSeparator());

        JButton gameButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.JEU) + " " + CTextPlaceHolder.VIDEO + " : "
                                        + test.getVideoGame().getName());
        panel.add(gameButton);
        gameButton.addActionListener(_ -> testController.getController().getVideoGameController().viewInfoGameFrame(test.getVideoGame()));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.PLATEFORME) + " : " + test.getPlatform()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.TEXTE) + " : " + test.getText()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.VERSION) + " : " + test.getNumVersion()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CONDITION_S) + " : " + test.getConditions()));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.NOTE_S) + " par " + CTextPlaceHolder.CATEGORIE));
        for (Map.Entry<CTest.ECategory, Integer> entry : test.getNoteCategory().entrySet()) {
            panel.add(new JLabel(entry.getKey().toString() + " : " + entry.getValue() + "/10"));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }
}
