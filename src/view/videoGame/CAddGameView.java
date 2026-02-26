package view.videoGame;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CAddGameView extends JFrame {
    private final CVideoGameController videoGameController;
    private final CVideoGame videoGame;

    private final JComboBox<CPlatform> platformBox;

    public CAddGameView(CVideoGameController videoGameController, CVideoGame videoGame, CVideoGameInfoView gameInfoView) {

        this.videoGameController = videoGameController;
        this.videoGame = videoGame;

        setTitle("Ajouter un jeu à sa collection : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Ajouter un jeu à sa collection : " + videoGame.getName()));
        panel.add(new JLabel("Choisir la plateforme :"));

        CPlayer player = (CPlayer) videoGameController.getController().getCurrentUser();

        ArrayList<CPlatform> platformsList = player.getPlatformNotOwnedForGame(videoGame);

        platformBox = new JComboBox<>(
                platformsList.toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        JButton submitButton = new JButton("Ajouter");
        panel.add(submitButton);

        submitButton.addActionListener(e -> addGame(gameInfoView));

        add(panel);
    }

    private void addGame(CVideoGameInfoView videoGameInfoView){
        CPlatform platform = (CPlatform) platformBox.getSelectedItem();
        videoGameController.addGameToCollection(videoGame, platform);
        videoGameInfoView.dispose();
        videoGameController.viewInfoGameFrame(videoGame);
        dispose();
    }
}
