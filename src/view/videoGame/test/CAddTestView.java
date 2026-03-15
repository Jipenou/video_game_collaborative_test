package view.videoGame.test;

import controller.CTestController;
import model.user.CTester;
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

    private final Map<CTest.ECategory, JSpinner> categorySpinners = new HashMap<>();
    private final Map<Enum<?>, JSpinner> specificSpinners = new HashMap<>();

    public CAddTestView(CTestController testController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.testController = testController;
        this.videoGame = videoGame;

        CTester tester = (CTester) testController.getController().getCurrentUser();

        setTitle("Ajouter un test : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Choisir la plateforme :"));

        platformBox = new JComboBox<>(
                tester.getPlatformsNotTestedForGame(videoGame).toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        panel.add(new JLabel("Version / Build :"));
        versionField = new JTextField();
        panel.add(versionField);

        panel.add(new JLabel("Conditions :"));
        conditionsField = new JTextField();
        panel.add(conditionsField);

        panel.add(new JLabel("Text :"));
        textArea = new JTextArea(5,20);
        panel.add(textArea);

        panel.add(new JLabel("Notes par catégorie"));
        for (CTest.ECategory category : CTest.ECategory.values()) {
            panel.add(new JLabel(category.toString()));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(5, 0, 10, 1));
            categorySpinners.put(category, spinner);
            panel.add(spinner);
        }

        JButton submitButton = new JButton("Valider");
        panel.add(submitButton);

        submitButton.addActionListener(e -> submitEvaluation(gameInfoView));

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

        for (Map.Entry<CTest.ECategory, JSpinner> entry : categorySpinners.entrySet()) {
            test.addCategoryScore(entry.getKey(), (int) entry.getValue().getValue());
        }

        dispose();
        gameInfoView.dispose();
        testController.getController().getVideoGameController().viewInfoGameFrame(videoGame);
    }
}
