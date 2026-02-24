package view;

import controller.CController;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CTester;
import view.user.CLoginView;
import view.user.CProfileView;
import view.videoGame.CVideoGamesView;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the main menu after login
 */
public class CMainMenuView extends JFrame {

    /** the main controller of the application */
    private final CController controller;

    public CMainMenuView(CController controller) {

        this.controller = controller;

        setTitle("Menu principal");
        setSize(400, 300);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.clearAllCSV();
                controller.saveAll();
                dispose();
                System.exit(0);
            }
        });

        AUser user = controller.getCurrentUser();

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Bienvenue " + user.getPseudo() + ", vous avez " + user.getNbToken() + " jetons"));

        JButton displayGamesButton = new JButton("Voir les jeux");
        JButton logoutButton = new JButton("Logout");
        JButton profileButton = new JButton("Mon profil");

        panel.add(displayGamesButton);
        panel.add(profileButton);
        panel.add(logoutButton);

        if(user instanceof CAdmin) {
            panel.add(new JButton("Administration"));
        }

        logoutButton.addActionListener(e -> logout());
        profileButton.addActionListener(e -> openProfile());
        displayGamesButton.addActionListener(e -> displayGames());

        add(panel);
    }

    /**
     * Logout, set back to the login frame
     */
    private void logout() {

        controller.setCurrentUser(null);

        dispose();

        new CLoginView(controller).setVisible(true);
    }

    /**
     * open the profile frame to see information about the user
     */
    private void openProfile(){
        new CProfileView(controller).setVisible(true);
    }

    /**
     * display the games
     */
    private void displayGames(){
        new CVideoGamesView(controller.getVideoGameController()).setVisible(true);
    }
}
