package view.videoGame.token;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the frame to remove token on a game
 */
public class CRemoveTokenOnGameView extends JFrame {

    /** the video game controller */
    private final CVideoGameController videoGameController;

    /** the video game */
    private final CVideoGame videoGame;

    /** the spinner to select the tokens to remove */
    private final JSpinner nbTokenSpinner;

    /** The label that react to the actions */
    private final JLabel messageLabel;

    public CRemoveTokenOnGameView(CVideoGameController videoGameController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.videoGameController = videoGameController;
        this.videoGame = videoGame;

        CPlayer currentUser = (CPlayer) videoGameController.getController().getCurrentUser();
        int tokensPlaced = videoGame.getTokenPlacedForUser(currentUser);

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.RETIRER) + " des " + CTextPlaceHolder.JETON_S + " sur le " + CTextPlaceHolder.JEU + " : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Vous avez posé (" + videoGame.getTokenPlacedForUser(currentUser) + " " + CTextPlaceHolder.JETON_S + ") :"));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CHOISIR) + "le " + CTextPlaceHolder.NOMBRE + " de " + CTextPlaceHolder.JETON_S +
                                 " à " + CTextPlaceHolder.RETIRER + " :"));

        nbTokenSpinner = new JSpinner(new SpinnerNumberModel(1, 1, tokensPlaced, 1));
        panel.add(nbTokenSpinner);

        JButton submitButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VALIDER));
        panel.add(submitButton);
        submitButton.addActionListener(_ -> submitTokens(gameInfoView));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);
    }

    /**
     * Submit the number of token to remove
     * @param gameInfoView the previous view to refresh after submit
     */
    private void submitTokens(CVideoGameInfoView gameInfoView) {
        int nbTokens = (int) nbTokenSpinner.getValue();
        videoGameController.removeTokenToGame(videoGame, nbTokens);
        dispose();
        gameInfoView.dispose();
        videoGameController.viewInfoGameFrame(videoGame);
    }
}
