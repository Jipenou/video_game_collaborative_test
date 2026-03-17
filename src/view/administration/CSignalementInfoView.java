package view.administration;

import controller.CAdminController;
import model.CSignalement;
import model.utils.CTextPlaceHolder;

import javax.swing.*;
import java.awt.*;

public class CSignalementInfoView extends JFrame {

    public CSignalementInfoView(CAdminController adminController, CSignalement signalement) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.INFORMATION_S + " des " + CTextPlaceHolder.SIGNALEMENT));
        setSize(700,600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(new JLabel("Reporteur : " + signalement.getReporter().getPseudo()));
        JButton evaluationButton = new JButton(signalement.getEvaluation().toStringSimple());
        evaluationButton.addActionListener(_ -> adminController.getController().getVideoGameController().displayEvaluation(signalement.getEvaluation()));
        panel.add(evaluationButton);

        add(panel);
    }
}
