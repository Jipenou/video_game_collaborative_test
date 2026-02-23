import controller.CController;
import controller.load.CCSVParserVideoGame;
import model.data.CDatabase;
import view.user.CLoginView;

/**
 * The main class of this application
 */
public class Main {
    private static final String PATH_TO_VIDEO_GAME_DATA = "./data/vg_data.csv";

    public static void main(String[] args){
        CDatabase database = new CDatabase();
        CController controller = new CController(database);

        CCSVParserVideoGame parserVideoGame = new CCSVParserVideoGame(controller, PATH_TO_VIDEO_GAME_DATA);
        parserVideoGame.readCSV();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new CLoginView(controller).setVisible(true);
        });
    }
}
