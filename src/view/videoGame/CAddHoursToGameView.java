package view.videoGame;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;

public class CAddHoursToGameView extends JFrame{
    private final CVideoGameController videoGameController;
    private final CVideoGame videoGame;

    private final JComboBox<CPlatform> platformBox;
    private final JTextArea hoursToAdd;

    /** The label that react to the actions */
    private final JLabel messageLabel;

    public CAddHoursToGameView(CVideoGameController videoGameController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.videoGameController = videoGameController;
        this.videoGame = videoGame;

        CPlayer player = (CPlayer) videoGameController.getController().getCurrentUser();

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " une " + CTextPlaceHolder.EVALUATION + " : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CHOISIR) + " la " + CTextPlaceHolder.PLATEFORME + " :"));

        platformBox = new JComboBox<>(
                player.getPlatformsForGame(videoGame).toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.HEURE_S) + " jouées :"));
        hoursToAdd = new JTextArea(5,20);
        panel.add(hoursToAdd);

        JButton submitHoursButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.ENREGISTRER));
        panel.add(submitHoursButton);
        submitHoursButton.addActionListener(_ -> submitHours(gameInfoView));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);
    }

    private void submitHours(CVideoGameInfoView videoGameInfoView){
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();

        if (platform == null) {
            messageLabel.setText("Veuillez sélectionner une " + CTextPlaceHolder.PLATEFORME);
            return;
        }

        try {
            int heures = Integer.parseInt(hoursToAdd.getText());

            if (heures <= 0) {
                messageLabel.setText("Le " + CTextPlaceHolder.NOMBRE + " d'" + CTextPlaceHolder.HEURE_S + " doit être positif");
                return;
            }

            videoGameController.addHoursToGame(videoGame, platform, heures);
            dispose();
            videoGameInfoView.dispose();
            videoGameController.viewInfoGameFrame(videoGame);

        } catch (NumberFormatException e) {
            messageLabel.setText("Veuillez entrer un " + CTextPlaceHolder.NOMBRE + " valide");
        }
    }
}
