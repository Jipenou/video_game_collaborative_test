package view;

import controller.CController;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;
import model.utils.CTextPlaceHolder;
import view.user.CLoginView;
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

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.MENU) + " " + CTextPlaceHolder.PRINCIPAL);
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
            panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.BIENVENUE) + " " +
                                player.getPseudo() + ", vous avez " + player.getNbToken() + " " + CTextPlaceHolder.JETON_S));
        }
        else {
            panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.BIENVENUE) + " " + CTextPlaceHolder.INVITE));
        }

        JButton displayGamesButton = new JButton("Tous les " + CTextPlaceHolder.JEU_S);
        panel.add(displayGamesButton);
        displayGamesButton.addActionListener(_ -> {
            controller.getVideoGameController().displayAllGamesFrame();
        });

        JButton evaluationButton = new JButton("Toutes les " + CTextPlaceHolder.EVALUATION_S);
        evaluationButton.addActionListener(_ -> {
            controller.getEvaluationController().openEvaluationFrame();
        });
        panel.add(evaluationButton);

        if(user instanceof CPlayer) {
            JButton testsButton = new JButton("Tous les " + CTextPlaceHolder.TEST_S);
            testsButton.addActionListener(_ -> {
                controller.getTestController().displayAllTestFrame();
            });
            panel.add(testsButton);

            JButton displayMyGames = new JButton("Mes " + CTextPlaceHolder.JEU_S);
            displayMyGames.addActionListener(_ -> {
                controller.getVideoGameController().displayMyGamesFrame();
            });
            panel.add(displayMyGames);
        }

        if(user instanceof CTester){
            JButton testGamessButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.JEU_S) + " à "
                                                    + CTextPlaceHolder.TESTER);
            testGamessButton.addActionListener(_ -> controller.getVideoGameController().displayGameToTestFrame());
            panel.add(testGamessButton);
        }

        if(user instanceof CAdmin) {
            JButton administrationButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.ADMINISTRATION));
            administrationButton.addActionListener(_ -> {
                controller.getAdminController().openAdministrationFrame();
            });
            panel.add(administrationButton);
        }

        JButton profileButton = new JButton("Mon " + CTextPlaceHolder.PROFIL);
        panel.add(profileButton);
        profileButton.addActionListener(_ -> {
            controller.getUserController().openProfile(controller.getCurrentUser());
        });

        JButton logoutButton = new JButton();
        if(controller.getCurrentUser() instanceof CPlayer){
            logoutButton.setText("Se " + CTextPlaceHolder.DECONNECTER);
        }
        else{
            logoutButton.setText(CTextPlaceHolder.capitalize(CTextPlaceHolder.PAGE) + " de " + CTextPlaceHolder.CONNEXION);
        }
        panel.add(logoutButton);
        logoutButton.addActionListener(_ -> {
            dispose();
            controller.logout();
        });

        JButton reloadButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.RECHARGER) + " la " + CTextPlaceHolder.PAGE);
        panel.add(reloadButton);
        reloadButton.addActionListener(_ -> {
            dispose();
            controller.openMainFrame();
        });

        add(panel);
    }
}
