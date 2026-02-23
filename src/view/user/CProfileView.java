package view.user;

import controller.CController;
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

    public CProfileView(CController controller){

        setTitle("Profil utilisateur");
        setSize(400,400);
        setLocationRelativeTo(null);

        AUser user = controller.getCurrentUser();

        if(user == null){
            return;
        }

        JPanel panelProfile = new JPanel(new GridLayout(0,1));

        panelProfile.add(new JLabel("Pseudo : " + user.getPseudo()));
        panelProfile.add(new JLabel("Role : " + user.getRole()));
        panelProfile.add(new JLabel("Nombre de jetons : " + user.getNbToken()));

        if(user instanceof CPlayer player){

            panelProfile.add(new JLabel("Temps total de jeu : " + player.getTotalPlayTime() + "h"));
            panelProfile.add(new JLabel("Nombre d'évaluations: " + player.getNbEvaluation()));
            panelProfile.add(new JLabel("Jeux: "));

            for(Map.Entry<?, Integer> entry : player.getPlayTime().entrySet()){
                panelProfile.add(new JLabel("\t" + entry.getKey() + " : " + entry.getValue() + "h"));
            }
        }

        if(user instanceof CTester tester){
            panelProfile.add(new JLabel("Nombre de tests : " + tester.getNbTest() + "\n"));
        }

        add(panelProfile);
    }
}
