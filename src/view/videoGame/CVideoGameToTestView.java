package view.videoGame;

import controller.CVideoGameController;
import model.user.AUser;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CVideoGameToTestView extends JFrame {

    /** the main controller of the application */
    private final CVideoGameController videoGameController;

    public CVideoGameToTestView(CVideoGameController videoGameController) {
        this.videoGameController = videoGameController;

        setTitle("Liste des jeux à tester");
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CVideoGame> games = videoGameController.getController().getDatabase().getVideoGamesSortedByTokens((CPlayer) videoGameController.getController().getCurrentUser());

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
