package view.videoGame.token;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;

import javax.swing.*;
import java.awt.*;

public class CAddTokenOnGameView extends JFrame {

    private final CVideoGameController videoGameController;
    private final CVideoGame videoGame;

    final JTextArea nbTokenArea;

    /** The label that react to the actions */
    private final JLabel messageLabel;

    public CAddTokenOnGameView(CVideoGameController videoGameController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.videoGameController = videoGameController;
        this.videoGame = videoGame;

        CPlayer currentUser = (CPlayer) videoGameController.getController().getCurrentUser();

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " des " + CTextPlaceHolder.JETON_S + " sur le " + CTextPlaceHolder.JEU + " : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CHOISIR) + " le" + CTextPlaceHolder.NOMBRE + " de " + CTextPlaceHolder.JETON_S +
                                  " à poser (" + currentUser.getNbToken() + " restants) :"));

        nbTokenArea = new JTextArea(1,20);
        panel.add(nbTokenArea);

        JButton submitButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VALIDER));
        panel.add(submitButton);
        submitButton.addActionListener(_ -> submitTokens(gameInfoView));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);
    }

    private void submitTokens(CVideoGameInfoView gameInfoView){
        CPlayer currentPlayer = (CPlayer) videoGameController.getController().getCurrentUser();

        try {
            int nbTokens = Integer.parseInt(nbTokenArea.getText());
            if (nbTokens > 0 && nbTokens <= currentPlayer.getNbToken()) {
                videoGameController.addTokenToGame(videoGame, nbTokens);
                dispose();
                gameInfoView.dispose();
                videoGameController.viewInfoGameFrame(videoGame);
            } else {
                messageLabel.setText("Valeur incorrecte ou " + CTextPlaceHolder.NOMBRE + " de " + CTextPlaceHolder.JETON_S + " insuffisants");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Veuillez entrer un " + CTextPlaceHolder.NOMBRE + " valide");
        }
    }
}
