package view.videoGame.token;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the frame to add token on a game
 */
public class CAddTokenOnGameView extends JFrame {

    /** the video game controller */
    private final CVideoGameController videoGameController;

    /** the video game concerned */
    private final CVideoGame videoGame;

    /** the spinner to add the hours */
    private final JSpinner nbTokenSpinner;

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

        nbTokenSpinner = new JSpinner(new SpinnerNumberModel(1, 1, currentUser.getNbToken(), 1));
        panel.add(nbTokenSpinner);

        JButton submitButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VALIDER));
        panel.add(submitButton);
        submitButton.addActionListener(_ -> submitTokens(gameInfoView));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);
    }

    /**
     * submit the token added
     * @param gameInfoView the previous view to reload after submitted the test
     */
    private void submitTokens(CVideoGameInfoView gameInfoView) {
        int nbTokens = (int) nbTokenSpinner.getValue();
        videoGameController.addTokenToGame(videoGame, nbTokens);
        dispose();
        gameInfoView.dispose();
        videoGameController.viewInfoGameFrame(videoGame);
    }
}
