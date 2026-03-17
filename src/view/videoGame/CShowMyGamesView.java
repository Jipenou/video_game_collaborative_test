package view.videoGame;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.utils.CTextPlaceHolder;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CShowMyGamesView extends JFrame{

    public CShowMyGamesView(CVideoGameController videoGameController) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.LISTE) + " de mes " + CTextPlaceHolder.JEU_S);
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        CPlayer actualPlayer = (CPlayer) videoGameController.getController().getCurrentUser();

        List<CVideoGame> games = actualPlayer.getGamesOwned();

        JList<CVideoGame> list = new JList<>(games.toArray(new CVideoGame[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR) + " " + CTextPlaceHolder.INFORMATION_S);
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(_ -> {
            CVideoGame videoGame = list.getSelectedValue();
            if(videoGame != null) {
                videoGameController.viewInfoGameFrame(videoGame);
            }
        });

        add(panel);
    }
}
