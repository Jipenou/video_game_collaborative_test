package view.administration;

import controller.CAdminController;
import controller.CVideoGameController;
import model.CSignalement;
import model.user.AUser;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CSignalementInfoView extends JFrame {
    /** the controller of the video game */
    private final CAdminController adminController;

    /** the game to display */
    private final CSignalement signalement;

    /** main panel */
    private final JPanel panel;

    public CSignalementInfoView(CAdminController adminController, CSignalement signalement) {
        this.adminController = adminController;
        this.signalement = signalement;
        AUser user = adminController.getController().getCurrentUser();

        setTitle("Infos du signalement");
        setSize(700,600);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Reporteur : " + signalement.getReporter().getPseudo()));
        JButton evaluationButton = new JButton(signalement.getEvaluation().toStringSimple());
        evaluationButton.addActionListener(e -> adminController.getController().getVideoGameController().displayEvaluation(signalement.getEvaluation()));
        panel.add(evaluationButton);

        add(panel);
    }
}
