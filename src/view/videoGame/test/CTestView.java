package view.videoGame.test;

import controller.CTestController;
import model.utils.CTextPlaceHolder;
import model.videoGame.CTest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the view that display all the tests of the application
 */
public class CTestView extends JFrame {

    public CTestView(CTestController testController) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.LISTE) + " des " + CTextPlaceHolder.TEST_S);
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CTest> tests = testController.getController().getDatabase().getTests();

        JList<CTest> list = new JList<>(tests.toArray(new CTest[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR) + " " + CTextPlaceHolder.INFORMATION_S);
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(_ -> {
            CTest test = list.getSelectedValue();
            if(test != null) {
                testController.displayTest(test);
            }
        });

        add(panel);
    }
}
