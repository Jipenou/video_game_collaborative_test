package view.user;

import controller.CController;
import controller.CUserController;
import model.user.AUser;
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

    public CProfileView(CUserController userController){

        setTitle("Profil utilisateur");
        setSize(400,400);
        setLocationRelativeTo(null);

        this.userController = userController;

        AUser user = userController.getController().getCurrentUser();

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

        JButton desinscrire = new JButton("Se désinscrire");
        panelProfile.add(desinscrire);
        desinscrire.addActionListener(e->desinscrire());

        add(panelProfile);
    }

    public void desinscrire(){
        userController.desinscrire();
        dispose();
    }
}
