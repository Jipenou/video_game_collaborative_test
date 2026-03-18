package view.user;

import controller.CUserController;
import model.user.*;
import model.utils.CTextPlaceHolder;
import model.videoGame.CEvaluation;

import javax.swing.*;
import java.awt.*;

/**
 * This class repressent the view to see the profil of a user
 */
public class CProfileView extends JFrame {

    public CProfileView(CUserController userController, AUser user){

        setTitle("Profil utilisateur");
        setSize(800,600);
        setLocationRelativeTo(null);

        if(user == null){
            return;
        }

        AUser currentUser = userController.getController().getCurrentUser();

        JPanel panelProfile = new JPanel(new GridLayout(0,1));

        panelProfile.add(new JLabel("Pseudo : " + user.getPseudo()));
        panelProfile.add(new JLabel("Role : " + user.getRole()));

        // if user is a player
        if(user instanceof CPlayer player){
            panelProfile.add(new JLabel("Nombre de jetons : " + player.getNbToken()));

            panelProfile.add(new JLabel("Jeux possédés (" + player.getTotalHoursPlayed() + "h) : "));
            // if user is a tester
            if(player instanceof CTester){
                for(CPlayerGame playerGame : player.getGamePlayedSortedDecroissant()){
                    JButton videoGameButton = new JButton(playerGame.getVideoGame().getName() + " : " + playerGame.getHoursPlayed() + "h");
                    panelProfile.add(videoGameButton);
                    videoGameButton.addActionListener(_ -> userController.getController().getVideoGameController().viewInfoGameFrame(playerGame.getVideoGame()));
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
                    videoGameButton.addActionListener(_ -> userController.getController().getVideoGameController().viewInfoGameFrame(playerGame.getVideoGame()));
                }
            }
        }

        // if user is a tester
        if(user instanceof CTester tester){
            panelProfile.add(new JLabel("Nombre de tests : " + tester.getNbTest() + "\n"));
        }

        // if user display is the current user or the current user is an admin
        if(currentUser == user || currentUser instanceof CAdmin){
            String displayBlocked;
            if(user.isBlocked()){
                displayBlocked = "Oui";
            }
            else{
                displayBlocked = "Non";
            }
            panelProfile.add(new JLabel("Bloqué ? : " + displayBlocked + "\n"));
        }

        // if the current user is an admin and the current user is not the displayed user and the displayed user is a player
        if(currentUser instanceof CAdmin && currentUser != user && !(user instanceof CAdmin) && user instanceof CPlayer player){
            JButton promoteButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.PROMOUVOIR) + " cet " + CTextPlaceHolder.UTILISATEUR);
            panelProfile.add(promoteButton);
            promoteButton.addActionListener(_ ->{
                userController.getController().getAdminController().promoteUser(player);
                dispose();
            });

            JButton deleteAccountButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.SUPPRIMER) +
                                                        " ce " + CTextPlaceHolder.COMPTE + " " + CTextPlaceHolder.UTILISATEUR);
            panelProfile.add(deleteAccountButton);
            deleteAccountButton.addActionListener(_ ->{
                userController.deleteAccount(user);
                dispose();
            });

            // if the user displayed is blocked
            if(!user.isBlocked()){
                JButton blockAccountButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.BLOQUER) + " ce " +
                                                            CTextPlaceHolder.COMPTE + " " + CTextPlaceHolder.UTILISATEUR);
                panelProfile.add(blockAccountButton);
                blockAccountButton.addActionListener(_ ->{
                    userController.blockAccount(user);
                    dispose();
                });
            }
            else{
                JButton unblockAccountButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.DEBLOQUER) + " ce " +
                                                            CTextPlaceHolder.COMPTE + " " + CTextPlaceHolder.UTILISATEUR);
                panelProfile.add(unblockAccountButton);
                unblockAccountButton.addActionListener(_ ->{
                    userController.unblockAccount(user);
                    dispose();
                });
            }
        }

        // if the current user is the user and the user is a player
        if(currentUser == user && user instanceof CPlayer){
            JButton desinscrireButton = new JButton("Se " + CTextPlaceHolder.DESINSCRIRE);
            panelProfile.add(desinscrireButton);
            desinscrireButton.addActionListener(_ ->{
                userController.desinscrire(user);
                dispose();
            });
        }

        JButton reloadButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.RECHARGER + " la " + CTextPlaceHolder.PAGE));
        panelProfile.add(reloadButton);
        reloadButton.addActionListener(_ -> {
            dispose();
            userController.openProfile(user);
        });

        add(panelProfile);
    }
}
