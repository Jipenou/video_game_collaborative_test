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

        if(userController.getController().getCurrentUser() == user && user instanceof CPlayer){
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
