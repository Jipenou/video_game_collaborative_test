package view.user;

import controller.CUserController;
import model.user.*;
import model.videoGame.CEvaluation;

import javax.swing.*;
import java.awt.*;

/**
 * This class repressent the view to see the profil of a user
 */
public class CProfileView extends JFrame {

    private CUserController userController;

    private AUser user;

    public CProfileView(CUserController userController, AUser user){

        setTitle("Profil utilisateur");
        setSize(800,600);
        setLocationRelativeTo(null);

        this.userController = userController;
        this.user = user;

        if(user == null){
            return;
        }

        JPanel panelProfile = new JPanel(new GridLayout(0,1));

        panelProfile.add(new JLabel("Pseudo : " + user.getPseudo()));
        panelProfile.add(new JLabel("Role : " + user.getRole()));

        if(user instanceof CPlayer player){
            panelProfile.add(new JLabel("Nombre de jetons : " + player.getNbToken()));

            panelProfile.add(new JLabel("Jeux possédés (" + player.getTotalHoursPlayed() + "h) : "));
            if(player instanceof CTester){
                for(CPlayerGame playerGame : player.getGamePlayedSortedDecroissant()){
                    JButton videoGameButton = new JButton(playerGame.getVideoGame().getName() + " : " + playerGame.getHoursPlayed() + "h");
                    panelProfile.add(videoGameButton);
                    videoGameButton.addActionListener(e -> userController.getController().getVideoGameController().viewInfoGameFrame(playerGame.getVideoGame()));
                }

                panelProfile.add(new JLabel("Nombre d'évaluations: " + player.getNbEvaluation()));

                for(CEvaluation evaluation : player.getEvaluations()){
                    panelProfile.add(new JLabel(evaluation.toStringLong()));
                }

            }
            else{
                for(CPlayerGame playerGame : player.getGamePlayed()){
                    JButton videoGameButton = new JButton(playerGame.getVideoGame().getName() + " : " + playerGame.getHoursPlayed() + "h");
                    panelProfile.add(videoGameButton);
                    videoGameButton.addActionListener(e -> userController.getController().getVideoGameController().viewInfoGameFrame(playerGame.getVideoGame()));
                }
            }
        }

        if(user instanceof CTester tester){
            panelProfile.add(new JLabel("Nombre de tests : " + tester.getNbTest() + "\n"));
        }

        if(userController.getController().getCurrentUser() == user || userController.getController().getCurrentUser() instanceof CAdmin){
            String displayBlocked;
            if(user.isBlocked()){
                displayBlocked = "Oui";
            }
            else{
                displayBlocked = "Non";
            }
            panelProfile.add(new JLabel("Bloqué ? : " + displayBlocked + "\n"));
        }

        AUser currentUser = userController.getController().getCurrentUser();

        if(currentUser instanceof CAdmin && currentUser != user && !(user instanceof CAdmin) && user instanceof CPlayer player){
            JButton promoteButton = new JButton("Promouvoir cet utilisateur");
            panelProfile.add(promoteButton);
            promoteButton.addActionListener(e->promoteAccount(player));

            JButton deleteAccountButton = new JButton("Supprimer ce compte utilisateur");
            panelProfile.add(deleteAccountButton);
            deleteAccountButton.addActionListener(e->deleteAccount(user));

            if(!user.isBlocked()){
                JButton blockAccountButton = new JButton("Bloquer ce compte utilisateur");
                panelProfile.add(blockAccountButton);
                blockAccountButton.addActionListener(e->blockAccount(user));
            }
            else{
                JButton unblockAccountButton = new JButton("Débloquer ce compte utilisateur");
                panelProfile.add(unblockAccountButton);
                unblockAccountButton.addActionListener(e->unblockAccount(user));
            }
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

    public void blockAccount(AUser user){
        userController.blockAccount(user);
        dispose();
    }

    public void unblockAccount(AUser user){
        userController.unblockAccount(user);
        dispose();
    }

    public void promoteAccount(CPlayer user){
        userController.getController().getAdminController().promoteUser(user);
        dispose();
    }
}
