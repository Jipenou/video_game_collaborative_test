package view.user;

import controller.CController;
import controller.CUserController;
import model.utils.CTextPlaceHolder;
import view.CMainMenuView;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the frame login and register in order to login or register
 */
public class CLoginView extends JFrame{
    /** the user controller */
    private final CUserController userController;

    /** the field to write the pseudo */
    private final JTextField pseudoField;

    /** The label that react to the actions */
    private final JLabel messageLabel;

    public CLoginView(CController controller) {

        this.userController = controller.getUserController();

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.APPLICATION_NAME));
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.clearAllCSV();
                controller.saveAll();
                dispose();
                System.exit(0);
            }
        });
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        pseudoField = new JTextField();
        panel.add(new JLabel(CTextPlaceHolder.PSEUDO + ":"));
        panel.add(pseudoField);

        JPanel buttons = new JPanel();

        JButton inviteButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.INVITE));
        JButton loginButton = new JButton("Se " + CTextPlaceHolder.CONNECTER);
        JButton registerButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.CREER) + " un " + CTextPlaceHolder.COMPTE);

        buttons.add(inviteButton);
        buttons.add(loginButton);
        buttons.add(registerButton);

        panel.add(buttons);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);

        inviteButton.addActionListener(_ -> {
            userController.loginGuest();
            dispose();
        });
        loginButton.addActionListener(_ -> login());
        registerButton.addActionListener(_ -> register());
    }

    /**
     * the action performed for clicking on the "login" button
     */
    private void login() {

        String pseudo = pseudoField.getText();

        if(userController.login(pseudo)) {
            dispose();
            new CMainMenuView(userController.getController()).setVisible(true);
        } else {
            messageLabel.setText(CTextPlaceHolder.UTILISATEUR + " inconnu");
        }
    }

    /**
     * the action performed for clicking on the "register" button
     */
    private void register() {

        String pseudo = pseudoField.getText();

        if(userController.register(pseudo)) {
            messageLabel.setText(CTextPlaceHolder.capitalize(CTextPlaceHolder.COMPTE) + " créé !");
        } else {
            messageLabel.setText(CTextPlaceHolder.PSEUDO + " déjà utilisé ou non conforme");
        }
    }
}
