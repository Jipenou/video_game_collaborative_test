package view.videoGame;

import controller.CVideoGameController;
import model.user.AUser;
import model.user.CPlayer;
import model.user.CTester;
import model.utils.CTextPlaceHolder;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * This class represent the view info of a game
 */
public class CVideoGameInfoView extends JFrame {

    public CVideoGameInfoView(CVideoGameController videoGameController, CVideoGame game) {

        AUser currentUser = videoGameController.getController().getCurrentUser();

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.INFORMATION_S) + " du " + CTextPlaceHolder.JEU);
        setSize(1000,800);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.NOM) + " : " + game.getName()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.GENRE) + " : " + game.getCategory()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.EDITEUR) + " : " + game.getEditor()));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.NOTE) + " " + CTextPlaceHolder.MOYENNE + " : " + game.getRating()));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.PLATEFORME_S) + " supportées :"));

        for(CPlatform platform : game.getPlatforms()){
            panel.add(new JLabel("<html>&nbsp;&nbsp;" + platform.getName() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date de sortie : " + platform.getReleaseYear() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Développeur : " + platform.getDeveloper() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ventes globales : " + platform.getGlobalSale() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Note moyenne des testeurs : " + platform.getTesterRating() + "</html>"));
            panel.add(new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Note moyenne des joueurs : " + platform.getPlayerRating() + "</html>"));
        }

        if(currentUser instanceof CPlayer player){
            // if the player have this game in our collection
            if(player.isGameInCollection(game)) {
                Map<CPlatform, Float> hoursPlayedOnThisGame = player.getHoursPlayedOnAGame(game);
                if(!hoursPlayedOnThisGame.isEmpty()){
                    JLabel nbHoursPlayed = new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.NOMBRE) + " d'" +
                                                       CTextPlaceHolder.HEURE_S + " jouées (" + player.getTotalHoursPlayedOnAGame(game) + "h joué au total) : ");
                    panel.add(nbHoursPlayed);

                    for(CPlatform platform : hoursPlayedOnThisGame.keySet()){
                        JLabel display = new JLabel(platform.getName() + " : " + hoursPlayedOnThisGame.get(platform) + "h");
                        panel.add(display);
                    }
                }
            }
        }

        // if we have some tests
        if(!game.getTests().isEmpty() && currentUser instanceof CPlayer) {
            panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.TEST_S) + " : "));
            for(CTest test : game.getTests().values()){
                JButton testButton = new JButton(test.getDate() + ", " + test.getTester().getPseudo());
                panel.add(testButton);

                testButton.addActionListener(_ -> videoGameController.displayTest(test));
            }
        } else {
            panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.AUCUN) + " " + CTextPlaceHolder.TEST +
                                " " + CTextPlaceHolder.DISPONIBLE));
        }

        if(currentUser instanceof CPlayer player){
            if(!game.areAllPossibleTestsDone()){
                JButton addTokenButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " des " +
                                                    CTextPlaceHolder.JETON_S + " sur le " + CTextPlaceHolder.JEU +
                                                    " (" + game.getAllTokenOnGame() + " pour le moment)");
                panel.add(addTokenButton);
                addTokenButton.addActionListener(_ -> videoGameController.addTokenFrame(this, game));

                if(game.hasUserPlacedToken(player)){
                    JButton removeTokenButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.RETIRER) + " des " +
                                                            CTextPlaceHolder.JETON_S + " sur le " + CTextPlaceHolder.JEU +
                                                            " (Vous avez placés " + game.getAllTokenOnGame() + " " + CTextPlaceHolder.JETON_S +
                                                            " pour le moment)");
                    panel.add(removeTokenButton);
                    removeTokenButton.addActionListener(_ -> videoGameController.removeTokenFrame(this, game));
                }
            }

            // if we dont owned this game on all platform
            if (!player.getPlatformNotOwnedForGame(game).isEmpty()) {
                String label;
                if (!player.isGameInCollection(game)) {
                    label = CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " le " +
                            CTextPlaceHolder.JEU + " à ma " + CTextPlaceHolder.COLLECTION;
                } else {
                    label = CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " le jeu à ma "
                            + CTextPlaceHolder.COLLECTION + " pour une autre " + CTextPlaceHolder.PLATEFORME;
                }
                JButton addToCollectionButton = new JButton(label);
                panel.add(addToCollectionButton);
                addToCollectionButton.addActionListener(_ -> videoGameController.displayAddGameFrame(this, game));
            }
        }

        // if there is evaluations
        if(!game.getEvaluations().isEmpty()) {
            panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.EVALUATION_S) + " : "));
            for(CEvaluation evaluation : game.getEvaluationsSortedByScoreThenDate()){
                JButton evalButton = new JButton(evaluation.getDate().toLocalDate() + ", " + evaluation.getPlayer().getPseudo() + ", utilite+ : " + evaluation.getUtiliteOui());
                panel.add(evalButton);

                evalButton.addActionListener(_ -> videoGameController.displayEvaluation(evaluation));
            }
        } else {
            panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.AUCUN + "e") + " " + CTextPlaceHolder.EVALUATION +
                                    " " + CTextPlaceHolder.DISPONIBLE));
        }

        panel.add(new JSeparator());

        if(currentUser instanceof CPlayer player && player.isGameInCollection(game) && !player.isBlocked() && player.getTotalHoursPlayedOnAGame(game) >= CEvaluation.NUMBER_HOURS_MINIMUM_PLAYED_TO_EVALUATE){
            JButton evalButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " une " + CTextPlaceHolder.EVALUATION);
            panel.add(evalButton);
            evalButton.addActionListener(_ -> videoGameController.addEvaluationFrame(this, game));
        }
        if(currentUser instanceof CTester tester && tester.isGameInCollection(game) && !tester.getPlatformsNotTestedForGame(game).isEmpty() && !tester.isBlocked()){
            JButton testButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " un " + CTextPlaceHolder.TEST);
            panel.add(testButton);
            testButton.addActionListener(_ -> videoGameController.addTestFrame(this, game));
        }
        if(currentUser instanceof CPlayer player){
            // if we have get this game at least on 1 platform
            //if(!player.getPlatformsForGame(game).isEmpty()){
            if(player.isGameInCollection(game)){
                JButton addHoursButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " du " + CTextPlaceHolder.TEMPS
                                                    + " de " + CTextPlaceHolder.JEU);
                panel.add(addHoursButton);
                addHoursButton.addActionListener(_ -> videoGameController.addHoursFrame(this, game));
            }
        }

        JButton reloadButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.RECHARGER) + " la " + CTextPlaceHolder.PAGE);
        panel.add(reloadButton);
        reloadButton.addActionListener(_ -> {
            dispose();
            videoGameController.viewInfoGameFrame(game);
        });

        add(panel);
    }
}
