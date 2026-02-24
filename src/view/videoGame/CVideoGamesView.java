package view.videoGame;

import controller.CVideoGameController;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the frame that display the games
 */
public class CVideoGamesView extends JFrame {

    /** the main controller of the application */
    private final CVideoGameController videoGameController;

    public CVideoGamesView(CVideoGameController videoGameController) {
        this.videoGameController = videoGameController;

        setTitle("Liste des jeux");
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CVideoGame> games = videoGameController.getController().getDatabase().getAllVideoGames();

        JList<CVideoGame> list = new JList<>(games.toArray(new CVideoGame[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton("Voir infos");
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(e -> {
            CVideoGame videoGame = list.getSelectedValue();
            if(videoGame != null) {
                videoGameController.viewInfoGameFrame(videoGame);
            }
        });

        add(panel);
    }
}
