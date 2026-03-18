package view.videoGame;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;

/**
 * This class represent the frame to add hours to an owned game
 */
public class CAddHoursToGameView extends JFrame{

    /** the video game controller */
    private final CVideoGameController videoGameController;

    /** the video game concerned */
    private final CVideoGame videoGame;

    /** the box that display the platform to add hours on */
    private final JComboBox<CPlatform> platformBox;

    /** the spinner that display the hours to add */
    private final JSpinner hoursSpinner;

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
        hoursSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.5, 9999.0, 0.5));
        panel.add(hoursSpinner);

        JButton submitHoursButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.ENREGISTRER));
        panel.add(submitHoursButton);
        submitHoursButton.addActionListener(_ -> submitHours(gameInfoView));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(messageLabel);

        add(panel);
    }

    /**
     * Submit the hours added
     * @param videoGameInfoView the previous view to update after submit
     */
    private void submitHours(CVideoGameInfoView videoGameInfoView) {
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();

        if (platform == null) {
            return;
        }

        float heures = ((Double) hoursSpinner.getValue()).floatValue();
        videoGameController.addHoursToGame(videoGame, platform, heures);
        dispose();
        videoGameInfoView.dispose();
        videoGameController.viewInfoGameFrame(videoGame);
    }
}
