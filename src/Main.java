import controller.CController;
import controller.load.CCSVParserVideoGame;
import model.data.CDatabase;

public class Main {
    private static final String PATH_TO_VIDEO_GAME_DDATA = "./data/vg_data.csv";

    public static void main(String[] args){
        CDatabase database = new CDatabase();
        CController controller = new CController(database);

        System.out.println("Application lancée");


        CCSVParserVideoGame parserVideoGame = new CCSVParserVideoGame(controller, PATH_TO_VIDEO_GAME_DDATA);
        parserVideoGame.readCSV();

    }
}
