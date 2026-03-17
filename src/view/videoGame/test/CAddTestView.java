package view.videoGame.test;

import controller.CTestController;
import model.user.CTester;
import model.utils.CTextPlaceHolder;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CAddTestView extends JFrame{
    private final CTestController testController;
    private final CVideoGame videoGame;

    private final JComboBox<CPlatform> platformBox;
    private final JTextArea textArea;
    private final JTextField conditionsField;
    private final JTextField versionField;

    private final Map<CTest.ECategory, JSpinner> categoryMap = new HashMap<>();

    public CAddTestView(CTestController testController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.testController = testController;
        this.videoGame = videoGame;

        CTester tester = (CTester) testController.getController().getCurrentUser();

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " un " + CTextPlaceHolder.TEST + " : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CHOISIR) + " la " + CTextPlaceHolder.PLATEFORME
                + " :"));

        platformBox = new JComboBox<>(
                tester.getPlatformsNotTestedForGame(videoGame).toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.VERSION) + " / " +
                                CTextPlaceHolder.capitalize(CTextPlaceHolder.BUILD) + " : "));
        versionField = new JTextField();
        panel.add(versionField);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CONDITION_S) + " :"));
        conditionsField = new JTextField();
        panel.add(conditionsField);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.TEXTE) + " :"));
        textArea = new JTextArea(5,20);
        panel.add(textArea);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.NOTE_S) + " par " + CTextPlaceHolder.CATEGORIE));
        for (CTest.ECategory category : CTest.ECategory.values()) {
            panel.add(new JLabel(category.toString()));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(5, 0, 10, 1));
            categoryMap.put(category, spinner);
            panel.add(spinner);
        }

        JButton submitButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VALIDER));
        panel.add(submitButton);

        submitButton.addActionListener(_ -> submitEvaluation(gameInfoView));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }

    private void submitEvaluation(CVideoGameInfoView gameInfoView){
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();
        String version = versionField.getText();
        String text = textArea.getText();
        String conditions = conditionsField.getText();

        CTester tester = (CTester) testController.getController().getCurrentUser();

        CTest test = testController.addNewTest(tester, videoGame, platform, text, version, conditions);

        for (Map.Entry<CTest.ECategory, JSpinner> entry : categoryMap.entrySet()) {
            test.addCategoryScore(entry.getKey(), (int) entry.getValue().getValue());
        }

        dispose();
        gameInfoView.dispose();
        testController.getController().getVideoGameController().viewInfoGameFrame(videoGame);
    }
}
