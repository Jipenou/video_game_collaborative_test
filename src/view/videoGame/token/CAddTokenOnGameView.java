package view.videoGame.token;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.videoGame.CVideoGame;
import view.videoGame.CVideoGameInfoView;

import javax.swing.*;
import java.awt.*;

public class CAddTokenOnGameView extends JFrame {

    private final CVideoGameController videoGameController;
    private final CVideoGame videoGame;

    final JTextArea nbTokenArea;

    /** The label that react to the actions */
    private JLabel messageLabel;

    public CAddTokenOnGameView(CVideoGameController videoGameController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.videoGameController = videoGameController;
        this.videoGame = videoGame;

        CPlayer currentUser = (CPlayer) videoGameController.getController().getCurrentUser();

        setTitle("Ajouter des token sur le jeux : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Choisir le nombre de jetons à poser (" + currentUser.getNbToken() + " restants) :"));

        nbTokenArea = new JTextArea(1,20);
        panel.add(nbTokenArea);

        JButton submitButton = new JButton("Valider");
        panel.add(submitButton);
        submitButton.addActionListener(e -> submitTokens(gameInfoView));

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
                messageLabel.setText("Valeur incorrecte ou nombre de jetons insuffisant");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Veuillez entrer un nombre valide");
        }
    }
}
