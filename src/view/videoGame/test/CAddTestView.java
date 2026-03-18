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

/**
 * This class represent the form to add a test
 */
public class CAddTestView extends JFrame{

    /** the test controller */
    private final CTestController testController;

    /** the video game tested */
    private final CVideoGame videoGame;

    /** the box that display the platform to test */
    private final JComboBox<CPlatform> platformBox;

    /** the text area to write the text of the test */
    private final JTextArea textArea;

    /** the text area to write the conditions of the test */
    private final JTextField conditionsField;

    /** the text area to write the version of the game tested */
    private final JTextField versionField;

    /** The label that react to the actions */
    private final JLabel messageLabel;

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

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }

    /**
     * Submit the evaluation
     * @param gameInfoView the previous view (to reload after submit the test)
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

        String conditions = conditionsField.getText().trim();
        if (conditions.isEmpty()) {
            messageLabel.setText("Veuillez entrer des " + CTextPlaceHolder.CONDITION_S);
            return;
        }

        String text = textArea.getText().trim();
        if (text.isEmpty()) {
            messageLabel.setText("Veuillez entrer un " + CTextPlaceHolder.TEXTE);
            return;
        }

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
