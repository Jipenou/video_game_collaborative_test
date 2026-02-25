package view.videoGame;

import controller.CVideoGameController;
import model.user.CPlayer;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CShowMyGamesView extends JFrame{
    /** the main controller of the application */
    private final CVideoGameController videoGameController;

    public CShowMyGamesView(CVideoGameController videoGameController) {
        this.videoGameController = videoGameController;

        setTitle("Liste de mes jeux");
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        CPlayer actualPlayer = (CPlayer) videoGameController.getController().getCurrentUser();

        List<CVideoGame> games = actualPlayer.getGamesOwned();

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
