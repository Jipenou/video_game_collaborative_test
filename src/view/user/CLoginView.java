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
    private JTextField pseudoField;

    /** The label that react to the actions */
    private JLabel messageLabel;

    public CLoginView(CController controller) {

        this.userController = controller.getUserController();

        setTitle(CTextPlaceHolder.APPLICATION_NAME);
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

        JButton inviteButton = new JButton("Invité");
        JButton loginButton = new JButton(CTextPlaceHolder.LOGIN);
        JButton registerButton = new JButton(CTextPlaceHolder.REGISTER);

        buttons.add(inviteButton);
        buttons.add(loginButton);
        buttons.add(registerButton);

        panel.add(buttons);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);

        inviteButton.addActionListener(e -> inviteLogin());
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());
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
            messageLabel.setText(CTextPlaceHolder.USER + " inconnu");
        }
    }

    /**
     * the action performed for clicking on the "register" button
     */
    private void register() {

        String pseudo = pseudoField.getText();

        if(userController.register(pseudo)) {
            messageLabel.setText(CTextPlaceHolder.ACCOUNT + " créé !");
        } else {
            messageLabel.setText(CTextPlaceHolder.PSEUDO + " déjà utilisé ou non conforme");
        }
    }

    private void inviteLogin(){
        userController.loginGuest();
        dispose();
    }

}
