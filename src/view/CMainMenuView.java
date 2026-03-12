package view;

import controller.CController;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;
import view.user.CLoginView;
import view.user.CProfileView;
import view.videoGame.CShowMyGamesView;
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

        if(user instanceof CPlayer player){
            panel.add(new JLabel("Bienvenue " + player.getPseudo() + ", vous avez " + player.getNbToken() + " jetons"));
        }
        else {
            panel.add(new JLabel("Bienvenue invité"));
        }

        JButton displayGamesButton = new JButton("Tous les jeux");
        panel.add(displayGamesButton);
        displayGamesButton.addActionListener(e -> displayGames());

        JButton evaluationButton = new JButton("Toutes les évaluations");
        evaluationButton.addActionListener(e -> openEvaluationFrame());
        panel.add(evaluationButton);

        if(user instanceof CPlayer) {
            JButton testsButton = new JButton("Tous les tests");
            testsButton.addActionListener(e -> openTestsFrame());
            panel.add(testsButton);

            JButton displayMyGames = new JButton("Mes jeux");
            displayMyGames.addActionListener(e -> displayMyGames());
            panel.add(displayMyGames);
        }

        if(user instanceof CTester){
            JButton testGamessButton = new JButton("Jeux à tester");
            testGamessButton.addActionListener(e -> controller.getVideoGameController().displayGameToTestFrame());
            panel.add(testGamessButton);
        }

        if(user instanceof CAdmin) {
            JButton administrationButton = new JButton("Administration");
            administrationButton.addActionListener(e -> openAdministrationFrame());
            panel.add(administrationButton);
        }

        JButton profileButton = new JButton("Mon profil");
        panel.add(profileButton);

        JButton logoutButton = new JButton();
        if(controller.getCurrentUser() instanceof CPlayer){
            logoutButton.setText("se déconnecter");
        }
        else{
            logoutButton.setText("Page de connexion");
        }
        panel.add(logoutButton);
        logoutButton.addActionListener(e -> logout());


        profileButton.addActionListener(e -> openProfile());

        add(panel);
    }

    private void displayMyGames(){
        new CShowMyGamesView(controller.getVideoGameController()).setVisible(true);
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
        controller.getUserController().openProfile(controller.getCurrentUser());
    }

    /**
     * display the games
     */
    private void displayGames(){
        new CVideoGamesView(controller.getVideoGameController()).setVisible(true);
    }

    private void openAdministrationFrame(){
        controller.getAdminController().openAdministrationFrame();
    }

    private void openEvaluationFrame(){
        controller.getEvaluationController().openEvaluationFrame();
    }

    private void openTestsFrame(){
        controller.getTestController().displayAllTestFrame();
    }
}
