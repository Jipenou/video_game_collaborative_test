package view.videoGame.test;

import controller.CEvaluationController;
import controller.CTestController;
import model.videoGame.CEvaluation;
import model.videoGame.CTest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CTestView extends JFrame {

    /** the main controller of the application */
    private final CTestController testController;

    public CTestView(CTestController testController) {
        this.testController = testController;

        setTitle("Liste des tests");
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CTest> tests = testController.getController().getDatabase().getTests();

        JList<CTest> list = new JList<>(tests.toArray(new CTest[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton("Voir infos");
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(e -> {
            CTest test = list.getSelectedValue();
            if(test != null) {
                testController.displayTest(test);
            }
        });

        add(panel);
    }
}
