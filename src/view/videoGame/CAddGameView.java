package view.videoGame;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
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

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " un " + CTextPlaceHolder.JEU + " à sa " +
                CTextPlaceHolder.COLLECTION + " : " + videoGame.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER) + " un " + CTextPlaceHolder.JEU +
                                " à sa " + CTextPlaceHolder.COLLECTION + " : " + videoGame.getName()));
        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.CHOISIR) + " la " + CTextPlaceHolder.PLATEFORME + " :"));

        CPlayer player = (CPlayer) videoGameController.getController().getCurrentUser();

        ArrayList<CPlatform> platformsList = player.getPlatformNotOwnedForGame(videoGame);

        platformBox = new JComboBox<>(
                platformsList.toArray(new CPlatform[0])
        );

        panel.add(platformBox);

        JButton submitButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.AJOUTER));
        panel.add(submitButton);

        submitButton.addActionListener(_ -> addGame(gameInfoView));

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
