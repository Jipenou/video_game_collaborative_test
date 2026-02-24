import controller.CController;
import controller.load.CControlLoadVideoGame;
import model.data.CDatabase;
import view.user.CLoginView;

/**
 * The main class of this application
 */
public class Main {
    public static void main(String[] args){
        CDatabase database = new CDatabase();
        CController controller = new CController(database);

        javax.swing.SwingUtilities.invokeLater(() -> {
            new CLoginView(controller).setVisible(true);
        });
    }
}
