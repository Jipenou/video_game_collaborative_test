package view.videoGame;

import controller.CController;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the frame that display the games
 */
public class gamesView extends JFrame {

    /** the main controller of the application */
    private final CController controller;

    public gamesView(CController controller) {
        this.controller = controller;

        setTitle("Liste des jeux");
        setSize(400,300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CVideoGame> games = controller.getDatabase().getAllVideoGames();

        JList<CVideoGame> list = new JList<>(games.toArray(new CVideoGame[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton("Voir infos");
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(e -> {
            CVideoGame videoGame = list.getSelectedValue();
            if(videoGame != null) {
                new CvideoGameInfoView(controller, videoGame).setVisible(true);
            }
        });

        add(panel);
    }
}
