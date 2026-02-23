package view;

import controller.CController;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CTester;
import view.user.CLoginView;
import view.user.CProfileView;
import view.videoGame.gamesView;

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        AUser user = controller.getCurrentUser();

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Bienvenue " + user.getPseudo() + ", vous avez " + user.getNbToken() + " jetons"));

        JButton displayGamesButton = new JButton("Voir les jeux");
        JButton logoutButton = new JButton("Logout");
        JButton profileButton = new JButton("Mon profil");

        panel.add(displayGamesButton);
        panel.add(profileButton);
        panel.add(logoutButton);

        if(user instanceof CTester) {
            panel.add(new JButton("Ajouter un test"));
        }

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
        new gamesView(controller).setVisible(true);
    }
}
