package view.videoGame;

import controller.CVideoGameController;
import model.utils.CTextPlaceHolder;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the frame that display the games
 */
public class CVideoGamesView extends JFrame {

    public CVideoGamesView(CVideoGameController videoGameController) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.LISTE) + " des " + CTextPlaceHolder.JEU_S);
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CVideoGame> games = videoGameController.getController().getDatabase().getAllVideoGames();

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
