package view.administration;

import controller.CAdminController;
import model.utils.CTextPlaceHolder;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the view of the administration frame
 */
public class CAdministrationView extends JFrame{

    public CAdministrationView(CAdminController adminController) {

        setTitle(CTextPlaceHolder.ADMINISTRATION);
        setSize(400, 300);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.BIENVENUE) + " dans le " + CTextPlaceHolder.MENU + " d'" + CTextPlaceHolder.ADMINISTRATION));

        JButton displaySignalement = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.SIGNALEMENT) + " d'" + CTextPlaceHolder.EVALUATION_S);
        JButton usersButton = new JButton("Tous les " + CTextPlaceHolder.UTILISATEUR_S);
        JButton userBlocked = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.UTILISATEUR_S) + " bloqués");

        panel.add(displaySignalement);
        panel.add(usersButton);
        panel.add(userBlocked);

        displaySignalement.addActionListener(_ -> adminController.openSignalementFrame());
        usersButton.addActionListener(_ -> adminController.openUsersFrame());
        userBlocked.addActionListener(_ -> adminController.openBlockedUsersFrame());

        add(panel);
    }
}
