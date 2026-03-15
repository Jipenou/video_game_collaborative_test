package view.videoGame;

import controller.CEvaluationController;
import controller.CVideoGameController;
import model.user.AUser;
import model.user.CPlayer;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;

public class CAddHoursToGameView extends JFrame{
    private final CVideoGameController videoGameController;
    private final CVideoGame videoGame;

    private final JComboBox<CPlatform> platformBox;
    private final JTextArea hoursToAdd;

    public CAddHoursToGameView(CVideoGameController videoGameController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.videoGameController = videoGameController;
        this.videoGame = videoGame;

        CPlayer player = (CPlayer) videoGameController.getController().getCurrentUser();

        setTitle("Ajouter une évaluation : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Choisir la plateforme :"));

        platformBox = new JComboBox<>(
                player.getPlatformsForGame(videoGame).toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        panel.add(new JLabel("Heures jouées :"));
        hoursToAdd = new JTextArea(5,20);
        panel.add(hoursToAdd);

        JButton submitHoursButton = new JButton("Enregistrer");
        panel.add(submitHoursButton);

        submitHoursButton.addActionListener(e -> submitHours(gameInfoView));

        add(panel);
    }

    private void submitHours(CVideoGameInfoView videoGameInfoView){
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();
        int heures = Integer.parseInt(hoursToAdd.getText());

        videoGameController.addHoursToGame(videoGame, platform, heures);
        dispose();
        videoGameInfoView.dispose();
        videoGameController.viewInfoGameFrame(videoGame);
    }
}
