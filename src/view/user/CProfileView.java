package view.user;

import controller.CController;
import controller.CUserController;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * This class repressent the view to see the profil of a user
 */
public class CProfileView extends JFrame {

    private CUserController userController;

    private AUser user;

    public CProfileView(CUserController userController, AUser user){

        setTitle("Profil utilisateur");
        setSize(400,400);
        setLocationRelativeTo(null);

        this.userController = userController;
        this.user = user;

        if(user == null){
            return;
        }

        JPanel panelProfile = new JPanel(new GridLayout(0,1));

        panelProfile.add(new JLabel("Pseudo : " + user.getPseudo()));
        panelProfile.add(new JLabel("Role : " + user.getRole()));
        panelProfile.add(new JLabel("Nombre de jetons : " + user.getNbToken()));

        if(user instanceof CPlayer player){
            panelProfile.add(new JLabel("Nombre d'évaluations: " + player.getNbEvaluation()));
        }

        if(user instanceof CTester tester){
            panelProfile.add(new JLabel("Nombre de tests : " + tester.getNbTest() + "\n"));
        }

        if(userController.getController().getCurrentUser() instanceof CAdmin && userController.getController().getCurrentUser() != user){
            JButton deleteAccountButton = new JButton("Supprimer ce compte utilisateur");
            panelProfile.add(deleteAccountButton);
            deleteAccountButton.addActionListener(e->deleteAccount(user));
        }

        if(userController.getController().getCurrentUser() == user){
            JButton desinscrireButton = new JButton("Se désinscrire");
            panelProfile.add(desinscrireButton);
            desinscrireButton.addActionListener(e->desinscrire(user));
        }

        add(panelProfile);
    }

    public void desinscrire(AUser user){
        userController.desinscrire(user);
        dispose();
    }

    public void deleteAccount(AUser user){
        userController.deleteAccount(user);
        dispose();
    }
}
