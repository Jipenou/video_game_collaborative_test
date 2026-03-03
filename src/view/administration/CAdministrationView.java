package view.administration;

import controller.CAdminController;
import model.user.AUser;

import javax.swing.*;
import java.awt.*;

public class CAdministrationView extends JFrame{

    /** the admin controller of the application */
    private final CAdminController adminController;

    public CAdministrationView(CAdminController adminController) {

        this.adminController = adminController;

        setTitle("Administration");
        setSize(400, 300);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Bienvenue dans le menu d'administration"));

        JButton displaySignalement = new JButton("Signalement d'évaluations");
        JButton userBlocked = new JButton("Utilisateurs bloqués");

        panel.add(displaySignalement);
        panel.add(userBlocked);

        displaySignalement.addActionListener(e -> displaySignalementFrame());
        userBlocked.addActionListener(e -> displayUserBlockedFrame());

        add(panel);
    }

    private void displaySignalementFrame(){
        adminController.openSignalementFrame();
    }

    private void displayUserBlockedFrame(){

    }
}
